package com.github.colorlines;

import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Position;
import com.github.colorlines.domain.Turn;

/**
 * @author Stanislav Kurilin
 */
class TurnImpl implements Turn {
    private final Ball ball;
    private final Position position;

    public TurnImpl(Ball ball, Position position) {
        this.ball = ball;
        this.position = position;
    }

    public Ball original() {
        return ball;
    }

    public Position moveTo() {
        return position;
    }
}
