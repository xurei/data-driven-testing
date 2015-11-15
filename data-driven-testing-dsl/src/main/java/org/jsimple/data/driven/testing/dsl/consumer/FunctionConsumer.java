package org.jsimple.data.driven.testing.dsl.consumer;

import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionConsumer<F> implements Consumer<DataContainer> {
    private Function<F, ?> function;

    public FunctionConsumer(Function<F, ?> function) {
        this.function = function;
    }

    public void accept(DataContainer dataContainer) {
        Optional<F> bodyValue = Optional
                .of(dataContainer.getBody())
                .map(input -> (F) input);

        dataContainer.setBody(function.apply(bodyValue.get()));
    }
}
