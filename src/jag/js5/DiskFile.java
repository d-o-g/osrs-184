package jag.js5;

import java.io.*;

public final class DiskFile {

    public final long length;
    public long caret;
    public RandomAccessFile file;

    public DiskFile(File file, String mode, long length) throws IOException {
        if (length == -1L) {
            length = Long.MAX_VALUE;
        }

        if (file.length() > length) {
            file.delete();
        }

        this.file = new RandomAccessFile(file, mode);
        this.length = length;
        caret = 0L;
        int var5 = this.file.read();
        if (var5 != -1 && !mode.equals("r")) {
            this.file.seek(0L);
            this.file.write(var5);
        }

        this.file.seek(0L);
    }

    public void close(boolean sync) throws IOException {
        if (file != null) {
            if (sync) {
                try {
                    file.getFD().sync();
                } catch (SyncFailedException ignored) {
                }
            }

            file.close();
            file = null;
        }

    }

    public long length() throws IOException {
        return file.length();
    }

    public void close() throws IOException {
        close(false);
    }

    public int read(byte[] buffer, int caret, int length) throws IOException {
        int read = file.read(buffer, caret, length);
        if (read > 0) {
            this.caret += read;
        }

        return read;
    }

    void seek(long caret) throws IOException {
        file.seek(caret);
        this.caret = caret;
    }

    public void write(byte[] buffer, int caret, int length) throws IOException {
        if ((long) length + this.caret > this.length) {
            file.seek(this.length);
            file.write(1);
            throw new EOFException();
        }
        file.write(buffer, caret, length);
        this.caret += length;
    }

    protected void finalize() throws Throwable {
        if (file != null) {
            close();
        }
    }
}
