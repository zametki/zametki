package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.annotation.MountPath
import com.github.zametki.model.Group

//todo: POST?
@MountPath("/ajax/rename-group/\${groupId}/\${name}")
class RenameGroupAjaxCall : BaseNNGroupActionAjaxCall() {
    override fun getResponseTextNN(group: Group): String {
        val name = getParameter("name").toString("")
        if (name.length < Group.MIN_NAME_LEN || name.length > Group.MAX_NAME_LEN) {
            return error("Illegal group name")
        }
        group.name = name
        Context.getGroupsDbi().update(group)

        return AjaxApiUtils.getGroupsListAsResponse(userId)
    }
}
