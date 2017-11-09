package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupTreeModel extends DefaultTreeModel {
    private static final Logger log = LoggerFactory.getLogger(GroupTreeModel.class);

    @NotNull
    public final Map<GroupId, GroupTreeNode> nodeByGroup;

    public GroupTreeModel(@NotNull TreeNode root, @NotNull Map<GroupId, GroupTreeNode> nodeByGroup) {
        super(root);
        this.nodeByGroup = nodeByGroup;
    }

    @NotNull
    public static GroupTreeModel build(@NotNull UserId userId) {
        List<Group> groups = Context.getGroupsDbi().getByUser(userId).stream()
                .map(groupId -> Context.getGroupsDbi().getById(groupId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<GroupId, GroupTreeNode> nodeById = new HashMap<>();
        GroupTreeNode rootNode = nodeById.computeIfAbsent(GroupId.Companion.getROOT(), GroupTreeNode::new);
        for (Group g : groups) {
            GroupTreeNode n = nodeById.computeIfAbsent(g.getId(), i -> new GroupTreeNode(g.getId()));
            GroupTreeNode parentNode = nodeById.computeIfAbsent(g.getParentId(), u -> new GroupTreeNode(g.getParentId()));
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

    public boolean canBeParent(@NotNull GroupId parentId, @NotNull GroupId groupId) {
        GroupTreeNode parentNode = nodeByGroup.get(parentId);
        GroupTreeNode node = nodeByGroup.get(groupId);
        return !(node == null || parentNode == null || node == parentNode) && !isParent(parentNode, node);
    }

    @NotNull
    public List<GroupTreeNode> flatList() {
        GroupTreeNode root = (GroupTreeNode) getRoot();
        Enumeration e = root.depthFirstEnumeration();

        List<GroupTreeNode> res = new ArrayList<>();
        while (e.hasMoreElements()) {
            GroupTreeNode n = (GroupTreeNode) e.nextElement();
            if (n.getParent() != null) { // root group is artificial
                res.add(n);
            }
        }
        Collections.reverse(res);
        return res;
    }

    @NotNull
    public List<GroupTreeNode> flatListWithoutBranch(@NotNull GroupTreeNode node) {
        GroupTreeNode root = (GroupTreeNode) getRoot();
        Enumeration e = root.depthFirstEnumeration();

        List<GroupTreeNode> res = new ArrayList<>();
        while (e.hasMoreElements()) {
            GroupTreeNode n = (GroupTreeNode) e.nextElement();
            if (n != node && !isParent(n, node) && n.getParent() != null) {
                res.add(n);
            }
        }
        Collections.reverse(res);
        return res;
    }
}
