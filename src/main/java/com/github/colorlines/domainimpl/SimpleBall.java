package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Color;
import com.github.colorlines.domain.Position;

/**
 * @author Stanislav Kurilin
 */
final class SimpleBall implements Ball {
    private final Color color;
    private final Position position;

    SimpleBall(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public Position position() {
        return position;
    }
}
