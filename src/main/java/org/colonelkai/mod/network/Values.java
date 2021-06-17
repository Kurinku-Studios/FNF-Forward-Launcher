package org.colonelkai.mod.network;

import java.io.File;

public interface Values {
    public static final String FLAUNCHER_DATA_PATH = System.getenv("APPDATA") + File.separator +
            "ForwardLauncher" + File.separator +
            "FLauncherData" + File.separator;


}
