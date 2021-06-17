package org.colonelkai.tasks.getter.transfer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractTransferTask<T, P> implements TransferTask<T, P> {

    protected final Set<Consumer<T>> completeEvents = new HashSet<>();
    protected final Set<Consumer<P>> processEvents = new HashSet<>();
    protected final Set<Consumer<IOException>> exceptionEvents = new HashSet<>();

    @Override
    public void onComplete(Consumer<T> consumer) {
        this.completeEvents.add(consumer);
    }

    @Override
    public void onExceptionThrown(Consumer<IOException> e) {
        this.exceptionEvents.add(e);
    }

    @Override
    public void onProgressUpdate(Consumer<P> e) {
        this.processEvents.add(e);
    }
}
