package org.jsimple.data.driven.testing.dsl.scenario;

import org.jsimple.data.driven.testing.database.dataset.DataSetType;
import org.jsimple.data.driven.testing.dsl.DataDrivenTestBuilder;
import org.jsimple.data.driven.testing.dsl.model.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created on 24.04.15.
 */
public class ScenarioFileComparisonTest extends DataDrivenTestBuilder {

    @Test
    public void compareSimpleTextFile() throws Exception {
        test("scenarioTester")
                .scenario("compareTxtFile")
                    .append(() -> "This is a dummy body")
                    .save().txt()
                    .compare().txt()
                .endScenario()
                .endTest();
    }

    @Test
    public void compareSimpleJsonFile() throws Exception {
        test("scenarioTester")
                .scenario("compareJsonFile")
                    .append(() -> Person
                            .newBuilder()
                            .firstName("Jérémy")
                            .lastName("Brixhe")
                            .value(new BigDecimal("100.00"))
                            .build())
                    .save().json()
                    .compare().json()
                .endScenario()
            .endTest();
    }

    @Test
    public void loadSaveCompareJsonTest() throws Exception {
        test("scenarioTester")
                .scenario("loadExecuteSaveCompareJsonFile")
                    .load().json(Person.class)
                    .configure(new Consumer<Person>() {
                        @Override
                        public void accept(Person person) {
                            person.setValue(new BigDecimal(1000.00));
                        }
                    })
                    .saveAndCompare().json()
                .endScenario()
            .endTest();
    }

    @Test(expected = AssertionError.class)
    public void fileComparisonFailedTest() throws Exception {
        test("scenarioTester")
                .scenario("failedToCompareTxtFile")
                    .append(() -> "This is a differentt dummy body")
                    .save().txt()
                    .compare().txt()
                .endScenario()
                .endTest();
    }

}