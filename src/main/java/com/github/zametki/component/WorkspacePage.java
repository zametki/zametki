package com.github.zametki.component;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.behavior.ajax.ActivateGroupAjaxCallback;
import com.github.zametki.behavior.ajax.CreateGroupAjaxCallback;
import com.github.zametki.behavior.ajax.RenameGroupAjaxCallback;
import com.github.zametki.behavior.ajax.MoveGroupAjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.bootstrap.BootstrapModal.BodyMode;
import com.github.zametki.component.group.GroupListPanel;
import com.github.zametki.component.group.GroupTreePanel;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.component.user.UserProfileSettingsPage;
import com.github.zametki.component.zametka.CreateZametkaPanel;
import com.github.zametki.component.zametka.ZametkaPanel;
import com.github.zametki.event.CreateZametkaFormToggleEvent;
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
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
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
    public final WorkspacePageState state = new WorkspacePageState();
    private final LentaProvider provider = new LentaProvider(state);
    private final BootstrapModal groupsModal;

    public WorkspacePage(PageParameters pp) {
        super(pp);

        UserSettings us = UserSettings.get();
        state.activeGroupModel.setObject(us.lastShownGroup);

        add(new LogoPanel("brand_logo"));
        add(new BookmarkablePageLink("logout_link", LogoutPage.class));
        add(new BookmarkablePageLink("settings_link", UserProfileSettingsPage.class));
        add(new LentaLink("lenta_link", state.activeGroupModel));
        add(new ActivateGroupAjaxCallback(state.activeGroupModel));
        add(new CreateGroupAjaxCallback(state.activeGroupModel));
        add(new MoveGroupAjaxCallback(state.activeGroupModel));
        add(new RenameGroupAjaxCallback(state.activeGroupModel));

        ComponentFactory f = markupId -> new GroupListPanel(markupId, state.activeGroupModel);
        groupsModal = new BootstrapModal("groups_modal", "Выбор группы", f, BodyMode.Lazy, BootstrapModal.FooterMode.Show);
        add(groupsModal);
        add(new BootstrapLazyModalLink("groups_popup_link", groupsModal));

        add(new GroupTreePanel("groups", state.activeGroupModel));

        CreateZametkaPanel createPanel = new CreateZametkaPanel("create_panel", state.activeGroupModel);
        add(createPanel);
        add(lenta);

        add(new AddZametkaLink("add_zametka_link", createPanel));

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

            GroupId activeGroupId = state.activeGroupModel.getObject();
            UserSettings us = UserSettings.get();
            us.lastShownGroup = activeGroupId;
            UserSettings.set(us);

            e.target.appendJavaScript("$site.Server2Client.dispatchActivateGroupNodeAction(" + (activeGroupId == null ? null : activeGroupId.intValue) + ")");
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
            tag.put("class", activeGroupModel.getObject() == null ? "btn btn-sm active-all" : "btn btn-sm");
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

    public static class AddZametkaLink extends AjaxLink<Void> {
        private final CreateZametkaPanel createPanel;

        public AddZametkaLink(String id, CreateZametkaPanel createPanel) {
            super(id);
            this.createPanel = createPanel;
        }

        @Override
        public void onClick(AjaxRequestTarget target) {
            createPanel.toggle(target);
        }

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("class", createPanel.isActive() ? "btn btn-sm active-create" : "btn btn-sm");
        }

        @OnPayload(CreateZametkaFormToggleEvent.class)
        public void onCreateZametkaFormToggleEvent(CreateZametkaFormToggleEvent e) {
            e.target.add(this);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$site.Shortcuts.bindWorkspacePageKeys()"));
    }
}
