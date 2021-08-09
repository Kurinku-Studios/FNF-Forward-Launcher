package org.colonelkai.guielements.nodes.input;

import javafx.scene.layout.HBox;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class PathInput<S> extends HBox implements NodeInput<S, Path> {

    private final Field field;
    private final S setting;

    public PathInput(Field field, S setting) {
        this.field = field;
        this.setting = setting;
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public S getFieldObject() {
        return this.setting;
    }
}
