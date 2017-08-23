package com.github.zametki.ajax;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Context;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AjaxApiUtils {
    public static final Group ROOT_GROUP = new Group();

    @NotNull
    public static JSONArray getGroups(@NotNull UserId userId) {
        GroupTreeModel treeModel = GroupTreeModel.build(userId);
        JSONArray groups = new JSONArray();
        treeModel.flatList().forEach(n -> groups.put(toJSON(n)));
        return groups;
    }

    @Nullable
    private static JSONObject toJSON(@NotNull GroupTreeNode node) {
        JSONObject json = new JSONObject();
        GroupId groupId = node.getGroupId();
        Group g = groupId.isRoot() ? ROOT_GROUP : Context.getGroupsDbi().getById(groupId);
        if (g == null) {
            return null;
        }
        json.put("id", g.id == null ? 0 : g.id.intValue);
        json.put("name", g.name);
        json.put("parentId", g.parentId.intValue);
        json.put("level", node.getLevel());
        json.put("entriesCount", Context.getZametkaDbi().countByGroup(g.userId, groupId));
        JSONArray children = new JSONArray();
        for (int i = 0, n = node.getChildCount(); i < n; i++) {
            GroupTreeNode childNode = (GroupTreeNode) node.getChildAt(i);
            children.put(childNode.getGroupId().intValue);
        }
        json.put("children", children);
        return json;
    }

    @NotNull
    public static String getGroupsListAsResponse(@NotNull UserId userId) {
        JSONObject response = new JSONObject();
        response.put("groups", getGroups(userId));
        return response.toString();
    }
}
