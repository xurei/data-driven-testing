package org.jsimple.data.driven.testing.webservices.function;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Function;

/**
 * Created by frederic on 04/07/15.
 */
public class JsonNodeToObject implements Function<JsonNode, Object> {

    private final Map<Class<?>, Function<Object, Object>> strategies;

    public JsonNodeToObject() {
        strategies = new HashMap<>(2);
        strategies.put(JSONArray.class,     o -> map((JSONArray) o));
        strategies.put(JSONObject.class,    o -> map((JSONObject) o));
        strategies.put(Object.class,        Object::toString);
    }

    @Override
    public Object apply(final JsonNode jsonNode) {
        return
            Optional
                .ofNullable(jsonNode)
                .map(this::map)
                .orElse(null);
    }

    private Object map(final JsonNode jsonNode) {
        return
            jsonNode.isArray()
                ? map(jsonNode.getArray())
                : map(jsonNode.getObject());
    }

    private Object map(final JSONArray array) {
        final Collection<Object> collection = new ArrayList<>(array.length());
        for (int index = 0; index < array.length(); index++) {
            collection.add(
                map(array.get(index)));
        }

        return collection;
    }

    private Object map(final JSONObject object) {
        final Map<String, Object> map = new HashMap<>();
        object.keys().forEachRemaining(keyObject -> {
            final String key = keyObject.toString();
            map.put(
                key,
                map(object.get(key)));
        });

        return map;
    }

    private Object map(final Object object) {
        return
            Optional
                .ofNullable(object)
                .map(o -> getFunction(o.getClass()).apply(o))
                .orElse(null);
    }

    private Function<Object, Object> getFunction(final Class<?> clazz) {
        return
            Optional
                .ofNullable(clazz)
                .map(c -> strategies.get(c))
                .orElseGet(() -> getFunction(clazz.getSuperclass()));
    }
}
