package org.colonelkai.guielements.nodes.settings;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.application.Settings;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class SettingsScrollPane extends ScrollPane {

    Font font = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

    Settings temporarySettings = new Settings();

    public SettingsScrollPane() {
        this.update();
    }

    public Settings getTemporarySettings() {
        return temporarySettings;
    }
    public void update() {
        VBox root = new VBox();
        root.getChildren().removeAll();
        root.setPadding(new Insets(10));
        root.prefWidthProperty().bind(this.widthProperty().subtract(15));
        this.setContent(root);

        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        // generate settings stuff because I've decided to be a smart little bean and make it all automatic
        // just wanted to be able to just add new things to Settings.java and be done with it
        // also was bored and wanted some fun - ColonelKai
        // straight from stackoverflow right into my pocket:
        // https://stackoverflow.com/questions/8524011/java-reflection-how-can-i-get-the-all-getter-methods-of-a-java-class-and-invoke
        try {
            for(PropertyDescriptor propertyDescriptor :
                    Introspector.getBeanInfo(this.temporarySettings.getClass()).getPropertyDescriptors()){

                // don't do anything with getClass for obvious reasons.
                if(propertyDescriptor.getReadMethod().invoke(this.temporarySettings) == this.temporarySettings.getClass()) {
                    continue;
                }

                String propertyDisplayName = this.temporarySettings.getSettingDisplayNames().get(propertyDescriptor.getName());
                Object attrib = propertyDescriptor.getReadMethod().invoke(this.temporarySettings);
                Type type = propertyDescriptor.getPropertyType();

                // let the generic class handle the rest
                root.getChildren().add(new SettingBox<>(attrib.getClass(), propertyDisplayName, type, propertyDescriptor, this.temporarySettings, root));

            }
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
