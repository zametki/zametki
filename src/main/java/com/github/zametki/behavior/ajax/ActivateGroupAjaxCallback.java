package com.github.zametki.behavior.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;

import static org.apache.wicket.ajax.attributes.CallbackParameter.explicit;

public class ActivateGroupAjaxCallback extends AbstractDefaultAjaxBehavior {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public ActivateGroupAjaxCallback(@NotNull IModel<GroupId> activeGroupModel) {
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return;
        }
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("groupId").toInt(-1)));
        if (group == null || !group.userId.equals(userId)) {
            return;
        }
        WicketUtils.reactiveUpdate(activeGroupModel, group.id, target);
    }

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.setMethod(AjaxRequestAttributes.Method.POST);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        CallbackParameter[] params = new CallbackParameter[]{explicit("groupId"),};
        response.render(OnDomReadyHeaderItem.forScript("$site.Ajax.activateGroup =" + getCallbackFunction(params) + "\n"));
    }
}
