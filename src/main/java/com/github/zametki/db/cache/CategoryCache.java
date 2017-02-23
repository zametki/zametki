package com.github.zametki.db.cache;

import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CategoryCache {

    private final Map<CategoryId, Category> categoryById = new ConcurrentHashMap<>();

    private final Map<UserId, List<CategoryId>> categoriesByUser = new ConcurrentHashMap<>();

    public Category getById(@NotNull CategoryId categoryId, @NotNull Function<CategoryId, Category> loader) {
        return categoryById.computeIfAbsent(categoryId, loader);
    }

    public void update(@NotNull Category category) {
        categoryById.put(category.id, category);
    }

    public void remove(@NotNull CategoryId categoryId) {
        categoryById.remove(categoryId);
    }

    public List<CategoryId> getByUser(@NotNull UserId userId, @NotNull Function<UserId, List<CategoryId>> loader) {
        return categoriesByUser.computeIfAbsent(userId, id -> Collections.unmodifiableList(loader.apply(id)));
    }

    public void remove(@NotNull UserId userId) {
        categoriesByUser.remove(userId);
    }
}
