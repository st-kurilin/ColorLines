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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

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

    private final static Cleaner HORIZONTAL = new Cleaner() {
        @Override
        public Set<Ball> apply(Area area) {
//            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
//            for (int x = Position.WIDTH_RANGE.lowerEndpoint(); Position.WIDTH_RANGE.contains(x); x++) {
//                final HashSet<Object> candidate = newHashSet();
//                for (int y = Position.HEIGHT_RANGE.lowerEndpoint(); Position.HEIGHT_RANGE.contains(y); y++) {
//                    final Position position = Position.create(x, y);
//                    if (!area.contains(position)) ;
//                    final Ball ball = area.take(position);
//
//                    if(candidate.isEmpty() || Iterables.getFirst(candidate))
//                }
//            }
//            return result.build();
            throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
        }
    };
    private final static Cleaner VERTICAL = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
        }
    };
    private final static Cleaner DIAGONAL = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
        }
    };
    private final static Cleaner DIAGONAL_BACK = new Cleaner() {
        @Override
        public Set<Ball> apply(@Nullable Area area) {
            throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
        }
    };
}
