package com.github.zametki.db.dbi;

import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GroupsDbi {

    void create(@NotNull Group group);

    @NotNull
    List<GroupId> getByUser(@Nullable UserId userId);

    @Nullable
    Group getById(@Nullable GroupId id);

    void update(@NotNull Group c);

    @Nullable
    Group getByName(@NotNull UserId userId, @NotNull String name);
}
