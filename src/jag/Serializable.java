package jag;

import jag.opcode.Buffer;

public interface Serializable {

    Object decode(Buffer buffer);

    void encode(Object value, Buffer buffer);
}
