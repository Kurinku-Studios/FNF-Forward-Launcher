package org.colonelkai.guielements.nodes.settings;

import org.colonelkai.application.Settings;

import java.awt.*;

public class SettingsScrollPane extends ScrollPane {

    Settings temporarySettings = new Settings();

    public Settings getTemporarySettings() {
        return temporarySettings;
    }

    public SettingsScrollPane() {

     }
}
