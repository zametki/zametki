package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper
import com.github.zametki.util.DateUtils.optionalInstant
import java.time.Instant

class User : Identifiable<UserId>() {

    /**
     * Authentication.
     */
    var login = ""

    var passwordHash = ""

    var email = ""

    var uid = "" //36-symbols UUID based hash, to be used for personal resources mapping (like avatar)

    /**
     * Status.
     */
    var registrationDate = Instant.MIN!!

    var terminationDate: Instant? = null

    /**
     * Tracking.
     */
    var lastLoginDate = Instant.MIN!!

    var settings = UserSettings("")

    override fun toString() = "U[" + (if (id == null) "?" else "" + id!!.dbValue) + "|" + email + "]"

    companion object {

        val LOGIN_MIN_LENGTH = 3
        val LOGIN_MAX_LENGTH = 30

        val PASSWORD_MIN_LENGTH = 5
        val PASSWORD_MAX_LENGTH = 30

        val EMAIL_MIN_LENGTH = 8
        val EMAIL_MAX_LENGTH = 50

        val FIRST_LAST_NAME_MIN_LENGTH = 2
        val FIRST_LAST_NAME_MAX_LENGTH = 30

        @Mapper
        @JvmField
        val MAPPER = DbMapper {
            val res = User()
            res.id = UserId(it.getInt("id"))
            res.login = it.getString("login")
            res.uid = it.getString("uid")
            res.email = it.getString("email")
            res.passwordHash = it.getString("password_hash")
            res.registrationDate = it.getTimestamp("registration_date").toInstant()
            res.terminationDate = optionalInstant(it.getTimestamp("termination_date"))
            res.lastLoginDate = optionalInstant(it.getTimestamp("last_login_date"))
            res.settings = UserSettings(it.getString("settings"))
            res
        }
    }
}
