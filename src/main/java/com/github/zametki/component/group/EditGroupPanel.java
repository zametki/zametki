package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.form.InputField;
import com.github.zametki.component.parsley.GroupNameJsValidator;
import com.github.zametki.component.parsley.ParsleyUtils;
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
import org.jetbrains.annotations.NotNull;

public class EditGroupPanel extends Panel {

    public EditGroupPanel(@NotNull String id, @NotNull GroupId groupId, @NotNull AjaxCallback doneCallback) {
        super(id);

        Group group = Context.getGroupsDbi().getById(groupId);
        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        String title = group == null ? "" : group.name;
        InputField groupNameField = new InputField("name_field", title);
        form.add(groupNameField);

        WebMarkupContainer groupNameError = new WebMarkupContainer("group_name_error");
        form.add(groupNameError);
        GroupNameJsValidator groupNameJsValidator = new GroupNameJsValidator(groupNameError);
        groupNameField.add(groupNameJsValidator);


        form.add(new BootstrapModalCloseLink("cancel_link"));

        ValidatingJsAjaxSubmitLink saveLink = new ValidatingJsAjaxSubmitLink("save_link", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                UserId userId = WebUtils.getUserIdOrRedirectHome();
                String newName = groupNameField.getModelObject();
                if (newName.equals(title)) {
                    doneCallback.callback(target);
                    return;
                }
                if (!groupNameJsValidator.validate(newName, target, groupNameField)) {
                    return;
                }
                Group sameNameGroup = Context.getGroupsDbi().getByName(userId, newName);
                if (sameNameGroup != null) {
                    ParsleyUtils.addParsleyError(target, groupNameError, "Группа с таким именем уже существует");
                    JsUtils.focus(target, groupNameField);
                    return;
                }
                Group c = Context.getGroupsDbi().getById(groupId);
                if (c == null) {
                    doneCallback.callback(target);
                    return;
                }
                c.name = newName;
                Context.getGroupsDbi().update(c);
                doneCallback.callback(target);
                send(getPage(), Broadcast.BREADTH, new UserGroupUpdatedEvent(target, userId, groupId));
            }
        };
        form.add(saveLink);

        JsUtils.clickOnEnter(groupNameField, saveLink);
    }
}
