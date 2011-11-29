package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Position;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;

/**
 * @author Stanislav Kurilin
 */
@Singleton
public class MutableArea implements Area {
    private final Set<Ball> balls = newHashSet();

    private Map<Position, Ball> ballsByPosition() {
        return Maps.uniqueIndex(balls, new Function<Ball, Position>() {
            @Override
            public Position apply(Ball input) {
                return input.position();
            }
        });
    }

    private final String TAKEN_WITH_WRONG_POSITION =
            "Can't deliver ball from specified location: %s Please make sure that ball exist using contains method";

    @Override
    public boolean contains(Position location) {
        return ballsByPosition().containsKey(location);
    }

    @Override
    public Ball take(Position location) {
        checkState(ballsByPosition().containsKey(location), format(TAKEN_WITH_WRONG_POSITION, location));
        return ballsByPosition().get(location);
    }

    public void addBalls(Set<Ball> added) {
        balls.addAll(added);
    }

    public void removeBalls(Set<Ball> toRemove) {
        balls.removeAll(toRemove);
    }
}
