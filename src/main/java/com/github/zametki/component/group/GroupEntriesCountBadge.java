package com.github.zametki.component.group;

import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.GroupId;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.jetbrains.annotations.NotNull;

public class GroupEntriesCountBadge extends Label {
    public GroupEntriesCountBadge(@NotNull String id, @NotNull GroupId groupId) {
        super(id, new GroupEntriesCountModel(groupId));
        setOutputMarkupId(true);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        int count = (int) getDefaultModelObject();
        if (count <= 0) {
            tag.put("style", "display:none");
        }
    }

    @OnPayload(ZametkaUpdateEvent.class)
    public void onZametkaUpdated(ZametkaUpdateEvent e) {
        if (e.updateType == ZametkaUpdateType.GROUP_CHANGED || e.updateType == ZametkaUpdateType.CREATED || e.updateType == ZametkaUpdateType.DELETED) {
            detach();
            e.target.add(this);
        }
    }

}
