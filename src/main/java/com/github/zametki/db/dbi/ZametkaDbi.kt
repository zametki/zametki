package com.github.zametki.db.dbi

import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId
import com.github.zametki.model.Zametka
import com.github.zametki.model.ZametkaId

interface ZametkaDbi {

    fun create(zametka: Zametka)

    /**
     * Список заметок сортированный по дате.
     */
    fun getByUser(userId: UserId?): List<ZametkaId>

    fun getById(id: ZametkaId?): Zametka?

    fun delete(zametkaId: ZametkaId)

    fun update(z: Zametka)

    fun countByGroup(userId: UserId, groupId: GroupId): Int
}
