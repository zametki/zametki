package com.github.zametki.db.cache;

import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class GroupsCache {

    private final Map<GroupId, Group> groupById = new ConcurrentHashMap<>();

    private final Map<UserId, List<GroupId>> groupsByUser = new ConcurrentHashMap<>();

    public Group getById(@NotNull GroupId groupId, @NotNull Function<GroupId, Group> loader) {
        return groupById.computeIfAbsent(groupId, loader);
    }

    public void update(@NotNull Group group) {
        groupById.put(group.id, group);
    }

    public void remove(@NotNull GroupId groupId) {
        groupById.remove(groupId);
    }

    public List<GroupId> getByUser(@NotNull UserId userId, @NotNull Function<UserId, List<GroupId>> loader) {
        return groupsByUser.computeIfAbsent(userId, id -> Collections.unmodifiableList(loader.apply(id)));
    }

    public void remove(@NotNull UserId userId) {
        groupsByUser.remove(userId);
    }
}
