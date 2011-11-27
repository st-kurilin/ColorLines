package com.github.colorlines.domain;

/**
 * @author Stanislav Kurilin
 */
public interface Area {
    boolean contains(Position location);

    /**
     * @param location specified
     * @return item from specified location
     * @throws IllegalStateException if it doesn't contain any item at specified location
     */
    Ball take(Position location);
}
