package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.util.CategoryUtils;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.TextUtils;
import com.github.zametki.util.WebUtils;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class CreateZametkaForm extends Panel {

    private BootstrapModal addCategoryModal;

    @NotNull
    public final InputArea contentField;

    public CreateZametkaForm(@NotNull String id, @NotNull IModel<CategoryId> activeCategory, @Nullable AjaxCallback doneCallback) {
        super(id);

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        UserId userId = WebUtils.getUserOrRedirectHome().id;
        //todo: save last selected category id to settings
        CategorySelector categorySelector = new CategorySelector("category_selector", userId, activeCategory);
        form.add(categorySelector);

        contentField = new InputArea("text");
        form.add(contentField);


        ValidatingJsAjaxSubmitLink createLink = new ValidatingJsAjaxSubmitLink("save_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                String content = contentField.getInputString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                Zametka z = new Zametka();
                z.userId = WebUtils.getUserOrRedirectHome().id;
                z.creationDate = Instant.now();
                z.content = content;
                CategoryId categoryId = categorySelector.getConvertedInput();
                if (categoryId == null) {
                    categoryId = CategoryUtils.getDefaultCategoryForUser(userId);
                }
                z.categoryId = categoryId;
                WicketUtils.reactiveUpdate(activeCategory, z.categoryId, target);
                //todo: check user is owner of category
                //todo: check category is not null
                Context.getZametkaDbi().create(z);

                contentField.clearInput();
                target.add(form);

                send(getPage(), Broadcast.BREADTH, new ZametkaUpdateEvent(target, z.id, ZametkaUpdateType.CREATED));
                if (doneCallback != null) {
                    doneCallback.callback(target);
                }
            }
        };
        form.add(createLink);
        JsUtils.clickOnCtrlEnter(contentField, createLink);

        form.add(new AjaxLink<Void>("cancel_button") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (doneCallback != null) {
                    doneCallback.callback(target);
                }
            }
        }.setVisible(doneCallback != null));

        addCategoryModal = new BootstrapModal("add_category_modal", "Новая категория",
                (ComponentFactory) markupId -> new CreateCategoryForm(markupId, categorySelector.getModel(),
                        (AjaxCallback) target -> {
                            addCategoryModal.hide(target);
                            JsUtils.focus(target, contentField);
                        }),
                BootstrapModal.BodyMode.Lazy, BootstrapModal.FooterMode.Hide);
        add(addCategoryModal);

        form.add(new BootstrapLazyModalLink("add_category", addCategoryModal));
    }
}
