package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;

public class ZametkaId extends AbstractId {
    public ZametkaId(int value) {
        super(value);
    }

    @Mapper
    public static final DbMapper<ZametkaId> MAPPER = r -> new ZametkaId(r.getInt(1));
}
