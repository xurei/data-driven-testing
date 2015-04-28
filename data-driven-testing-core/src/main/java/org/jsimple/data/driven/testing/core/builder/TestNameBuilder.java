package org.jsimple.data.driven.testing.core.builder;

/**
 * Created by frederic on 26/04/15.
 */
public interface TestNameBuilder<O> {

    /**
     * Defines test name
     * @param testName
     * @return
     */
    O name(String testName);
}
