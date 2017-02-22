package com.github.zametki.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class LentaPageSelectedCategoryChanged extends AjaxEvent {
    public LentaPageSelectedCategoryChanged(@NotNull AjaxRequestTarget target) {
        super(target);
    }
}
