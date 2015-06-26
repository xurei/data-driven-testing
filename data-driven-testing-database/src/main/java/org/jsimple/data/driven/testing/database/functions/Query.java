package org.jsimple.data.driven.testing.database.functions;

import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.database.interfaces.DataSourceBuilder;
import org.jsimple.data.driven.testing.database.interfaces.SQLBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * Created by frederic on 03/06/15.
 */
public class Query implements Consumer<Tester> {

    public static final class Builder
        implements
            DataSourceBuilder<SQLBuilder<Builder>>,
            SQLBuilder<Builder> {

        private DataSource dataSource;
        private String query;

        private Builder() {
        }

        @Override
        public SQLBuilder<Builder> dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        @Override
        public Builder sql(String query) {
            this.query = query;
            return this;
        }

        public Query build() {
            return new Query(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Query copy) {
        Builder builder = new Builder();
        builder.dataSource = copy.dataSource;
        builder.query = copy.query;

        return builder;
    }

    private final DataSource dataSource;
    private final String query;

    private Query(Builder builder) {
        dataSource = builder.dataSource;
        query = builder.query;
    }

    @Override
    public void accept(Tester tester) {
        tester.value(
            new JdbcTemplate(dataSource)
                .queryForList(query));
    }
}
