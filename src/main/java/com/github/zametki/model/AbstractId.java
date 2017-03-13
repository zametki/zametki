package com.github.zametki.model;

import com.github.mjdbc.type.DbInt;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for all Ids. Serializable DB value.
 */
public class AbstractId implements DbInt, IClusterable, Comparable<AbstractId> {
    protected final int value;

    public AbstractId(int value) {
        this.value = value;
    }

    @Override
    public int getDbValue() {
        return value;
    }


    public int hashCode() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractId that = (AbstractId) o;
        return value == that.value;
    }

    public String toString() {
        return getClass().getSimpleName() + "[" + value + "]";
    }

    public boolean isValid() {
        return value > 0;
    }

    public boolean isRoot() {
        return !isValid();
    }
    @Override
    public int compareTo(@NotNull AbstractId o) {
        return Integer.compare(value, o.value);
    }
}
