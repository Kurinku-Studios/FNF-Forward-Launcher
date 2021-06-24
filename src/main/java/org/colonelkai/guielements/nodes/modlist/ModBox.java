package org.colonelkai.guielements.nodes.modlist;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.Mod;

public class ModBox extends HBox {

    Mod mod;

    public ModBox(Mod mod) {
        super(75);

        this.mod = mod;

        this.setBackground(new Background(
                new BackgroundFill(Color.rgb(220,220,220), null, null)
        ));
    }

    public void update() {
        this.getChildren().clear();
        Font fontBig = Font.loadFont(
                ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 30
        );
        Font fontSmall = Font.loadFont(
                ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
        );

        Label modNameLabel = new Label(this.mod.getModName());
        modNameLabel.setFont(fontBig);

        Label modDevLabel = new Label(this.mod.getModDev());
        modDevLabel.setFont(fontSmall);

        VBox modLabels = new VBox(modNameLabel, modDevLabel);
        VBox.setVgrow(modNameLabel, Priority.ALWAYS);

        this.getChildren().add(modLabels);
        VBox.setVgrow(modLabels, Priority.ALWAYS);

        ModButton button = new ModButton(mod);
        button.update();
        button.setAlignment(Pos.CENTER);
        this.getChildren().add(button);

        this.setPadding(new Insets(5, 5, 5, 5));
    }



}
