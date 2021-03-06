package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.exceptions.NoValueException;
import org.jsimple.data.driven.testing.core.interfaces.ExpectExceptionBuilder;
import org.jsimple.data.driven.testing.core.interfaces.FileNameBuilder;
import org.jsimple.data.driven.testing.core.interfaces.SimpleTestBuilder;

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
     * @return
     */
    public static ExpectExceptionBuilder<ExpectException.Builder> expectException() {
        return ExpectException.newBuilder();
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> FileNameBuilder<SimpleTestBuilder<I, O, ConsumerSimpleTest.Builder<I, O>>> script() {
        return ConsumerSimpleTest.<I, O>newBuilder();
    }

    /**
     *
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> SimpleTestBuilder<I, O, BiConsumerSimpleTest.Builder<I, O>> scenario() {
        return BiConsumerSimpleTest.<I, O>newBuilder();
    }
}
