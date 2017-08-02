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

public class CreateGroupAjaxCallback extends ZApiAjaxCallback {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public CreateGroupAjaxCallback(@NotNull IModel<GroupId> activeGroupModel) {
        super("createGroup", new String[]{"parentGroupId", "name"});
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return;
        }
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group parentGroup = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("parentGroupId").toInt(-1)));
        if (parentGroup != null && !parentGroup.userId.equals(userId)) {
            return;
        }
        String name = params.getParameterValue("name").toString();
        if (name.isEmpty()) {
            return;
        }
        Group group = new Group();
        group.parentId = parentGroup != null ? parentGroup.id : GroupId.UNDEFINED;
        group.name = name;
        group.userId = userId;
        Context.getGroupsDbi().create(group);

        Page page = target.getPage();
        page.send(page, Broadcast.BREADTH, new GroupTreeChangeEvent(target, userId, group.id, GroupTreeChangeType.GroupCreated));
        WicketUtils.reactiveUpdate(activeGroupModel, group.id, target);
    }

}
