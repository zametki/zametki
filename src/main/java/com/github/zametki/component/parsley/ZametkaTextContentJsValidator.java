package com.github.zametki.component.parsley;

import com.github.zametki.model.Zametka;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class ZametkaTextContentJsValidator extends MinMaxJsValidator {

    private static final String MIN_MESSAGE = "Мин. длина заметки: " + PL.npl(Zametka.MIN_CONTENT_LEN, " символ");
    private static final String MAX_MESSAGE = "Имя заметки не может превышать " + PL.npl(Zametka.MAX_CONTENT_LEN, " символ");

    public ZametkaTextContentJsValidator(@Nullable Component errorContainer) {
        super(errorContainer, Zametka.MIN_CONTENT_LEN, Zametka.MAX_CONTENT_LEN, MIN_MESSAGE, MAX_MESSAGE);
    }
}