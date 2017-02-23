package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.Context;
import com.github.zametki.db.cache.ZametkaCache;
import com.github.zametki.db.dbi.AbstractDbi;
import com.github.zametki.db.dbi.ZametkaDbi;
import com.github.zametki.db.sql.ZametkaSql;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ZametkaDbiImpl extends AbstractDbi implements ZametkaDbi {
    private final ZametkaSql sql;

    public ZametkaDbiImpl(@NotNull Db db) {
        super(db);
        sql = db.attachSql(ZametkaSql.class);
    }

    @NotNull
    private ZametkaCache zc() {
        return Context.getZametkaCache();
    }

    @Override
    public void create(@NotNull Zametka z) {
        z.id = sql.insert(z);
        zc().remove(z.userId);
        zc().update(z);
    }

    @NotNull
    @Override
    public List<ZametkaId> getByUser(@Nullable UserId userId) {
        if (isInvalid(userId)) {
            return Collections.emptyList();
        }
        return zc().getByUser(userId, sql::getByUser);
    }

    @Nullable
    @Override
    public Zametka getById(@Nullable ZametkaId id) {
        if (isInvalid(id)) {
            return null;
        }
        return zc().getById(id, sql::getById);
    }

    @Override
    public void delete(@NotNull ZametkaId zametkaId) {
        Zametka z = getById(zametkaId);
        if (z == null) {
            return;
        }
        sql.delete(zametkaId);
        zc().remove(zametkaId);
        zc().remove(z.userId);
    }

    @Override
    public void update(@NotNull Zametka z) {
        sql.update(z);
        zc().update(z);
    }

}
