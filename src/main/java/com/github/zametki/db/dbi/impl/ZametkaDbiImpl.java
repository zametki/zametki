package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
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

    @Override
    public void create(@NotNull Zametka z) {
        z.id = sql.insert(z);
    }

    @NotNull
    @Override
    public List<ZametkaId> getByUser(@Nullable UserId userId) {
        return userId == null || userId.equals(UserId.INVALID_ID) ? Collections.emptyList() : sql.getByUser(userId);
    }

    @Nullable
    @Override
    public Zametka getById(@Nullable ZametkaId id) {
        return id == null ? null : sql.getById(id);
    }

    @Override
    public void delete(@NotNull ZametkaId zametkaId) {
        sql.delete(zametkaId);
    }

}
