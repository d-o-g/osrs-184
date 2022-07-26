package jag.statics;

import jag.game.client;
import jag.opcode.OldConnectionTaskProcessor;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;

public class Statics35 {

    public static final char[] cp1252AsciiExtension = new char[]{'€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ'};
    public static byte[][][] aByteArrayArrayArray1615;

    public static double[] method1172(double var0, double var2, int var4) {
        int var5 = var4 * 2 + 1;
        double[] var6 = new double[var5];
        int var7 = -var4;

        for (int var8 = 0; var7 <= var4; ++var8) {
            var6[var8] = OldConnectionTaskProcessor.method700(var7, var0, var2);
            ++var7;
        }

        return var6;
    }

    public static void teleport(int var0, int var1, int var2, boolean var3) {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.TELEPORT, client.netWriter.encryptor);
        packet.buffer.writeByteS(var2);
        packet.buffer.ip4(var3 ? client.anInt1002 : 0);
        packet.buffer.p2(var1);
        packet.buffer.ip2a(var0);
        client.netWriter.writeLater(packet);
    }
}
