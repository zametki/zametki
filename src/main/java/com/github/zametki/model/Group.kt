package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

class Group : Identifiable<GroupId>() {

    var parentId = GroupId.ROOT

    var userId = UserId.INVALID_ID

    var name = ""


    override fun toString() = "Group[" + (if (id == null) "?" else "" + id!!.intValue) + "|" + name + "]"

    companion object {

        val DEFAULT_GROUP_TITLE = "Без группы"
        val MIN_NAME_LEN = 1
        val MAX_NAME_LEN = 48

        @Mapper
        @JvmField
        val MAPPER = DbMapper { r ->
            val res = Group()
            res.id = GroupId(r.getInt("id"))
            res.parentId = GroupId(r.getInt("parent_id"))
            res.userId = UserId(r.getInt("user_id"))
            res.name = r.getString("name")
            res
        }
    }

}
