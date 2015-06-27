package org.jsimple.data.driven.tester.camel.function;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.jsimple.data.driven.testing.core.TestFactory;
import org.jsimple.data.driven.testing.core.function.TextTester;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by frederic on 02/05/15.
 */
public class CamelTesterTest extends CamelTestSupport {

    @Produce(uri = "direct:source")
    private ProducerTemplate source;

    @EndpointInject(uri = "mock:destination")
    private MockEndpoint destination;

    @Before
    public void before() throws Exception {
        context().addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:source")
                    .split().body(Collection.class)
                    .to("mock:destination");
            }
        });
    }

    @Test
    public void testSaveMockEndpoint() throws IOException {

        TestFactory.createTest()
            .name("camel")
            .scenario("save")
            .begin()
                .value(Arrays.asList("one", "two"))
                .apply((Collection<String> col) -> source.sendBody(col))
                .script(
                    CamelTester.<String>compareMockEndpoint()
                        .mockEndpoint(destination)
                        .fileName("string.txt")
                        .type(String.class)
                        .save(TextTester.save())
                        .comparison(TextTester.compare())
                        .build())
                .compare("0-string.txt", TextTester.compare())
                .compare("1-string.txt", TextTester.compare())
                .end();
    }
}
