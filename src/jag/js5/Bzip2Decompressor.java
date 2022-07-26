package jag.js5;

import jag.DirectByteBufferProvider;

public final class Bzip2Decompressor {

    public static final Bzip2Entry entry = new Bzip2Entry();

    public static int decompress(byte[] outputBuffer, int size, byte[] inputBuffer, int caret) {
        synchronized (entry) {
            entry.inputBuffer = inputBuffer;
            entry.caret = caret;
            entry.outputBuffer = outputBuffer;
            entry.nextOut = 0;
            entry.size = size;
            entry.bsLive = 0;
            entry.bsBuffer = 0;
            entry.nextUnusedBit = 0;
            entry.anInt1598 = 0;
            processEntry(entry);
            size -= entry.size;
            entry.inputBuffer = null;
            entry.outputBuffer = null;
            return size;
        }
    }

    public static void processEntry(Bzip2Entry entry) {
        int var19 = 0;
        int[] var20 = null;
        int[] var21 = null;
        int[] var22 = null;
        entry.blockSize = 1410065408;
        if (DirectByteBufferProvider.block == null) {
            DirectByteBufferProvider.block = new int[100000];
        }

        boolean var23 = true;

        while (true) {
            while (var23) {
                byte var24 = readUByte(entry);
                if (var24 == 23) {
                    return;
                }

                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readUByte(entry);
                var24 = readBit(entry);
                if (var24 != 0) {
                }

                entry.pointer = 0;
                var24 = readUByte(entry);
                entry.pointer = entry.pointer << 8 | var24 & 255;
                var24 = readUByte(entry);
                entry.pointer = entry.pointer << 8 | var24 & 255;
                var24 = readUByte(entry);
                entry.pointer = entry.pointer << 8 | var24 & 255;

                for (int var36 = 0; var36 < 16; ++var36) {
                    var24 = readBit(entry);
                    entry.used16[var36] = var24 == 1;
                }

                for (int var36 = 0; var36 < 256; ++var36) {
                    entry.used[var36] = false;
                }

                int var37;
                for (int var36 = 0; var36 < 16; ++var36) {
                    if (entry.used16[var36]) {
                        for (var37 = 0; var37 < 16; ++var37) {
                            var24 = readBit(entry);
                            if (var24 == 1) {
                                entry.used[var37 + var36 * 16] = true;
                            }
                        }
                    }
                }

                createMaps(entry);
                int var39 = entry.inUseCaret + 2;
                int var40 = readBits(3, entry);
                int var41 = readBits(15, entry);

                for (int var36 = 0; var36 < var41; ++var36) {
                    var37 = 0;

                    while (true) {
                        var24 = readBit(entry);
                        if (var24 == 0) {
                            entry.selector[var36] = (byte) var37;
                            break;
                        }

                        ++var37;
                    }
                }

                byte[] var25 = new byte[6];

                byte var26 = 0;
                while (var26 < var40) {
                    var25[var26] = var26++;
                }

                for (int var36 = 0; var36 < var41; ++var36) {
                    var26 = entry.selector[var36];

                    byte var27;
                    for (var27 = var25[var26]; var26 > 0; --var26) {
                        var25[var26] = var25[var26 - 1];
                    }

                    var25[0] = var27;
                    entry.aByteArray1600[var36] = var27;
                }

                for (int var38 = 0; var38 < var40; ++var38) {
                    int var50 = readBits(5, entry);

                    for (int var36 = 0; var36 < var39; ++var36) {
                        while (true) {
                            var24 = readBit(entry);
                            if (var24 == 0) {
                                entry.aByteArrayArray1609[var38][var36] = (byte) var50;
                                break;
                            }

                            var24 = readBit(entry);
                            if (var24 == 0) {
                                ++var50;
                            } else {
                                --var50;
                            }
                        }
                    }
                }

                for (int var38 = 0; var38 < var40; ++var38) {
                    byte var28 = 32;
                    byte var29 = 0;

                    for (int var36 = 0; var36 < var39; ++var36) {
                        if (entry.aByteArrayArray1609[var38][var36] > var29) {
                            var29 = entry.aByteArrayArray1609[var38][var36];
                        }

                        if (entry.aByteArrayArray1609[var38][var36] < var28) {
                            var28 = entry.aByteArrayArray1609[var38][var36];
                        }
                    }

                    createDecodeTables(entry.limit[var38], entry.base[var38], entry.perm[var38], entry.aByteArrayArray1609[var38], var28, var29, var39);
                    entry.minimumLengths[var38] = var28;
                }

                int var42 = entry.inUseCaret + 1;
                int var43 = -1;
                byte var44 = 0;

                for (int var36 = 0; var36 <= 255; ++var36) {
                    entry.unzftab[var36] = 0;
                }

                int var55 = 4095;

                int var30;
                int var56;
                for (var30 = 15; var30 >= 0; --var30) {
                    for (var56 = 15; var56 >= 0; --var56) {
                        entry.getAndMoveToFrontDecode[var55] = (byte) (var56 + var30 * 16);
                        --var55;
                    }

                    entry.anIntArray1597[var30] = var55 + 1;
                }

                int var47 = 0;
                byte var54;
                if (var44 == 0) {
                    ++var43;
                    var44 = 50;
                    var54 = entry.aByteArray1600[var43];
                    var19 = entry.minimumLengths[var54];
                    var20 = entry.limit[var54];
                    var22 = entry.perm[var54];
                    var21 = entry.base[var54];
                }

                int var45 = var44 - 1;
                int var51 = var19;

                int var52;
                byte var53;
                for (var52 = readBits(var19, entry); var52 > var20[var51]; var52 = var52 << 1 | var53) {
                    ++var51;
                    var53 = readBit(entry);
                }

                int var46 = var22[var52 - var21[var51]];

                while (true) {
                    int[] var10000;
                    while (var46 != var42) {
                        if (var46 != 0 && var46 != 1) {
                            int var31 = var46 - 1;
                            int var32;
                            if (var31 < 16) {
                                var32 = entry.anIntArray1597[0];

                                for (var24 = entry.getAndMoveToFrontDecode[var32 + var31]; var31 > 3; var31 -= 4) {
                                    int var33 = var32 + var31;
                                    entry.getAndMoveToFrontDecode[var33] = entry.getAndMoveToFrontDecode[var33 - 1];
                                    entry.getAndMoveToFrontDecode[var33 - 1] = entry.getAndMoveToFrontDecode[var33 - 2];
                                    entry.getAndMoveToFrontDecode[var33 - 2] = entry.getAndMoveToFrontDecode[var33 - 3];
                                    entry.getAndMoveToFrontDecode[var33 - 3] = entry.getAndMoveToFrontDecode[var33 - 4];
                                }

                                while (var31 > 0) {
                                    entry.getAndMoveToFrontDecode[var32 + var31] = entry.getAndMoveToFrontDecode[var32 + var31 - 1];
                                    --var31;
                                }

                                entry.getAndMoveToFrontDecode[var32] = var24;
                            } else {
                                int var34 = var31 / 16;
                                int var35 = var31 % 16;
                                var32 = entry.anIntArray1597[var34] + var35;

                                for (var24 = entry.getAndMoveToFrontDecode[var32]; var32 > entry.anIntArray1597[var34]; --var32) {
                                    entry.getAndMoveToFrontDecode[var32] = entry.getAndMoveToFrontDecode[var32 - 1];
                                }

                                for (; var34 > 0; --var34) {
                                    entry.getAndMoveToFrontDecode[entry.anIntArray1597[var34]] = entry.getAndMoveToFrontDecode[entry.anIntArray1597[var34 - 1] + 16 - 1];
                                }

                                entry.getAndMoveToFrontDecode[entry.anIntArray1597[0]] = var24;
                                if (entry.anIntArray1597[0] == 0) {
                                    var55 = 4095;

                                    for (var30 = 15; var30 >= 0; --var30) {
                                        for (var56 = 15; var56 >= 0; --var56) {
                                            entry.getAndMoveToFrontDecode[var55] = entry.getAndMoveToFrontDecode[entry.anIntArray1597[var30] + var56];
                                            --var55;
                                        }

                                        entry.anIntArray1597[var30] = var55 + 1;
                                    }
                                }
                            }

                            DirectByteBufferProvider.block[var47] = entry.unseq[var24 & 255] & 255;
                            ++var47;
                            if (var45 == 0) {
                                ++var43;
                                var45 = 50;
                                var54 = entry.aByteArray1600[var43];
                                var19 = entry.minimumLengths[var54];
                                var20 = entry.limit[var54];
                                var22 = entry.perm[var54];
                                var21 = entry.base[var54];
                            }

                            --var45;
                            var51 = var19;

                            for (var52 = readBits(var19, entry); var52 > var20[var51]; var52 = var52 << 1 | var53) {
                                ++var51;
                                var53 = readBit(entry);
                            }

                            var46 = var22[var52 - var21[var51]];
                        } else {
                            int var48 = -1;
                            int var49 = 1;

                            do {
                                if (var46 == 0) {
                                    var48 += var49;
                                } else if (var46 == 1) {
                                    var48 += var49 * 2;
                                }

                                var49 *= 2;
                                if (var45 == 0) {
                                    ++var43;
                                    var45 = 50;
                                    var54 = entry.aByteArray1600[var43];
                                    var19 = entry.minimumLengths[var54];
                                    var20 = entry.limit[var54];
                                    var22 = entry.perm[var54];
                                    var21 = entry.base[var54];
                                }

                                --var45;
                                var51 = var19;

                                for (var52 = readBits(var19, entry); var52 > var20[var51]; var52 = var52 << 1 | var53) {
                                    ++var51;
                                    var53 = readBit(entry);
                                }

                                var46 = var22[var52 - var21[var51]];
                            } while (var46 == 0 || var46 == 1);

                            ++var48;
                            var24 = entry.unseq[entry.getAndMoveToFrontDecode[entry.anIntArray1597[0]] & 255];
                            var10000 = entry.unzftab;

                            for (var10000[var24 & 255] += var48; var48 > 0; --var48) {
                                DirectByteBufferProvider.block[var47] = var24 & 255;
                                ++var47;
                            }
                        }
                    }

                    entry.anInt1585 = 0;
                    entry.aByte1578 = 0;
                    entry.cftab[0] = 0;

                    for (int var36 = 1; var36 <= 256; ++var36) {
                        entry.cftab[var36] = entry.unzftab[var36 - 1];
                    }

                    for (int var36 = 1; var36 <= 256; ++var36) {
                        var10000 = entry.cftab;
                        var10000[var36] += entry.cftab[var36 - 1];
                    }

                    for (int var36 = 0; var36 < var47; ++var36) {
                        var24 = (byte) (DirectByteBufferProvider.block[var36] & 255);
                        var10000 = DirectByteBufferProvider.block;
                        int var10001 = entry.cftab[var24 & 255];
                        var10000[var10001] |= var36 << 8;
                    }

                    entry.anInt1588 = DirectByteBufferProvider.block[entry.pointer] >> 8;
                    entry.anInt1590 = 0;
                    entry.anInt1588 = DirectByteBufferProvider.block[entry.anInt1588];
                    entry.anInt1604 = (byte) (entry.anInt1588 & 255);
                    entry.anInt1588 >>= 8;
                    ++entry.anInt1590;
                    entry.anInt1594 = var47;
                    method1180(entry);
                    if (entry.anInt1594 + 1 == entry.anInt1590 && entry.anInt1585 == 0) {
                        var23 = true;
                        break;
                    }

                    var23 = false;
                    break;
                }
            }

            return;
        }
    }

    static byte readUByte(Bzip2Entry var0) {
        return (byte) readBits(8, var0);
    }

    static int readBits(int var0, Bzip2Entry var1) {
        while (var1.bsLive < var0) {
            var1.bsBuffer = var1.bsBuffer << 8 | var1.inputBuffer[var1.caret] & 255;
            var1.bsLive += 8;
            ++var1.caret;
            ++var1.nextUnusedBit;
        }

        int var2 = var1.bsBuffer >> var1.bsLive - var0 & (1 << var0) - 1;
        var1.bsLive -= var0;
        return var2;
    }

    static byte readBit(Bzip2Entry entry) {
        return (byte) readBits(1, entry);
    }

    static void createMaps(Bzip2Entry entry) {
        entry.inUseCaret = 0;
        for (int i = 0; i < 256; ++i) {
            if (entry.used[i]) {
                entry.unseq[entry.inUseCaret] = (byte) i;
                ++entry.inUseCaret;
            }
        }
    }

    static void createDecodeTables(int[] limit, int[] base, int[] perm,
            byte[] len, int min, int max, int alpha) {

        int var7 = 0;
        for (int i = min; i <= max; ++i) {
            for (int j = 0; j < alpha; ++j) {
                if (i == len[j]) {
                    perm[var7] = j;
                    ++var7;
                }
            }
        }

        for (int i = 0; i < 23; ++i) {
            base[i] = 0;
        }

        for (int i = 0; i < alpha; ++i) {
            ++base[len[i] + 1];
        }

        for (int i = 1; i < 23; ++i) {
            base[i] += base[i - 1];
        }

        for (int i = 0; i < 23; ++i) {
            limit[i] = 0;
        }

        int vec = 0;
        for (int i = min; i <= max; ++i) {
            vec += base[i + 1] - base[i];
            limit[i] = vec - 1;
            vec <<= 1;
        }

        for (int i = min + 1; i <= max; ++i) {
            base[i] = (limit[i - 1] + 1 << 1) - base[i];
        }
    }

    static void method1180(Bzip2Entry var0) {
        byte var1 = var0.aByte1578;
        int var2 = var0.anInt1585;
        int var3 = var0.anInt1590;
        int var4 = var0.anInt1604;
        int[] var5 = DirectByteBufferProvider.block;
        int var6 = var0.anInt1588;
        byte[] var7 = var0.outputBuffer;
        int var8 = var0.nextOut;
        int var9 = var0.size;
        int var11 = var0.anInt1594 + 1;

        label61:
        while (true) {
            if (var2 > 0) {
                while (true) {
                    if (var9 == 0) {
                        break label61;
                    }

                    if (var2 == 1) {
                        if (var9 == 0) {
                            var2 = 1;
                            break label61;
                        }

                        var7[var8] = var1;
                        ++var8;
                        --var9;
                        break;
                    }

                    var7[var8] = var1;
                    --var2;
                    ++var8;
                    --var9;
                }
            }

            while (var3 != var11) {
                var1 = (byte) var4;
                var6 = var5[var6];
                byte var13 = (byte) var6;
                var6 >>= 8;
                ++var3;
                if (var13 != var4) {
                    var4 = var13;

                } else {
                    if (var3 != var11) {
                        var2 = 2;
                        var6 = var5[var6];
                        var13 = (byte) var6;
                        var6 >>= 8;
                        ++var3;
                        if (var3 != var11) {
                            if (var13 != var4) {
                                var4 = var13;
                            } else {
                                var2 = 3;
                                var6 = var5[var6];
                                var13 = (byte) var6;
                                var6 >>= 8;
                                ++var3;
                                if (var3 != var11) {
                                    if (var13 != var4) {
                                        var4 = var13;
                                    } else {
                                        var6 = var5[var6];
                                        var13 = (byte) var6;
                                        var6 >>= 8;
                                        ++var3;
                                        var2 = (var13 & 255) + 4;
                                        var6 = var5[var6];
                                        var4 = (byte) var6;
                                        var6 >>= 8;
                                        ++var3;
                                    }
                                }
                            }
                        }
                        continue label61;
                    }

                }
                if (var9 == 0) {
                    var2 = 1;
                    break label61;
                }
                var7[var8] = var1;
                ++var8;
                --var9;
            }

            var2 = 0;
            break;
        }

        int var12 = var0.anInt1598;
        var0.anInt1598 += var9 - var9;
        if (var0.anInt1598 < var12) {
        }

        var0.aByte1578 = var1;
        var0.anInt1585 = var2;
        var0.anInt1590 = var3;
        var0.anInt1604 = var4;
        DirectByteBufferProvider.block = var5;
        var0.anInt1588 = var6;
        var0.outputBuffer = var7;
        var0.nextOut = var8;
        var0.size = var9;
    }
}
