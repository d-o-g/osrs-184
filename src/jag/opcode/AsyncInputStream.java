package jag.opcode;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class AsyncInputStream implements Runnable {

    final int payloadCapacity;
    final byte[] payload;
    final InputStream input;
    final Thread thread;
    int anInt1662;
    int anInt1664;
    IOException error;

    AsyncInputStream(InputStream input, int payloadCapacity) {
        anInt1664 = 0;
        anInt1662 = 0;
        this.input = input;
        this.payloadCapacity = payloadCapacity + 1;
        payload = new byte[this.payloadCapacity];
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    boolean available(int var1) throws IOException {
        if (var1 == 0) {
            return true;
        }
        if (var1 > 0 && var1 < payloadCapacity) {
            synchronized (this) {
                int var3;
                if (anInt1664 <= anInt1662) {
                    var3 = anInt1662 - anInt1664;
                } else {
                    var3 = payloadCapacity - anInt1664 + anInt1662;
                }

                if (var3 < var1) {
                    if (error != null) {
                        throw new IOException(error.toString());
                    }
                    notifyAll();
                    return false;
                }
                return true;
            }
        }
        throw new IOException();
    }

    int read() throws IOException {
        synchronized (this) {
            if (anInt1662 == anInt1664) {
                if (error != null) {
                    throw new IOException(error.toString());
                }
                return -1;
            }
            int var2 = payload[anInt1664] & 255;
            anInt1664 = (anInt1664 + 1) % payloadCapacity;
            notifyAll();
            return var2;
        }
    }

    int read(byte[] var1, int var2, int var3) throws IOException {
        if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
            synchronized (this) {
                int var5;
                if (anInt1664 <= anInt1662) {
                    var5 = anInt1662 - anInt1664;
                } else {
                    var5 = payloadCapacity - anInt1664 + anInt1662;
                }

                if (var3 > var5) {
                    var3 = var5;
                }

                if (var3 == 0 && error != null) {
                    throw new IOException(error.toString());
                }
                if (var3 + anInt1664 <= payloadCapacity) {
                    System.arraycopy(payload, anInt1664, var1, var2, var3);
                } else {
                    int var6 = payloadCapacity - anInt1664;
                    System.arraycopy(payload, anInt1664, var1, var2, var6);
                    System.arraycopy(payload, 0, var1, var6 + var2, var3 - var6);
                }

                anInt1664 = (var3 + anInt1664) % payloadCapacity;
                notifyAll();
                return var3;
            }
        }
        throw new IOException();
    }

    int available() throws IOException {
        synchronized (this) {
            int var2;
            if (anInt1664 <= anInt1662) {
                var2 = anInt1662 - anInt1664;
            } else {
                var2 = payloadCapacity - anInt1664 + anInt1662;
            }

            if (var2 <= 0 && error != null) {
                throw new IOException(error.toString());
            }
            notifyAll();
            return var2;
        }
    }

    void close() {
        synchronized (this) {
            if (error == null) {
                error = new IOException("");
            }

            notifyAll();
        }

        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

    public void run() {
        while (true) {
            int var2;
            synchronized (this) {
                while (true) {
                    if (error != null) {
                        return;
                    }

                    if (anInt1664 == 0) {
                        var2 = payloadCapacity - anInt1662 - 1;
                    } else if (anInt1664 <= anInt1662) {
                        var2 = payloadCapacity - anInt1662;
                    } else {
                        var2 = anInt1664 - anInt1662 - 1;
                    }

                    if (var2 > 0) {
                        break;
                    }

                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            int var5;
            try {
                var5 = input.read(payload, anInt1662, var2);
                if (var5 == -1) {
                    throw new EOFException();
                }
            } catch (IOException var11) {
                synchronized (this) {
                    error = var11;
                    return;
                }
            }

            synchronized (this) {
                anInt1662 = (var5 + anInt1662) % payloadCapacity;
            }
        }
    }
}
