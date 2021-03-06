package org.jsimple.data.driven.testing.core.function;

import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.core.interfaces.CausedByBuilder;
import org.jsimple.data.driven.testing.core.interfaces.ExpectExceptionBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by frederic on 08/06/15.
 */
public class ExpectException implements Consumer<Exception> {

    public static final class Builder
        implements
            ExpectExceptionBuilder<Builder>,
            CausedByBuilder<Builder>{

        private Collection<Class<? extends Throwable>> exceptions;

        private Builder() {
            exceptions = new ArrayList<>();
        }

        @Override
        public CausedByBuilder<Builder> exception(Class<? extends Throwable> exception) {
            exceptions.add(exception);
            return this;
        }

        @Override
        public Builder causedBy(Class<? extends Throwable> exception) {
            exceptions.add(exception);
            return this;
        }

        public ExpectException build() {
            return new ExpectException(this);
        }
    }

    public static ExpectExceptionBuilder<Builder> newBuilder() {
        return new Builder();
    }

    public static ExpectExceptionBuilder<Builder> newBuilder(ExpectException copy) {
        Builder builder = new Builder();
        builder.exceptions = copy.exceptions;
        return builder;
    }

    private final Collection<Class<? extends Throwable>> exceptions;

    private ExpectException(Builder builder) {
        exceptions = builder.exceptions;
    }

    @Override
    public void accept(final Exception e) {

        Throwable currentException = e;
        for (final Class<? extends Throwable> expectedType : exceptions) {
            Assertions
                .assertThat(currentException)
                .overridingErrorMessage(MessageFormat.format("expected exception {0}", expectedType.getClass()))
                .isNotNull();

            Assertions.assertThat(currentException).isInstanceOf(expectedType);

            currentException = currentException.getCause();
        }
    }

}
