package org.colonelkai.file;

import org.colonelkai.file.json.simple.SimpleJsonFile;

public enum DataFileType {

    JSON(new SimpleJsonFile());

    private final DataFile dataFile;

    DataFileType(DataFile file) {
        this.dataFile = file;
    }

    public DataFile createFile() {
        return this.dataFile;
    }
}
