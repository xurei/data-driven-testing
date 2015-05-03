package org.jsimple.data.driven.tester.camel.function;

/**
 * Created by frederic on 01/05/15.
 */
public interface FileNameBuilder<R> {

    /**
     *
     * @param fileName
     * @return
     */
    R fileName(String fileName);
}
