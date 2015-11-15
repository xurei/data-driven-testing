package org.jsimple.data.driven.testing.dsl.definition;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.jsimple.data.driven.testing.dsl.Block;
import org.jsimple.data.driven.testing.dsl.consumer.FileComparisonConsumer;
import org.jsimple.data.driven.testing.dsl.consumer.SaveFileConsumer;
import org.jsimple.data.driven.testing.json.function.JsonCompare;
import org.jsimple.data.driven.testing.json.function.JsonSave;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.util.function.Consumer;

public class SaveAndCompareDefinition<R extends Block> {

    private R parent;
    private File errorTargetFolder;

    public SaveAndCompareDefinition(R parent, File errorTargetFolder) {
        this.parent = parent;
        this.errorTargetFolder = errorTargetFolder;
    }

    public R custom(Save saveStrategy, Comparison fileComparison) {
        parent.addConsumer(new SaveFileConsumer(saveStrategy));
        parent.addConsumer(new FileComparisonConsumer(fileComparison, errorTargetFolder));
        return parent;
    }

    public R json() {
        return custom(JsonSave.newBuilder().build(), new JsonCompare(JSONCompareMode.LENIENT));
    }

    public R json(final JSONCompareMode compareMode) {
        return custom(JsonSave.newBuilder().build(), new JsonCompare(compareMode));
    }

    public R json(final Consumer<ObjectMapper> consumer) {
        return custom(JsonSave.newBuilder().configure(consumer).build(), new JsonCompare(JSONCompareMode.LENIENT));
    }

    public R json(final JSONCompareMode compareMode, Consumer<ObjectMapper> consumer) {
        return custom(JsonSave.newBuilder().configure(consumer).build(), new JsonCompare(compareMode));
    }

    public R txt() {
        return null;
//        return custom(new SaveText(),new CompareText());
    }

}
