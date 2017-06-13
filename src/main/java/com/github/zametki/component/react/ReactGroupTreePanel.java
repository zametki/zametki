package com.github.zametki.component.react;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupTreeExpandedStateChangeEvent;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserSettings;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ReactGroupTreePanel extends Panel {

    @NotNull
    private final ContainerWithId tree = new ContainerWithId("tree");

    @NotNull
    private final IModel<GroupId> activeCategoryModel;

    public ReactGroupTreePanel(String id, @NotNull IModel<GroupId> activeCategoryModel) {
        super(id);
        this.activeCategoryModel = activeCategoryModel;
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

    @OnPayload(GroupTreeExpandedStateChangeEvent.class)
    public void onGroupUpdated(GroupTreeExpandedStateChangeEvent e) {
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
    public String getUpdateScript() {
        GroupTreeModel treeModel = GroupTreeModel.build(WebUtils.getUserOrRedirectHome());
        JSONObject jsonTree = toJSON((GroupTreeNode) treeModel.getRoot());
        if (jsonTree == null) {
            jsonTree = new JSONObject();
        }
        return "$site.ReactUtils.onGroupTreeChanged(" + jsonTree.toString() + ");";
    }

    @Nullable
    private JSONObject toJSON(@NotNull GroupTreeNode node) {
        Set<GroupId> expandedGroups = UserSettings.get().getExpandedGroups();
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
        if (g.id != null && g.id.equals(activeCategoryModel.getObject())) {
            json.put("active", true);
        }
        if (expandedGroups.contains(g.id)) {
            json.put("expanded", true);
        }
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
