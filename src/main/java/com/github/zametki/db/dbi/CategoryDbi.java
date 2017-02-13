package com.github.zametki.db.dbi;

import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CategoryDbi {

    void create(@NotNull Category category);

    @NotNull
    List<CategoryId> getByUser(@Nullable UserId userId);

    @Nullable
    Category getById(@Nullable CategoryId id);
}
