package org.colonelkai.mod.network;

import org.colonelkai.mod.Mod;
import org.colonelkai.tasks.getter.transfer.download.DownloadTask;
import org.colonelkai.tasks.getter.transfer.zip.UnzipTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.Function;

public class ZippedModDownloadTask extends DownloadTask<File> {

    private final Mod mod;
    private boolean isDownloading;
    private final UnzipTask unzip;

    public ZippedModDownloadTask(Mod mod, Function<Mod, URL> input, File downloadTo) throws IOException {
        super(input.apply(mod).openStream(), new FileOutputStream(downloadTo), (os) -> downloadTo);
        File folder = new File(downloadTo.getParentFile(), "source");
        this.unzip = new UnzipTask(downloadTo, folder);
        this.mod = mod;
        this.onComplete(zipFile -> this.unzip.getAsynced().start());
    }

    public void applyDownloadContext(DownloadContext context) {
        context.getOnCompleteExtract().forEach(this.unzip::onComplete);
        context.getOnCompleteDownload().forEach(this::onComplete);
        context.getOnProgressExtract().forEach(this.unzip::onProgressUpdate);
        context.getOnProgressDownload().forEach(this::onProgressUpdate);
    }

    public UnzipTask getUnzipTask() {
        return this.unzip;
    }

    public boolean isDownloading() {
        return this.isDownloading;
    }

    public Mod getMod() {
        return this.mod;
    }

    @Override
    public File get() throws IOException {
        this.isDownloading = true;
        File file = super.get();
        this.isDownloading = false;
        return file;
    }

    public static <T> DownloadTask<T> of(URL url, File file, Function<OutputStream, T> mapper) throws IOException {
        return new DownloadTask<>(url.openStream(), new FileOutputStream(file), mapper);
    }
}
