package com.github.zametki.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.Instant;

public class DateUtils {

    @Contract("null -> null")
    public static Instant optionalInstant(@Nullable Timestamp ts) {
        return ts == null ? null : ts.toInstant();
    }

}
