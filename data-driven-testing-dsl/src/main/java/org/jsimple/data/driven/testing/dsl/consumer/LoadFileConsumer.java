package org.jsimple.data.driven.testing.dsl.consumer;

import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class LoadFileConsumer implements Consumer<DataContainer> {
    private File fileToLoad;
    private final Load loadStrategy;

    public LoadFileConsumer(Load loadStrategy) {
        this.loadStrategy = loadStrategy;
    }

    public void setFile(File fileToLoad) {
        this.fileToLoad = fileToLoad;
    }

    public void accept(DataContainer dataContainer) {
        try {
            dataContainer.setBody(loadStrategy.load(new FileInputStream(fileToLoad)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
