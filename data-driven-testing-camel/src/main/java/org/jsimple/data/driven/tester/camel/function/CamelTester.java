package org.jsimple.data.driven.tester.camel.function;

/**
 * Created by frederic on 01/05/15.
 */
public class CamelTester {

    /**
     *
     * @param <I>
     * @return
     */
    public static <I> CompareMockEndpoint.Builder<I> compareMockEndpoint() {
        return CompareMockEndpoint.newBuilder();
    }
}
