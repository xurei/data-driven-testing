package org.jsimple.data.driven.testing.json.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jsimple.data.driven.testing.api.structure.Save;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * Created by frederic on 27/06/15.
 */
public class JsonSave<I> implements Save<I> {

    public static class Builder<I> {
        private final ObjectMapper objectMapper;

        private Builder() {
            objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }

        public Builder configure(Consumer<ObjectMapper> configurator) {
            configurator.accept(objectMapper);

            return this;
        }

        public JsonSave<I> build() {
            return new JsonSave<>(this);
        }
    }

    public static <I> Builder<I> newBuilder() {
        return new Builder<>();
    }

    private final ObjectMapper objectMapper;

    private JsonSave(Builder builder) {
        objectMapper = builder.objectMapper;
    }

    @Override
    public void save(I value, OutputStream output) throws IOException {
        objectMapper.writeValue(output, value);
    }
}
