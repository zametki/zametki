package com.github.zametki.provider;

import com.github.zametki.Context;
import com.github.zametki.UserSession;
import com.github.zametki.component.WorkspacePageState;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.ZametkaId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LentaProvider extends AbstractListProvider<ZametkaId> {

    @NotNull
    private final WorkspacePageState state;

    public LentaProvider(@NotNull WorkspacePageState state) {
        this.state = state;
    }

    @Override
    public List<ZametkaId> getList() {
        List<ZametkaId> res = Context.getZametkaDbi().getByUser(UserSession.get().getUserId());
        GroupId activeCategory = state.activeGroupModel.getObject();
        if (activeCategory != null) {
            res = res.stream()
                    .map(id -> Context.getZametkaDbi().getById(id))
                    .filter(z -> z != null && Objects.equals(z.groupId, activeCategory))
                    .map(z -> z.id)
                    .collect(Collectors.toList());
        } else {
            res = new ArrayList<>(res);
        }
        Collections.reverse(res);
        return res;
    }

    @Override
    public IModel<ZametkaId> model(ZametkaId z) {
        return Model.of(z);
    }
}
