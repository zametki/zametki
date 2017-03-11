package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.event.UserGroupUpdatedEvent;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.User;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.extensions.markup.html.repeater.util.TreeModelProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupTreePanel extends Panel {
    private static final Logger log = LoggerFactory.getLogger(GroupTreePanel.class);

    private final ContainerWithId panel = new ContainerWithId("panel");

    private final GroupsProvider provider;

    @NotNull
    private final IModel<GroupId> groupModel;

    public GroupTreePanel(String id, @NotNull IModel<GroupId> groupModel) {
        super(id);
        this.groupModel = groupModel;

        add(panel);

        User user = WebUtils.getUserOrRedirectHome();

        List<Group> groups = Context.getGroupsDbi().getByUser(user.id).stream()
                .map(groupId -> Context.getGroupsDbi().getById(groupId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        TreeModel model = buildTreeModel(user.rootGroupId, groups);
        provider = new GroupsProvider(model);

        NestedTree<DefaultMutableTreeNode> tree = new NestedTree<DefaultMutableTreeNode>("tree", provider) {

            @Override
            protected Component newContentComponent(String id, IModel<DefaultMutableTreeNode> model) {
//                GroupId groupId = (GroupId) model.getObject().getUserObject();
                return new Folder<>(id, this, model);
//                return new GroupTreeItem(id, groupId, groupModel);
            }
        };
        tree.expand((DefaultMutableTreeNode) model.getRoot());
        panel.add(tree);
    }

    @OnPayload(UserGroupUpdatedEvent.class)
    public void onCategoriesUpdated(UserGroupUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    @OnModelUpdate
    public void onModelUpdateAjaxEvent(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == groupModel) {
            provider.detach();
            e.target.add(panel);
        }
    }

    @OnPayload(UserGroupUpdatedEvent.class)
    public void onUserCategoriesUpdatedEvent(UserGroupUpdatedEvent e) {
        provider.detach();
        e.target.add(panel);
    }

    @NotNull
    public static DefaultTreeModel buildTreeModel(@NotNull GroupId rootGroupId, List<Group> groups) {
        Map<GroupId, DefaultMutableTreeNode> nodeById = new HashMap<>();
        DefaultMutableTreeNode rootNode = nodeById.computeIfAbsent(rootGroupId, DefaultMutableTreeNode::new);
        for (Group g : groups) {
            if (g.id.equals(rootGroupId)) {
                continue;
            }
            DefaultMutableTreeNode n = nodeById.computeIfAbsent(g.id, i -> new DefaultMutableTreeNode(g.id));
            DefaultMutableTreeNode parentNode = nodeById.computeIfAbsent(g.parentId, u -> new DefaultMutableTreeNode(g.parentId));
            if (isParent(parentNode, n)) {
                rootNode.add(n);
                log.error("Loop found: {}", g);
            } else {
                parentNode.add(n);
            }
        }
        // fix detached nodes
        for (DefaultMutableTreeNode n : nodeById.values()) {
            DefaultMutableTreeNode p = getParent(n);
            if (p != rootNode) {
                log.error("Not connected to root: {}", n);
                rootNode.add(p);
            }
        }
        return new DefaultTreeModel(rootNode);
    }

    private static DefaultMutableTreeNode getParent(DefaultMutableTreeNode n) {
        DefaultMutableTreeNode p = (DefaultMutableTreeNode) n.getParent();
        return p == null ? n : p;
    }

    private static boolean isParent(@NotNull TreeNode node, @NotNull TreeNode parentNode) {
        TreeNode p = node.getParent();
        return p == parentNode || p != null && isParent(p, parentNode);
    }

    private static class GroupsProvider extends TreeModelProvider<DefaultMutableTreeNode> {
        public GroupsProvider(@NotNull TreeModel treeModel) {
            super(treeModel);
        }

        @Override
        public IModel<DefaultMutableTreeNode> model(DefaultMutableTreeNode node) {
            return Model.of(node);
        }
    }
}
