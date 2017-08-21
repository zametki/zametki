package com.github.zametki.component;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.behavior.ajax.CreateGroupAjaxCallback;
import com.github.zametki.behavior.ajax.DeleteGroupAjaxCallback;
import com.github.zametki.behavior.ajax.MoveGroupAjaxCallback;
import com.github.zametki.behavior.ajax.RenameGroupAjaxCallback;
import com.github.zametki.component.group.GroupTreePanel;
import com.github.zametki.component.group.NotesViewPanel;
import com.github.zametki.component.user.BaseUserPage;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserSettings;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;

@MountPath("/my")
public class WorkspacePage extends BaseUserPage {

    public final WorkspacePageState state = new WorkspacePageState();

    public WorkspacePage(PageParameters pp) {
        super(pp);

//        UserSettings us = UserSettings.get();
//        state.activeGroupModel.setObject(us.lastShownGroup);
//
//        add(new LogoPanel("brand_logo"));
//        add(new BookmarkablePageLink("logout_link", LogoutPage.class));
//        add(new BookmarkablePageLink("settings_link", UserProfileSettingsPage.class));
//        add(new LentaLink("lenta_link", state.activeGroupModel));
//
        add(new CreateGroupAjaxCallback(state.activeGroupModel));
        add(new MoveGroupAjaxCallback(state.activeGroupModel));
        add(new RenameGroupAjaxCallback(state.activeGroupModel));
        add(new DeleteGroupAjaxCallback(state.activeGroupModel));

//        ComponentFactory f = markupId -> new GroupListPanel(markupId, state.activeGroupModel);
//        groupsModal = new BootstrapModal("groups_modal", "Выбор группы", f, BodyMode.Lazy, BootstrapModal.FooterMode.Show);
//        add(groupsModal);
//        add(new BootstrapLazyModalLink("groups_popup_link", groupsModal));

        add(new GroupTreePanel("groups", state.activeGroupModel));
        add(new NotesViewPanel("notes", state));
        add(new NavbarPanel("navbar"));

/*        CreateZametkaPanel createPanel = new CreateZametkaPanel("create_panel", state.activeGroupModel);
        add(createPanel);
        add(lenta);

//        add(new AddZametkaLink("add_zametka_link", createPanel));

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
        });*/
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == state.activeGroupModel) {
//            groupsModal.hide(e.target);

            GroupId activeGroupId = state.activeGroupModel.getObject();
            UserSettings us = UserSettings.get();
            us.lastShownGroup = activeGroupId;
            UserSettings.set(us);

            e.target.appendJavaScript("$site.Server2Client.dispatchActivateGroupNodeAction(" + (activeGroupId == null ? null : activeGroupId.intValue) + ")");
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(OnDomReadyHeaderItem.forScript("$site.Shortcuts.bindWorkspacePageKeys()"));
    }
}
