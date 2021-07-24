package org.colonelkai.os;

public class WindowsSystem implements OperatingSystem {
    @Override
    public String getTypeName() {
        return "Windows";
    }

    @Override
    public String[] getExecutableFileType() {
        return new String[]{
                "exe",
                "msi"
        };
    }
}
