package org.colonelkai.mod;

import org.colonelkai.mod.network.Values;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

public class Mod {
    private String modID;
    private String modName;
    private String modDescription;
    private String modDev;
    private String execPath;

    private long modVersion;
    private long bytesToDownload;

    private URL downloadURL;
    private URL iconURL;
    private URL bgPictureURL;

    private InputStream cachedIcon;
    private InputStream cachedBGPicture;

    private File localDir;

    public Mod(String modID, String modName, String modDescription, String modDev, String execPath, long modVersion, long bytesToDownload,
               URL downloadURL, URL iconURL, URL bgPictureURL) {
        this.modID = modID;
        this.modName = modName;
        this.modDev = modDev;
        this.modVersion = modVersion;
        this.modDescription = modDescription;
        this.execPath = execPath;
        this.bytesToDownload = bytesToDownload;
        this.downloadURL = downloadURL;
        this.iconURL = iconURL;
        this.bgPictureURL = bgPictureURL;
    }


    public Mod(String modID, String modName, long modVersion) {
        this.modID = modID;
        this.modName = modName;
        this.modVersion = modVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mod mod)) {
            return false;
        }
        if (!mod.getModID().equals(this.getModID())) {
            return false;
        }
        return mod.getModVersion() == this.getModVersion();
    }

    public void run() {
        new Thread(() -> {
            try {
                Process process = Runtime.getRuntime().exec(
                        Values.FLAUNCHER_DATA_PATH + this.getModID() + File.separator + "source"
                                + File.separator + this.execPath,
                        null,
                        new File(Values.FLAUNCHER_DATA_PATH + this.getModID() + File.separator + "source"
                                + File.separator + this.execPath).getParentFile()
                );

                new Thread(() -> {
                    InputStream regularStream = process.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(regularStream));
                    br.lines().forEach(System.out::println);
                }).start();
                new Thread(() -> {
                    InputStream regularStream = process.getErrorStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(regularStream));
                    br.lines().forEach(System.out::println);
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean isInstalled() {
        File file = new File(Values.FLAUNCHER_DATA_PATH + modID + File.separator + "source" + File.separator + execPath);
        return file.exists();
        /*File[] files = file.listFiles(File::isFile);
        return files != null && files.length != 0;*/
    }

    public void deleteMod() {
        try {
            Files.walk(Path.of(Values.FLAUNCHER_DATA_PATH + modID))
                    .map(Path::toFile)
                    .filter(File::exists)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getModDev() {
        return modDev;
    }

    public void setModDev(String modDev) {
        this.modDev = modDev;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public long getModVersion() {
        return modVersion;
    }

    public void setModVersion(long modVersion) {
        this.modVersion = modVersion;
    }

    public String getModDescription() {
        return modDescription;
    }

    public void setModDescription(String modDescription) {
        this.modDescription = modDescription;
    }

    public long getBytesToDownload() {
        return bytesToDownload;
    }

    public void setBytesToDownload(long bytesToDownload) {
        this.bytesToDownload = bytesToDownload;
    }

    public URL getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(URL downloadURL) {
        this.downloadURL = downloadURL;
    }

    public URL getIconURL() {
        return iconURL;
    }

    public void setIconURL(URL iconURL) {
        this.iconURL = iconURL;
    }

    public URL getBgPictureURL() {
        return bgPictureURL;
    }

    public void setBgPictureURL(URL bgPictureURL) {
        this.bgPictureURL = bgPictureURL;
    }

    public Optional<InputStream> getCachedIcon() {
        return Optional.ofNullable(cachedIcon);
    }

    public void setCachedIcon(InputStream cachedIcon) {
        this.cachedIcon = cachedIcon;
    }

    public Optional<InputStream> getCachedBGPicture() {
        return Optional.ofNullable(cachedBGPicture);
    }

    public void setCachedBGPicture(InputStream cachedBGPicture) {
        this.cachedBGPicture = cachedBGPicture;
    }

    public File getLocalDir() {
        return localDir;
    }

    public void setLocalDir(File localDir) {
        this.localDir = localDir;
    }
}
