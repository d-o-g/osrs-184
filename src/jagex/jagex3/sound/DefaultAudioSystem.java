package jagex.jagex3.sound;

import jagex.oldscape.URLRequest;
import jagex.datastructure.instrusive.hashtable.IterableNodeTable;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

public class DefaultAudioSystem extends AudioSystem {

  public AudioFormat format;
  public int bufferSize;
  public SourceDataLine sourceDataLine;
  public byte[] buffer;

  public DefaultAudioSystem() {
  }

  public void method1085() {
    this.sourceDataLine.flush();
  }

  public void method1086() {
    if (this.sourceDataLine != null) {
      this.sourceDataLine.close();
      this.sourceDataLine = null;
    }

  }

  public int method1088() {
    return this.bufferSize - (this.sourceDataLine.available() >> (useTwoChannels ? 2 : 1));
  }

  public void method1090(int var1) throws LineUnavailableException {
    try {
      Info var2 = new Info(SourceDataLine.class, this.format, var1 << (useTwoChannels ? 2 : 1));
      this.sourceDataLine = (SourceDataLine) javax.sound.sampled.AudioSystem.getLine(var2);
      this.sourceDataLine.open();
      this.sourceDataLine.start();
      this.bufferSize = var1;
    } catch (LineUnavailableException var5) {
      int var3 = (var1 >>> 1 & 1431655765) + (var1 & 1431655765);
      var3 = (var3 >>> 2 & 858993459) + (var3 & 858993459);
      var3 = (var3 >>> 4) + var3 & 252645135;
      var3 += var3 >>> 8;
      var3 += var3 >>> 16;
      int var4 = var3 & 255;
      if (var4 != 1) {
        this.method1090(IterableNodeTable.nextPowerOfTwo(var1));
      } else {
        this.sourceDataLine = null;
        throw var5;
      }
    }
  }

  public void method1087() {
    int var1 = 256;
    if (useTwoChannels) {
      var1 <<= 1;
    }

    for (int var2 = 0; var2 < var1; ++var2) {
      int var3 = super.samples[var2];
      if ((var3 + 8388608 & -16777216) != 0) {
        var3 = 8388607 ^ var3 >> 31;
      }

      this.buffer[var2 * 2] = (byte) (var3 >> 8);
      this.buffer[var2 * 2 + 1] = (byte) (var3 >> 16);
    }

    this.sourceDataLine.write(this.buffer, 0, var1 << 1);
  }

  public void method1089() {
    this.format = new AudioFormat((float) URLRequest.audioSampleRate, 16, useTwoChannels ? 2 : 1, true, false);
    this.buffer = new byte[256 << (useTwoChannels ? 2 : 1)];
  }
}
