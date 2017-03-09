package com.github.zametki.component.parsley;

import com.github.zametki.util.JsUtils;
import com.github.zametki.util.ValidatorUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinMaxJsValidator extends RequiredFieldJsValidator {

    private final int min;
    private final int max;
    @NotNull
    private final String minMessage;
    @NotNull
    private final String maxMessage;

    public MinMaxJsValidator(@Nullable Component errorContainer, int min, int max, @NotNull String minMessage, @NotNull String maxMessage) {
        super(errorContainer);
        this.min = min;
        this.max = max;
        this.minMessage = minMessage;
        this.maxMessage = maxMessage;

        attributeMap.put("data-parsley-minlength", min);
        attributeMap.put("data-parsley-maxlength", max);
        attributeMap.put("data-parsley-minlength-message", minMessage);
        attributeMap.put("data-parsley-maxlength-message", maxMessage);
    }

    public boolean validate(@Nullable String value, AjaxRequestTarget target, @Nullable Component componentToFocus) {
        return validate(value, target, errorContainer, componentToFocus);
    }

    public boolean validate(@Nullable String value, AjaxRequestTarget target, @Nullable Component errorContainer, @Nullable Component componentToFocus) {
        String errorMessage = ValidatorUtils.validateRange(value, min, max, minMessage, maxMessage);
        if (errorMessage == null) {
            return true;
        }
        if (errorContainer != null) {
            ParsleyUtils.addParsleyError(target, errorContainer, errorMessage);
        }
        if (componentToFocus != null) {
            JsUtils.focus(target, componentToFocus);
        }
        return false;
    }
}