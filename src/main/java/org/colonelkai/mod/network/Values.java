package org.colonelkai.mod.network;

import org.colonelkai.tasks.getter.transfer.download.DownloadContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface Values {
    public static final String FLAUNCHER_DATA_PATH = System.getenv("APPDATA") + File.separator +
            "ForwardLauncher" + File.separator +
            "FLauncherData" + File.separator;

    public static List<DownloadContext> downloadContexts = new ArrayList<>();
}
