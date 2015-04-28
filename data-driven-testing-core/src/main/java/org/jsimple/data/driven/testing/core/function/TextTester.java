package org.jsimple.data.driven.testing.core.function;

import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;

/**
 * Created by frederic on 26/04/15.
 */
public class TextTester {

    /**
     *
     * @return
     */
    public static Load<String> loadText() {
        return IOUtils::toString;
    }

    /**
     *
     * @param
     * @return
     */
    public static Save<String> saveToText() {
        return IOUtils::write;
    }

    /**
     *
     * @return
     */
    public static Comparison compareText() {
        return IOUtils::contentEquals;
    }
}
