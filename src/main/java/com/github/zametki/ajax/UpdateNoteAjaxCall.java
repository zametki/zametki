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
@MountPath("/ajax/update-note")
public class UpdateNoteAjaxCall extends BaseAjaxCall {

    @NotNull
    @Override
    protected String getResponseText() throws Exception {
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return permissionError();
        }
        ZametkaId zId = new ZametkaId(getParameter("noteId").toInt(-1));
        Zametka z = Context.getZametkaDbi().getById(zId);
        if (z == null || !z.userId.equals(userId)) {
            return error("No user note found with id: " + zId.intValue);
        }
        String text = getParameter("text").toString("");
        if (text.length() < Zametka.MIN_CONTENT_LEN || text.length() > Zametka.MAX_CONTENT_LEN) {
            return error("Illegal text length: " + text.length());
        }
        z.content = text;
        Context.getZametkaDbi().update(z);

        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, z.groupId);
    }
}
