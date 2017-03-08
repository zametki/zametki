package com.github.zametki.model;

import com.github.mjdbc.DbMapper;
import com.github.mjdbc.Mapper;

public class GroupId extends AbstractId {

    public static final GroupId INVALID_ID = new GroupId(0);

    public GroupId(int value) {
        super(value);
    }

    @Mapper
    public static final DbMapper<GroupId> MAPPER = r -> new GroupId(r.getInt(1));
}

