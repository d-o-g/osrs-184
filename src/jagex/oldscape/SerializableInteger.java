package jagex.oldscape;

import jagex.jagex3.js5.Js5Worker;
import jagex.messaging.Buffer;

public final class SerializableInteger implements Serializable {

  public static int method405() {
    byte var2 = 0;
    return var2 + Js5Worker.pendingPriorityCount + Js5Worker.pendingPriorityResponseCount;
  }

  void encodeAsInteger(Integer value, Buffer buffer) {
    buffer.p4(value);
  }

  public Object decode(Buffer buffer) {
    return buffer.g4();
  }

  public void encode(Object value, Buffer buffer) {
    encodeAsInteger((Integer) value, buffer);
  }
}
