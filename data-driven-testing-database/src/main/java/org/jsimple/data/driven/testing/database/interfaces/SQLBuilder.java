package org.jsimple.data.driven.testing.database.interfaces;

/**
 * Created by frederic on 05/06/15.
 */
public interface SQLBuilder<R> {

    /**
     *
     * @param query
     * @return
     */
    R sql(String query);
}
