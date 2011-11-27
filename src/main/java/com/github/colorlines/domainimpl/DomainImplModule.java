package com.github.colorlines.domainimpl;

import com.github.colorlines.Game;
import com.github.colorlines.consoleplayer.PlayerImpl;
import com.github.colorlines.domain.*;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
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
        bind(Player.class).to(PlayerImpl.class);
    }

    @Provides
    @Singleton
    Area area(MutableArea area) {
        return area;
    }

    public static void main(String[] args) {
        Guice.createInjector(new DomainImplModule()).getInstance(Game.class).play();
    }
}
