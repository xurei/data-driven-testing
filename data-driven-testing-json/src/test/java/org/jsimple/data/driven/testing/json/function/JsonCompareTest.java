package org.jsimple.data.driven.testing.json.function;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Created by frederic on 27/06/15.
 */
public class JsonCompareTest {

    public static final String RESOURCES = "src/test/resources/comparison";

    @Test
    public void testOk() throws IOException {

        final Boolean equivalent =
            JsonTester.compare(JSONCompareMode.LENIENT)
                .equivalent(
                    getInputStream("person1.json"),
                    getInputStream("person1-complete.json"));

        Assertions.assertThat(equivalent).isTrue();
    }

    @Test
    public void testNotOk() throws IOException {

        final Boolean equivalent =
            JsonTester.compare(JSONCompareMode.STRICT)
                .equivalent(
                    getInputStream("person1.json"),
                    getInputStream("person1-complete.json"));

        Assertions.assertThat(equivalent).isFalse();
    }

    private InputStream getInputStream(final String fileName) throws FileNotFoundException {
        return new FileInputStream(Paths.get(RESOURCES, fileName).toFile());
    }
}
