package jag.commons.time;

import jag.game.client;
import jag.game.relationship.ChatLine;
import jag.opcode.ClientProt;
import jag.opcode.OutgoingPacket;
import jag.statics.CursorEntities;
import jag.statics.Statics53;

public abstract class Clock {

    public static long aLong1564;
    public static long aLong1565;

    public static int anInt889;

    public Clock() {

    }

    public static void processDialogActionPacket(int var0, int var1) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.DIALOG_ACTION, client.netWriter.encryptor);
        packet.buffer.ip4(var0);
        packet.buffer.p2(var1);
        client.netWriter.writeLater(packet);
    }

    public static int method722() {
        return CursorEntities.viewportMouseX;
    }

    public static int method723(int key) {
        ChatLine line = Statics53.CHAT_LINE_TABLE.lookup(key);
        if (line == null) {
            return -1;
        }
        return line.previousDoubly == Statics53.CHAT_LINE_QUEUE.sentinel ? -1 : ((ChatLine) line.previousDoubly).index;
    }

    public static Clock create() {
        try {
            return new NanoClock();
        } catch (Throwable var1) {
            return new MillisClock();
        }
    }

    public static synchronized long now() {
        long var0 = System.currentTimeMillis();
        if (var0 < aLong1564) {
            aLong1565 += aLong1564 - var0;
        }

        aLong1564 = var0;
        return var0 + aLong1565;
    }

    public abstract int sleep(int time, int var2);

    public abstract void update();
}
