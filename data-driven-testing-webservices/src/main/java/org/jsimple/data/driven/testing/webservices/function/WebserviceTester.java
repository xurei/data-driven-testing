package org.jsimple.data.driven.testing.webservices.function;

import com.mashape.unirest.http.JsonNode;

import java.util.function.Function;

/**
 * Created by frederic on 29/06/15.
 */
public class WebserviceTester {

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseToMap.Builder<T> toMap() {
        return ResponseToMap.<T>newBuilder();
    }

    /**
     *
     * @return
     */
    public static Function<JsonNode, Object> toSerializable() {
        return new JsonNodeToObject();
    }
}
