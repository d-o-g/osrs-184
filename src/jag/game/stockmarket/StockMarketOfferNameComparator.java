package jag.game.stockmarket;

import jag.Login;
import jag.URLRequest;
import jag.audi.AudioRunnable;
import jag.audi.AudioSystem;
import jag.commons.Jagexception;
import jag.game.type.Varbit;
import jag.js5.ReferenceTable;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class StockMarketOfferNameComparator implements Comparator<StockMarketEvent> {

    public static ReferenceTable aReferenceTable480;

    public static AudioSystem method329(int var1, int var2) {
        if (URLRequest.audioSampleRate == 0) {
            throw new IllegalStateException();
        }

        if (var1 >= 0 && var1 < 2) {
            if (var2 < 256) {
                var2 = 256;
            }

            try {
                AudioSystem var3 = AudioSystem.provider.provide();
                var3.samples = new int[256 * (AudioSystem.useTwoChannels ? 2 : 1)];
                var3.anInt1847 = var2;
                var3.method1089();
                var3.anInt1834 = (var2 & -1024) + 1024;
                if (var3.anInt1834 > 16384) {
                    var3.anInt1834 = 16384;
                }

                var3.method1090(var3.anInt1834);
                if (Login.anInt603 > 0 && Jagexception.anAudioRunnable1880 == null) {
                    Jagexception.anAudioRunnable1880 = new AudioRunnable();
                    AudioSystem.service = Executors.newScheduledThreadPool(1);
                    AudioSystem.service.scheduleAtFixedRate(Jagexception.anAudioRunnable1880, 0L, 10L, TimeUnit.MILLISECONDS);
                }

                if (Jagexception.anAudioRunnable1880 != null) {
                    if (Jagexception.anAudioRunnable1880.systems[var1] != null) {
                        throw new IllegalArgumentException();
                    }

                    Jagexception.anAudioRunnable1880.systems[var1] = var3;
                }

                return var3;
            } catch (Throwable var4) {
                return new AudioSystem();
            }
        }
        throw new IllegalArgumentException();
    }

    public static void method327(ReferenceTable var0) {
        Varbit.table = var0;
    }

    public static void method328(String[] var0, short[] var1, int var2, int var3) {
        if (var2 < var3) {
            int var4 = (var3 + var2) / 2;
            int var5 = var2;
            String var6 = var0[var4];
            var0[var4] = var0[var3];
            var0[var3] = var6;
            short var7 = var1[var4];
            var1[var4] = var1[var3];
            var1[var3] = var7;

            for (int var8 = var2; var8 < var3; ++var8) {
                if (var6 == null || var0[var8] != null && var0[var8].compareTo(var6) < (var8 & 1)) {
                    String var9 = var0[var8];
                    var0[var8] = var0[var5];
                    var0[var5] = var9;
                    short var10 = var1[var8];
                    var1[var8] = var1[var5];
                    var1[var5++] = var10;
                }
            }

            var0[var3] = var0[var5];
            var0[var5] = var6;
            var1[var3] = var1[var5];
            var1[var5] = var7;
            method328(var0, var1, var2, var5 - 1);
            method328(var0, var1, var5 + 1, var3);
        }

    }

    int method326(StockMarketEvent var1, StockMarketEvent var2) {
        return var1.method390().compareTo(var2.method390());
    }

    public int compare(StockMarketEvent var1, StockMarketEvent var2) {
        return this.method326(var1, var2);
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }
}
