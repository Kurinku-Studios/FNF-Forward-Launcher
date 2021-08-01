package org.colonelkai.guielements.window;


import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.colonelkai.ForwardLauncher;

import java.util.Objects;

public class SettingsWindow extends Stage {

    public SettingsWindow(){
        this.update();
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
