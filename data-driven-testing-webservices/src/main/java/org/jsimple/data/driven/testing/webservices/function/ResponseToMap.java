package org.jsimple.data.driven.testing.webservices.function;

import com.mashape.unirest.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by frederic on 04/07/15.
 */
public class ResponseToMap<T> implements Function<HttpResponse<T>, Map<String, Object>> {

    public static class Builder<T> {

        private Optional<Function<T, Object>> mapper;

        private Builder() {
            mapper = Optional.empty();
        }

        public Builder<T> mapper(Function<T, Object> mapper) {
            this.mapper = Optional.of(mapper);
            return this;
        }

        public ResponseToMap<T> build() {
            return new ResponseToMap<>(this);
        }
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<>();
    }

    public static <T> Builder<T> newBuilder(ResponseToMap<T> copy) {
        Builder builder = new Builder<>();
        builder.mapper = Optional.ofNullable(copy.mapper);
        return builder;
    }

    private final Function<T, Object> mapper;

    private ResponseToMap(Builder<T> builder) {
        mapper = builder.mapper.orElse(Object::toString);
    }

    @Override
    public Map<String, Object> apply(final HttpResponse<T> httpResponse) {
        final Map<String, Object> response = new HashMap<>();
        response.put("status",  createStatus(httpResponse).orElse(null));
        response.put("headers", createHeaders(httpResponse).orElse(null));
        response.put("body",    createBody(httpResponse).orElse(null));

        return response;
    }

    private Optional<Integer> createStatus(HttpResponse<T> httpResponse) {
        return
            Optional
                .ofNullable(httpResponse)
                .map(HttpResponse::getStatus);
    }

    private Optional<Map<String, Object>> createHeaders(final HttpResponse<T> httpResponse) {
        return
            Optional
                .ofNullable(httpResponse)
                .map(HttpResponse::getHeaders)
                .map(HashMap::new);
    }

    private Optional<Object> createBody(HttpResponse<T> httpResponse) {
        return
            Optional
                .ofNullable(httpResponse)
                .map(HttpResponse::getBody)
                .map(mapper);
    }
}
