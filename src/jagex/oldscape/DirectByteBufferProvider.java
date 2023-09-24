package jagex.oldscape;

import java.nio.ByteBuffer;

public class DirectByteBufferProvider extends ByteBufferProvider {

  public static int[] block;
  public static int[] anIntArray1136;

  ByteBuffer internal;

  public DirectByteBufferProvider() {

  }

  public byte[] get() {
    byte[] allocate = new byte[internal.capacity()];
    internal.position(0);
    internal.get(allocate);
    return allocate;
  }

  public void allocate(byte[] var1) {
    internal = ByteBuffer.allocateDirect(var1.length);
    internal.position(0);
    internal.put(var1);
  }
}
