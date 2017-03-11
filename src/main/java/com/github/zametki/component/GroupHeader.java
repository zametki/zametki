package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.group.EditGroupPanel;
import com.github.zametki.event.GroupTreeChangeEvent;
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
    private final WebMarkupContainer menuBlock = new ContainerWithId("menu_block");

    @NotNull
    private final WebMarkupContainer angleDownIcon = new ContainerWithId("angle_down_icon");

    @NotNull
    private final IModel<GroupId> activeCategory;

    @NotNull
    private BootstrapModal editGroupModal;

    public GroupHeader(String id, @NotNull IModel<GroupId> activeCategory) {
        super(id);
        this.activeCategory = activeCategory;
        add(panel);

        WebMarkupContainer nameLink = new ContainerWithId("name_link") {
            @Override
            protected void onComponentTag(ComponentTag tag) {
                super.onComponentTag(tag);
                boolean hasCategory = activeCategory.getObject() != null;
                String cls = "group-header" + (hasCategory ? " dropdown-toggle dropdown-toggle-no-caret" : "");
                tag.put("class", cls);
                tag.setName(hasCategory ? "a" : "div");
            }
        };

        panel.add(nameLink);
        Label nameLabel = new Label("name", LambdaModel.of((SerializableSupplier<String>) () -> {
            GroupId id1 = activeCategory.getObject();
            if (id1 == null) {
                return "Все категории";
            }
            Group c = Context.getGroupsDbi().getById(id1);
            return c == null ? "???" : c.name;
        }));

        nameLabel.setOutputMarkupId(true);
        nameLink.add(nameLabel);
        nameLink.add(angleDownIcon);
        panel.add(menuBlock);

        editGroupModal = new BootstrapModal("edit_group_modal", "Редактирование группы",
                (ComponentFactory) markupId -> new EditGroupPanel(markupId, activeCategory.getObject(),
                        (AjaxCallback) target -> editGroupModal.hide(target)),
                BootstrapModal.BodyMode.Lazy, BootstrapModal.FooterMode.Hide);
        add(editGroupModal);
        menuBlock.add(new BootstrapLazyModalLink("edit_link", editGroupModal));

        updateMenuVisibility();
    }

    private void update(@NotNull AjaxRequestTarget target) {
        target.add(panel);
        updateMenuVisibility();
    }

    private void updateMenuVisibility() {
        boolean hasCategory = activeCategory.getObject() != null;
        menuBlock.setVisible(hasCategory);
        angleDownIcon.setVisible(hasCategory);
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        if (e.groupId.equals(activeCategory.getObject())) {
            update(e.target);
        }
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == activeCategory) {
            update(e.target);
        }
    }
}
