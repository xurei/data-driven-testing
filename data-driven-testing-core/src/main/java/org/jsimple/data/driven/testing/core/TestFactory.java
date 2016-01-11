package org.jsimple.data.driven.testing.core;

import org.apache.commons.io.FileUtils;
import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.jsimple.data.driven.testing.core.interfaces.FolderBuilder;
import org.jsimple.data.driven.testing.core.interfaces.ScenarioNameBuilder;
import org.jsimple.data.driven.testing.core.interfaces.TesterBuilder;
import org.jsimple.data.driven.testing.core.interfaces.Trigger;
import org.jsimple.data.driven.testing.core.function.CoreTester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.StringBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import difflib.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by frederic on 25/04/15.
 */
public class TestFactory implements Tester {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static final class Builder
        implements
            TesterBuilder,
            ScenarioNameBuilder<FolderBuilder<Tester>>,
            FolderBuilder<Tester>,
            Trigger<Tester> {

        private String           testName;
        private String           scenarioName;
        private Optional<String> resourcePath;
        private Optional<String> targetPath;

        private Builder() {
            resourcePath = Optional.empty();
            targetPath = Optional.empty();
        }

        @Override
        public ScenarioNameBuilder<FolderBuilder<Tester>> name(String testName) {
            this.testName = testName;

            return this;
        }

        @Override
        public FolderBuilder<Tester> scenario(String scenarioName) {
            this.scenarioName = scenarioName;

            return this;
        }

        @Override
        public FolderBuilder<Tester> resourcePath(final String resourceFolder) {
            this.resourcePath = Optional.of(resourceFolder);

            return this;
        }

        @Override
        public FolderBuilder<Tester> targetPath(final String targetFolder) {
            this.targetPath = Optional.of(targetFolder);

            return this;
        }

        public Tester begin() throws IOException {
            return new TestFactory(this);
        }
    }

    public static TesterBuilder createTest() {
        return new Builder();
    }

    //--------------------------------------------------------------------------
    // Private constants
    //--------------------------------------------------------------------------
    private static final String DEFAULT_RESOURCE_FOLDER = "src/test/resources";
    private static final String DEFAULT_TARGET_FOLDER   = "target/tests";
    private static final String INPUT_FOLDER_NAME       = "input";
    private static final String OUTPUT_FOLDER_NAME      = "output";
    private static final String ERROR_FOLDER_NAME       = "error";

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final String testName;
    private final String scenarioName;

    private final Path inputResourcePath;
    private final Path outputResourcePath;
    private final Path outputTargetPath;
    private final Path errorTargetPath;

    private Optional<?> value;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    private TestFactory(Builder builder) throws IOException {
        testName = builder.testName;
        scenarioName = builder.scenarioName;

        final Path resourcePath =
            Paths.get(
                builder.resourcePath.orElse(DEFAULT_RESOURCE_FOLDER),
                testName,
                scenarioName);

        inputResourcePath = resourcePath.resolve(INPUT_FOLDER_NAME);
        outputResourcePath = resourcePath.resolve(OUTPUT_FOLDER_NAME);

        final Path targetPath =
            Paths.get(
                builder.targetPath.orElse(DEFAULT_TARGET_FOLDER),
                testName,
                scenarioName);

        outputTargetPath = targetPath.resolve(OUTPUT_FOLDER_NAME);
        errorTargetPath = targetPath.resolve(ERROR_FOLDER_NAME);

        value = Optional.empty();

        FileUtils.deleteDirectory(targetPath.toFile());
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    @Override
    public Tester value(Object value) {
        this.value = Optional.ofNullable(value);

        return this;
    }

    @Override
    public <T> Tester load(String fileName, Load<T> strategy) throws IOException {
        inputResourcePath.toFile().mkdirs();

        try (final InputStream inputStream = createInputStream(inputResourcePath, fileName)) {
            value =
                Optional.of(
                    strategy.load(inputStream));

            return this;
        }
    }

    @Override
    public <I, O> Tester map(Function<I, O> function) {
        value =
            Optional.ofNullable(
                value
                    .map((Object value) -> (I) value)
                    .map(function)
                    .orElse(null));

        return this;
    }

    @Override
    public <I> Tester apply(Consumer<I> consumer) {
        consumer.accept(
            value
                .map((Object value) -> (I) value)
                .orElseThrow(CoreTester::noValue));

        return this;
    }

    @Override
    public <I> Tester save(String fileName, Save<I> strategy) throws IOException {

        outputTargetPath.toFile().mkdirs();

        try (final OutputStream outputStream = createOutputStream(outputTargetPath, fileName)) {
            strategy.save(
                value
                    .map((Object value) -> (I) value)
                    .orElseThrow(CoreTester::noValue),
                outputStream);

            return this;
        }
    }

    @Override
    public Tester compare(String fileName, Comparison strategy) throws IOException {

        Boolean errors = false;

        outputResourcePath.toFile().mkdirs();
        final File resourceFile = outputResourcePath.resolve(fileName).toFile();
        final File targetFile = outputTargetPath.resolve(fileName).toFile();

        try (
            final InputStream target    = new FileInputStream(targetFile);
            final InputStream resource  = createInputStream(outputResourcePath, fileName)) {

            errors = !strategy.equivalent(resource, target);

            return this;
        }
        catch (Throwable e) {
            errors = true;

            return this;
        }
        finally {
            if (errors) {
                final File errorTargetFolder = errorTargetPath.toFile();
                FileUtils.moveFileToDirectory(
                    targetFile,
                    errorTargetFolder,
                    true);
                final File errorTargetFile = FileUtils.getFile(errorTargetFolder, targetFile.getName());

                List<String> expected = fileToLines(resourceFile);
                List<String> obtained = fileToLines(errorTargetFile);

                // Compute diff. Get the Patch object. Patch is the container for computed deltas.
                Patch patch = DiffUtils.diff(expected, obtained);

                StringBuilder sb = new StringBuilder();
                for (Delta delta: patch.getDeltas()) {
                    System.err.println(delta);
                }
                //assertThat(false).overridingErrorMessage(sb.toString()).isTrue();
            }
        }
    }

    @Override
    public Tester script(Consumer<Tester> consumer) {

        consumer.accept(this);

        return this;
    }

    @Override
    public Tester scenario(BiConsumer<String, Tester> script) throws IOException {
        final File inputFolder = inputResourcePath.toFile();
        inputFolder.mkdirs();

        Arrays
            .asList(inputFolder.list())
            .forEach((String fileName) -> script.accept(fileName, this));

        return this;
    }

    @Override
    public void end() {

        final File errorFolder = errorTargetPath.toFile();

        assertThat(!errorFolder.exists() || errorFolder.list().length == 0)
            .overridingErrorMessage("Errors occured, see folder %s", errorFolder.getAbsolutePath())
            .isTrue();
    }

    //--------------------------------------------------------------------------
    // Private methods
    //--------------------------------------------------------------------------
    private InputStream createInputStream(final Path path, final String fileName) throws FileNotFoundException {
        return
            new FileInputStream(
                path.resolve(fileName).toFile());
    }

    private OutputStream createOutputStream(final Path path, final String fileName) throws FileNotFoundException {
        return
            new FileOutputStream(
                path.resolve(fileName).toFile());
    }

    // Helper method for get the file content
    private static List<String> fileToLines(File file) {
        List<String> lines = new ArrayList<String>();
        String line = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
