package jagex.jagex3.sound;

import jagex.messaging.Buffer;

public class AudioEnvelope {

  public int ticks;
  public int maxTicks;
  public int points;
  public int anInt2027;
  public int phaseIndex;
  public int[] anIntArray2029;
  public int[] phases;
  public int amplitude;
  public int step;
  public int start;
  public int end;

  public AudioEnvelope() {
    this.points = 2;
    this.anIntArray2029 = new int[2];
    this.phases = new int[2];
    this.anIntArray2029[1] = 65535;
    this.phases[1] = 65535;
  }

  public final void decode(Buffer buffer) {
    this.anInt2027 = buffer.g1();
    this.start = buffer.g4();
    this.end = buffer.g4();
    this.decodePoints(buffer);
  }

  public final void reset() {
    this.ticks = 0;
    this.phaseIndex = 0;
    this.step = 0;
    this.amplitude = 0;
    this.maxTicks = 0;
  }

  public final void decodePoints(Buffer buffer) {
    this.points = buffer.g1();
    this.anIntArray2029 = new int[this.points];
    this.phases = new int[this.points];

    for (int i = 0; i < this.points; ++i) {
      this.anIntArray2029[i] = buffer.g2();
      this.phases[i] = buffer.g2();
    }

  }

  public final int nextSample(int rate) {
    if (this.maxTicks >= this.ticks) {
      this.amplitude = this.phases[this.phaseIndex++] << 15;
      if (this.phaseIndex >= this.points) {
        this.phaseIndex = this.points - 1;
      }

      this.ticks = (int) ((double) this.anIntArray2029[this.phaseIndex] / 65536.0D * (double) rate);
      if (this.ticks > this.maxTicks) {
        this.step = ((this.phases[this.phaseIndex] << 15) - this.amplitude) / (this.ticks - this.maxTicks);
      }
    }

    this.amplitude += this.step;
    ++this.maxTicks;
    return this.amplitude - this.step >> 15;
  }
}
