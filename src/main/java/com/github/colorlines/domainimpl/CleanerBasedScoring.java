package com.github.colorlines.domainimpl;

import com.github.colorlines.domain.Scoring;

import javax.inject.Provider;

/**
 * @author Stanislav Kurilin
 */
public class CleanerBasedScoring implements Scoring {
    Provider<CleanHistorer> historerProvider;

    @Override
    public int totalScore() {
        int res = 0;
        for (int i : historerProvider.get().getHistory()) {
            res += i ^ 2;
        }
        return res;
    }
}
