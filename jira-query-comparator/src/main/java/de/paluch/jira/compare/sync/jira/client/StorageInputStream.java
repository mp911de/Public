package de.paluch.jira.compare.sync.jira.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Inputstream that stores everything what was read from a underlying InputStream (delegate).
 * 
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a>
 */
public class StorageInputStream extends InputStream {

    private final ByteArrayOutputStream storage = new ByteArrayOutputStream();
    private final InputStream delegate;

    /**
     * @param delegate
     */
    public StorageInputStream(InputStream delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {
        delegate.close();
        storage.close();
        super.close();
    }

    /**
     * @return byte[]
     */
    public byte[] getStoredData() {
        return storage.toByteArray();
    }

    /**
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {
        int data = delegate.read();
        if (data != -1) {
            storage.write(data);
            System.err.write(data);
        }
        return data;
    }

}
