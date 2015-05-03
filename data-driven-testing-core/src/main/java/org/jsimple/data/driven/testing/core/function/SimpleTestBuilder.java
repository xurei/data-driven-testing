package org.jsimple.data.driven.testing.core.function;

import org.jsimple.data.driven.testing.api.interfaces.ComparisonBuilder;
import org.jsimple.data.driven.testing.api.interfaces.FunctionBuilder;
import org.jsimple.data.driven.testing.api.interfaces.LoadBuilder;
import org.jsimple.data.driven.testing.api.interfaces.SaveBuilder;

/**
 * Created by frederic on 01/05/15.
 */
public interface SimpleTestBuilder<I, O, B>
    extends LoadBuilder<I, FunctionBuilder<I, O, SaveBuilder<O, ComparisonBuilder<B>>>> {
}
