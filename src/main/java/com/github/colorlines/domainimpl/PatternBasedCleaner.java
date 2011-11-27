package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.AreaCleaner;
import com.github.colorlines.domain.Ball;

import javax.inject.Named;
import java.util.Set;

/**
 * @author Stanislav Kurilin
 */
@Named("areaCleanerTarget")
public class PatternBasedCleaner implements AreaCleaner {
    @Override
    public Set<Ball> clean(Area area) {
        throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
    }
}
