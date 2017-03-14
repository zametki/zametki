package com.github.zametki.component.zametka;

import com.github.zametki.Context;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

public class ZametkaGroupLabel extends Label {

    @NotNull
    private final ZametkaId zametkaId;

    public ZametkaGroupLabel(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id, new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                Zametka z = Context.getZametkaDbi().getById(zametkaId);
                Group cat = z == null ? null : Context.getGroupsDbi().getById(z.groupId);
                return cat == null ? "???" : cat.name;
            }
        });
        this.zametkaId = zametkaId;
        setOutputMarkupId(true);
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.GROUP_CHANGED && zametkaId.equals(e.zametkaId)) {
            detach();
            e.target.add(this);
        }
    }
}
