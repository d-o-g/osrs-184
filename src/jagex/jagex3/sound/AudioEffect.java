package jagex.jagex3.sound;

import jagex.jagex3.sound.vorbis.RawAudioOverride;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class AudioEffect {

  public final AudioInstrument[] instruments;

  public int start;
  public int end;

  public AudioEffect(Buffer buffer) {
    instruments = new AudioInstrument[10];

    for (int i = 0; i < 10; ++i) {
      int remaining = buffer.g1();
      if (remaining != 0) {
        --buffer.pos;
        instruments[i] = new AudioInstrument();
        instruments[i].decode(buffer);
      }
    }

    start = buffer.g2();
    end = buffer.g2();
  }

  public static AudioEffect load(ReferenceTable table, int fileId, int archiveId) {
    byte[] data = table.unpack(fileId, archiveId);
    return data == null ? null : new AudioEffect(new Buffer(data));
  }

  public final byte[] generateData() {
    int maxDuration = 0;

    for (int i = 0; i < 10; ++i) {
      if (instruments[i] != null && instruments[i].attackDelay + instruments[i].decayDelay > maxDuration) {
        maxDuration = instruments[i].attackDelay + instruments[i].decayDelay;
      }
    }

    if (maxDuration == 0) {
      return new byte[0];
    }

    int length = maxDuration * 22050 / 1000;
    byte[] data = new byte[length];
    for (int i = 0; i < 10; ++i) {
      if (instruments[i] != null) {
        int duration = instruments[i].attackDelay * 22050 / 1000;
        int offset = instruments[i].decayDelay * 22050 / 1000;
        int[] samples = instruments[i].generateSamples(duration, instruments[i].attackDelay);

        for (int j = 0; j < duration; ++j) {
          int sample = (samples[j] >> 8) + data[j + offset];
          if ((sample + 128 & -256) != 0) {
            sample = sample >> 31 ^ 127;
          }

          data[j + offset] = (byte) sample;
        }
      }
    }

    return data;
  }

  public RawAudioOverride createOverride() {
    byte[] data = generateData();
    return new RawAudioOverride(data, start * 22050 / 1000, end * 22050 / 1000);
  }

  public final int trim() {
    int minDelay = 9999999;

    for (int i = 0; i < 10; ++i) {
      if (instruments[i] != null && instruments[i].decayDelay / 20 < minDelay) {
        minDelay = instruments[i].decayDelay / 20;
      }
    }

    if (start < end && start / 20 < minDelay) {
      minDelay = start / 20;
    }

    if (minDelay != 9999999 && minDelay != 0) {
      for (int i = 0; i < 10; ++i) {
        if (instruments[i] != null) {
          AudioInstrument instrument = instruments[i];
          instrument.decayDelay -= minDelay * 20;
        }
      }

      if (start < end) {
        start -= minDelay * 20;
        end -= minDelay * 20;
      }

      return minDelay;
    }

    return 0;
  }
}
