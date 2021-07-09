package org.colonelkai.mod.network;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public interface Values {
    String FLAUNCHER_DATA_PATH = System.getenv("APPDATA") + File.separator +
            "ForwardLauncher" + File.separator +
            "FLauncherData" + File.separator;

    Collection<ZippedModDownloadTask> MOD_TASKS = new ArrayList<>();
}
