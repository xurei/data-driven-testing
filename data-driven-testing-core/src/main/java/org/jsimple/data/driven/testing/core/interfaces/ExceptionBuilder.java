package org.jsimple.data.driven.testing.core.interfaces;

/**
 * Created by frederic on 08/06/15.
 */
public interface ExceptionBuilder<R> {

    /**
     *
     * @param exception
     * @return
     */
    R exception(Class<? extends Throwable> exception);
}
