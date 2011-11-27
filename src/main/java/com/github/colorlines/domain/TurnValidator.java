package com.github.colorlines.domain;

/**
 * @author Stanislav Kurilin
 */
public interface TurnValidator {
    boolean isValid(Area area, Turn turn);
}
