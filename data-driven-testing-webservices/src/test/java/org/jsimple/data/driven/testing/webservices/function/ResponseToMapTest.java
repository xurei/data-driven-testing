package org.jsimple.data.driven.testing.webservices.function;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.jsimple.data.driven.testing.core.TestFactory;
import org.jsimple.data.driven.testing.json.function.JsonTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * Created by frederic on 29/06/15.
 */
public class ResponseToMapTest extends ServerResource {

    private final String TEST_NAME = "response-to-map";

    private Server server;

    @Get
    public String represent() {
        return
            "{ \"message\":" +
                "{ " +
                    "\"status\":\"OK\"," +
                    "\"body\":\"hello world\"" +
                "}" +
            "}";
    }

    @Before
    public void before() throws Exception {
        server =
            new Server(
                Protocol.HTTP,
                8182,
                ResponseToMapTest.class);
        server.start();
    }

    @After
    public void after() throws Exception {
        server.stop();
    }

    @Test
    public void testDefaultMapper() throws Exception {

        final String outputFileName = "output.txt";

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("default")
            .begin()
                .value(
                    Unirest
                        .get("http://localhost:8182")
                        .asJson())
                .map(WebserviceTester.<JsonNode>toMap().build())
                .save(outputFileName, JsonTester.save())
                .compare(outputFileName, JsonTester.compare(JSONCompareMode.LENIENT))
            .end();

    }

    @Test
    public void testJsonMapper() throws Exception {

        final String outputFileName = "output.txt";

        TestFactory.createTest()
            .name(TEST_NAME)
            .scenario("json")
            .begin()
                .value(
                    Unirest
                        .get("http://localhost:8182")
                        .asJson())
                .map(
                    WebserviceTester.<JsonNode>toMap()
                        .mapper(WebserviceTester.toSerializable())
                        .build())
                .save(outputFileName, JsonTester.save())
                .compare(outputFileName, JsonTester.compare(JSONCompareMode.LENIENT))
            .end();

    }
}
