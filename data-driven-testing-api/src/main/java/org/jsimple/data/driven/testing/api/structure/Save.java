package org.jsimple.data.driven.testing.api.structure;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by frederic on 23/04/15.
 */
@FunctionalInterface
public interface Save<I> {

    /**
     *
     * @param value
     * @param output
     * @throws IOException
     */
    void save(I value, OutputStream output) throws IOException;
}
