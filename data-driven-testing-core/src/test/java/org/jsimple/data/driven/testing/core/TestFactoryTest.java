package org.jsimple.data.driven.testing.core;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.core.function.CoreTester;
import org.jsimple.data.driven.testing.core.function.TextTester;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frederic on 27/04/15.
 */
public class TestFactoryTest {

    private static final String TEST_NAME = "tester";

    @Test
    public void testNoOuputFile() throws IOException {

        final String scenarioName = "no-output-file";
        final String fileName = "doesnotexists.txt";

        final Tester tester =
            TestFactory.createTest()
                .name(TEST_NAME)
                .scenario(scenarioName)
                .begin()
                    .value("whatever the value")
                    .map((String value) -> value.toUpperCase())
                    .save(fileName, TextTester.save())
                    .compare(fileName, TextTester.compare());

        try {
            tester.end();

            Assertions.fail("An error should have been thrown above");
        }
        catch (AssertionError e) {
            final Path outputPath =
                Paths.get(
                    "target/tests/",
                    TEST_NAME,
                    scenarioName,
                    "error",
                    fileName);

            assertThat(outputPath).exists();
            assertThat(FileUtils.readFileToString(outputPath.toFile())).isEqualTo("WHATEVER THE VALUE");
        }
    }

    @Test
    public void testFromValue() throws IOException {

        final Map<String, Object> value = new HashMap<>();
        value.put("one", 1);
        value.put("two", 2);

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("from-value")
            .begin()
                .value(value)
                .apply((Map<String, Object> map) -> map.put("three", 3))
                .map(Object::toString)
                .save("map.txt", TextTester.save())
                .compare("map.txt", TextTester.compare())
                .end();
    }

    @Test
    public void testFromFile() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("from-file")
            .begin()
                .load("input.txt", TextTester.load())
                .map((String value) -> value.toUpperCase())
                .save("output.txt", TextTester.save())
                .compare("output.txt", TextTester.compare())
                .end();
    }

    @Test
    public void testScript() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("script")
            .begin()
                .script(
                    CoreTester.<String, String>script()
                        .fileName("one.txt")
                        .load(TextTester.load())
                        .function(String::toUpperCase)
                        .save(TextTester.save())
                        .comparison(TextTester.compare())
                        .build())
                .end();
    }

    @Test
    public void testScenario() throws IOException {

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("scenario")
            .begin()
                .scenario(
                    CoreTester.<String, String>scenario()
                        .load(TextTester.load())
                        .function(String::toUpperCase)
                        .save(TextTester.save())
                        .comparison(TextTester.compare())
                        .build())
                .end();
    }
}
