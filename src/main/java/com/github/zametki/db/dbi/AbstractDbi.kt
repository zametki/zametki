package com.github.zametki.db.dbi

import com.github.mjdbc.Db
import com.github.zametki.model.AbstractId
import com.github.zametki.util.LazyValue
import org.jetbrains.annotations.Contract

/**
 * Base class for all Dbi implementations.
 */
open class AbstractDbi(protected val db: Db) {
    /**
     * Helper methods used in Dbi implementations to assert about some state.
     * Placed here because not used anywhere else in the code.
     */
    fun assertTrue(v: Boolean, checkName: LazyValue<String>) {
        if (!v) {
            throw IllegalStateException(checkName.get())
        }
    }

    @Contract("null -> true")
    fun isInvalid(id: AbstractId?) = id == null || !id.isValid
}
