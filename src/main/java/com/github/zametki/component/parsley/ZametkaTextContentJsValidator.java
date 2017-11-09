package com.github.zametki.component.parsley;

import com.github.zametki.model.Zametka;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class ZametkaTextContentJsValidator extends MinMaxJsValidator {

    private static final String MIN_MESSAGE = "Мин. длина заметки: " + PL.npl(Zametka.Companion.getMIN_CONTENT_LEN(), " символ");
    private static final String MAX_MESSAGE = "Имя заметки не может превышать " + PL.npl(Zametka.Companion.getMAX_CONTENT_LEN(), " символ");

    public ZametkaTextContentJsValidator(@Nullable Component errorContainer) {
        super(errorContainer, Zametka.Companion.getMIN_CONTENT_LEN(), Zametka.Companion.getMAX_CONTENT_LEN(), MIN_MESSAGE, MAX_MESSAGE);
    }
}