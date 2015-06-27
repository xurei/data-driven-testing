package org.jsimple.data.driven.testing.json.function;

import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * Created by frederic on 26/04/15.
 */
public class JsonTester {

    /**
     *
     * @return
     */
    public static <T> Load<T> load(Class<T> type) {
        return
            JsonLoad.<T>newBuilder()
                .type(type)
                .build();
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> JsonLoad.Builder<T> customLoad() {
        return JsonLoad.newBuilder();
    }

    /**
     *
     * @param
     * @return
     */
    public static <T> Save<T> save() {
        return
            JsonSave.<T>newBuilder()
                .build();
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> JsonSave.Builder<T> customSave() {
        return JsonSave.newBuilder();
    }

    /**
     *
     * @return
     */
    public static Comparison compare(JSONCompareMode compareMode) {
        return new JsonCompare(compareMode);
    }
}
