package org.colonelkai.guielements.nodes.settings;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.application.Settings;
import org.colonelkai.guielements.nodes.input.NodeInput;

public class SettingBox<T> extends HBox {

    private final NodeInput<Settings, T> inputBox;
    private final String displayName;

    public static final Font FONT = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

    public SettingBox(String displayName, NodeInput<Settings, T> inputBox) {
        if (!(inputBox instanceof Node)) {
            throw new RuntimeException("NodeInput must extend Node");
        }
        this.displayName = displayName;
        this.inputBox = inputBox;
        this.update();
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public NodeInput<Settings, T> getInputBox() {
        return this.inputBox;
    }

    public void update() {
        this.getChildren().clear();

        // ---- Anchor Panes ----
        AnchorPane apLeft = new AnchorPane();
        HBox.setHgrow(apLeft, Priority.ALWAYS);//Make AnchorPane apLeft grow horizontally
        AnchorPane apRight = new AnchorPane();
        this.getChildren().add(apLeft);
        this.getChildren().add(apRight);

        // ---- LABEL ----
        Label label = new Label(displayName);
        label.setFont(FONT);
        apLeft.getChildren().add(label);
        apRight.getChildren().add((Node) this.inputBox);
    }

}
