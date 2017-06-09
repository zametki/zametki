package com.github.zametki.component.react;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReactGroupTreePanel extends Panel {

    private final ContainerWithId tree = new ContainerWithId("tree");

    public ReactGroupTreePanel(String id) {
        super(id);
        add(tree);
    }

    @OnPayload(GroupTreeChangeEvent.class)
    public void onGroupTreeChanged(GroupTreeChangeEvent e) {
        e.target.appendJavaScript(getUpdateScript());
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        e.target.appendJavaScript(getUpdateScript());
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        e.target.appendJavaScript(getUpdateScript());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getUpdateScript() + ";$site.ReactUtils.renderGroupTree('" + tree.getMarkupId() + "')"));
    }

    private static final Group ROOT_GROUP = new Group();

    static {
        ROOT_GROUP.name = "root";
    }

    @NotNull
    public static String getUpdateScript() {
        GroupTreeModel treeModel = GroupTreeModel.build(WebUtils.getUserOrRedirectHome());
        JSONObject jsonTree = toJSON((GroupTreeNode) treeModel.getRoot());
        if (jsonTree == null) {
            jsonTree = new JSONObject();
        }
        return "$site.ReactUtils.onGroupTreeChanged(" + jsonTree.toString() + ");";
    }

    @Nullable
    private static JSONObject toJSON(@NotNull GroupTreeNode node) {
        JSONObject json = new JSONObject();
        GroupId groupId = node.getGroupId();
        Group g = groupId.isRoot() ? ROOT_GROUP : Context.getGroupsDbi().getById(groupId);
        if (g == null) {
            return null;
        }
        json.put("id", g.id == null ? 0 : g.id.getDbValue());
        json.put("name", g.name);
        json.put("parent", g.parentId.intValue);
        json.put("level", node.getLevel());
        int childCount = node.getChildCount();
        if (childCount > 0) {
            JSONArray children = new JSONArray();
            for (int i = 0; i < childCount; i++) {
                GroupTreeNode childNode = (GroupTreeNode) node.getChildAt(i);
                JSONObject childJson = toJSON(childNode);
                if (childJson != null) {
                    children.put(childJson);
                }
            }
            if (children.length() > 0) {
                json.put("children", children);
            }
        }
        return json;
    }


}
