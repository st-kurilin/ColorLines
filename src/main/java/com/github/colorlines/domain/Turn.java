package com.github.colorlines.domain;

/**
 * @author Stanislav Kurilin
 */
public interface Turn {
    Ball original();

    Position moveTo();
}
