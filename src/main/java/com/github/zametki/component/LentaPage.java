package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapModal.BodyMode;
import com.github.zametki.component.group.GroupsListPanel;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.component.zametka.CreateZametkaButtonPanel;
import com.github.zametki.component.zametka.ZametkaPanel;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserSettings;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
    private final BootstrapModal groupsModal;

    public LentaPage(PageParameters pp) {
        super(pp);

        UserSettings us = UserSettings.get();
        state.activeGroupModel.setObject(us.lastShownGroup);

        add(new LogoPanel("brand_logo"));
        add(new BookmarkablePageLink("logout_link", LogoutPage.class));

        ComponentFactory f = markupId -> new GroupsListPanel(markupId, state.activeGroupModel);
        groupsModal = new BootstrapModal("groups_modal", "Выбор группы", f, BodyMode.Lazy, BootstrapModal.FooterMode.Show);
        add(groupsModal);
        add(new BootstrapLazyModalLink("groups_popup_link", groupsModal));

        add(new GroupsListPanel("groups", state.activeGroupModel));
        add(new CreateZametkaButtonPanel("create_panel", state.activeGroupModel));
        add(lenta);

        lenta.add(new GroupHeader("group_name", state.activeGroupModel));

        //todo: move to separate component
        lenta.add(new DataView<ZametkaId>("zametka", provider) {
            @Override
            protected void populateItem(Item<ZametkaId> item) {
                ZametkaId id = item.getModelObject();
                ZametkaPanel.Settings settings = new ZametkaPanel.Settings();
                settings.showCategory = state.activeGroupModel.getObject() == null;
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
        if (e.model == state.activeGroupModel) {
            provider.detach();
            e.target.add(lenta);
            groupsModal.hide(e.target);

            UserSettings us = UserSettings.get();
            us.lastShownGroup = state.activeGroupModel.getObject();
            UserSettings.set(us);
        }
    }

    private class LentaProvider extends AbstractListProvider<ZametkaId> {
        @Override
        public List<ZametkaId> getList() {
            List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
            GroupId activeCategory = state.activeGroupModel.getObject();
            if (activeCategory != null) {
                res = res.stream()
                        .map(id -> Context.getZametkaDbi().getById(id))
                        .filter(z -> z != null && Objects.equals(z.groupId, activeCategory))
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
