package org.jsimple.data.driven.testing.dsl;

import java.util.function.Consumer;

public interface Block<T> {

    void setParent(T parent);

    void addConsumer(Consumer<DataContainer> consumer);
}
