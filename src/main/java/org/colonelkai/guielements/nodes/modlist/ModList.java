package org.colonelkai.guielements.nodes.modlist;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.guielements.nodes.modbox.ModBox;
import org.colonelkai.mod.Mod;
import org.colonelkai.mod.network.ReferenceTableHandler;

import java.util.*;
import java.util.stream.Collectors;

public class ModList extends HBox {

    /*
    ModList is the thing you see when you launch the launcher, it is the list on the left that has the list of mods
    and will cycle through them like pages.

    The reason this is a HBox instead of an HBox is that, it has two buttons on both sides
    that allow you to go through pages.
     */

    int curPage;
    int maxPage;

    private String searchTerm = "";

    List<Mod> modList = new ArrayList<>();

    public ModList() {
        this.setPage(1);
    }

    Font fontBig = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 25);

    private Set<Mod> filterForSearch(Set<Mod> fullModSetUnchecked) {
        return fullModSetUnchecked
                .stream()
                .filter(mod -> (
                        mod.getModName().toLowerCase().contains(this.getSearchTerm().toLowerCase())
                                || mod.getModDev().toLowerCase().contains(this.getSearchTerm().toLowerCase(Locale.ROOT))
                ))
                .collect(Collectors.toSet());
    }

    // the reason we have is to update the modList and handle the page calculation stuff in a separate function :)
    public void setPage(int page) {
        // todo turn this into async if you somehow manage to get this piece of shit software to be popular so it doesn't
        // slow down the whole thing
        Set<Mod> fullModSetUnchecked = ReferenceTableHandler.getAllMods();

        Set<Mod> fullModSet = filterForSearch(fullModSetUnchecked)
                .stream()
                .filter(Mod::isSupportedOnPlatform)
                .collect(Collectors.toSet());

        int fullModAmount = fullModSet.size();

        // get the max page no, and if it has more mods than perfectly divisible by 5, accommodate for those extra mods by
        // adding another page
        this.maxPage = (fullModAmount - (fullModAmount % 5)) / 5; // look at this shit, doing MATH!
        if (fullModAmount % 5 != 0) {
            this.maxPage += 1;
        }

        this.curPage = page;
        if (this.curPage > this.maxPage) {
            this.curPage = this.maxPage;
        }
        if (this.curPage < 1) {
            this.curPage = 1;
        }

        int startIndex = (this.curPage - 1) * 5;
        int endIndex = startIndex + 4;

        // if the index extends too much, put it in it's place
        if ((endIndex + 1) > fullModAmount) {
            endIndex = fullModSet.size() - 1;
        }

        // we gotta convert and sort for the list
        List<Mod> fullModList = fullModSet
                .stream()
                .sorted(Comparator.comparing(Mod::getModName))
                .collect(Collectors.toList());

        this.modList.clear();

        for (int i = startIndex; i <= endIndex; i++) {
            modList.add(fullModList.get(i));
        }
    }

    private List<Button> getNavButtons() {
        List<Button> buttons = new ArrayList<>();

        ModList modList = this;
        int curPage = this.curPage;

        // left button
        Button leftButton = new Button("<");
        leftButton.prefHeightProperty().bind(this.heightProperty());


        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modList.setPage(curPage - 1);
                modList.update();
            }
        });

        buttons.add(leftButton);

        // right button
        Button rightButton = new Button(">");
        rightButton.prefHeightProperty().bind(this.heightProperty());

        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                modList.setPage(curPage + 1);
                modList.update();
            }
        });

        buttons.add(rightButton);

        return buttons;
    }

    private VBox getMiddleList() {
        VBox vbox = new VBox();

        vbox.setBackground(new Background(
                new BackgroundFill(Color.rgb(220, 220, 220), null, null)
        ));

        this.modList.stream()
                .forEach(mod -> {
                    ModBox modBox = new ModBox(mod);
                    modBox.update();

                    vbox.getChildren().add(modBox);
                });


        // gotta set the width of all of them the same so it don't look misaligned
        DoubleProperty width = new SimpleDoubleProperty(0);

        for (Node node : vbox.getChildren()) {
            ModBox modBox = (ModBox) node;

            if (modBox.getTitle().widthProperty().get() > width.get()) {
                width.set(modBox.getTitle().widthProperty().get());
            }
            modBox.getTitle().prefWidthProperty().bind(width);
        }
        width.set(width.get() + 200);

        vbox.setAlignment(Pos.CENTER);


        if (modList.isEmpty()) {
            Label noModFoundLabel = new Label("                No Mods Found                ");
            noModFoundLabel.setFont(fontBig);
            vbox.getChildren().add(noModFoundLabel);
        }

        return vbox;
    }

    public void update() {
        this.getChildren().clear();

        List<Button> buttons = this.getNavButtons();

        this.getChildren().add(buttons.get(0));
        this.getChildren().add(this.getMiddleList());
        this.getChildren().add(buttons.get(1));
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}

