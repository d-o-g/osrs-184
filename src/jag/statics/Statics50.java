package jag.statics;

import jag.audi.PcmStream_Sub3;
import jag.audi.PcmStream_Sub4;
import jag.commons.Jagexception;
import jag.game.Server;
import jag.graphics.Font;
import jag.opcode.OutgoingPacket;
import jag.worldmap.WorldMapTileDecor;

import javax.imageio.ImageIO;

public class Statics50 {
    public static PcmStream_Sub3 aClass5_Sub6_Sub3_326;

    static {
        ImageIO.setUseCache(false);
    }

    public static Font method221(byte[] var0) {
        if (var0 == null) {
            return null;
        }
        Font var1 = new Font(var0, Statics41.anIntArray1821, PcmStream_Sub4.anIntArray1107, Statics41.anIntArray1820, Jagexception.anIntArray1878, OutgoingPacket.aByteArrayArray114);
        Statics41.anIntArray1821 = null;
        PcmStream_Sub4.anIntArray1107 = null;
        Statics41.anIntArray1820 = null;
        Jagexception.anIntArray1878 = null;
        Statics41.anIntArray1823 = null;
        OutgoingPacket.aByteArrayArray114 = null;
        return var1;
    }

    public static void method220(int var0, boolean var1, int var2, boolean var3) {
        if (Server.servers != null) {
            WorldMapTileDecor.method357(0, Server.servers.length - 1, var0, var1, var2, var3);
        }

    }
}
