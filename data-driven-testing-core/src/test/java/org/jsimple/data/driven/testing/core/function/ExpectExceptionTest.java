package org.jsimple.data.driven.testing.core.function;

import org.junit.Test;

/**
 * Created by frederic on 12/06/15.
 */
public class ExpectExceptionTest {

    @Test
    public void testOk() throws Exception {

        CoreTester.expectException()
            .exception(RuntimeException.class)
            .causedBy(IndexOutOfBoundsException.class)
            .build()
            .accept(new RuntimeException(new IndexOutOfBoundsException()));
    }

    @Test(expected = AssertionError.class)
    public void testNotOk() throws Exception {

        CoreTester.expectException()
            .exception(RuntimeException.class)
            .causedBy(IndexOutOfBoundsException.class)
            .build()
            .accept(new RuntimeException());
    }
}
