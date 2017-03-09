package com.github.zametki.component.parsley;

import com.github.zametki.model.Group;
import com.github.zametki.util.PL;
import org.apache.wicket.Component;
import org.jetbrains.annotations.Nullable;

public class GroupNameJsValidator extends MinMaxJsValidator {

    public static final String MIN_ERROR = "Мин. длина имени группы: " + PL.npl(Group.MIN_NAME_LEN, " символ");
    public static final String MAX_ERROR = "Имя группы не может превышать " + PL.npl(Group.MAX_NAME_LEN, " символ");

    public GroupNameJsValidator(@Nullable Component errorContainer) {
        super(errorContainer, Group.MIN_NAME_LEN, Group.MAX_NAME_LEN, MIN_ERROR, MAX_ERROR);
    }
}
