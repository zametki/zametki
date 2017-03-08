package com.github.zametki.component.parsley;

import com.github.zametki.model.Group;
import com.github.zametki.util.JsUtils;
import com.github.zametki.util.PL;
import com.github.zametki.util.ValidatorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GroupNameJsValidator extends RequiredFieldJsValidator {

    public GroupNameJsValidator(@Nullable Component errorContainer) {
        super(errorContainer);
        attributeMap.put("data-parsley-minlength", Group.MIN_NAME_LEN);
        attributeMap.put("data-parsley-maxlength", Group.MAX_NAME_LEN);
        attributeMap.put("data-parsley-minlength-message", "Мин. длина имени группы: " + PL.npl(Group.MIN_NAME_LEN, " символ"));
        attributeMap.put("data-parsley-maxlength-message", "Имя группы не может превышать " + PL.npl(Group.MAX_NAME_LEN, " символ"));
    }


    public static boolean validate(@Nullable String value, AjaxRequestTarget target, @NotNull Component errorContainer, @Nullable Component componentToFocus) {
        String errorMessage = ValidatorUtils.validateRange(value,
                Group.MIN_NAME_LEN, Group.MAX_NAME_LEN,
                () -> "Мин. длина имени группы: " + PL.npl(Group.MIN_NAME_LEN, " символ"),
                () -> "Имя группы не может превышать " + PL.npl(Group.MAX_NAME_LEN, " символ"));
        if (errorMessage == null) {
            return true;
        }
        ParsleyUtils.addParsleyError(target, errorContainer, errorMessage);
        if (componentToFocus != null) {
            JsUtils.focus(target, componentToFocus);
        }
        return false;
    }
}
