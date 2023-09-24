package jagex.oldscape.client;

public class Vars {

  public static final int[] baseValues;
  public static final int[] values;
  public static final int[] masks;

  static {
    masks = new int[32];

    int mask = 2;
    for (int i = 0; i < 32; ++i) {
      masks[i] = mask - 1;
      mask += mask;
    }

    baseValues = new int[4000];
    values = new int[4000];
  }
}
