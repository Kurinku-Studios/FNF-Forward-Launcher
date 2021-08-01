package org.colonelkai.os;

import java.io.File;

public class LinuxSystem implements OperatingSystem {
    @Override
    public String getTypeName() {
        return "Linux";
    }

    @Override
    public boolean canExecute(File file) {
        String name = file.getName().toLowerCase();
        if (!name.contains(".")) {
            return true;
        }
        if (name.endsWith(".tar.gz")) {
            return true;
        }
        if (name.endsWith(".bin")) {
            return true;
        }
        return name.endsWith(".sh");
    }

    @Override
    @Deprecated
    public String[] getExecutableFileType() {
        return new String[]{"sh", "bin", "tar.gz"};
    }
}
