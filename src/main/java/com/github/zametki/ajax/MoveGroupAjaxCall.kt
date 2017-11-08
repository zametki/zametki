package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.annotation.MountPath
import com.github.zametki.model.Group
import com.github.zametki.model.GroupId

//TODO: POST
@MountPath("/ajax/move-group/\${groupId}/\${parentId}")
class MoveGroupAjaxCall : BaseNNGroupActionAjaxCall() {

    override fun getResponseTextNN(group: Group): String {
        val parentGroupId = GroupId(getParameter("parentId").toInt(-1))
        if (!parentGroupId.isRoot) {
            val parentGroup = Context.getGroupsDbi().getById(parentGroupId)
            if (parentGroup == null || parentGroup.userId != userId) {
                return error("Illegal parent group")
            }
        }
        group.parentId = parentGroupId
        Context.getGroupsDbi().update(group)
        return AjaxApiUtils.getGroupsListAsResponse(userId)
    }

}
