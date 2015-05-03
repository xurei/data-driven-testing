package org.jsimple.data.driven.testing.api.interfaces;

import java.util.function.Function;

/**
 * Created by frederic on 01/05/15.
 */
public interface FunctionBuilder<I, O, R> {

    /**
     *
     * @param function
     * @param <I>
     * @param <O>
     * @return
     */
    R function(Function<I, O> function);
}
