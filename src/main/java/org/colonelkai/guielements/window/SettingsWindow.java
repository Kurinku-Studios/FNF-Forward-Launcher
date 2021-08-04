package org.colonelkai.guielements.window;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;

import java.util.Objects;

public class SettingsWindow extends Stage {

    public SettingsWindow(){
        this.update();

        this.setScene(new Scene(getRoot()));
    }

    private VBox getRoot(){
        VBox root = new VBox();

        Image image = new Image(ForwardLauncher.class.getResourceAsStream("/background.png"));

        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        Background background = new Background(backgroundImage);

        root.setBackground(background);

        // add things to the main root VBox now :)


        return root;
    }

    public void update(){
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Settings");

        this.setWidth(640);
        this.setHeight(400);

        this.getIcons().add(new Image(
                Objects.requireNonNull(ForwardLauncher.class.getResourceAsStream("/icon.png"))));
    }
}
