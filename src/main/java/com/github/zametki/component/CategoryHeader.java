package com.github.zametki.component;

import com.github.zametki.Context;
import com.github.zametki.event.dispatcher.ModelUpdateAjaxEvent;
import com.github.zametki.event.dispatcher.OnModelUpdate;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.jetbrains.annotations.NotNull;

public class CategoryHeader extends Panel {

    @NotNull
    private final Label nameLabel;
    @NotNull
    private final IModel<CategoryId> activeCategory;

    public CategoryHeader(String id, @NotNull IModel<CategoryId> activeCategory) {
        super(id);
        this.activeCategory = activeCategory;

        nameLabel = new Label("name", LambdaModel.of((SerializableSupplier<String>) () -> {
            CategoryId id1 = activeCategory.getObject();
            if (id1 == null) {
                return "Все категории";
            }
            Category c = Context.getCategoryDbi().getById(id1);
            return c == null ? "???" : c.title;
        }));
        nameLabel.setOutputMarkupId(true);
        add(nameLabel);
    }

    @OnModelUpdate
    public void onModelUpdate(@NotNull ModelUpdateAjaxEvent e) {
        if (e.model == activeCategory) {
            e.target.add(nameLabel);
        }
    }
}
