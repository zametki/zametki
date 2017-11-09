package com.github.zametki.model

import com.github.mjdbc.DbMapper
import com.github.mjdbc.Mapper
import com.github.zametki.util.DateUtils.optionalInstant
import java.time.Instant

/**
 *
 */
class VerificationRecord : Identifiable<VerificationRecordId> {

    lateinit var hash: String
    var userId: UserId? = null
    lateinit var type: VerificationRecordType
    lateinit var value: String
    lateinit var creationDate: Instant
    lateinit var verificationDate: Instant

    constructor() {}

    constructor(user: User, type: VerificationRecordType, value: String) {
        this.userId = user.id
        this.type = type
        this.value = value
        this.creationDate = Instant.now()
    }

    companion object {

        @Mapper
        @JvmField
        val MAPPER = DbMapper { r ->
            val res = VerificationRecord()
            res.id = VerificationRecordId(r.getInt("id"))
            res.hash = r.getString("hash")
            res.userId = UserId(r.getInt("user_id"))
            res.type = VerificationRecordType.fromId(r.getInt("type"))
            res.value = r.getString("value")
            res.creationDate = r.getTimestamp("creation_date").toInstant()
            res.verificationDate = optionalInstant(r.getTimestamp("verification_date"))
            res
        }
    }


}
