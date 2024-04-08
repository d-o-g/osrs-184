package jagex.core.compression.gzip;

import jagex.messaging.Buffer;

import java.util.zip.Inflater;

public class GzipDecompressor {

  public Inflater inflater;

  public void decompress(Buffer buffer, byte[] target) {
    if (buffer.payload[buffer.pos] != 31 || buffer.payload[buffer.pos + 1] != -117) {
      throw new RuntimeException("");
    }

    if (inflater == null) {
      inflater = new Inflater(true);
    }

    try {
      inflater.setInput(buffer.payload, buffer.pos + 10, buffer.payload.length - (buffer.pos + 8 + 10));
      inflater.inflate(target);
    } catch (Exception e) {
      inflater.reset();
      throw new RuntimeException("");
    }

    inflater.reset();
  }
}
