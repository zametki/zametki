package com.github.zametki.db.dbi.impl

import com.github.mjdbc.Db
import com.github.zametki.Context
import com.github.zametki.db.dbi.AbstractDbi
import com.github.zametki.db.dbi.UsersDbi
import com.github.zametki.db.sql.UsersSql
import com.github.zametki.db.sql.VerificationRecordSql
import com.github.zametki.model.*
import com.github.zametki.util.LazyValue
import com.github.zametki.util.TextUtils
import java.time.Instant
import java.util.*

/**
 * Database access interface for user data
 */
class UsersDbiImpl(db: Db) : AbstractDbi(db), UsersDbi {

    private val usersSql = db.attachSql(UsersSql::class.java)

    private val vrSql = db.attachSql(VerificationRecordSql::class.java)

    override fun getUserById(userId: UserId?) = if (userId == null) null else usersSql.selectUserById(userId)


    /**
     * Creates user, assigns uid and id. Repacks personal info and settings.
     */
    override fun createUser(user: User) {
        user.uid = UUID.randomUUID().toString()
        user.id = usersSql.insertUser(user)

        val g = Group()
        g.userId = user.id!!
        g.name = Group.DEFAULT_GROUP_TITLE
        Context.getGroupsDbi().create(g)
    }

    override fun updateLastLoginDate(user: User) {
        user.lastLoginDate = Instant.now()
        usersSql.updateLastLoginDate(user)
    }

    override fun getUserByLogin(login: String?) = if (TextUtils.isEmpty(login)) null else usersSql.selectUserByLogin(login!!)

    override fun getUserByEmail(email: String?) = if (TextUtils.isEmpty(email)) null else usersSql.selectUserByEmail(email!!)

    override fun updatePassword(user: User, r: VerificationRecord?) {
        assertTrue(r == null || r.type == VerificationRecordType.PasswordReset && user.id == r.userId, LazyValue { "Некорректная запись: $r user: $user" })
        usersSql.updatePasswordHash(user.id!!, user.passwordHash)
        if (r != null) {
            vrSql.updateVerificationDate(r.id!!, Date())
        }
    }

    override fun updateSettings(user: User) {
        usersSql.updateSettings(user)
    }

    /**
     * Creates verification record. Assigns hash to it.
     */
    override fun createVerificationRecord(r: VerificationRecord) {
        r.hash = UUID.randomUUID().toString()
        r.id = vrSql.insertVerificationRecord(r)
    }

    override fun getVerificationRecordById(id: VerificationRecordId) = vrSql.selectVerificationRecordById(id)

    override fun getVerificationRecordByHashAndType(hash: String, type: VerificationRecordType) = vrSql.selectVerificationRecordByHashAndType(hash, type)
}
