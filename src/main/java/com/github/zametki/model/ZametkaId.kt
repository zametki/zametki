package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

class ZametkaId(value: Int) : AbstractId(value) {
    companion object {

        @Mapper
        @JvmField
        val MAPPER = DbMapper { ZametkaId(it.getInt(1)) }
    }
}
