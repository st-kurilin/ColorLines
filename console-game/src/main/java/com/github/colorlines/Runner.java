package com.github.colorlines;

import com.github.colorlines.consoleplayer.ConsolePlayerModule;
import com.github.colorlines.domainimpl.DomainImplModule;
import com.google.inject.Guice;

/**
 * @author Stanislav Kurilin
 */
public class Runner {
    public static void main(String[] args) {
        Guice.createInjector(new DomainImplModule(), new ConsolePlayerModule())
                .getInstance(Game.class).play();
    }
}
