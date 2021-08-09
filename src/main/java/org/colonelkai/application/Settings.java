package org.colonelkai.application;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;

public class Settings {
    // all of the settings :)
    // default values?
    private boolean isDarkMode;
    private Path defaultInstallFolder = Path.of("test");
    // there isn't much right now but we will add more surely.

    @Deprecated
    public HashMap<String, String> getSettingDisplayNames() {
        HashMap<String, String> displayNames = new HashMap();
        displayNames.put("darkMode", "Dark Mode");
        displayNames.put("defaultInstallFolder", "Default Install Folder");
        return displayNames;
    }

    public String formatFieldName(Field field) {
        String fieldName = field.getName();
        StringBuilder builder = new StringBuilder();
        for (int I = 0; I < fieldName.length(); I++) {
            char at = fieldName.charAt(I);
            if (I == 0) {
                builder.append(Character.toUpperCase(at));
                continue;
            }
            if (Character.isUpperCase(at)) {
                builder.append(" ");
            }
            builder.append(at);
        }
        return builder.toString();
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
