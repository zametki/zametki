package com.github.zametki.model;

import com.github.mjdbc.type.DbInt;

public enum ZType implements DbInt {
    PLAIN_TEXT(0);

    public final int id;

    ZType(int id) {
        this.id = id;
    }


    @Override
    public int getDbValue() {
        return 0;
    }

    public static ZType fromDbValue(int id) {
        if (id != 0) {
            throw new IllegalArgumentException("Unsupported content type: " + id);
        }
        return PLAIN_TEXT;
    }
}
