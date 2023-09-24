package jagex.jagex3.graphics;

import jagex.datastructure.DoublyLinkedNode;

public class JagGraphics extends DoublyLinkedNode {

  public static int[] drawingAreaPixels;

  public static int drawingAreaWidth;
  public static int drawingAreaHeight;

  public static int drawingAreaTop = 0;
  public static int drawingAreaLeft = 0;
  public static int drawingAreaRight = 0;
  public static int drawingAreaBottom = 0;

  protected JagGraphics() {
  }

  public static void method1366(int[] var0) {
    var0[0] = drawingAreaLeft;
    var0[1] = drawingAreaTop;
    var0[2] = drawingAreaBottom;
    var0[3] = drawingAreaRight;
  }

  public static void setTarget(int[] var0, int var1, int var2) {
    drawingAreaPixels = var0;
    drawingAreaWidth = var1;
    drawingAreaHeight = var2;
    setClip(0, 0, var1, var2);
  }

  public static void drawVerticalLine(int var0, int var1, int var2, int var3) {
    if (var0 >= drawingAreaLeft && var0 < drawingAreaBottom) {
      if (var1 < drawingAreaTop) {
        var2 -= drawingAreaTop - var1;
        var1 = drawingAreaTop;
      }

      if (var2 + var1 > drawingAreaRight) {
        var2 = drawingAreaRight - var1;
      }

      int var4 = var0 + drawingAreaWidth * var1;

      for (int var5 = 0; var5 < var2; ++var5) {
        drawingAreaPixels[var4 + var5 * drawingAreaWidth] = var3;
      }

    }
  }

  public static void fillRect(int var0, int var1, int var2, int var3, int var4) {
    if (var0 < drawingAreaLeft) {
      var2 -= drawingAreaLeft - var0;
      var0 = drawingAreaLeft;
    }

    if (var1 < drawingAreaTop) {
      var3 -= drawingAreaTop - var1;
      var1 = drawingAreaTop;
    }

    if (var0 + var2 > drawingAreaBottom) {
      var2 = drawingAreaBottom - var0;
    }

    if (var3 + var1 > drawingAreaRight) {
      var3 = drawingAreaRight - var1;
    }

    int var5 = drawingAreaWidth - var2;
    int var6 = var0 + drawingAreaWidth * var1;

    for (int var7 = -var3; var7 < 0; ++var7) {
      for (int var8 = -var2; var8 < 0; ++var8) {
        drawingAreaPixels[var6++] = var4;
      }

      var6 += var5;
    }

  }

  public static void drawHorizontalLine(int var0, int var1, int var2, int var3) {
    if (var1 >= drawingAreaTop && var1 < drawingAreaRight) {
      if (var0 < drawingAreaLeft) {
        var2 -= drawingAreaLeft - var0;
        var0 = drawingAreaLeft;
      }

      if (var0 + var2 > drawingAreaBottom) {
        var2 = drawingAreaBottom - var0;
      }

      int var4 = var0 + drawingAreaWidth * var1;

      for (int var5 = 0; var5 < var2; ++var5) {
        drawingAreaPixels[var4 + var5] = var3;
      }

    }
  }

  public static void setClip(int var0, int var1, int var2, int var3) {
    if (var0 < 0) {
      var0 = 0;
    }

    if (var1 < 0) {
      var1 = 0;
    }

    if (var2 > drawingAreaWidth) {
      var2 = drawingAreaWidth;
    }

    if (var3 > drawingAreaHeight) {
      var3 = drawingAreaHeight;
    }

    drawingAreaLeft = var0;
    drawingAreaTop = var1;
    drawingAreaBottom = var2;
    drawingAreaRight = var3;
  }

  public static void method1367(int var0, int var1, int var2, int var3, int var4) {
    if (var4 != 0) {
      if (var4 == 256) {
        method1357(var0, var1, var2, var3);
      } else {
        if (var2 < 0) {
          var2 = -var2;
        }

        int var5 = 256 - var4;
        int var6 = (var3 >> 16 & 255) * var4;
        int var7 = (var3 >> 8 & 255) * var4;
        int var8 = var4 * (var3 & 255);
        int var9 = var1 - var2;
        if (var9 < drawingAreaTop) {
          var9 = drawingAreaTop;
        }

        int var10 = var2 + var1 + 1;
        if (var10 > drawingAreaRight) {
          var10 = drawingAreaRight;
        }

        int var11 = var9;
        int var12 = var2 * var2;
        int var13 = 0;
        int var14 = var1 - var9;
        int var15 = var14 * var14;
        int var16 = var15 - var14;
        if (var1 > var10) {
          var1 = var10;
        }

        int var17;
        int var18;
        int var19;
        int var20;
        int var21;
        int var22;
        int var23;
        int var24;
        while (var11 < var1) {
          while (var16 <= var12 || var15 <= var12) {
            var15 = var15 + var13 + var13;
            var16 += var13++ + var13;
          }

          var17 = var0 - var13 + 1;
          if (var17 < drawingAreaLeft) {
            var17 = drawingAreaLeft;
          }

          var18 = var0 + var13;
          if (var18 > drawingAreaBottom) {
            var18 = drawingAreaBottom;
          }

          var19 = var17 + var11 * drawingAreaWidth;

          for (var20 = var17; var20 < var18; ++var20) {
            var21 = var5 * (drawingAreaPixels[var19] >> 16 & 255);
            var22 = (drawingAreaPixels[var19] >> 8 & 255) * var5;
            var23 = var5 * (drawingAreaPixels[var19] & 255);
            var24 = (var8 + var23 >> 8) + (var6 + var21 >> 8 << 16) + (var7 + var22 >> 8 << 8);
            drawingAreaPixels[var19++] = var24;
          }

          ++var11;
          var15 -= var14-- + var14;
          var16 -= var14 + var14;
        }

        var13 = var2;
        var14 = -var14;
        var16 = var12 + var14 * var14;
        var15 = var16 - var2;

        for (var16 -= var14; var11 < var10; var15 += var14++ + var14) {
          while (var16 > var12 && var15 > var12) {
            var16 -= var13-- + var13;
            var15 -= var13 + var13;
          }

          var17 = var0 - var13;
          if (var17 < drawingAreaLeft) {
            var17 = drawingAreaLeft;
          }

          var18 = var0 + var13;
          if (var18 > drawingAreaBottom - 1) {
            var18 = drawingAreaBottom - 1;
          }

          var19 = var17 + var11 * drawingAreaWidth;

          for (var20 = var17; var20 <= var18; ++var20) {
            var21 = var5 * (drawingAreaPixels[var19] >> 16 & 255);
            var22 = (drawingAreaPixels[var19] >> 8 & 255) * var5;
            var23 = var5 * (drawingAreaPixels[var19] & 255);
            var24 = (var8 + var23 >> 8) + (var6 + var21 >> 8 << 16) + (var7 + var22 >> 8 << 8);
            drawingAreaPixels[var19++] = var24;
          }

          ++var11;
          var16 = var16 + var14 + var14;
        }

      }
    }
  }

  public static void method1361(int var0, int var1, int var2, int var3, int var4, int var5, byte[] var6, int var7) {
    if (var0 + var2 >= 0 && var3 + var1 >= 0) {
      if (var0 < drawingAreaWidth && var1 < drawingAreaHeight) {
        int var8 = 0;
        int var9 = 0;
        if (var0 < 0) {
          var8 -= var0;
          var2 += var0;
        }

        if (var1 < 0) {
          var9 -= var1;
          var3 += var1;
        }

        if (var0 + var2 > drawingAreaWidth) {
          var2 = drawingAreaWidth - var0;
        }

        if (var3 + var1 > drawingAreaHeight) {
          var3 = drawingAreaHeight - var1;
        }

        int var10 = var6.length / var7;
        int var11 = drawingAreaWidth - var2;
        int var12 = var4 >>> 24;
        int var13 = var5 >>> 24;
        int var14;
        int var15;
        int var16;
        int var17;
        int var18;
        if (var12 == 255 && var13 == 255) {
          var14 = var0 + var8 + (var9 + var1) * drawingAreaWidth;

          for (var15 = var9 + var1; var15 < var3 + var9 + var1; ++var15) {
            for (var16 = var0 + var8; var16 < var0 + var8 + var2; ++var16) {
              var17 = (var15 - var1) % var10;
              var18 = (var16 - var0) % var7;
              if (var6[var18 + var17 * var7] != 0) {
                drawingAreaPixels[var14++] = var5;
              } else {
                drawingAreaPixels[var14++] = var4;
              }
            }

            var14 += var11;
          }
        } else {
          var14 = var0 + var8 + (var9 + var1) * drawingAreaWidth;

          for (var15 = var9 + var1; var15 < var3 + var9 + var1; ++var15) {
            for (var16 = var0 + var8; var16 < var0 + var8 + var2; ++var16) {
              var17 = (var15 - var1) % var10;
              var18 = (var16 - var0) % var7;
              int var19 = var4;
              if (var6[var18 + var17 * var7] != 0) {
                var19 = var5;
              }

              int var20 = var19 >>> 24;
              int var21 = 255 - var20;
              int var22 = drawingAreaPixels[var14];
              int var23 = ((var19 & 16711935) * var20 + (var22 & 16711935) * var21 & -16711936) + (var20 * (var19 & 65280) + var21 * (var22 & 65280) & 16711680) >> 8;
              drawingAreaPixels[var14++] = var23;
            }

            var14 += var11;
          }
        }

      }
    }
  }

  static void method1357(int var0, int var1, int var2, int var3) {
    if (var2 == 0) {
      method1375(var0, var1, var3);
    } else {
      if (var2 < 0) {
        var2 = -var2;
      }

      int var4 = var1 - var2;
      if (var4 < drawingAreaTop) {
        var4 = drawingAreaTop;
      }

      int var5 = var2 + var1 + 1;
      if (var5 > drawingAreaRight) {
        var5 = drawingAreaRight;
      }

      int var6 = var4;
      int var7 = var2 * var2;
      int var8 = 0;
      int var9 = var1 - var4;
      int var10 = var9 * var9;
      int var11 = var10 - var9;
      if (var1 > var5) {
        var1 = var5;
      }

      int var12;
      int var13;
      int var14;
      int var15;
      while (var6 < var1) {
        while (var11 <= var7 || var10 <= var7) {
          var10 = var10 + var8 + var8;
          var11 += var8++ + var8;
        }

        var12 = var0 - var8 + 1;
        if (var12 < drawingAreaLeft) {
          var12 = drawingAreaLeft;
        }

        var13 = var0 + var8;
        if (var13 > drawingAreaBottom) {
          var13 = drawingAreaBottom;
        }

        var14 = var12 + var6 * drawingAreaWidth;

        for (var15 = var12; var15 < var13; ++var15) {
          drawingAreaPixels[var14++] = var3;
        }

        ++var6;
        var10 -= var9-- + var9;
        var11 -= var9 + var9;
      }

      var8 = var2;
      var9 = var6 - var1;
      var11 = var7 + var9 * var9;
      var10 = var11 - var2;

      for (var11 -= var9; var6 < var5; var10 += var9++ + var9) {
        while (var11 > var7 && var10 > var7) {
          var11 -= var8-- + var8;
          var10 -= var8 + var8;
        }

        var12 = var0 - var8;
        if (var12 < drawingAreaLeft) {
          var12 = drawingAreaLeft;
        }

        var13 = var0 + var8;
        if (var13 > drawingAreaBottom - 1) {
          var13 = drawingAreaBottom - 1;
        }

        var14 = var12 + var6 * drawingAreaWidth;

        for (var15 = var12; var15 <= var13; ++var15) {
          drawingAreaPixels[var14++] = var3;
        }

        ++var6;
        var11 = var11 + var9 + var9;
      }

    }
  }

  public static void resetDrawingArea() {
    drawingAreaLeft = 0;
    drawingAreaTop = 0;
    drawingAreaBottom = drawingAreaWidth;
    drawingAreaRight = drawingAreaHeight;
  }

  public static void method1370(int var0, int var1, int var2, int var3, int var4, int var5) {
    if (var0 < drawingAreaLeft) {
      var2 -= drawingAreaLeft - var0;
      var0 = drawingAreaLeft;
    }

    if (var1 < drawingAreaTop) {
      var3 -= drawingAreaTop - var1;
      var1 = drawingAreaTop;
    }

    if (var0 + var2 > drawingAreaBottom) {
      var2 = drawingAreaBottom - var0;
    }

    if (var3 + var1 > drawingAreaRight) {
      var3 = drawingAreaRight - var1;
    }

    var4 = (var5 * (var4 & 16711935) >> 8 & 16711935) + (var5 * (var4 & 65280) >> 8 & 65280);
    int var6 = 256 - var5;
    int var7 = drawingAreaWidth - var2;
    int var8 = var0 + drawingAreaWidth * var1;

    for (int var9 = 0; var9 < var3; ++var9) {
      for (int var10 = -var2; var10 < 0; ++var10) {
        int var11 = drawingAreaPixels[var8];
        var11 = ((var11 & 16711935) * var6 >> 8 & 16711935) + (var6 * (var11 & 65280) >> 8 & 65280);
        drawingAreaPixels[var8++] = var11 + var4;
      }

      var8 += var7;
    }

  }

  public static void method1372(int var0, int var1, int var2, int var3, int var4) {
    drawHorizontalLine(var0, var1, var2, var4);
    drawHorizontalLine(var0, var3 + var1 - 1, var2, var4);
    drawVerticalLine(var0, var1, var3, var4);
    drawVerticalLine(var0 + var2 - 1, var1, var3, var4);
  }

  static void method1375(int var0, int var1, int var2) {
    if (var0 >= drawingAreaLeft && var1 >= drawingAreaTop && var0 < drawingAreaBottom && var1 < drawingAreaRight) {
      drawingAreaPixels[var0 + drawingAreaWidth * var1] = var2;
    }
  }

  public static void clear() {
    int var0 = 0;

    int var1;
    for (var1 = drawingAreaWidth * drawingAreaHeight - 7; var0 < var1; drawingAreaPixels[var0++] = 0) {
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
      drawingAreaPixels[var0++] = 0;
    }

    for (var1 += 7; var0 < var1; drawingAreaPixels[var0++] = 0) {
    }

  }

  public static void method1373(int[] var0) {
    drawingAreaLeft = var0[0];
    drawingAreaTop = var0[1];
    drawingAreaBottom = var0[2];
    drawingAreaRight = var0[3];
  }

  public static void method1376(int var0, int var1, int var2, int var3, int var4, int var5) {
    if (var2 > 0 && var3 > 0) {
      int var6 = 0;
      int var7 = 65536 / var3;
      if (var0 < drawingAreaLeft) {
        var2 -= drawingAreaLeft - var0;
        var0 = drawingAreaLeft;
      }

      if (var1 < drawingAreaTop) {
        var6 += (drawingAreaTop - var1) * var7;
        var3 -= drawingAreaTop - var1;
        var1 = drawingAreaTop;
      }

      if (var0 + var2 > drawingAreaBottom) {
        var2 = drawingAreaBottom - var0;
      }

      if (var3 + var1 > drawingAreaRight) {
        var3 = drawingAreaRight - var1;
      }

      int var8 = drawingAreaWidth - var2;
      int var9 = var0 + drawingAreaWidth * var1;

      for (int var10 = -var3; var10 < 0; ++var10) {
        int var11 = 65536 - var6 >> 8;
        int var12 = var6 >> 8;
        int var13 = (var12 * (var5 & 16711935) + var11 * (var4 & 16711935) & -16711936) + (var12 * (var5 & 65280) + var11 * (var4 & 65280) & 16711680) >>> 8;

        for (int var14 = -var2; var14 < 0; ++var14) {
          drawingAreaPixels[var9++] = var13;
        }

        var9 += var8;
        var6 += var7;
      }

    }
  }

  public static void method1362(int var0, int var1, int var2, int[] var3, int[] var4) {
    int var5 = var0 + drawingAreaWidth * var1;

    for (var1 = 0; var1 < var3.length; ++var1) {
      int var6 = var5 + var3[var1];

      for (var0 = -var4[var1]; var0 < 0; ++var0) {
        drawingAreaPixels[var6++] = var2;
      }

      var5 += drawingAreaWidth;
    }

  }

  public static void drawRect(int var0, int var1, int var2, int var3, int var4, int var5) {
    drawHorizontalLine(var0, var1, var2, var4, var5);
    drawHorizontalLine(var0, var3 + var1 - 1, var2, var4, var5);
    if (var3 >= 3) {
      drawVerticalLine(var0, var1 + 1, var3 - 2, var4, var5);
      drawVerticalLine(var0 + var2 - 1, var1 + 1, var3 - 2, var4, var5);
    }

  }

  public static void drawDiagonalLine(int x, int y, int width, int height, int rgb) {
    width -= x;
    height -= y;
    if (height == 0) {
      if (width >= 0) {
        drawHorizontalLine(x, y, width + 1, rgb);
      } else {
        drawHorizontalLine(x + width, y, -width + 1, rgb);
      }

    } else if (width == 0) {
      if (height >= 0) {
        drawVerticalLine(x, y, height + 1, rgb);
      } else {
        drawVerticalLine(x, height + y, -height + 1, rgb);
      }

    } else {
      if (height + width < 0) {
        x += width;
        width = -width;
        y += height;
        height = -height;
      }

      int yStep;
      int var6;
      if (width > height) {
        y <<= 16;
        y += 32768;
        height <<= 16;
        yStep = (int) Math.floor((double) height / (double) width + 0.5D);
        width += x;
        if (x < drawingAreaLeft) {
          y += yStep * (drawingAreaLeft - x);
          x = drawingAreaLeft;
        }

        if (width >= drawingAreaBottom) {
          width = drawingAreaBottom - 1;
        }

        while (x <= width) {
          var6 = y >> 16;
          if (var6 >= drawingAreaTop && var6 < drawingAreaRight) {
            drawingAreaPixels[x + var6 * drawingAreaWidth] = rgb;
          }

          y += yStep;
          ++x;
        }
      } else {
        x <<= 16;
        x += 32768;
        width <<= 16;
        yStep = (int) Math.floor((double) width / (double) height + 0.5D);
        height += y;
        if (y < drawingAreaTop) {
          x += (drawingAreaTop - y) * yStep;
          y = drawingAreaTop;
        }

        if (height >= drawingAreaRight) {
          height = drawingAreaRight - 1;
        }

        while (y <= height) {
          var6 = x >> 16;
          if (var6 >= drawingAreaLeft && var6 < drawingAreaBottom) {
            drawingAreaPixels[var6 + drawingAreaWidth * y] = rgb;
          }

          x += yStep;
          ++y;
        }
      }

    }
  }

  public static void method1359(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
    if (var2 > 0 && var3 > 0) {
      int var8 = 0;
      int var9 = 65536 / var3;
      if (var0 < drawingAreaLeft) {
        var2 -= drawingAreaLeft - var0;
        var0 = drawingAreaLeft;
      }

      if (var1 < drawingAreaTop) {
        var8 += (drawingAreaTop - var1) * var9;
        var3 -= drawingAreaTop - var1;
        var1 = drawingAreaTop;
      }

      if (var0 + var2 > drawingAreaBottom) {
        var2 = drawingAreaBottom - var0;
      }

      if (var3 + var1 > drawingAreaRight) {
        var3 = drawingAreaRight - var1;
      }

      int var10 = drawingAreaWidth - var2;
      int var11 = var0 + drawingAreaWidth * var1;

      for (int var12 = -var3; var12 < 0; ++var12) {
        int var13 = 65536 - var8 >> 8;
        int var14 = var8 >> 8;
        int var15 = (var13 * var6 + var14 * var7 & 65280) >>> 8;
        if (var15 == 0) {
          var11 += drawingAreaWidth;
        } else {
          int var16 = (var14 * (var5 & 16711935) + var13 * (var4 & 16711935) & -16711936) + (var14 * (var5 & 65280) + var13 * (var4 & 65280) & 16711680) >>> 8;
          int var17 = 255 - var15;
          int var18 = ((var16 & 16711935) * var15 >> 8 & 16711935) + (var15 * (var16 & 65280) >> 8 & 65280);

          for (int var19 = -var2; var19 < 0; ++var19) {
            int var20 = drawingAreaPixels[var11];
            if (var20 == 0) {
              drawingAreaPixels[var11++] = var18;
            } else {
              var20 = ((var20 & 16711935) * var17 >> 8 & 16711935) + (var17 * (var20 & 65280) >> 8 & 65280);
              drawingAreaPixels[var11++] = var18 + var20;
            }
          }

          var11 += var10;
        }
        var8 += var9;
      }

    }
  }

  public static void method1364(int var0, int var1, int var2, int var3) {
    if (drawingAreaLeft < var0) {
      drawingAreaLeft = var0;
    }

    if (drawingAreaTop < var1) {
      drawingAreaTop = var1;
    }

    if (drawingAreaBottom > var2) {
      drawingAreaBottom = var2;
    }

    if (drawingAreaRight > var3) {
      drawingAreaRight = var3;
    }

  }

  static void drawHorizontalLine(int var0, int var1, int var2, int rgb, int alpha) {
    if (var1 >= drawingAreaTop && var1 < drawingAreaRight) {
      if (var0 < drawingAreaLeft) {
        var2 -= drawingAreaLeft - var0;
        var0 = drawingAreaLeft;
      }

      if (var0 + var2 > drawingAreaBottom) {
        var2 = drawingAreaBottom - var0;
      }

      int intensity = 256 - alpha;
      int srcR = (rgb >> 16 & 255) * alpha;
      int srcG = (rgb >> 8 & 0xff) * alpha;
      int srcB = (rgb & 0xff) * alpha;
      int pixel = var0 + drawingAreaWidth * var1;

      for (int x = 0; x < var2; ++x) {
        int dstR = intensity * (drawingAreaPixels[pixel] >> 16 & 255);
        int dstG = (drawingAreaPixels[pixel] >> 8 & 255) * intensity;
        int dstB = intensity * (drawingAreaPixels[pixel] & 255);
        int dstRgb = (srcB + dstB >> 8) + (srcR + dstR >> 8 << 16) + (srcG + dstG >> 8 << 8);
        drawingAreaPixels[pixel++] = dstRgb;
      }

    }
  }

  static void drawVerticalLine(int var0, int var1, int var2, int var3, int var4) {
    if (var0 >= drawingAreaLeft && var0 < drawingAreaBottom) {
      if (var1 < drawingAreaTop) {
        var2 -= drawingAreaTop - var1;
        var1 = drawingAreaTop;
      }

      if (var2 + var1 > drawingAreaRight) {
        var2 = drawingAreaRight - var1;
      }

      int var5 = 256 - var4;
      int var6 = (var3 >> 16 & 255) * var4;
      int var7 = (var3 >> 8 & 255) * var4;
      int var8 = var4 * (var3 & 255);
      int var9 = var0 + drawingAreaWidth * var1;

      for (int var10 = 0; var10 < var2; ++var10) {
        int var11 = var5 * (drawingAreaPixels[var9] >> 16 & 255);
        int var12 = (drawingAreaPixels[var9] >> 8 & 255) * var5;
        int var13 = var5 * (drawingAreaPixels[var9] & 255);
        int var14 = (var8 + var13 >> 8) + (var6 + var11 >> 8 << 16) + (var7 + var12 >> 8 << 8);
        drawingAreaPixels[var9] = var14;
        var9 += drawingAreaWidth;
      }

    }
  }
}
