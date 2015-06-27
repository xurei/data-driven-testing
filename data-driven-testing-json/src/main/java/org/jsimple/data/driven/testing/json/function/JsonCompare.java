package org.jsimple.data.driven.testing.json.function;

import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.io.InputStream;

public class JsonCompare implements Comparison {

    private final JSONCompareMode compareMode;

    public JsonCompare(final JSONCompareMode compareMode) {
        this.compareMode = compareMode;
    }

    @Override
    public Boolean equivalent(final InputStream resource, final InputStream actual) throws IOException {
        try {
            JSONAssert.assertEquals(
                IOUtils.toString(resource),
                IOUtils.toString(actual),
                compareMode);

            return true;
        } catch (final Throwable e) {
            return false;
        }
    }
}
