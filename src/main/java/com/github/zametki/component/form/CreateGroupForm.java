package com.github.zametki.component.form;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.event.UserGroupUpdatedEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
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

public class CreateGroupForm extends Panel {

    public CreateGroupForm(@NotNull String id, IModel<GroupId> modelToUpdate, @NotNull AjaxCallback doneCallback) {
        super(id);
        UserId userId = WebUtils.getUserOrRedirectHome().id;

        Form form = new Form("form");
        add(form);

        InputField nameField = new InputField("group_name");
        form.add(nameField);

        // kind of optimization: we know that this component is always used in bs modal window
        form.add(new BootstrapModalCloseLink("cancel_button"));

        AjaxSubmitLink createButton = new AjaxSubmitLink("create_button") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                String newCategoryName = nameField.getInputString();
                //todo: validation & error reporting
                if (TextUtils.isEmpty(newCategoryName)) {
                    return;
                }
                Group group = new Group();
                group.title = newCategoryName;
                group.userId = userId;
                Context.getGroupsDbi().create(group);

                nameField.clear();
                target.add(nameField);
                modelToUpdate.setObject(group.id);

                doneCallback.callback(target);
                send(getPage(), Broadcast.BREADTH, new UserGroupUpdatedEvent(target, userId, group.id));
            }
        };
        form.add(createButton);

        JsUtils.clickOnEnter(nameField, createButton);
    }
}
