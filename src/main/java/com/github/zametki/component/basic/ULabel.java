package com.github.zametki.component.basic;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;

public class ULabel extends Label {

    public ULabel(@NotNull String id, @NotNull String value) {
        this(id, Model.of(value));
    }

    public ULabel(@NotNull String id, @NotNull IModel<String> model) {
        super(id, model);
        setEscapeModelStrings(false);
    }
}
