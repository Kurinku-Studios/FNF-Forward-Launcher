package org.colonelkai.tasks.getter.transfer.zip;

import org.colonelkai.tasks.getter.transfer.AbstractTransferTask;

import javax.management.RuntimeErrorException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.ZipFile;

public class UnzipTask extends AbstractTransferTask<File, ZipFileContext> {

    private final File zipFile;
    private final File folder;

    public UnzipTask(File zipFile, File folder) {
        this.folder = folder;
        this.zipFile = zipFile;
    }

    @Override
    public File get() {
        try {
            if (!this.folder.exists()) {
                Files.createDirectories(this.zipFile.toPath());
            }
            ZipFile zip = new ZipFile(this.zipFile);
            ZipFileContext context = new ZipFileContext(zip);
            zip.stream()
                    .filter(zipEntry -> !zipEntry.isDirectory())
                    .forEach(entry -> {
                        InputStream inputStream;
                        try {
                            context.nextEntry(entry);
                            inputStream = zip.getInputStream(entry);
                            File fileToBe = new File(this.folder, entry.getName()); //create location to file to be
                            Files.createDirectories(fileToBe.getParentFile().toPath());
                            Files.copy(inputStream, fileToBe.toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            zip.close();
            Files.delete(this.zipFile.toPath());
        } catch (IOException e) {
            this.exceptionEvents.parallelStream().forEach(ex -> ex.accept(e));
            throw new RuntimeErrorException(new Error(e));
        }
        return this.folder;
    }
}
