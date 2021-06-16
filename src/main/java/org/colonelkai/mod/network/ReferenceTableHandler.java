package org.colonelkai.mod.network;

import org.colonelkai.mod.Mod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

public class ReferenceTableHandler {
    // class that will handle the updating of the local reference table and possibly other functions.

    public static Set<Mod> getAllMods() {
        File jsonFilePath = new File(System.getenv("APPDATA") + File.separator + "ForwardLauncher"
        + "referencetable.json");



        return Collections.emptySet();
    }

}
