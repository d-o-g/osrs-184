package jagex.statics;

import jagex.messaging.OldConnectionTaskProcessor;

public class Statics35 {

  public static final char[] cp1252AsciiExtension = new char[]{'€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ'};
  public static byte[][][] overlayOrientations;

  public static double[] method1172(double var0, double var2, int var4) {
    int var5 = var4 * 2 + 1;
    double[] var6 = new double[var5];
    int var7 = -var4;

    for (int var8 = 0; var7 <= var4; ++var8) {
      var6[var8] = OldConnectionTaskProcessor.method700(var7, var0, var2);
      ++var7;
    }

    return var6;
  }

}
