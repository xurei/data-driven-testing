package org.jsimple.data.driven.testing.api;

import org.jsimple.data.driven.testing.api.interfaces.FileComparator;
import org.jsimple.data.driven.testing.api.interfaces.ValueLoader;
import org.jsimple.data.driven.testing.api.interfaces.ValueOperations;
import org.jsimple.data.driven.testing.api.interfaces.ValueSaver;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by frederic on 23/04/15.
 */
public interface Tester
    extends
        ValueLoader<
            ValueOperations<
                ValueSaver<
                    FileComparator<Tester>>>> {

    /**
     * Execute a given runnable
     * @param runnable
     * @return the current Tester instance
     */
    default Tester execute(Runnable runnable) {
        runnable.run();

        return this;
    }

    /**
     * Execute a given script on the current Tester
     * @param consumer
     * @return the current Tester instance
     */
    Tester script(Consumer<Tester> consumer) throws IOException;

    /**
     * Execute a given script for each input file
     * @return
     * @throws IOException
     */
    Tester script(BiConsumer<String, Tester> biConsumer) throws IOException;

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
