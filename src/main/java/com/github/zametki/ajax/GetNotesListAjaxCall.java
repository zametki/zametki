package com.github.zametki.ajax;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.ZDateFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@MountPath("/ajax/notes/${groupId}")
public class GetNotesListAjaxCall extends BaseGroupActionAjaxCall {

    @NotNull
    @Override
    protected String getResponseText(@Nullable Group group) throws Exception {
        GroupId groupId = group == null ? null : group.id;
        JSONArray notesArray = AjaxApiUtils.getNotes(groupId);
        return new JSONObject().put("notes", notesArray).toString();
    }
}
