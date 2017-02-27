package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapModal.BodyMode;
import com.github.zametki.component.category.CategoriesListPanel;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.component.z.CreateZametkaButtonPanel;
import com.github.zametki.component.z.ZametkaPanel;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    private final BootstrapModal categoriesModal;

    public LentaPage() {

        add(new LogoPanel("brand_logo"));
        add(new BookmarkablePageLink("logout_link", LogoutPage.class));

        ComponentFactory f = markupId -> new CategoriesListPanel(markupId, state.activeCategory);
        categoriesModal = new BootstrapModal("categories_modal", "Выбор категории", f, BodyMode.Lazy, BootstrapModal.FooterMode.Show);
        add(categoriesModal);
        add(new BootstrapLazyModalLink("categories_popup_link", categoriesModal));

        add(new CategoriesListPanel("categories", state.activeCategory));
        add(new CreateZametkaButtonPanel("create_panel", state.activeCategory));
        add(lenta);

        lenta.add(new CategoryHeader("category_name", state.activeCategory));

        //todo: move to separate component
        lenta.add(new DataView<ZametkaId>("zametka", provider) {
            @Override
            protected void populateItem(Item<ZametkaId> item) {
                ZametkaId id = item.getModelObject();
                ZametkaPanel.Settings settings = new ZametkaPanel.Settings();
                settings.showCategory = state.activeCategory.getObject() == null;
                item.add(new ZametkaPanel("z", id, settings));
            }
        });
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.CONTENT_CHANGED) {
            return;
        }
        provider.detach();
        e.target.add(lenta);
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == state.activeCategory) {
            provider.detach();
            e.target.add(lenta);
            categoriesModal.hide(e.target);
        }
    }

    private class LentaProvider extends AbstractListProvider<ZametkaId> {
        @Override
        public List<ZametkaId> getList() {
            List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
            CategoryId activeCategory = state.activeCategory.getObject();
            if (activeCategory != null) {
                res = res.stream()
                        .map(id -> Context.getZametkaDbi().getById(id))
                        .filter(z -> z != null && Objects.equals(z.categoryId, activeCategory))
                        .map(z -> z.id)
                        .collect(Collectors.toList());
            } else {
                res = new ArrayList<>(res);
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
