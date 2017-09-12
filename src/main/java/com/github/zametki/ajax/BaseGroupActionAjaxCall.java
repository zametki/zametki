package com.github.zametki.ajax;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseGroupActionAjaxCall extends BaseAjaxCall {

    protected UserId userId;

    @NotNull
    @Override
    protected String getResponseText() throws Exception {
        userId = UserSession.get().getUserId();
        if (userId == null) {
            return permissionError();
        }
        Group group = Context.getGroupsDbi().getById(new GroupId(getParameter("groupId").toInt(-1)));
        if (group != null && !group.userId.equals(userId)) {
            return permissionError();
        }
        return getResponseText(group);
    }

    protected abstract String getResponseText(@Nullable Group group) throws Exception;
}
