package org.colonelkai.guielements.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.guielements.nodes.modbox.ModBox;
import org.colonelkai.guielements.nodes.modlist.ModList;
import org.colonelkai.mod.Mods;
import org.colonelkai.mod.network.ReferenceTableHandler;

import java.io.IOException;
import java.util.Objects;

public class MainStageHandler {

    static ModList modList;

    private static VBox rightSide() {
        VBox vbox = new VBox();

        // refresh button yes yes
        Button refreshButton = new Button();
        ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/refresh_button.png")));
        buttonImageView.setFitWidth(200);
        buttonImageView.setPreserveRatio(true);
        refreshButton.setGraphic(buttonImageView);
        refreshButton.setStyle("-fx-background-color: transparent;");
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ReferenceTableHandler.updateReferenceTable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainStageHandler.modList.update();
            }
        });
        vbox.getChildren().add(refreshButton);

        return vbox;
    }

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

        ModList modList = new ModList();
        modList.update();
        MainStageHandler.modList = modList;

        VBox rightSide = rightSide();
        rightSide.setAlignment(Pos.CENTER);

        HBox root = new HBox(20, modList, rightSide);
        root.setAlignment(Pos.CENTER);

        root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));

        Scene scene = createScene(root);
        scene.setFill(Color.DARKGRAY);
        stage.setScene(scene);
    }
}
