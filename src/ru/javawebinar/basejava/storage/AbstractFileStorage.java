package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractFileStorage<SK> extends AbstractStorage<SK> {

    private AbstractStreamStrategy streamStrategy;

    protected AbstractFileStorage(AbstractStreamStrategy streamStrategy) {
        this.streamStrategy = streamStrategy;
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        streamStrategy.doWrite(r, os);
    }

    protected Resume doRead(InputStream is) throws IOException {
        return streamStrategy.doRead(is);
    }
}
