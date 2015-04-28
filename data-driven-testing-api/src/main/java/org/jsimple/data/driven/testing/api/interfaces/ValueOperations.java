package org.jsimple.data.driven.testing.api.interfaces;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by frederic on 23/04/15.
 */
public interface ValueOperations<R> {

    /**
     * Map the current value with with given @{Function} or set the given default value
     * @param <I>
     * @param <O>
     * @param function
     * @param function
     * @return the current Tester instance
     */
    <I, O> R map(Function<I, O> function);

    /**
     * Execute a given @{Consumer} for the current value
     * @param consumer
     * @param <I>
     * @return the current Tester instance
     */
    <I> R apply(Consumer<I> consumer);
}
