package org.colonelkai.tasks.getter.transfer.download;

import org.colonelkai.tasks.getter.transfer.AbstractTransferTask;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

public class DownloadTask<T> extends AbstractTransferTask<T, Long> {

    private final InputStream input;
    private final FileOutputStream output;
    private final Function<OutputStream, T> mapper;

    public DownloadTask(InputStream input, FileOutputStream output, Function<OutputStream, T> mapper) {
        this.input = input;
        this.output = output;
        this.mapper = mapper;
    }

    public InputStream getInput() {
        return input;
    }

    public FileOutputStream getOutput() {
        return output;
    }

    public Function<OutputStream, T> getMapper() {
        return this.mapper;
    }

    @Override
    public T get() throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(this.input);
        InteruptableReadableByteChannel wrappedReadableByteChannel = new InteruptableReadableByteChannel(readableByteChannel, this.processEvents);
        FileChannel channel = this.output.getChannel();
        channel.transferFrom(wrappedReadableByteChannel, 0, Long.MAX_VALUE);
        this.output.flush();
        T mapped = this.mapper.apply(this.output);
        this.completeEvents.parallelStream().forEach(e -> e.accept(mapped));
        return mapped;
    }

    public static <T> DownloadTask<T> of(URL url, FileOutputStream output, Function<OutputStream, T> mapper) throws IOException {
        return new DownloadTask<>(url.openStream(), output, mapper);
    }

    public static <T> DownloadTask<T> of(URL url, File file, Function<OutputStream, T> mapper) throws IOException {
        return new DownloadTask<>(url.openStream(), new FileOutputStream(file), mapper);
    }

    public static <T> DownloadTask<T> of(InputStream stream, File file, Function<OutputStream, T> mapper) throws FileNotFoundException {
        return new DownloadTask<>(stream, new FileOutputStream(file), mapper);
    }
}
