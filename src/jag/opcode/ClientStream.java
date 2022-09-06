package jag.opcode;

import jag.commons.collection.LinkedList;

import java.io.IOException;

public class ClientStream {

    public final Buffer outbuffer;
    public final LinkedList<OutgoingPacket> outgoing;
    public final BitBuffer inbuffer;

    public IsaacCipher encryptor;
    public Connection connection;

    public boolean alive;

    public int buffered;
    public int idleReadTicks;
    public int idleWriteTicks;
    public int incomingPacketSize;

    public ServerProt currentIncomingPacket;
    public ServerProt lastIncomingPacket;
    public ServerProt secondLastIncomingPacket;
    public ServerProt thirdLastIncomingPacket;

    public ClientStream() {
        outgoing = new LinkedList<>();
        buffered = 0;
        outbuffer = new Buffer(5000);
        inbuffer = new BitBuffer(40000);
        currentIncomingPacket = null;
        incomingPacketSize = 0;
        alive = true;
        idleReadTicks = 0;
        idleWriteTicks = 0;
    }

    public Connection unwrap() {
        return connection;
    }

    public void stop() {
        if (connection != null) {
            connection.stop();
            connection = null;
        }
    }

    public final void writeLater(OutgoingPacket packet) {
        outgoing.pushBack(packet);
        packet.size = packet.buffer.pos;
        packet.buffer.pos = 0;
        buffered += packet.size;
    }

    public void kill() {
        connection = null;
    }

    public final void drop() {
        outgoing._clear();
        buffered = 0;
    }

    public final void flush() throws IOException {
        if (connection != null && buffered > 0) {
            outbuffer.pos = 0;

            while (true) {
                OutgoingPacket packet = outgoing.head();
                if (packet == null || packet.size > outbuffer.payload.length - outbuffer.pos) {
                    connection.write(outbuffer.payload, 0, outbuffer.pos);
                    idleWriteTicks = 0;
                    break;
                }

                outbuffer.pdata(packet.buffer.payload, 0, packet.size);
                buffered -= packet.size;
                packet.unlink();
                packet.buffer.cache();
                packet.cache();
            }
        }

    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
