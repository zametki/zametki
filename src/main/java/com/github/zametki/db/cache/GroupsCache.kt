package com.github.zametki.db.cache

import com.github.zametki.model.Group
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function

class GroupsCache {

    private val groupById = ConcurrentHashMap<GroupId, Group?>()

    private val groupsByUser = ConcurrentHashMap<UserId, List<GroupId>>()

    fun getById(groupId: GroupId, loader: Function<GroupId, Group?>) = groupById.computeIfAbsent(groupId, loader)

    fun update(group: Group) {
        groupById.put(group.id!!, group)
    }

    fun remove(groupId: GroupId) {
        groupById.remove(groupId)
    }

    fun getByUser(userId: UserId, loader: Function<UserId, List<GroupId>>) = groupsByUser.computeIfAbsent(userId) { id -> Collections.unmodifiableList(loader.apply(id)) }

    fun remove(userId: UserId) {
        groupsByUser.remove(userId)
    }
}
