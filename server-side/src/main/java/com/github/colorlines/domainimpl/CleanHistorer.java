package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.AreaCleaner;
import com.github.colorlines.domain.Ball;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Stanislav Kurilin
 */

@Named("areaCleanerWrapper")
@Singleton
public class CleanHistorer implements AreaCleaner {
    private final AreaCleaner cleaner;
    private final List<Integer> history = newArrayList();

    @Inject
    public CleanHistorer(@Named("areaCleanerTarget") AreaCleaner cleaner) {
        this.cleaner = cleaner;
    }

    @Override
    public Set<Ball> clean(Area area) {
        final Set<Ball> balls = cleaner.clean(area);
        history.add(balls.size());
        return balls;
    }

    public List<Integer> getHistory() {
        return history;
    }
}
