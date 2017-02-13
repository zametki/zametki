package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.event.dispatcher.OnPayload;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategorySelector extends DropDownChoice<CategoryId> {

    @NotNull
    private final UserId userId;

    public CategorySelector(@NotNull String id, @NotNull UserId userId) {
        super(id);
        this.userId = userId;
        setOutputMarkupId(true);
        updateChoices(userId);
        setChoiceRenderer(new ChoiceRenderer<CategoryId>() {
            @Override
            public Object getDisplayValue(CategoryId id) {
                Category c = Context.getCategoryDbi().getById(id);
                return c == null ? "???" : c.title;
            }
        });
    }

    private void updateChoices(@NotNull UserId userId) {
        List<CategoryId> choices = Context.getCategoryDbi().getByUser(userId);
        //todo: sort
        setChoices(choices);
        if (getDefaultModelObject() == null && !choices.isEmpty()) {
            setDefaultModel(Model.of(choices.get(0)));
        }
    }

    @OnPayload(UserCategoriesUpdatedEvent.class)
    public void onCategoriesUpdate(UserCategoriesUpdatedEvent e) {
        if (e.userId.equals(userId)) {
            updateChoices(userId);
            e.target.add(this);
        }
    }
}
