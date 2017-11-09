package com.github.zametki.db.sql

import com.github.mjdbc.Bind
import com.github.mjdbc.BindBean
import com.github.mjdbc.Sql
import com.github.zametki.model.VerificationRecord
import com.github.zametki.model.VerificationRecordId
import com.github.zametki.model.VerificationRecordType

import java.util.Date


interface VerificationRecordSql {

    @Sql("UPDATE verification_record SET verification_date = :date WHERE id = :id")
    fun updateVerificationDate(@Bind("id") id: VerificationRecordId, @Bind("date") date: Date)

    @Sql("INSERT INTO verification_record (hash, user_id, type, value, creation_date, verification_date) " + "VALUES (:hash, :userId, :type, :value, :creationDate, :verificationDate)")
    fun insertVerificationRecord(@BindBean r: VerificationRecord): VerificationRecordId

    @Sql("SELECT * FROM verification_record WHERE id = :id")
    fun selectVerificationRecordById(@Bind("id") id: VerificationRecordId): VerificationRecord

    @Sql("SELECT * FROM verification_record WHERE hash = :hash AND type = :type")
    fun selectVerificationRecordByHashAndType(@Bind("hash") hash: String, @Bind("type") type: VerificationRecordType): VerificationRecord
}
