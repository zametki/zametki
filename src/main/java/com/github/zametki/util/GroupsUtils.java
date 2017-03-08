package com.github.zametki.util;

import com.github.zametki.Context;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;

public class GroupsUtils {

    /**
     * TODO: select real default.
     */
    @NotNull
    public static GroupId getDefaultGroupForUser(@NotNull UserId userId) {
        return Context.getGroupsDbi().getByUser(userId).stream()
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No group found: " + userId));
    }
}
