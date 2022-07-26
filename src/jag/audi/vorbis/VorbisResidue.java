package jag.audi.vorbis;

public class VorbisResidue {
    final int classbook;
    final int type;
    final int end;
    final int start;
    final int partitionSize;
    final int[] anIntArray681;
    final int classifications;

    VorbisResidue() {
        type = VorbisSample.read(16);
        start = VorbisSample.read(24);
        end = VorbisSample.read(24);
        partitionSize = VorbisSample.read(24) + 1;
        classifications = VorbisSample.read(6) + 1;
        classbook = VorbisSample.read(8);
        int[] var1 = new int[classifications];

        int var2 = 0;
        while (var2 < classifications) {
            int var3 = 0;
            int var4 = VorbisSample.read(3);
            boolean var5 = VorbisSample.read() != 0;
            if (var5) {
                var3 = VorbisSample.read(5);
            }

            var1[var2] = var3 << 3 | var4;
            ++var2;
        }

        anIntArray681 = new int[classifications * 8];

        for (var2 = 0; var2 < classifications * 8; ++var2) {
            anIntArray681[var2] = (var1[var2 >> 3] & 1 << (var2 & 7)) != 0 ? VorbisSample.read(8) : -1;
        }

    }

    void method501(float[] var1, int var2, boolean var3) {
        int var4;
        for (var4 = 0; var4 < var2; ++var4) {
            var1[var4] = 0.0F;
        }

        if (!var3) {
            var4 = VorbisSample.aClass86Array557[classbook].dims;
            int var5 = end - start;
            int var6 = var5 / partitionSize;
            int[] var7 = new int[var6];

            for (int var8 = 0; var8 < 8; ++var8) {
                int var9 = 0;

                while (var9 < var6) {
                    int var10;
                    int var11;
                    if (var8 == 0) {
                        var10 = VorbisSample.aClass86Array557[classbook].method461();

                        for (var11 = var4 - 1; var11 >= 0; --var11) {
                            if (var9 + var11 < var6) {
                                var7[var9 + var11] = var10 % classifications;
                            }

                            var10 /= classifications;
                        }
                    }

                    for (var10 = 0; var10 < var4; ++var10) {
                        var11 = var7[var9];
                        int var12 = anIntArray681[var8 + var11 * 8];
                        if (var12 >= 0) {
                            int var13 = var9 * partitionSize + start;
                            Codebook var14 = VorbisSample.aClass86Array557[var12];
                            int var15;
                            if (type == 0) {
                                var15 = partitionSize / var14.dims;

                                for (int var16 = 0; var16 < var15; ++var16) {
                                    float[] var17 = var14.method460();

                                    for (int var18 = 0; var18 < var14.dims; ++var18) {
                                        var1[var13 + var16 + var18 * var15] += var17[var18];
                                    }
                                }
                            } else {
                                var15 = 0;

                                while (var15 < partitionSize) {
                                    float[] var19 = var14.method460();

                                    for (int var20 = 0; var20 < var14.dims; ++var20) {
                                        var1[var13 + var15] += var19[var20];
                                        ++var15;
                                    }
                                }
                            }
                        }

                        ++var9;
                        if (var9 >= var6) {
                            break;
                        }
                    }
                }
            }

        }
    }
}
