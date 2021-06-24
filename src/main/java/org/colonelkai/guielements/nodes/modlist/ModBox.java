package org.colonelkai.guielements.nodes.modlist;

import javafx.scene.layout.HBox;
import org.colonelkai.mod.Mod;

public class ModBox extends HBox {

    Mod mod;

    public ModBox(Mod mod) {
        this.mod = mod;
    }

    public void update() {
        this.getChildren().clear();
    }


}
