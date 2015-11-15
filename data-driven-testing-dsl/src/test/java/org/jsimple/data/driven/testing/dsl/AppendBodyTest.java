package org.jsimple.data.driven.testing.dsl;

import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.dsl.model.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created on 24.04.15.
 */
public class AppendBodyTest extends DataDrivenTestBuilder {

    @Test
    public void appendSimpleObjectTest() throws Exception {
        test("scenarioLoadFileTest")
                .append(() -> "This is a dummy body")
                .configure(new Consumer<String>() {
                    @Override
                    public void accept(String input) {
                        Assertions.assertThat(input)
                                .isNotNull()
                                .isEqualTo("This is a dummy body");
                    }
                })
                .endTest();
    }

    @Test
    public void modifyBodyTest() throws Exception {
        test("scenarioLoadFileTest")
                .append(() -> Person
                        .newBuilder()
                        .firstName("Jérémy")
                        .lastName("Brixhe")
                        .value(new BigDecimal("100.00"))
                        .build())
                .map(new Function<Person, String>() {
                    @Override
                    public String apply(Person input) {
                        return input.getFirstName();
                    }
                })
                .configure(new Consumer<String>() {
                    @Override
                    public void accept(String input) {
                        Assertions.assertThat(input)
                                .isNotNull()
                                .isEqualTo("Jérémy");
                    }
                })
                .endTest();
    }
}