package com.github.zametki.behavior.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class ZApiAjaxCallback extends AbstractDefaultAjaxBehavior {

    protected final String callbackName;
    protected final String[] paramNames;

    public ZApiAjaxCallback(@NotNull String callbackName, @NotNull String[] paramNames) {
        this.callbackName = callbackName;
        this.paramNames = paramNames;
    }

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.setMethod(AjaxRequestAttributes.Method.POST);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        CallbackParameter[] params = Arrays.stream(paramNames).map(CallbackParameter::explicit).toArray(CallbackParameter[]::new);
        response.render(OnDomReadyHeaderItem.forScript("$site.Ajax." + callbackName + " =" + getCallbackFunction(params) + "\n"));
    }


}
