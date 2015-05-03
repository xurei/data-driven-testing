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
    public static Load<String> load() {
        return IOUtils::toString;
    }

    /**
     *
     * @param
     * @return
     */
    public static Save<String> save() {
        return IOUtils::write;
    }

    /**
     *
     * @return
     */
    public static Comparison compare() {
        return IOUtils::contentEquals;
    }
}
