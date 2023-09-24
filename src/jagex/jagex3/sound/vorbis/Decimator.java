package jagex.jagex3.sound.vorbis;

public class Decimator {
  int[][] table;
  int outputRate;
  int inputRate;

  public Decimator(int var1, int var2) {
    if (var2 != var1) {
      int var3 = var1;
      int var4 = var2;
      if (var2 > var1) {
        var3 = var2;
        var4 = var1;
      }

      while (var4 != 0) {
        int var5 = var3 % var4;
        var3 = var4;
        var4 = var5;
      }

      var1 /= var3;
      var2 /= var3;
      inputRate = var1;
      outputRate = var2;
      table = new int[var1][14];

      for (int var7 = 0; var7 < var1; ++var7) {
        int[] var8 = table[var7];
        double var9 = (double) var7 / (double) var1 + 6.0D;
        int var11 = (int) Math.floor(var9 - 7.0D + 1.0D);
        if (var11 < 0) {
          var11 = 0;
        }

        int var12 = (int) Math.ceil(var9 + 7.0D);
        if (var12 > 14) {
          var12 = 14;
        }

        for (double var13 = (double) var2 / (double) var1; var11 < var12; ++var11) {
          double var15 = 3.141592653589793D * ((double) var11 - var9);
          double var17 = var13;
          if (var15 < -1.0E-4D || var15 > 1.0E-4D) {
            var17 = var13 * (Math.sin(var15) / var15);
          }

          var17 *= 0.54D + 0.46D * Math.cos(((double) var11 - var9) * 0.2243994752564138D);
          var8[var11] = (int) Math.floor(65536.0D * var17 + 0.5D);
        }
      }

    }
  }

  byte[] resample(byte[] var1) {
    if (table != null) {
      int var2 = (int) ((long) var1.length * (long) outputRate / (long) inputRate) + 14;
      int[] var3 = new int[var2];
      int var4 = 0;
      int var5 = 0;

      int var6;
      for (var6 = 0; var6 < var1.length; ++var6) {
        byte var7 = var1[var6];
        int[] var8 = table[var5];

        int var9;
        for (var9 = 0; var9 < 14; ++var9) {
          var3[var4 + var9] += var7 * var8[var9];
        }

        var5 += outputRate;
        var9 = var5 / inputRate;
        var4 += var9;
        var5 -= var9 * inputRate;
      }

      var1 = new byte[var2];

      for (var6 = 0; var6 < var2; ++var6) {
        int var10 = var3[var6] + 32768 >> 16;
        if (var10 < -128) {
          var1[var6] = -128;
        } else if (var10 > 127) {
          var1[var6] = 127;
        } else {
          var1[var6] = (byte) var10;
        }
      }
    }

    return var1;
  }

  int scale(int var1) {
    if (table != null) {
      var1 = (int) ((long) var1 * (long) outputRate / (long) inputRate);
    }

    return var1;
  }

  int seek(int var1) {
    if (table != null) {
      var1 = (int) ((long) var1 * (long) outputRate / (long) inputRate) + 6;
    }

    return var1;
  }
}
