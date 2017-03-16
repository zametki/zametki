package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserSettings;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

public class NestedGroupsTree extends NestedTree<GroupTreeNode> {

    @NotNull
    private final IModel<GroupId> activeGroupModel;

    public NestedGroupsTree(@NotNull String id, @NotNull GroupsTreeProvider provider, @NotNull IModel<GroupId> activeGroupModel) {
        super(id, provider);
        this.activeGroupModel = activeGroupModel;

        JSONArray expanded = UserSettings.get().expandedGroups;
        GroupTreeModel model = getTreeModel();
        for (int i = 0, n = expanded.length(); i < n; i++) {
            GroupId groupId = new GroupId(expanded.getInt(i));
            GroupTreeNode node = model.nodeByGroup.get(groupId);
            if (node != null) {
                super.expand(node);
            }
        }
        GroupId selectedGroupId = activeGroupModel.getObject();
        if (selectedGroupId != null) {
            GroupTreeNode selectedNode = model.nodeByGroup.get(selectedGroupId);
            if (selectedNode != null) {
                for (GroupTreeNode n = selectedNode.getParentNode(); n != null; n = n.getParentNode()) {
                    super.expand(n);
                }
            }
        }
    }

    @Override
    public Component newNodeComponent(String id, IModel<GroupTreeNode> model) {
        return new GroupTreeNodePanel(id, this, model, activeGroupModel);
    }

    @Override
    protected Component newContentComponent(String contentId, IModel<GroupTreeNode> m) {
        return new GroupTreeItemContentPanel(contentId, m, activeGroupModel);
    }

    @Override
    public void expand(GroupTreeNode node) {
        super.expand(node);
        saveTreeStateToSettings();
    }

    @Override
    public void collapse(GroupTreeNode node) {
        super.collapse(node);
        saveTreeStateToSettings();
    }

    private void saveTreeStateToSettings() {
        JSONArray expandedGroups = new JSONArray();
        getTreeModel().flatList().forEach(n -> {
            if (getState(n) == State.EXPANDED) {
                expandedGroups.put(n.getGroupId().getDbValue());
            }
        });
        UserSettings us = UserSettings.get();
        us.expandedGroups = expandedGroups;
        UserSettings.set(us);

    }

    @NotNull
    private GroupTreeModel getTreeModel() {
        return ((GroupsTreeProvider) getProvider()).treeModel;
    }
}
