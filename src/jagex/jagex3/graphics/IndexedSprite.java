package jagex.jagex3.graphics;

import jagex.jagex3.js5.ReferenceTable;
import jagex.statics.Statics38;

public final class IndexedSprite extends JagGraphics {

  public byte[] indices;

  public int[] palette;

  public int insetX;
  public int insetY;
  public int anInt378;
  public int anInt375;
  public int anInt377;
  public int anInt372;

  static void method1323(int[] var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
    int var9 = -(var5 >> 2);
    var5 = -(var5 & 3);

    for (int var10 = -var6; var10 < 0; ++var10) {
      int var11;
      byte var12;
      for (var11 = var9; var11 < 0; ++var11) {
        var12 = var1[var3++];
        if (var12 != 0) {
          var0[var4++] = var2[var12 & 255];
        } else {
          ++var4;
        }

        var12 = var1[var3++];
        if (var12 != 0) {
          var0[var4++] = var2[var12 & 255];
        } else {
          ++var4;
        }

        var12 = var1[var3++];
        if (var12 != 0) {
          var0[var4++] = var2[var12 & 255];
        } else {
          ++var4;
        }

        var12 = var1[var3++];
        if (var12 != 0) {
          var0[var4++] = var2[var12 & 255];
        } else {
          ++var4;
        }
      }

      for (var11 = var5; var11 < 0; ++var11) {
        var12 = var1[var3++];
        if (var12 != 0) {
          var0[var4++] = var2[var12 & 255];
        } else {
          ++var4;
        }
      }

      var4 += var7;
      var3 += var8;
    }

  }

  static void method1321(int[] var0, byte[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
    int var12 = var3;

    for (int var13 = -var8; var13 < 0; ++var13) {
      int var14 = var11 * (var4 >> 16);

      for (int var15 = -var7; var15 < 0; ++var15) {
        byte var16 = var1[(var3 >> 16) + var14];
        if (var16 != 0) {
          var0[var5++] = var2[var16 & 255];
        } else {
          ++var5;
        }

        var3 += var9;
      }

      var4 += var10;
      var3 = var12;
      var5 += var6;
    }

  }

  public static IndexedSprite[] method474(ReferenceTable var0, String var1, String var2) {
    int var3 = var0.getGroup(var1);
    int var4 = var0.getFile(var3, var2);
    return Statics38.method1194(var0, var3, var4);
  }

  public void method499() {
    if (this.anInt378 != this.anInt375 || this.anInt377 != this.anInt372) {
      byte[] var1 = new byte[this.anInt375 * this.anInt372];
      int var2 = 0;

      for (int var3 = 0; var3 < this.anInt377; ++var3) {
        for (int var4 = 0; var4 < this.anInt378; ++var4) {
          var1[var4 + (var3 + this.insetY) * this.anInt375 + this.insetX] = this.indices[var2++];
        }
      }

      this.indices = var1;
      this.anInt378 = this.anInt375;
      this.anInt377 = this.anInt372;
      this.insetX = 0;
      this.insetY = 0;
    }
  }

  public void renderAt(int var1, int var2) {
    var1 += this.insetX;
    var2 += this.insetY;
    int var3 = var1 + var2 * drawingAreaWidth;
    int var4 = 0;
    int var5 = this.anInt377;
    int var6 = this.anInt378;
    int var7 = drawingAreaWidth - var6;
    int var8 = 0;
    int var9;
    if (var2 < drawingAreaTop) {
      var9 = drawingAreaTop - var2;
      var5 -= var9;
      var2 = drawingAreaTop;
      var4 += var9 * var6;
      var3 += var9 * drawingAreaWidth;
    }

    if (var5 + var2 > drawingAreaRight) {
      var5 -= var5 + var2 - drawingAreaRight;
    }

    if (var1 < drawingAreaLeft) {
      var9 = drawingAreaLeft - var1;
      var6 -= var9;
      var1 = drawingAreaLeft;
      var4 += var9;
      var3 += var9;
      var8 += var9;
      var7 += var9;
    }

    if (var6 + var1 > drawingAreaBottom) {
      var9 = var6 + var1 - drawingAreaBottom;
      var6 -= var9;
      var8 += var9;
      var7 += var9;
    }

    if (var6 > 0 && var5 > 0) {
      method1323(drawingAreaPixels, this.indices, this.palette, var4, var3, var6, var5, var7, var8);
    }
  }

  public void method1322(int var1, int var2, int var3, int var4) {
    int var5 = this.anInt378;
    int var6 = this.anInt377;
    int var7 = 0;
    int var8 = 0;
    int var9 = this.anInt375;
    int var10 = this.anInt372;
    int var11 = (var9 << 16) / var3;
    int var12 = (var10 << 16) / var4;
    int var13;
    if (this.insetX > 0) {
      var13 = (var11 + (this.insetX << 16) - 1) / var11;
      var1 += var13;
      var7 += var13 * var11 - (this.insetX << 16);
    }

    if (this.insetY > 0) {
      var13 = (var12 + (this.insetY << 16) - 1) / var12;
      var2 += var13;
      var8 += var13 * var12 - (this.insetY << 16);
    }

    if (var5 < var9) {
      var3 = (var11 + ((var5 << 16) - var7) - 1) / var11;
    }

    if (var6 < var10) {
      var4 = (var12 + ((var6 << 16) - var8) - 1) / var12;
    }

    var13 = var1 + var2 * drawingAreaWidth;
    int var14 = drawingAreaWidth - var3;
    if (var2 + var4 > drawingAreaRight) {
      var4 -= var2 + var4 - drawingAreaRight;
    }

    int var15;
    if (var2 < drawingAreaTop) {
      var15 = drawingAreaTop - var2;
      var4 -= var15;
      var13 += var15 * drawingAreaWidth;
      var8 += var12 * var15;
    }

    if (var3 + var1 > drawingAreaBottom) {
      var15 = var3 + var1 - drawingAreaBottom;
      var3 -= var15;
      var14 += var15;
    }

    if (var1 < drawingAreaLeft) {
      var15 = drawingAreaLeft - var1;
      var3 -= var15;
      var13 += var15;
      var7 += var11 * var15;
      var14 += var15;
    }

    method1321(drawingAreaPixels, this.indices, this.palette, var7, var8, var13, var14, var3, var4, var11, var12, var5);
  }

  public void method1325(int var1, int var2, int var3) {
    for (int var4 = 0; var4 < this.palette.length; ++var4) {
      int var5 = this.palette[var4] >> 16 & 255;
      var5 += var1;
      if (var5 < 0) {
        var5 = 0;
      } else if (var5 > 255) {
        var5 = 255;
      }

      int var6 = this.palette[var4] >> 8 & 255;
      var6 += var2;
      if (var6 < 0) {
        var6 = 0;
      } else if (var6 > 255) {
        var6 = 255;
      }

      int var7 = this.palette[var4] & 255;
      var7 += var3;
      if (var7 < 0) {
        var7 = 0;
      } else if (var7 > 255) {
        var7 = 255;
      }

      this.palette[var4] = var7 + (var6 << 8) + (var5 << 16);
    }

  }
}
