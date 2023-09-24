package jagex.oldscape;

import jagex.jagex3.sound.Class97;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public final class SerializableLong implements Serializable {

  public static ReferenceTable aReferenceTable645;

  public static int method466(CharSequence seq, int var1) {
    return Class97.method536(seq, var1);
  }

  void encodeAsLong(Long value, Buffer buffer) {
    buffer.p8(value);
  }

  public Object decode(Buffer buffer) {
    return buffer.g8();
  }

  public void encode(Object value, Buffer buffer) {
    encodeAsLong((Long) value, buffer);
  }
}
