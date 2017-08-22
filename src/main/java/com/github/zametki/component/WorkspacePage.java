package com.github.zametki.component;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.behavior.ajax.CreateGroupAjaxCallback;
import com.github.zametki.behavior.ajax.DeleteGroupAjaxCallback;
import com.github.zametki.behavior.ajax.MoveGroupAjaxCallback;
import com.github.zametki.behavior.ajax.RenameGroupAjaxCallback;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MountPath("/my")
public class WorkspacePage extends BaseUserPage {

    public final WorkspacePageState state = new WorkspacePageState();

    private final ContainerWithId navbarView = new ContainerWithId("navbar");
    private final ContainerWithId groupsView = new ContainerWithId("groups");
    private final ContainerWithId notesView = new ContainerWithId("notes");

    public WorkspacePage(PageParameters pp) {
        super(pp);

        //todo: rework all callbacks to pure AJAX
        add(new CreateGroupAjaxCallback(state.activeGroupModel));
        add(new MoveGroupAjaxCallback(state.activeGroupModel));
        add(new RenameGroupAjaxCallback(state.activeGroupModel));
        add(new DeleteGroupAjaxCallback(state.activeGroupModel));

        add(navbarView);
        add(groupsView);
        add(notesView);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript(getInitScript()));
    }

    @NotNull
    public String getInitScript() {
        GroupTreeModel treeModel = GroupTreeModel.build(WebUtils.getUserOrRedirectHome());
        JSONArray groups = new JSONArray();
        treeModel.flatList().forEach(n -> groups.put(toJSON(n)));

        JSONObject initContext = new JSONObject();
        initContext.put("groups", groups);
        initContext.put("notesViewId", notesView.getMarkupId());
        initContext.put("navbarViewId", navbarView.getMarkupId());
        initContext.put("groupsViewId", groupsView.getMarkupId());
        return "$site.Server2Client.init(" + initContext.toString() + ");";
    }

    private static final Group ROOT_GROUP = new Group();
    static {
        ROOT_GROUP.name = "root";
    }

    @Nullable
    private JSONObject toJSON(@NotNull GroupTreeNode node) {
        JSONObject json = new JSONObject();
        GroupId groupId = node.getGroupId();
        Group g = groupId.isRoot() ? ROOT_GROUP : Context.getGroupsDbi().getById(groupId);
        if (g == null) {
            return null;
        }
        json.put("id", g.id == null ? 0 : g.id.intValue);
        json.put("name", g.name);
        json.put("parentId", g.parentId.intValue);
        json.put("level", node.getLevel());
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
