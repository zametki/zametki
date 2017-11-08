package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.UserSession
import com.github.zametki.annotation.MountPath
import com.github.zametki.annotation.Post
import com.github.zametki.model.Zametka
import com.github.zametki.model.ZametkaId

@Post
@MountPath("/ajax/update-note")
class UpdateNoteAjaxCall : BaseAjaxCall() {

    override fun getResponseText(): String {
        val userId = UserSession.get().userId ?: return permissionError()
        val zId = ZametkaId(getParameter("noteId").toInt(-1))
        val z = Context.getZametkaDbi().getById(zId)
        if (z == null || z.userId != userId) {
            return error("No user note found with id: " + zId.intValue)
        }
        val text = getParameter("text").toString("")
        if (text.length < Zametka.MIN_CONTENT_LEN || text.length > Zametka.MAX_CONTENT_LEN) {
            return error("Illegal text length: " + text.length)
        }
        z.content = text
        Context.getZametkaDbi().update(z)

        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, z.groupId)
    }
}
