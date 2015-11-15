package org.jsimple.data.driven.testing.dsl;

public abstract class DataDrivenTestBuilder {
    protected Tester test(String testName) {
        return new Tester(buildTesterFactory(testName));
    }

    protected TesterFactory buildTesterFactory(String testName) {
        return new TesterFactory(testName);
    }
}
