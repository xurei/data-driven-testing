package org.jsimple.data.driven.testing.dsl;

import org.jsimple.data.driven.testing.dsl.definition.FileCompareDefinition;
import org.jsimple.data.driven.testing.dsl.definition.LoadDefinition;
import org.jsimple.data.driven.testing.dsl.definition.SaveAndCompareDefinition;
import org.jsimple.data.driven.testing.dsl.definition.SaveDefinition;

import java.io.File;

public class TesterFactory {
    //--------------------------------------------------------------------------
    // Private Fields
    //--------------------------------------------------------------------------
    protected final String testName;

    public TesterFactory(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }


    public <R extends Block> LoadDefinition<R> buildNewLoadDefinition(R parent, File file) {
        return new LoadDefinition<>(parent, file);
    }

    public <R extends Block> SaveDefinition<R> buildNewSaveDefinition(R parent, File file) {
        return new SaveDefinition<>(parent, file);
    }

    public <R extends Block> SaveAndCompareDefinition<R> buildSaveAndCompareDefinition(R parent, File errorTargetFolder) {
        return new SaveAndCompareDefinition<>(parent, errorTargetFolder);
    }

    public <R extends Block> FileCompareDefinition<R> buildCompareDefinition(R parent, File errorTargetFolder) {
        return new FileCompareDefinition<>(parent, errorTargetFolder);
    }

    public <T> ScenarioTester<T> buildNewscenarioTester(String scenarioName) {
        return new ScenarioTester<T>(this, scenarioName) {
        };
    }
}
