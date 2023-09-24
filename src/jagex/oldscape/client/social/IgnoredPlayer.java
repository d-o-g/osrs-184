package jagex.oldscape.client.social;

public class IgnoredPlayer extends Chatter {

  public int anInt1147;

  IgnoredPlayer() {

  }

  int method842(IgnoredPlayer var1) {
    return this.anInt1147 - var1.anInt1147;
  }

  public int compare0(Chatter var1) {
    return this.method842((IgnoredPlayer) var1);
  }

  public int compareTo(Object var1) {
    return this.method842((IgnoredPlayer) var1);
  }
}
