package jag.js5;

import jag.Huffman;
import jag.audi.AudioRunnable;
import jag.game.relationship.AssociateComparatorByWorld;
import jag.opcode.Buffer;

import java.io.EOFException;
import java.io.IOException;

public final class ResourceCache {

    static final byte[] buffer = new byte[520];

    BufferedFile data;
    BufferedFile index;
    int caret;
    int id;

    public ResourceCache(int id, BufferedFile data, BufferedFile index, int caret) {
        this.id = id;
        this.data = data;
        this.index = index;
        this.caret = caret;
    }

    public static void method1489(Buffer buffer, int var1) {
        BufferedFile.createRandom(buffer.payload, var1);
        AudioRunnable.method986(buffer, var1);
    }

    public static int method1491(Buffer var0, String var1) {
        int var2 = var0.pos;
        byte[] var3 = AssociateComparatorByWorld.method680(var1);
        var0.pSmart2or3(var3.length);
        var0.pos += Huffman.instance.compress(var3, 0, var3.length, var0.payload, var0.pos);
        return var0.pos - var2;
    }

    boolean write(int var1, byte[] var2, int var3, boolean var4) {
        synchronized (data) {
            try {
                int var6;
                boolean var10000;
                if (var4) {
                    if (index.method1409() < (long) (var1 * 6 + 6)) {
                        var10000 = false;
                        return var10000;
                    }

                    index.seek(var1 * 6);
                    index.read(buffer, 0, 6);
                    var6 = (buffer[5] & 255) + ((buffer[3] & 255) << 16) + ((buffer[4] & 255) << 8);
                    if (var6 <= 0 || (long) var6 > data.method1409() / 520L) {
                        var10000 = false;
                        return var10000;
                    }
                } else {
                    var6 = (int) ((data.method1409() + 519L) / 520L);
                    if (var6 == 0) {
                        var6 = 1;
                    }
                }

                buffer[0] = (byte) (var3 >> 16);
                buffer[1] = (byte) (var3 >> 8);
                buffer[2] = (byte) var3;
                buffer[3] = (byte) (var6 >> 16);
                buffer[4] = (byte) (var6 >> 8);
                buffer[5] = (byte) var6;
                index.seek(var1 * 6);
                index.write(buffer, 0, 6);
                int var7 = 0;
                int var8 = 0;

                while (true) {
                    if (var7 < var3) {
                        label171:
                        {
                            int var9 = 0;
                            int var11;
                            if (var4) {
                                data.seek(520L * (long) var6);
                                int var12;
                                int var13;
                                if (var1 > 65535) {
                                    try {
                                        data.read(buffer, 0, 10);
                                    } catch (EOFException var17) {
                                        break label171;
                                    }

                                    var11 = ((buffer[1] & 255) << 16) + ((buffer[0] & 255) << 24) + (buffer[3] & 255) + ((buffer[2] & 255) << 8);
                                    var12 = (buffer[5] & 255) + ((buffer[4] & 255) << 8);
                                    var9 = (buffer[8] & 255) + ((buffer[7] & 255) << 8) + ((buffer[6] & 255) << 16);
                                    var13 = buffer[9] & 255;
                                } else {
                                    try {
                                        data.read(buffer, 0, 8);
                                    } catch (EOFException var16) {
                                        break label171;
                                    }

                                    var11 = (buffer[1] & 255) + ((buffer[0] & 255) << 8);
                                    var12 = (buffer[3] & 255) + ((buffer[2] & 255) << 8);
                                    var9 = ((buffer[5] & 255) << 8) + ((buffer[4] & 255) << 16) + (buffer[6] & 255);
                                    var13 = buffer[7] & 255;
                                }

                                if (var11 != var1 || var8 != var12 || var13 != id) {
                                    var10000 = false;
                                    return var10000;
                                }

                                if (var9 < 0 || (long) var9 > data.method1409() / 520L) {
                                    var10000 = false;
                                    return var10000;
                                }
                            }

                            if (var9 == 0) {
                                var4 = false;
                                var9 = (int) ((data.method1409() + 519L) / 520L);
                                if (var9 == 0) {
                                    ++var9;
                                }

                                if (var9 == var6) {
                                    ++var9;
                                }
                            }

                            if (var1 > 65535) {
                                if (var3 - var7 <= 510) {
                                    var9 = 0;
                                }

                                buffer[0] = (byte) (var1 >> 24);
                                buffer[1] = (byte) (var1 >> 16);
                                buffer[2] = (byte) (var1 >> 8);
                                buffer[3] = (byte) var1;
                                buffer[4] = (byte) (var8 >> 8);
                                buffer[5] = (byte) var8;
                                buffer[6] = (byte) (var9 >> 16);
                                buffer[7] = (byte) (var9 >> 8);
                                buffer[8] = (byte) var9;
                                buffer[9] = (byte) id;
                                data.seek(520L * (long) var6);
                                data.write(buffer, 0, 10);
                                var11 = var3 - var7;
                                if (var11 > 510) {
                                    var11 = 510;
                                }

                            } else {
                                if (var3 - var7 <= 512) {
                                    var9 = 0;
                                }

                                buffer[0] = (byte) (var1 >> 8);
                                buffer[1] = (byte) var1;
                                buffer[2] = (byte) (var8 >> 8);
                                buffer[3] = (byte) var8;
                                buffer[4] = (byte) (var9 >> 16);
                                buffer[5] = (byte) (var9 >> 8);
                                buffer[6] = (byte) var9;
                                buffer[7] = (byte) id;
                                data.seek((long) var6 * 520L);
                                data.write(buffer, 0, 8);
                                var11 = var3 - var7;
                                if (var11 > 512) {
                                    var11 = 512;
                                }

                            }
                            data.write(var2, var7, var11);
                            var7 += var11;

                            var6 = var9;
                            ++var8;
                            continue;
                        }
                    }

                    var10000 = true;
                    return var10000;
                }
            } catch (IOException var18) {
                return false;
            }
        }
    }

    public boolean write(int var1, byte[] var2, int var3) {
        synchronized (data) {
            if (var3 >= 0 && var3 <= caret) {
                boolean var5 = write(var1, var2, var3, true);
                if (!var5) {
                    var5 = write(var1, var2, var3, false);
                }

                return var5;
            }
            throw new IllegalArgumentException("" + id + ',' + var1 + ',' + var3);
        }
    }

    public byte[] read(int var1) {
        synchronized (data) {
            try {
                Object var10000;
                if (index.method1409() < (long) (var1 * 6 + 6)) {
                    var10000 = null;
                    return (byte[]) var10000;
                }
                index.seek(var1 * 6);
                index.read(buffer, 0, 6);
                int var3 = ((buffer[0] & 255) << 16) + (buffer[2] & 255) + ((buffer[1] & 255) << 8);
                int var4 = (buffer[5] & 255) + ((buffer[3] & 255) << 16) + ((buffer[4] & 255) << 8);
                if (var3 < 0 || var3 > caret) {
                    var10000 = null;
                    return (byte[]) var10000;
                }
                if (var4 <= 0 || (long) var4 > data.method1409() / 520L) {
                    var10000 = null;
                    return (byte[]) var10000;
                }
                byte[] var5 = new byte[var3];
                int var6 = 0;
                int var7 = 0;

                while (var6 < var3) {
                    if (var4 == 0) {
                        var10000 = null;
                        return (byte[]) var10000;
                    }

                    data.seek((long) var4 * 520L);
                    int var8 = var3 - var6;
                    byte var9;
                    int var10;
                    int var11;
                    int var12;
                    int var13;
                    if (var1 > 65535) {
                        if (var8 > 510) {
                            var8 = 510;
                        }

                        var9 = 10;
                        data.read(buffer, 0, var8 + var9);
                        var10 = ((buffer[1] & 255) << 16) + ((buffer[0] & 255) << 24) + (buffer[3] & 255) + ((buffer[2] & 255) << 8);
                        var11 = (buffer[5] & 255) + ((buffer[4] & 255) << 8);
                        var12 = (buffer[8] & 255) + ((buffer[7] & 255) << 8) + ((buffer[6] & 255) << 16);
                        var13 = buffer[9] & 255;
                    } else {
                        if (var8 > 512) {
                            var8 = 512;
                        }

                        var9 = 8;
                        data.read(buffer, 0, var8 + var9);
                        var10 = (buffer[1] & 255) + ((buffer[0] & 255) << 8);
                        var11 = (buffer[3] & 255) + ((buffer[2] & 255) << 8);
                        var12 = ((buffer[5] & 255) << 8) + ((buffer[4] & 255) << 16) + (buffer[6] & 255);
                        var13 = buffer[7] & 255;
                    }

                    if (var10 == var1 && var11 == var7 && var13 == id) {
                        if (var12 >= 0 && (long) var12 <= data.method1409() / 520L) {
                            int var14 = var9 + var8;

                            for (int var15 = var9; var15 < var14; ++var15) {
                                var5[var6++] = buffer[var15];
                            }

                            var4 = var12;
                            ++var7;
                            continue;
                        }

                        var10000 = null;
                        return (byte[]) var10000;
                    }

                    var10000 = null;
                    return (byte[]) var10000;
                }

                return var5;
            } catch (IOException var18) {
                return null;
            }
        }
    }

    public String toString() {
        return "" + id;
    }
}
