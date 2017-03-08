package com.github.zametki.db.dbi;

import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ZametkaDbi {

    void create(@NotNull Zametka zametka);

    /**
     * Список заметок сортированный по дате.
     */
    @NotNull
    List<ZametkaId> getByUser(@Nullable UserId userId);

    @Nullable
    Zametka getById(@Nullable ZametkaId id);

    void delete(@NotNull ZametkaId zametkaId);

    void update(@NotNull Zametka z);

    int countByCategory(UserId userId, @NotNull GroupId groupId);
}
