package org.colonelkai.guielements.nodes.downloadslist;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.mod.network.ZippedModDownloadTask;

public class DownloadBox extends VBox {

    /*
    Have the progress bar on the bottom
    and also mod name and size on top

    I have no idea what i'm doing aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
     */

    private final ZippedModDownloadTask downloadContext;

    private final Font fontSmall = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );

    public DownloadBox(ZippedModDownloadTask downloadContext) {
        this.downloadContext = downloadContext;
        this.update();
        this.getChildren().add(new Label("test"));
    }

    public ZippedModDownloadTask getDownloadTask() {
        return this.downloadContext;
    }

    public Font getSmallFont() {
        return this.fontSmall;
    }

    private HBox topHBox() {
        HBox hbox = new HBox();

        Label label = new Label();
        String statusString;
        if (this.downloadContext.isDownloading()) {
            statusString = "Downloading";
        } else {
            statusString = "Unzipping";
        }

        label.setFont(this.fontSmall);
        label.setText(statusString + this.downloadContext.getMod().getModName());

        hbox.getChildren().add(label);

        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }

    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(0);
        this.downloadContext.onProgressUpdate(p -> progressBar.setProgress(
                (int) ((p * 100) / downloadContext.getMod().getBytesToDownload())
        ));
        return progressBar;
    }

    public void update() {
        this.getChildren().clear();

        this.getChildren().add(topHBox());
        this.getChildren().add(getProgressBar());
    }
}
