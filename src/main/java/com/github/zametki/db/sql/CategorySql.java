package com.github.zametki.db.sql;

import com.github.mjdbc.Bind;
import com.github.mjdbc.BindBean;
import com.github.mjdbc.Sql;
import com.github.zametki.model.Category;
import com.github.zametki.model.CategoryId;
import com.github.zametki.model.UserId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Set of 'category' table queries
 */
public interface CategorySql {

    @NotNull
    @Sql("INSERT INTO category (user_id, title) VALUES (:userId, :title)")
    CategoryId insert(@BindBean Category category);

    @Nullable
    @Sql("SELECT * FROM category WHERE id = :id")
    Category getById(@Bind("id") CategoryId id);

    @NotNull
    @Sql("SELECT id FROM category WHERE user_id = :userId ORDER BY title")
    List<CategoryId> getByUser(@Bind("userId") UserId userId);

    @Sql("DELETE FROM category WHERE id = :id")
    void delete(@Bind("id") CategoryId id);

    @Sql("UPDATE category SET title = :title WHERE id = :id")
    void update(@NotNull @BindBean Category c);
}
