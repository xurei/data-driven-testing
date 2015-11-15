package org.jsimple.data.driven.testing.dsl;

import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.dsl.model.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Created on 24.04.15.
 */
public class AbstractTesterLoadInputTest extends DataDrivenTestBuilder {

    @Test
    public void loadSimpleTextFileFromStringUriTest() throws Exception {
        test("loadSimpleTextFile")
                .load("src/test/resources/simple/test/loadSimpleTextFile.txt").txt()
                .configure(new Consumer<String>() {
                    @Override
                    public void accept(String input) {
                        Assertions.assertThat(input)
                                .isNotNull()
                                .isEqualTo("blablabla");
                    }
                })
                .endTest();
    }

    @Test
    public void loadSimpleJsonFileFromStringUriTest() throws Exception {
        final Person expectedPerson = Person
                .newBuilder()
                .firstName("Jérémy")
                .lastName("Brixhe")
                .value(new BigDecimal("100.00"))
                .build();

        test("loadSimpleJsonFile")
                .load("src/test/resources/simple/test/loadSimpleJsonFile.json").json(Person.class)
                .configure(new Consumer<Person>() {
                    @Override
                    public void accept(Person input) {
                        Assertions.assertThat(input)
                                .isNotNull()
                                .isEqualTo(expectedPerson);
                    }
                })
                .endTest();
    }
}