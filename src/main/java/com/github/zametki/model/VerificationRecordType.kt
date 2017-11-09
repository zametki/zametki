package com.github.zametki.model

import com.github.mjdbc.type.DbInt

enum class VerificationRecordType(private val dbId: Int) : DbInt {
    EmailValidation(1),
    PasswordReset(2);


    override fun getDbValue() = dbId

    companion object {

        fun fromId(id: Int): VerificationRecordType {
            if (id == EmailValidation.dbId) {
                return EmailValidation
            } else if (id == PasswordReset.dbId) {
                return PasswordReset
            }
            throw IllegalArgumentException("Not valid id: " + id)
        }
    }
}
