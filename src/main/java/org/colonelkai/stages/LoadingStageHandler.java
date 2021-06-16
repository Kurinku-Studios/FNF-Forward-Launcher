package org.colonelkai.stages;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.Mods;
import org.colonelkai.mod.network.ReferenceTableHandler;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoadingStageHandler {

    private static Label createLoading() {
        Label loadingText = new Label("Starting FNF: Forward Launcher...");
        loadingText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
        loadingText.setTextFill(Color.WHITE);
        loadingText.setLayoutX(175);
        loadingText.setLayoutY(67.5);

        return loadingText;
    }

    private static Label createCredit() {
        Label creditText = new Label("by ColonelKai");
        creditText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
        creditText.setTextFill(Color.LIGHTGRAY);
        creditText.setLayoutX(49);
        creditText.setLayoutY(100);

        return creditText;
    }

    private static Rectangle createRect() {
        Rectangle rect = new Rectangle(400,150);
        rect.setFill(Color.rgb(100, 0, 0));
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);

        return rect;
    }

    private static ImageView createLogo() {
        ImageView imageView = new ImageView(new Image(
                Objects.requireNonNull(ForwardLauncher.class.getResourceAsStream("/logo.png"))));
        imageView.setX(20);
        imageView.setY(30);

        imageView.setFitHeight(250);
        imageView.setFitWidth(125);

        imageView.setPreserveRatio(true);

        return imageView;
    }

    private static Scene createLoadingScene(Group group) {
        Scene loadingScene = new Scene(group);
        loadingScene.setFill(Color.TRANSPARENT);

        return loadingScene;
    }

    public static void init(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Group group = new Group(createRect());
        group.getChildren().add(createLogo());
        group.getChildren().add(createCredit());

        Label loadingText = createLoading(); // we need this here as it will be modified later on
        group.getChildren().add(loadingText);

        Scene loadingScene = createLoadingScene(group);

        primaryStage.setWidth(400);
        primaryStage.setHeight(150);
        primaryStage.setScene(loadingScene);

        primaryStage.show();
        primaryStage.centerOnScreen();

        // Do loading stuff here

        loadingText.setText("Loading mods...");
        Mods.MOD_SET.clear();
        Mods.MOD_SET.addAll(ReferenceTableHandler.getAllMods());
    }
}
