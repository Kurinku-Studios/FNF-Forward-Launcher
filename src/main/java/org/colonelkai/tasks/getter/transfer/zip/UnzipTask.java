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
                Files.createDirectories(this.folder.toPath());
            }
            ZipFile zip = new ZipFile(this.zipFile);
            ZipFileContext context = new ZipFileContext(zip);
            zip.stream()
                    .filter(zipEntry -> !zipEntry.isDirectory())
                    .forEach(entry -> {
                        try {
                            context.nextEntry(entry);
                            InputStream inputStream = zip.getInputStream(entry);
                            File fileToBe = new File(this.folder, entry.getName()); //create location to file to be
                            Files.deleteIfExists(fileToBe.toPath());

                            this.processEvents.parallelStream().forEach(c -> c.accept(context));
                            Files.createDirectories(fileToBe.getParentFile().toPath());
                            Files.copy(inputStream, fileToBe.toPath());
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            zip.close();
            try {
                Files.delete(this.zipFile.toPath());
            } catch (IOException ex) {
                this.zipFile.deleteOnExit();
            }
            this.completeEvents.parallelStream().forEach(c -> c.accept(this.zipFile));
        } catch (IOException e) {
            System.err.println("Could not extract: " + this.zipFile.getPath());
            this.exceptionEvents.parallelStream().forEach(ex -> ex.accept(e));
            throw new RuntimeErrorException(new Error(e));
        }
        return this.folder;
    }
}
