package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;
import org.jetbrains.annotations.NotNull;

public class Category extends Identifiable<CategoryId> {

    public static final String WITHOUT_CATEGORY_TITLE = "Без категории";
    public static final int MIN_TITLE_LEN = 1;
    public static final int MAX_TITLE_LEN = 48;

    @NotNull
    public UserId userId = UserId.INVALID_ID;

    @NotNull
    public String title = "";

    @Mapper
    public static final DbMapper<Category> MAPPER = r -> {
        Category res = new Category();
        res.id = new CategoryId(r.getInt("id"));
        res.userId = new UserId(r.getInt("user_id"));
        res.title = r.getString("title");
        return res;
    };


    @Override
    public String toString() {
        return "Cat[" + (id == null ? "?" : "" + id.value) + "|" + title + "]";
    }

}
