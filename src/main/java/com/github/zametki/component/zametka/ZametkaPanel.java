package com.github.zametki.component.zametka;

import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.basic.InvisibleBlock;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.ZDateFormat;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

public class ZametkaPanel extends Panel {

    private static final ZDateFormat DF = ZDateFormat.getInstance("dd MMMM yyyy", Constants.MOSCOW_TZ);
    @NotNull
    private final ZametkaId zametkaId;

    @NotNull
    private final WebMarkupContainer panel = new ContainerWithId("panel");

    public ZametkaPanel(@NotNull String id, @NotNull ZametkaId zametkaId, @NotNull Settings settings) {
        super(id);
        this.zametkaId = zametkaId;
        Zametka z = Context.getZametkaDbi().getById(zametkaId);
        if (z == null) {
            setVisible(false);
            return;
        }
        add(panel);
        panel.add(settings.showCategory ? new ZametkaGroupBadge("group_badge", zametkaId) : new InvisibleBlock("group_badge"));
        panel.add(new Label("date", DF.format(z.creationDate)));
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
                    JsUtils.focus(target, ((EditZametkaPanel) panel.get("edit_panel")).contentField);
                    return;
                }
                EditZametkaPanel editPanel = new EditZametkaPanel("edit_panel", zametkaId, (AjaxCallback) this::closeEditPanel);
                panel.get("edit_panel").replaceWith(editPanel);
                target.add(panel);

                JsUtils.focus(target, editPanel.contentField);
            }

            private void closeEditPanel(AjaxRequestTarget target) {
                panel.get("edit_panel").replaceWith(new WebMarkupContainer("edit_panel"));
                target.add(panel);
            }
        });

        BootstrapModal movePopup = new BootstrapModal("move_popup", "Изменение группы",
                (ComponentFactory) markupId -> new MoveZametkaPopup(markupId, zametkaId),
                BootstrapModal.BodyMode.Lazy, BootstrapModal.FooterMode.Hide);
        panel.add(movePopup);

        panel.add(new BootstrapLazyModalLink("move_link", movePopup));
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.DELETED && zametkaId.equals(e.zametkaId)) {
            panel.setVisible(false);
            e.target.add(panel);
        }
    }

    public static class Settings implements IClusterable {
        public boolean showCategory;
    }
}
