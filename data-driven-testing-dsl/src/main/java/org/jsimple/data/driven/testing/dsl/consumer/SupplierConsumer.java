package org.jsimple.data.driven.testing.dsl.consumer;


import org.jsimple.data.driven.testing.dsl.DataContainer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SupplierConsumer implements Consumer<DataContainer> {

    private Supplier supplier;

    public SupplierConsumer(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void accept(DataContainer dataContainer) {
        dataContainer.setBody(supplier.get());
    }
}
