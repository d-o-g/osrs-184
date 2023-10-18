package jagex.jagex3.sound;

import jagex.jagex3.sound.vorbis.RawAudioOverride;
import jagex.jagex3.sound.vorbis.VorbisSample;
import jagex.datastructure.instrusive.hashtable.NodeTable;
import jagex.jagex3.js5.ReferenceTable;

public class AudioManager {

  final ReferenceTable audioEffectTable;
  final ReferenceTable vorbisSampleTable;
  final NodeTable<RawAudioOverride> rawAudioOverries;
  final NodeTable<VorbisSample> vorbisSamples;

  public AudioManager(ReferenceTable vorbisSampleTable, ReferenceTable audioEffectTable) {
    this.vorbisSamples = new NodeTable<>(256);
    this.rawAudioOverries = new NodeTable<>(256);
    this.vorbisSampleTable = vorbisSampleTable;
    this.audioEffectTable = audioEffectTable;
  }

  RawAudioOverride getRawAudioOverride(int var1, int var2, int[] var3) {
    int hash = var2 ^ (var1 << 4 & 65535 | var1 >>> 12);
    hash |= var1 << 16;
    long key = (long) hash ^ 4294967296L;
    RawAudioOverride override = rawAudioOverries.lookup(key);
    if (override != null) {
      return override;
    }

    if (var3 != null && var3[0] <= 0) {
      return null;
    }

    VorbisSample sample = vorbisSamples.lookup(key);
    if (sample == null) {
      sample = VorbisSample.load(audioEffectTable, var1, var2);
      if (sample == null) {
        return null;
      }

      vorbisSamples.put(sample, key);
    }

    override = sample.defineAudioOverride(var3);
    if (override == null) {
      return null;
    }

    sample.unlink();
    rawAudioOverries.put(override, key);
    return override;
  }

  RawAudioOverride getAudioEffectOverride(int var1, int var2, int[] var3) {
    int hash = var2 ^ (var1 << 4 & 65535 | var1 >>> 12);
    hash |= var1 << 16;
    long key = hash;
    RawAudioOverride override = rawAudioOverries.lookup(key);
    if (override != null) {
      return override;
    }

    if (var3 != null && var3[0] <= 0) {
      return null;
    }

    AudioEffect effect = AudioEffect.load(vorbisSampleTable, var1, var2);
    if (effect == null) {
      return null;
    }

    override = effect.createOverride();
    rawAudioOverries.put(override, key);
    if (var3 != null) {
      var3[0] -= override.samples.length;
    }

    return override;
  }

  public RawAudioOverride getRawAudioOverride(int var1, int[] var2) {
    if (audioEffectTable.childrenCount() == 1) {
      return getRawAudioOverride(0, var1, var2);
    }

    if (audioEffectTable.getFileCount(var1) == 1) {
      return getRawAudioOverride(var1, 0, var2);
    }
    throw new RuntimeException();
  }

  public RawAudioOverride getAudioEffectOverride(int var1, int[] var2) {
    if (vorbisSampleTable.childrenCount() == 1) {
      return getAudioEffectOverride(0, var1, var2);
    }

    if (vorbisSampleTable.getFileCount(var1) == 1) {
      return getAudioEffectOverride(var1, 0, var2);
    }
    throw new RuntimeException();
  }
}
