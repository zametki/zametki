package com.github.zametki.ajax;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.component.group.GroupTreeModel;
import com.github.zametki.component.group.GroupTreeNode;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.ZDateFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @NotNull
    public static JSONArray getNotes(@Nullable GroupId groupId) {
        List<ZametkaId> noteIds = AjaxApiUtils.getList(groupId);
        JSONArray notesArray = new JSONArray();
        for (ZametkaId id : noteIds) {
            Zametka z = Context.getZametkaDbi().getById(id);
            if (z == null) {
                continue;
            }
            notesArray.put(toJSON(z));
        }
        return notesArray;
    }

    private static List<ZametkaId> getList(@Nullable GroupId groupId) {
        List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
        if (groupId != null) {
            res = res.stream()
                    .map(id -> Context.getZametkaDbi().getById(id))
                    .filter(z -> z != null && Objects.equals(z.groupId, groupId))
                    .map(z -> z.id)
                    .collect(Collectors.toList());
        } else {
            res = new ArrayList<>(res);
        }
        Collections.reverse(res);
        return res;
    }

    private static final ZDateFormat NOTES_LIST_DF = ZDateFormat.getInstance("dd MMMM yyyy", Constants.MOSCOW_TZ);

    @NotNull
    public static JSONObject toJSON(@NotNull Zametka z) {
        JSONObject json = new JSONObject();
        json.put("id", z.id.intValue);
        json.put("group", z.groupId.intValue);
        json.put("body", z.content);
        json.put("dateText", NOTES_LIST_DF.format(z.creationDate));
        return json;
    }

}
