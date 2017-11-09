package com.github.zametki.db.cache

import com.github.zametki.model.UserId
import com.github.zametki.model.Zametka
import com.github.zametki.model.ZametkaId
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function

class ZametkaCache {

    private val zametkaById = ConcurrentHashMap<ZametkaId, Zametka?>()

    private val zametkaByUser = ConcurrentHashMap<UserId, List<ZametkaId>>()

    fun getById(zametkaId: ZametkaId, loader: Function<ZametkaId, Zametka?>) = zametkaById.computeIfAbsent(zametkaId, loader)

    fun update(zametka: Zametka) {
        zametkaById.put(zametka.id!!, zametka)
    }

    fun remove(zametkaId: ZametkaId) {
        zametkaById.remove(zametkaId)
    }

    fun getByUser(userId: UserId, loader: Function<UserId, List<ZametkaId>>) = zametkaByUser.computeIfAbsent(userId) { id -> Collections.unmodifiableList(loader.apply(id)) }

    fun remove(userId: UserId) {
        zametkaByUser.remove(userId)
    }
}
