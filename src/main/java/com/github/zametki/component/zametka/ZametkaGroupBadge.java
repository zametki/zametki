package com.github.zametki.component.zametka;

import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.provider.GreoupsProvider;
import com.github.zametki.event.UserGroupUpdatedEvent;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.EmptyDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.jetbrains.annotations.NotNull;

public class ZametkaGroupBadge extends Panel {

    @NotNull
    private final IDataProvider<GroupId> provider;

    public ZametkaGroupBadge(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id);
        Zametka zametka = Context.getZametkaDbi().getById(zametkaId);
        if (zametka == null) {
            setVisible(false);
            provider = new EmptyDataProvider<>();
            return;
        }
        WebMarkupContainer panel = new ContainerWithId("panel");
        add(panel);

        provider = new GreoupsProvider(zametka.userId);
        panel.add(new ZametkaGroupLabel("group_label", zametkaId));
        panel.add(new DataView<GroupId>("group_option", provider) {
            @Override
            protected void populateItem(Item<GroupId> item) {
                GroupId groupId = item.getModelObject();
                Group cat = Context.getGroupsDbi().getById(groupId);
                if (cat == null) {
                    item.setVisible(false);
                    return;
                }
                AjaxLink link = new AjaxLink<Void>("group_link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        Zametka z = Context.getZametkaDbi().getById(zametkaId);
                        if (z == null || z.groupId.equals(groupId)) {
                            return;
                        }
                        z.groupId = groupId;
                        Context.getZametkaDbi().update(z);
                        send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId, ZametkaUpdateType.CATEGORY_CHANGED));
                    }
                };
                item.add(link);

                link.add(new Label("group_name", cat.name));
            }
        });
    }

    @OnPayload(UserGroupUpdatedEvent.class)
    public void onCategoriesUpdate(@NotNull UserGroupUpdatedEvent e) {
        assert provider != null;
    }
}
