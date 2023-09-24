package jagex.jagex3.sound.vorbis;

public class RawAudioOverride extends VorbisNode {
  public byte[] samples;
  public int sampleRate;
  public boolean aBoolean502;
  public int start;
  public int end;

  public RawAudioOverride(byte[] samples, int start, int end) {
    sampleRate = 22050;
    this.samples = samples;
    this.start = start;
    this.end = end;
  }

  RawAudioOverride(int var1, byte[] var2, int var3, int var4, boolean var5) {
    sampleRate = var1;
    samples = var2;
    start = var3;
    end = var4;
    aBoolean502 = var5;
  }

  public RawAudioOverride resample(Decimator decimator) {
    samples = decimator.resample(samples);
    sampleRate = decimator.scale(sampleRate);
    if (start == end) {
      start = end = decimator.seek(start);
    } else {
      start = decimator.seek(start);
      end = decimator.seek(end);
      if (start == end) {
        --start;
      }
    }

    return this;
  }
}
