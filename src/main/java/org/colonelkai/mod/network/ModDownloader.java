package org.colonelkai.mod.network;

import org.colonelkai.mod.Mod;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ModDownloader {
    // will do what it says, retrieve a mod from a specified repo.

    private static final String FLAUNCHER_DATA_PATH = System.getenv("APPDATA") + File.separator +
            "ForwardLauncher" + File.separator +
            "FLauncherData" + File.separator;

    private static void downloadZip(Mod mod) throws IOException {
        // download mod itself
        URL downloadURL = mod.getDownloadURL();
        ReadableByteChannel readChannel = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fileOS = new FileOutputStream(
                        FLAUNCHER_DATA_PATH +
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
                        FLAUNCHER_DATA_PATH +
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
                        FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        "bg.png"
        );

        FileChannel writeChannel = fileOS.getChannel();
        writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }

    public static void downloadMod(Mod mod) throws IOException {
        File mainDir = new File(FLAUNCHER_DATA_PATH+mod.getModID());
        mainDir.mkdir();

        downloadZip(mod);
        downloadIcon(mod);
        downloadBgPicture(mod);

       // unzip main mod and delete zip
        File zipFile = new File(
                        FLAUNCHER_DATA_PATH +
                        mod.getModID() + File.separator +
                        mod.getModID() + ".zip"
        );

        File destDir = new File(FLAUNCHER_DATA_PATH + mod.getModID() + File.separator + "source");
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
