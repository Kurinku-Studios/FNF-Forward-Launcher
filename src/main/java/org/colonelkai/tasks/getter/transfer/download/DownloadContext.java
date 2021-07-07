package org.colonelkai.tasks.getter.transfer.download;

import org.colonelkai.mod.Mod;

public class DownloadContext {

    /*
    no idea what i'm doing lol
     */

    private Mod mod;
    private final long size;
    private boolean isDownloading;
    private int percentage;

    public DownloadContext(Mod mod, long size) {
        this.mod = mod;
        this.size = size;
    }

    public long getSize() {
        return this.size;
    }

    public Mod getMod() {
        return this.mod;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }
}
