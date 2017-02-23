package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.category.CategoryNavBar;
import com.github.zametki.component.form.CreateZametkaForm;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.component.z.ZametkaPanel;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Страница со списком персональных заметок.
 */
@MountPath("/lenta")
public class LentaPage extends BaseUserPage {

    private final WebMarkupContainer lenta = new ContainerWithId("lenta");
    private final LentaProvider provider = new LentaProvider();

    public final LentaPageState state = new LentaPageState();

    public LentaPage() {

        add(new CategoryNavBar("categories", state.activeCategory));
        add(new CreateZametkaForm("create_form", state.activeCategory));
        add(lenta);

        //todo: move to separate component
        lenta.add(new DataView<ZametkaId>("zametka", provider) {
            @Override
            protected void populateItem(Item<ZametkaId> item) {
                ZametkaId id = item.getModelObject();
                item.add(new ZametkaPanel("z", id));
            }
        });
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        provider.detach();
        e.target.add(lenta);
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == state.activeCategory) {
            provider.detach();
            e.target.add(lenta);
        }
    }

    private class LentaProvider extends AbstractListProvider<ZametkaId> {
        @Override
        public List<ZametkaId> getList() {
            List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
            CategoryId selectedId = state.activeCategory.getObject();
            if (selectedId != null) {
                res = res.stream()
                        .map(id -> Context.getZametkaDbi().getById(id))
                        .filter(z -> z != null && Objects.equals(z.categoryId, selectedId))
                        .map(z -> z.id)
                        .collect(Collectors.toList());
            }
            Collections.reverse(res);
            return res;
        }

        @Override
        public IModel<ZametkaId> model(ZametkaId z) {
            return Model.of(z);
        }
    }
}
