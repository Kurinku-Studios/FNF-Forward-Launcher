package org.colonelkai;

import javafx.application.Application;
import javafx.stage.Stage;
import org.colonelkai.stages.LoadingStageHandler;

public class ForwardLauncher extends Application {
    @Override
    public void start(Stage firstStage) throws Exception {
        LoadingStageHandler.init(firstStage); // the first stage will be used as a loading screen.
        //firstStage.hide(); // read that it's better than .close(); (commented until done next scene)
    }

    public static void main(String[] args) {
        ForwardLauncher.launch(args);
    }
}
