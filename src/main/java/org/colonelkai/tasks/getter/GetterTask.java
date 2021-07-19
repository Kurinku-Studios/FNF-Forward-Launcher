package org.colonelkai.tasks.getter;

import java.util.Set;
import java.util.function.Consumer;

public interface GetterTask<T> {

    void onComplete(Consumer<T> consumer);

    void onException(Consumer<Exception> consumer);

    Set<Consumer<Exception>> getExceptionHandlers();

    Set<Consumer<T>> getCompleteHandlers();

    T get() throws Exception;

    default Thread getAsynced() {
        return new Thread(() -> {
            try {
                T value = this.get();
                this.getCompleteHandlers().parallelStream().forEach(c -> c.accept(value));
            } catch (Exception e) {
                this.getExceptionHandlers().parallelStream().forEach(c -> c.accept(e));
            }
        });
    }

}
