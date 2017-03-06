package com.github.zametki.component.bootstrap;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.jetbrains.annotations.NotNull;

public class BootstrapModalCloseLink extends WebMarkupContainer {

    public BootstrapModalCloseLink(@NotNull String id) {
        super(id);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("href", "javascript:;");
        tag.put("onclick", "$site.Utils.closeModal(this);");
    }
}
