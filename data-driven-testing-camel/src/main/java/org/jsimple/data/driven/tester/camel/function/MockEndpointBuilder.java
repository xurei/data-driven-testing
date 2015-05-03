package org.jsimple.data.driven.tester.camel.function;

import org.apache.camel.component.mock.MockEndpoint;

/**
 * Created by frederic on 01/05/15.
 */
public interface MockEndpointBuilder<R> {

    /**
     *
     * @param mockEndpoint
     * @return
     */
    R mockEndpoint(MockEndpoint mockEndpoint);
}
