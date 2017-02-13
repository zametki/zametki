package com.github.zametki.event;

import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class UserCategoriesUpdatedEvent extends AjaxEvent {
    @NotNull
    public final UserId userId;

    public UserCategoriesUpdatedEvent(@NotNull AjaxRequestTarget target, @NotNull UserId userId) {
        super(target);
        this.userId = userId;
    }
}
