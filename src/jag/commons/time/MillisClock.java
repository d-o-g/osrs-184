package jag.commons.time;

public class MillisClock extends Clock {
    final long[] aLongArray820;
    int anInt821;
    int anInt819;
    int anInt817;
    long aLong818;
    int anInt816;

    MillisClock() {
        this.aLongArray820 = new long[10];
        this.anInt821 = 256;
        this.anInt819 = 1;
        this.anInt817 = 0;
        this.aLong818 = Clock.now();

        for (int var1 = 0; var1 < 10; ++var1) {
            this.aLongArray820[var1] = this.aLong818;
        }

    }

    public int sleep(int time, int var2) {
        int var3 = this.anInt821;
        int var4 = this.anInt819;
        this.anInt821 = 300;
        this.anInt819 = 1;
        this.aLong818 = Clock.now();
        if (0L == this.aLongArray820[this.anInt816]) {
            this.anInt821 = var3;
            this.anInt819 = var4;
        } else if (this.aLong818 > this.aLongArray820[this.anInt816]) {
            this.anInt821 = (int) ((long) (time * 2560) / (this.aLong818 - this.aLongArray820[this.anInt816]));
        }

        if (this.anInt821 < 25) {
            this.anInt821 = 25;
        }

        if (this.anInt821 > 256) {
            this.anInt821 = 256;
            this.anInt819 = (int) ((long) time - (this.aLong818 - this.aLongArray820[this.anInt816]) / 10L);
        }

        if (this.anInt819 > time) {
            this.anInt819 = time;
        }

        this.aLongArray820[this.anInt816] = this.aLong818;
        this.anInt816 = (this.anInt816 + 1) % 10;
        if (this.anInt819 > 1) {
            for (int var5 = 0; var5 < 10; ++var5) {
                if (0L != this.aLongArray820[var5]) {
                    this.aLongArray820[var5] += this.anInt819;
                }
            }
        }

        if (this.anInt819 < var2) {
            this.anInt819 = var2;
        }

        long var6 = this.anInt819;
        if (var6 > 0L) {
            if (var6 % 10L == 0L) {
                long var8 = var6 - 1L;

                try {
                    Thread.sleep(var8);
                } catch (InterruptedException ignored) {
                }

                try {
                    Thread.sleep(1L);
                } catch (InterruptedException ignored) {
                }
            } else {
                try {
                    Thread.sleep(var6);
                } catch (InterruptedException ignored) {
                }
            }
        }

        int var12;
        for (var12 = 0; this.anInt817 < 256; this.anInt817 += this.anInt821) {
            ++var12;
        }

        this.anInt817 &= 255;
        return var12;
    }

    public void update() {
        for (int var1 = 0; var1 < 10; ++var1) {
            this.aLongArray820[var1] = 0L;
        }

    }
}
