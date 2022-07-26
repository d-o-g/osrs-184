package jag.statics;

import jag.audi.PcmStream_Sub4;
import jag.commons.Jagexception;
import jag.game.type.AnimationFrameGroup;
import jag.graphics.IndexedSprite;
import jag.js5.ReferenceTable;
import jag.opcode.OutgoingPacket;

public class Statics38 {
    public static IndexedSprite[] method1194(ReferenceTable var0, int var1, int var2) {
        if (!ReferenceTable.method534(var0, var1, var2)) {
            return null;
        }
        IndexedSprite[] var3 = new IndexedSprite[Statics41.anInt1822];

        for (int var4 = 0; var4 < Statics41.anInt1822; ++var4) {
            IndexedSprite var5 = var3[var4] = new IndexedSprite();
            var5.anInt375 = Statics41.anInt1824;
            var5.anInt372 = AnimationFrameGroup.anInt378;
            var5.insetX = Statics41.anIntArray1821[var4];
            var5.insetY = PcmStream_Sub4.anIntArray1107[var4];
            var5.anInt378 = Statics41.anIntArray1820[var4];
            var5.anInt377 = Jagexception.anIntArray1878[var4];
            var5.palette = Statics41.anIntArray1823;
            var5.indices = OutgoingPacket.aByteArrayArray114[var4];
        }

        Statics41.anIntArray1821 = null;
        PcmStream_Sub4.anIntArray1107 = null;
        Statics41.anIntArray1820 = null;
        Jagexception.anIntArray1878 = null;
        Statics41.anIntArray1823 = null;
        OutgoingPacket.aByteArrayArray114 = null;
        return var3;
    }
}
