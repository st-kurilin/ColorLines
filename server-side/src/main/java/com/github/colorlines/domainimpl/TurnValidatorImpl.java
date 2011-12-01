package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.Position;
import com.github.colorlines.domain.Turn;
import com.github.colorlines.domain.TurnValidator;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Sets.filter;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Math.ceil;

/**
 * @author Stanislav Kurilin
 */
public class TurnValidatorImpl implements TurnValidator {
    @Override
    public boolean isValid(Area area, Turn turn) {
        if (!area.contains(turn.original().position())) {
            return false;
        }
        if (area.contains(turn.moveTo())) {
            return false;
        }

        final PriorityQueue<PathStep> pathSteps = new PriorityQueue<PathStep>(
                (int) ceil(distance(turn.moveTo(), turn.original().position())), predictor(turn.moveTo()));
        final Set<Position> visited = newHashSet();
        pathSteps.add(new PathStep(0, turn.original().position()));
        do {
            final PathStep current = pathSteps.poll();
            if (current.current.equals(turn.moveTo())) {
                return true;
            }
            final Set<Position> validNextSteps =
                    filter(neiborhoods(current.current), and(empty(area), nonVisited(visited)));
            for (Position each : validNextSteps) {
                pathSteps.add(new PathStep(current.stepsDone + 1, each));
            }
            visited.addAll(validNextSteps);
        } while (!pathSteps.isEmpty());

        return false;
    }

    private Predicate<Position> empty(final Area area) {
        return new Predicate<Position>() {
            @Override
            public boolean apply(@Nullable Position input) {
                return !area.contains(input);
            }
        };
    }

    private Predicate<Position> nonVisited(final Set<Position> visited) {
        return new Predicate<Position>() {
            @Override
            public boolean apply(@Nullable Position input) {
                return !visited.contains(input);
            }
        };
    }

    private static double distance(Position left, Position right) {
        return Math.abs(left.getX() - right.getX()) + Math.abs(left.getY() - right.getY());
    }

    private static Comparator<PathStep> predictor(final Position target) {
        return new Comparator<PathStep>() {
            @Override
            public int compare(PathStep left, PathStep right) {
                return Double.compare(left.stepsDone + distance(left.current, target),
                        right.stepsDone + distance(right.current, target));
            }
        };
    }

    private Set<Position> neiborhoods(final Position left) {
        return filter(Position.ALL, new Predicate<Position>() {
            @Override
            public boolean apply(@Nullable Position o) {
                return distance(o, left) == 1;
            }
        });
    }

    private static class PathStep {
        final int stepsDone;
        final Position current;

        private PathStep(int stepsDone, Position current) {
            this.stepsDone = stepsDone;
            this.current = current;
        }
    }
}
