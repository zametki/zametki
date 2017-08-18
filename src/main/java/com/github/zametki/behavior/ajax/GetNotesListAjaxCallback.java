package com.github.zametki.behavior.ajax;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.zametki.Constants;
import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.ZDateFormat;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetNotesListAjaxCallback extends ZApiAjaxCallback {

    public GetNotesListAjaxCallback() {
        super("getNodesList", new String[]{"groupId"});
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        //todo: common subclass to check user auth status
        UserId userId = UserSession.get().getUserId();
        if (userId == null) {
            return;
        }
        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        Group group = Context.getGroupsDbi().getById(new GroupId(params.getParameterValue("groupId").toInt(-1)));
        //todo: common utils to check user rights
        if (group != null && !group.userId.equals(userId)) {
            return;
        }
        List<ZametkaId> noteIds = getList(group == null ? null : group.id);
        JSONArray result = new JSONArray();
        for (ZametkaId id : noteIds) {
            Zametka z = Context.getZametkaDbi().getById(id);
            if (z == null) {
                continue;
            }
            result.put(toJSON(z));
        }

//        target.appendJavaScript(getUpdateScript() + ";$site.Server2Client.renderNotesView('" + panel.getMarkupId() + "')"
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
