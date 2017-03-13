package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ComponentFactory;
import com.github.zametki.component.bootstrap.BootstrapLazyModalLink;
import com.github.zametki.component.bootstrap.BootstrapModal;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.component.parsley.ZametkaTextContentJsValidator;
import com.github.zametki.event.ZametkaUpdateEvent;
import com.github.zametki.event.ZametkaUpdateType;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.WebUtils;
import com.github.zametki.util.WicketUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class CreateZametkaForm extends Panel {

    private BootstrapModal addGroupModal;

    @NotNull
    public final InputArea contentField;

    public CreateZametkaForm(@NotNull String id, @NotNull IModel<GroupId> activeGroup, @Nullable AjaxCallback doneCallback) {
        super(id);

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        UserId userId = WebUtils.getUserIdOrRedirectHome();
        FollowingGroupsSelector groupsSelector = new FollowingGroupsSelector("group_selector", userId, activeGroup);
        form.add(groupsSelector);

        contentField = new InputArea("text");
        form.add(contentField);
        WebMarkupContainer contentError = new WebMarkupContainer("text_error");
        form.add(contentError);
        ZametkaTextContentJsValidator contentJsValidator = new ZametkaTextContentJsValidator(contentError);
        contentField.add(contentJsValidator);


        ValidatingJsAjaxSubmitLink createLink = new ValidatingJsAjaxSubmitLink("save_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                String content = contentField.getInputString();
                if (!contentJsValidator.validate(content, target, contentField)) {
                    return;
                }
                Zametka z = new Zametka();
                z.userId = WebUtils.getUserIdOrRedirectHome();
                z.creationDate = Instant.now();
                z.content = content;
                z.groupId = groupsSelector.getConvertedInput();
                //todo: check that group exists and not removed asynchronously
                WicketUtils.reactiveUpdate(activeGroup, z.groupId, target);
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

        addGroupModal = new BootstrapModal("add_group_modal", "Новая группа",
                (ComponentFactory) markupId -> new CreateGroupForm(markupId, groupsSelector.getModel(),
                        (AjaxCallback) target -> {
                            addGroupModal.hide(target);
                            JsUtils.focus(target, contentField);
                        }),
                BootstrapModal.BodyMode.Lazy, BootstrapModal.FooterMode.Hide);

        addGroupModal.addOnCloseJs("$('#" + contentField.getMarkupId() + "').focus();");
        add(addGroupModal);

        form.add(new BootstrapLazyModalLink("add_group", addGroupModal));
    }
}
