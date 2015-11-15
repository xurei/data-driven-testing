package org.jsimple.data.driven.testing.dsl;

public class Tester extends AbstractTester<Tester> {
    public Tester(TesterFactory testContext) {
        super(testContext);
    }

    @Override
    protected Tester self() {
        return this;
    }

    public void endTest() throws Exception {
        accept(new DataContainer());
    }
}
