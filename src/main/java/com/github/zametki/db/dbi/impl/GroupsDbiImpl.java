package com.github.zametki.db.dbi.impl;

import com.github.mjdbc.Db;
import com.github.zametki.Context;
import com.github.zametki.db.cache.GroupsCache;
import com.github.zametki.db.dbi.AbstractDbi;
import com.github.zametki.db.dbi.GroupsDbi;
import com.github.zametki.db.sql.GroupSql;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GroupsDbiImpl extends AbstractDbi implements GroupsDbi {

    private final GroupSql sql;

    public GroupsDbiImpl(@NotNull Db db) {
        super(db);
        sql = db.attachSql(GroupSql.class);
    }

    @NotNull
    private GroupsCache cc() {
        return Context.getGroupsCache();
    }

    @Override
    public void create(@NotNull Group c) {
        c.id = sql.insert(c);
        cc().remove(c.userId);
        cc().update(c);
    }

    @NotNull
    @Override
    public List<GroupId> getByUser(@Nullable UserId userId) {
        if (isInvalid(userId)) {
            return Collections.emptyList();
        }
        return cc().getByUser(userId, sql::getByUser);
    }

    @Nullable
    @Override
    public Group getById(@Nullable GroupId id) {
        if (isInvalid(id)) {
            return null;
        }
        return cc().getById(id, sql::getById);
    }

    @Override
    public void update(@NotNull Group g) {
        sql.update(g);
        cc().update(g);
    }

    @Nullable
    @Override
    public Group getByName(@NotNull UserId userId, @NotNull String name) {
        List<GroupId> groupIds = getByUser(userId);
        for (GroupId id : groupIds) {
            Group g = getById(id);
            if (g != null && g.name.equals(name)) {
                return g;
            }
        }
        return null;
    }

}
