package com.github.zametki.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.jetbrains.annotations.NotNull;

public class ToggleDisplayBehavior extends Behavior {

    @NotNull
    public final Component blockToShowHide;

    public ToggleDisplayBehavior(@NotNull Component blockToShowHide, @NotNull String initialState) {
        this.blockToShowHide = blockToShowHide;
        blockToShowHide.setOutputMarkupId(true);
        blockToShowHide.add(new AttributeAppender("style", "display:" + initialState + ";") {
            @Override
            public boolean isTemporary(Component component) {
                return true;
            }
        });
    }

    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        tag.getAttributes().put("onclick", "$('#" + blockToShowHide.getMarkupId() + "').toggle(); return false;");
    }
}
