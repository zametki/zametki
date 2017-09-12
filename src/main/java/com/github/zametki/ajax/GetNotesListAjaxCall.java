package com.github.zametki.ajax;

import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MountPath("/ajax/notes/${groupId}")
public class GetNotesListAjaxCall extends BaseGroupActionAjaxCall {

    @NotNull
    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        return AjaxApiUtils.getNotesListAsResponse(group);
    }
}
