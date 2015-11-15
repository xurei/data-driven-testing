package org.jsimple.data.driven.testing.dsl.scenario;

import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.dsl.DataDrivenTestBuilder;
import org.jsimple.data.driven.testing.dsl.model.Person;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created on 24.04.15.
 */
public class ScenarioLoadInputFileTest extends DataDrivenTestBuilder {

    @Test
    public void loadSimpleTextFile() throws Exception {
        test("scenarioTester")
                .scenario("loadTxtFile")
                .load().txt()
                .configure(o -> Assertions.assertThat(o)
                        .isNotNull()
                        .isEqualTo("blablabla"))
                .endScenario()
                .endTest();
    }

    @Test
    public void loadSimpleJsonFile() throws Exception {
        final Person expectedPerson = Person
                .newBuilder()
                .firstName("Jérémy")
                .lastName("Brixhe")
                .value(new BigDecimal("100.00"))
                .build();

        test("scenarioTester")
                .scenario("loadJsonFile")
                .load().json(Person.class)
                .configure(input -> Assertions.assertThat(input)
                        .isNotNull()
                        .isEqualTo(expectedPerson))
                .endScenario()
                .endTest();
    }
}