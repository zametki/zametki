package com.github.zametki.component.group;

import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.model.GroupId;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.Node;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

//TODO: do not use wicket not at all
public class GroupTreeNodePanel extends Node<GroupTreeNode> {

    @NotNull
    private final NestedGroupsTree tree;
    @NotNull
    private final IModel<GroupTreeNode> model;
    @NotNull
    private final IModel<GroupId> activeGroupModel;

    boolean active;

    public GroupTreeNodePanel(@NotNull String id, @NotNull NestedGroupsTree tree,
                              @NotNull IModel<GroupTreeNode> model, @NotNull IModel<GroupId> activeGroupModel) {
        super(id, tree, model);
        this.tree = tree;
        this.model = model;
        this.activeGroupModel = activeGroupModel;
        active = model.getObject().getGroupId().equals(activeGroupModel.getObject());
        setOutputMarkupId(true);

        // bad design in wicket -> createContent called from super class
        get(CONTENT_ID).replaceWith(createContent(CONTENT_ID, model));
        get("junction").setDefaultModel(Model.of(""));
    }

    protected Component createContent(String contentId, IModel<GroupTreeNode> model) {
        //noinspection ConstantConditions
        if (tree == null) {
            return new WebMarkupContainer(contentId);
        }
        return tree.newContentComponent(contentId, model);
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model != activeGroupModel) {
            return;
        }
        boolean newActive = model.getObject().getGroupId().equals(activeGroupModel.getObject());
        if (active != newActive) {
            active = newActive;
            e.target.add(this);
        }
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        int level = model.getObject().getLevel();
        int paddingLeft = 16 * level;
        tag.put("style", "padding-left: " + paddingLeft + "px;");
        if (active) {
            tag.put("class", "tree-node tree-node-active");
        }
    }
}
