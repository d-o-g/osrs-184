package jagex.oldscape.shared.prot;

import jagex.datastructure.Node;
import jagex.messaging.*;
import jagex.oldscape.client.client;

public class OutgoingPacket extends Node {

  public static final OutgoingPacket[] cache = new OutgoingPacket[300];

  public static int index = 0;

  public BitBuffer buffer;

  public int size;
  int payloadSize;

  ClientProt meta;

  OutgoingPacket() {

  }

  public static OutgoingPacket prepare(ClientProt meta, IsaacCipher cipher) {
    OutgoingPacket packet;
    if (index == 0) {
      packet = new OutgoingPacket();
    } else {
      packet = cache[--index];
    }

    packet.meta = meta;
    packet.payloadSize = meta.size;
    if (packet.payloadSize == -1) {
      packet.buffer = new BitBuffer(260);
    } else if (packet.payloadSize == -2) {
      packet.buffer = new BitBuffer(10000);
    } else if (packet.payloadSize <= 18) {
      packet.buffer = new BitBuffer(20);
    } else if (packet.payloadSize <= 98) {
      packet.buffer = new BitBuffer(100);
    } else {
      packet.buffer = new BitBuffer(260);
    }

    packet.buffer.setCipher(cipher);
    packet.buffer.pheader(packet.meta.opcode);
    packet.size = 0;
    return packet;
  }

  public static OutgoingPacket prepareLoginPacket() {
    OutgoingPacket packet;
    if (index == 0) {
      packet = new OutgoingPacket();
    } else {
      packet = cache[--index];
    }

    packet.meta = null;
    packet.payloadSize = 0;
    packet.buffer = new BitBuffer(5000);
    return packet;
  }

  public static void method1343(String var0) {
    if (!var0.equals("")) {
      OutgoingPacket packet = prepare(ClientProt.JOIN_CLANCHANNEL, client.stream.encryptor);
      packet.buffer.p1(Buffer.stringLengthPlusOne(var0));
      packet.buffer.pcstr(var0);
      client.stream.writeLater(packet);
    }
  }

  public void cache() {
    if (index < cache.length) {
      cache[++index - 1] = this;
    }
  }
}
