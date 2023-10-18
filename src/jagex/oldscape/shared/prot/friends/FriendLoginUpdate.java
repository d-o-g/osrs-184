package jagex.oldscape.shared.prot.friends;

import jagex.jagex3.sound.AudioManager;
import jagex.datastructure.Linkable;
import jagex.core.time.Clock;
import jagex.oldscape.client.social.NamePair;

public class FriendLoginUpdate extends Linkable {

  public static AudioManager aAudioManager_668;
  public final int time;
  public final short world;
  public final NamePair namePair;

  public FriendLoginUpdate(NamePair namePair, int world) {
    this.time = (int) (Clock.now() / 1000L);
    this.namePair = namePair;
    this.world = (short) world;
  }
}
