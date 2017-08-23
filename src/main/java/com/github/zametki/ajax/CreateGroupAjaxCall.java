package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.jetbrains.annotations.Nullable;

//TODO: POST
@MountPath("/ajax/create-group/${groupId}/${name}")
public class CreateGroupAjaxCall extends BaseGroupActionAjaxCall {

    @Override
    protected String getResponseText(@Nullable Group parentGroup) throws Exception {
        String name = getPageParameters().get("name").toString(" ");
        if (name.length() < Group.MIN_NAME_LEN || name.length() > Group.MAX_NAME_LEN) {
            return error("Illegal group name");
        }
        Group group = new Group();
        group.parentId = parentGroup != null ? parentGroup.id : GroupId.ROOT;
        group.name = name;
        group.userId = userId;
        Context.getGroupsDbi().create(group);

        return AjaxApiUtils.getGroupsListAsResponse(userId);
    }

}
