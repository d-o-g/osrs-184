package jagex.statics;

public class Statics39 {
  static final int[] SIN;
  static final int[] COS;

  static {
    SIN = new int[2048];
    COS = new int[2048];
    double var0 = 0.0030679615757712823D;

    for (int var2 = 0; var2 < 2048; ++var2) {
      SIN[var2] = (int) (65536.0D * Math.sin(var0 * (double) var2));
      COS[var2] = (int) (65536.0D * Math.cos((double) var2 * var0));
    }

  }
}
