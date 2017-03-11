package com.github.zametki.event;

import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class GroupTreeChangeEvent extends AjaxEvent {

    @NotNull
    public final UserId userId;

    @NotNull
    public final GroupId groupId;

    @NotNull
    public final GroupTreeChangeType changeType;

    public GroupTreeChangeEvent(@NotNull AjaxRequestTarget target, @NotNull UserId userId, @NotNull GroupId groupId, @NotNull GroupTreeChangeType changeType) {
        super(target);
        this.userId = userId;
        this.groupId = groupId;
        this.changeType = changeType;
    }
}
