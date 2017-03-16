package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;

public class GroupTreeNode extends DefaultMutableTreeNode {

    public GroupTreeNode(@NotNull GroupId groupId) {
        setUserObject(groupId);
    }

    @NotNull
    public GroupId getGroupId() {
        return (GroupId) getUserObject();
    }

    @Nullable
    public GroupTreeNode getParentNode() {
        return (GroupTreeNode) getParent();
    }
}
