package com.github.zametki.component.provider;

import com.github.zametki.Context;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.AbstractListProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoriesProvider extends AbstractListProvider<CategoryId> {

    @NotNull
    private final UserId userId;

    public CategoriesProvider(@NotNull UserId userId) {
        this.userId = userId;
    }

    @Override
    public List<CategoryId> getList() {
        return Context.getCategoryDbi().getByUser(userId);
    }

    @Override
    public IModel<CategoryId> model(CategoryId categoryId) {
        return Model.of(categoryId);
    }
}
