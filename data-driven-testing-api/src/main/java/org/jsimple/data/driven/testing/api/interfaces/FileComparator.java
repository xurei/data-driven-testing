package org.jsimple.data.driven.testing.api.interfaces;

import org.jsimple.data.driven.testing.api.structure.Comparison;

import java.io.IOException;

/**
 * Created by frederic on 23/04/15.
 */
public interface FileComparator<R> {

    /**
     * Compare resource file to actual file for a given file name with a given
     * {@link Comparison}
     * @param fileName
     * @return the current Tester instance
     */
    R compare(String fileName, Comparison strategy) throws IOException;
}
