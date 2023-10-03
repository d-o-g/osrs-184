package jagex.statics;

import jagex.jagex3.sound.AudioSystem;
import jagex.jagex3.sound.AudioTrack;
import jagex.jagex3.js5.ReferenceTable;

public class Statics57 {
  public static AudioTrack anAudioTrack1158;
  public static ReferenceTable aReferenceTable1159;
  public static ReferenceTable aReferenceTable1160;
  public static String cookie;
  public static int[] mapFileIds;

  static {
    AudioSystem.state = 0;
  }

  public static void method533() {
    Statics50.aClass5_Sub6_Sub3_326.method756();
    AudioSystem.state = 1;
    AudioSystem.tracks = null;
  }
}
