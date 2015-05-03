package org.jsimple.data.driven.tester.camel.function;

import org.jsimple.data.driven.testing.api.Tester;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;
import org.jsimple.data.driven.testing.api.interfaces.TypeBuilder;

import java.util.function.Consumer;

/**
 * Created by frederic on 01/05/15.
 */
public interface SaveMockEndpoint<I> extends Consumer<Tester> {

    /**
     *
     * @param <I>
     */
    interface Builder<I, B extends Builder<I, B>> extends MockEndpointBuilder<FileNameBuilder<TypeBuilder<I, SaveBuilder<I, B>>>> {

        /**
         *
         * @return
         */
        SaveMockEndpoint<I> build();
    }
}
