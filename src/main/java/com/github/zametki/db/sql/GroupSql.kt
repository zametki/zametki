package com.github.zametki.db.sql

import com.github.mjdbc.Bind
import com.github.mjdbc.BindBean
import com.github.mjdbc.Sql
import com.github.zametki.model.Group
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId

/**
 * Set of 'group' table queries
 */
interface GroupSql {

    @Sql("INSERT INTO groups (parent_id, user_id, name) VALUES (:parentId, :userId, :name)")
    fun insert(@BindBean group: Group): GroupId

    @Sql("SELECT * FROM groups WHERE id = :id")
    fun getById(@Bind("id") id: GroupId): Group?

    @Sql("SELECT id FROM groups WHERE user_id = :userId ORDER BY name")
    fun getByUser(@Bind("userId") userId: UserId): List<GroupId>

    @Sql("DELETE FROM groups WHERE id = :id")
    fun delete(@Bind("id") id: GroupId)

    @Sql("UPDATE groups SET parent_id = :parentId, name = :name WHERE id = :id")
    fun update(@BindBean c: Group)

    @Sql("SELECT id FROM groups WHERE parent_id = :id")
    fun getSubgroups(@Bind("id") id: GroupId): List<GroupId>
}
