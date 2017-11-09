package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

import java.time.Instant

class Zametka : Identifiable<ZametkaId>() {

    var userId = UserId.INVALID_ID

    var creationDate = Instant.MIN!!

    var type = ZType.PLAIN_TEXT

    var content = ""

    var groupId = GroupId.ROOT

    override fun toString() = "Z[" + (if (id == null) "?" else "" + id!!.intValue) + "]"

    companion object {

        val MIN_CONTENT_LEN = 1
        val MAX_CONTENT_LEN = 65535

        @Mapper
        @JvmField
        val MAPPER = DbMapper {
            val res = Zametka()
            res.id = ZametkaId(it.getInt("id"))
            res.userId = UserId(it.getInt("user_id"))
            res.creationDate = it.getTimestamp("creation_date").toInstant()
            res.type = ZType.fromDbValue(it.getInt("type"))
            res.content = it.getString("content")
            res.groupId = GroupId(it.getInt("group_id"))
            res
        }
    }

}
