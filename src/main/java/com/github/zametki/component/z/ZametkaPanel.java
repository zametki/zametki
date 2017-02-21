package com.github.zametki.component.z;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.ZametkaId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;

public class ZametkaPanel extends Panel {

    @NotNull
    private final ZametkaId zametkaId;

    @NotNull
    private final WebMarkupContainer panel = new ContainerWithId("panel");

    public ZametkaPanel(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id);
        this.zametkaId = zametkaId;

        add(panel);
        panel.add(new ZametkaCategoryBadge("category_badge", zametkaId));
        panel.add(new ZametkaContentLabel("content", zametkaId));
        panel.add(new AjaxLink<Void>("delete_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Context.getZametkaDbi().delete(zametkaId);
                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId, ZametkaUpdateType.DELETED));
            }
        });
        panel.add(new WebMarkupContainer("edit_panel"));
        panel.add(new AjaxLink<Void>("edit_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (panel.get("edit_panel") instanceof EditZametkaPanel) {
                    return;
                }
                panel.get("edit_panel").replaceWith(new EditZametkaPanel("edit_panel", zametkaId, (AjaxCallback) this::closeEditPanel));
                target.add(panel);
            }

            private void closeEditPanel(AjaxRequestTarget target) {
                panel.get("edit_panel").replaceWith(new WebMarkupContainer("edit_panel"));
                target.add(panel);
            }
        });
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.DELETED && zametkaId.equals(e.zametkaId)) {
            panel.setVisible(false);
            e.target.add(panel);
        }
    }
}
