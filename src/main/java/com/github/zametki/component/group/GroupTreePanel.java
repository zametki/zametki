package com.github.zametki.component.group;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GroupTreePanel extends Panel {

    @NotNull
    private final ContainerWithId tree = new ContainerWithId("tree");

    @NotNull
    private IModel<GroupId> activeGroupModel;

    public GroupTreePanel(@Nullable String id, @NotNull IModel<GroupId> activeGroupModel) {
        super(id);
        this.activeGroupModel = activeGroupModel;
        add(tree);
    }

    @OnPayload(GroupTreeChangeEvent.class)
    public void onGroupTreeChanged(GroupTreeChangeEvent e) {
        update(e.target);
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        update(e.target);
    }

    private void update(@NotNull AjaxRequestTarget target) {
        target.appendJavaScript(getUpdateScript());
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.GROUP_CHANGED || e.updateType == ZametkaUpdateType.CREATED || e.updateType == ZametkaUpdateType.DELETED) {
            update(e.target);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getUpdateScript() + ";$site.Server2Client.renderGroupTreeView('" + tree.getMarkupId() + "')"));
    }

    private static final Group ROOT_GROUP = new Group();

    static {
        ROOT_GROUP.name = "root";
    }

    @NotNull
    public String getUpdateScript() {
        GroupTreeModel treeModel = GroupTreeModel.build(WebUtils.getUserOrRedirectHome());
        JSONArray nodes = new JSONArray();
        treeModel.flatList().forEach(n -> nodes.put(toJSON(n)));
        return "$site.Server2Client.dispatchUpdateGroupTreeAction(" + nodes.toString() + ");";
    }

    @Nullable
    private JSONObject toJSON(@NotNull GroupTreeNode node) {
        JSONObject json = new JSONObject();
        GroupId groupId = node.getGroupId();
        Group g = groupId.isRoot() ? ROOT_GROUP : Context.getGroupsDbi().getById(groupId);
        if (g == null) {
            return null;
        }
        json.put("id", g.id == null ? 0 : g.id.getDbValue());
        json.put("name", g.name);
        json.put("parentId", g.parentId.intValue);
        json.put("level", node.getLevel());
        json.put("active", node.getGroupId().equals(activeGroupModel.getObject()));
        json.put("entriesCount", Context.getZametkaDbi().countByGroup(g.userId, groupId));
        JSONArray children = new JSONArray();
        for (int i = 0, n = node.getChildCount(); i < n; i++) {
            GroupTreeNode childNode = (GroupTreeNode) node.getChildAt(i);
            children.put(childNode.getGroupId().intValue);
        }
        json.put("children", children);
        return json;
    }
}
