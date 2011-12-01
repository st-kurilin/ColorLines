package com.github.colorlines.domain;

import com.google.common.collect.Iterables;
import com.google.common.collect.Range;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.DiscreteDomains.integers;
import static com.google.common.collect.Ranges.closed;

/**
 * @author Stanislav Kurilin
 */
public final class Position {
    @Deprecated //use iterator
    public static Range<Integer> WIDTH_RANGE = closed(0, 8);
    @Deprecated //use iterator
    public static Range<Integer> HEIGHT_RANGE = closed(0, 8);

    public static Set<Integer> X_VALUES = closed(0, 8).asSet(integers());
    public static Set<Integer> Y_VALUES = closed(0, 8).asSet(integers());

    private final int x, y;

    public static Position create(int x, int y) {
        checkArgument(Iterables.contains(X_VALUES, x));
        checkArgument(Iterables.contains(Y_VALUES, y));
        return new Position(x, y);
    }

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Position position = (Position) other;
        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
