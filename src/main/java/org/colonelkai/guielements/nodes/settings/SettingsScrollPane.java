package org.colonelkai.guielements.nodes.settings;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.application.Settings;
import org.colonelkai.guielements.nodes.input.NodeInput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<SettingBox<Object>> nodes = Stream
                .of(this.temporarySettings.getClass().getDeclaredFields())
                .map(field -> NodeInput.buildNode(field, this.temporarySettings))
                .map(node -> new SettingBox<>(this.temporarySettings.formatFieldName(node.getField()), node))
                .sorted(Comparator.comparing(SettingBox::getDisplayName))
                .collect(Collectors.toList());
        root.getChildren().addAll(nodes);
    }
}
