package com.github.zametki.util;

import com.github.zametki.Context;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;

public class CategoryUtils {

    /**
     * TODO: select real default.
     */
    @NotNull
    public static CategoryId getDefaultCategoryForUser(@NotNull UserId userId) {
        return Context.getCategoryDbi().getByUser(userId).stream()
                .sorted()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No category found: " + userId));
    }
}
