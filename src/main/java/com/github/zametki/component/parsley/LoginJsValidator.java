package com.github.zametki.component.parsley;

import com.github.zametki.model.User;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class LoginJsValidator extends RequiredFieldJsValidator {

    public LoginJsValidator(@Nullable Component errorContainer) {
        super(errorContainer);
        attributeMap.put("data-parsley-minlength", User.LOGIN_MIN_LENGTH);
        attributeMap.put("data-parsley-maxlength", User.LOGIN_MAX_LENGTH);
        attributeMap.put("data-parsley-minlength-message", "Мин. длина имени пользователя: " + PL.npl(User.LOGIN_MIN_LENGTH, " символ"));
        attributeMap.put("data-parsley-maxlength-message", "Имя пользователя не может превышать " + PL.npl(User.LOGIN_MAX_LENGTH, " символ"));
    }
}
