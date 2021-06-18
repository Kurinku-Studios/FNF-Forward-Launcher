package org.colonelkai.stages;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;

import java.io.File;
import java.util.Objects;

public class MainStageHandler {

    private static Label createLabel() {
        return new Label("you're mom");
    }

    private static Scene createScene(Group group) {
        Scene scene = new Scene(group);
        scene.setFill(Color.rgb(50,205,50));

        return scene;
    }

    private static void setUpStage(Stage stage) {
        stage.setResizable(false);
        stage.setWidth(750);
        stage.setHeight(500);
        stage.getIcons().add(new Image(
                Objects.requireNonNull(ForwardLauncher.class.getResourceAsStream("/icon.png"))));
        stage.setTitle("FNF: Forward Launcher");
    }

    private static Button createRefreshButton() {
        Button refreshButton = new Button("Refresh Mods");
        refreshButton.setAlignment(Pos.TOP_RIGHT);

        return refreshButton;
    }

    public static void init(Stage stage) {
        stage.show();
        setUpStage(stage);

        Group group = new Group(createRefreshButton());

        Scene scene = createScene(group);

        stage.setScene(scene);
    }
}
