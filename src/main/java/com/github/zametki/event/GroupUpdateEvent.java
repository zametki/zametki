package com.github.zametki.event;

import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

/**
 * Fired on group property change: name change, style change.
 */
public class GroupUpdateEvent extends AjaxEvent {

    @NotNull
    public final UserId userId;

    @NotNull
    public final GroupId groupId;

    public GroupUpdateEvent(@NotNull AjaxRequestTarget target, @NotNull UserId userId, @NotNull GroupId groupId) {
        super(target);
        this.userId = userId;
        this.groupId = groupId;
    }
}
