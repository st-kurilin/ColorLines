package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.AreaCleaner;
import com.github.colorlines.domain.BallGenerator;
import com.github.colorlines.domain.TurnValidator;
import com.google.common.base.Predicate;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * @author Stanislav Kurilin
 */
public class DomainImplModule extends AbstractModule {
    @Override
    protected void configure() {
        bindScope(GameScoped.class, new GameScope());
        bind(AreaCleaner.class).to(CleanHistorer.class);
        bind(AreaCleaner.class).annotatedWith(Names.named("areaCleanerTarget")).to(PatternBasedCleaner.class);

        bind(BallGenerator.class).to(RandomBallGenerator.class);
        bindConstant().annotatedWith(Names.named("ballsGenerateByOneTime")).to(3);

        bind(TurnValidator.class).to(TurnValidatorImpl.class);
        bind(new TypeLiteral<Predicate<Area>>() {
        }).annotatedWith(Names.named("gameCanBeContinuePredicate")).to(GameCanBeContinuePredicate.class);


        bind(Area.class).to(MutableArea.class);

    }
}
