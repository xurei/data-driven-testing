package org.jsimple.data.driven.testing.database.functions;

import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 03/06/15.
 */
public class AssertQuery implements Consumer<Tester> {

    public static final class Builder
        implements
            SaveBuilder<Collection<Map<String, Object>>, Builder> {


        private DataSource dataSource;
        private String query;
        private String fileName;
        private Save<Collection<Map<String, Object>>> save;
        private Comparison comparison;

        private Builder() {
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder save(Save<Collection<Map<String, Object>>> save) {
            this.save = save;
            return this;
        }

        public Builder comparison(Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        public AssertQuery build() {
            return new AssertQuery(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(AssertQuery copy) {
        Builder builder = new Builder();
        builder.dataSource = copy.dataSource;
        builder.query = copy.query;
        builder.fileName = copy.fileName;
        builder.save = copy.save;
        builder.comparison = copy.comparison;
        return builder;
    }

    private final DataSource dataSource;
    private final String query;
    private final String fileName;
    private final Save<Collection<Map<String, Object>>> save;
    private final Comparison comparison;

    private AssertQuery(Builder builder) {
        dataSource = builder.dataSource;
        query = builder.query;
        fileName = builder.fileName;
        save = builder.save;
        comparison = builder.comparison;
    }

    @Override
    public void accept(Tester tester) {
        try {
            tester
                .value(query())
                .save(fileName, save)
                .compare(fileName, comparison);
        }
        catch (IOException e) {
            fail("Something went wrong", e);
        }

    }

    private Collection<Map<String, Object>> query() {
        return
            new JdbcTemplate(dataSource)
                .queryForList(query);
    }
}
