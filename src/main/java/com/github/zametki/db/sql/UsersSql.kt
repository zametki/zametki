package com.github.zametki.db.sql

import com.github.mjdbc.Bind
import com.github.mjdbc.BindBean
import com.github.mjdbc.Sql
import com.github.zametki.model.User
import com.github.zametki.model.UserId

/**
 * Set of 'users' table queries
 */
interface UsersSql {

    @Sql("SELECT * FROM users WHERE id = :id")
    fun selectUserById(@Bind("id") userId: UserId): User?

    @Sql("SELECT * FROM users WHERE login = :login")
    fun selectUserByLogin(@Bind("login") login: String): User?

    @Sql("SELECT * FROM users WHERE email = :email")
    fun selectUserByEmail(@Bind("email") email: String): User?

    @Sql("INSERT INTO users (login, password_hash, email, uid, registration_date, termination_date, last_login_date, settings) " + "VALUES (:login, :passwordHash, :email, :uid, :registrationDate, :terminationDate, :lastLoginDate, :settings)")
    fun insertUser(@BindBean user: User): UserId

    @Sql("UPDATE users SET password_hash = :hash WHERE id = :id")
    fun updatePasswordHash(@Bind("id") id: UserId, @Bind("hash") passwordHash: String)

    @Sql("UPDATE users SET settings = :settings WHERE id = :id")
    fun updateSettings(@BindBean user: User)

    @Sql("UPDATE users SET last_login_date = :lastLoginDate WHERE id = :id")
    fun updateLastLoginDate(@BindBean user: User)
}
