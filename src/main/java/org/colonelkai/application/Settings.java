package org.colonelkai.application;

import java.nio.file.Path;
import java.util.HashMap;

public class Settings {
    // all of the settings :)
    private boolean isDarkMode;
    private Path defaultInstallFolder;
    // there isn't much right now but we will add more surely.

    // for settings display.
    public HashMap<String, String> displayNames = new HashMap();

    public Settings() {
        this.displayNames.put("darkMode", "Dark Mode");
        this.displayNames.put("defaultInstallFolder", "Default Install Folder");
    }

    // TODO: save/load
    public void saveToFile() {

    }

    public void loadFromFile() {

    }

    public boolean isDarkMode() {
        return isDarkMode;
    }


    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
    }

    public Path getDefaultInstallFolder() {
        return defaultInstallFolder;
    }

    public void setDefaultInstallFolder(Path defaultInstallFolder) {
        this.defaultInstallFolder = defaultInstallFolder;
    }
}
