package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.*;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;

import javax.inject.Named;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static com.github.colorlines.domain.Position.*;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.ImmutableList.of;

/**
 * @author Stanislav Kurilin, Alex Lenkevich
 */
@Named("areaCleanerTarget")
public class PatternBasedCleaner implements AreaCleaner {
    private final Collection<Cleaner> cleaners = transform(of(new Function<Position, UnmodifiableIterator<Position>>() {
                @Override
                public UnmodifiableIterator<Position> apply(final Position position) {
                    return new UnmodifiableIterator<Position>() {
                        Position curent = position;

                        @Override
                        public boolean hasNext() {
                            return X_VALUES.contains(curent.getX() + 1);
                        }

                        @Override
                        public Position next() {
                            return curent = create(curent.getX() + 1, curent.getY());
                        }
                    };
                }
            }, new Function<Position, UnmodifiableIterator<Position>>() {
                @Override
                public UnmodifiableIterator<Position> apply(final Position position) {
                    return new UnmodifiableIterator<Position>() {
                        Position curent = position;

                        @Override
                        public boolean hasNext() {
                            return Y_VALUES.contains(curent.getY() + 1);
                        }

                        @Override
                        public Position next() {
                            return curent = create(curent.getX(), curent.getY() + 1);
                        }
                    };
                }
            }, new Function<Position, UnmodifiableIterator<Position>>() {
                @Override
                public UnmodifiableIterator<Position> apply(final Position position) {
                    return new UnmodifiableIterator<Position>() {
                        Position curent = position;

                        @Override
                        public boolean hasNext() {
                            return X_VALUES.contains(curent.getX() + 1) && Y_VALUES.contains(curent.getY() + 1);
                        }

                        @Override
                        public Position next() {
                            return curent = create(curent.getX() + 1, curent.getY() + 1);
                        }
                    };
                }
            }, new Function<Position, UnmodifiableIterator<Position>>() {
        @Override
        public UnmodifiableIterator<Position> apply(final Position position) {
            return new UnmodifiableIterator<Position>() {
                Position curent = position;

                @Override
                public boolean hasNext() {
                    return X_VALUES.contains(curent.getX() - 1) && Y_VALUES.contains(curent.getY() + 1);
                }

                @Override
                public Position next() {
                    return curent = create(curent.getX() - 1, curent.getY() + 1);
                }
            };
        }
    }
    ), new Function<Function<Position, UnmodifiableIterator<Position>>, Cleaner>() {
        @Override
        public Cleaner apply(final Function<Position, UnmodifiableIterator<Position>> input) {
            return new Cleaner(new IteratorFactory() {
                @Override
                public UnmodifiableIterator<Position> iterator(Position init) {
                    return input.apply(init);
                }
            });
        }
    });

    @Override
    public Set<Ball> clean(Area area) {
        final ImmutableSet.Builder<Ball> builder = ImmutableSet.builder();
        for (Cleaner cleaner : cleaners) {
            builder.addAll(cleaner.apply(area));
        }
        return builder.build();
    }


    interface IteratorFactory {
        UnmodifiableIterator<Position> iterator(Position init);
    }

    private static class Cleaner implements Function<Area, Set<Ball>> {
        final IteratorFactory iteratorFactory;

        private Cleaner(IteratorFactory iteratorFactory) {
            this.iteratorFactory = iteratorFactory;
        }

        @Override
        public Set<Ball> apply(Area area) {
            final ImmutableSet.Builder<Ball> result = ImmutableSet.builder();
            for (Position init : Position.ALL) {
                if (!area.contains(init)) continue;
                final Iterator<Position> it = iteratorFactory.iterator(init);
                final ImmutableSet.Builder<Ball> builder = ImmutableSet.builder();
                builder.add(area.take(init));
                final Color color = area.take(init).color();
                while (it.hasNext()) {
                    final Position next = it.next();
                    if (!area.contains(next) || area.take(next).color() != color) break;
                    builder.add(area.take(next));
                }
                final ImmutableSet<Ball> series = builder.build();
                if (series.size() >= 5) result.addAll(series);
            }
            return result.build();
        }
    }
}
