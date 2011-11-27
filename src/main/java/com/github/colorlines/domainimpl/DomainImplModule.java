package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import javax.inject.Singleton;

/**
 * @author Stanislav Kurilin
 */
public class DomainImplModule extends AbstractModule {
    @Override
    protected void configure() {
        bindScope(GameScoped.class, new GameScope());
        bind(AreaCleaner.class).to(CleanHistorer.class);
        bind(BallGenerator.class).to(RandomBallGenerator.class);
        bindConstant().annotatedWith(Names.named("ballsGenerateByOneTime")).to(3);
        bind(AreaCleaner.class).annotatedWith(Names.named("areaCleanerTarget")).to(PatternBasedCleaner.class);
        bind(TurnValidator.class).toInstance(new TurnValidator() {
            @Override
            public boolean isValid(Area area, Turn turn) {
                return true;
            }
        });
        bind(Player.class).toInstance(new Player() {
            @Override
            public Turn turn(Area area, TurnValidator validator) {
                throw new UnsupportedOperationException("Not implemented yet"); //TODO: [stas]: implement
            }
        });
    }

    @Provides
    @Singleton
    Area area(MutableArea area) {
        return area;
    }
}
