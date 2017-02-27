package com.github.zametki.component.z;

import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.basic.InvisibleBlock;
import com.github.zametki.component.form.CreateZametkaForm;
import com.github.zametki.model.CategoryId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class CreateZametkaButtonPanel extends Panel {

    public CreateZametkaButtonPanel(String id, IModel<CategoryId> activeCategory) {
        super(id);

        WebMarkupContainer panel = new ContainerWithId("panel");
        add(panel);

        panel.add(new InvisibleBlock("form_panel"));

        AjaxLink<Void> toggleLink = new AjaxLink<Void>("toggle_form_panel_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (isFormVisible()) {
                    switchToCollapsedMode(target);
                    return;
                }
                panel.get("form_panel").replaceWith(new CreateZametkaForm("form_panel", activeCategory, (AjaxCallback) this::switchToCollapsedMode));
                setVisible(false);
                target.add(panel);
            }

            private void switchToCollapsedMode(AjaxRequestTarget target) {
                panel.get("form_panel").replaceWith(new InvisibleBlock("form_panel"));
                setVisible(true);
                target.add(panel);
            }

            private boolean isFormVisible() {
                return panel.get("form_panel").isVisible();
            }
        };
        panel.add(toggleLink);
    }
}
