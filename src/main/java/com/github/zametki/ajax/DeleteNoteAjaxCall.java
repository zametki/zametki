package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.annotation.Post;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;

@Post
@MountPath("/ajax/delete-note")
public class DeleteNoteAjaxCall extends BaseAjaxCall {

    @NotNull
    @Override
    protected String getResponseText() throws Exception {
        //todo: move auth check to a base class
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return permissionError();
        }

        ZametkaId zametkaId = new ZametkaId(getParameter("noteId").toInt(-1));
        Zametka z = Context.getZametkaDbi().getById(zametkaId);
        if (z == null || !z.userId.equals(userId)) {
            return error("Note not found");
        }
        Context.getZametkaDbi().delete(zametkaId);

        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, z.groupId);
    }
}
