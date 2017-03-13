package com.github.zametki.component.group;

import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.User;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.util.TreeModelProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GroupTreePanel extends Panel {

    @NotNull
    private final ContainerWithId panel = new ContainerWithId("panel");
    @NotNull
    private final GroupsProvider provider;
    @NotNull
    private final IModel<GroupId> activeGroupModel;
    @NotNull
    private final NestedTree<GroupTreeNode> tree;
    @NotNull
    private final GroupTreeModel treeModel;

    public GroupTreePanel(String id, @NotNull IModel<GroupId> activeGroupModel) {
        super(id);
        this.activeGroupModel = activeGroupModel;
        add(panel);

        User user = WebUtils.getUserOrRedirectHome();
        treeModel = GroupTreeModel.build(user);
        provider = new GroupsProvider(treeModel);

        tree = new NestedGroupsTree("tree", provider, activeGroupModel);
        panel.add(tree);
    }

    @OnPayload(GroupTreeChangeEvent.class)
    public void onGroupTreeChanged(GroupTreeChangeEvent e) {
        provider.detach();
        treeModel.onGroupTreeChanged(e);
        e.target.add(panel);
    }

    @OnPayload(GroupUpdateEvent.class)
    public void onGroupUpdated(GroupUpdateEvent e) {
        GroupTreeNode node = treeModel.nodeByGroup.get(e.groupId);
        tree.updateNode(node, Optional.of(e.target));
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model != activeGroupModel) {
            return;
        }

        GroupTreeNode node = treeModel.nodeByGroup.get(activeGroupModel.getObject());
        if (node != null) {
            GroupTreeNode parent = (GroupTreeNode) node.getParent();
            if (parent != null) {
                tree.expand(parent);
            }
        }
        e.target.add(panel);
    }

    private static class GroupsProvider extends TreeModelProvider<GroupTreeNode> {
        public GroupsProvider(@NotNull GroupTreeModel treeModel) {
            super(treeModel, false);
        }

        @Override
        public IModel<GroupTreeNode> model(GroupTreeNode node) {
            return Model.of(node);
        }
    }

}
