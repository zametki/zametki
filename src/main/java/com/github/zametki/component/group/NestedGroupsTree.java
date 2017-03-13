package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.util.TreeModelProvider;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

public class NestedGroupsTree extends NestedTree<GroupTreeNode> {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public NestedGroupsTree(@NotNull String id, @NotNull TreeModelProvider<GroupTreeNode> provider, @NotNull IModel<GroupId> activeGroupModel) {
        super(id, provider);
        this.activeGroupModel = activeGroupModel;
    }

    @Override
    public Component newNodeComponent(String id, IModel<GroupTreeNode> model) {
        return new GroupTreeNodePanel(id, this, model, activeGroupModel);
    }

    @Override
    protected Component newContentComponent(String contentId, IModel<GroupTreeNode> m) {
        return new GroupTreeItemContentPanel(contentId, m, activeGroupModel);
    }
}
