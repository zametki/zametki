package com.github.zametki.db.sql;

import com.github.mjdbc.Bind;
import com.github.mjdbc.BindBean;
import com.github.mjdbc.Sql;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Set of 'group' table queries
 */
public interface GroupSql {

    @NotNull
    @Sql("INSERT INTO groups (parent_id, user_id, name) VALUES (:parentId, :userId, :name)")
    GroupId insert(@BindBean Group group);

    @Nullable
    @Sql("SELECT * FROM groups WHERE id = :id")
    Group getById(@Bind("id") GroupId id);

    @NotNull
    @Sql("SELECT id FROM groups WHERE user_id = :userId ORDER BY name")
    List<GroupId> getByUser(@Bind("userId") UserId userId);

    @Sql("DELETE FROM groups WHERE id = :id")
    void delete(@Bind("id") GroupId id);

    @Sql("UPDATE groups SET parent_id = :parentId, name = :name WHERE id = :id")
    void update(@NotNull @BindBean Group c);

    @Sql("SELECT id FROM groups WHERE parent_id = :id")
    List<GroupId> getSubgroups(@NotNull @Bind("id") GroupId id);
}
