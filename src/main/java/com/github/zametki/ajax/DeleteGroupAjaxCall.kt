package com.github.zametki.ajax

import com.github.zametki.Context
import com.github.zametki.annotation.MountPath
import com.github.zametki.model.Group

//todo: POST/DELETE?
@MountPath("/ajax/delete-group/\${groupId}")
class DeleteGroupAjaxCall : BaseNNGroupActionAjaxCall() {

    override fun getResponseTextNN(group: Group): String {
        val nChildren = Context.getZametkaDbi().countByGroup(group.userId, group.id)
        if (nChildren > 0) {
            return error("Group is not empty")
        }
        val subgroups = Context.getGroupsDbi().getSubgroups(group.id)
        if (!subgroups.isEmpty()) {
            return error("Group is not empty")
        }
        Context.getGroupsDbi().removeEmptyGroup(group)

        return AjaxApiUtils.getGroupsListAsResponse(userId)
    }

}
