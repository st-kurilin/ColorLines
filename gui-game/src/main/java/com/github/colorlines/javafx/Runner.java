package com.github.colorlines.javafx;

import com.github.colorlines.Game;
import com.github.colorlines.domainimpl.DomainImplModule;
import com.google.inject.Guice;

/**
 * @author Stanislav Kurilin
 */
public class Runner {
    public static void main(String[] args) {
        Guice.createInjector(new DomainImplModule(), new JavaFxModule())
                .getInstance(Game.class).play();
    }
}
