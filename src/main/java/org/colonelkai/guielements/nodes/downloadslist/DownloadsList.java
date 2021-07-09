package org.colonelkai.guielements.nodes.downloadslist;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.network.Values;
import org.colonelkai.tasks.getter.transfer.download.DownloadContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DownloadsList extends VBox {

    Font fontSmall = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );

    public DownloadsList() {

    }

    private List<DownloadBox> getDownloads(List<DownloadContext> downloadContextList) {
        List<DownloadBox> downloads = new ArrayList<>();

        System.out.println("GETTING DOWNLOAD BOXES");

        downloadContextList.sort(Comparator.comparingLong(DownloadContext::getSize));

        // for now it'll only display the 6 biggest downloads, i don't know why anyone would download more than 6
        // at one go, but i'll fix this on a later release as i'm rushing this before F3 launches.
        // TODO fix this. this is horrible
        if(downloadContextList.size()>6) {
            List<DownloadContext> newDownloadContextList = new ArrayList<>();
            for(int i = 0; i < 6; i++) {
                newDownloadContextList.add(downloadContextList.get(i));
            }
            downloadContextList.clear();
            downloadContextList.addAll(newDownloadContextList);
        }

        for(DownloadContext downloadContext : downloadContextList) {
            System.out.println("ADDED BOX");
            downloads.add(new DownloadBox(downloadContext));
        }

        return downloads; // TODO THIS DOESN'T SHOW UP, NEED FIX
    }

    private Label getBlank() {
        Label label = new Label("No ongoing downloads.");
        label.setAlignment(Pos.CENTER);
        label.setFont(fontSmall);
        return label;
    }

    public void update() {
        this.getChildren().clear();

        this.setPrefHeight(300);
        this.setPrefWidth(100);
        this.setBackground(new Background(
                new BackgroundFill(Color.rgb(220,220,220), null, null)
        ));
        this.setAlignment(Pos.CENTER);

        List<DownloadContext> downloadContextList = Values.downloadContexts;

        if(downloadContextList.isEmpty()) {
            this.getChildren().add(this.getBlank());
            System.out.println("balls");
        } else {
            this.getChildren().addAll(getDownloads(downloadContextList));
            System.out.println("showing downloads");
        }
    }
}
