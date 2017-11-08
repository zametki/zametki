package com.github.zametki.model;

import com.github.mjdbc.type.DbInt;
import org.apache.wicket.util.io.IClusterable;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for all Ids. Serializable DB value.
 */
public class AbstractId implements DbInt, IClusterable, Comparable<AbstractId> {
    public final int intValue;

    public AbstractId(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public int getDbValue() {
        return intValue;
    }


    public int hashCode() {
        return intValue;
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
        return intValue == that.intValue;
    }

    public String toString() {
        return getClass().getSimpleName() + "[" + intValue + "]";
    }

    public boolean isValid() {
        return intValue > 0;
    }
    public boolean isInvalid() {
        return !isValid();
    }

    public boolean isRoot() {
        return !isValid();
    }
    @Override
    public int compareTo(@NotNull AbstractId o) {
        return Integer.compare(intValue, o.intValue);
    }
}
