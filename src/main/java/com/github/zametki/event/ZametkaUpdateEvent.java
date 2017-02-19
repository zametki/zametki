package com.github.zametki.event;

import com.github.zametki.model.ZametkaId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class ZametkaUpdateEvent extends AjaxEvent {
    @NotNull
    public final ZametkaId zametkaId;

    public ZametkaUpdateEvent(@NotNull AjaxRequestTarget target, @NotNull ZametkaId zametkaId) {
        super(target);
        this.zametkaId = zametkaId;
    }
}
