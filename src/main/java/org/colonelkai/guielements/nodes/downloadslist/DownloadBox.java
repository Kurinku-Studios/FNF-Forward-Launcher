package org.colonelkai.guielements.nodes.downloadslist;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.colonelkai.tasks.getter.transfer.download.DownloadContext;

public class DownloadBox extends VBox {

    /*
    Have the progress bar on the bottom
    and also mod name and size on top

    I have no idea what i'm doing aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
     */

    DownloadContext downloadContext;

    public DownloadBox(DownloadContext downloadContext) {
        this.downloadContext = downloadContext;
    }

    private HBox topHBox() {
        HBox hbox = new HBox();

        Label label = new Label();
        String statusString;
        if(this.downloadContext.isDownloading()) {

        }

        hbox.getChildren().add();

        return hbox;
    }

    public void update() {
        this.getChildren().clear();


    }
}
