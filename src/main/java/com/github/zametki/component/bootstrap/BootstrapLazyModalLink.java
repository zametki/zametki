package com.github.zametki.component.bootstrap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.jetbrains.annotations.NotNull;

public class BootstrapLazyModalLink extends AjaxLink<Void> {

    @NotNull
    private final BootstrapModal modal;

    public BootstrapLazyModalLink(@NotNull String id, @NotNull BootstrapModal modal) {
        super(id);
        this.modal = modal;
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        modal.show(target);
        target.appendJavaScript("$('#" + modal.getDataTargetId() + "').modal();");
    }
}
