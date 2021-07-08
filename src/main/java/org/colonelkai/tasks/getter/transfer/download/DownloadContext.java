package org.colonelkai.tasks.getter.transfer.download;

import org.colonelkai.mod.Mod;

public class DownloadContext {

    /*
    no idea what i'm doing lol
     */

    private Mod mod;

    public DownloadTask getTask() {
        return task;
    }

    public void setTask(DownloadTask task) {
        this.task = task;
    }

    private final long size;
    private boolean isDownloading;

    DownloadTask task;

    public DownloadContext(Mod mod, long size, DownloadTask task) {
        this.mod = mod;
        this.size = size;
        this.task = task;
    }

    public long getSize() {
        return this.size;
    }

    public Mod getMod() {
        return this.mod;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }
}
