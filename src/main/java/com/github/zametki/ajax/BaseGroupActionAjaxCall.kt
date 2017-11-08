package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.UserSession
import com.github.zametki.model.Group
import com.github.zametki.model.GroupId
import com.github.zametki.model.UserId

abstract class BaseGroupActionAjaxCall : BaseAjaxCall() {

    protected var userId: UserId = UserId.INVALID_ID

    @Throws(Exception::class)
    override fun getResponseText(): String {
        userId = UserSession.get().userId ?: UserId.INVALID_ID
        if (userId.isInvalid) {
            return permissionError()
        }
        val group = Context.getGroupsDbi().getById(GroupId(getParameter("groupId").toInt(-1)))
        return if (group != null && group.userId != userId) {
            permissionError()
        } else getResponseText(group)
    }

    @Throws(Exception::class)
    protected abstract fun getResponseText(group: Group?): String
}
