package org.colonelkai.mod.network;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.colonelkai.mod.Mod;

import javax.lang.model.type.NullType;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipFile;


public class ModDownloader {
    // will do what it says, retrieve a mod from a specified repo.

    private static void updateAfterCompare(Mod oldMod, Mod newMod) {
        if(newMod.getModVersion() > oldMod.getModVersion()) {
            oldMod.deleteMod();
            try {
                ModDownloader.downloadMod(newMod);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateAllMods(Set<Mod> oldModSet, Set<Mod> newModSet, Label loadingLabel) {
        // NOTE: "old mod" means a mod object that has data that has not been made from the newest data that has been
        // pulled from central repo. "new mod" means the exact opposite, as in, it has the up-to-date data.

        HashMap<Mod, Mod> toBeUpdated = new HashMap<>();

        oldModSet
                .parallelStream()
                .forEach( oldMod -> { // for every old mod
                    Optional<Mod> matchingNewMod = newModSet.parallelStream() // try to find a matching new mod
                            .filter(newMod -> newMod.getModID().equals(oldMod.getModID()))
                            .findAny();
                    if(matchingNewMod.isPresent()) {
                        // download mod if matched one found
                        loadingLabel.setText("Updating mod: "+oldMod.getModName());
                        toBeUpdated.put(oldMod, matchingNewMod.get());

                    };
                    // I am aware if there is no matching new mod, the old mod will just be deleted. It is intentional.
                    // This ensures if a mod is removed from the central repo, it is deleted from the launcher :)
                });


    }

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

    public static void downloadMod(Mod mod) throws IOException {
        File mainDir = new File(Values.FLAUNCHER_DATA_PATH+mod.getModID());
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
        if(!(destDir.getParentFile().exists())) destDir.getParentFile().mkdir();
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
