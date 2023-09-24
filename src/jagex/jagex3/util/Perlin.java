package jagex.jagex3.util;

public class Perlin {

  public static int noise(int var0, int var1) {
    int var2 = blend(var0 - 1, var1 - 1) + blend(var0 + 1, var1 - 1) + blend(var0 - 1, 1 + var1) + blend(1 + var0, var1 + 1);
    int var3 = blend(var0 - 1, var1) + blend(var0 + 1, var1) + blend(var0, var1 - 1) + blend(var0, var1 + 1);
    int var4 = blend(var0, var1);
    return var2 / 16 + var3 / 8 + var4 / 4;
  }

  private static int blend(int var0, int var1) {
    int var2 = var0 + var1 * 57;
    var2 ^= var2 << 13;
    int var3 = var2 * (var2 * var2 * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE;
    return var3 >> 19 & 255;
  }
}
