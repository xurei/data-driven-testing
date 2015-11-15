package org.jsimple.data.driven.testing.dsl.definition;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.jsimple.data.driven.testing.dsl.Block;
import org.jsimple.data.driven.testing.dsl.consumer.SaveFileConsumer;
import org.jsimple.data.driven.testing.json.function.JsonSave;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

public class SaveDefinition<R extends Block> {

    private R parent;
    private File fileToLoad;

    public SaveDefinition(R parent, File file) {
        this.parent = parent;
        this.fileToLoad = file;
    }

    public R custom(Save saveStrategy) {
        SaveFileConsumer fileConsumer = new SaveFileConsumer(saveStrategy);
        fileConsumer.setFile(fileToLoad);
        parent.addConsumer(fileConsumer);
        return parent;
    }

    public R json() {
        return custom(JsonSave.newBuilder().build());
    }

    public R json(Consumer<ObjectMapper> configurator) {
        return custom(JsonSave.newBuilder().configure(configurator).build());
    }

    public R txt() {
        return custom((value, output) -> IOUtils.write(value == null ? "null" : value.toString(), output));
    }
}
