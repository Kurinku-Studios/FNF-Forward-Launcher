package org.colonelkai.application;

import java.nio.file.Path;

public class Settings {
    // all of the settings :)
    boolean isDarkMode;
    Path defaultInstallFolder;
    // there isn't much right now but we will add more surely.
    // possible way to make it so that you can build games that you download? This would be a huge advantage to FLauncher

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
