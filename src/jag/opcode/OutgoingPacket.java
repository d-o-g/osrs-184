package jag.opcode;

import jag.commons.collection.Node;

public class OutgoingPacket extends Node {

    public static final OutgoingPacket[] cache = new OutgoingPacket[300];
    public static byte[][] aByteArrayArray114;
    public static int anInt112 = 0;

    public BitBuffer buffer;
    public int size;
    ClientProt meta;
    int payloadSize;

    OutgoingPacket() {
    }

    public static OutgoingPacket prepare(ClientProt meta, IsaacCipher cipher) {
        OutgoingPacket packet;
        if (anInt112 == 0) {
            packet = new OutgoingPacket();
        } else {
            packet = cache[--anInt112];
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
        if (anInt112 == 0) {
            packet = new OutgoingPacket();
        } else {
            packet = cache[--anInt112];
        }

        packet.meta = null;
        packet.payloadSize = 0;
        packet.buffer = new BitBuffer(5000);
        return packet;
    }

    public void cache() {
        if (anInt112 < cache.length) {
            cache[++anInt112 - 1] = this;
        }
    }
}
