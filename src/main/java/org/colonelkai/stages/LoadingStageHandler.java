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

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoadingStageHandler {

    public static void init(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        // mmm rectangl
        Rectangle rect = new Rectangle(400,150);
        rect.setFill(Color.rgb(100, 0, 0));
        rect.setArcHeight(10.0);
        rect.setArcWidth(10.0);

        Group group = new Group(rect);

        // Putting the logo
        ImageView imageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/logo.png")));
        imageView.setX(20);
        imageView.setY(30);

        imageView.setFitHeight(250);
        imageView.setFitWidth(125);

        imageView.setPreserveRatio(true);

        group.getChildren().add(imageView);

        // Putting the credit :)
        Label creditText = new Label("by ColonelKai");
        creditText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 10));
        creditText.setTextFill(Color.LIGHTGRAY);
        creditText.setLayoutX(49);
        creditText.setLayoutY(100);

        group.getChildren().add(creditText);

        // Putting the "loading..." text

        Label loadingText = new Label("Starting FNF: Forward Launcher...");
        loadingText.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
        loadingText.setTextFill(Color.WHITE);
        loadingText.setLayoutX(175);
        loadingText.setLayoutY(67.5);

        group.getChildren().add(loadingText);
        Scene loadingScene = new Scene(group);

        //loadingScene.setFill(Color.rgb(100, 0, 0));
        loadingScene.setFill(Color.TRANSPARENT);
        loadingScene.getStylesheets().add(
                Objects.requireNonNull(ForwardLauncher.class.getResource("/stylesheets/LoadingScreen.css"))
                        .toExternalForm()
        );

        primaryStage.setWidth(400);
        primaryStage.setHeight(150);
        primaryStage.setScene(loadingScene);

        primaryStage.show();
        primaryStage.centerOnScreen();

        // Do loading stuff here

    }
}
