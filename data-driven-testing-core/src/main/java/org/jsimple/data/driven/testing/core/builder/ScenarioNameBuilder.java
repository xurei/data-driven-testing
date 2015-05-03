package org.jsimple.data.driven.testing.core.builder;

/**
 * Created by frederic on 26/04/15.
 */
public interface ScenarioNameBuilder<O> {

    /**
     * Defines script name
     * @param scenarioName
     * @return
     */
    O scenario(String scenarioName);
}
