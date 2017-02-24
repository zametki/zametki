package com.github.zametki.component;

import com.github.zametki.model.CategoryId;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

public class LentaPageState implements IClusterable {

    @NotNull
    public final IModel<CategoryId> activeCategory = Model.of();

}