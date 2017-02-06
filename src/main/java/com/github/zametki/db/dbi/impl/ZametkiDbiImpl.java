package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.db.dbi.AbstractDbi;
import com.github.zametki.db.dbi.ZametkiDbi;
import com.github.zametki.db.sql.ZametkiSql;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ZametkiDbiImpl extends AbstractDbi implements ZametkiDbi {
    private final ZametkiSql sql;

    public ZametkiDbiImpl(@NotNull Db db) {
        super(db);
        sql = db.attachSql(ZametkiSql.class);
    }

    @Override
    public void create(@NotNull Zametka z) {
        z.id = sql.insert(z);
    }

    @Override
    public List<ZametkaId> getByUser(@Nullable UserId userId) {
        return userId == null ? Collections.emptyList() : sql.getByUser(userId);
    }

    @Nullable
    @Override
    public Zametka getById(@Nullable ZametkaId id) {
        return id == null ? null : sql.getById(id);
    }

}
