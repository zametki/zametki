package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

class UserId(id: Int) : AbstractId(id) {
    companion object {
        val INVALID_ID = UserId(-1)

        @Mapper
        @JvmField
        val MAPPER = DbMapper<UserId> { UserId(it.getInt(1)) }
    }
}
