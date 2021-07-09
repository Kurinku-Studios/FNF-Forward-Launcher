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
import org.colonelkai.mod.network.ZippedModDownloadTask;
import org.colonelkai.mod.network.Values;

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

        List<ZippedModDownloadTask> pageContexts = new ArrayList<>(7);
        for (ZippedModDownloadTask context : downloadContextList) {
            for (int i = 0; i < 6; i++) {
                ZippedModDownloadTask contextAt = null;
                if (pageContexts.size() > i) {
                    contextAt = pageContexts.get(i);
                }
                if (contextAt == null) {
                    if (pageContexts.size() <= i) {
                        pageContexts.add(i, context);
                        break;
                    }
                    pageContexts.set(i, context);
                    break;
                }
                if (contextAt.getMod().getBytesToDownload() < context.getMod().getBytesToDownload()) {
                    pageContexts.add(i, context);
                    pageContexts.remove(pageContexts.size() - 1);
                    break;
                }
            }
        }
        return pageContexts
                .stream()
                .map(DownloadBox::new)
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
        children.clear();

        this.setPrefHeight(300);
        this.setPrefWidth(100);
        this.setBackground(new Background(
                new BackgroundFill(Color.rgb(220, 220, 220), null, null)
        ));
        this.setAlignment(Pos.CENTER);

        Collection<ZippedModDownloadTask> downloadContextList = Values.MOD_TASKS;

        if (downloadContextList.isEmpty()) {
            children.add(this.getBlank());
            System.out.println("balls");
        } else {
            children.addAll(getDownloads(downloadContextList));
            System.out.println("showing downloads");
        }
    }
}
