package com.github.colorlines.domain;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Stanislav Kurilin
 */
public final class Position {
    public static Range<Integer> WIDTH_RANGE = Ranges.closed(0, 8);
    public static Range<Integer> HEIGHT_RANGE = Ranges.closed(0, 8);

    private final int x, y;

    public static Position create(int x, int y) {
        checkArgument(WIDTH_RANGE.contains(x));
        checkArgument(HEIGHT_RANGE.contains(y));
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
