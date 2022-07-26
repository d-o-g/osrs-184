package jag.audi;

import jag.audi.vorbis.RawAudioOverride;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class AudioEffect {
    public final AudioInstrument[] instruments;
    public int start;
    public int end;

    public AudioEffect(Buffer var1) {
        instruments = new AudioInstrument[10];

        for (int var2 = 0; var2 < 10; ++var2) {
            int var3 = var1.g1();
            if (var3 != 0) {
                --var1.pos;
                instruments[var2] = new AudioInstrument();
                instruments[var2].method1298(var1);
            }
        }

        start = var1.g2();
        end = var1.g2();
    }

    public static AudioEffect load(ReferenceTable var0, int var1, int var2) {
        byte[] var3 = var0.unpack(var1, var2);
        return var3 == null ? null : new AudioEffect(new Buffer(var3));
    }

    public final byte[] method1520() {
        int var1 = 0;

        int var2;
        for (var2 = 0; var2 < 10; ++var2) {
            if (instruments[var2] != null && instruments[var2].anInt1790 + instruments[var2].anInt1787 > var1) {
                var1 = instruments[var2].anInt1790 + instruments[var2].anInt1787;
            }
        }

        if (var1 == 0) {
            return new byte[0];
        }
        var2 = var1 * 22050 / 1000;
        byte[] var3 = new byte[var2];

        for (int var4 = 0; var4 < 10; ++var4) {
            if (instruments[var4] != null) {
                int var5 = instruments[var4].anInt1790 * 22050 / 1000;
                int var6 = instruments[var4].anInt1787 * 22050 / 1000;
                int[] var7 = instruments[var4].method1299(var5, instruments[var4].anInt1790);

                for (int var8 = 0; var8 < var5; ++var8) {
                    int var9 = (var7[var8] >> 8) + var3[var8 + var6];
                    if ((var9 + 128 & -256) != 0) {
                        var9 = var9 >> 31 ^ 127;
                    }

                    var3[var8 + var6] = (byte) var9;
                }
            }
        }

        return var3;
    }

    public RawAudioOverride method1523() {
        byte[] var1 = method1520();
        return new RawAudioOverride(var1, start * 22050 / 1000, end * 22050 / 1000);
    }

    public final int method1521() {
        int var1 = 9999999;

        int var2;
        for (var2 = 0; var2 < 10; ++var2) {
            if (instruments[var2] != null && instruments[var2].anInt1787 / 20 < var1) {
                var1 = instruments[var2].anInt1787 / 20;
            }
        }

        if (start < end && start / 20 < var1) {
            var1 = start / 20;
        }

        if (var1 != 9999999 && var1 != 0) {
            for (var2 = 0; var2 < 10; ++var2) {
                if (instruments[var2] != null) {
                    AudioInstrument var10000 = instruments[var2];
                    var10000.anInt1787 -= var1 * 20;
                }
            }

            if (start < end) {
                start -= var1 * 20;
                end -= var1 * 20;
            }

            return var1;
        }
        return 0;
    }
}
