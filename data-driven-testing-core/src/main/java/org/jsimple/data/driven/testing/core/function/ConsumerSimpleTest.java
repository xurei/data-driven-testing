package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.Tester;

import java.util.function.Consumer;

/**
 * Created by frederic on 28/04/15.
 */
public class ConsumerSimpleTest<I, O>
    extends SimpleTest<I, O>
    implements Consumer<Tester> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static class Builder<I, O> extends SimpleTest.Builder<I, O, Builder<I, O>> {

        private String fileName;

        private Builder() {}

        @Override
        public Builder<I, O> self() {
            return this;
        }

        public Builder<I, O> fileName(String fileName) {
            this.fileName = fileName;

            return this;
        }

        public ConsumerSimpleTest<I, O> build() {
            return new ConsumerSimpleTest<>(this);
        }
    }

    public static <I, O> Builder<I, O> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final String fileName;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    private ConsumerSimpleTest(Builder builder) {
        super(builder);

        this.fileName = builder.fileName;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public void accept(Tester tester) {
        scenario(fileName, tester);
    }
}
