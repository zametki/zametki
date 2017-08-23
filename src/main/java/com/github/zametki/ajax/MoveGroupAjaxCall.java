package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.jetbrains.annotations.Nullable;

//TODO: POST
@MountPath("/ajax/move-group/${groupId}/${parentId}")
public class MoveGroupAjaxCall extends BaseGroupActionAjaxCall {

    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        if (group == null) {
            return error("Illegal group");
        }
        GroupId parentGroupId = new GroupId(getPageParameters().get("parentId").toInt(-1));
        if (!parentGroupId.isRoot()) {
            Group parentGroup = Context.getGroupsDbi().getById(parentGroupId);
            if (parentGroup == null || !parentGroup.userId.equals(userId)) {
                return error("Illegal parent group");
            }
        }
        group.parentId = parentGroupId;
        Context.getGroupsDbi().update(group);
        return AjaxApiUtils.getGroupsListAsResponse(userId);
    }

}
