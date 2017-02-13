package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.db.dbi.AbstractDbi;
import com.github.zametki.db.dbi.CategoryDbi;
import com.github.zametki.db.sql.CategorySql;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CategoryDbiImpl extends AbstractDbi implements CategoryDbi {

    private final CategorySql sql;

    public CategoryDbiImpl(@NotNull Db db) {
        super(db);
        sql = db.attachSql(CategorySql.class);
    }

    @Override
    public void create(@NotNull Category c) {
        c.id = sql.insert(c);
    }

    @NotNull
    @Override
    public List<CategoryId> getByUser(@Nullable UserId userId) {
        return userId == null || userId.equals(UserId.INVALID_ID) ? Collections.emptyList() : sql.getByUser(userId);
    }

    @Nullable
    @Override
    public Category getById(@Nullable CategoryId id) {
        return id == null || id.equals(CategoryId.INVALID_ID) ? null : sql.getById(id);
    }

}
