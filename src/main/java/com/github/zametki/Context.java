package com.github.zametki;

import com.github.mjdbc.Binders;
import com.github.mjdbc.Db;
import com.github.zametki.db.cache.CategoryCache;
import com.github.zametki.db.cache.ZametkaCache;
import com.github.zametki.db.dbi.CategoryDbi;
import com.github.zametki.db.dbi.UsersDbi;
import com.github.zametki.db.dbi.ZametkaDbi;
import com.github.zametki.db.dbi.impl.CategoryDbiImpl;
import com.github.zametki.db.dbi.impl.UsersDbiImpl;
import com.github.zametki.db.dbi.impl.ZametkaDbiImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;

public class Context {
    private static final Logger log = LoggerFactory.getLogger(Context.class);

    private static HikariDataSource ds;
    private static UsersDbi usersDbi;
    private static ZametkaDbi zametkaDbi;
    private static CategoryDbi categoryDbi;

    private static Properties prodConfig = new Properties();

    private static final CategoryCache categoryCache = new CategoryCache();
    private static final ZametkaCache zametkaCache = new ZametkaCache();

    public static void init() {
        try {
            if (isProduction()) {
                prodConfig.load(new FileInputStream("/opt/zametki/service.properties"));
            }
            ds = new HikariDataSource(prepareDbConfig("/hikari.properties"));
            Db db = Db.newInstance(ds);
            registerBuiltInTypes(db);
            usersDbi = db.attachDbi(new UsersDbiImpl(db), UsersDbi.class);
            zametkaDbi = db.attachDbi(new ZametkaDbiImpl(db), ZametkaDbi.class);
            categoryDbi = db.attachDbi(new CategoryDbiImpl(db), CategoryDbi.class);
        } catch (Exception e) {
            log.error("", e);
            shutdown();
            throw new RuntimeException(e);
        }
    }

    private static void registerBuiltInTypes(@NotNull Db db) {
        db.registerBinder(Instant.class, (statement, idx, value) -> Binders.TimestampBinder.bind(statement, idx, value == null ? null : new Timestamp(value.toEpochMilli())));
        db.registerMapper(Instant.class, r -> {
            Timestamp v = r.getTimestamp(1);
            return v == null ? null : v.toInstant();
        });
    }

    public static void shutdown() {
        close(ds);
    }

    private static void close(Closeable c) {
        try {
            c.close();
        } catch (Exception ignored) {
        }
    }

    public static UsersDbi getUsersDbi() {
        return usersDbi;
    }

    public static ZametkaDbi getZametkaDbi() {
        return zametkaDbi;
    }

    public static CategoryDbi getCategoryDbi() {
        return categoryDbi;
    }

    public static CategoryCache getCategoryCache() {
        return categoryCache;
    }

    public static ZametkaCache getZametkaCache() {
        return zametkaCache;
    }

    public static boolean isProduction() {
        return System.getProperty("online.zametki.production") != null;
    }

    public static String getBaseUrl() {
        return isProduction() ? "https://zametki.online" : "http://localhost:8080";
    }

    public static Properties getProdConfig() {
        return prodConfig;
    }

    @NotNull
    private static HikariConfig prepareDbConfig(@NotNull String resource) {
        HikariConfig dbConfig = new HikariConfig(resource);
        dbConfig.addDataSourceProperty("cachePrepStmts", "true");
        dbConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        dbConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dbConfig.addDataSourceProperty("useServerPrepStmts", "true");
        return dbConfig;
    }

}
