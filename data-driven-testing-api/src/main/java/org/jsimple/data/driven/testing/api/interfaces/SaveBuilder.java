package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Save;

/**
 * Created by frederic on 01/05/15.
 */
public interface SaveBuilder<I, R> {

    /**
     *
     * @param save
     * @return
     */
    R save(Save<I> save);
}
