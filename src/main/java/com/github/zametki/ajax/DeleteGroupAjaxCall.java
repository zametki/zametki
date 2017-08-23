package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//todo: POST/DELETE?
@MountPath("/ajax/delete-group/${groupId}")
public class DeleteGroupAjaxCall extends BaseGroupActionAjaxCall {

    @NotNull
    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        if (group == null) {
            return error("Group not found");
        }
        int nChildren = Context.getZametkaDbi().countByGroup(group.userId, group.id);
        if (nChildren > 0) {
            return error("Group is not empty");
        }
        List<GroupId> subgroups = Context.getGroupsDbi().getSubgroups(group.id);
        if (!subgroups.isEmpty()) {
            return error("Group is not empty");
        }
        Context.getGroupsDbi().removeEmptyGroup(group);

        return AjaxApiUtils.getGroupsListAsResponse(userId);
    }

}
