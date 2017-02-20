package com.github.zametki.component.z;

import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.provider.CategoriesProvider;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
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

public class ZametkaCategoryBadge extends Panel {

    private final WebMarkupContainer panel = new ContainerWithId("panel");

    @NotNull
    private final UserId userId;

    @NotNull
    private final IDataProvider<CategoryId> provider;

    public ZametkaCategoryBadge(@NotNull String id, @NotNull ZametkaId zametkaId) {
        super(id);

        Zametka zametka = Context.getZametkaDbi().getById(zametkaId);
        if (zametka == null) {
            setVisible(false);
            userId = UserId.INVALID_ID;
            provider = new EmptyDataProvider<>();
            return;
        }
        userId = zametka.userId;
        provider = new CategoriesProvider(zametka.userId);

        add(panel);
        panel.add(new ZametkaCategoryLabel("category_label", zametkaId));
        panel.add(new DataView<CategoryId>("category_option", provider) {
            @Override
            protected void populateItem(Item<CategoryId> item) {
                CategoryId categoryId = item.getModelObject();
                Category cat = Context.getCategoryDbi().getById(categoryId);
                if (cat == null) {
                    item.setVisible(false);
                    return;
                }
                AjaxLink link = new AjaxLink<Void>("category_link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        Zametka z = Context.getZametkaDbi().getById(zametkaId);
                        if (z == null) {
                            return;
                        }
                        z.categoryId = categoryId;
                        Context.getZametkaDbi().update(z);
                        send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, zametkaId, ZametkaUpdateType.CATEGORY_CHANGED));
                    }
                };
                item.add(link);

                link.add(new Label("category_name", cat.title));
            }
        });
    }

    @OnPayload(UserCategoriesUpdatedEvent.class)
    public void onCategoriesUpdate(@NotNull UserCategoriesUpdatedEvent e) {
        if (e.userId.equals(userId)) {
            assert provider != null;
            e.target.add(panel);
        }
    }
}
