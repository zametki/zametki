package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.event.UserCategoriesUpdatedEvent;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.model.Category;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.util.TextUtils;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import java.time.Instant;

public class CreateZametkaForm extends Panel {

    public CreateZametkaForm(String id) {
        super(id);

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        UserId userId = WebUtils.getUserOrRedirectHome().id;
        //todo: save last selected category id to settings
        CategorySelector categorySelector = new CategorySelector("category_selector", userId, null);
        form.add(categorySelector);

        InputArea textField = new InputArea("text");
        form.add(textField);

        form.add(new ValidatingJsAjaxSubmitLink("save_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                String content = textField.getInputString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                Zametka z = new Zametka();
                z.userId = WebUtils.getUserOrRedirectHome().id;
                z.creationDate = Instant.now();
                z.content = content;
                z.categoryId = categorySelector.getConvertedInput();
                //todo: check user is owner of category
                //todo: check category is not null
                Context.getZametkaDbi().create(z);

                textField.clearInput();
                target.add(form);

                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, z.id, ZametkaUpdateType.CREATED));
            }
        });

        InputField categoryNameField = new InputField("new_category_name");
        form.add(categoryNameField);
        form.add(new AjaxSubmitLink("add_category") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                String newCategoryName = categoryNameField.getInputString();
                //todo: validation & error reporting
                if (TextUtils.isEmpty(newCategoryName)) {
                    return;
                }
                Category category = new Category();
                category.title = newCategoryName;
                category.userId = userId;
                Context.getCategoryDbi().create(category);

                categoryNameField.clear();
                target.add(categoryNameField);
                categorySelector.setDefaultModelObject(category.id);

                send(getPage(), Broadcast.BREADTH, new UserCategoriesUpdatedEvent(target, userId, category.id));
            }
        });
    }
}
