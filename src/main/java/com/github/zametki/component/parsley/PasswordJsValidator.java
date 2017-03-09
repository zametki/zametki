package com.github.zametki.component.parsley;


import com.github.zametki.model.User;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class PasswordJsValidator extends MinMaxJsValidator {

    public static final String MIN_ERROR = "Мин. длина пароля: " + PL.npl(User.PASSWORD_MIN_LENGTH, " символ");
    public static final String MAX_ERROR = "Пароль не может превышать " + PL.npl(User.PASSWORD_MAX_LENGTH, " символ");

    public PasswordJsValidator(@Nullable Component errorContainer) {
        super(errorContainer, User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH, MIN_ERROR, MAX_ERROR);
    }
}
