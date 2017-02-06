package com.github.zametki.db.dbi;

import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZametkiDbi {

    void create(@NotNull Zametka z);

    List<ZametkaId> getByUser(@Nullable UserId userId);

    @Nullable
    Zametka getById(@Nullable ZametkaId id);
}
