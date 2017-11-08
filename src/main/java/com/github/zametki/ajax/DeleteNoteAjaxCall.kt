package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.UserSession
import com.github.zametki.annotation.MountPath
import com.github.zametki.annotation.Post
import com.github.zametki.model.ZametkaId

@Post
@MountPath("/ajax/delete-note")
class DeleteNoteAjaxCall : BaseAjaxCall() {

    override fun getResponseText(): String {
        //todo: move auth check to a base class
        val userId = UserSession.get().userId ?: return permissionError()

        val zametkaId = ZametkaId(getParameter("noteId").toInt(-1))
        val z = Context.getZametkaDbi().getById(zametkaId)
        if (z == null || z.userId != userId) {
            return error("Note not found")
        }
        Context.getZametkaDbi().delete(zametkaId)

        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, z.groupId)
    }
}
