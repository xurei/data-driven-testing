package org.jsimple.data.driven.testing.database.functions;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jsimple.data.driven.testing.api.Tester;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by frederic on 03/06/15.
 */
public class QueryTest {

    @Test
    public void testQuery() throws IOException {

        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(
            "jdbc:h2:mem:query;" +
                "INIT=" +
                    "RUNSCRIPT FROM 'classpath:database/create.sql'\\;" +
                    "RUNSCRIPT FROM 'classpath:database/populate.sql'");

        final Tester tester = mock(Tester.class);
        when(tester.value(any())).thenReturn(tester);

        DatabaseTester.query()
            .dataSource(dataSource)
            .sql("SELECT * FROM TEST")
            .build()
                .accept(tester);

        final ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(tester, times(1)).value(captor.capture());

        final Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) captor.getValue();
        assertThat(collection).hasSize(2);

        verifyNoMoreInteractions(tester);
    }
}
