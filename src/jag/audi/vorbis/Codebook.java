package jag.audi.vorbis;

import jag.worldmap.WorldMapGroundDecorType2;

public class Codebook {
    final int entries;
    final int[] entryLengths;
    final int dims;
    float[][] aFloatArrayArray640;
    int[] keys;
    int[] anIntArray641;

    Codebook() {
        VorbisSample.read(24);
        dims = VorbisSample.read(16);
        entries = VorbisSample.read(24);
        entryLengths = new int[entries];
        boolean var1 = VorbisSample.read() != 0;

        if (var1) {
            int var2 = 0;
            for (int var3 = VorbisSample.read(5) + 1; var2 < entries; ++var3) {
                int var4 = VorbisSample.read(log(entries - var2));
                for (int var5 = 0; var5 < var4; ++var5) {
                    entryLengths[var2++] = var3;
                }
            }

        } else {
            boolean var14 = VorbisSample.read() != 0;

            for (int var3 = 0; var3 < entries; ++var3) {
                if (var14 && VorbisSample.read() == 0) {
                    entryLengths[var3] = 0;
                } else {
                    entryLengths[var3] = VorbisSample.read(5) + 1;
                }
            }
        }

        method463();

        int var2 = VorbisSample.read(4);
        if (var2 > 0) {
            float var15 = VorbisSample.unpack(VorbisSample.read(32));
            float var16 = VorbisSample.unpack(VorbisSample.read(32));
            int var5 = VorbisSample.read(4) + 1;
            boolean var6 = VorbisSample.read() != 0;
            int var7;
            if (var2 == 1) {
                var7 = map(entries, dims);
            } else {
                var7 = entries * dims;
            }

            anIntArray641 = new int[var7];

            int var8;
            for (var8 = 0; var8 < var7; ++var8) {
                anIntArray641[var8] = VorbisSample.read(var5);
            }

            aFloatArrayArray640 = new float[entries][dims];
            float var9;
            int var10;
            int var11;
            if (var2 == 1) {
                for (var8 = 0; var8 < entries; ++var8) {
                    var9 = 0.0F;
                    var10 = 1;

                    for (var11 = 0; var11 < dims; ++var11) {
                        int var12 = var8 / var10 % var7;
                        float var13 = (float) anIntArray641[var12] * var16 + var15 + var9;
                        aFloatArrayArray640[var8][var11] = var13;
                        if (var6) {
                            var9 = var13;
                        }

                        var10 *= var7;
                    }
                }
            } else {
                for (var8 = 0; var8 < entries; ++var8) {
                    var9 = 0.0F;
                    var10 = var8 * dims;

                    for (var11 = 0; var11 < dims; ++var11) {
                        float var17 = (float) anIntArray641[var10] * var16 + var15 + var9;
                        aFloatArrayArray640[var8][var11] = var17;
                        if (var6) {
                            var9 = var17;
                        }

                        ++var10;
                    }
                }
            }
        }

    }

    static int map(int var0, int var1) {
        int var2 = (int) Math.pow(var0, 1.0D / (double) var1) + 1;
        while (WorldMapGroundDecorType2.method183(var2, var1) > var0) {
            --var2;
        }
        return var2;
    }

    public static int log(int var0) {
        int var1 = 0;
        if (var0 < 0 || var0 >= 65536) {
            var0 >>>= 16;
            var1 += 16;
        }

        if (var0 >= 256) {
            var0 >>>= 8;
            var1 += 8;
        }

        if (var0 >= 16) {
            var0 >>>= 4;
            var1 += 4;
        }

        if (var0 >= 4) {
            var0 >>>= 2;
            var1 += 2;
        }

        if (var0 >= 1) {
            var0 >>>= 1;
            ++var1;
        }

        return var0 + var1;
    }

    int method461() {
        int var1 = 0;
        while (keys[var1] >= 0) {
            var1 = VorbisSample.read() != 0 ? keys[var1] : var1 + 1;
        }

        return ~keys[var1];
    }

    float[] method460() {
        return aFloatArrayArray640[method461()];
    }

    void method463() {
        int[] var1 = new int[entries];
        int[] var2 = new int[33];

        int var3;
        int var4;
        int var6;
        int var7;
        int var8;
        int var9;
        int var11;
        for (var3 = 0; var3 < entries; ++var3) {
            var4 = entryLengths[var3];
            if (var4 != 0) {
                var6 = 1 << 32 - var4;
                var7 = var2[var4];
                var1[var3] = var7;
                int var12;
                if ((var7 & var6) != 0) {
                    var8 = var2[var4 - 1];
                } else {
                    var8 = var7 | var6;

                    for (var9 = var4 - 1; var9 >= 1; --var9) {
                        var12 = var2[var9];
                        if (var12 != var7) {
                            break;
                        }

                        var11 = 1 << 32 - var9;
                        if ((var12 & var11) != 0) {
                            var2[var9] = var2[var9 - 1];
                            break;
                        }

                        var2[var9] = var12 | var11;
                    }
                }

                var2[var4] = var8;

                for (var9 = var4 + 1; var9 <= 32; ++var9) {
                    var12 = var2[var9];
                    if (var12 == var7) {
                        var2[var9] = var8;
                    }
                }
            }
        }

        keys = new int[8];
        int var5 = 0;

        for (var3 = 0; var3 < entries; ++var3) {
            var4 = entryLengths[var3];
            if (var4 != 0) {
                var6 = var1[var3];
                var7 = 0;

                for (var8 = 0; var8 < var4; ++var8) {
                    var9 = Integer.MIN_VALUE >>> var8;
                    if ((var6 & var9) != 0) {
                        if (keys[var7] == 0) {
                            keys[var7] = var5;
                        }

                        var7 = keys[var7];
                    } else {
                        ++var7;
                    }

                    if (var7 >= keys.length) {
                        int[] var10 = new int[keys.length * 2];

                        for (var11 = 0; var11 < keys.length; ++var11) {
                            var10[var11] = keys[var11];
                        }

                        keys = var10;
                    }

                }

                keys[var7] = ~var3;
                if (var7 >= var5) {
                    var5 = var7 + 1;
                }
            }
        }

    }
}
