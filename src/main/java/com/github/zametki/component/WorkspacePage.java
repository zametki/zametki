package com.github.zametki.component;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapModal.BodyMode;
import com.github.zametki.component.group.GroupListPanel;
import com.github.zametki.component.group.GroupTreePanel;
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
import com.github.zametki.provider.LentaProvider;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;

@MountPath("/my")
public class WorkspacePage extends BaseUserPage {

    private final WebMarkupContainer lenta = new ContainerWithId("lenta");
    public final LentaPageState state = new LentaPageState();
    private final LentaProvider provider = new LentaProvider(state);
    private final BootstrapModal groupsModal;

    public WorkspacePage(PageParameters pp) {
        super(pp);

        UserSettings us = UserSettings.get();
        state.activeGroupModel.setObject(us.lastShownGroup);

        add(new LogoPanel("brand_logo"));
        add(new BookmarkablePageLink("logout_link", LogoutPage.class));
        add(new LentaLink("lenta_link", state.activeGroupModel));

        ComponentFactory f = markupId -> new GroupListPanel(markupId, state.activeGroupModel);
        groupsModal = new BootstrapModal("groups_modal", "Выбор группы", f, BodyMode.Lazy, BootstrapModal.FooterMode.Show);
        add(groupsModal);
        add(new BootstrapLazyModalLink("groups_popup_link", groupsModal));

        add(new GroupTreePanel("groups", state.activeGroupModel));
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

    public static class LentaLink extends AjaxLink<Void> {
        private final IModel<GroupId> activeGroupModel;

        public LentaLink(@NotNull String id, IModel<GroupId> activeGroupModel) {
            super(id);
            this.activeGroupModel = activeGroupModel;
            setOutputMarkupId(true);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("class", activeGroupModel.getObject() == null ? "nav-link active" : "nav-link");
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            WicketUtils.reactiveUpdate(activeGroupModel, null, target);
        }

        @OnModelUpdate
        public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
            if (e.model == activeGroupModel) {
                e.target.add(this);
            }
        }
    }
}