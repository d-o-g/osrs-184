package jagex.jagex3.sound;

import jagex.messaging.Buffer;
import jagex.statics.Statics31;

import java.util.Random;

public class AudioInstrument {

  public static final int[] RANDOM;
  public static final int[] DECAY;
  public static final int[] SIN_WAVE;
  public static final int[] ATTACK;
  public static final int[] SQUARE;
  public static final int[] NOISE;
  public static final int[] PHASE;
  public static final int[] AMPLITUDE;

  static {
    RANDOM = new int[32768];
    Random random = new Random(0L);

    for (int i = 0; i < 32768; ++i) {
      RANDOM[i] = (random.nextInt() & 2) - 1;
    }

    SIN_WAVE = new int[32768];

    for (int i = 0; i < 32768; ++i) {
      SIN_WAVE[i] = (int) (Math.sin((double) i / 5215.1903D) * 16384.0D);
    }

    DECAY = new int[220500];
    ATTACK = new int[5];
    SQUARE = new int[5];
    NOISE = new int[5];
    PHASE = new int[5];
    AMPLITUDE = new int[5];
  }

  public AudioEnvelope pitchEnvelope;
  public int[] amplitude;
  public int attackDelay;
  public int decayDelay;
  public AudioEnvelope volumeEnvelope;
  public int[] oscillatorPitch;
  public AudioEnvelope pitchVolumeEnvelope;
  public AudioEnvelope sustainEnvelope;
  public AudioEnvelope releaseEnvelope;
  public int[] phase;
  public AudioEnvelope idkEnvelope;
  public int initialPhase;
  public int phaseShift;
  public AudioEnvelope phaseVolumeEnvelope;
  public AudioEnvelope anAudioEnvelope1781;
  public AudioFilter filter;
  public AudioEnvelope filterEnvelope;

  public AudioInstrument() {
    this.amplitude = new int[]{0, 0, 0, 0, 0};
    this.oscillatorPitch = new int[]{0, 0, 0, 0, 0};
    this.phase = new int[]{0, 0, 0, 0, 0};
    this.initialPhase = 0;
    this.phaseShift = 100;
    this.attackDelay = 500;
    this.decayDelay = 0;
  }

  public final void decode(Buffer buffer) {
    this.pitchEnvelope = new AudioEnvelope();
    this.pitchEnvelope.decode(buffer);
    this.volumeEnvelope = new AudioEnvelope();
    this.volumeEnvelope.decode(buffer);
    int remaining = buffer.g1();
    if (remaining != 0) {
      --buffer.pos;
      this.pitchVolumeEnvelope = new AudioEnvelope();
      this.pitchVolumeEnvelope.decode(buffer);
      this.releaseEnvelope = new AudioEnvelope();
      this.releaseEnvelope.decode(buffer);
    }

    remaining = buffer.g1();
    if (remaining != 0) {
      --buffer.pos;
      this.sustainEnvelope = new AudioEnvelope();
      this.sustainEnvelope.decode(buffer);
      this.idkEnvelope = new AudioEnvelope();
      this.idkEnvelope.decode(buffer);
    }

    remaining = buffer.g1();
    if (remaining != 0) {
      --buffer.pos;
      this.phaseVolumeEnvelope = new AudioEnvelope();
      this.phaseVolumeEnvelope.decode(buffer);
      this.anAudioEnvelope1781 = new AudioEnvelope();
      this.anAudioEnvelope1781.decode(buffer);
    }

    for (int i = 0; i < 10; ++i) {
      int amp = buffer.gSmarts();
      if (amp == 0) {
        break;
      }

      this.amplitude[i] = amp;
      this.oscillatorPitch[i] = buffer.gSmart();
      this.phase[i] = buffer.gSmarts();
    }

    this.initialPhase = buffer.gSmarts();
    this.phaseShift = buffer.gSmarts();
    this.attackDelay = buffer.g2();
    this.decayDelay = buffer.g2();
    this.filter = new AudioFilter();
    this.filterEnvelope = new AudioEnvelope();
    this.filter.decode(buffer, this.filterEnvelope);
  }

  public final int[] generateSamples(int length, int sampleRate) {
    Statics31.method1139(DECAY, 0, length);
    if (sampleRate < 10) {
      return DECAY;
    }

    double rate = (double) length / ((double) sampleRate + 0.0D);
    this.pitchEnvelope.reset();
    this.volumeEnvelope.reset();
    int base = 0;
    int offset = 0;
    int var7 = 0;
    if (this.pitchVolumeEnvelope != null) {
      this.pitchVolumeEnvelope.reset();
      this.releaseEnvelope.reset();
      base = (int) ((double) (this.pitchVolumeEnvelope.end - this.pitchVolumeEnvelope.start) * 32.768D / rate);
      offset = (int) ((double) this.pitchVolumeEnvelope.start * 32.768D / rate);
    }

    int var8 = 0;
    int var9 = 0;
    int var10 = 0;
    if (this.sustainEnvelope != null) {
      this.sustainEnvelope.reset();
      this.idkEnvelope.reset();
      var8 = (int) ((double) (this.sustainEnvelope.end - this.sustainEnvelope.start) * 32.768D / rate);
      var9 = (int) ((double) this.sustainEnvelope.start * 32.768D / rate);
    }

    int var11;
    for (var11 = 0; var11 < 5; ++var11) {
      if (this.amplitude[var11] != 0) {
        ATTACK[var11] = 0;
        SQUARE[var11] = (int) ((double) this.phase[var11] * rate);
        NOISE[var11] = (this.amplitude[var11] << 14) / 100;
        PHASE[var11] = (int) ((double) (this.pitchEnvelope.end - this.pitchEnvelope.start) * 32.768D * Math.pow(1.0057929410678534D, this.oscillatorPitch[var11]) / rate);
        AMPLITUDE[var11] = (int) ((double) this.pitchEnvelope.start * 32.768D / rate);
      }
    }

    int var12;
    int var13;
    int var14;
    int var15;
    int[] var10000;
    for (var11 = 0; var11 < length; ++var11) {
      var12 = this.pitchEnvelope.nextSample(length);
      var13 = this.volumeEnvelope.nextSample(length);
      if (this.pitchVolumeEnvelope != null) {
        var14 = this.pitchVolumeEnvelope.nextSample(length);
        var15 = this.releaseEnvelope.nextSample(length);
        var12 += this.method1300(var7, var15, this.pitchVolumeEnvelope.anInt2027) >> 1;
        var7 = var7 + offset + (var14 * base >> 16);
      }

      if (this.sustainEnvelope != null) {
        var14 = this.sustainEnvelope.nextSample(length);
        var15 = this.idkEnvelope.nextSample(length);
        var13 = var13 * ((this.method1300(var10, var15, this.sustainEnvelope.anInt2027) >> 1) + 32768) >> 15;
        var10 = var10 + var9 + (var14 * var8 >> 16);
      }

      for (var14 = 0; var14 < 5; ++var14) {
        if (this.amplitude[var14] != 0) {
          var15 = SQUARE[var14] + var11;
          if (var15 < length) {
            var10000 = DECAY;
            var10000[var15] += this.method1300(ATTACK[var14], var13 * NOISE[var14] >> 15, this.pitchEnvelope.anInt2027);
            var10000 = ATTACK;
            var10000[var14] += (var12 * PHASE[var14] >> 16) + AMPLITUDE[var14];
          }
        }
      }
    }

    int var16;
    if (this.phaseVolumeEnvelope != null) {
      this.phaseVolumeEnvelope.reset();
      this.anAudioEnvelope1781.reset();
      var11 = 0;
      boolean var20 = true;

      for (var14 = 0; var14 < length; ++var14) {
        var15 = this.phaseVolumeEnvelope.nextSample(length);
        var16 = this.anAudioEnvelope1781.nextSample(length);
        if (var20) {
          var12 = (var15 * (this.phaseVolumeEnvelope.end - this.phaseVolumeEnvelope.start) >> 8) + this.phaseVolumeEnvelope.start;
        } else {
          var12 = (var16 * (this.phaseVolumeEnvelope.end - this.phaseVolumeEnvelope.start) >> 8) + this.phaseVolumeEnvelope.start;
        }

        var11 += 256;
        if (var11 >= var12) {
          var11 = 0;
          var20 = !var20;
        }

        if (var20) {
          DECAY[var14] = 0;
        }
      }
    }

    if (this.initialPhase > 0 && this.phaseShift > 0) {
      var11 = (int) ((double) this.initialPhase * rate);

      for (var12 = var11; var12 < length; ++var12) {
        var10000 = DECAY;
        var10000[var12] += DECAY[var12 - var11] * this.phaseShift / 100;
      }
    }

    if (this.filter.anIntArray743[0] > 0 || this.filter.anIntArray743[1] > 0) {
      this.filterEnvelope.reset();
      var11 = this.filterEnvelope.nextSample(length + 1);
      var12 = this.filter.method548(0, (float) var11 / 65536.0F);
      var13 = this.filter.method548(1, (float) var11 / 65536.0F);
      if (length >= var12 + var13) {
        var14 = 0;
        var15 = Math.min(var13, length - var12);

        int var17;
        while (var14 < var15) {
          var16 = (int) ((long) DECAY[var14 + var12] * (long) AudioFilter.anInt742 >> 16);

          for (var17 = 0; var17 < var12; ++var17) {
            var16 += (int) ((long) DECAY[var14 + var12 - 1 - var17] * (long) AudioFilter.anIntArrayArray738[0][var17] >> 16);
          }

          for (var17 = 0; var17 < var14; ++var17) {
            var16 -= (int) ((long) DECAY[var14 - 1 - var17] * (long) AudioFilter.anIntArrayArray738[1][var17] >> 16);
          }

          DECAY[var14] = var16;
          var11 = this.filterEnvelope.nextSample(length + 1);
          ++var14;
        }

        var15 = 128;

        while (true) {
          if (var15 > length - var12) {
            var15 = length - var12;
          }

          int var18;
          while (var14 < var15) {
            var17 = (int) ((long) DECAY[var14 + var12] * (long) AudioFilter.anInt742 >> 16);

            for (var18 = 0; var18 < var12; ++var18) {
              var17 += (int) ((long) DECAY[var14 + var12 - 1 - var18] * (long) AudioFilter.anIntArrayArray738[0][var18] >> 16);
            }

            for (var18 = 0; var18 < var13; ++var18) {
              var17 -= (int) ((long) DECAY[var14 - 1 - var18] * (long) AudioFilter.anIntArrayArray738[1][var18] >> 16);
            }

            DECAY[var14] = var17;
            var11 = this.filterEnvelope.nextSample(length + 1);
            ++var14;
          }

          if (var14 >= length - var12) {
            while (var14 < length) {
              var17 = 0;

              for (var18 = var14 + var12 - length; var18 < var12; ++var18) {
                var17 += (int) ((long) DECAY[var14 + var12 - 1 - var18] * (long) AudioFilter.anIntArrayArray738[0][var18] >> 16);
              }

              for (var18 = 0; var18 < var13; ++var18) {
                var17 -= (int) ((long) DECAY[var14 - 1 - var18] * (long) AudioFilter.anIntArrayArray738[1][var18] >> 16);
              }

              DECAY[var14] = var17;
              this.filterEnvelope.nextSample(length + 1);
              ++var14;
            }
            break;
          }

          var12 = this.filter.method548(0, (float) var11 / 65536.0F);
          var13 = this.filter.method548(1, (float) var11 / 65536.0F);
          var15 += 128;
        }
      }
    }

    for (var11 = 0; var11 < length; ++var11) {
      if (DECAY[var11] < -32768) {
        DECAY[var11] = -32768;
      }

      if (DECAY[var11] > 32767) {
        DECAY[var11] = 32767;
      }
    }

    return DECAY;
  }

  public final int method1300(int var1, int var2, int var3) {
    if (var3 == 1) {
      return (var1 & 32767) < 16384 ? var2 : -var2;
    }
    if (var3 == 2) {
      return SIN_WAVE[var1 & 32767] * var2 >> 14;
    }
    if (var3 == 3) {
      return (var2 * (var1 & 32767) >> 14) - var2;
    }
    return var3 == 4 ? var2 * RANDOM[var1 / 2607 & 32767] : 0;
  }
}
