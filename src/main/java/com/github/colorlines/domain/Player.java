package com.github.colorlines.domain;

/**
 * @author Stanislav Kurilin
 */
public interface Player {
    Turn turn(Area area, TurnValidator validator);
}
