package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Load;

import java.io.IOException;

/**
 * Created by frederic on 23/04/15.
 */
public interface ValueLoader<R> {

    /**
     * Set the value that will be used as the input value for other actions
     * @param value
     * @return the current Tester instance
     */
    R value(Object value);

    /**
     * Load a file with a given @{Load} and assign the result to the
     * test value
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    <O> R load(String fileName, Load<O> strategy) throws IOException;
}
