package com.github.zametki.util;

import com.github.zametki.Constants;
import org.apache.commons.lang3.time.FastDateFormat;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ZDateFormat {

    @NotNull
    private final FastDateFormat df;
    private final boolean fixMonthNames;

    private ZDateFormat(@NotNull String format, @NotNull TimeZone timeZone, @NotNull Locale locale) {
        df = FastDateFormat.getInstance(format, timeZone, locale);
        fixMonthNames = format.contains("MMM");
    }


    @NotNull
    public static ZDateFormat getInstance(@NotNull String format, @NotNull TimeZone timeZone) {
        return getInstance(format, timeZone, Constants.RUSSIAN_LOCALE);
    }

    @NotNull
    public static ZDateFormat getInstance(@NotNull String format, @NotNull TimeZone timeZone, @NotNull Locale locale) {
        return new ZDateFormat(format, timeZone, locale);
    }

    @NotNull
    public String format(@NotNull Instant date) {
        String res = df.format(new Date(date.toEpochMilli()));
        return fixMonthNames ? res.replace("май", "мая") : res;
    }

    @SuppressWarnings("unused")
    @NotNull
    public Instant parse(@NotNull String val) {
        try {
            return df.parse(val).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
