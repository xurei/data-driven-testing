package org.jsimple.data.driven.tester.camel.function;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.jsimple.data.driven.tester.camel.interfaces.MockEndpointBuilder;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.interfaces.ComparisonBuilder;
import org.jsimple.data.driven.testing.api.interfaces.FileNameBuilder;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;
import org.jsimple.data.driven.testing.api.interfaces.TypeBuilder;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Save;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 01/05/15.
 */
public class VerifyMockEndpoint<I> implements Consumer<Tester> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder<I>
        implements
            MockEndpointBuilder<FileNameBuilder<TypeBuilder<I, SaveBuilder<I, ComparisonBuilder<Builder<I>>>>>>,
            FileNameBuilder<TypeBuilder<I, SaveBuilder<I, ComparisonBuilder<Builder<I>>>>>,
            TypeBuilder<I, SaveBuilder<I, ComparisonBuilder<Builder<I>>>>,
            SaveBuilder<I, ComparisonBuilder<Builder<I>>>,
            ComparisonBuilder<Builder<I>> {

        private MockEndpoint    mockEndpoint;
        private Class<I>        type;
        private String          fileName;
        private Save<I>         save;
        private Comparison      comparison;

        private Builder() {
        }

        public FileNameBuilder<TypeBuilder<I, SaveBuilder<I, ComparisonBuilder<Builder<I>>>>> mockEndpoint(final MockEndpoint mockEndpoint) {
            this.mockEndpoint = mockEndpoint;

            return this;
        }

        public TypeBuilder<I, SaveBuilder<I, ComparisonBuilder<Builder<I>>>> fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SaveBuilder<I, ComparisonBuilder<Builder<I>>> type(final Class<I> type) {
            this.type = type;
            return this;
        }

        public ComparisonBuilder<Builder<I>> save(final Save<I> save) {
            this.save = save;
            return this;
        }

        @Override
        public Builder<I> comparison(Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        public VerifyMockEndpoint<I> build() {
            return new VerifyMockEndpoint<>(this);
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
    private final String       fileName;
    private final Save<I>      save;

    private VerifyMockEndpoint(Builder builder) {
        mockEndpoint    = builder.mockEndpoint;
        type            = builder.type;
        fileName        = builder.fileName;
        save            = builder.save;
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
