package com.github.zametki.ajax

import com.github.zametki.annotation.MountPath
import com.github.zametki.model.Group

@MountPath("/ajax/notes/\${groupId}")
class GetNotesListAjaxCall : BaseGroupActionAjaxCall() {

    override fun getResponseText(group: Group?) = AjaxApiUtils.getNotesListAsResponse(group)
}
