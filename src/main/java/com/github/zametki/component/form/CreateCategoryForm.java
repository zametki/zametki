package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.TextUtils;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

public class CreateCategoryForm extends Panel {

    public CreateCategoryForm(@NotNull String id, IModel<CategoryId> modelToUpdate, @NotNull AjaxCallback doneCallback) {
        super(id);
        UserId userId = WebUtils.getUserOrRedirectHome().id;

        Form form = new Form("form");
        add(form);

        InputField nameField = new InputField("category_name");
        form.add(nameField);

        AjaxSubmitLink createButton = new AjaxSubmitLink("create_button") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                String newCategoryName = nameField.getInputString();
                //todo: validation & error reporting
                if (TextUtils.isEmpty(newCategoryName)) {
                    return;
                }
                Category category = new Category();
                category.title = newCategoryName;
                category.userId = userId;
                Context.getCategoryDbi().create(category);

                nameField.clear();
                target.add(nameField);
                modelToUpdate.setObject(category.id);

                doneCallback.callback(target);
                send(getPage(), Broadcast.BREADTH, new UserCategoriesUpdatedEvent(target, userId, category.id));
            }
        };
        form.add(createButton);

        JsUtils.clickOnEnter(nameField, createButton);
    }
}
