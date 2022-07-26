package jag.audi;

import jag.opcode.Buffer;

public class MidiFileDecoder {
    static final byte[] aByteArray1118 = new byte[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public int[] anIntArray1115;
    long aLong1119;
    int[] anIntArray1116;
    Buffer aBuffer1120;
    int anInt1114;
    int[] anIntArray1117;
    int anInt1121;
    int[] anIntArray1113;

    public MidiFileDecoder(byte[] var1) {
        this.aBuffer1120 = new Buffer(null);
        this.method793(var1);
    }

    MidiFileDecoder() {
        this.aBuffer1120 = new Buffer(null);
    }

    int method783(int var1) {
        byte var2 = this.aBuffer1120.payload[this.aBuffer1120.pos];
        int var5;
        if (var2 < 0) {
            var5 = var2 & 255;
            this.anIntArray1113[var1] = var5;
            ++this.aBuffer1120.pos;
        } else {
            var5 = this.anIntArray1113[var1];
        }

        if (var5 != 240 && var5 != 247) {
            return this.method781(var1, var5);
        }
        int var3 = this.aBuffer1120.method1044();
        if (var5 == 247 && var3 > 0) {
            int var4 = this.aBuffer1120.payload[this.aBuffer1120.pos] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
                ++this.aBuffer1120.pos;
                this.anIntArray1113[var1] = var4;
                return this.method781(var1, var4);
            }
        }

        Buffer var10000 = this.aBuffer1120;
        var10000.pos += var3;
        return 0;
    }

    void method785() {
        this.aBuffer1120.payload = null;
        this.anIntArray1117 = null;
        this.anIntArray1116 = null;
        this.anIntArray1115 = null;
        this.anIntArray1113 = null;
    }

    boolean method791() {
        return this.aBuffer1120.payload != null;
    }

    void method793(byte[] var1) {
        this.aBuffer1120.payload = var1;
        this.aBuffer1120.pos = 10;
        int var2 = this.aBuffer1120.g2();
        this.anInt1121 = this.aBuffer1120.g2();
        this.anInt1114 = 500000;
        this.anIntArray1117 = new int[var2];

        Buffer var10000;
        int var3;
        int var5;
        for (var3 = 0; var3 < var2; var10000.pos += var5) {
            int var4 = this.aBuffer1120.g4();
            var5 = this.aBuffer1120.g4();
            if (var4 == 1297379947) {
                this.anIntArray1117[var3] = this.aBuffer1120.pos;
                ++var3;
            }

            var10000 = this.aBuffer1120;
        }

        this.aLong1119 = 0L;
        this.anIntArray1116 = new int[var2];

        for (var3 = 0; var3 < var2; ++var3) {
            this.anIntArray1116[var3] = this.anIntArray1117[var3];
        }

        this.anIntArray1115 = new int[var2];
        this.anIntArray1113 = new int[var2];
    }

    public void method789(int var1) {
        this.aBuffer1120.pos = this.anIntArray1116[var1];
    }

    public int method786() {
        int var1 = this.anIntArray1116.length;
        int var2 = -1;
        int var3 = Integer.MAX_VALUE;

        for (int var4 = 0; var4 < var1; ++var4) {
            if (this.anIntArray1116[var4] >= 0 && this.anIntArray1115[var4] < var3) {
                var2 = var4;
                var3 = this.anIntArray1115[var4];
            }
        }

        return var2;
    }

    public int method790() {
        return this.anIntArray1116.length;
    }

    public int method792(int var1) {
        return this.method783(var1);
    }

    public void method784(int var1) {
        int var2 = this.aBuffer1120.method1044();
        int[] var10000 = this.anIntArray1115;
        var10000[var1] += var2;
    }

    int method781(int var1, int var2) {
        int var4;
        if (var2 == 255) {
            int var7 = this.aBuffer1120.g1();
            var4 = this.aBuffer1120.method1044();
            Buffer var10000;
            if (var7 == 47) {
                var10000 = this.aBuffer1120;
                var10000.pos += var4;
                return 1;
            }
            if (var7 == 81) {
                int var5 = this.aBuffer1120.g3();
                var4 -= 3;
                int var6 = this.anIntArray1115[var1];
                this.aLong1119 += (long) var6 * (long) (this.anInt1114 - var5);
                this.anInt1114 = var5;
                var10000 = this.aBuffer1120;
                var10000.pos += var4;
                return 2;
            }
            var10000 = this.aBuffer1120;
            var10000.pos += var4;
            return 3;
        }
        byte var3 = aByteArray1118[var2 - 128];
        var4 = var2;
        if (var3 >= 1) {
            var4 = var2 | this.aBuffer1120.g1() << 8;
        }

        if (var3 >= 2) {
            var4 |= this.aBuffer1120.g1() << 16;
        }

        return var4;
    }

    long method780(int var1) {
        return this.aLong1119 + (long) var1 * (long) this.anInt1114;
    }

    public void method788() {
        this.aBuffer1120.pos = -1;
    }

    public void method787(int var1) {
        this.anIntArray1116[var1] = this.aBuffer1120.pos;
    }

    public boolean method779() {
        int var1 = this.anIntArray1116.length;

        for (int anAnIntArray1116 : this.anIntArray1116) {
            if (anAnIntArray1116 >= 0) {
                return false;
            }
        }

        return true;
    }

    void method782(long var1) {
        this.aLong1119 = var1;
        int var3 = this.anIntArray1116.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            this.anIntArray1115[var4] = 0;
            this.anIntArray1113[var4] = 0;
            this.aBuffer1120.pos = this.anIntArray1117[var4];
            this.method784(var4);
            this.anIntArray1116[var4] = this.aBuffer1120.pos;
        }

    }
}
