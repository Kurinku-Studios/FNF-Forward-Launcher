package org.colonelkai.guielements.nodes.floatingmenu;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

public class FloatingMenu extends VBox {

    // stuff that will be on this floating menu
    Set<Node> elements = new HashSet<>();

    public FloatingMenu() {

    }

    public static void update() {

    }
}
