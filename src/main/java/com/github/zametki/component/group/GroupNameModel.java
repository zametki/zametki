package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

public class GroupNameModel extends LoadableDetachableModel<String> {
    @NotNull
    private final GroupId groupId;

    public GroupNameModel(@NotNull GroupId groupId) {
        this.groupId = groupId;
    }

    @Override
    protected String load() {
        Group g = Context.getGroupsDbi().getById(groupId);
        return g == null ? "???" : g.name;
    }
}
