package org.jsimple.data.driven.testing.core.builder;

import org.jsimple.data.driven.testing.api.Tester;

/**
 * Created by frederic on 27/04/15.
 */
public interface TesterBuilder
    extends
        TestNameBuilder<ScenarioNameBuilder<FolderBuilder<Tester>>>,
        ScenarioNameBuilder<FolderBuilder<Tester>>,
        FolderBuilder<Tester>,
        Trigger<Tester> {
}
