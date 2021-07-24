package org.colonelkai.os;

import java.io.File;

public class MacSystem implements OperatingSystem {
    @Override
    public String getTypeName() {
        return "MacOS";
    }

    @Override
    public boolean canExecute(File file) {
        return file.getName().endsWith(".app");
    }

    @Override
    @Deprecated
    public String[] getExecutableFileType() {
        return new String[0];
    }
}
