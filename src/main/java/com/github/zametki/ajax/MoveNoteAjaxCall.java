package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.Nullable;

//TODO: POST
@MountPath("/ajax/move-note/${noteId}/${groupId}")
public class MoveNoteAjaxCall extends BaseGroupActionAjaxCall {

    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        if (group == null || group.id.isRoot()) {
            return error("Illegal group");
        }
        ZametkaId zametkaId = new ZametkaId(getParameter("noteId").toInt(-1));
        Zametka z = Context.getZametkaDbi().getById(zametkaId);
        if (z == null || !z.userId.equals(userId)) {
            return error("Note not found");
        }
        GroupId oldGroupId = z.groupId;
        if (!z.groupId.equals(group.id)) {
            z.groupId = group.id;
            Context.getZametkaDbi().update(z);
        }
        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, oldGroupId);
    }
}
