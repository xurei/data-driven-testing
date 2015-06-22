package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.interfaces.ComparisonBuilder;
import org.jsimple.data.driven.testing.api.interfaces.FunctionBuilder;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;
import org.jsimple.data.driven.testing.api.structure.Comparison;
import org.jsimple.data.driven.testing.api.structure.Load;
import org.jsimple.data.driven.testing.api.structure.Save;
import org.jsimple.data.driven.testing.core.interfaces.SimpleTestBuilder;

import java.io.IOException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.fail;

/**
 * Created by frederic on 27/04/15.
 */
public abstract class SimpleTest<I, O> {

    //--------------------------------------------------------------------------
    // Builder
    //--------------------------------------------------------------------------
    public static abstract class Builder<I, O, B
        extends Builder<I, O, B>>
        implements
        SimpleTestBuilder<I, O, B>,
            FunctionBuilder<I, O, SaveBuilder<O, ComparisonBuilder<B>>>,
            SaveBuilder<O, ComparisonBuilder<B>>,
            ComparisonBuilder<B> {

        private Load<I>        load;
        private Save<O>        save;
        private Function<I, O> function;
        private Comparison     comparison;

        protected Builder() {
        }

        public abstract B self();

        public B load(Load<I> load) {
            this.load = load;

            return self();
        }

        public B function(final Function<I, O> mapping) {
            this.function = mapping;

            return self();
        }

        public B save(Save<O> save) {
            this.save = save;

            return self();
        }

        public B comparison(Comparison comparison) {
            this.comparison = comparison;

            return self();
        }
    }

    //--------------------------------------------------------------------------
    // Private fields
    //--------------------------------------------------------------------------
    private final Function<I, O> function;
    private final Load<I>        load;
    private final Save<O>        save;
    private final Comparison     comparison;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    protected SimpleTest(final Builder builder) {
        function = builder.function;
        load = builder.load;
        save = builder.save;
        comparison = builder.comparison;
    }

    //--------------------------------------------------------------------------
    // Public methods
    //--------------------------------------------------------------------------
    public void script(final String fileName, final Tester tester) {
        try {
            tester
                .load(fileName, load)
                .map(function)
                .save(fileName, save)
                .compare(fileName, comparison);
        }
        catch (IOException e) {
            fail("Something went wrong", e);
        }
    }
}
