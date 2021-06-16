package org.colonelkai.mod;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class Mod {
    private String modID;
    private String modName;
    private String modVersion;
    private String modDescription;

    private long bytesToDownload;
    private long modNumericalVersion;

    private URL downloadURL;
    private URL iconURL;
    private URL bgPictureURL;

    private InputStream cachedIcon;
    private InputStream cachedBGPicture;

    private File localDir;

    public Mod(String modID, String modName, String modVersion, String modDescription, long bytesToDownload, long modNumericalVersion,
               URL downloadURL, URL iconURL, URL bgPictureURL) {
        this.modID = modID;
        this.modName = modName;
        this.modVersion = modVersion;
        this.modNumericalVersion = modNumericalVersion;
        this.modDescription = modDescription;
        this.bytesToDownload = bytesToDownload;
        this.downloadURL = downloadURL;
        this.iconURL = iconURL;
        this.bgPictureURL = bgPictureURL;
    }

    public Mod(String modID, String modName, String modVersion) {
        this.modID = modID;
        this.modName = modName;
        this.modVersion = modVersion;
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
