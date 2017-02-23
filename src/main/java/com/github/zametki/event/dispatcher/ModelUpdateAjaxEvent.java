package com.github.zametki.event.dispatcher;

import com.github.zametki.event.AjaxEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

public class ModelUpdateAjaxEvent extends AjaxEvent {

    @NotNull
    public final IModel model;

    public ModelUpdateAjaxEvent(@NotNull AjaxRequestTarget target, @NotNull IModel model) {
        super(target);
        this.model = model;
    }
}
