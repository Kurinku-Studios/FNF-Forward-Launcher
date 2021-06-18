package org.colonelkai.mod.network;

import javafx.scene.control.Label;
import org.colonelkai.mod.Mod;
import org.colonelkai.tasks.getter.transfer.download.DownloadTask;
import org.colonelkai.tasks.getter.transfer.zip.UnzipTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipFile;

public class ModDownloader {
    // will do what it says, retrieve a mod from a specified repo.

    @Deprecated
    private static void updateAfterCompare(Mod oldMod, Mod newMod) {
        if (newMod.getModVersion() > oldMod.getModVersion()) {
            oldMod.deleteMod();
            try {
                ModDownloader.downloadMod(newMod);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean requiresUpdate(Mod oldMod, Mod newMod) {
        return oldMod.getModVersion() < newMod.getModVersion();
    }

    public static void updateMods(Collection<Mod> oldMods, Collection<Mod> newMods) {
        oldMods.parallelStream().forEach(m -> {
            try {
                updateMod(m, newMods);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void updateMod(Mod mod, Collection<Mod> mods) throws IOException {
        Optional<Mod> opNewMod = mods.parallelStream().filter(m -> m.getModID().equals(mod.getModID())).findAny();

        if( (opNewMod.isEmpty() || requiresUpdate(mod, opNewMod.get())) && mod.isInstalled())  {
            mod.deleteMod();

            if(opNewMod.isPresent()) {
                downloadModAsynced(opNewMod.get());
            }
        }
    }

    @Deprecated
    public static void updateAllMods(Set<Mod> oldModSet, Set<Mod> newModSet, Label loadingLabel) {
        // NOTE: "old mod" means a mod object that has data that has not been made from the newest data that has been
        // pulled from central repo. "new mod" means the exact opposite, as in, it has the up-to-date data.

        HashMap<Mod, Mod> toBeUpdated = new HashMap<>();

        oldModSet
                .parallelStream()
                .forEach(oldMod -> { // for every old mod
                    Optional<Mod> matchingNewMod = newModSet.parallelStream() // try to find a matching new mod
                            .filter(newMod -> newMod.getModID().equals(oldMod.getModID()))
                            .findAny();
                    if (matchingNewMod.isPresent()) {
                        // download mod if matched one found
                        loadingLabel.setText("Updating mod: " + oldMod.getModName());
                        toBeUpdated.put(oldMod, matchingNewMod.get());

                    }
                    ;
                    // I am aware if there is no matching new mod, the old mod will just be deleted. It is intentional.
                    // This ensures if a mod is removed from the central repo, it is deleted from the launcher :)
                });


    }

    private static File getFilePath(Mod mod) {
        return new File(Values.FLAUNCHER_DATA_PATH + mod.getModID());
    }

    private static DownloadTask<File> downloadZipAsynced(Mod mod) throws IOException {
        return downloadAsynced(mod, mod.getDownloadURL(), mod.getModID() + ".zip");
    }

    private static DownloadTask<File> downloadAsynced(Mod mod, URL url, String target) throws IOException {
        File downloadTo = new File(getFilePath(mod), target);
        return DownloadTask.of(url, downloadTo, (os) -> downloadTo);
    }

    @Deprecated
    private static void downloadZip(Mod mod) throws IOException {
        // download mod itself
        URL downloadURL = mod.getDownloadURL();
        ReadableByteChannel readChannel = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fileOS = new FileOutputStream(
                Values.FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        mod.getModID() + ".zip"
        );

        FileChannel writeChannel = fileOS.getChannel();
        writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }

    private static DownloadTask<File> downloadIconAsynced(Mod mod) throws IOException {
        return downloadAsynced(mod, mod.getIconURL(), "icon.png");
    }

    @Deprecated
    private static void downloadIcon(Mod mod) throws IOException {
        // download icon
        URL downloadURL = mod.getIconURL();
        ReadableByteChannel readChannel = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fileOS = new FileOutputStream(
                Values.FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        "icon.png"
        );

        FileChannel writeChannel = fileOS.getChannel();
        writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }

    private static DownloadTask<File> downloadBigPictureAsynced(Mod mod) throws IOException {
        return downloadAsynced(mod, mod.getBgPictureURL(), "bg.png");
    }

    @Deprecated
    private static void downloadBgPicture(Mod mod) throws IOException {
        // download bg picture
        URL downloadURL = mod.getIconURL();
        ReadableByteChannel readChannel = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fileOS = new FileOutputStream(
                Values.FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        "bg.png"
        );

        FileChannel writeChannel = fileOS.getChannel();
        writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }

    public static void downloadModAsynced(Mod mod) throws IOException {
        File mainDir = getFilePath(mod);    
        Files.createDirectories(mainDir.toPath());
        DownloadTask<File> zipDownload = downloadZipAsynced(mod);
        zipDownload.onComplete(zipFile -> {
            File folder = new File(getFilePath(mod), "source");
            UnzipTask task = new UnzipTask(zipFile, folder);
            task.getAsynced().start();
        });

        downloadIconAsynced(mod).getAsynced().start();
        downloadBigPictureAsynced(mod).getAsynced().start();
        zipDownload.getAsynced().start();

    }

    @Deprecated
    public static void downloadMod(Mod mod) throws IOException {
        File mainDir = new File(Values.FLAUNCHER_DATA_PATH + mod.getModID());
        mainDir.mkdir();

        downloadZip(mod);
        downloadIcon(mod);
        downloadBgPicture(mod);

        // unzip main mod and delete zip
        File zipFile = new File(
                Values.FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        mod.getModID() + ".zip"
        );

        File destDir = new File(Values.FLAUNCHER_DATA_PATH + mod.getModID() + File.separator + "source");
        if (!(destDir.getParentFile().exists())) destDir.getParentFile().mkdir();
        destDir.mkdir();

        ZipFile zip = new ZipFile(zipFile);
        zip.stream()
                .filter(zipEntry -> !zipEntry.isDirectory())
                .forEach(entry -> {
                    InputStream inputStream;
                    try {
                        inputStream = zip.getInputStream(entry);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    File fileToBe = new File(destDir, entry.getName()); //create location to file to be
                    fileToBe.getParentFile().mkdirs(); //makes the folder
                    try {
                        Files.copy(inputStream, fileToBe.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        zip.close();

        zipFile.delete(); // poof
    }
}
