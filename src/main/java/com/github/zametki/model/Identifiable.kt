package com.github.zametki.model

/**
 * Database entity with ID. Not serializable and can't not be stored in Wicket sessions.
 */
open class Identifiable<T : AbstractId> {
    //TODO: make not nullable
    var id: T? = null

    override fun toString() = javaClass.simpleName + "[id:" + id + "]"
}
