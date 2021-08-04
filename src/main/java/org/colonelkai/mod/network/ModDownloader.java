package org.colonelkai.mod.network;

import javafx.application.Platform;
import org.colonelkai.application.Values;
import org.colonelkai.guielements.stages.MainStageHandler;
import org.colonelkai.mod.Mod;
import org.colonelkai.tasks.getter.transfer.download.DownloadTask;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class ModDownloader {

    private static boolean requiresUpdate(Mod oldMod, Mod newMod) {
        return oldMod.getModVersion() < newMod.getModVersion();
    }

    public static void updateMods(DownloadContext context, Collection<Mod> oldMods, Collection<Mod> newMods) {
        oldMods.parallelStream().forEach(m -> {
            try {
                updateMod(context, m, newMods);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void updateMod(DownloadContext context, Mod mod, Collection<Mod> mods) throws IOException {
        Optional<Mod> opNewMod = mods.parallelStream().filter(m -> m.getModID().equals(mod.getModID())).findAny();
        if (/*mod.isInstalled() ||*/ opNewMod.isEmpty() || requiresUpdate(mod, opNewMod.get())) {
            mod.deleteMod();

            if (opNewMod.isPresent()) {
                downloadModAsynced(context, opNewMod.get());
            }
        }
    }

    private static File getFilePath(Mod mod) {
        return new File(Values.FLAUNCHER_DATA_PATH + mod.getModID());
    }

    private static ZippedModDownloadTask downloadZipAsynced(DownloadContext context, Mod mod) throws IOException {
        File downloadTo = new File(getFilePath(mod), mod.getModID() + ".zip");
        ZippedModDownloadTask task = new ZippedModDownloadTask(mod, Mod::getDownloadURL, downloadTo);
        task.applyDownloadContext(context);
        return task;
    }

    private static DownloadTask<File> downloadAsynced(DownloadContext context, Mod mod, Function<Mod, URL> url, String target) throws IOException {
        File downloadTo = new File(getFilePath(mod), target);
        DownloadTask<File> task = DownloadTask.of(url.apply(mod), downloadTo, (os) -> downloadTo);
        context.getOnCompleteDownload().forEach(task::onComplete);
        return task;
    }

    private static DownloadTask<File> downloadIconAsynced(DownloadContext context, Mod mod) throws IOException {
        return downloadAsynced(context, mod, Mod::getIconURL, "icon.png");
    }

    private static DownloadTask<File> downloadBigPictureAsynced(DownloadContext context, Mod mod) throws IOException {
        return downloadAsynced(context, mod, Mod::getBgPictureURL, "bg.png");
    }

    public static void downloadModAsynced(DownloadContext context, Mod mod) throws IOException {
        File mainDir = getFilePath(mod);
        Files.createDirectories(mainDir.toPath());
        ZippedModDownloadTask zipDownload = downloadZipAsynced(context, mod);
        Values.MOD_TASKS.add(zipDownload);

        MainStageHandler.downloadsList.update();

        zipDownload.getUnzipTask().onComplete(a -> {
            Values.MOD_TASKS.remove(zipDownload);
            Platform.runLater(MainStageHandler.downloadsList::update);
        });

        downloadIconAsynced(context, mod).getAsynced().start();
        downloadBigPictureAsynced(context, mod).getAsynced().start();
        zipDownload.getAsynced().start();
    }
}
