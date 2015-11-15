package org.jsimple.data.driven.testing.dsl.definition;

import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.dsl.Block;
import org.jsimple.data.driven.testing.dsl.consumer.FileComparisonConsumer;
import org.jsimple.data.driven.testing.json.function.JsonCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.io.IOException;

public class FileCompareDefinition<R extends Block> {

    private R parent;
    private File errorTargetFolder;

    public FileCompareDefinition(R parent, File errorTargetFolder) {
        this.parent = parent;
        this.errorTargetFolder = errorTargetFolder;
    }

    public R custom(Comparison fileComparison) {
        FileComparisonConsumer fileConsumer = new FileComparisonConsumer(fileComparison, errorTargetFolder);
        parent.addConsumer(fileConsumer);
        return parent;
    }

    public R json() {
        return custom(new JsonCompare(JSONCompareMode.LENIENT));
    }

    public R json(final JSONCompareMode compareMode) {
        return custom(new JsonCompare(compareMode));
    }

    public R txt() {
        return custom((resource, actual) -> {
            try {
                return IOUtils.contentEquals(resource, actual);
            } catch (IOException e) {
                return false;
            }
        });
    }

}
