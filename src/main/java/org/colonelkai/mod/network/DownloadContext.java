package org.colonelkai.mod.network;

import org.colonelkai.tasks.getter.transfer.zip.ZipFileContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DownloadContext {

    private final List<Consumer<File>> onCompleteDownload = new ArrayList<>();
    private final List<Consumer<File>> onCompleteExtract = new ArrayList<>();
    private final List<Consumer<ZipFileContext>> onProgressExtract = new ArrayList<>();
    private final List<Consumer<Long>> onProgressDownload = new ArrayList<>();

    public List<Consumer<Long>> getOnProgressDownload() {
        return this.onProgressDownload;
    }

    public void addOnProgressDownload(Consumer<Long> consumers) {
        this.onProgressDownload.add(consumers);
    }

    public List<Consumer<ZipFileContext>> getOnProgressExtract() {
        return this.onProgressExtract;
    }

    public void addOnProgressExtract(Consumer<ZipFileContext> consumers) {
        this.onProgressExtract.add(consumers);
    }

    public List<Consumer<File>> getOnCompleteDownload() {
        return this.onCompleteDownload;
    }

    public void addOnCompleteExtract(Consumer<File> consumers) {
        this.onCompleteExtract.add(consumers);
    }

    public List<Consumer<File>> getOnCompleteExtract() {
        return this.onCompleteExtract;
    }

    public void addOnCompleteDownload(Consumer<File> consumers) {
        this.onCompleteDownload.add(consumers);
    }
}
