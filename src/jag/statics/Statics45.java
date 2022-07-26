package jag.statics;

import jag.audi.PcmStream_Sub4;
import jag.commons.Jagexception;
import jag.game.type.AnimationFrameGroup;
import jag.opcode.Buffer;
import jag.opcode.OutgoingPacket;

public final class Statics45 {

    public static final int[] anIntArray406 = new int[]{1, 2, 4, 8};
    public static final int[] anIntArray395 = new int[]{16, 32, 64, 128};
    public static final int[] anIntArray402 = new int[]{1, 0, -1, 0};
    public static final int[] anIntArray394 = new int[]{0, -1, 0, 1};
    public static final int[] anIntArray397 = new int[]{1, -1, -1, 1};
    public static final int[] anIntArray392 = new int[]{-1, -1, 1, 1};
    public static final int[][][] tileHeights = new int[4][105][105];
    public static final byte[][][] sceneRenderRules = new byte[4][104][104];
    public static int anInt405 = 99;
    public static int tileHueOffset = (int) (Math.random() * 17.0D) - 8;
    public static int tileLightnessOffset = (int) (Math.random() * 33.0D) - 16;
    public static long aLong403;
    public static byte[][][] aByteArrayArrayArray404;
    public static byte[][][] aByteArrayArrayArray401;
    public static int[][][] anIntArrayArrayArray393;
    public static byte[][][] aByteArrayArrayArray400;
    public static int[] anIntArray396;
    public static int[] anIntArray390;
    public static int[] anIntArray389;

    public static void method267(byte[] var0) {
        Buffer var1 = new Buffer(var0);
        var1.pos = var0.length - 2;
        Statics41.anInt1822 = var1.g2();
        Statics41.anIntArray1821 = new int[Statics41.anInt1822];
        PcmStream_Sub4.anIntArray1107 = new int[Statics41.anInt1822];
        Statics41.anIntArray1820 = new int[Statics41.anInt1822];
        Jagexception.anIntArray1878 = new int[Statics41.anInt1822];
        OutgoingPacket.aByteArrayArray114 = new byte[Statics41.anInt1822][];
        var1.pos = var0.length - 7 - Statics41.anInt1822 * 8;
        Statics41.anInt1824 = var1.g2();
        AnimationFrameGroup.anInt378 = var1.g2();
        int var2 = (var1.g1() & 255) + 1;

        int var3;
        for (var3 = 0; var3 < Statics41.anInt1822; ++var3) {
            Statics41.anIntArray1821[var3] = var1.g2();
        }

        for (var3 = 0; var3 < Statics41.anInt1822; ++var3) {
            PcmStream_Sub4.anIntArray1107[var3] = var1.g2();
        }

        for (var3 = 0; var3 < Statics41.anInt1822; ++var3) {
            Statics41.anIntArray1820[var3] = var1.g2();
        }

        for (var3 = 0; var3 < Statics41.anInt1822; ++var3) {
            Jagexception.anIntArray1878[var3] = var1.g2();
        }

        var1.pos = var0.length - 7 - Statics41.anInt1822 * 8 - (var2 - 1) * 3;
        Statics41.anIntArray1823 = new int[var2];

        for (var3 = 1; var3 < var2; ++var3) {
            Statics41.anIntArray1823[var3] = var1.g3();
            if (Statics41.anIntArray1823[var3] == 0) {
                Statics41.anIntArray1823[var3] = 1;
            }
        }

        var1.pos = 0;

        for (var3 = 0; var3 < Statics41.anInt1822; ++var3) {
            int var4 = Statics41.anIntArray1820[var3];
            int var5 = Jagexception.anIntArray1878[var3];
            int var6 = var4 * var5;
            byte[] var7 = new byte[var6];
            OutgoingPacket.aByteArrayArray114[var3] = var7;
            int var8 = var1.g1();
            int var9;
            if (var8 == 0) {
                for (var9 = 0; var9 < var6; ++var9) {
                    var7[var9] = var1.g1b();
                }
            } else if (var8 == 1) {
                for (var9 = 0; var9 < var4; ++var9) {
                    for (int var10 = 0; var10 < var5; ++var10) {
                        var7[var9 + var4 * var10] = var1.g1b();
                    }
                }
            }
        }

    }

    public static void method180(int var0, int var1, int var2, int var3) {
        for (int var4 = var1; var4 <= var3 + var1; ++var4) {
            for (int var5 = var0; var5 <= var0 + var2; ++var5) {
                if (var5 >= 0 && var5 < 104 && var4 >= 0 && var4 < 104) {
                    aByteArrayArrayArray400[0][var5][var4] = 127;
                    if (var0 == var5 && var5 > 0) {
                        tileHeights[0][var5][var4] = tileHeights[0][var5 - 1][var4];
                    }

                    if (var0 + var2 == var5 && var5 < 103) {
                        tileHeights[0][var5][var4] = tileHeights[0][var5 + 1][var4];
                    }

                    if (var4 == var1 && var4 > 0) {
                        tileHeights[0][var5][var4] = tileHeights[0][var5][var4 - 1];
                    }

                    if (var4 == var3 + var1 && var4 < 103) {
                        tileHeights[0][var5][var4] = tileHeights[0][var5][var4 + 1];
                    }
                }
            }
        }

    }

    public static void method189(int var0, int var1, int var2) {
        int var3;
        for (var3 = 0; var3 < 8; ++var3) {
            for (int var4 = 0; var4 < 8; ++var4) {
                tileHeights[var0][var3 + var1][var4 + var2] = 0;
            }
        }

        if (var1 > 0) {
            for (var3 = 1; var3 < 8; ++var3) {
                tileHeights[var0][var1][var3 + var2] = tileHeights[var0][var1 - 1][var3 + var2];
            }
        }

        if (var2 > 0) {
            for (var3 = 1; var3 < 8; ++var3) {
                tileHeights[var0][var3 + var1][var2] = tileHeights[var0][var3 + var1][var2 - 1];
            }
        }

        if (var1 > 0 && tileHeights[var0][var1 - 1][var2] != 0) {
            tileHeights[var0][var1][var2] = tileHeights[var0][var1 - 1][var2];
        } else if (var2 > 0 && tileHeights[var0][var1][var2 - 1] != 0) {
            tileHeights[var0][var1][var2] = tileHeights[var0][var1][var2 - 1];
        } else if (var1 > 0 && var2 > 0 && tileHeights[var0][var1 - 1][var2 - 1] != 0) {
            tileHeights[var0][var1][var2] = tileHeights[var0][var1 - 1][var2 - 1];
        }

    }
}
