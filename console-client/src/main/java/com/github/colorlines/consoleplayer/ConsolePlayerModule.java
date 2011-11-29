package com.github.colorlines.consoleplayer;

import com.github.colorlines.domain.Player;
import com.google.inject.AbstractModule;

/**
 * @author Stanislav Kurilin
 */
public class ConsolePlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Player.class).to(PlayerImpl.class);
    }
}
