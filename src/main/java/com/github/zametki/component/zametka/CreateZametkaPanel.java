package com.github.zametki.component.zametka;

import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.basic.InvisibleBlock;
import com.github.zametki.component.form.CreateZametkaForm;
import com.github.zametki.event.CreateZametkaFormToggleEvent;
import com.github.zametki.model.GroupId;
import com.github.zametki.util.JsUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class CreateZametkaPanel extends Panel {

    private final WebMarkupContainer panel = new ContainerWithId("panel");
    private final IModel<GroupId> activeCategory;

    public CreateZametkaPanel(String id, IModel<GroupId> activeCategory) {
        super(id);
        this.activeCategory = activeCategory;

        add(panel);

        panel.add(new InvisibleBlock("form_panel"));
    }

    public void toggle(AjaxRequestTarget target) {
        if (isFormVisible()) {
            switchToCollapsedMode(target);
            return;
        }
        //noinspection WicketForgeJavaIdInspection
        CreateZametkaForm createForm = new CreateZametkaForm("form_panel", activeCategory, (AjaxCallback) this::switchToCollapsedMode);
        panel.get("form_panel").replaceWith(createForm);
        target.add(panel);

        JsUtils.focus(target, createForm.contentField);
        send(getPage(), Broadcast.BREADTH, new CreateZametkaFormToggleEvent(target, true));
    }

    private void switchToCollapsedMode(AjaxRequestTarget target) {
        //noinspection WicketForgeJavaIdInspection
        panel.get("form_panel").replaceWith(new InvisibleBlock("form_panel"));
        target.add(panel);
        send(getPage(), Broadcast.BREADTH, new CreateZametkaFormToggleEvent(target, false));
    }

    private boolean isFormVisible() {
        return panel.get("form_panel").isVisible();
    }

    public boolean isActive() {
        return panel.get("form_panel").isVisible();
    }
}
