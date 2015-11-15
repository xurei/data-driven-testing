package org.jsimple.data.driven.testing.dsl;

import org.jsimple.data.driven.testing.dsl.consumer.SupplierConsumer;
import org.jsimple.data.driven.testing.dsl.consumer.FunctionConsumer;
import org.jsimple.data.driven.testing.dsl.consumer.ConfigureConsumer;
import org.jsimple.data.driven.testing.dsl.definition.LoadDefinition;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractTester<Type extends AbstractTester<Type>> implements Block<Type>, Consumer<DataContainer> {
    //--------------------------------------------------------------------------
    // Private Fields
    //--------------------------------------------------------------------------
    protected final TesterFactory testerFactory;
    private final LinkedList<Consumer<DataContainer>> consumers = new LinkedList<>();
    protected Type parent;

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    protected AbstractTester(TesterFactory testerFactory) {
        this.testerFactory = testerFactory;
    }
    //--------------------------------------------------------------------------
    // Public Methods
    //--------------------------------------------------------------------------
    public final LoadDefinition<Type> load(String fileUri) {
        return this.load(new File(fileUri));
    }

    public final LoadDefinition<Type> load(File file) {
        return testerFactory.buildNewLoadDefinition(self(), file);
    }

    public final <F> Type configure(Consumer<F> function) {
        addConsumer(new ConfigureConsumer<>(function));
        return self();
    }

    public final <F> Type append(Supplier<F> appender) {
        addConsumer(new SupplierConsumer(appender));
        return self();
    }

    public final <F, T> Type map(Function<F, T> function) {
        addConsumer(new FunctionConsumer<>(function));
        return self();
    }

    public final ScenarioTester<Type> scenario(String scenarioName) {
        ScenarioTester<Type> typeScenarioTester = testerFactory.buildNewscenarioTester(scenarioName);
        addConsumer(typeScenarioTester);
        return typeScenarioTester;
    }

    @Override
    public void addConsumer(Consumer<DataContainer> consumer) {
        if (consumer instanceof Block) {
            ((Block<Type>) consumer).setParent(self());
        }
        consumers.add(consumer);
    }

    @Override
    public void accept(DataContainer dataContainer) {
        consumers.forEach(consumer1 -> consumer1.accept(dataContainer));
    }

    @Override
    public void setParent(Type parent) {
        this.parent = parent;
    }

    protected Collection<Consumer> getConsumers() {
        return Collections.unmodifiableCollection(consumers);
    }

    protected abstract Type self();
}
