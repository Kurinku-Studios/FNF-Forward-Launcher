package org.colonelkai.guielements.nodes.input;

import java.lang.reflect.Field;
import java.nio.file.Path;

public interface NodeInput<S, T> {

    Field getField();

    S getFieldObject();

    default T getValue() throws IllegalAccessException {
        return (T) this.getField().get(getFieldObject());
    }

    default void setValue(T value) throws IllegalAccessException {
        this.getField().set(getFieldObject(), value);
    }

    static <S, T> NodeInput<S, T> buildNode(Field field, S setting) {
        Class<?> clazz = field.getType();
        if (clazz == boolean.class || clazz == Boolean.class) {
            return (NodeInput<S, T>) new BooleanInput<>(field, setting);
        }
        if (clazz == Path.class) {
            return (NodeInput<S, T>) new PathInput<>(field, setting);
        }
        throw new IllegalArgumentException("Unknown NodeInput for " + clazz.getSimpleName());
    }
}
