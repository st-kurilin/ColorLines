package com.github.colorlines.domainimpl;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Stanislav Kurilin
 */


@Target({TYPE, METHOD})
@Retention(RUNTIME)
@ScopeAnnotation
public @interface GameScoped {
}