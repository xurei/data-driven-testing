package org.jsimple.data.driven.testing.api.interfaces;

/**
 * Created by frederic on 01/05/15.
 */
public interface TypeBuilder<T, R> {

    /**
     *
     * @param type
     * @return
     */
    R type(Class<T> type);
}
