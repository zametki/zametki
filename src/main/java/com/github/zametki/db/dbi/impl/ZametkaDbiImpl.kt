package com.github.zametki.db.dbi.impl

import com.github.mjdbc.Db
import com.github.zametki.Context
import com.github.zametki.db.dbi.AbstractDbi
import com.github.zametki.db.dbi.ZametkaDbi
import com.github.zametki.db.sql.ZametkaSql
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId
import com.github.zametki.model.Zametka
import com.github.zametki.model.ZametkaId
import java.util.function.Function

class ZametkaDbiImpl(db: Db) : AbstractDbi(db), ZametkaDbi {
    private val sql = db.attachSql(ZametkaSql::class.java)

    private fun zc() = Context.getZametkaCache()

    override fun create(zametka: Zametka) {
        zametka.id = sql.insert(zametka)
        zc().remove(zametka.userId)
        zc().update(zametka)
    }

    override fun getByUser(userId: UserId?): List<ZametkaId> {
        return if (userId == null || isInvalid(userId)) {
            emptyList()
        } else zc().getByUser(userId, Function { sql.getByUser(it) })
    }

    override fun getById(id: ZametkaId?): Zametka? {
        return if (isInvalid(id)) {
            null
        } else zc().getById(id!!, Function { sql.getById(it) })
    }

    override fun delete(zametkaId: ZametkaId) {
        val z = getById(zametkaId) ?: return
        sql.delete(zametkaId)
        zc().remove(zametkaId)
        zc().remove(z.userId)
    }

    override fun update(z: Zametka) {
        sql.update(z)
        zc().update(z)
    }

    override fun countByGroup(userId: UserId, groupId: GroupId): Int {
        return getByUser(userId).stream()
                .map<Zametka> { this.getById(it) }
                .filter { z -> z != null && z.groupId == groupId }
                .count().toInt()
    }

}
