package jagex.jagex3.sound;

import jagex.datastructure.Node;

public class AudioBuffer extends Node {

  public final byte[] payload;

  public AudioBuffer(byte[] payload) {
    this.payload = payload;
  }
}
