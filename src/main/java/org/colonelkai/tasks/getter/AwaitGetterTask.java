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
    @Deprecated
    public void onException(Consumer<Exception> consumer) {
        throw new RuntimeException("Task does not throw exception");
    }

    @Override
    public Set<Consumer<Exception>> getExceptionHandlers() {
        return Collections.emptySet();
    }

    @Override
    public Set<Consumer<Boolean>> getCompleteHandlers() {
        return this.completeEvents;
    }

    @Override
    public Boolean get() {
        Set<GetterTask<?>> tasks = Collections.synchronizedSet(new HashSet<>(this.tasks));
        this.tasks.parallelStream().forEach(getter -> {
            getter.onComplete((complete) -> tasks.remove(getter));
        });
        while (!tasks.isEmpty()) {
        }
        return true;
    }
}
