package com.github.zametki.db.sql

import com.github.mjdbc.Bind
import com.github.mjdbc.BindBean
import com.github.mjdbc.Sql
import com.github.zametki.model.UserId
import com.github.zametki.model.Zametka
import com.github.zametki.model.ZametkaId

/**
 * Set of 'zametka' table queries
 */
interface ZametkaSql {

    @Sql("INSERT INTO zametka (creation_date, user_id, type, content, group_id) " + "VALUES (:creationDate, :userId, :type, :content, :groupId)")
    fun insert(@BindBean zametka: Zametka): ZametkaId

    @Sql("SELECT * FROM zametka WHERE id = :id")
    fun getById(@Bind("id") id: ZametkaId): Zametka?

    @Sql("SELECT id FROM zametka WHERE user_id = :id ORDER BY creation_date ASC")
    fun getByUser(@Bind("id") userId: UserId): List<ZametkaId>

    @Sql("DELETE FROM zametka WHERE id = :id")
    fun delete(@Bind("id") id: ZametkaId)

    @Sql("UPDATE zametka SET content = :content, group_id = :groupId WHERE id = :id")
    fun update(@BindBean z: Zametka)
}
