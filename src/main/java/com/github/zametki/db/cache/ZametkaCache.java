package com.github.zametki.db.cache;

import com.github.zametki.model.UserId;
import com.github.zametki.model.Zametka;
import com.github.zametki.model.ZametkaId;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ZametkaCache {

    private final Map<ZametkaId, Zametka> zametkaById = new ConcurrentHashMap<>();

    private final Map<UserId, List<ZametkaId>> zametkaByUser = new ConcurrentHashMap<>();

    public Zametka getById(@NotNull ZametkaId zametkaId, @NotNull Function<ZametkaId, Zametka> loader) {
        return zametkaById.computeIfAbsent(zametkaId, loader);
    }

    public void update(@NotNull Zametka zametka) {
        zametkaById.put(zametka.id, zametka);
    }

    public void remove(@NotNull ZametkaId zametkaId) {
        zametkaById.remove(zametkaId);
    }

    public List<ZametkaId> getByUser(@NotNull UserId userId, @NotNull Function<UserId, List<ZametkaId>> loader) {
        return zametkaByUser.computeIfAbsent(userId, id -> Collections.unmodifiableList(loader.apply(id)));
    }

    public void remove(@NotNull UserId userId) {
        zametkaByUser.remove(userId);
    }
}
