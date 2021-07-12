package org.colonelkai.guielements.nodes.downloadslist;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.network.Values;
import org.colonelkai.mod.network.ZippedModDownloadTask;

import java.util.*;
import java.util.stream.Collectors;

public class DownloadsList extends VBox {

    Font fontSmall = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );

    public DownloadsList() {

    }

    private SortedSet<DownloadBox> getDownloads(Collection<ZippedModDownloadTask> downloadContextList) {
        System.out.println("GETTING DOWNLOAD BOXES");

        List<ZippedModDownloadTask> pageContexts = new ArrayList<>(downloadContextList);

        pageContexts.sort((m1, m2) -> {
            if (m1.isDownloading() && m2.isDownloading()) {
                return 0;
            }
            if (m1.isDownloading()) {
                return -1;
            }
            if (m2.isDownloading()) {
                return 1;
            }
            return Comparator.<ZippedModDownloadTask>comparingLong(m3 -> m3.getMod().getBytesToDownload()).compare(m1, m2);
        });

        if (pageContexts.size() > 5) {
            List<ZippedModDownloadTask> tempPageContexts = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                tempPageContexts.add(pageContexts.get(i));
            }
            pageContexts.clear();
            pageContexts.addAll(tempPageContexts);
        }

        Set<DownloadBox> current = this.getChildren().stream().filter(n -> n instanceof DownloadBox).map(n -> (DownloadBox) n).collect(Collectors.toSet());

        return pageContexts
                .stream()
                .map(c -> current.stream().filter(b -> b.getDownloadTask().getMod().equals(c.getMod())).findAny().orElseGet(() -> new DownloadBox(c)))
                .collect(Collectors
                        .toCollection(() -> new TreeSet<>(Comparator.comparingLong(downloadBox -> downloadBox.getDownloadTask().getMod().getBytesToDownload()))));
    }

    private Label getBlank() {
        Label label = new Label("No ongoing downloads.");
        label.setAlignment(Pos.CENTER);
        label.setFont(fontSmall);
        return label;
    }

    public void update() {
        ObservableList<Node> children = this.getChildren();
        this.setPrefHeight(300);
        this.setPrefWidth(100);
        this.setBackground(new Background(
                new BackgroundFill(Color.rgb(220, 220, 220), null, null)
        ));
        this.setAlignment(Pos.CENTER);

        Collection<ZippedModDownloadTask> downloadContextList = Values.MOD_TASKS;

        if (downloadContextList.isEmpty()) {
            children.clear();
            children.add(this.getBlank());
        } else {
            SortedSet<DownloadBox> newList = getDownloads(downloadContextList);
            children.clear();
            children.addAll(newList);
        }
    }
}
