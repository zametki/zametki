package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import org.jetbrains.annotations.Nullable;

//todo: POST?
@MountPath("/ajax/rename-group/${groupId}/${name}")
public class RenameGroupAjaxCall extends BaseGroupActionAjaxCall {
    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        if (group == null) {
            return error("Group not found");
        }
        String name = getParameter("name").toString("");
        if (name.length() < Group.MIN_NAME_LEN || name.length() > Group.MAX_NAME_LEN) {
            return error("Illegal group name");
        }
        group.name = name;
        Context.getGroupsDbi().update(group);

        return AjaxApiUtils.getGroupsListAsResponse(userId);
    }
}
