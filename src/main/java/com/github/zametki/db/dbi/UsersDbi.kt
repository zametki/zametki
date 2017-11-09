package com.github.zametki.db.dbi

import com.github.zametki.model.User
import com.github.zametki.model.UserId
import com.github.zametki.model.VerificationRecord
import com.github.zametki.model.VerificationRecordId
import com.github.zametki.model.VerificationRecordType

interface UsersDbi {

    fun getUserById(userId: UserId?): User?

    fun createUser(user: User)

    fun updateLastLoginDate(user: User)

    fun getUserByLogin(login: String?): User?

    fun getUserByEmail(email: String?): User?

    fun updatePassword(user: User, r: VerificationRecord?)

    fun updateSettings(user: User)

    fun createVerificationRecord(r: VerificationRecord)

    fun getVerificationRecordById(id: VerificationRecordId): VerificationRecord?

    fun getVerificationRecordByHashAndType(hash: String, type: VerificationRecordType): VerificationRecord?
}