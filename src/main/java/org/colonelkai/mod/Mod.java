package org.colonelkai.mod;

import java.io.File;
import java.net.URL;

public class Mod {
    private String modID;
    private String modName;
    private String modVersion;
    private String modDescription;

    private boolean isInstalled;
    private long bytesToDownload;

    private URL downloadURL;
    private URL iconURL;
    private URL bgPictureURL;

    private File cachedIcon;
    private File cachedBGPicture;
    private File localDir;



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

    public String getModVersion() {
        return modVersion;
    }
    public void setModVersion(String modVersion) {
        this.modVersion = modVersion;
    }

    public String getModDescription() {
        return modDescription;
    }
    public void setModDescription(String modDescription) {
        this.modDescription = modDescription;
    }

    public boolean isInstalled() {
        return isInstalled;
    }
    public void setInstalled(boolean installed) {
        isInstalled = installed;
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

    public File getCachedIcon() {
        return cachedIcon;
    }
    public void setCachedIcon(File cachedIcon) {
        this.cachedIcon = cachedIcon;
    }

    public File getCachedBGPicture() {
        return cachedBGPicture;
    }
    public void setCachedBGPicture(File cachedBGPicture) {
        this.cachedBGPicture = cachedBGPicture;
    }

    public File getLocalDir() {
        return localDir;
    }
    public void setLocalDir(File localDir) {
        this.localDir = localDir;
    }
}
