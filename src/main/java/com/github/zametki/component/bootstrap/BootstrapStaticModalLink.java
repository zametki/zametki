package com.github.zametki.component.bootstrap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.jetbrains.annotations.NotNull;

public class BootstrapStaticModalLink extends WebMarkupContainer {

    public BootstrapStaticModalLink(@NotNull String id, @NotNull BootstrapModal modal) {
        super(id);

        if (modal.bodyMode != BootstrapModal.BodyMode.Static) {
            throw new IllegalStateException("BootstrapStaticModalLink is valid only for modals with static body!");
        }

        add(new AttributeModifier("onclick", "$('#" + modal.getDataTargetId() + "').modal();"));
        add(new AttributeModifier("href", "javascript:"));
    }
}
