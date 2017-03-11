package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

public class GroupEntriesCountModel extends LoadableDetachableModel<Integer> {
    @NotNull
    private final GroupId groupId;

    public GroupEntriesCountModel(@NotNull GroupId groupId) {
        this.groupId = groupId;
    }

    @Override
    protected Integer load() {
        UserId userId = UserSession.get().getUserId();
        return userId == null ? 0 : Context.getZametkaDbi().countByGroup(userId, groupId);
    }
}
