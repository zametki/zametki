package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.Context;
import com.github.zametki.db.cache.CategoryCache;
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

    @NotNull
    private CategoryCache cc() {
        return Context.getCategoryCache();
    }

    @Override
    public void create(@NotNull Category c) {
        c.id = sql.insert(c);
        cc().remove(c.userId);
        cc().update(c);
    }

    @NotNull
    @Override
    public List<CategoryId> getByUser(@Nullable UserId userId) {
        if (userId == null || !userId.isValid()) {
            return Collections.emptyList();
        }
        return cc().getByUser(userId, sql::getByUser);
    }

    @Nullable
    @Override
    public Category getById(@Nullable CategoryId id) {
        if (id == null || !id.isValid()) {
            return null;
        }
        return cc().getById(id, sql::getById);
    }

}
