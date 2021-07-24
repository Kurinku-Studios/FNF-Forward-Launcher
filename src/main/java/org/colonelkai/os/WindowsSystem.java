package org.colonelkai.os;

import java.io.File;
import java.util.stream.Stream;

public class WindowsSystem implements OperatingSystem {
    @Override
    public String getTypeName() {
        return "Windows";
    }

    @Override
    public boolean canExecute(File file) {
        String name = file.getName().toLowerCase();
        return Stream.of(this.getExecutableFileType()).anyMatch(type -> name.endsWith("." + type));
    }

    @Override
    public String[] getExecutableFileType() {
        return new String[]{
                "exe",
                "msi"
        };
    }
}
