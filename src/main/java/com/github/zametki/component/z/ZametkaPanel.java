package com.github.zametki.component.z;

import com.github.zametki.Context;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.model.Category;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;

public class ZametkaPanel extends Panel {
    public ZametkaPanel(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id);
        Zametka z = Context.getZametkaDbi().getById(zametkaId);
        if (z == null) {
            setVisible(false);
            return;
        }
        add(new Label("content", z.content));
        Category cat = Context.getCategoryDbi().getById(z.categoryId);
        add(new Label("category", cat == null ? "???" : cat.title));

        add(new AjaxLink<Void>("delete_link") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Context.getZametkaDbi().delete(zametkaId);
                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId));
            }
        });
    }
}
