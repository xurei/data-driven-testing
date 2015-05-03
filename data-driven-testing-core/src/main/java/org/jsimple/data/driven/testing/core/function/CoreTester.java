package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.exceptions.NoValueException;

/**
 * Created by frederic on 26/04/15.
 */
public class CoreTester {

    /**
     *
     * @return
     */
    public static NoValueException noValue() {
        return new NoValueException("No value defined, use Tester::value or Tester::load methods");
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> SimpleTestBuilder<I, O, ConsumerSimpleTest.Builder<I, O>> script(final String fileName) {
        return
            ConsumerSimpleTest.<I, O>newBuilder()
                .fileName(fileName);
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> SimpleTestBuilder<I, O, BiConsumerSimpleTest.Builder<I, O>> script() {
        return BiConsumerSimpleTest.<I, O>newBuilder();
    }
}
