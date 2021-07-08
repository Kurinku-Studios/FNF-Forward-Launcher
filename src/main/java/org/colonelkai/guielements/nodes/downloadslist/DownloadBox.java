package org.colonelkai.guielements.nodes.downloadslist;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.colonelkai.ForwardLauncher;
import org.colonelkai.tasks.getter.transfer.download.DownloadContext;

public class DownloadBox extends VBox {

    /*
    Have the progress bar on the bottom
    and also mod name and size on top

    I have no idea what i'm doing aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
     */

    DownloadContext downloadContext;

    Font fontSmall = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );

    public DownloadBox(DownloadContext downloadContext) {
        this.downloadContext = downloadContext;
    }

    private HBox topHBox() {
        HBox hbox = new HBox();

        Label label = new Label();
        String statusString;
        if(this.downloadContext.isDownloading()) {
            statusString = "Downloading";
        } else {
            statusString = "Unzipping";
        }

        label.setFont(fontSmall);
        label.setText(statusString + this.downloadContext.getMod().getModName());

        hbox.getChildren().add(label);

        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }

    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(0);
        this.downloadContext.getTask().onProgressUpdate(p->progressBar.setProgress(
                ((int) p * 100) / downloadContext.getSize()
        ));
        return progressBar;
    }

    public void update() {
        this.getChildren().clear();

        this.getChildren().add(topHBox());
        this.getChildren().add(getProgressBar());

    }
}
