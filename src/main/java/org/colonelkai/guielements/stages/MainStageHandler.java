package org.colonelkai.guielements.stages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;

import java.util.Objects;

public class MainStageHandler {

    private static Scene createScene(Pane root) {

        Image image = new Image(ForwardLauncher.class.getResourceAsStream("/background.png"));

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,false, false, true, true));

        Background background = new Background(backgroundImage);

        root.setBackground(background);

        return new Scene(root);
    }

    private static void setupStage(Stage stage) {

        // stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(true);
        stage.setWidth(740);
        stage.setHeight(500);
        stage.getIcons().add(new Image(
                Objects.requireNonNull(ForwardLauncher.class.getResourceAsStream("/icon.png"))));
        stage.setTitle("FNF: Forward Launcher");
    }

    public static void init(Stage stage) {
        setupStage(stage);
        stage.show();

        Pane root = new Pane();
        Scene scene = createScene(root);

        stage.setScene(scene);
    }
}
