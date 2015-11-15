package org.jsimple.data.driven.testing.dsl.consumer;

import org.apache.commons.io.FileUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class FileComparisonConsumer implements Consumer<DataContainer> {

    private final File errorTargetFolder;
    private final Comparison fileComparison;

    private File expectedFile;
    private File generatedFile;

    public FileComparisonConsumer(Comparison fileComparison, File errorTargetFolder) {
        this.fileComparison = fileComparison;
        this.errorTargetFolder = errorTargetFolder;
    }

    public void setExpectedFile(File expectedFile) {
        this.expectedFile = expectedFile;
    }

    public void setGeneratedFile(File generatedFile) {
        this.generatedFile = generatedFile;
    }

    @Override
    public void accept(DataContainer dataContainer) {
        try {
            if (!fileComparison.equivalent(new FileInputStream(expectedFile), new FileInputStream(generatedFile))) {
                try {
                    FileUtils.moveFileToDirectory(generatedFile, errorTargetFolder, true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
