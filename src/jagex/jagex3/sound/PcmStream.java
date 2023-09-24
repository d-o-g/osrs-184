package jagex.jagex3.sound;

import jagex.jagex3.sound.vorbis.VorbisNode;
import jagex.datastructure.Node;

public abstract class PcmStream extends Node {
  public volatile boolean aBoolean665;
  public VorbisNode aVorbisNode_667;
  public PcmStream aPcmStream_664;
  public int anInt666;

  protected PcmStream() {
    this.aBoolean665 = true;
  }

  public abstract void method311(int[] var1, int var2, int var3);

  public abstract void method303(int var1);

  final void method482(int[] var1, int var2, int var3) {
    if (this.aBoolean665) {
      this.method311(var1, var2, var3);
    } else {
      this.method303(var3);
    }

  }

  public abstract PcmStream method308();

  public abstract PcmStream method307();

  public int method483() {
    return 255;
  }

  public abstract int method305();
}
