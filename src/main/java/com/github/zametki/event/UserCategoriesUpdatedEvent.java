package com.github.zametki.event;

import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class UserCategoriesUpdatedEvent extends AjaxEvent {

    @NotNull
    public final UserId userId;

    @NotNull
    public final CategoryId categoryId;

    public UserCategoriesUpdatedEvent(@NotNull AjaxRequestTarget target, @NotNull UserId userId, @NotNull CategoryId categoryId) {
        super(target);
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
