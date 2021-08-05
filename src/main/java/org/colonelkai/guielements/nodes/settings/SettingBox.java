package org.colonelkai.guielements.nodes.settings;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.application.Settings;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class SettingBox<T> extends HBox {
    private Type type;
    private T setting;
    private String displayName;
    // I feel like this is bad code, and it probably is.
    PropertyDescriptor propertyDescriptor;
    Settings temporarySettings;

    Font font = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

    public SettingBox(T setting, String displayName, Type type, PropertyDescriptor propertyDescriptor, Settings temporarySettings) throws InvocationTargetException, IllegalAccessException {
        this.setting = setting;
        this.displayName = displayName;
        this.type = type;
        this.propertyDescriptor = propertyDescriptor;
        this.temporarySettings = temporarySettings;
        this.update();
    }

    public void update() throws InvocationTargetException, IllegalAccessException {

        // ---- LABEL ----
        Label label = new Label(displayName);
        label.setFont(font);
        this.getChildren().add(label);

        // ---- BOOLEAN ----
        if(this.type == boolean.class) {
            System.out.println("yo mama");
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setFont(font);
            if((boolean) propertyDescriptor.getReadMethod().invoke(this.temporarySettings)) {
                toggleButton.setText("On");
            } else {
                toggleButton.setText("Off");
            }
            toggleButton.setOnAction(actionEvent -> {
                try {
                    propertyDescriptor.getWriteMethod().invoke(this.temporarySettings, toggleButton.isSelected());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                // I don't know if this update call is necessary
                try {
                    this.update();
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            this.getChildren().add(toggleButton);



        }


        int componentWidth = 0;
        for(Node node : this.getChildren()){
            Labeled labeled = (Labeled) node;
            componentWidth += labeled.getWidth();
        }

        this.setSpacing((double) this.getWidth() - (componentWidth));
    }

}
