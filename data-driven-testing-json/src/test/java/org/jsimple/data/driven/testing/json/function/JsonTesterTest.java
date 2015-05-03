package org.jsimple.data.driven.testing.json.function;

import org.jsimple.data.driven.testing.core.TestFactory;
import org.jsimple.data.driven.testing.core.function.CoreTester;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by frederic on 28/04/15.
 */
public class JsonTesterTest {

    @Test
    public void testScript() throws IOException {

        TestFactory.createTest()
            .name("json-tester")
            .scenario("script")
            .begin()
                .script(
                    CoreTester.<Collection, Collection<Integer>>script("input.json")
                        .load(JsonTester.load((Collection.class)))
                        .function(
                            collection ->
                                (Collection<Integer>)
                                    collection.stream()
                                        .map((Object o) -> ((Integer) o) * 2)
                                        .collect(Collectors.toList()))
                        .save(JsonTester.<Collection<Integer>>save())
                        .comparison(JsonTester.compare(JSONCompareMode.STRICT))
                        .build())
                .end();
    }

}