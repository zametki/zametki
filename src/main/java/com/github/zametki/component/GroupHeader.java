package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.group.EditGroupPanel;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.jetbrains.annotations.NotNull;

public class GroupHeader extends Panel {

    @NotNull
    private final WebMarkupContainer panel = new ContainerWithId("panel");

    @NotNull
    private final WebMarkupContainer angleDownIcon = new ContainerWithId("angle_down_icon");

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    @NotNull
    private BootstrapModal editGroupModal;

    public GroupHeader(String id, @NotNull IModel<GroupId> activeGroupModel) {
        super(id);
        this.activeGroupModel = activeGroupModel;

        editGroupModal = new BootstrapModal("edit_group_modal", "Редактирование группы", (ComponentFactory) markupId -> {
            Group group = Context.getGroupsDbi().getById(activeGroupModel.getObject());
            if (group == null) {
                return new Label(markupId, "Нет выбранной группы");
            }
            return new EditGroupPanel(markupId, group, (AjaxCallback) target -> editGroupModal.hide(target));
        },
                BootstrapModal.BodyMode.Lazy, BootstrapModal.FooterMode.Hide);
        add(editGroupModal);

        add(panel);
        BootstrapLazyModalLink nameLink = new BootstrapLazyModalLink("name_link", editGroupModal) {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                boolean hasGroup = activeGroupModel.getObject() != null;
                tag.setName(hasGroup ? "a" : "div");
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (activeGroupModel.getObject() != null) {
                    super.onClick(target);
                }
            }
        };
        panel.add(nameLink);

        nameLink.add(new Label("name", LambdaModel.of((SerializableSupplier<String>) () -> {
            GroupId groupId = activeGroupModel.getObject();
            if (groupId == null) {
                return "Все заметки";
            }
            Group c = Context.getGroupsDbi().getById(groupId);
            return c == null ? "???" : c.name;
        })));
        nameLink.add(angleDownIcon);

        updateMenuVisibility();
    }

    private void update(@NotNull AjaxRequestTarget target) {
        target.add(panel);
        updateMenuVisibility();
    }

    private void updateMenuVisibility() {
        boolean hasGroup = activeGroupModel.getObject() != null;
        angleDownIcon.setVisible(hasGroup);
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        if (e.groupId.equals(activeGroupModel.getObject())) {
            update(e.target);
        }
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == activeGroupModel) {
            update(e.target);
        }
    }
}
