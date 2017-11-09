package com.github.zametki.model

import com.github.mjdbc.type.DbInt
import org.apache.wicket.util.io.IClusterable

/**
 * Base class for all Ids. Serializable DB value.
 */
open class AbstractId(val intValue: Int) : DbInt, IClusterable, Comparable<AbstractId> {

    val isValid: Boolean get() = intValue > 0
    val isInvalid: Boolean get() = !isValid
    val isRoot: Boolean get() = !isValid

    override fun getDbValue() = intValue

    override fun hashCode() = intValue

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        val that = other as AbstractId?
        return intValue == that!!.intValue
    }

    override fun toString() = javaClass.simpleName + "[" + intValue + "]"

    override fun compareTo(other: AbstractId) = Integer.compare(intValue, other.intValue)
}
