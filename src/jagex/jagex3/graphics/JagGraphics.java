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

  public static void fillRect(int x, int y, int width, int height, int color) {
    if (x < drawingAreaLeft) {
      width -= drawingAreaLeft - x;
      x = drawingAreaLeft;
    }

    if (y < drawingAreaTop) {
      height -= drawingAreaTop - y;
      y = drawingAreaTop;
    }

    if (x + width > drawingAreaRight) {
      width = drawingAreaRight - x;
    }

    if (y + height > drawingAreaBottom) {
      height = drawingAreaBottom - y;
    }

    int stride = drawingAreaWidth - width;
    int offset = x + drawingAreaWidth * y;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        drawingAreaPixels[offset + j] = color;
      }
      offset += stride;
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

  public static void setClip(int x, int y, int width, int height) {
    if (x < 0) {
      x = 0;
    }

    if (y < 0) {
      y = 0;
    }

    if (width > drawingAreaWidth) {
      width = drawingAreaWidth;
    }

    if (height > drawingAreaHeight) {
      height = drawingAreaHeight;
    }

    drawingAreaLeft = x;
    drawingAreaTop = y;
    drawingAreaRight = width;
    drawingAreaBottom = height;
  }

  public static void applyImageFilter(int x, int y, int width, int height, int intensity) {
    if (intensity != 0) {
      if (intensity == 256) {
        applyFullIntensityFilter(x, y, width, height);
      } else {
        int absWidth = Math.abs(width);
        int diffIntensity = 256 - intensity;
        int redIntensity = (height >> 16 & 255) * intensity;
        int greenIntensity = (height >> 8 & 255) * intensity;
        int blueIntensity = (height & 255) * intensity;
        int startY = y - absWidth;
        startY = Math.max(startY, drawingAreaTop);
        int endY = absWidth + y + 1;
        endY = Math.min(endY, drawingAreaRight);
        int currentY = startY;
        int radiusSquared = absWidth * absWidth;
        int delta = 0;
        int startYDifference = y - startY;
        int startYSquared = startYDifference * startYDifference;
        int innerDiff = startYSquared - startYDifference;

        if (y > endY) {
          y = endY;
        }

        int x1;
        int x2;
        int pixelOffset;
        int redComponent;
        int greenComponent;
        int blueComponent;

        while (currentY < y) {
          while (innerDiff <= radiusSquared || startYSquared <= radiusSquared) {
            startYSquared = startYSquared + delta + delta;
            innerDiff += delta++ + delta;
          }

          x1 = x - delta + 1;
          if (x1 < drawingAreaLeft) {
            x1 = drawingAreaLeft;
          }

          x2 = x + delta;
          if (x2 > drawingAreaBottom) {
            x2 = drawingAreaBottom;
          }

          pixelOffset = x1 + currentY * drawingAreaWidth;

          for (int currentX = x1; currentX < x2; ++currentX) {
            redComponent = diffIntensity * (drawingAreaPixels[pixelOffset] >> 16 & 255);
            greenComponent = (drawingAreaPixels[pixelOffset] >> 8 & 255) * diffIntensity;
            blueComponent = diffIntensity * (drawingAreaPixels[pixelOffset] & 255);
            int resultPixel = (blueIntensity + blueComponent >> 8) + (redIntensity + redComponent >> 8 << 16) + (greenIntensity + greenComponent >> 8 << 8);
            drawingAreaPixels[pixelOffset++] = resultPixel;
          }

          ++currentY;
          startYSquared -= startYDifference-- + startYDifference;
          innerDiff -= startYDifference + startYDifference;
        }

        delta = absWidth;
        startYDifference = -startYDifference;
        innerDiff = radiusSquared + startYDifference * startYDifference;
        startYSquared = innerDiff - absWidth;

        for (innerDiff -= startYDifference; currentY < endY; startYSquared += startYDifference++ + startYDifference) {
          while (innerDiff > radiusSquared && startYSquared > radiusSquared) {
            innerDiff -= delta-- + delta;
            startYSquared -= delta + delta;
          }

          x1 = x - delta;
          if (x1 < drawingAreaLeft) {
            x1 = drawingAreaLeft;
          }

          x2 = x + delta;
          if (x2 > drawingAreaBottom - 1) {
            x2 = drawingAreaBottom - 1;
          }

          pixelOffset = x1 + currentY * drawingAreaWidth;

          for (int currentX = x1; currentX <= x2; ++currentX) {
            redComponent = diffIntensity * (drawingAreaPixels[pixelOffset] >> 16 & 255);
            greenComponent = (drawingAreaPixels[pixelOffset] >> 8 & 255) * diffIntensity;
            blueComponent = diffIntensity * (drawingAreaPixels[pixelOffset] & 255);
            int resultPixel = (blueIntensity + blueComponent >> 8) + (redIntensity + redComponent >> 8 << 16) + (greenIntensity + greenComponent >> 8 << 8);
            drawingAreaPixels[pixelOffset++] = resultPixel;
          }

          ++currentY;
          innerDiff = innerDiff + delta + delta;
        }
      }
    }
  }

  public static void drawImageWithMask(int x, int y, int width, int height, int primaryColor, int secondaryColor, byte[] maskData, int maskWidth) {
    if (x + width >= 0 && y + height >= 0) {
      if (x < drawingAreaWidth && y < drawingAreaHeight) {
        int xOffset = 0;
        int yOffset = 0;

        if (x < 0) {
          xOffset -= x;
          width += x;
        }

        if (y < 0) {
          yOffset -= y;
          height += y;
        }

        if (x + width > drawingAreaWidth) {
          width = drawingAreaWidth - x;
        }

        if (y + height > drawingAreaHeight) {
          height = drawingAreaHeight - y;
        }

        int maskLength = maskData.length / maskWidth;
        int rowLength = drawingAreaWidth - width;
        int primaryAlpha = primaryColor >>> 24;
        int secondaryAlpha = secondaryColor >>> 24;
        int pixelOffset;
        if (primaryAlpha == 255 && secondaryAlpha == 255) {
          pixelOffset = x + xOffset + (yOffset + y) * drawingAreaWidth;

          for (int currentY = yOffset + y; currentY < height + yOffset + y; ++currentY) {
            for (int currentX = xOffset + x; currentX < xOffset + x + width; ++currentX) {
              int maskX = (currentY - y) % maskLength;
              int maskY = (currentX - x) % maskWidth;

              if (maskData[maskY + maskX * maskWidth] != 0) {
                drawingAreaPixels[pixelOffset++] = secondaryColor;
              } else {
                drawingAreaPixels[pixelOffset++] = primaryColor;
              }
            }

            pixelOffset += rowLength;
          }
        } else {
          pixelOffset = x + xOffset + (yOffset + y) * drawingAreaWidth;

          for (int currentY = yOffset + y; currentY < height + yOffset + y; ++currentY) {
            for (int currentX = xOffset + x; currentX < xOffset + x + width; ++currentX) {
              int maskX = (currentY - y) % maskLength;
              int maskY = (currentX - x) % maskWidth;
              int targetColor = primaryColor;

              if (maskData[maskY + maskX * maskWidth] != 0) {
                targetColor = secondaryColor;
              }

              int targetAlpha = targetColor >>> 24;
              int sourceAlpha = 255 - targetAlpha;
              int currentPixel = drawingAreaPixels[pixelOffset];
              int targetRed = ((targetColor & 16711935) * targetAlpha + (currentPixel & 16711935) * sourceAlpha & -16711936) + (targetAlpha * (targetColor & 65280) + sourceAlpha * (currentPixel & 65280) & 16711680) >> 8;
              drawingAreaPixels[pixelOffset++] = targetRed;
            }

            pixelOffset += rowLength;
          }
        }
      }
    }
  }

  public static void setPixel(int x, int y, int color) {
    if (x >= drawingAreaLeft && y >= drawingAreaTop && x < drawingAreaBottom && y < drawingAreaRight) {
      drawingAreaPixels[x + drawingAreaWidth * y] = color;
    }
  }

  public static void applyFullIntensityFilter(int x, int y, int radius, int color) {
    if (radius == 0) {
      setPixel(x, y, color);
    } else {
      if (radius < 0) {
        radius = -radius;
      }

      int startY = y - radius;
      if (startY < drawingAreaTop) {
        startY = drawingAreaTop;
      }

      int endY = radius + y + 1;
      if (endY > drawingAreaRight) {
        endY = drawingAreaRight;
      }

      int currentY = startY;
      int radiusSquared = radius * radius;
      int delta = 0;
      int startYDifference = y - startY;
      int startYSquared = startYDifference * startYDifference;
      int innerDiff = startYSquared - startYDifference;

      if (y > endY) {
        y = endY;
      }

      int x1;
      int x2;
      int pixelOffset;

      while (currentY < y) {
        while (innerDiff <= radiusSquared || startYSquared <= radiusSquared) {
          startYSquared = startYSquared + delta + delta;
          innerDiff += delta++ + delta;
        }

        x1 = x - delta + 1;
        if (x1 < drawingAreaLeft) {
          x1 = drawingAreaLeft;
        }

        x2 = x + delta;
        if (x2 > drawingAreaBottom) {
          x2 = drawingAreaBottom;
        }

        pixelOffset = x1 + currentY * drawingAreaWidth;

        for (int currentX = x1; currentX < x2; ++currentX) {
          drawingAreaPixels[pixelOffset++] = color;
        }

        ++currentY;
        startYSquared -= startYDifference-- + startYDifference;
        innerDiff -= startYDifference + startYDifference;
      }

      delta = radius;
      startYDifference = -startYDifference;
      innerDiff = radiusSquared + startYDifference * startYDifference;
      startYSquared = innerDiff - radius;

      for (innerDiff -= startYDifference; currentY < endY; startYSquared += startYDifference++ + startYDifference) {
        while (innerDiff > radiusSquared && startYSquared > radiusSquared) {
          innerDiff -= delta-- + delta;
          startYSquared -= delta + delta;
        }

        x1 = x - delta;
        if (x1 < drawingAreaLeft) {
          x1 = drawingAreaLeft;
        }

        x2 = x + delta;
        if (x2 > drawingAreaBottom - 1) {
          x2 = drawingAreaBottom - 1;
        }

        pixelOffset = x1 + currentY * drawingAreaWidth;

        for (int currentX = x1; currentX <= x2; ++currentX) {
          drawingAreaPixels[pixelOffset++] = color;
        }

        ++currentY;
        innerDiff = innerDiff + delta + delta;
      }
    }
  }

  public static void resetDrawingArea() {
    drawingAreaLeft = 0;
    drawingAreaTop = 0;
    drawingAreaBottom = drawingAreaWidth;
    drawingAreaRight = drawingAreaHeight;
  }

  public static void drawRectWithAlpha(int x, int y, int width, int height, int color, int alpha) {
    if (x < drawingAreaLeft) {
      width -= drawingAreaLeft - x;
      x = drawingAreaLeft;
    }

    if (y < drawingAreaTop) {
      height -= drawingAreaTop - y;
      y = drawingAreaTop;
    }

    if (x + width > drawingAreaBottom) {
      width = drawingAreaBottom - x;
    }

    if (y + height > drawingAreaRight) {
      height = drawingAreaRight - y;
    }

    int combinedColor = (alpha * (color & 16711935) >> 8 & 16711935) + (alpha * (color & 65280) >> 8 & 65280);
    int inverseAlpha = 256 - alpha;
    int stride = drawingAreaWidth - width;
    int pixelOffset = x + drawingAreaWidth * y;

    for (int row = 0; row < height; ++row) {
      for (int col = 0; col < width; ++col) {
        int pixelColor = drawingAreaPixels[pixelOffset];
        pixelColor = ((pixelColor & 16711935) * inverseAlpha >> 8 & 16711935) + (inverseAlpha * (pixelColor & 65280) >> 8 & 65280);
        drawingAreaPixels[pixelOffset++] = pixelColor + combinedColor;
      }
      pixelOffset += stride;
    }
  }

  public static void drawRectOutline(int x, int y, int width, int height, int color) {
    drawHorizontalLine(x, y, width, color);
    drawHorizontalLine(x, y + height - 1, width, color);
    drawVerticalLine(x, y, height, color);
    drawVerticalLine(x + width - 1, y, height, color);
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

  public static void blitRectWithAlpha(int x, int y, int width, int height, int color1, int color2) {
    if (width > 0 && height > 0) {
      int delta = 0;
      int deltaStep = 65536 / height;
      if (x < drawingAreaLeft) {
        width -= drawingAreaLeft - x;
        x = drawingAreaLeft;
      }

      if (y < drawingAreaTop) {
        delta += (drawingAreaTop - y) * deltaStep;
        height -= drawingAreaTop - y;
        y = drawingAreaTop;
      }

      if (x + width > drawingAreaBottom) {
        width = drawingAreaBottom - x;
      }

      if (height + y > drawingAreaRight) {
        height = drawingAreaRight - y;
      }

      int stride = drawingAreaWidth - width;
      int offset = x + drawingAreaWidth * y;

      for (int dy = -height; dy < 0; ++dy) {
        int alpha1 = 65536 - delta >> 8;
        int alpha2 = delta >> 8;
        int blendedColor = (alpha2 * (color2 & 16711935) + alpha1 * (color1 & 16711935) & -16711936) + (alpha2 * (color2 & 65280) + alpha1 * (color1 & 65280) & 16711680) >>> 8;

        for (int dx = -width; dx < 0; ++dx) {
          drawingAreaPixels[offset++] = blendedColor;
        }

        offset += stride;
        delta += deltaStep;
      }
    }
  }

  public static void fillRectangles(int x, int y, int color, int[] widths, int[] heights) {
    int offset = x + drawingAreaWidth * y;
    for (int i = 0; i < widths.length; ++i) {
      int rowOffset = offset + widths[i];
      for (int j = -heights[i]; j < 0; ++j) {
        drawingAreaPixels[rowOffset++] = color;
      }
      offset += drawingAreaWidth;
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

  public static void blitImageWithAlpha(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
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

  public static void setDrawingArea(int var0, int var1, int var2, int var3) {
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
