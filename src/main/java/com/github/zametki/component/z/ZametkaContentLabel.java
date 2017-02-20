package com.github.zametki.component.z;

import com.github.zametki.Context;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

public class ZametkaContentLabel extends Label {

    @NotNull
    private final ZametkaId zametkaId;

    public ZametkaContentLabel(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id, new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                Zametka zam = Context.getZametkaDbi().getById(zametkaId);
                return zam == null ? "???" : zam.content;
            }
        });
        this.zametkaId = zametkaId;
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.CONTENT_CHANGED && zametkaId.equals(e.zametkaId)) {
            detach();
            e.target.add(this);
        }
    }
}
