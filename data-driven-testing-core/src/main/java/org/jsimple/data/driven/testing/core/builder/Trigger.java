package org.jsimple.data.driven.testing.core.builder;

import java.io.IOException;

/**
 * Created by frederic on 27/04/15.
 */
public interface Trigger<O> {

    /**
     *
     * @return
     */
    O begin() throws IOException;
}
