package org.jsimple.data.driven.testing.json.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsimple.data.driven.testing.api.structure.Load;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * Created by frederic on 27/06/15.
 */
public class JsonLoad<O> implements Load<O> {

    public static class Builder<O> {

        private final ObjectMapper objectMapper;
        private Class<O> type;

        private Builder() {
            objectMapper = new ObjectMapper();
        }

        public Builder configure(Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public Builder type(Class<O> type) {
            this.type = type;

            return this;
        }

        public JsonLoad<O> build() {
            return new JsonLoad<>(this);
        }
    }

    public static <O> Builder<O> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;
    private final Class<O> type;

    private JsonLoad(Builder builder) {
        objectMapper = builder.objectMapper;
        type = builder.type;
    }

    @Override
    public O load(InputStream input) throws IOException {
        return objectMapper.readValue(input, type);
    }
}
