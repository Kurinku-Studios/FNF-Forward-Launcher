package org.colonelkai.guielements.nodes.settings;

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

                // we want the value name and the switch/setter to be on the opposite sides so we do this.
                HBox hbox = new HBox();
                hbox.setPrefWidth(this.getWidth());


                String propertyDisplayName = this.temporarySettings.getSettingDisplayNames().get(propertyDescriptor.getName());

                Label propertyLabel = new Label(propertyDisplayName);
                propertyLabel.setFont(font);

                hbox.getChildren().add(propertyLabel);

                Class<?> type = propertyDescriptor.getPropertyType();

                if(type == boolean.class) {
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
                        this.update();
                    });
                    hbox.getChildren().add(toggleButton);

                }

                int componentWidth = 0;
                for(Node node : hbox.getChildren()){
                    Labeled labeled = (Labeled) node;
                    componentWidth += labeled.getWidth();
                }

                hbox.setSpacing((double) this.getWidth() - (componentWidth));
                root.getChildren().add(hbox);

            }
        // don't blame/praise me, IntelliJ did this.
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
