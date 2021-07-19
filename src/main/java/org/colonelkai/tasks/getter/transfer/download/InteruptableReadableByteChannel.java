package org.colonelkai.tasks.getter.transfer.download;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Collection;
import java.util.function.Consumer;

public class InteruptableReadableByteChannel extends WrappedReadableByteChannel {
    public InteruptableReadableByteChannel(ReadableByteChannel channel, Consumer<Long>... events) {
        super(channel, events);
    }

    public InteruptableReadableByteChannel(ReadableByteChannel channel, Collection<Consumer<Long>> events) {
        super(channel, events);
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        if(Thread.currentThread().isInterrupted()) {
            throw new IllegalStateException("Thread was interrupted");
        }
        return super.read(dst);
    }
}
