package org.jsimple.data.driven.testing.dsl.consumer;

import org.jsimple.data.driven.testing.api.structure.Save;
import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class SaveFileConsumer implements Consumer<DataContainer> {
    private File file;
    private final Save saveStrategy;

    public SaveFileConsumer(Save saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public void setFile(File fileToLoad) {
        this.file = fileToLoad;
    }

    public void accept(DataContainer dataContainer) {
        try {
            saveStrategy.save(dataContainer.getBody(), new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
