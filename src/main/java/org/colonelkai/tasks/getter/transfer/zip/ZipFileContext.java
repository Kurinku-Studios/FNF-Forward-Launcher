package org.colonelkai.tasks.getter.transfer.zip;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileContext {

    private final ZipFile file;
    private final int total;
    private ZipEntry entry;
    private int count;

    public ZipFileContext(ZipFile file) {
        this.file = file;
        this.total = file.size();
    }

    public ZipFile getFile() {
        return this.file;
    }

    public int getTotalCount() {
        return this.total;
    }

    public int getCount() {
        return this.count;
    }

    public ZipEntry getEntry() {
        return this.entry;
    }

    public void nextEntry(ZipEntry entry) {
        this.entry = entry;
        this.count++;
    }
}
