package com.github.zametki.db.dbi;

import com.github.mjdbc.Db;
import com.github.zametki.model.AbstractId;
import com.github.zametki.util.LazyValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for all Dbi implementations.
 */
public class AbstractDbi {
    @NotNull
    protected final Db db;

    public AbstractDbi(@NotNull Db db) {
        this.db = db;
    }

    /**
     * Helper methods used in Dbi implementations to assert about some state.
     * Placed here because not used anywhere else in the code.
     */
    public static void assertTrue(boolean v, @NotNull LazyValue<String> checkName) {
        if (!v) {
            throw new IllegalStateException(checkName.get());
        }
    }

    @Contract("null -> true")
    public static boolean isInvalid(@Nullable AbstractId id) {
        return id == null || !id.isValid();
    }
}
