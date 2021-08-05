package org.colonelkai.guielements.nodes.settings;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.application.Settings;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class SettingBox<T> extends HBox {
    private final Type type;
    private final String displayName;
    private T setting;
    private PropertyDescriptor propertyDescriptor;
    private Settings temporarySettings;

    Font font = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

    public SettingBox(
            T setting, String displayName, Type type, PropertyDescriptor propertyDescriptor, Settings temporarySettings, VBox vb) throws InvocationTargetException, IllegalAccessException {
        this.setting = setting;
        this.displayName = displayName;
        this.type = type;
        this.propertyDescriptor = propertyDescriptor;
        this.temporarySettings = temporarySettings;

        this.prefWidthProperty().bind(vb.widthProperty());

        this.update();
    }

    public void update() throws InvocationTargetException, IllegalAccessException {
        this.getChildren().clear();

        // ---- Anchor Panes ----
        AnchorPane apLeft = new AnchorPane();
        HBox.setHgrow(apLeft, Priority.ALWAYS);//Make AnchorPane apLeft grow horizontally
        AnchorPane apRight = new AnchorPane();
        this.getChildren().add(apLeft);
        this.getChildren().add(apRight);

        // ---- LABEL ----
        Label label = new Label(displayName);
        label.setFont(font);
        apLeft.getChildren().add(label);

        // ---- BOOLEAN ----
        if(this.type == boolean.class) {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setFont(font);
            boolean curValue = (boolean) propertyDescriptor.getReadMethod().invoke(this.temporarySettings);
            if(curValue) {
                toggleButton.setText("On");
            } else {
                toggleButton.setText("Off");
            }
            toggleButton.setOnAction(actionEvent -> {
                try {
                    if(curValue) {
                        propertyDescriptor.getWriteMethod().invoke(this.temporarySettings, false);
                    } else {
                        propertyDescriptor.getWriteMethod().invoke(this.temporarySettings, true);
                    }
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
            apRight.getChildren().add(toggleButton);



        }

    }

}
