package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.annotation.MountPath
import com.github.zametki.model.Group
import com.github.zametki.model.ZametkaId

//TODO: POST
@MountPath("/ajax/move-note/\${noteId}/\${groupId}")
class MoveNoteAjaxCall : BaseNNGroupActionAjaxCall() {

    override fun getResponseTextNN(group: Group): String {
        if (group.id.isRoot) {
            return error("Illegal group")
        }
        val zametkaId = ZametkaId(getParameter("noteId").toInt(-1))
        val z = Context.getZametkaDbi().getById(zametkaId)
        if (z == null || z.userId != userId) {
            return error("Note not found")
        }
        val oldGroupId = z.groupId
        if (z.groupId != group.id) {
            z.groupId = group.id
            Context.getZametkaDbi().update(z)
        }
        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, oldGroupId)
    }
}
