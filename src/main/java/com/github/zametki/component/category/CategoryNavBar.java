package com.github.zametki.component.category;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.behavior.ClassAppender;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.CategoryId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.zametki.util.WicketUtils.reactiveUpdate;

public class CategoryNavBar extends Panel {

    private final ContainerWithId panel = new ContainerWithId("panel");

    private final CategoriesProvider provider = new CategoriesProvider();

    @NotNull
    private final IModel<CategoryId> categoryModel;

    public CategoryNavBar(String id, @NotNull IModel<CategoryId> categoryModel) {
        super(id);
        this.categoryModel = categoryModel;

        add(panel);

        panel.add(new DataView<NavbarOption>("cat", provider) {
            @Override
            protected void populateItem(Item<NavbarOption> item) {
                NavbarOption o = item.getModelObject();
                AjaxLink<Void> link = new AjaxLink<Void>("link") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        reactiveUpdate(categoryModel, o.categoryId, target);
                    }
                };
                if (Objects.equals(o.categoryId, categoryModel.getObject())) {
                    link.add(new ClassAppender("active"));
                }
                item.add(link);
                link.add(new Label("title", o.title));
            }
        });
    }

    @OnPayload(UserCategoriesUpdatedEvent.class)
    public void onCategoriesUpdated(UserCategoriesUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    private static class NavbarOption implements IClusterable {
        @Nullable
        private final CategoryId categoryId;
        @NotNull
        private final String title;

        public NavbarOption(@Nullable CategoryId categoryId, @NotNull String title) {
            this.categoryId = categoryId;
            this.title = title;
        }
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == categoryModel) {
            provider.detach();
            e.target.add(panel);
        }
    }

    @OnPayload(UserCategoriesUpdatedEvent.class)
    public void onUserCategoriesUpdatedEvent(UserCategoriesUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    private static class CategoriesProvider extends AbstractListProvider<NavbarOption> {

        @Override
        public List<NavbarOption> getList() {
            List<NavbarOption> list = new ArrayList<>();
            list.add(new NavbarOption(null, "Все"));
            List<CategoryId> userCategories = Context.getCategoryDbi().getByUser(UserSession.get().getUserId());
            userCategories.stream()
                    .map(id -> Context.getCategoryDbi().getById(id))
                    .filter(Objects::nonNull)
                    .forEach(c -> list.add(new NavbarOption(c.id, c.title)));
            return list;
        }

        @Override
        public IModel<NavbarOption> model(NavbarOption o) {
            return Model.of(o);
        }
    }
}
