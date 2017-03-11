package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.parsley.GroupNameJsValidator;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.event.UserGroupUpdatedEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.UserId;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.WebUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

public class CreateGroupForm extends Panel {

    public CreateGroupForm(@NotNull String id, IModel<GroupId> modelToUpdate, @NotNull AjaxCallback doneCallback) {
        super(id);
        UserId userId = WebUtils.getUserOrRedirectHome().id;

        Form form = new Form("form");
        add(form);

        ParentGroupSelector parentGroupSelector = new ParentGroupSelector("parent_group", userId, modelToUpdate.getObject());
        form.add(parentGroupSelector);

        InputField groupNameField = new InputField("group_name");
        form.add(groupNameField);

        WebMarkupContainer groupNameError = new WebMarkupContainer("group_name_error");
        form.add(groupNameError);
        GroupNameJsValidator groupNameJsValidator = new GroupNameJsValidator(groupNameError);
        groupNameField.add(groupNameJsValidator);


        // kind of optimization: we know that this component is always used in bs modal window
        form.add(new BootstrapModalCloseLink("cancel_button"));

        ValidatingJsAjaxSubmitLink createButton = new ValidatingJsAjaxSubmitLink("create_button", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                String newCategoryName = groupNameField.getInputString();
                if (!groupNameJsValidator.validate(newCategoryName, target, groupNameError, groupNameField)) {
                    return;
                }
                Group group = new Group();
                group.parentId = parentGroupSelector.getConvertedInput();
                group.name = newCategoryName;
                group.userId = userId;
                Context.getGroupsDbi().create(group);

                groupNameField.clear();
                target.add(groupNameField);
                modelToUpdate.setObject(group.id);

                doneCallback.callback(target);
                send(getPage(), Broadcast.BREADTH, new UserGroupUpdatedEvent(target, userId, group.id));
            }
        };
        form.add(createButton);

        JsUtils.clickOnEnter(groupNameField, createButton);
    }
}
