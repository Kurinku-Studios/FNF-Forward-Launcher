package org.colonelkai.tasks.getter.transfer.download;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class WrappedReadableByteChannel implements ReadableByteChannel {

    private final ReadableByteChannel channel;
    private final Set<Consumer<Long>> processEvent = new HashSet<>();

    private long readSoFar;

    public WrappedReadableByteChannel(ReadableByteChannel channel, Consumer<Long>... events) {
        this(channel, Arrays.asList(events));
    }

    public WrappedReadableByteChannel(ReadableByteChannel channel, Collection<Consumer<Long>> events) {
        this.channel = channel;
        this.processEvent.addAll(events);
    }

    public void onProgressUpdate(Consumer<Long> consumer) {
        this.processEvent.add(consumer);
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        int read = this.channel.read(dst);
        if (read > 0) {
            this.readSoFar += read;
            this.processEvent.parallelStream().forEach(c -> c.accept(this.readSoFar));
        }
        return read;
    }

    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
