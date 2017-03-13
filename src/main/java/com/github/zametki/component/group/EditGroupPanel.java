package com.github.zametki.component.group;

import com.github.zametki.Context;
import com.github.zametki.component.basic.AjaxCallback;
import com.github.zametki.component.basic.ContainerWithId;
import com.github.zametki.component.bootstrap.BootstrapModalCloseLink;
import com.github.zametki.component.form.InputField;
import com.github.zametki.component.form.ParentGroupSelector;
import com.github.zametki.component.parsley.GroupNameJsValidator;
import com.github.zametki.component.parsley.ParsleyUtils;
import com.github.zametki.component.parsley.ValidatingJsAjaxSubmitLink;
import com.github.zametki.event.GroupTreeChangeEvent;
import com.github.zametki.event.GroupTreeChangeType;
import com.github.zametki.event.GroupUpdateEvent;
import com.github.zametki.model.Group;
import com.github.zametki.model.GroupId;
import com.github.zametki.model.User;
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

        User user = WebUtils.getUserOrRedirectHome();

        Group group = Context.getGroupsDbi().getById(groupId);
        assert group != null;

        Form form = new Form("form");
        form.setOutputMarkupId(true);
        add(form);

        ParentGroupSelector parentGroupSelector = new ParentGroupSelector("parent_group_field", group, GroupTreeModel.build(user));
        form.add(parentGroupSelector);
        WebMarkupContainer parentGroupError = new ContainerWithId("parent_group_error");
        form.add(parentGroupError);

        String name = group.name;
        InputField groupNameField = new InputField("name_field", name);
        form.add(groupNameField);

        WebMarkupContainer groupNameError = new WebMarkupContainer("group_name_error");
        form.add(groupNameError);
        GroupNameJsValidator groupNameJsValidator = new GroupNameJsValidator(groupNameError);
        groupNameField.add(groupNameJsValidator);

        form.add(new BootstrapModalCloseLink("cancel_link"));

        ValidatingJsAjaxSubmitLink saveLink = new ValidatingJsAjaxSubmitLink("save_link", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                User user = WebUtils.getUserOrRedirectHome();
                if (groupId.isRoot()) { // can't edit root category.
                    doneCallback.callback(target);
                    return;
                }

                Group group = Context.getGroupsDbi().getById(groupId);
                if (group == null) {
                    doneCallback.callback(target);
                    return;
                }

                GroupTreeNode newParentNode = parentGroupSelector.getConvertedInput();
                GroupId newParentId = newParentNode == null ? null : newParentNode.getGroupId();
                GroupTreeModel tree = GroupTreeModel.build(user);
                if (newParentId == null || !tree.canBeParent(newParentId, groupId)) {
                    ParsleyUtils.addParsleyError(target, parentGroupError, "Некорректная родительская группа");
                    JsUtils.focus(target, parentGroupSelector);
                    return;
                }
                boolean parentChanged = !group.parentId.equals(newParentId);

                String newName = groupNameField.getModelObject();
                if (!groupNameJsValidator.validate(newName, target, groupNameField)) {
                    return;
                }
                boolean nameChanged = !newName.equals(group.name);
                Group sameNameGroup = nameChanged ? Context.getGroupsDbi().getByName(user.id, newName) : null;
                if (sameNameGroup != null) {
                    ParsleyUtils.addParsleyError(target, groupNameError, "Группа с таким именем уже существует");
                    JsUtils.focus(target, groupNameField);
                    return;
                }
                if (nameChanged || parentChanged) {
                    group.name = newName;
                    group.parentId = newParentId;
                    Context.getGroupsDbi().update(group);
                    send(getPage(), Broadcast.BREADTH, new GroupUpdateEvent(target, user.id, groupId));
                    if (parentChanged) {
                        send(getPage(), Broadcast.BREADTH, new GroupTreeChangeEvent(target, user.id, groupId, GroupTreeChangeType.ParentChanged));
                    }
                }
                doneCallback.callback(target);
            }
        };
        form.add(saveLink);

        JsUtils.clickOnEnter(groupNameField, saveLink);
    }
}
