package com.github.zametki.component.provider;

import com.github.zametki.Context;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GreoupsProvider extends AbstractListProvider<GroupId> {

    @NotNull
    private final UserId userId;

    public GreoupsProvider(@NotNull UserId userId) {
        this.userId = userId;
    }

    @Override
    public List<GroupId> getList() {
        return Context.getGroupsDbi().getByUser(userId);
    }

    @Override
    public IModel<GroupId> model(GroupId groupId) {
        return Model.of(groupId);
    }
}
