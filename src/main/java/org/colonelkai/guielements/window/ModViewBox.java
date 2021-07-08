package org.colonelkai.guielements.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.Mod;
import org.colonelkai.mod.network.ModDownloader;
import org.colonelkai.mod.network.Values;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ModViewBox {

    /*
    This is a popup window that shows the Icon, have a background and the description of the mod.
    This is where you download a mod if it isn't downloaded, or if it is, uninstall it.

    I know mose is going to kill me for putting this many shit into this single file, but fuck it, most of this don't
    deserve their own class :)

    I am so sorry to anyone who has to deal with this.
     */

    Stage window;
    Mod mod;

    Font font = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );
    Font fontBig = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 40
    );

    public ModViewBox(Mod mod) {
        this.mod = mod;

        this.window = new Stage();
    }

    public Background getBackground() {
        // background time
        Image bgImage;

        // If the mod is downloaded, just get the background image from there.
        if(mod.isInstalled()) {
            bgImage = new Image(new File(
                    Values.FLAUNCHER_DATA_PATH + File.separator + mod.getModID() + File.separator + "bg.png"
            ).toURI().toString());
        } else { // If the mod isn't downloaded, get it from the URL.
            bgImage = new Image(mod.getBgPictureURL().toString());
        }

        BackgroundImage backgroundImage = new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,false, false, true, true));

        return new Background(backgroundImage);
    }

    public ImageView getIcon() {
        // Icon on the left yes yes
        Image iconImage;

        // If the mod is downloaded, just get the icon image from there.
        if(mod.isInstalled()) {
            iconImage = new Image(new File(
                    Values.FLAUNCHER_DATA_PATH + File.separator + mod.getModID() + File.separator + "icon.png"
            ).toURI().toString());
        } else { // If the mod isn't downloaded, get it from the URL.
            iconImage = new Image(mod.getIconURL().toString());
        }

        ImageView iconView = new ImageView(iconImage);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(200);
        iconView.setFitWidth(200);

        return iconView;
    }

    public VBox getRightSide() {
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER_RIGHT);

        vbox.setBackground(new Background(
                new BackgroundFill(Color.rgb(220,220,220, 0.8), null, null)
        ));

        Label title = new Label(mod.getModName());
        title.setFont(fontBig);
        title.setAlignment(Pos.CENTER);
        title.setContentDisplay(ContentDisplay.TOP); // this piece of shit wont center no matter what i do
        vbox.getChildren().add(title);

        String description = mod.getModDescription();

        // I had to add newlines to this description so it doesn't go off to the side of the screen

        List<String> descStringList = Arrays.asList(description.split(" ")); // fucking regex, luckily it's just a space
        List<String> finalDescStringList = new ArrayList<>();

        long maxCharactersBeforeNewline = 20;
        long counter = 0;

        // couldn't do a lambda, feeling sad today
        for (String string : descStringList) {
            counter += string.length();
            if (counter > maxCharactersBeforeNewline) {
                counter = 0;
                string += "\n";
            }
            finalDescStringList.add(string);
        }

        String finalDescString = String.join(" ", finalDescStringList);

        Label descLabel = new Label(finalDescString);
        descLabel.setFont(font);
        descLabel.setAlignment(Pos.CENTER);
        vbox.getChildren().add(descLabel);
        vbox.getChildren().add(getPlayButton());

        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER); // WHY DID THIS WORK, WHY?

        return vbox;
    }

    public HBox getPlayButton() {
        HBox hbox = new HBox();
        if(mod.isInstalled()) {
            ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/Launch.png")));
            buttonImageView.setFitWidth(150);
            buttonImageView.setFitHeight(75);
            buttonImageView.setPreserveRatio(true);
            Button launchButton = new Button();
            launchButton.setGraphic(buttonImageView);
            launchButton.setStyle("-fx-background-color: transparent;");
            launchButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mod.run();
                    window.hide();
                }
            });
            hbox.getChildren().add(launchButton);
        } else {
            Button downloadButton = new Button();
            ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/Download.png")));
            buttonImageView.setFitWidth(150);
            buttonImageView.setFitHeight(75);
            buttonImageView.setPreserveRatio(true);
            downloadButton.setGraphic(buttonImageView);
            downloadButton.setStyle("-fx-background-color: transparent;");
            downloadButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        ModDownloader.downloadModAsynced(mod);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    window.hide();
                }
            });
            hbox.getChildren().add(downloadButton);
        }
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        return hbox;
    }

    public void display() {

        // this block input events to other windows until this one is closed
        this.window.initModality(Modality.APPLICATION_MODAL);
        this.window.setTitle(this.mod.getModName() + " Information");

        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        this.window.setWidth(640);
        this.window.setHeight(400);

        layout.getChildren().add(getIcon());
        layout.getChildren().add(getRightSide());

        layout.setBackground(getBackground());

        this.window.getIcons().add(new Image(
                Objects.requireNonNull(ForwardLauncher.class.getResourceAsStream("/icon.png"))));

        this.window.setScene(scene);
        window.show();
    }
}
