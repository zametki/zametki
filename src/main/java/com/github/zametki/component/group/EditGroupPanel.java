package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.form.InputField;
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
import org.jetbrains.annotations.NotNull;

public class EditGroupPanel extends Panel {

    public EditGroupPanel(@NotNull String id, @NotNull GroupId groupId, @NotNull AjaxCallback doneCallback) {
        super(id);

        Group group = Context.getGroupsDbi().getById(groupId);
        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        String title = group == null ? "" : group.name;
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

        JsUtils.clickOnEnter(nameField, saveLink);
    }
}
