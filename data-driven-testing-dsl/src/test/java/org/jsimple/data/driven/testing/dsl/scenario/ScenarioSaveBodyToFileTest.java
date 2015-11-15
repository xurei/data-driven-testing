package org.jsimple.data.driven.testing.dsl.scenario;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.dsl.DataDrivenTestBuilder;
import org.jsimple.data.driven.testing.dsl.Scenarios;
import org.jsimple.data.driven.testing.dsl.model.Person;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * Created on 24.04.15.
 */
public class ScenarioSaveBodyToFileTest extends DataDrivenTestBuilder {

    @Test
    public void saveSimpleTextFile() throws Exception {
        test("scenarioTester")
                .scenario("saveTxtFile")
                    .append(() -> "This is a dummy body")
                    .save().txt()
                .endScenario()
                .endTest();

        String expectedFileContent = "This is a dummy body";
        File expectedFile = new File(Scenarios.buildOutputTargetFolder("scenarioTester", "saveTxtFile"), "saveSimpleTextFile.txt");
        Assertions.assertThat(expectedFile.exists()).isTrue();

        String fileContent = FileUtils.readFileToString(expectedFile);
        Assertions.assertThat(fileContent).isNotNull().isEqualTo(expectedFileContent);

    }

    @Test
    public void saveSimpleJsonFile() throws Exception {
        test("scenarioTester")
                .scenario("saveJsonFile")
                    .append(() -> Person
                            .newBuilder()
                            .firstName("Jérémy")
                            .lastName("Brixhe")
                            .value(new BigDecimal("100.00"))
                            .build())
                    .save().json()
                .endScenario()
                .endTest();

        String expectedFileContent = "{\n" +
                "  \"firstName\" : \"Jérémy\",\n" +
                "  \"lastName\" : \"Brixhe\",\n" +
                "  \"value\" : 100.00\n" +
                "}";
        File expectedFile = new File(Scenarios.buildOutputTargetFolder("scenarioTester", "saveJsonFile"), "saveSimpleJsonFile.json");
        Assertions.assertThat(expectedFile.exists()).isTrue();

        String fileContent = FileUtils.readFileToString(expectedFile);
        Assertions.assertThat(fileContent).isNotNull().isEqualTo(expectedFileContent);
    }
}