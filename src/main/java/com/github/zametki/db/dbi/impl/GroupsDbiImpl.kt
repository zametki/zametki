package com.github.zametki.db.dbi.impl

import com.github.mjdbc.Db
import com.github.zametki.Context
import com.github.zametki.db.dbi.AbstractDbi
import com.github.zametki.db.dbi.GroupsDbi
import com.github.zametki.db.sql.GroupSql
import com.github.zametki.model.Group
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId
import java.util.function.Function

class GroupsDbiImpl(db: Db) : AbstractDbi(db), GroupsDbi {

    private val sql = db.attachSql(GroupSql::class.java)

    private fun cc() = Context.getGroupsCache()

    override fun create(g: Group) {
        g.id = sql.insert(g)
        cc().remove(g.userId)
        cc().update(g)
    }

    override fun getByUser(userId: UserId?): List<GroupId> {
        return if (isInvalid(userId)) {
            emptyList()
        } else cc().getByUser(userId!!, Function { sql.getByUser(it) })
    }

    override fun getById(id: GroupId?): Group? {
        return if (isInvalid(id)) {
            null
        } else cc().getById(id!!, Function { sql.getById(it) })
    }

    override fun update(g: Group) {
        sql.update(g)
        cc().update(g)
    }

    override fun getByName(userId: UserId, name: String): Group? {
        val groupIds = getByUser(userId)
        return groupIds
                .map { getById(it) }
                .firstOrNull { it != null && it.name == name }
    }

    override fun removeEmptyGroup(group: Group) {
        sql.delete(group.id!!)
        cc().remove(group.id!!)
        cc().remove(group.userId)
    }

    override fun getSubgroups(id: GroupId?) =
            if (id == null || !id.isValid) {
                emptyList()
            } else sql.getSubgroups(id)

}
