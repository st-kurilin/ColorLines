package com.github.colorlines.domain;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

/**
 * @author Stanislav Kurilin
 */
public final class Position {
    public static Range<Integer> WIDTH_RANGE = Ranges.closed(0, 9);
    public static Range<Integer> HEIGHT_RANGE = Ranges.closed(0, 9);

    private final int x, y;

    public Position(int y, int x) {
        this.y = y;
        this.x = x;
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
