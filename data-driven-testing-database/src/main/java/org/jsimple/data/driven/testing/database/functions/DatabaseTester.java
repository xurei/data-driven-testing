package org.jsimple.data.driven.testing.database.functions;

/**
 * Created by frederic on 03/06/15.
 */
public class DatabaseTester {

    /**
     *
     * @return
     */
    public static Execute.Builder execute() {
        return Execute.newBuilder();
    }

    /**
     *
     * @return
     */
    public static AssertQuery.Builder assertQuery() {
        return AssertQuery.newBuilder();
    }
}
