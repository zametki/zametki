package com.github.zametki.model

import com.github.mjdbc.type.DbString
import com.github.openjson.JSONArray
import com.github.openjson.JSONObject
import com.github.zametki.Context
import com.github.zametki.UserSession
import com.github.zametki.util.JsonUtils

/**
 * Set of user settings serializable to JSON
 */
class UserSettings(json: String) : DbString {

    private var lastShownGroup: GroupId? = null

    var expandedGroups: JSONArray

    init {
        expandedGroups = if (json.isEmpty()) {
            JSONArray()
        } else {
            val obj = JSONObject(json)
            val lsg = obj.optInt("lsg", 0)
            lastShownGroup = if (lsg <= 0) null else GroupId(lsg)
            if (obj.has("eg")) obj.getJSONArray("eg") else JSONArray()
        }
    }

    override fun getDbValue(): String {
        val obj = JSONObject()
        JsonUtils.putIfNotNull(obj, "lsg", lastShownGroup)
        obj.put("eg", expandedGroups)
        return obj.toString()
    }

    companion object {

        fun get(): UserSettings {
            val user = UserSession.get().user ?: return UserSettings("")
            return user.settings
        }

        fun set(us: UserSettings) {
            val user = UserSession.get().user ?: return
            user.settings = us
            Context.getUsersDbi().updateSettings(user)
        }
    }

}
