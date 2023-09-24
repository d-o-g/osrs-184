package jagex.oldscape;

import jagex.messaging.Buffer;

public interface Serializable {

  Object decode(Buffer buffer);

  void encode(Object value, Buffer buffer);
}
