package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Save;

import java.io.IOException;

/**
 * Created by frederic on 23/04/15.
 */
public interface ValueSaver<R> extends ValueOperations<ValueSaver<R>> {

    /**
     * Save the current value to file with a given @{Save}
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    <I> R save(String fileName, Save<I> strategy) throws IOException;
}
