package com.github.zametki.ajax;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.annotation.MountPath;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.ZDateFormat;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@MountPath("/ajax/notes/${groupId}")
public class GetNotesListAjaxCall extends BaseAjaxCall {

    @NotNull
    @Override
    protected String getResponseText() throws Exception {
        //todo: common subclass to check user auth status
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return "todo";
        }
        PageParameters pp = getPageParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(pp.get("groupId").toInt(-1)));
        //todo: common utils to check user rights
        if (group != null && !group.userId.equals(userId)) {
            return "todo";
        }
        List<ZametkaId> noteIds = getList(group == null ? null : group.id);
        JSONArray notesArray = new JSONArray();
        for (ZametkaId id : noteIds) {
            Zametka z = Context.getZametkaDbi().getById(id);
            if (z == null) {
                continue;
            }
            notesArray.put(toJSON(z));
        }
        return new JSONObject().put("notes", notesArray).toString();
    }

    private static final ZDateFormat DF = ZDateFormat.getInstance("dd MMMM yyyy", Constants.MOSCOW_TZ);

    @NotNull
    public static JSONObject toJSON(@NotNull Zametka z) {
        JSONObject json = new JSONObject();
        json.put("id", z.id.intValue);
        json.put("group", z.groupId.intValue);
        json.put("body", z.content);
        json.put("dateText", DF.format(z.creationDate));
        return json;
    }

    public static List<ZametkaId> getList(@Nullable GroupId groupId) {
        List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
        if (groupId != null) {
            res = res.stream()
                    .map(id -> Context.getZametkaDbi().getById(id))
                    .filter(z -> z != null && Objects.equals(z.groupId, groupId))
                    .map(z -> z.id)
                    .collect(Collectors.toList());
        } else {
            res = new ArrayList<>(res);
        }
        Collections.reverse(res);
        return res;
    }
}
