package org.colonelkai.tasks.getter;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface GetterTask<T> extends Supplier<T> {

    void onComplete(Consumer<T> consumer);

    default Thread getAsynced() {
        Class<?> clazz = this.getClass();
        System.out.println("t: " + clazz.getName());
        return new Thread(this::get);
    }

}
