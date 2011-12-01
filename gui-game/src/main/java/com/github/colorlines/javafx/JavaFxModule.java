package com.github.colorlines.javafx;

import com.github.colorlines.domain.Player;
import com.google.inject.AbstractModule;

/**
 * @author Stanislav Kurilin
 */
public class JavaFxModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Player.class).to(GuiClient.class);
    }
}

