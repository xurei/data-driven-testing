package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.Tester;

import java.util.function.BiConsumer;

/**
 * Created by frederic on 28/04/15.
 */
public class BiConsumerSimpleTest<I, O>
    extends SimpleTest<I, O>
    implements BiConsumer<String, Tester> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static class Builder<I, O> extends SimpleTest.Builder<I, O, Builder<I, O>> {

        @Override
        public Builder<I, O> self() {
            return this;
        }

        public BiConsumerSimpleTest<I, O> build() {
            return new BiConsumerSimpleTest<>(this);
        }
    }

    public static <I, O> Builder<I, O> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    public BiConsumerSimpleTest(Builder builder) {
        super(builder);
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public void accept(String fileName, Tester tester) {
        script(fileName, tester);
    }
}
