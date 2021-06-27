package org.colonelkai.guielements.nodes.modlist;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.Mod;

public class ModButton extends HBox {

    Mod mod;

    public ModButton(Mod mod) {
        this.mod = mod;
    }

    public void update() {
        this.getChildren().clear();

        Font font = Font.loadFont(
                ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
        );

        // ExecButton is the thing that is either "play" or "see more"
        Button execButton;

        // if mod is installed, make the one with "Play" and then the dropdown menu.
        if(this.mod.isInstalled()) {
            execButton = new Button("Launch");
            execButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mod.run();
                }
            });

        } else { // if the mod isn't installed, simply make the execButton say "See More" that launches the big mod screen.
            execButton = new Button("See More");

        }

        execButton.setFont(font);

        this.getChildren().add(execButton);
    }
}
