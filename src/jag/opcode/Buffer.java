package jag.opcode;

import jag.commons.Strings;
import jag.commons.collection.Node;
import jag.game.relationship.AssociateComparatorByName;
import jag.statics.*;
import jag.worldmap.WorldMapIcon;

import java.math.BigInteger;

//p = put
//n = negated
//i = inverted
//enc = encrypt
//tab = table
//pos = position
//dec = decrypt
//str = string
//jstr = jagex string
//tiny = TEA
//b = used in signed values
public class Buffer extends Node {

    public static final int[] CRC32TAB;
    public static final long[] CRC64TAB;

    public static final byte[][] SMALL;
    public static final byte[][] MEDIUM;
    public static final byte[][] LARGE;

    public static byte[][][] variadic;

    public static int[] variadicSizes;
    public static int[] variadicIndices;

    public static int smallCount = 0;
    public static int mediumCount = 0;
    public static int largeCount = 0;

    static {
        CRC32TAB = new int[256];

        for (int i = 0; i < 256; ++i) {
            int crc = i;

            for (int j = 0; j < 8; ++j) {
                if ((crc & 1) == 1) {
                    crc = crc >>> 1 ^ 0xedb88320;
                } else {
                    crc >>>= 1;
                }
            }

            CRC32TAB[i] = crc;
        }

        CRC64TAB = new long[256];

        for (int i = 0; i < 256; ++i) {
            long crc = i;

            for (int j = 0; j < 8; ++j) {
                if ((crc & 1L) == 1L) {
                    crc = crc >>> 1 ^ 0xc96c5795d7870f42L;
                } else {
                    crc >>>= 1;
                }
            }

            CRC64TAB[i] = crc;
        }

        SMALL = new byte[1000][];
        MEDIUM = new byte[250][];
        LARGE = new byte[50][];
    }

    public byte[] payload;
    public int pos;

    public Buffer(int size) {
        payload = newSyncPooledBuffer(size);
        pos = 0;
    }

    public Buffer(byte[] payload) {
        this.payload = payload;
        pos = 0;
    }

    public static int stringLengthPlusOne(String str) {
        return str.length() + 1;
    }

    public static synchronized byte[] newPooledBuffer(int size) {
        if (size == 100 && smallCount > 0) {
            byte[] alloc = SMALL[--smallCount];
            SMALL[smallCount] = null;
            return alloc;
        }

        if (size == 5000 && mediumCount > 0) {
            byte[] alloc = MEDIUM[--mediumCount];
            MEDIUM[mediumCount] = null;
            return alloc;
        }

        if (size == 30000 && largeCount > 0) {
            byte[] alloc = LARGE[--largeCount];
            LARGE[largeCount] = null;
            return alloc;
        }

        if (variadic != null) {
            for (int i = 0; i < variadicSizes.length; ++i) {
                if (variadicSizes[i] == size && variadicIndices[i] > 0) {
                    byte[] alloc = variadic[i][--variadicIndices[i]];
                    variadic[i][variadicIndices[i]] = null;
                    return alloc;
                }
            }
        }

        return new byte[size];
    }

    public static synchronized byte[] newSyncPooledBuffer(int size0) {
        return newPooledBuffer(size0);
    }

    public static int pstrseq(CharSequence sequence, int offset, int length, byte[] buffer, int caret) {
        int size = length - offset;

        for (int i = 0; i < size; ++i) {
            char c = sequence.charAt(i + offset);
            if (c > 0 && c < 128 || c >= 160 && c <= 255) {
                buffer[i + caret] = (byte) c;
            } else if (c == 8364) {
                buffer[i + caret] = -128;
            } else if (c == 8218) {
                buffer[i + caret] = -126;
            } else if (c == 402) {
                buffer[i + caret] = -125;
            } else if (c == 8222) {
                buffer[i + caret] = -124;
            } else if (c == 8230) {
                buffer[i + caret] = -123;
            } else if (c == 8224) {
                buffer[i + caret] = -122;
            } else if (c == 8225) {
                buffer[i + caret] = -121;
            } else if (c == 710) {
                buffer[i + caret] = -120;
            } else if (c == 8240) {
                buffer[i + caret] = -119;
            } else if (c == 352) {
                buffer[i + caret] = -118;
            } else if (c == 8249) {
                buffer[i + caret] = -117;
            } else if (c == 338) {
                buffer[i + caret] = -116;
            } else if (c == 381) {
                buffer[i + caret] = -114;
            } else if (c == 8216) {
                buffer[i + caret] = -111;
            } else if (c == 8217) {
                buffer[i + caret] = -110;
            } else if (c == 8220) {
                buffer[i + caret] = -109;
            } else if (c == 8221) {
                buffer[i + caret] = -108;
            } else if (c == 8226) {
                buffer[i + caret] = -107;
            } else if (c == 8211) {
                buffer[i + caret] = -106;
            } else if (c == 8212) {
                buffer[i + caret] = -105;
            } else if (c == 732) {
                buffer[i + caret] = -104;
            } else if (c == 8482) {
                buffer[i + caret] = -103;
            } else if (c == 353) {
                buffer[i + caret] = -102;
            } else if (c == 8250) {
                buffer[i + caret] = -101;
            } else if (c == 339) {
                buffer[i + caret] = -100;
            } else if (c == 382) {
                buffer[i + caret] = -98;
            } else if (c == 376) {
                buffer[i + caret] = -97;
            } else {
                buffer[i + caret] = 63;
            }
        }

        return size;
    }

    public static String readStringFromBytes(byte[] var0, int var1, int var2) {
        char[] var3 = new char[var2];
        int var4 = 0;

        for (int var5 = 0; var5 < var2; ++var5) {
            int var6 = var0[var5 + var1] & 255;
            if (var6 != 0) {
                if (var6 >= 128 && var6 < 160) {
                    char var7 = Statics35.cp1252AsciiExtension[var6 - 128];
                    if (var7 == 0) {
                        var7 = '?';
                    }

                    var6 = var7;
                }

                var3[var4++] = (char) var6;
            }
        }

        return new String(var3, 0, var4);
    }

    public static int crc32(byte[] var0, int var1) {
        return crc32(var0, 0, var1);
    }

    public static int crc32(byte[] var0, int var1, int var2) {
        int var3 = -1;

        for (int var4 = var1; var4 < var2; ++var4) {
            var3 = var3 >>> 8 ^ CRC32TAB[(var3 ^ var0[var4]) & 255];
        }

        var3 = ~var3;
        return var3;
    }

    public static int stringLengthPlusTwo(String var0) {
        return var0.length() + 2;
    }

    public static synchronized void allocate(byte[] buffer) {
        if (buffer.length == 100 && smallCount < 1000) {
            SMALL[++smallCount - 1] = buffer;
        } else if (buffer.length == 5000 && mediumCount < 250) {
            MEDIUM[++mediumCount - 1] = buffer;
        } else if (buffer.length == 30000 && largeCount < 50) {
            LARGE[++largeCount - 1] = buffer;
        } else {
            if (variadic != null) {
                for (int var1 = 0; var1 < variadicSizes.length; ++var1) {
                    if (buffer.length == variadicSizes[var1] && variadicIndices[var1] < variadic[var1].length) {
                        variadic[var1][variadicIndices[var1]++] = buffer;
                        return;
                    }
                }
            }

        }
    }

    public boolean gBit() {
        return (g1() & 1) == 1;
    }

    public int g1() {
        return payload[pos++] & 0xff;
    }

    public byte g1b() {
        return payload[pos++];
    }

    public int g2() {
        return ((payload[pos++] & 0xff) << 8) + (payload[pos++] & 0xff);
    }

    public int g2b() {
        int v = ((payload[pos++] & 0xff) << 8) + (payload[pos++] & 0xff);
        if (v > 32767) {
            v -= 65536;
        }

        return v;
    }

    public int g3() {
        return ((payload[pos++] & 0xff) << 16)
                + ((payload[pos++] & 0xff) << 8)
                + (payload[pos++] & 0xff);
    }

    public int g4() {
        return ((payload[pos++] & 0xff) << 24)
                + ((payload[pos++] & 0xff) << 16)
                + ((payload[pos++] & 0xff) << 8)
                + (payload[pos++] & 0xff);
    }

    public long g8() {
        long a = (long) g4() & 0xffffffffL;
        long b = (long) g4() & 0xffffffffL;
        return b + (a << 32);
    }

    public int gSmartsSeq() {
        int v = 0;

        int inc = gSmarts();
        while (inc == 32767) {
            v += 32767;
            inc = gSmarts();
        }

        v += inc;
        return v;
    }

    public int gSmarts() {
        int ubyte = payload[pos] & 0xff;
        return ubyte >= 128 ? g2() - 32768 : g1();
    }

    public int gSmart() {
        int ubyte = payload[pos] & 0xff;
        return ubyte < 128 ? g1() - 64 : g2() - 49152;
    }

    public void pBit(boolean v) {
        p1(v ? 1 : 0);
    }

    public void p1(int v) {
        payload[pos++] = (byte) v;
    }

    public void p1n(int v) {
        payload[pos++] = (byte) (0 - v);
    }

    public void p2(int v) {
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) v;
    }

    public void ip2(int v) {
        payload[pos++] = (byte) v;
        payload[pos++] = (byte) (v >> 8);
    }

    public void p3(int v) {
        payload[pos++] = (byte) (v >> 16);
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) v;
    }

    public void p4(int v) {
        payload[pos++] = (byte) (v >> 24);
        payload[pos++] = (byte) (v >> 16);
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) v;
    }

    public void ip4(int v) {
        payload[pos++] = (byte) v;
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) (v >> 16);
        payload[pos++] = (byte) (v >> 24);
    }

    public void p8(long v) {
        payload[pos++] = (byte) ((int) (v >> 56));
        payload[pos++] = (byte) ((int) (v >> 48));
        payload[pos++] = (byte) ((int) (v >> 40));
        payload[pos++] = (byte) ((int) (v >> 32));
        payload[pos++] = (byte) ((int) (v >> 24));
        payload[pos++] = (byte) ((int) (v >> 16));
        payload[pos++] = (byte) ((int) (v >> 8));
        payload[pos++] = (byte) ((int) v);
    }

    public void pcstr(String str) {
        int index = str.indexOf(0);
        if (index >= 0) {
            throw new IllegalArgumentException("");
        }
        pos += pstrseq(str, 0, str.length(), payload, pos);
        payload[pos++] = 0;
    }

    public String checkedgstr() {
        byte validator = payload[pos++];
        if (validator != 0) {
            throw new IllegalStateException("");
        }

        int src = pos;

        while (payload[pos++] != 0) {

        }

        int dst = pos - src - 1;
        return dst == 0 ? "" : readStringFromBytes(payload, src, dst);
    }

    public String fastgstr() {
        if (payload[pos] == 0) {
            pos++;
            return null;
        }
        return gstr();
    }

    public String gstr() {
        int src = pos;
        while (payload[pos++] != 0) {

        }

        int dst = pos - src - 1;
        return dst == 0 ? "" : readStringFromBytes(payload, src, dst);
    }

    public int method1051() {
        if (payload[pos] < 0) {
            return g4() & Integer.MAX_VALUE;
        }
        int v = g2();
        return v == 32767 ? -1 : v;
    }

    public int method1044() {
        byte v = payload[pos++];

        int operand = 0;
        while (v < 0) {
            operand = (operand | v & 127) << 7;
            v = payload[pos++];
        }

        return operand | v;
    }

    public void gdata(byte[] data, int src, int offset) {
        for (int i = src; i < offset + src; ++i) {
            data[i] = payload[pos++];
        }
    }

    public int g2s_le() {
        return (payload[pos++] - 128 & 0xff) + ((payload[pos++] & 0xff) << 8);
    }

    public int g2_le() {
        return (payload[pos++] & 0xff) + ((payload[pos++] & 0xff) << 8);
    }

    public int g1_alt4() {
        return 128 - payload[pos++] & 0xff;
    }

    public void pFlag(int flag) {
        if ((flag & -128) != 0) {
            if ((flag & -16384) != 0) {
                if ((flag & -2097152) != 0) {
                    if ((flag & -268435456) != 0) {
                        p1(flag >>> 28 | 128);
                    }
                    p1(flag >>> 21 | 128);
                }
                p1(flag >>> 14 | 128);
            }
            p1(flag >>> 7 | 128);
        }
        p1(flag & 127);
    }

    public int pCrc(int v) {
        int crc = crc32(payload, v, pos);
        p4(crc);
        return crc;
    }

    public void pdata(byte[] data, int src, int offset) {
        for (int i = src; i < offset + src; ++i) {
            payload[pos++] = data[i];
        }
    }

    public int g2or4() {
        return payload[pos] < 0 ? g4() & Integer.MAX_VALUE : g2();
    }

    public void tinyKeyEncrypt(int[] keys, int start, int end) {
        int src = pos;
        pos = start;
        int dst = (end - start) / 8;

        for (int i = 0; i < dst; ++i) {
            int l = g4();
            int r = g4();
            int var9 = 0xc6ef3720;
            int var10 = 0x9e3779b9;

            for (int var11 = 32; var11-- > 0; l -= r + (r << 4 ^ r >>> 5) ^ var9 + keys[var9 & 3]) {
                r -= l + (l << 4 ^ l >>> 5) ^ keys[var9 >>> 11 & 3] + var9;
                var9 -= var10;
            }

            pos -= 8;
            p4(l);
            p4(r);
        }

        pos = src;
    }

    public void ip2_alt1(int v) {
        payload[pos++] = (byte) (v + 128);
        payload[pos++] = (byte) (v >> 8);
    }

    public void cache() {
        if (payload != null) {
            allocate(payload);
        }

        payload = null;
    }

    public int ig1_alt1() {
        return 0 - payload[pos++] & 0xff;
    }

    public int g2_alt4() {
        return ((payload[pos++] & 0xff) << 8) + (payload[pos++] - 128 & 0xff);
    }

    public void pstr(String str) {
        int validator = str.indexOf(0);
        if (validator >= 0) {
            throw new IllegalArgumentException("");
        }
        payload[pos++] = 0;
        pos += pstrseq(str, 0, str.length(), payload, pos);
        payload[pos++] = 0;
    }

    public void p4_alt2(int v) {
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) v;
        payload[pos++] = (byte) (v >> 24);
        payload[pos++] = (byte) (v >> 16);
    }

    public void p4_alt1(int v) {
        payload[pos++] = (byte) (v >> 16);
        payload[pos++] = (byte) (v >> 24);
        payload[pos++] = (byte) v;
        payload[pos++] = (byte) (v >> 8);
    }

    public void psize1(int v) {
        if (v >= 0 && v <= 255) {
            payload[pos - v - 1] = (byte) v;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int g2_alt3() {
        int v = (payload[pos++] - 128 & 0xff) + ((payload[pos++] & 0xff) << 8);
        if (v > 32767) {
            v -= 65536;
        }

        return v;
    }

    public int ig4() {
        return (payload[pos++] & 0xff)
                + ((payload[pos++] & 0xff) << 8)
                + ((payload[pos++] & 0xff) << 16)
                + ((payload[pos++] & 0xff) << 24);
    }

    public int g1_alt3() {
        return payload[pos++] - 128 & 0xff;
    }

    public void p2_alt1(int v) {
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) (v + 128);
    }

    public byte ig1() {
        return (byte) (0 - payload[pos++]);
    }

    public void p1_alt2(int v) {
        payload[pos++] = (byte) (v + 128);
    }

    public void p1_alt1(int v) {
        payload[pos++] = (byte) (128 - v);
    }

    public byte g1_alt2() {
        return (byte) (payload[pos++] - 128);
    }

    public int g4_alt2() {
        return ((payload[pos++] & 0xff) << 8)
                + (payload[pos++] & 0xff)
                + ((payload[pos++] & 0xff) << 24)
                + ((payload[pos++] & 0xff) << 16);
    }

    public boolean matchCrcs() {
        pos -= 4;
        int l = crc32(payload, 0, pos);
        int r = g4();
        return r == l;
    }

    public int g2_alt2() {
        int v = ((payload[pos++] & 0xff) << 8) + (payload[pos++] - 128 & 0xff);
        if (v > 32767) {
            v -= 65536;
        }

        return v;
    }

    public int g4_alt1() {
        return ((payload[pos++] & 0xff) << 16)
                + ((payload[pos++] & 0xff) << 24)
                + (payload[pos++] & 0xff)
                + ((payload[pos++] & 0xff) << 8);
    }

    public String method1045() {
        byte validator = payload[pos++];
        if (validator != 0) {
            throw new IllegalStateException("");
        }
        int offset = method1044();
        if (offset + pos > payload.length) {
            throw new IllegalStateException("");
        }
        String str = AssociateComparatorByName.method719(payload, pos, offset);
        pos += offset;
        return str;
    }

    public byte g1_alt1() {
        return (byte) (128 - payload[pos++]);
    }

    public void pSize2(int v) {
        if (v >= 0 && v <= 65535) {
            payload[pos - v - 2] = (byte) (v >> 8);
            payload[pos - v - 1] = (byte) v;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void pRsa(BigInteger exponent, BigInteger modulus) {
        int var3 = pos;
        pos = 0;
        byte[] var4 = new byte[var3];
        gdata(var4, 0, var3);
        BigInteger var5 = new BigInteger(var4);
        BigInteger var6 = var5.modPow(exponent, modulus);
        byte[] var7 = var6.toByteArray();
        pos = 0;
        p2(var7.length);
        pdata(var7, 0, var7.length);
    }

    public void ip3(int v) {
        payload[pos++] = (byte) v;
        payload[pos++] = (byte) (v >> 8);
        payload[pos++] = (byte) (v >> 16);
    }

    public int g2_alt1() {
        int v = (payload[pos++] & 0xff) + ((payload[pos++] & 0xff) << 8);
        if (v > 32767) {
            v -= 65536;
        }

        return v;
    }

    public void method1052(CharSequence seq) {
        int flag = Strings.method702(seq);
        payload[pos++] = 0;
        pFlag(flag);
        pos += WorldMapIcon.method202(payload, pos, seq);
    }

    public void pSize4(int v) {
        if (v < 0) {
            throw new IllegalArgumentException();
        }
        payload[pos - v - 4] = (byte) (v >> 24);
        payload[pos - v - 3] = (byte) (v >> 16);
        payload[pos - v - 2] = (byte) (v >> 8);
        payload[pos - v - 1] = (byte) v;
    }

    public void pSmart2or3(int v) {
        if (v >= 0 && v < 128) {
            p1(v);
        } else if (v >= 0 && v < 32768) {
            p2(v + 32768);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void p6(long v) {
        payload[pos++] = (byte) ((int) (v >> 40));
        payload[pos++] = (byte) ((int) (v >> 32));
        payload[pos++] = (byte) ((int) (v >> 24));
        payload[pos++] = (byte) ((int) (v >> 16));
        payload[pos++] = (byte) ((int) (v >> 8));
        payload[pos++] = (byte) ((int) v);
    }

    public int p3_alt1() {
        return (payload[pos++] & 0xff)
                + ((payload[pos++] & 0xff) << 8)
                + ((payload[pos++] & 0xff) << 16);
    }

    public void tinyKeyEncrypt(int[] keys) {
        int var2 = pos / 8;
        pos = 0;

        for (int var3 = 0; var3 < var2; ++var3) {
            int var4 = g4();
            int var5 = g4();
            int var6 = 0;
            int var7 = 0x9e3779b9;

            for (int var8 = 32; var8-- > 0; var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ keys[var6 >>> 11 & 3] + var6) {
                var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var6 + keys[var6 & 3];
                var6 += var7;
            }

            pos -= 8;
            p4(var4);
            p4(var5);
        }

    }

    public void tinyKeyEncrypt2(int[] var1, int var2, int var3) {
        int var4 = pos;
        pos = var2;
        int var5 = (var3 - var2) / 8;

        for (int var6 = 0; var6 < var5; ++var6) {
            int var7 = g4();
            int var8 = g4();
            int var9 = 0;
            int var10 = -1640531527;

            for (int var11 = 32; var11-- > 0; var8 += var7 + (var7 << 4 ^ var7 >>> 5) ^ var1[var9 >>> 11 & 3] + var9) {
                var7 += var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
                var9 += var10;
            }

            pos -= 8;
            p4(var7);
            p4(var8);
        }

        pos = var4;
    }

    public void tinyKeyDecrypt(int[] var1) {
        int var2 = pos / 8;
        pos = 0;

        for (int var3 = 0; var3 < var2; ++var3) {
            int var4 = g4();
            int var5 = g4();
            int var6 = -957401312;
            int var7 = -1640531527;

            for (int var8 = 32; var8-- > 0; var4 -= var5 + (var5 << 4 ^ var5 >>> 5) ^ var6 + var1[var6 & 3]) {
                var5 -= var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
                var6 -= var7;
            }

            pos -= 8;
            p4(var4);
            p4(var5);
        }
    }

    public void igdataa(byte[] data, int var2, int var3) {
        for (int i = var3 + var2 - 1; i >= var2; --i) {
            data[i] = (byte) (payload[pos++] - 128);
        }
    }

    public void igdata(byte[] data, int var2, int var3) {
        for (int i = var2; i < var3 + var2; ++i) {
            data[i] = (byte) (payload[pos++] - 128);
        }
    }
}
