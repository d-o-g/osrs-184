package jag.js5;

import jag.opcode.Buffer;

import java.util.zip.Inflater;

public class GzipDecompressor {

    public Inflater inflater;

    public GzipDecompressor() {
    }

    public void decompress(Buffer buffer, byte[] b) {
        if (buffer.payload[buffer.pos] == 31 && buffer.payload[buffer.pos + 1] == -117) {
            if (inflater == null) {
                inflater = new Inflater(true);
            }

            try {
                inflater.setInput(buffer.payload, buffer.pos + 10, buffer.payload.length - (buffer.pos + 8 + 10));
                inflater.inflate(b);
            } catch (Exception var4) {
                inflater.reset();
                throw new RuntimeException("");
            }

            inflater.reset();
        } else {
            throw new RuntimeException("");
        }
    }
}
