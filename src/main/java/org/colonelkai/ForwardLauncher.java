package org.colonelkai;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import org.colonelkai.guielements.stages.LoadingStageHandler;

public class ForwardLauncher extends Application {
    @Override
    public void start(Stage firstStage) throws Exception {
        LoadingStageHandler.init(firstStage); // the first stage will be used as a loading screen.

        // TODO does this even work?
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("An Unknown Error occurred.");
                errorAlert.setContentText("Idk what happened, go harass Colonel Kai#3957\n(Error Stack Copied to Clipboard)");
                errorAlert.showAndWait();
                ClipboardContent content = new ClipboardContent();
                content.putString(e.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
    }

    public static void main(String[] args) {
        ForwardLauncher.launch(args);
    }
}
