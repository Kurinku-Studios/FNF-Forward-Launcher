package org.colonelkai.guielements.stages;

import com.sun.tools.javac.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.guielements.nodes.downloadslist.DownloadsList;
import org.colonelkai.guielements.nodes.modlist.ModList;
import org.colonelkai.guielements.window.SettingsWindow;
import org.colonelkai.mod.network.ReferenceTableHandler;

import java.io.IOException;
import java.util.Objects;

public class MainStageHandler {

    public static ModList modList;
    public static DownloadsList downloadsList = new DownloadsList();

    private static Font searchFont = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

    private static VBox rightSide() {
        VBox vbox = new VBox(10);

        TextField textField = new TextField("");
        textField.setPromptText("Search funks");
        textField.setFont(MainStageHandler.searchFont);
        textField.textProperty().addListener(actionEvent -> {
            MainStageHandler.modList.setSearchTerm(textField.getText());
            MainStageHandler.modList.setPage(1);
            MainStageHandler.modList.update();
        });
        vbox.getChildren().add(textField);

        // refresh button yes yes
        HBox rightSideButtons = new HBox();
        Button refreshButton = new Button();
        ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/refresh_button.png")));
        buttonImageView.setFitWidth(200);
        buttonImageView.setPreserveRatio(true);
        refreshButton.setGraphic(buttonImageView);
        refreshButton.setStyle("-fx-background-color: transparent;");
        refreshButton.setOnAction(actionEvent -> {
            try {
                ReferenceTableHandler.updateReferenceTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MainStageHandler.modList.update();
        });
        rightSideButtons.getChildren().add(refreshButton);
        Button settingsButton = new Button();
        ImageView settingsImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/settings.png")));
        settingsImageView.setFitWidth(70);
        settingsImageView.setPreserveRatio(true);
        settingsButton.setFont(searchFont);
        settingsButton.setGraphic(settingsImageView);
        settingsButton.setStyle("-fx-background-color: transparent;");
        settingsButton.setOnAction(actionEvent -> {
            SettingsWindow settingsWindow = new SettingsWindow();
            settingsWindow.show();
        });
        rightSideButtons.getChildren().add(settingsButton);

        vbox.getChildren().add(rightSideButtons);


        // download list
        DownloadsList downloadsList = new DownloadsList();
        downloadsList.update();
        MainStageHandler.downloadsList = downloadsList;
        vbox.getChildren().add(downloadsList);

        return vbox;
    }

    private static Scene createScene(Pane root) {

        Image image = new Image(ForwardLauncher.class.getResourceAsStream("/background.png"));

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        Background background = new Background(backgroundImage);

        root.setBackground(background);


        return new Scene(root);

    }

    private static void setupStage(Stage stage) {

        // stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(true);
        stage.setWidth(850);
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
