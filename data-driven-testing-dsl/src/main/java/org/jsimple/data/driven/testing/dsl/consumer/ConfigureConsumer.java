package org.jsimple.data.driven.testing.dsl.consumer;

import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.util.Optional;
import java.util.function.Consumer;

public class ConfigureConsumer<T> implements Consumer<DataContainer> {

    private final Consumer<T> consumer;

    public ConfigureConsumer(Consumer<T> function) {
        this.consumer = function;
    }

    @Override
    public void accept(DataContainer dataContainer) {
        Optional<T> bodyValue = Optional
                .of(dataContainer.getBody())
                .map(input -> (T) input);

        consumer.accept(bodyValue.get());
    }
}
