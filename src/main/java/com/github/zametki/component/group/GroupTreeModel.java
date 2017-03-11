package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupTreeModel extends DefaultTreeModel {
    private static final Logger log = LoggerFactory.getLogger(GroupTreeModel.class);

    @NotNull
    public Map<GroupId, GroupTreeNode> nodeByGroup;

    public GroupTreeModel(@NotNull TreeNode root, @NotNull Map<GroupId, GroupTreeNode> nodeByGroup) {
        super(root);
        this.nodeByGroup = nodeByGroup;
    }

    @NotNull
    public static GroupTreeModel build(@NotNull User user) {
        List<Group> groups = Context.getGroupsDbi().getByUser(user.id).stream()
                .map(groupId -> Context.getGroupsDbi().getById(groupId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<GroupId, GroupTreeNode> nodeById = new HashMap<>();
        GroupTreeNode rootNode = nodeById.computeIfAbsent(user.rootGroupId, GroupTreeNode::new);
        for (Group g : groups) {
            if (g.id.equals(user.rootGroupId)) {
                continue;
            }
            GroupTreeNode n = nodeById.computeIfAbsent(g.id, i -> new GroupTreeNode(g.id));
            GroupTreeNode parentNode = nodeById.computeIfAbsent(g.parentId, u -> new GroupTreeNode(g.parentId));
            if (isParent(parentNode, n)) {
                rootNode.add(n);
                log.error("Loop found: {}", g);
            } else {
                parentNode.add(n);
            }
        }
        // fix detached nodes
        for (GroupTreeNode n : nodeById.values()) {
            if (n == rootNode) {
                continue;
            }
            GroupTreeNode p = (GroupTreeNode) n.getParent();
            if (p == null) {
                log.error("Not connected to root: {}", n);
                rootNode.add(n);
            }
        }
        return new GroupTreeModel(rootNode, nodeById);
    }

    private static boolean isParent(@NotNull TreeNode node, @NotNull TreeNode parentNode) {
        TreeNode p = node.getParent();
        return p == parentNode || p != null && isParent(p, parentNode);
    }


    public void onGroupTreeChanged(@NotNull GroupTreeChangeEvent e) {
        switch (e.changeType) {
            case Created:
                onGroupCreated(e.groupId);
                break;
            case ParentChanged:
                //todo:
                break;
            case Deleted:
                //todo:
                break;
        }
    }

    private void onGroupCreated(@NotNull GroupId groupId) {
        Group newGroup = Context.getGroupsDbi().getById(groupId);
        if (newGroup == null) {
            return;
        }
        GroupTreeNode newNode = new GroupTreeNode(groupId);
        nodeByGroup.put(groupId, newNode);
        GroupTreeNode parentGroup = nodeByGroup.get(newGroup.parentId);
        parentGroup.add(newNode);
    }

}
