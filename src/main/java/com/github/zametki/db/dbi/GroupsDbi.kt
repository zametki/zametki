package com.github.zametki.db.dbi

import com.github.zametki.model.Group
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId

interface GroupsDbi {

    fun create(g: Group)

    fun getByUser(userId: UserId?): List<GroupId>

    fun getById(id: GroupId?): Group?

    fun update(g: Group)

    fun getByName(userId: UserId, name: String): Group?

    fun removeEmptyGroup(group: Group)

    fun getSubgroups(id: GroupId?): List<GroupId>
}
