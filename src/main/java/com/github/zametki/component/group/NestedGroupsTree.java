package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.util.TreeModelProvider;
import org.apache.wicket.model.IModel;

public class NestedGroupsTree extends NestedTree<GroupTreeNode> {
    private final IModel<GroupId> activeGroupModel;

    public NestedGroupsTree(TreeModelProvider<GroupTreeNode> provider, IModel<GroupId> activeGroupModel) {
        super("tree", provider);
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
