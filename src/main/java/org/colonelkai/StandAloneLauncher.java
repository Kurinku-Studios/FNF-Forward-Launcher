package org.colonelkai;

import org.colonelkai.file.DataFileType;
import org.colonelkai.file.simple.SimpleRootDataNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StandAloneLauncher {

    public static void main(String[] args) {
        if (args.length != 0) {
            Integer gitRun = null;
            StringBuilder tagName = new StringBuilder();
            StringBuilder specifiedVersion = new StringBuilder();
            String argType = "None";
            for (String arg : args) {
                if (arg.startsWith("--")) {
                    argType = arg.substring(2).toLowerCase();
                    continue;
                }
                switch (argType) {
                    case "specifiedversion":
                        specifiedVersion.append(arg).append(" ");
                        break;
                    case "tagname":
                        tagName.append(arg).append(" ");
                        break;
                    case "gitrun":
                        try {
                            gitRun = Integer.parseInt(arg);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println(arg + " is not a whole number");
                            System.exit(0);
                            return;
                        }

                    default:
                        System.err.println("Unknown argument: " + argType + " | value: " + arg);
                }
            }
            SimpleRootDataNode rootNode = new SimpleRootDataNode();
            rootNode.at("ID", "FNF-Forward-Launcher");
            if (!specifiedVersion.isEmpty()) {
                rootNode.at("SpecifiedVersion", specifiedVersion.toString().trim());
            }
            if (!tagName.isEmpty()) {
                rootNode.at("TagName", tagName.toString().trim());
            }
            if (gitRun != null) {
                rootNode.at("GitRun", gitRun);
            }

            File file = new File("src/main/resources/meta/Info.json");
            try {
                Files.createDirectories(file.getParentFile().toPath());
                Files.createFile(file.toPath());
                DataFileType.JSON.createFile().build(rootNode, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
            return;
        }

        ForwardLauncher.main(args);
    }
}
