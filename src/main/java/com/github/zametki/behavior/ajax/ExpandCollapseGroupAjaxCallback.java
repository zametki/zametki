package com.github.zametki.behavior.ajax;

import com.github.openjson.JSONArray;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.event.GroupTreeExpandedStateChangeEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.UserSettings;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;

public class ExpandCollapseGroupAjaxCallback extends AbstractDefaultAjaxBehavior {

    @Override
    protected void respond(AjaxRequestTarget target) {
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("groupId").toInt(-1)));
        boolean expanded = params.getParameterValue("expanded").toBoolean();
        UserId userId = UserSession.get().getUserId();
        if (group == null || !group.userId.equals(userId)) {
            return;
        }
        //todo:
        UserSettings us = UserSettings.get();
        us.expandedGroups = new JSONArray();
        us.expandedGroups.put(group.id.getDbValue());
        UserSettings.set(us);

        Page page = target.getPage();
        page.send(page, Broadcast.BREADTH, new GroupTreeExpandedStateChangeEvent(target, group.id, expanded));
    }

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.setMethod(AjaxRequestAttributes.Method.POST);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        CallbackParameter[] params = new CallbackParameter[]{
                CallbackParameter.explicit("groupId"),
                CallbackParameter.explicit("expanded")
        };
        response.render(OnDomReadyHeaderItem.forScript("$site.Ajax.callbacks.toggleGroupExpandedState=" + getCallbackFunction(params) + "\n"));
    }
}
