package com.github.zametki.util;

import org.apache.wicket.util.cookies.CookieDefaults;

import java.time.Duration;

public class Cookies {

    private final static CookieDefaults NEVER_EXPIRE_COOKIE_DEFAULTS = new CookieDefaults();
    public final static CookieDefaults MONTH_1_COOKIE_DEFAULTS = new CookieDefaults();

    static {
        NEVER_EXPIRE_COOKIE_DEFAULTS.setMaxAge(Integer.MAX_VALUE);
        MONTH_1_COOKIE_DEFAULTS.setMaxAge((int) Duration.ofDays(31).getSeconds());
    }
}
