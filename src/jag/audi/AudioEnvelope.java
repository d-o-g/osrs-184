package jag.audi;

import jag.opcode.Buffer;

public class AudioEnvelope {
    public int ticks;
    public int max;
    public int anInt2028;
    public int anInt2027;
    public int phaseIndex;
    public int[] anIntArray2029;
    public int[] phases;
    public int amplitude;
    public int step;
    public int start;
    public int end;

    public AudioEnvelope() {
        this.anInt2028 = 2;
        this.anIntArray2029 = new int[2];
        this.phases = new int[2];
        this.anIntArray2029[0] = 0;
        this.anIntArray2029[1] = 65535;
        this.phases[0] = 0;
        this.phases[1] = 65535;
    }

    public final void method1511(Buffer var1) {
        this.anInt2027 = var1.g1();
        this.start = var1.g4();
        this.end = var1.g4();
        this.method1512(var1);
    }

    public final void method1510() {
        this.ticks = 0;
        this.phaseIndex = 0;
        this.step = 0;
        this.amplitude = 0;
        this.max = 0;
    }

    public final void method1512(Buffer var1) {
        this.anInt2028 = var1.g1();
        this.anIntArray2029 = new int[this.anInt2028];
        this.phases = new int[this.anInt2028];

        for (int var2 = 0; var2 < this.anInt2028; ++var2) {
            this.anIntArray2029[var2] = var1.g2();
            this.phases[var2] = var1.g2();
        }

    }

    public final int method1509(int var1) {
        if (this.max >= this.ticks) {
            this.amplitude = this.phases[this.phaseIndex++] << 15;
            if (this.phaseIndex >= this.anInt2028) {
                this.phaseIndex = this.anInt2028 - 1;
            }

            this.ticks = (int) ((double) this.anIntArray2029[this.phaseIndex] / 65536.0D * (double) var1);
            if (this.ticks > this.max) {
                this.step = ((this.phases[this.phaseIndex] << 15) - this.amplitude) / (this.ticks - this.max);
            }
        }

        this.amplitude += this.step;
        ++this.max;
        return this.amplitude - this.step >> 15;
    }
}
