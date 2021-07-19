package org.colonelkai.tasks.getter.transfer;

import org.colonelkai.tasks.getter.GetterTask;

import java.io.IOException;
import java.util.function.Consumer;

public interface TransferTask<T, P> extends GetterTask<T> {

    void onExceptionThrown(Consumer<Exception> e);

    void onProgressUpdate(Consumer<P> e);
}
