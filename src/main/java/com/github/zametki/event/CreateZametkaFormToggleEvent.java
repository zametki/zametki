package com.github.zametki.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class CreateZametkaFormToggleEvent extends AjaxEvent {
    public final boolean active;

    public CreateZametkaFormToggleEvent(@NotNull AjaxRequestTarget target, boolean active) {
        super(target);
        this.active = active;
    }
}
