package com.github.zametki.util;

import com.github.zametki.Context;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupUtils {

    @NotNull
    public static GroupId getDefaultUserGroup(@NotNull UserId userId) {
        List<GroupId> groups = Context.getGroupsDbi().getByUser(userId);
        return groups.stream().sorted().findFirst().orElseThrow(() -> new IllegalStateException("User has no groups: " + userId));
    }
}
