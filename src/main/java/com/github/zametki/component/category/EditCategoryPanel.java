package com.github.zametki.component.category;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.form.InputField;
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
import org.jetbrains.annotations.NotNull;

public class EditCategoryPanel extends Panel {

    public EditCategoryPanel(@NotNull String id, @NotNull CategoryId categoryId, @NotNull AjaxCallback doneCallback) {
        super(id);

        Category category = Context.getCategoryDbi().getById(categoryId);
        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        String title = category == null ? "" : category.title;
        InputField nameField = new InputField("name_field", title);
        form.add(nameField);

        form.add(new BootstrapModalCloseLink("cancel_link"));

        AjaxSubmitLink saveLink = new AjaxSubmitLink("save_link") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                UserId userId = WebUtils.getUserIdOrRedirectHome();
                String newName = nameField.getModelObject();
                if (newName.equals(title)) {
                    doneCallback.callback(target);
                    return;
                }
                if (TextUtils.isEmpty(newName)) { // todo: validate!
                    return;
                }
                Category c = Context.getCategoryDbi().getById(categoryId);
                if (c == null) {
                    doneCallback.callback(target);
                    return;
                }
                c.title = newName;
                Context.getCategoryDbi().update(c);
                doneCallback.callback(target);
                send(getPage(), Broadcast.BREADTH, new UserCategoriesUpdatedEvent(target, userId, categoryId));
            }
        };
        form.add(saveLink);

        JsUtils.clickOnEnter(nameField, saveLink);
    }
}
