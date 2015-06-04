package org.jsimple.data.driven.testing.database.functions;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by frederic on 03/06/15.
 */
public class AssertQueryTest {

    @Test
    public void testQuery() throws IOException {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(
            "jdbc:h2:mem:query;" +
                "INIT=" +
                    "RUNSCRIPT FROM 'classpath:database/create.sql'\\;" +
                    "RUNSCRIPT FROM 'classpath:database/populate.sql'");

        final String fileName = "database.txt";

        final Tester tester = mock(Tester.class);
        when(tester.value(any())).thenReturn(tester);
        when(tester.save(any(String.class), any(Save.class))).thenReturn(tester);
        when(tester.compare(any(String.class), any(Comparison.class))).thenReturn(tester);

        final Save<Collection<Map<String, Object>>> save = mock(Save.class);
        final Comparison comparison = mock(Comparison.class);

        DatabaseTester.assertQuery()
            .dataSource(dataSource)
            .query("SELECT * FROM TEST")
            .fileName(fileName)
            .save(save)
            .comparison(comparison)
            .build()
                .accept(tester);

        verifyNoMoreInteractions(save);
        verifyNoMoreInteractions(comparison);

        final ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(tester, times(1)).value(captor.capture());

        final Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) captor.getValue();
        assertThat(collection).hasSize(2);

        verify(tester, times(1)).save(eq(fileName), eq(save));
        verify(tester, times(1)).compare(eq(fileName), eq(comparison));
        verifyNoMoreInteractions(tester);
    }
}
