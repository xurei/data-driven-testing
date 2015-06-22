package org.jsimple.data.driven.testing.database.interfaces;

import javax.sql.DataSource;

/**
 * Created by frederic on 05/06/15.
 */
public interface DataSourceBuilder<R> {

    /**
     *
     * @param dataSource
     * @return
     */
    R dataSource(DataSource dataSource);
}
