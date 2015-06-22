package org.jsimple.data.driven.tester.camel.function;

import org.apache.camel.Exchange;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by frederic on 08/06/15.
 */
public class GetException implements Function<Exchange, Optional<Exception>> {

    @Override
    public Optional<Exception> apply(Exchange exchange) {
        return
            Optional.ofNullable(
                Optional
                    .ofNullable(exchange)
                    .map(Exchange::getException)
                    .orElse(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class)));
    }
}
