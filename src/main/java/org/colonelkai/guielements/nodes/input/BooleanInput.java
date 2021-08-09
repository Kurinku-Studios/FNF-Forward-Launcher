package org.colonelkai.guielements.nodes.input;

import javafx.scene.control.ToggleButton;
import org.colonelkai.guielements.nodes.settings.SettingBox;

import java.lang.reflect.Field;

public class BooleanInput<S> extends ToggleButton implements NodeInput<S, Boolean> {

    private final Field field;
    private final S setting;

    public BooleanInput(Field field, S setting) {
        this.field = field;
        this.setting = setting;
        update();
    }

    @Override
    public void setValue(Boolean value) throws IllegalAccessException {
        NodeInput.super.setValue(value);
        this.setPressed(value);
    }


    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public S getFieldObject() {
        return this.setting;
    }

    private void update() {
        this.field.setAccessible(true);
        this.setFont(SettingBox.FONT);
        boolean curValue;
        try {
            curValue = this.getValue();
        } catch (IllegalAccessException e) {
            curValue = false;
        }
        final boolean finalCurValue = curValue;
        this.setText(curValue ? "On" : "Off");
        this.setOnAction(actionEvent -> {
            try {
                this.setValue(!finalCurValue);
                update();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }
}
