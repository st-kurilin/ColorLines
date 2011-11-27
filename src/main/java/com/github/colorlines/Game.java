package com.github.colorlines;

import com.github.colorlines.domain.*;
import com.github.colorlines.domainimpl.GameScope;
import com.google.common.base.Predicate;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Stanislav Kurilin
 */
public class Game {
    private final Area area;
    private final Player player;
    private final AreaCleaner areaCleaner;
    private final BallGenerator ballGenerator;
    private final TurnValidator validator;
    private final Predicate<Area> canContinue;
    private final GameScope scope;

    @Inject
    public Game(Predicate<Area> canContinue, TurnValidator validator, BallGenerator ballGenerator,
                AreaCleaner areaCleaner, Player player, Area area, GameScope scope) {
        this.canContinue = canContinue;
        this.validator = validator;
        this.ballGenerator = ballGenerator;
        this.areaCleaner = areaCleaner;
        this.player = player;
        this.area = area;
        this.scope = scope;
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

    private void addBalls(Set<Ball> generate) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
    }

    private boolean removeLinesIfTheyExist() {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
    }

    private void applyTurn(Turn turn) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
    }
}
