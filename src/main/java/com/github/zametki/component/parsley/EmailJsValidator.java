package com.github.zametki.component.parsley;


import com.github.zametki.model.User;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class EmailJsValidator extends RequiredFieldJsValidator {

    public EmailJsValidator(@Nullable Component errorContainer) {
        super(errorContainer);

        attributeMap.put("data-parsley-type", "email");
        attributeMap.put("data-parsley-maxlength", User.EMAIL_MAX_LENGTH);
        attributeMap.put("data-parsley-maxlength-message", "Максимально допустимая длина email: " + PL.npl(User.EMAIL_MAX_LENGTH, " символ"));
    }
}
