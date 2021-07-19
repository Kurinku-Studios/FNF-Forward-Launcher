package org.colonelkai.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface DataFile {

    String build(DataNode root);

    default void build(DataNode root, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.append(build(root));
        writer.flush();
        writer.close();
    }

    DataNode generateFrom();

    DataNode generateFrom(String contents);

    String[] expectedFileTypes();

}
