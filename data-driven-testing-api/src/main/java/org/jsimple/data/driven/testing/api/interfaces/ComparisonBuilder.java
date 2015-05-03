package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Comparison;

/**
 * Created by frederic on 01/05/15.
 */
public interface ComparisonBuilder<R> {

    /**
     *
     * @param comparison
     * @return
     */
    R comparison(Comparison comparison);
}
