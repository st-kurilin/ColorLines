package com.github.colorlines.javafx;

import com.github.colorlines.domain.Area;
import com.github.colorlines.domain.Player;
import com.github.colorlines.domain.Turn;
import com.github.colorlines.domain.TurnValidator;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Stanislav Kurilin
 */
@Singleton
public class GuiClient implements Player {
    private App app;

    public GuiClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                app = new App();
                app.go();

            }
        }).start();
    }

    @Override
    public Turn turn(Area area, TurnValidator validator) {
        app.block(true);
        app.refresh(area, validator);
        final ArrayList<Turn> objects = newArrayList();
        app.setTurnListener(new Function<Turn, Void>() {
            @Override
            public Void apply(@Nullable Turn turn) {
                objects.add(turn);
                return null;
            }
        });
        app.block(false);
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } while (objects.isEmpty());
        return objects.iterator().next();
    }
}
