package org.colonelkai.guielements.nodes.downloadslist;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private final ProgressBar progressBar = new ProgressBar(0);
    private final Label statusLabel = new Label("Downloading");

    private final Font fontSmall = Font.loadFont(
            ForwardLauncher.class.getResourceAsStream("/fonts/Funkin.otf"), 20
    );

    public DownloadBox(ZippedModDownloadTask downloadContext) {
        this.downloadContext = downloadContext;
        downloadContext.onComplete(c -> Platform.runLater(() -> this.statusLabel.setText("Unzipping")));
        this.progressBar.prefWidthProperty().bind(this.widthProperty());
        this.update();
    }

    public Label getStatusLabel() {
        return this.statusLabel;
    }

    public ZippedModDownloadTask getDownloadTask() {
        return this.downloadContext;
    }

    public Font getSmallFont() {
        return this.fontSmall;
    }

    private VBox topVBox() {
        VBox box = new VBox();

        Label label = new Label(this.downloadContext.getMod().getModName());

        label.setFont(this.fontSmall);
        label.setText(this.downloadContext.getMod().getModName());

        box.getChildren().add(label);
        box.getChildren().add(this.statusLabel);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void update() {
        this.getChildren().clear();

        this.getChildren().add(topVBox());
        this.getChildren().add(getProgressBar());
    }
}
