package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper

class VerificationRecordId(id: Int) : AbstractId(id) {
    companion object {

        @Mapper
        @JvmField
        val MAPPER = DbMapper { r -> VerificationRecordId(r.getInt(1)) }
    }
}
