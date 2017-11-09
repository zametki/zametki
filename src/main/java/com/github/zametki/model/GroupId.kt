package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

class GroupId(value: Int) : AbstractId(value) {
    companion object {

        val ROOT = GroupId(0)

        @Mapper
        @JvmField
        val MAPPER = DbMapper { r -> GroupId(r.getInt(1)) }
    }
}

