package com.github.zametki.behavior.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.User;
import com.github.zametki.model.UserId;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;

public class DeleteGroupAjaxCallback extends ZApiAjaxCallback {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public DeleteGroupAjaxCallback(@NotNull IModel<GroupId> activeGroupModel) {
        super("deleteGroup", new String[]{"groupId"});
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        //todo: common subclass to check user auth status
        UserId userId = UserSession.get().getUserId();
        User user = userId == null ? null : Context.getUsersDbi().getUserById(userId);
        if (user == null) {
            return;
        }

        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("groupId").toInt(-1)));
        //todo: common utils to check user rights
        if (group == null || group.id.isRoot() || !group.userId.equals(userId)) {
            return;
        }

        //todo: report errors!

        int nChildren = Context.getZametkaDbi().countByGroup(userId, group.id);
        if (nChildren > 0) {
            return;
        }
        GroupTreeModel tree = GroupTreeModel.build(user);
        GroupTreeNode node = tree.nodeByGroup.get(group.id);
        if (!node.isLeaf()) {
            return;
        }

        // here we have empty group we can safely remove.
        GroupId newActiveId = group.parentId;
        Context.getGroupsDbi().removeEmptyGroup(group);

        Page page = target.getPage();
        page.send(page, Broadcast.BREADTH, new GroupUpdateEvent(target, userId, group.id));
        WicketUtils.reactiveUpdate(activeGroupModel, newActiveId, target);
    }

}
