package org.jsimple.data.driven.testing.api;

import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by frederic on 23/04/15.
 */
public interface Tester {

    /**
     * Set the value that will be used as the input value for other actions
     * @param value
     * @return the current Tester instance
     */
    Tester value(Object value);

    /**
     * Load a file with a given @{Load} and assign the result to the
     * test value
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    <O> Tester load(String fileName, Load<O> strategy) throws IOException;

    /**
     * Map the current value with with given @{Function} or set the given default value
     * @param <I>
     * @param <O>
     * @param function
     * @param function
     * @return the current Tester instance
     */
    <I, O> Tester map(Function<I, O> function);

    /**
     * Execute a given @{Consumer} for the current value
     * @param consumer
     * @param <I>
     * @return the current Tester instance
     */
    <I> Tester apply(Consumer<I> consumer);

    /**
     * Save the current value to file with a given @{Save}
     * @param fileName
     * @param strategy
     * @return the current Tester instance
     */
    <I> Tester save(String fileName, Save<I> strategy) throws IOException;

    /**
     * Compare resource file to actual file for a given file name with a given
     * {@link Comparison}
     * @param fileName
     * @return the current Tester instance
     */
    Tester compare(String fileName, Comparison strategy) throws IOException;

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
     * Execute a given scenario on the current Tester
     * @param scenario
     * @return the current Tester instance
     */
    Tester script(Consumer<Tester> scenario) throws IOException;

    /**
     * Execute a given scenario for each input file
     * @return
     * @throws IOException
     */
    Tester scenario(BiConsumer<String, Tester> script) throws IOException;

    /**
     * Finish the test asserting that no errors happened
     */
    void end();
}
