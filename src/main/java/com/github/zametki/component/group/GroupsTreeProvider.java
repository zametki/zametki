package com.github.zametki.component.group;

import org.apache.wicket.extensions.markup.html.repeater.util.TreeModelProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

public class GroupsTreeProvider extends TreeModelProvider<GroupTreeNode> {
    @NotNull
    public final GroupTreeModel treeModel;

    public GroupsTreeProvider(@NotNull GroupTreeModel treeModel) {
        super(treeModel, false);
        this.treeModel = treeModel;
    }

    @Override
    public IModel<GroupTreeNode> model(GroupTreeNode node) {
        return Model.of(node);
    }
}
