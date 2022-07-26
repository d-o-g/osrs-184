package jag.opcode;

import java.io.IOException;
import java.net.Socket;

public class AsyncConnection extends Connection {

    public static int anInt1210;

    final Socket socket;
    AsyncOutputStream output;
    AsyncInputStream input;

    public AsyncConnection(Socket socket, int inputCapacity, int outputCapacity) throws IOException {
        this.socket = socket;
        socket.setSoTimeout(30000);
        socket.setTcpNoDelay(true);
        socket.setReceiveBufferSize(65536);
        socket.setSendBufferSize(65536);
        input = new AsyncInputStream(socket.getInputStream(), inputCapacity);
        output = new AsyncOutputStream(socket.getOutputStream(), outputCapacity);
    }

    public void stop() {
        output.close();

        try {
            socket.close();
        } catch (IOException ignored) {

        }

        input.close();
    }

    public boolean available(int amount) throws IOException {
        return input.available(amount);
    }

    public int read(byte[] payload, int caret, int length) throws IOException {
        return input.read(payload, caret, length);
    }

    public void write(byte[] payload, int caret, int length) throws IOException {
        output.write(payload, caret, length);
    }

    public int available() throws IOException {
        return input.available();
    }

    public int read() throws IOException {
        return input.read();
    }

    protected void finalize() {
        stop();
    }
}
