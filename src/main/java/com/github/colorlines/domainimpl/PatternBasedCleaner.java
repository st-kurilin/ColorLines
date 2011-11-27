package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.AreaCleaner;
import com.github.colorlines.domain.Ball;
import com.github.colorlines.domain.Position;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.*;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Sets.*;

/**
 * @author Stanislav Kurilin
 */
@Named("areaCleanerTarget")
public class PatternBasedCleaner implements AreaCleaner {
    private final Collection<Cleaner> cleaners =
            ImmutableList.of(HORIZONTAL, VERTICAL, DIAGONAL, DIAGONAL_BACK);

    @Override
    public Set<Ball> clean(Area area) {
        final ImmutableSet.Builder<Ball> builder = ImmutableSet.builder();
        for (Cleaner cleaner : cleaners) {
            builder.addAll(cleaner.apply(area));
        }
        return builder.build();
    }

    interface Cleaner extends Function<Area, Set<Ball>> {
    }

    private static final int minBallsToDestroy = 5;

    private final static Cleaner HORIZONTAL = new Cleaner() {
        @Override
        public Set<Ball> apply(Area area) {
            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
            for (int y = Position.HEIGHT_RANGE.lowerEndpoint(); Position.HEIGHT_RANGE.contains(y); y++) {
                final LinkedList<Ball> candidate = newLinkedList();
                for (int x = Position.WIDTH_RANGE.lowerEndpoint(); Position.WIDTH_RANGE.contains(x); x++) {
                    Position curPosition = Position.create(x, y);
                    if(!area.contains(curPosition)){
                        if(candidate.size() >= minBallsToDestroy){
                            result.addAll(candidate);
                        }
                        candidate.clear();
                        continue;
                    }
                    final Ball ball = area.take(curPosition);
                    if(candidate.isEmpty() || candidate.getFirst().color().equals(ball.color())){
                        candidate.add(ball);
                    } else {
                        if(candidate.size() >= minBallsToDestroy){
                            result.addAll(candidate);
                        }
                        candidate.clear();
                    }
                }
                if(candidate.size() >= minBallsToDestroy){
                    result.addAll(candidate);
                }
                candidate.clear();
            }
            return result.build();
        }
    };
    private final static Cleaner VERTICAL = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
            for (int x = Position.WIDTH_RANGE.lowerEndpoint(); Position.WIDTH_RANGE.contains(x); x++) {
                final LinkedList<Ball> candidate = newLinkedList();
                for (int y = Position.HEIGHT_RANGE.lowerEndpoint(); Position.HEIGHT_RANGE.contains(y); y++) {
                    Position curPosition = Position.create(x, y);
                    if(!area.contains(curPosition)){
                        if(candidate.size() >= minBallsToDestroy){
                            result.addAll(candidate);
                        }
                        candidate.clear();
                        continue;
                    }
                    final Ball ball = area.take(curPosition);
                    if(candidate.isEmpty() || candidate.getFirst().color().equals(ball.color())){
                        candidate.add(ball);
                    } else {
                        if(candidate.size() >= minBallsToDestroy){
                            result.addAll(candidate);
                        }
                        candidate.clear();
                    }
                }
                if(candidate.size() >= minBallsToDestroy){
                    result.addAll(candidate);
                }
                candidate.clear();
            }
            return result.build();
        }
    };
    private final static Cleaner DIAGONAL = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
            for (int x = Position.WIDTH_RANGE.lowerEndpoint(); Position.WIDTH_RANGE.contains(x); x++) {
                final LinkedList<Ball> candidate = newLinkedList();
                for (int y = Position.HEIGHT_RANGE.lowerEndpoint(); Position.HEIGHT_RANGE.contains(y); y++) {
                    for(int i = 0 ; i < Math.max(Position.HEIGHT_RANGE.upperEndpoint(), Position.HEIGHT_RANGE.upperEndpoint()) ; i ++ ){
                        if(!Position.WIDTH_RANGE.contains(x + i) || !Position.HEIGHT_RANGE.contains(y + i) || !area.contains(Position.create(x + i, y + i))){
                            if(candidate.size() >= minBallsToDestroy){
                                result.addAll(candidate);
                            }
                            candidate.clear();
                            continue;
                        }
                        Position curPosition = Position.create(x + i, y + i);
                        final Ball ball = area.take(curPosition);
                        if(candidate.isEmpty() || candidate.getFirst().color().equals(ball.color())){
                            candidate.add(ball);
                        } else {
                            if(candidate.size() >= minBallsToDestroy){
                                result.addAll(candidate);
                            }
                            candidate.clear();
                        }
                    }
                }
                if(candidate.size() >= minBallsToDestroy){
                    result.addAll(candidate);
                }
                candidate.clear();
            }
            return result.build();
        }
    };
    private final static Cleaner DIAGONAL_BACK = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
            for (int x = Position.WIDTH_RANGE.lowerEndpoint(); Position.WIDTH_RANGE.contains(x); x++) {
                final LinkedList<Ball> candidate = newLinkedList();
                for (int y = Position.HEIGHT_RANGE.lowerEndpoint(); Position.HEIGHT_RANGE.contains(y); y++) {
                    for(int i = 0 ; i < Math.max(Position.HEIGHT_RANGE.upperEndpoint(), Position.HEIGHT_RANGE.upperEndpoint()) ; i ++ ){
                        if(!Position.WIDTH_RANGE.contains(x - i) || !Position.HEIGHT_RANGE.contains(y + i) || !area.contains(Position.create(x - i, y + i))){
                            if(candidate.size() >= minBallsToDestroy){
                                result.addAll(candidate);
                            }
                            candidate.clear();
                            continue;
                        }
                        Position curPosition = Position.create(x - i, y + i);
                        final Ball ball = area.take(curPosition);
                        if(candidate.isEmpty() || candidate.getFirst().color().equals(ball.color())){
                            candidate.add(ball);
                        } else {
                            if(candidate.size() >= minBallsToDestroy){
                                result.addAll(candidate);
                            }
                            candidate.clear();
                        }
                    }
                }
                if(candidate.size() >= minBallsToDestroy){
                    result.addAll(candidate);
                }
                candidate.clear();
            }
            return result.build();
        }
    };
}
