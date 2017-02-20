package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CategorySelector extends DropDownChoice<CategoryId> {

    @NotNull
    private final UserId userId;

    public CategorySelector(@NotNull String id, @NotNull UserId userId, @Nullable CategoryId selectedId) {
        super(id);
        this.userId = userId;
        setOutputMarkupId(true);
        setDefaultModel(Model.of(selectedId));

        setChoices(new LoadableDetachableModel<List<? extends CategoryId>>() {
            @Override
            protected List<? extends CategoryId> load() {
                return Context.getCategoryDbi().getByUser(userId);
            }
        });

        setChoiceRenderer(new ChoiceRenderer<CategoryId>() {
            @Override
            public Object getDisplayValue(CategoryId id) {
                Category c = Context.getCategoryDbi().getById(id);
                return c == null ? "???" : c.title;
            }
        });
    }

    @OnPayload(UserCategoriesUpdatedEvent.class)
    public void onCategoriesUpdate(@NotNull UserCategoriesUpdatedEvent e) {
        if (e.userId.equals(userId)) {
            detach();
            e.target.add(this);
        }
    }
}
