package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.category.CategoryNavBar;
import com.github.zametki.component.form.CreateZametkaForm;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.component.z.ZametkaPanel;
import com.github.zametki.event.LentaPageSelectedCategoryChanged;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Collections;
import java.util.List;
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

        add(new CategoryNavBar("categories", this));
        add(new CreateZametkaForm("create_form"));
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

    @OnPayload(LentaPageSelectedCategoryChanged.class)
    public void onLentaPageSelectedCategoryChanged(LentaPageSelectedCategoryChanged e) {
        provider.detach();
        e.target.add(lenta);
    }

    private class LentaProvider extends AbstractListProvider<ZametkaId> {
        @Override
        public List<ZametkaId> getList() {
            List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
            if (state.selectedCategoryId != null) {
                res = res.stream()
                        .map(id -> Context.getZametkaDbi().getById(id))
                        .filter(z -> z != null && z.categoryId == state.selectedCategoryId)
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
