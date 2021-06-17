package org.colonelkai.mod.network;

import org.colonelkai.mod.Mod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Set;

public class ReferenceTableHandler {
    // class that will handle the updating of the local reference table and possibly other functions.

    public static void updateReferenceTable() throws IOException {
        URL downloadURL = new URL("https://raw.githubusercontent.com/ColonelKai/FLauncherData/main/referencetable.json");
        ReadableByteChannel readChannel = Channels.newChannel(downloadURL.openStream());
        File file = new File(
                Values.FLAUNCHER_DATA_PATH + "referencetable.json"
        );

        file.delete();

        FileOutputStream fileOS = new FileOutputStream(file.getPath());

        FileChannel writeChannel = fileOS.getChannel();
        writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
    }

    public static Set<Mod> getAllMods() {
        File jsonFilePath = new File(System.getenv("APPDATA") + File.separator + "ForwardLauncher"
        + File.separator + "FLauncherData" + File.separator + "referencetable.json");

        JSONParser parser = new JSONParser();
        Set<Mod> mods = new HashSet<>();

        try(Reader reader = new FileReader(jsonFilePath)){

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray modsJsonArray = (JSONArray) jsonObject.get("modlist");

            for (String modID : (Iterable<String>) modsJsonArray) {
                JSONObject jsonModObject = (JSONObject) jsonObject.get(modID);

                Mod mod = new Mod(
                        modID,
                        (String) jsonModObject.get("modName"),
                        (String) jsonModObject.get("modDescription"),
                        (long) jsonModObject.get("modVersion"),
                        (long) jsonModObject.get("bytesToDownload"),
                        new URL((String) jsonModObject.get("downloadURL")),
                        new URL((String) jsonModObject.get("iconURL")),
                        new URL((String) jsonModObject.get("bgPictureURL"))
                );

                mods.add(mod);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mods;
    }

}
