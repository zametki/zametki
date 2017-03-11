package com.github.zametki.component.group;

import com.github.zametki.model.GroupId;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.jetbrains.annotations.NotNull;

public class GroupEntriesCountBadge extends Label {
    public GroupEntriesCountBadge(@NotNull String id, @NotNull GroupId groupId) {
        super(id, new GroupEntriesCountModel(groupId));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        int count = (int) getDefaultModelObject();
        if (count <= 0) {
            tag.put("style", "display:none");
        }
    }
}
