package org.colonelkai.tasks.getter.transfer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractTransferTask<T, P> implements TransferTask<T, P> {

    protected final Set<Consumer<T>> completeEvents = new HashSet<>();
    protected final Set<Consumer<P>> processEvents = new HashSet<>();
    protected final Set<Consumer<Exception>> exceptionEvents = new HashSet<>();


    @Override
    public Set<Consumer<Exception>> getExceptionHandlers() {
        return this.exceptionEvents;
    }

    @Override
    public Set<Consumer<T>> getCompleteHandlers() {
        return this.completeEvents;
    }


    @Override
    public void onComplete(Consumer<T> consumer) {
        this.completeEvents.add(consumer);
    }

    @Override
    public void onException(Consumer<Exception> consumer) {
        this.exceptionEvents.add(consumer);
    }
  
    public void onExceptionThrown(Consumer<Exception> e) {
        this.exceptionEvents.add(e);
    }

    @Override
    public void onProgressUpdate(Consumer<P> e) {
        this.processEvents.add(e);
    }
}
