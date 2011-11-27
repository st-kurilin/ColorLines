package com.github.colorlines;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Color;
import com.github.colorlines.domain.Position;
import com.github.colorlines.domainimpl.SimpleBall;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import java.util.Collection;

/**
 * @author Stanislav Kurilin
 */
class ConstantArea implements Area {
    private final Collection<Ball> value;

    public ConstantArea(Collection<Ball> value) {
        this.value = value;
    }

    public static ConstantArea oneColoredBalls(Collection<Position> value) {
        return new ConstantArea(Collections2.transform(value, new Function<Position, Ball>() {
            @Override
            public Ball apply(Position input) {
                return new SimpleBall(Color.values()[0], input);
            }
        }));
    }

    @Override
    public boolean contains(final Position location) {
        return Iterables.any(value, new Predicate<Ball>() {
            @Override
            public boolean apply(Ball input) {
                return input.position().equals(location);
            }
        });
    }

    @Override
    public Ball take(Position location) {
        for (Ball each : value) if (each.position().equals(location)) return each;
        throw new IllegalStateException("Illegal location" + location);
    }
}
