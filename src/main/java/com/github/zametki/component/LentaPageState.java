package com.github.zametki.component;

import com.github.zametki.model.CategoryId;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.Nullable;

public class LentaPageState implements IClusterable {
    @Nullable
    public CategoryId selectedCategoryId = null;
}
