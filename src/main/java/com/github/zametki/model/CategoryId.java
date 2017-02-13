package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;

public class CategoryId extends AbstractId {

    public static final CategoryId INVALID_ID = new CategoryId(0);

    public CategoryId(int value) {
        super(value);
    }

    @Mapper
    public static final DbMapper<CategoryId> MAPPER = r -> new CategoryId(r.getInt(1));
}

