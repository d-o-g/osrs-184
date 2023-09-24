package jagex.jagex3.util;

public class Skills {

  public static final boolean[] ENABLED = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false};
  public static final int[] EXP_TABLE;
  public static final int[] experiences = new int[25];
  public static final int[] levels = new int[25];
  public static final int[] currentLevels = new int[25];

  static {
    EXP_TABLE = new int[99];

    int total = 0;
    for (int i = 0; i < 99; ++i) {
      int level = i + 1;
      int xp = (int) ((double) level + 300.0D * Math.pow(2.0D, (double) level / 7.0D));
      total += xp;
      EXP_TABLE[i] = total / 4;
    }
  }
}
