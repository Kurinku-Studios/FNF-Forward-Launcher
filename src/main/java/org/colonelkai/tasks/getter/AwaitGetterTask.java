package org.colonelkai.tasks.getter;

import java.util.*;
import java.util.function.Consumer;

public class AwaitGetterTask implements GetterTask<Boolean> {

    private final Set<GetterTask<?>> tasks = new HashSet<>();
    private final Set<Consumer<Boolean>> completeEvents = new HashSet<>();

    public AwaitGetterTask(GetterTask<?>... getters) {
        this(Arrays.asList(getters));
    }

    public AwaitGetterTask(Collection<GetterTask<?>> collection) {
        this.tasks.addAll(collection);
    }


    @Override
    public void onComplete(Consumer<Boolean> consumer) {
        this.completeEvents.add(consumer);
    }

    @Override
    public Boolean get() {
        Set<GetterTask<?>> tasks = Collections.synchronizedSet(new HashSet<>(this.tasks));
        this.tasks.parallelStream().forEach(getter -> {
            getter.onComplete((complete) -> tasks.remove(getter));
        });
        while (!tasks.isEmpty()) {
        }
        this.completeEvents.parallelStream().forEach(e -> e.accept(true));
        return true;
    }
}
