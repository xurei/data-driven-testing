package org.jsimple.data.driven.testing.database.interfaces;

/**
 * Created by frederic on 05/06/15.
 */
public interface QueryBuilder<R> {

    /**
     *
     * @param query
     * @return
     */
    R query(String query);
}
