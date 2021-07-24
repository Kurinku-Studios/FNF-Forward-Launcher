package org.colonelkai.os;

import java.io.File;

public interface OperatingSystem {

    String getTypeName();

    boolean canExecute(File file);

    @Deprecated
    String[] getExecutableFileType();
}
