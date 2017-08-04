package com.github.zametki.behavior.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupTreeChangeType;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;

public class MoveGroupAjaxCallback extends ZApiAjaxCallback {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public MoveGroupAjaxCallback(@NotNull IModel<GroupId> activeGroupModel) {
        super("moveGroup", new String[]{"groupId", "parentGroupId"});
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        //todo: common subclass to check user auth status
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return;
        }
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("groupId").toInt(-1)));
        //todo: common utils to check user rights
        if (group == null || !group.userId.equals(userId)) {
            return;
        }
        GroupId parentGroupId = new GroupId(params.getParameterValue("parentGroupId").toInt(-1));
        if (!parentGroupId.isRoot()) {
            Group parentGroup = Context.getGroupsDbi().getById(parentGroupId);
            if (parentGroup == null || !parentGroup.userId.equals(userId)) {
                return;
            }
        }
        group.parentId = parentGroupId;
        Context.getGroupsDbi().update(group);

        Page page = target.getPage();
        page.send(page, Broadcast.BREADTH, new GroupTreeChangeEvent(target, userId, group.id, GroupTreeChangeType.GroupParentUpdated));
        WicketUtils.reactiveUpdate(activeGroupModel, group.id, target);
    }
}
