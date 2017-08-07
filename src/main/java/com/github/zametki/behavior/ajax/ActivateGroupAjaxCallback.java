package com.github.zametki.behavior.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;

public class ActivateGroupAjaxCallback extends ZApiAjaxCallback {
    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public ActivateGroupAjaxCallback(@NotNull IModel<GroupId> activeGroupModel) {
        super("activateGroup", new String[]{"groupId"});
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return;
        }
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        GroupId groupId = new GroupId(params.getParameterValue("groupId").toInt(-1));
        if (!groupId.isRoot()) {
            Group group = Context.getGroupsDbi().getById(groupId);
            if (group == null || !group.userId.equals(userId)) {
                return;
            }
        }
        WicketUtils.reactiveUpdate(activeGroupModel, groupId.isValid() ? groupId : null, target);
    }
}
