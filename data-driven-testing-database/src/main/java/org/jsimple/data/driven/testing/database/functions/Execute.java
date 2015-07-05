package org.jsimple.data.driven.testing.database.functions;

import org.jsimple.data.driven.testing.database.interfaces.DataSourceBuilder;
import org.jsimple.data.driven.testing.database.interfaces.SQLBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by frederic on 03/06/15.
 */
public class Execute implements Runnable {

    public static class Builder
        implements
            DataSourceBuilder<SQLBuilder<Builder>>,
            SQLBuilder<Builder> {

        private DataSource dataSource;
        private Collection<String> orders;

        private Builder() {
            orders = new ArrayList<>();
        }

        @Override
        public SQLBuilder<Builder> dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        @Override
        public Builder sql(String order) {
            orders.add(order);
            return this;
        }

        public Execute build() {
            return new Execute(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Execute copy) {
        Builder builder = new Builder();
        builder.dataSource = copy.dataSource;
        builder.orders = new ArrayList<>(copy.orders);
        return builder;
    }

    private final DataSource dataSource;
    private final Collection<String> orders;

    private Execute(Builder builder) {
        dataSource = builder.dataSource;
        orders = builder.orders;
    }

    @Override
    public void run() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        orders.forEach(jdbcTemplate::execute);
    }
}
