package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Load;

/**
 * Created by frederic on 01/05/15.
 */
public interface LoadBuilder<O, R> {

    /**
     *
     * @param load
     * @return
     */
    R load(Load<O> load);
}
