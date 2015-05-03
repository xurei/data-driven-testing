package org.jsimple.data.driven.tester.camel.function;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;
import org.jsimple.data.driven.testing.api.interfaces.TypeBuilder;
import org.jsimple.data.driven.testing.api.structure.Save;

import java.io.IOException;
import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 01/05/15.
 */
public class DefaultSaveMockEndpoint<I> implements SaveMockEndpoint<I> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<I>
        implements
            SaveMockEndpoint.Builder<I, Builder<I>>,
            FileNameBuilder<TypeBuilder<I, SaveBuilder<I, Builder<I>>>>,
            TypeBuilder<I, SaveBuilder<I, Builder<I>>>,
            SaveBuilder<I, Builder<I>> {

        private MockEndpoint mockEndpoint;
        private Class<I>     type;
        private Save<I>      save;
        private String       fileName;

        private Builder() {
        }

        public Builder<I> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public Builder<I> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder<I> type(final Class<I> type) {
            this.type = type;
            return this;
        }

        public Builder<I> save(final Save<I> save) {
            this.save = save;
            return this;
        }

        public SaveMockEndpoint<I> build() {
            return new DefaultSaveMockEndpoint<>(this);
        }
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final MockEndpoint mockEndpoint;
    private final Class<I>     type;
    private final Save<I>      save;
    private final String       fileName;

    private DefaultSaveMockEndpoint(Builder builder) {
        mockEndpoint    = builder.mockEndpoint;
        type            = builder.type;
        save            = builder.save;
        fileName        = builder.fileName;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public void accept(Tester tester) {

        Integer index = 0;
        for (Exchange exchange : mockEndpoint.getExchanges()) {
            try {
                tester
                    .value(exchange.getIn().getBody(type))
                    .save(MessageFormat.format("{0}-{1}", index++, fileName), save);
            }
            catch (IOException e) {
                fail("Something went wrong", e);
            }
        }
    }
}
