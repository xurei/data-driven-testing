package org.jsimple.data.driven.testing.json.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by frederic on 26/04/15.
 */
public class JsonTester {

    /**
     *
     * @return
     */
    public static <T> Load<T> load(Class<T> type) {
        return (InputStream input) -> new ObjectMapper().readValue(input, type);
    }

    /**
     *
     * @param
     * @return
     */
    public static <T> Save<T> save() {
        return (T value, OutputStream output) -> new ObjectMapper().writeValue(output, value);
    }

    /**
     *
     * @return
     */
    public static Comparison compare(JSONCompareMode compareMode) {
        return (InputStream resource, InputStream actual) -> {
            try {
                JSONAssert.assertEquals(
                    IOUtils.toString(resource),
                    IOUtils.toString(actual),
                    compareMode);

                return true;
            }
            catch (Exception e) {
                return false;
            }
        };
    }
}
