package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.annotation.Post;
import com.github.zametki.model.Group;
import com.github.zametki.model.Zametka;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@Post
@MountPath("/ajax/create-note")
public class CreateNoteAjaxCall extends BaseGroupActionAjaxCall {

    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        if (group == null) {
            return error("Illegal group name");
        }
        String text = getParameter("text").toString("");
        if (text.length() < Zametka.MIN_CONTENT_LEN || text.length() > Zametka.MAX_CONTENT_LEN) {
            return error("Illegal text length: " + text.length());
        }
        Zametka z = new Zametka();
        z.groupId = group.id;
        z.creationDate = Instant.now();
        z.userId = userId;
        z.content = text;
        Context.getZametkaDbi().create(z);

        return AjaxApiUtils.getNotesAndGroupsAsResponse(userId, group.id);
    }

}
