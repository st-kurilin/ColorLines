package com.github.colorlines.consoleplayer;

import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Position;
import com.github.colorlines.domain.Turn;

/**
 * Created by IntelliJ IDEA.
 * User: Alex Lenkevich
 * Date: 27.11.11
 * Time: 15:13
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
