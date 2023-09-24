package jagex.oldscape.script;

public class ClientScriptFrame {

  public int instructionIndex;
  public ClientScript target;
  public int[] intLocals;
  public String[] stringLocals;

  public ClientScriptFrame() {
    this.instructionIndex = -1;
  }

  public static int method295(double var0, double var2, double var4) {
    double var6 = var4;
    double var8 = var4;
    double var10 = var4;
    if (var2 != 0.0D) {
      double var12;
      if (var4 < 0.5D) {
        var12 = (1.0D + var2) * var4;
      } else {
        var12 = var2 + var4 - var4 * var2;
      }

      double var14 = var4 * 2.0D - var12;
      double var16 = 0.3333333333333333D + var0;
      if (var16 > 1.0D) {
        --var16;
      }

      double var20 = var0 - 0.3333333333333333D;
      if (var20 < 0.0D) {
        ++var20;
      }

      if (var16 * 6.0D < 1.0D) {
        var6 = var16 * (var12 - var14) * 6.0D + var14;
      } else if (var16 * 2.0D < 1.0D) {
        var6 = var12;
      } else if (3.0D * var16 < 2.0D) {
        var6 = var14 + 6.0D * (var12 - var14) * (0.6666666666666666D - var16);
      } else {
        var6 = var14;
      }

      if (6.0D * var0 < 1.0D) {
        var8 = var0 * (var12 - var14) * 6.0D + var14;
      } else if (2.0D * var0 < 1.0D) {
        var8 = var12;
      } else if (3.0D * var0 < 2.0D) {
        var8 = var14 + 6.0D * (var12 - var14) * (0.6666666666666666D - var0);
      } else {
        var8 = var14;
      }

      if (6.0D * var20 < 1.0D) {
        var10 = var14 + (var12 - var14) * 6.0D * var20;
      } else if (var20 * 2.0D < 1.0D) {
        var10 = var12;
      } else if (3.0D * var20 < 2.0D) {
        var10 = var14 + 6.0D * (0.6666666666666666D - var20) * (var12 - var14);
      } else {
        var10 = var14;
      }
    }

    int var22 = (int) (var6 * 256.0D);
    int var23 = (int) (256.0D * var8);
    int var24 = (int) (256.0D * var10);
    return var24 + (var23 << 8) + (var22 << 16);
  }

}
