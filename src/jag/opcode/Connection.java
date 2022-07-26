package jag.opcode;

import java.io.IOException;

public abstract class Connection {

    protected Connection() {

    }

    public abstract void stop();

    public abstract boolean available(int amount) throws IOException;

    public abstract int read(byte[] payload, int caret, int length) throws IOException;

    public abstract void write(byte[] payload, int caret, int length) throws IOException;

    public abstract int available() throws IOException;

    public abstract int read() throws IOException;
}
