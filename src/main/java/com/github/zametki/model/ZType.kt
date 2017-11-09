package com.github.zametki.model

import com.github.mjdbc.type.DbInt

enum class ZType(val id: Int) : DbInt {
    PLAIN_TEXT(0);


    override fun getDbValue() = 0

    companion object {

        fun fromDbValue(id: Int): ZType {
            if (id != 0) {
                throw IllegalArgumentException("Unsupported content type: " + id)
            }
            return PLAIN_TEXT
        }
    }
}
