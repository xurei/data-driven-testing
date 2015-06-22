package org.jsimple.data.driven.testing.core.interfaces;

/**
 * Created by frederic on 08/06/15.
 */
public interface CausedByBuilder<R> {

    /**
     *
     * @param exception
     * @return
     */
    R causedBy(Class<? extends Throwable> exception);
}
