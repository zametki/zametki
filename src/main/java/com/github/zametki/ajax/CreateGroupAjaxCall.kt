package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.annotation.MountPath
import com.github.zametki.annotation.Post
import com.github.zametki.model.Group
import com.github.zametki.model.GroupId

@Post
@MountPath("/ajax/create-group")
class CreateGroupAjaxCall : BaseGroupActionAjaxCall() {

    override fun getResponseText(group: Group?): String {
        val name = getParameter("name").toString("")
        if (name.length < Group.MIN_NAME_LEN || name.length > Group.MAX_NAME_LEN) {
            return error("Illegal group name")
        }
        val newGroup = Group()
        newGroup.parentId = if (group != null) group.id else GroupId.ROOT
        newGroup.name = name
        newGroup.userId = userId
        Context.getGroupsDbi().create(newGroup)

        return AjaxApiUtils.getGroupsListAsResponse(userId)
    }

}
