package org.colonelkai.guielements.nodes.modbox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.guielements.stages.MainStageHandler;
import org.colonelkai.guielements.window.ModViewBox;
import org.colonelkai.mod.Mod;

public class ModButton extends HBox {

    private final Mod mod;

    public ModButton(Mod mod) {
        this.mod = mod;
    }

    /*
    I hate this class i hope i dont have to touch this ever again
     */

    public void update() {
        this.getChildren().clear();

        Font font = Font.loadFont(
                ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20);

        // ExecButton is the thing that is either "play" or "see more"
        Button execButton;

        // if mod is installed, make the one with "Play" and then the dropdown menu.
        if (this.mod.isInstalled()) {
            execButton = new Button();
            execButton.setOnAction(actionEvent -> mod.run());
            ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/Launch.png")));
            buttonImageView.setFitWidth(75);
            buttonImageView.setFitHeight(37.5);
            buttonImageView.setPreserveRatio(true);
            execButton.setGraphic(buttonImageView);

            this.getChildren().add(execButton);

            // Dropdown menu
            Button miscButton = new Button("\u25BC");
            miscButton.prefHeightProperty().bind(execButton.heightProperty());
            this.getChildren().add(miscButton);

            ContextMenu miscOptions = new ContextMenu();

            MenuItem uninstall = new MenuItem("Uninstall");
            uninstall.setOnAction(actionEvent -> {
                mod.deleteMod();
                try {MainStageHandler.modList.update();} catch (Exception ignored) {}
            });

            MenuItem seeMore = new MenuItem("See More");
            seeMore.setOnAction(actionEvent -> new ModViewBox(mod).display());

            miscOptions.getItems().addAll(uninstall, seeMore);

            miscButton.setOnAction(actionEvent -> miscOptions.show(miscButton, Side.BOTTOM, 0, 0));

        } else { // if the mod isn't installed, simply make the execButton say "See More" that launches the big mod screen.
            execButton = new Button();
            ImageView buttonImageView = new ImageView(new Image(ForwardLauncher.class.getResourceAsStream("/buttons/See_More.png")));
            buttonImageView.setFitWidth(75);
            buttonImageView.setFitHeight(37.5);
            buttonImageView.setPreserveRatio(true);
            execButton.setGraphic(buttonImageView);
            execButton.setOnAction(actionEvent -> new ModViewBox(mod).display());
            this.getChildren().add(execButton);
        }

        execButton.setFont(font);

    }
}
