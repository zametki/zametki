package com.github.zametki.event;

import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;

public class GroupTreeExpandedStateChangeEvent extends AjaxEvent {

    @NotNull
    public final GroupId groupId;

    public final boolean expanded;

    public GroupTreeExpandedStateChangeEvent(@NotNull AjaxRequestTarget target, @NotNull GroupId groupId, boolean expanded) {
        super(target);
        this.groupId = groupId;
        this.expanded = expanded;
    }
}
