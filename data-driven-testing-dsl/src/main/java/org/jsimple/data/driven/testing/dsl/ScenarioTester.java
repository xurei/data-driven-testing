package org.jsimple.data.driven.testing.dsl;

import org.apache.commons.io.FileUtils;
import org.jsimple.data.driven.testing.dsl.consumer.FileComparisonConsumer;
import org.jsimple.data.driven.testing.dsl.consumer.SaveFileConsumer;
import org.jsimple.data.driven.testing.dsl.consumer.LoadFileConsumer;
import org.jsimple.data.driven.testing.dsl.definition.FileCompareDefinition;
import org.jsimple.data.driven.testing.dsl.definition.LoadDefinition;
import org.jsimple.data.driven.testing.dsl.definition.SaveAndCompareDefinition;
import org.jsimple.data.driven.testing.dsl.definition.SaveDefinition;

import java.io.File;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ScenarioTester<Parent> extends AbstractTester<ScenarioTester<Parent>> {

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    protected final File inputResourceFolder;
    protected final File outputResourceFolder;
    protected final File outputTargetFolder;
    protected final File errorTargetFolder;


    public ScenarioTester(TesterFactory testContext, String scenarioName) {
        super(testContext);

        inputResourceFolder = Scenarios.buildInputFolder(testContext.testName, scenarioName);
        inputResourceFolder.mkdirs();

        outputResourceFolder = Scenarios.buildOutputResourceFolder(testContext.testName, scenarioName);
        outputResourceFolder.mkdirs();

        outputTargetFolder = Scenarios.buildOutputTargetFolder(testContext.testName, scenarioName);
        FileUtils.deleteQuietly(outputTargetFolder);
        outputTargetFolder.mkdirs();

        errorTargetFolder = Scenarios.buildErrorTargetFolder(testContext.testName, scenarioName);
        FileUtils.deleteQuietly(errorTargetFolder);
    }

    public LoadDefinition<ScenarioTester<Parent>> load() {
        return testerFactory.buildNewLoadDefinition(self(), null);
    }

    public SaveDefinition<ScenarioTester<Parent>> save() {
        return testerFactory.buildNewSaveDefinition(self(), null);
    }

    public FileCompareDefinition<ScenarioTester<Parent>> compare() {
        return testerFactory.buildCompareDefinition(self(), errorTargetFolder);
    }

    public SaveAndCompareDefinition<ScenarioTester<Parent>> saveAndCompare() {
        return testerFactory.buildSaveAndCompareDefinition(self(), errorTargetFolder);
    }

    public Parent endScenario() {
        return (Parent) parent;
    }

    @Override
    public void accept(DataContainer body) {
        String[] files = inputResourceFolder.list();

        assertThat(files != null).isTrue();
        assertThat(files.length > 0).isTrue();

        for (final String fileName : files) {
            DataContainer scenarioBody = new DataContainer();
            for (final Consumer consumer : getConsumers()) {
                if (consumer instanceof LoadFileConsumer) {
                    ((LoadFileConsumer) consumer).setFile(new File(inputResourceFolder, fileName));
                } else if (consumer instanceof SaveFileConsumer) {
                    ((SaveFileConsumer) consumer).setFile(new File(outputTargetFolder, fileName));
                } else if (consumer instanceof FileComparisonConsumer) {
                    ((FileComparisonConsumer) consumer).setExpectedFile(new File(outputResourceFolder, fileName));
                    ((FileComparisonConsumer) consumer).setGeneratedFile(new File(outputTargetFolder, fileName));
                }

                consumer.accept(scenarioBody);
            }
        }

        assertThat(!errorTargetFolder.exists() || errorTargetFolder.list().length == 0)
                .overridingErrorMessage("Errors occured, see folder %s", errorTargetFolder.getAbsolutePath())
                .isTrue();
    }

    @Override
    protected ScenarioTester<Parent> self() {
        return this;
    }
}