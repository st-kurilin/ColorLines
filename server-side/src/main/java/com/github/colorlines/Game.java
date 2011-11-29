package com.github.colorlines;

import com.github.colorlines.domain.*;
import com.github.colorlines.domainimpl.GameScope;
import com.github.colorlines.domainimpl.MutableArea;
import com.github.colorlines.domainimpl.SimpleBall;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.of;

/**
 * @author Stanislav Kurilin
 */
public class Game {
    private final MutableArea area;
    private final Player player;
    private final AreaCleaner areaCleaner;
    private final BallGenerator ballGenerator;
    private final TurnValidator validator;
    private final Predicate<Area> canContinue;
    private final GameScope scope;

    @Inject
    public Game(@Named("gameCanBeContinuePredicate") Predicate<Area> canContinue,
                TurnValidator validator, BallGenerator ballGenerator,
                Player player, MutableArea area, GameScope scope, AreaCleaner areaCleaner) {
        this.canContinue = canContinue;
        this.validator = validator;
        this.ballGenerator = ballGenerator;
        this.player = player;
        this.area = area;
        this.scope = scope;
        this.areaCleaner = areaCleaner;
    }

    public void play() {
        scope.reset();
        addBalls(ballGenerator.generate());
        while (canContinue.apply(area)) {
            final Turn turn = player.turn(area, validator);
            if (!validator.isValid(area, turn)) {
                if (area.contains(turn.moveTo()) || area.take(turn.original().position()) == turn.original()) {
                    throw new IllegalStateException("Illegal turn");
                }
            }
            applyTurn(turn);
            final boolean removed = removeLinesIfTheyExist();
            if (!removed) {
                addBalls(ballGenerator.generate());
            }
        }
        scope.reset();
    }

    private void addBalls(Set<Ball> generated) {
        area.addBalls(generated);
    }

    private boolean removeLinesIfTheyExist() {
        final Set<Ball> removed = areaCleaner.clean(area);
        area.removeBalls(removed);
        return !removed.isEmpty();
    }

    private void applyTurn(final Turn turn) {
        final Ball original = turn.original();
        area.removeBalls(of(original));
        area.addBalls(ImmutableSet.<Ball>of(new SimpleBall(original.color(), turn.moveTo())));

    }
}
