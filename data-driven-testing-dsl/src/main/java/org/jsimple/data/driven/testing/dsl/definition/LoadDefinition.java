package org.jsimple.data.driven.testing.dsl.definition;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.dsl.Block;
import org.jsimple.data.driven.testing.dsl.consumer.LoadFileConsumer;
import org.jsimple.data.driven.testing.json.function.JsonLoad;

import java.io.File;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LoadDefinition<R extends Block> {

    private R parent;
    private File fileToLoad;

    public LoadDefinition(R parent, File file) {
        this.parent = parent;
        this.fileToLoad = file;
    }

    public R custom(Load loadStrategy) {
        LoadFileConsumer fileConsumer = new LoadFileConsumer(loadStrategy);
        fileConsumer.setFile(fileToLoad);
        parent.addConsumer(fileConsumer);
        return parent;
    }

    public <O> R json(Class<O> clazz) {
        return custom(JsonLoad.<O>newBuilder().type(clazz).build());
    }

    public <O> R json(Class<O> clazz, Consumer<ObjectMapper> consumer) {
        return custom(JsonLoad.<O>newBuilder().type(clazz).configure(consumer).build());
    }

    public R txt() {
        return custom(input -> IOUtils.readLines(input).stream().collect(Collectors.joining("\n")));
    }
}
