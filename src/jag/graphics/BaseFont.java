package jag.graphics;

import jag.SerializableLong;
import jag.audi.Class97;

import java.util.Random;

public abstract class BaseFont extends JagGraphics {
    static final String[] aStringArray1572 = new String[100];
    static final Random aRandom1575 = new Random();
    public static IndexedSprite[] aDoublyNode_Sub24_Sub4Array1573;
    static int anInt367 = -1;
    static int anInt386 = -1;
    static int anInt666 = 256;
    static int anInt366 = -1;
    static int anInt702 = 0;
    static int anInt788 = 0;
    static int anInt696 = -1;
    static int anInt791 = 0;
    static int anInt563 = 0;

    public int anInt375;
    public int anInt372;
    public int anInt379;
    int[] anIntArray1574;
    byte[][] aByteArrayArray582;
    int[] anIntArray1107;
    int[] anIntArray747;
    byte[] aByteArray1425;
    int[] anIntArray749;
    int[] anIntArray1108;

    BaseFont(byte[] var1, int[] var2, int[] var3, int[] var4, int[] var5, byte[][] var7) {
        this.aByteArrayArray582 = new byte[256][];
        this.anInt375 = 0;
        this.anIntArray1107 = var2;
        this.anIntArray747 = var3;
        this.anIntArray749 = var4;
        this.anIntArray1108 = var5;
        this.method1167(var1);
        this.aByteArrayArray582 = var7;
        int var8 = Integer.MAX_VALUE;
        int var9 = Integer.MIN_VALUE;

        for (int var10 = 0; var10 < 256; ++var10) {
            if (this.anIntArray747[var10] < var8 && this.anIntArray1108[var10] != 0) {
                var8 = this.anIntArray747[var10];
            }

            if (this.anIntArray747[var10] + this.anIntArray1108[var10] > var9) {
                var9 = this.anIntArray747[var10] + this.anIntArray1108[var10];
            }
        }

        this.anInt372 = this.anInt375 - var8;
        this.anInt379 = var9 - this.anInt375;
    }

    BaseFont(byte[] var1) {
        this.aByteArrayArray582 = new byte[256][];
        this.anInt375 = 0;
        this.method1167(var1);
    }

    static int method1153(byte[][] var0, byte[][] var1, int[] var2, int[] var3, int[] var4, int var5, int var6) {
        int var7 = var2[var5];
        int var8 = var7 + var4[var5];
        int var9 = var2[var6];
        int var10 = var9 + var4[var6];
        int var11 = var7;
        if (var9 > var7) {
            var11 = var9;
        }

        int var12 = var8;
        if (var10 < var8) {
            var12 = var10;
        }

        int var13 = var3[var5];
        if (var3[var6] < var13) {
            var13 = var3[var6];
        }

        byte[] var14 = var1[var5];
        byte[] var15 = var0[var6];
        int var16 = var11 - var7;
        int var17 = var11 - var9;

        for (int var18 = var11; var18 < var12; ++var18) {
            int var19 = var14[var16++] + var15[var17++];
            if (var19 < var13) {
                var13 = var19;
            }
        }

        return -var13;
    }

    static void method1159(byte[] var0, int var1, int var2, int var3, int var4, int var5) {
        int var6 = var1 + var2 * drawingAreaWidth;
        int var7 = drawingAreaWidth - var3;
        int var8 = 0;
        int var9 = 0;
        int var10;
        if (var2 < drawingAreaTop) {
            var10 = drawingAreaTop - var2;
            var4 -= var10;
            var2 = drawingAreaTop;
            var9 += var3 * var10;
            var6 += var10 * drawingAreaWidth;
        }

        if (var2 + var4 > drawingAreaRight) {
            var4 -= var2 + var4 - drawingAreaRight;
        }

        if (var1 < drawingAreaLeft) {
            var10 = drawingAreaLeft - var1;
            var3 -= var10;
            var1 = drawingAreaLeft;
            var9 += var10;
            var6 += var10;
            var8 += var10;
            var7 += var10;
        }

        if (var3 + var1 > drawingAreaBottom) {
            var10 = var3 + var1 - drawingAreaBottom;
            var3 -= var10;
            var8 += var10;
            var7 += var10;
        }

        if (var3 > 0 && var4 > 0) {
            method1156(drawingAreaPixels, var0, var5, var9, var6, var3, var4, var7, var8);
        }
    }

    static void method1156(int[] var0, byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = -(var5 >> 2);
        var5 = -(var5 & 3);

        for (int var10 = -var6; var10 < 0; ++var10) {
            int var11;
            for (var11 = var9; var11 < 0; ++var11) {
                if (var1[var3++] != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                if (var1[var3++] != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                if (var1[var3++] != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                if (var1[var3++] != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            for (var11 = var5; var11 < 0; ++var11) {
                if (var1[var3++] != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method1158(int[] var0, byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        var2 = ((var2 & 65280) * var9 & 16711680) + (var9 * (var2 & 16711935) & -16711936) >> 8;
        var9 = 256 - var9;

        for (int var10 = -var6; var10 < 0; ++var10) {
            for (int var11 = -var5; var11 < 0; ++var11) {
                if (var1[var3++] != 0) {
                    int var12 = var0[var4];
                    var0[var4++] = (((var12 & 65280) * var9 & 16711680) + ((var12 & 16711935) * var9 & -16711936) >> 8) + var2;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method1148(byte[] var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = var1 + var2 * drawingAreaWidth;
        int var8 = drawingAreaWidth - var3;
        int var9 = 0;
        int var10 = 0;
        int var11;
        if (var2 < drawingAreaTop) {
            var11 = drawingAreaTop - var2;
            var4 -= var11;
            var2 = drawingAreaTop;
            var10 += var3 * var11;
            var7 += var11 * drawingAreaWidth;
        }

        if (var2 + var4 > drawingAreaRight) {
            var4 -= var2 + var4 - drawingAreaRight;
        }

        if (var1 < drawingAreaLeft) {
            var11 = drawingAreaLeft - var1;
            var3 -= var11;
            var1 = drawingAreaLeft;
            var10 += var11;
            var7 += var11;
            var9 += var11;
            var8 += var11;
        }

        if (var3 + var1 > drawingAreaBottom) {
            var11 = var3 + var1 - drawingAreaBottom;
            var3 -= var11;
            var9 += var11;
            var8 += var11;
        }

        if (var3 > 0 && var4 > 0) {
            method1158(drawingAreaPixels, var0, var5, var10, var7, var3, var4, var8, var9, var6);
        }
    }

    public static String method1166(String var0) {
        int var1 = var0.length();
        int var2 = 0;

        for (int var3 = 0; var3 < var1; ++var3) {
            char var4 = var0.charAt(var3);
            if (var4 == '<' || var4 == '>') {
                var2 += 3;
            }
        }

        StringBuilder var5 = new StringBuilder(var1 + var2);

        for (int var7 = 0; var7 < var1; ++var7) {
            char var6 = var0.charAt(var7);
            if (var6 == '<') {
                var5.append("<lt>");
            } else if (var6 == '>') {
                var5.append("<gt>");
            } else {
                var5.append(var6);
            }
        }

        return var5.toString();
    }

    public static int method1501(CharSequence var0) {
        return Class97.method536(var0, 10);
    }

    public static byte toCp1252Byte(char var0) {
        byte var1;
        if (var0 > 0 && var0 < 128 || var0 >= 160 && var0 <= 255) {
            var1 = (byte) var0;
        } else if (var0 == 8364) {
            var1 = -128;
        } else if (var0 == 8218) {
            var1 = -126;
        } else if (var0 == 402) {
            var1 = -125;
        } else if (var0 == 8222) {
            var1 = -124;
        } else if (var0 == 8230) {
            var1 = -123;
        } else if (var0 == 8224) {
            var1 = -122;
        } else if (var0 == 8225) {
            var1 = -121;
        } else if (var0 == 710) {
            var1 = -120;
        } else if (var0 == 8240) {
            var1 = -119;
        } else if (var0 == 352) {
            var1 = -118;
        } else if (var0 == 8249) {
            var1 = -117;
        } else if (var0 == 338) {
            var1 = -116;
        } else if (var0 == 381) {
            var1 = -114;
        } else if (var0 == 8216) {
            var1 = -111;
        } else if (var0 == 8217) {
            var1 = -110;
        } else if (var0 == 8220) {
            var1 = -109;
        } else if (var0 == 8221) {
            var1 = -108;
        } else if (var0 == 8226) {
            var1 = -107;
        } else if (var0 == 8211) {
            var1 = -106;
        } else if (var0 == 8212) {
            var1 = -105;
        } else if (var0 == 732) {
            var1 = -104;
        } else if (var0 == 8482) {
            var1 = -103;
        } else if (var0 == 353) {
            var1 = -102;
        } else if (var0 == 8250) {
            var1 = -101;
        } else if (var0 == 339) {
            var1 = -100;
        } else if (var0 == 382) {
            var1 = -98;
        } else if (var0 == 376) {
            var1 = -97;
        } else {
            var1 = 63;
        }

        return var1;
    }

    void method1160(int var1, int var2) {
        anInt367 = -1;
        anInt386 = -1;
        anInt366 = var2;
        anInt696 = var2;
        anInt702 = var1;
        anInt788 = var1;
        anInt666 = 256;
        anInt791 = 0;
        anInt563 = 0;
    }

    public int textWidth(String var1) {
        if (var1 == null) {
            return 0;
        }
        int var2 = -1;
        int var3 = -1;
        int var4 = 0;

        for (int var5 = 0; var5 < var1.length(); ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 == '<') {
                var2 = var5;
            } else {
                if (var6 == '>' && var2 != -1) {
                    String var7 = var1.substring(var2 + 1, var5);
                    var2 = -1;
                    if (var7.equals("lt")) {
                        var6 = '<';
                    } else {
                        if (!var7.equals("gt")) {
                            if (var7.startsWith("img=")) {
                                try {
                                    int var8 = method1501(var7.substring(4));
                                    var4 += aDoublyNode_Sub24_Sub4Array1573[var8].anInt375;
                                    var3 = -1;
                                } catch (Exception ignored) {
                                }
                            }
                            continue;
                        }

                        var6 = '>';
                    }
                }

                if (var6 == 160) {
                    var6 = ' ';
                }

                if (var2 == -1) {
                    var4 += this.anIntArray1574[(char) (toCp1252Byte(var6) & 255)];
                    if (this.aByteArray1425 != null && var3 != -1) {
                        var4 += this.aByteArray1425[var6 + (var3 << 8)];
                    }

                    var3 = var6;
                }
            }
        }

        return var4;
    }

    public int method1161(String var1, int[] var2, String[] var3) {
        if (var1 == null) {
            return 0;
        }
        int var4 = 0;
        int var5 = 0;
        StringBuilder var6 = new StringBuilder(100);
        int var7 = -1;
        int var8 = 0;
        byte var9 = 0;
        int var10 = -1;
        char var11 = 0;
        int var12 = 0;
        int var13 = var1.length();

        for (int var14 = 0; var14 < var13; ++var14) {
            char var15 = var1.charAt(var14);
            if (var15 == '<') {
                var10 = var14;
            } else {
                if (var15 == '>' && var10 != -1) {
                    String var16 = var1.substring(var10 + 1, var14);
                    var10 = -1;
                    var6.append('<');
                    var6.append(var16);
                    var6.append('>');
                    if (var16.equals("br")) {
                        var3[var12] = var6.toString().substring(var5, var6.length());
                        ++var12;
                        var5 = var6.length();
                        var4 = 0;
                        var7 = -1;
                        var11 = 0;
                    } else if (var16.equals("lt")) {
                        var4 += this.method1147('<');
                        if (this.aByteArray1425 != null && var11 != -1) {
                            var4 += this.aByteArray1425[(var11 << '\b') + 60];
                        }

                        var11 = '<';
                    } else if (var16.equals("gt")) {
                        var4 += this.method1147('>');
                        if (this.aByteArray1425 != null && var11 != -1) {
                            var4 += this.aByteArray1425[(var11 << '\b') + 62];
                        }

                        var11 = '>';
                    } else if (var16.startsWith("img=")) {
                        try {
                            int var17 = method1501(var16.substring(4));
                            var4 += aDoublyNode_Sub24_Sub4Array1573[var17].anInt375;
                            var11 = 0;
                        } catch (Exception ignored) {
                        }
                    }

                    var15 = 0;
                }

                if (var10 == -1) {
                    if (var15 != 0) {
                        var6.append(var15);
                        var4 += this.method1147(var15);
                        if (this.aByteArray1425 != null && var11 != -1) {
                            var4 += this.aByteArray1425[var15 + (var11 << '\b')];
                        }

                        var11 = var15;
                    }

                    if (var15 == ' ') {
                        var7 = var6.length();
                        var8 = var4;
                        var9 = 1;
                    }

                    if (var2 != null && var4 > var2[var12 < var2.length ? var12 : var2.length - 1] && var7 >= 0) {
                        var3[var12] = var6.toString().substring(var5, var7 - var9);
                        ++var12;
                        var5 = var7;
                        var7 = -1;
                        var4 -= var8;
                        var11 = 0;
                    }

                    if (var15 == '-') {
                        var7 = var6.length();
                        var8 = var4;
                        var9 = 0;
                    }
                }
            }
        }

        String var19 = var6.toString();
        if (var19.length() > var5) {
            var3[var12++] = var19.substring(var5);
        }

        return var12;
    }

    void method1165(String var1, int var2, int var3) {
        var3 -= this.anInt375;
        int var4 = -1;
        int var5 = -1;

        for (int var6 = 0; var6 < var1.length(); ++var6) {
            if (var1.charAt(var6) != 0) {
                char var7 = (char) (toCp1252Byte(var1.charAt(var6)) & 255);
                if (var7 == '<') {
                    var4 = var6;
                } else {
                    int var9;
                    if (var7 == '>' && var4 != -1) {
                        String var8 = var1.substring(var4 + 1, var6);
                        var4 = -1;
                        if (var8.equals("lt")) {
                            var7 = '<';
                        } else {
                            if (!var8.equals("gt")) {
                                if (var8.startsWith("img=")) {
                                    try {
                                        var9 = method1501(var8.substring(4));
                                        IndexedSprite var10 = aDoublyNode_Sub24_Sub4Array1573[var9];
                                        var10.renderAt(var2, var3 + this.anInt375 - var10.anInt372);
                                        var2 += var10.anInt375;
                                        var5 = -1;
                                    } catch (Exception ignored) {
                                    }
                                } else {
                                    this.method1162(var8);
                                }
                                continue;
                            }

                            var7 = '>';
                        }
                    }

                    if (var7 == 160) {
                        var7 = ' ';
                    }

                    if (var4 == -1) {
                        if (this.aByteArray1425 != null && var5 != -1) {
                            var2 += this.aByteArray1425[var7 + (var5 << 8)];
                        }

                        int var12 = this.anIntArray749[var7];
                        var9 = this.anIntArray1108[var7];
                        if (var7 != ' ') {
                            if (anInt666 == 256) {
                                if (anInt696 != -1) {
                                    method1159(this.aByteArrayArray582[var7], var2 + this.anIntArray1107[var7] + 1, var3 + this.anIntArray747[var7] + 1, var12, var9, anInt696);
                                }

                                this.method272(this.aByteArrayArray582[var7], var2 + this.anIntArray1107[var7], var3 + this.anIntArray747[var7], var12, var9, anInt788);
                            } else {
                                if (anInt696 != -1) {
                                    method1148(this.aByteArrayArray582[var7], var2 + this.anIntArray1107[var7] + 1, var3 + this.anIntArray747[var7] + 1, var12, var9, anInt696, anInt666);
                                }

                                this.method273(this.aByteArrayArray582[var7], var2 + this.anIntArray1107[var7], var3 + this.anIntArray747[var7], var12, var9, anInt788, anInt666);
                            }
                        } else if (anInt791 > 0) {
                            anInt563 += anInt791;
                            var2 += anInt563 >> 8;
                            anInt563 &= 255;
                        }

                        int var13 = this.anIntArray1574[var7];
                        if (anInt367 != -1) {
                            drawHorizontalLine(var2, var3 + (int) ((double) this.anInt375 * 0.7D), var13, anInt367);
                        }

                        if (anInt386 != -1) {
                            drawHorizontalLine(var2, var3 + this.anInt375 + 1, var13, anInt386);
                        }

                        var2 += var13;
                        var5 = var7;
                    }
                }
            }
        }

    }

    void method1167(byte[] var1) {
        this.anIntArray1574 = new int[256];
        int var2;
        if (var1.length == 257) {
            for (var2 = 0; var2 < this.anIntArray1574.length; ++var2) {
                this.anIntArray1574[var2] = var1[var2] & 255;
            }

            this.anInt375 = var1[256] & 255;
        } else {
            var2 = 0;

            for (int var3 = 0; var3 < 256; ++var3) {
                this.anIntArray1574[var3] = var1[var2++] & 255;
            }

            int[] var4 = new int[256];
            int[] var5 = new int[256];

            int var6;
            for (var6 = 0; var6 < 256; ++var6) {
                var4[var6] = var1[var2++] & 255;
            }

            for (var6 = 0; var6 < 256; ++var6) {
                var5[var6] = var1[var2++] & 255;
            }

            byte[][] var7 = new byte[256][];

            int var10;
            for (int var8 = 0; var8 < 256; ++var8) {
                var7[var8] = new byte[var4[var8]];
                byte var9 = 0;

                for (var10 = 0; var10 < var7[var8].length; ++var10) {
                    var9 += var1[var2++];
                    var7[var8][var10] = var9;
                }
            }

            byte[][] var11 = new byte[256][];

            int var13;
            for (var13 = 0; var13 < 256; ++var13) {
                var11[var13] = new byte[var4[var13]];
                byte var14 = 0;

                for (int var12 = 0; var12 < var11[var13].length; ++var12) {
                    var14 += var1[var2++];
                    var11[var13][var12] = var14;
                }
            }

            this.aByteArray1425 = new byte[65536];

            for (var13 = 0; var13 < 256; ++var13) {
                if (var13 != 32 && var13 != 160) {
                    for (var10 = 0; var10 < 256; ++var10) {
                        if (var10 != 32 && var10 != 160) {
                            this.aByteArray1425[var10 + (var13 << 8)] = (byte) method1153(var7, var11, var5, this.anIntArray1574, var4, var13, var10);
                        }
                    }
                }
            }

            this.anInt375 = var5[32] + var4[32];
        }

    }

    void method1163(String var1, int var2, int var3, int[] var4, int[] var5) {
        var3 -= this.anInt375;
        int var6 = -1;
        int var7 = -1;
        int var8 = 0;

        for (int var9 = 0; var9 < var1.length(); ++var9) {
            if (var1.charAt(var9) != 0) {
                char var10 = (char) (toCp1252Byte(var1.charAt(var9)) & 255);
                if (var10 == '<') {
                    var6 = var9;
                } else {
                    int var12;
                    int var13;
                    int var14;
                    if (var10 == '>' && var6 != -1) {
                        String var11 = var1.substring(var6 + 1, var9);
                        var6 = -1;
                        if (var11.equals("lt")) {
                            var10 = '<';
                        } else {
                            if (!var11.equals("gt")) {
                                if (var11.startsWith("img=")) {
                                    try {
                                        if (var4 != null) {
                                            var12 = var4[var8];
                                        } else {
                                            var12 = 0;
                                        }

                                        if (var5 != null) {
                                            var13 = var5[var8];
                                        } else {
                                            var13 = 0;
                                        }

                                        ++var8;
                                        var14 = method1501(var11.substring(4));
                                        IndexedSprite var15 = aDoublyNode_Sub24_Sub4Array1573[var14];
                                        var15.renderAt(var12 + var2, var13 + (var3 + this.anInt375 - var15.anInt372));
                                        var2 += var15.anInt375;
                                        var7 = -1;
                                    } catch (Exception ignored) {
                                    }
                                } else {
                                    this.method1162(var11);
                                }
                                continue;
                            }

                            var10 = '>';
                        }
                    }

                    if (var10 == 160) {
                        var10 = ' ';
                    }

                    if (var6 == -1) {
                        if (this.aByteArray1425 != null && var7 != -1) {
                            var2 += this.aByteArray1425[var10 + (var7 << 8)];
                        }

                        int var17 = this.anIntArray749[var10];
                        var12 = this.anIntArray1108[var10];
                        if (var4 != null) {
                            var13 = var4[var8];
                        } else {
                            var13 = 0;
                        }

                        if (var5 != null) {
                            var14 = var5[var8];
                        } else {
                            var14 = 0;
                        }

                        ++var8;
                        if (var10 != ' ') {
                            if (anInt666 == 256) {
                                if (anInt696 != -1) {
                                    method1159(this.aByteArrayArray582[var10], var13 + var2 + this.anIntArray1107[var10] + 1, var3 + var14 + this.anIntArray747[var10] + 1, var17, var12, anInt696);
                                }

                                this.method272(this.aByteArrayArray582[var10], var13 + var2 + this.anIntArray1107[var10], var3 + var14 + this.anIntArray747[var10], var17, var12, anInt788);
                            } else {
                                if (anInt696 != -1) {
                                    method1148(this.aByteArrayArray582[var10], var13 + var2 + this.anIntArray1107[var10] + 1, var3 + var14 + this.anIntArray747[var10] + 1, var17, var12, anInt696, anInt666);
                                }

                                this.method273(this.aByteArrayArray582[var10], var13 + var2 + this.anIntArray1107[var10], var3 + var14 + this.anIntArray747[var10], var17, var12, anInt788, anInt666);
                            }
                        } else if (anInt791 > 0) {
                            anInt563 += anInt791;
                            var2 += anInt563 >> 8;
                            anInt563 &= 255;
                        }

                        int var18 = this.anIntArray1574[var10];
                        if (anInt367 != -1) {
                            drawHorizontalLine(var2, var3 + (int) ((double) this.anInt375 * 0.7D), var18, anInt367);
                        }

                        if (anInt386 != -1) {
                            drawHorizontalLine(var2, var3 + this.anInt375, var18, anInt386);
                        }

                        var2 += var18;
                        var7 = var10;
                    }
                }
            }
        }

    }

    int method1147(char var1) {
        if (var1 == 160) {
            var1 = ' ';
        }

        return this.anIntArray1574[toCp1252Byte(var1) & 255];
    }

    void method1162(String var1) {
        try {
            if (var1.startsWith("col=")) {
                anInt788 = SerializableLong.method466(var1.substring(4), 16);
            } else if (var1.equals("/col")) {
                anInt788 = anInt702;
            } else if (var1.startsWith("str=")) {
                anInt367 = SerializableLong.method466(var1.substring(4), 16);
            } else if (var1.equals("str")) {
                anInt367 = 8388608;
            } else if (var1.equals("/str")) {
                anInt367 = -1;
            } else if (var1.startsWith("u=")) {
                anInt386 = SerializableLong.method466(var1.substring(2), 16);
            } else if (var1.equals("u")) {
                anInt386 = 0;
            } else if (var1.equals("/u")) {
                anInt386 = -1;
            } else if (var1.startsWith("shad=")) {
                anInt696 = SerializableLong.method466(var1.substring(5), 16);
            } else if (var1.equals("shad")) {
                anInt696 = 0;
            } else if (var1.equals("/shad")) {
                anInt696 = anInt366;
            } else if (var1.equals("br")) {
                this.method1160(anInt702, anInt366);
            }
        } catch (Exception ignored) {
        }

    }

    public int method1150(String var1, int var2) {
        return this.method1161(var1, new int[]{var2}, aStringArray1572);
    }

    public int method1149(String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
        if (var1 == null) {
            return 0;
        }
        this.method1160(var6, var7);
        if (var10 == 0) {
            var10 = this.anInt375;
        }

        int[] var11 = new int[]{var4};
        if (var5 < var10 + this.anInt372 + this.anInt379 && var5 < var10 + var10) {
            var11 = null;
        }

        int var12 = this.method1161(var1, var11, aStringArray1572);
        if (var9 == 3 && var12 == 1) {
            var9 = 1;
        }

        int var13;
        int var14;
        if (var9 == 0) {
            var13 = var3 + this.anInt372;
        } else if (var9 == 1) {
            var13 = var3 + (var5 - this.anInt372 - this.anInt379 - var10 * (var12 - 1)) / 2 + this.anInt372;
        } else if (var9 == 2) {
            var13 = var3 + var5 - this.anInt379 - var10 * (var12 - 1);
        } else {
            var14 = (var5 - this.anInt372 - this.anInt379 - var10 * (var12 - 1)) / (var12 + 1);
            if (var14 < 0) {
                var14 = 0;
            }

            var13 = var3 + var14 + this.anInt372;
            var10 += var14;
        }

        for (var14 = 0; var14 < var12; ++var14) {
            if (var8 == 0) {
                this.method1165(aStringArray1572[var14], var2, var13);
            } else if (var8 == 1) {
                this.method1165(aStringArray1572[var14], var2 + (var4 - this.textWidth(aStringArray1572[var14])) / 2, var13);
            } else if (var8 == 2) {
                this.method1165(aStringArray1572[var14], var2 + var4 - this.textWidth(aStringArray1572[var14]), var13);
            } else if (var14 == var12 - 1) {
                this.method1165(aStringArray1572[var14], var2, var13);
            } else {
                this.method1143(aStringArray1572[var14], var4);
                this.method1165(aStringArray1572[var14], var2, var13);
                anInt791 = 0;
            }

            var13 += var10;
        }

        return var12;
    }

    void method1143(String var1, int var2) {
        int var3 = 0;
        boolean var4 = false;

        for (int var5 = 0; var5 < var1.length(); ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 == '<') {
                var4 = true;
            } else if (var6 == '>') {
                var4 = false;
            } else if (!var4 && var6 == ' ') {
                ++var3;
            }
        }

        if (var3 > 0) {
            anInt791 = (var2 - this.textWidth(var1) << 8) / var3;
        }

    }

    abstract void method272(byte[] var1, int var2, int var3, int var4, int var5, int var6);

    abstract void method273(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7);

    public int method1144(String var1, int var2) {
        int var3 = this.method1161(var1, new int[]{var2}, aStringArray1572);
        int var4 = 0;

        for (int var5 = 0; var5 < var3; ++var5) {
            int var6 = this.textWidth(aStringArray1572[var5]);
            if (var6 > var4) {
                var4 = var6;
            }
        }

        return var4;
    }

    public void method1154(String var1, int var2, int var3, int var4, int var5) {
        if (var1 != null) {
            this.method1160(var4, var5);
            this.method1165(var1, var2 - this.textWidth(var1) / 2, var3);
        }
    }

    public void method1142(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.method1160(var4, var5);
            aRandom1575.setSeed(var6);
            anInt666 = 192 + (aRandom1575.nextInt() & 31);
            int[] var7 = new int[var1.length()];
            int var8 = 0;

            for (int var9 = 0; var9 < var1.length(); ++var9) {
                var7[var9] = var8;
                if ((aRandom1575.nextInt() & 3) == 0) {
                    ++var8;
                }
            }

            this.method1163(var1, var2, var3, var7, null);
        }
    }

    public void drawString(String var1, int var2, int var3, int var4, int var5) {
        if (var1 != null) {
            this.method1160(var4, var5);
            this.method1165(var1, var2, var3);
        }
    }

    public void method1151(String var1, int var2, int var3, int var4, int var5) {
        if (var1 != null) {
            this.method1160(var4, var5);
            this.method1165(var1, var2 - this.textWidth(var1), var3);
        }
    }

    public void method1157(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.method1160(var4, var5);
            int[] var7 = new int[var1.length()];

            for (int var8 = 0; var8 < var1.length(); ++var8) {
                var7[var8] = (int) (Math.sin((double) var8 / 2.0D + (double) var6 / 5.0D) * 5.0D);
            }

            this.method1163(var1, var2 - this.textWidth(var1) / 2, var3, null, var7);
        }
    }

    public void method1155(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.method1160(var4, var5);
            int[] var7 = new int[var1.length()];
            int[] var8 = new int[var1.length()];

            for (int var9 = 0; var9 < var1.length(); ++var9) {
                var7[var9] = (int) (Math.sin((double) var9 / 5.0D + (double) var6 / 5.0D) * 5.0D);
                var8[var9] = (int) (Math.sin((double) var9 / 3.0D + (double) var6 / 5.0D) * 5.0D);
            }

            this.method1163(var1, var2 - this.textWidth(var1) / 2, var3, var7, var8);
        }
    }

    public void method1146(String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (var1 != null) {
            this.method1160(var4, var5);
            double var8 = 7.0D - (double) var7 / 8.0D;
            if (var8 < 0.0D) {
                var8 = 0.0D;
            }

            int[] var10 = new int[var1.length()];

            for (int var11 = 0; var11 < var1.length(); ++var11) {
                var10[var11] = (int) (Math.sin((double) var11 / 1.5D + (double) var6 / 1.0D) * var8);
            }

            this.method1163(var1, var2 - this.textWidth(var1) / 2, var3, null, var10);
        }
    }

    public void method1164(String var1, int var2, int var3, int var4, int var5, int var6) {
        if (var1 != null) {
            this.method1160(var4, var5);
            anInt666 = var6;
            this.method1165(var1, var2, var3);
        }
    }
}
