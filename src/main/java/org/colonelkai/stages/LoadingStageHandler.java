package org.colonelkai.stages;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.colonelkai.ForwardLauncher;

public class LoadingStageHandler {

    public static void init(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        ImageView imageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("logo.png")));
        imageView.setX(50);
        imageView.setY(25);

        imageView.setFitHeight(100);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);

        Group root = new Group(imageView);

        Scene loadingScene = new Scene(root);

        loadingScene.setFill(Color.rgb(100, 0, 0));

        primaryStage.setWidth(400);
        primaryStage.setHeight(150);

        primaryStage.setScene(loadingScene);

        primaryStage.show();
    }
}
