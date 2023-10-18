package jagex.jagex3.graphics;

public class JagGraphics3D extends JagGraphics {

  public static final int[] SIN_TABLE = new int[2048];
  public static final int[] COS_TABLE = new int[2048];
  public static final int[] COLOR_PALETTE = new int[65536];
  public static final int[] RECIPROCAL_32768 = new int[512];
  public static final int[] RECIPROCAL_65536 = new int[2048];

  public static int[] anIntArray787 = new int[1024];

  public static boolean drawingOffscreen = false;
  public static boolean aBoolean789 = true;
  public static boolean aBoolean786 = false;
  public static boolean lowDetail = false;

  public static int alpha = 0;
  public static int scale = 512;
  public static int anInt386;
  public static int anInt366;
  public static int anInt666;
  public static int anInt788;
  public static int anInt791;
  public static int anInt696;
  public static int anInt563;
  public static int anInt702;
  public static MaterialProvider materialProvider;

  static {
    for (int i = 1; i < 512; ++i) {
      RECIPROCAL_32768[i] = 32768 / i;
    }

    for (int i = 1; i < 2048; ++i) {
      RECIPROCAL_65536[i] = 65536 / i;
    }

    for (int i = 0; i < 2048; ++i) {
      SIN_TABLE[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
      COS_TABLE[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
    }
  }

  public static int dot(int var0, int var1, int var2, int var3) {
    return var0 * var2 + var3 * var1 >> 16;
  }

  public static int cross(int var0, int var1, int var2, int var3) {
    return var2 * var1 - var3 * var0 >> 16;
  }

  public static int rgbPow(int rgb, double power) {
    int red = (rgb >> 16) & 0xFF;
    int green = (rgb >> 8) & 0xFF;
    int blue = rgb & 0xFF;

    double redPow = Math.pow(red / 256.0, power);
    double greenPow = Math.pow(green / 256.0, power);
    double bluePow = Math.pow(blue / 256.0, power);

    int newRed = (int) (redPow * 256.0);
    int newGreen = (int) (greenPow * 256.0);
    int newBlue = (int) (bluePow * 256.0);

    return (newRed << 16) | (newGreen << 8) | newBlue;
  }

  public static void fillTriangle(int var0, int var1, int var2, int var3, int var4, int var5, int color) {
    int var7 = 0;
    if (var0 != var1) {
      var7 = (var4 - var3 << 14) / (var1 - var0);
    }

    int var8 = 0;
    if (var2 != var1) {
      var8 = (var5 - var4 << 14) / (var2 - var1);
    }

    int var9 = 0;
    if (var0 != var2) {
      var9 = (var3 - var5 << 14) / (var0 - var2);
    }

    if (var0 <= var1 && var0 <= var2) {
      if (var0 < anInt702) {
        if (var1 > anInt702) {
          var1 = anInt702;
        }

        if (var2 > anInt702) {
          var2 = anInt702;
        }

        if (var1 < var2) {
          var5 = var3 <<= 14;
          if (var0 < 0) {
            var5 -= var0 * var9;
            var3 -= var0 * var7;
            var0 = 0;
          }

          var4 <<= 14;
          if (var1 < 0) {
            var4 -= var8 * var1;
            var1 = 0;
          }

          if (var0 != var1 && var9 < var7 || var0 == var1 && var9 > var8) {
            var2 -= var1;
            var1 -= var0;
            var0 = anIntArray787[var0];

            while (true) {
              --var1;
              if (var1 < 0) {
                while (true) {
                  --var2;
                  if (var2 < 0) {
                    return;
                  }

                  applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var4 >> 14);
                  var5 += var9;
                  var4 += var8;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var3 >> 14);
              var5 += var9;
              var3 += var7;
              var0 += JagGraphics.drawingAreaWidth;
            }
          }
          var2 -= var1;
          var1 -= var0;
          var0 = anIntArray787[var0];

          while (true) {
            --var1;
            if (var1 < 0) {
              while (true) {
                --var2;
                if (var2 < 0) {
                  return;
                }

                applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var4 >> 14, var5 >> 14);
                var5 += var9;
                var4 += var8;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var5 >> 14);
            var5 += var9;
            var3 += var7;
            var0 += JagGraphics.drawingAreaWidth;
          }
        }
        var4 = var3 <<= 14;
        if (var0 < 0) {
          var4 -= var0 * var9;
          var3 -= var0 * var7;
          var0 = 0;
        }

        var5 <<= 14;
        if (var2 < 0) {
          var5 -= var8 * var2;
          var2 = 0;
        }

        if (var0 != var2 && var9 < var7 || var0 == var2 && var8 > var7) {
          var1 -= var2;
          var2 -= var0;
          var0 = anIntArray787[var0];

          while (true) {
            --var2;
            if (var2 < 0) {
              while (true) {
                --var1;
                if (var1 < 0) {
                  return;
                }

                applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var3 >> 14);
                var5 += var8;
                var3 += var7;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var4 >> 14, var3 >> 14);
            var4 += var9;
            var3 += var7;
            var0 += JagGraphics.drawingAreaWidth;
          }
        }
        var1 -= var2;
        var2 -= var0;
        var0 = anIntArray787[var0];

        while (true) {
          --var2;
          if (var2 < 0) {
            while (true) {
              --var1;
              if (var1 < 0) {
                return;
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var5 >> 14);
              var5 += var8;
              var3 += var7;
              var0 += JagGraphics.drawingAreaWidth;
            }
          }

          applyColorToPixels(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var4 >> 14);
          var4 += var9;
          var3 += var7;
          var0 += JagGraphics.drawingAreaWidth;
        }
      }
    } else if (var1 <= var2) {
      if (var1 < anInt702) {
        if (var2 > anInt702) {
          var2 = anInt702;
        }

        if (var0 > anInt702) {
          var0 = anInt702;
        }

        if (var2 < var0) {
          var3 = var4 <<= 14;
          if (var1 < 0) {
            var3 -= var7 * var1;
            var4 -= var8 * var1;
            var1 = 0;
          }

          var5 <<= 14;
          if (var2 < 0) {
            var5 -= var9 * var2;
            var2 = 0;
          }

          if (var2 != var1 && var7 < var8 || var2 == var1 && var7 > var9) {
            var0 -= var2;
            var2 -= var1;
            var1 = anIntArray787[var1];

            while (true) {
              --var2;
              if (var2 < 0) {
                while (true) {
                  --var0;
                  if (var0 < 0) {
                    return;
                  }

                  applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var5 >> 14);
                  var3 += var7;
                  var5 += var9;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var4 >> 14);
              var3 += var7;
              var4 += var8;
              var1 += JagGraphics.drawingAreaWidth;
            }
          }
          var0 -= var2;
          var2 -= var1;
          var1 = anIntArray787[var1];

          while (true) {
            --var2;
            if (var2 < 0) {
              while (true) {
                --var0;
                if (var0 < 0) {
                  return;
                }

                applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var5 >> 14, var3 >> 14);
                var3 += var7;
                var5 += var9;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var3 >> 14);
            var3 += var7;
            var4 += var8;
            var1 += JagGraphics.drawingAreaWidth;
          }
        }
        var5 = var4 <<= 14;
        if (var1 < 0) {
          var5 -= var7 * var1;
          var4 -= var8 * var1;
          var1 = 0;
        }

        var3 <<= 14;
        if (var0 < 0) {
          var3 -= var0 * var9;
          var0 = 0;
        }

        if (var7 < var8) {
          var2 -= var0;
          var0 -= var1;
          var1 = anIntArray787[var1];

          while (true) {
            --var0;
            if (var0 < 0) {
              while (true) {
                --var2;
                if (var2 < 0) {
                  return;
                }

                applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var4 >> 14);
                var3 += var9;
                var4 += var8;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var5 >> 14, var4 >> 14);
            var5 += var7;
            var4 += var8;
            var1 += JagGraphics.drawingAreaWidth;
          }
        }
        var2 -= var0;
        var0 -= var1;
        var1 = anIntArray787[var1];

        while (true) {
          --var0;
          if (var0 < 0) {
            while (true) {
              --var2;
              if (var2 < 0) {
                return;
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var3 >> 14);
              var3 += var9;
              var4 += var8;
              var1 += JagGraphics.drawingAreaWidth;
            }
          }

          applyColorToPixels(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var5 >> 14);
          var5 += var7;
          var4 += var8;
          var1 += JagGraphics.drawingAreaWidth;
        }
      }
    } else if (var2 < anInt702) {
      if (var0 > anInt702) {
        var0 = anInt702;
      }

      if (var1 > anInt702) {
        var1 = anInt702;
      }

      if (var0 < var1) {
        var4 = var5 <<= 14;
        if (var2 < 0) {
          var4 -= var8 * var2;
          var5 -= var9 * var2;
          var2 = 0;
        }

        var3 <<= 14;
        if (var0 < 0) {
          var3 -= var0 * var7;
          var0 = 0;
        }

        if (var8 < var9) {
          var1 -= var0;
          var0 -= var2;
          var2 = anIntArray787[var2];

          while (true) {
            --var0;
            if (var0 < 0) {
              while (true) {
                --var1;
                if (var1 < 0) {
                  return;
                }

                applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var3 >> 14);
                var4 += var8;
                var3 += var7;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var5 >> 14);
            var4 += var8;
            var5 += var9;
            var2 += JagGraphics.drawingAreaWidth;
          }
        }
        var1 -= var0;
        var0 -= var2;
        var2 = anIntArray787[var2];

        while (true) {
          --var0;
          if (var0 < 0) {
            while (true) {
              --var1;
              if (var1 < 0) {
                return;
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var3 >> 14, var4 >> 14);
              var4 += var8;
              var3 += var7;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var4 >> 14);
          var4 += var8;
          var5 += var9;
          var2 += JagGraphics.drawingAreaWidth;
        }
      }
      var3 = var5 <<= 14;
      if (var2 < 0) {
        var3 -= var8 * var2;
        var5 -= var9 * var2;
        var2 = 0;
      }

      var4 <<= 14;
      if (var1 < 0) {
        var4 -= var7 * var1;
        var1 = 0;
      }

      if (var8 < var9) {
        var0 -= var1;
        var1 -= var2;
        var2 = anIntArray787[var2];

        while (true) {
          --var1;
          if (var1 < 0) {
            while (true) {
              --var0;
              if (var0 < 0) {
                return;
              }

              applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var5 >> 14);
              var4 += var7;
              var5 += var9;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var3 >> 14, var5 >> 14);
          var3 += var8;
          var5 += var9;
          var2 += JagGraphics.drawingAreaWidth;
        }
      }
      var0 -= var1;
      var1 -= var2;
      var2 = anIntArray787[var2];

      while (true) {
        --var1;
        if (var1 < 0) {
          while (true) {
            --var0;
            if (var0 < 0) {
              return;
            }

            applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var4 >> 14);
            var4 += var7;
            var5 += var9;
            var2 += JagGraphics.drawingAreaWidth;
          }
        }

        applyColorToPixels(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var3 >> 14);
        var3 += var8;
        var5 += var9;
        var2 += JagGraphics.drawingAreaWidth;
      }
    }
  }

  public static void rasterizeTriangleWithShading(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
    int var9 = var4 - var3;
    int var10 = var1 - var0;
    int var11 = var5 - var3;
    int var12 = var2 - var0;
    int var13 = var7 - var6;
    int var14 = var8 - var6;
    int var15;
    if (var2 != var1) {
      var15 = (var5 - var4 << 14) / (var2 - var1);
    } else {
      var15 = 0;
    }

    int var16;
    if (var0 != var1) {
      var16 = (var9 << 14) / var10;
    } else {
      var16 = 0;
    }

    int var17;
    if (var0 != var2) {
      var17 = (var11 << 14) / var12;
    } else {
      var17 = 0;
    }

    int var18 = var9 * var12 - var11 * var10;
    if (var18 != 0) {
      int var19 = (var13 * var12 - var14 * var10 << 8) / var18;
      int var20 = (var14 * var9 - var13 * var11 << 8) / var18;
      if (var0 <= var1 && var0 <= var2) {
        if (var0 < anInt702) {
          if (var1 > anInt702) {
            var1 = anInt702;
          }

          if (var2 > anInt702) {
            var2 = anInt702;
          }

          var6 = var19 + ((var6 << 8) - var3 * var19);
          if (var1 < var2) {
            var5 = var3 <<= 14;
            if (var0 < 0) {
              var5 -= var0 * var17;
              var3 -= var0 * var16;
              var6 -= var0 * var20;
              var0 = 0;
            }

            var4 <<= 14;
            if (var1 < 0) {
              var4 -= var15 * var1;
              var1 = 0;
            }

            if ((var0 == var1 || var17 >= var16) && (var0 != var1 || var17 <= var15)) {
              var2 -= var1;
              var1 -= var0;
              var0 = anIntArray787[var0];

              while (true) {
                --var1;
                if (var1 < 0) {
                  while (true) {
                    --var2;
                    if (var2 < 0) {
                      return;
                    }

                    drawPixels(JagGraphics.drawingAreaPixels, var0, var4 >> 14, var5 >> 14, var6, var19);
                    var5 += var17;
                    var4 += var15;
                    var6 += var20;
                    var0 += JagGraphics.drawingAreaWidth;
                  }
                }

                drawPixels(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var5 >> 14, var6, var19);
                var5 += var17;
                var3 += var16;
                var6 += var20;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }
            var2 -= var1;
            var1 -= var0;
            var0 = anIntArray787[var0];

            while (true) {
              --var1;
              if (var1 < 0) {
                while (true) {
                  --var2;
                  if (var2 < 0) {
                    return;
                  }

                  drawPixels(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var4 >> 14, var6, var19);
                  var5 += var17;
                  var4 += var15;
                  var6 += var20;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              drawPixels(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var3 >> 14, var6, var19);
              var5 += var17;
              var3 += var16;
              var6 += var20;
              var0 += JagGraphics.drawingAreaWidth;
            }
          }
          var4 = var3 <<= 14;
          if (var0 < 0) {
            var4 -= var0 * var17;
            var3 -= var0 * var16;
            var6 -= var0 * var20;
            var0 = 0;
          }

          var5 <<= 14;
          if (var2 < 0) {
            var5 -= var15 * var2;
            var2 = 0;
          }

          if (var0 != var2 && var17 < var16 || var0 == var2 && var15 > var16) {
            var1 -= var2;
            var2 -= var0;
            var0 = anIntArray787[var0];

            while (true) {
              --var2;
              if (var2 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  drawPixels(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var3 >> 14, var6, var19);
                  var5 += var15;
                  var3 += var16;
                  var6 += var20;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              drawPixels(JagGraphics.drawingAreaPixels, var0, var4 >> 14, var3 >> 14, var6, var19);
              var4 += var17;
              var3 += var16;
              var6 += var20;
              var0 += JagGraphics.drawingAreaWidth;
            }
          }
          var1 -= var2;
          var2 -= var0;
          var0 = anIntArray787[var0];

          while (true) {
            --var2;
            if (var2 < 0) {
              while (true) {
                --var1;
                if (var1 < 0) {
                  return;
                }

                drawPixels(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var5 >> 14, var6, var19);
                var5 += var15;
                var3 += var16;
                var6 += var20;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            drawPixels(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var4 >> 14, var6, var19);
            var4 += var17;
            var3 += var16;
            var6 += var20;
            var0 += JagGraphics.drawingAreaWidth;
          }
        }
      } else if (var1 <= var2) {
        if (var1 < anInt702) {
          if (var2 > anInt702) {
            var2 = anInt702;
          }

          if (var0 > anInt702) {
            var0 = anInt702;
          }

          var7 = var19 + ((var7 << 8) - var19 * var4);
          if (var2 < var0) {
            var3 = var4 <<= 14;
            if (var1 < 0) {
              var3 -= var16 * var1;
              var4 -= var15 * var1;
              var7 -= var20 * var1;
              var1 = 0;
            }

            var5 <<= 14;
            if (var2 < 0) {
              var5 -= var17 * var2;
              var2 = 0;
            }

            if ((var2 == var1 || var16 >= var15) && (var2 != var1 || var16 <= var17)) {
              var0 -= var2;
              var2 -= var1;
              var1 = anIntArray787[var1];

              while (true) {
                --var2;
                if (var2 < 0) {
                  while (true) {
                    --var0;
                    if (var0 < 0) {
                      return;
                    }

                    drawPixels(JagGraphics.drawingAreaPixels, var1, var5 >> 14, var3 >> 14, var7, var19);
                    var3 += var16;
                    var5 += var17;
                    var7 += var20;
                    var1 += JagGraphics.drawingAreaWidth;
                  }
                }

                drawPixels(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var3 >> 14, var7, var19);
                var3 += var16;
                var4 += var15;
                var7 += var20;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }
            var0 -= var2;
            var2 -= var1;
            var1 = anIntArray787[var1];

            while (true) {
              --var2;
              if (var2 < 0) {
                while (true) {
                  --var0;
                  if (var0 < 0) {
                    return;
                  }

                  drawPixels(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var5 >> 14, var7, var19);
                  var3 += var16;
                  var5 += var17;
                  var7 += var20;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              drawPixels(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var4 >> 14, var7, var19);
              var3 += var16;
              var4 += var15;
              var7 += var20;
              var1 += JagGraphics.drawingAreaWidth;
            }
          }
          var5 = var4 <<= 14;
          if (var1 < 0) {
            var5 -= var16 * var1;
            var4 -= var15 * var1;
            var7 -= var20 * var1;
            var1 = 0;
          }

          var3 <<= 14;
          if (var0 < 0) {
            var3 -= var0 * var17;
            var0 = 0;
          }

          if (var16 < var15) {
            var2 -= var0;
            var0 -= var1;
            var1 = anIntArray787[var1];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var2;
                  if (var2 < 0) {
                    return;
                  }

                  drawPixels(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var4 >> 14, var7, var19);
                  var3 += var17;
                  var4 += var15;
                  var7 += var20;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              drawPixels(JagGraphics.drawingAreaPixels, var1, var5 >> 14, var4 >> 14, var7, var19);
              var5 += var16;
              var4 += var15;
              var7 += var20;
              var1 += JagGraphics.drawingAreaWidth;
            }
          }
          var2 -= var0;
          var0 -= var1;
          var1 = anIntArray787[var1];

          while (true) {
            --var0;
            if (var0 < 0) {
              while (true) {
                --var2;
                if (var2 < 0) {
                  return;
                }

                drawPixels(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var3 >> 14, var7, var19);
                var3 += var17;
                var4 += var15;
                var7 += var20;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            drawPixels(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var5 >> 14, var7, var19);
            var5 += var16;
            var4 += var15;
            var7 += var20;
            var1 += JagGraphics.drawingAreaWidth;
          }
        }
      } else if (var2 < anInt702) {
        if (var0 > anInt702) {
          var0 = anInt702;
        }

        if (var1 > anInt702) {
          var1 = anInt702;
        }

        var8 = var19 + ((var8 << 8) - var5 * var19);
        if (var0 < var1) {
          var4 = var5 <<= 14;
          if (var2 < 0) {
            var4 -= var15 * var2;
            var5 -= var17 * var2;
            var8 -= var20 * var2;
            var2 = 0;
          }

          var3 <<= 14;
          if (var0 < 0) {
            var3 -= var0 * var16;
            var0 = 0;
          }

          if (var15 < var17) {
            var1 -= var0;
            var0 -= var2;
            var2 = anIntArray787[var2];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  drawPixels(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var3 >> 14, var8, var19);
                  var4 += var15;
                  var3 += var16;
                  var8 += var20;
                  var2 += JagGraphics.drawingAreaWidth;
                }
              }

              drawPixels(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var5 >> 14, var8, var19);
              var4 += var15;
              var5 += var17;
              var8 += var20;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }
          var1 -= var0;
          var0 -= var2;
          var2 = anIntArray787[var2];

          while (true) {
            --var0;
            if (var0 < 0) {
              while (true) {
                --var1;
                if (var1 < 0) {
                  return;
                }

                drawPixels(JagGraphics.drawingAreaPixels, var2, var3 >> 14, var4 >> 14, var8, var19);
                var4 += var15;
                var3 += var16;
                var8 += var20;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            drawPixels(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var4 >> 14, var8, var19);
            var4 += var15;
            var5 += var17;
            var8 += var20;
            var2 += JagGraphics.drawingAreaWidth;
          }
        }
        var3 = var5 <<= 14;
        if (var2 < 0) {
          var3 -= var15 * var2;
          var5 -= var17 * var2;
          var8 -= var20 * var2;
          var2 = 0;
        }

        var4 <<= 14;
        if (var1 < 0) {
          var4 -= var16 * var1;
          var1 = 0;
        }

        if (var15 < var17) {
          var0 -= var1;
          var1 -= var2;
          var2 = anIntArray787[var2];

          while (true) {
            --var1;
            if (var1 < 0) {
              while (true) {
                --var0;
                if (var0 < 0) {
                  return;
                }

                drawPixels(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var5 >> 14, var8, var19);
                var4 += var16;
                var5 += var17;
                var8 += var20;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            drawPixels(JagGraphics.drawingAreaPixels, var2, var3 >> 14, var5 >> 14, var8, var19);
            var3 += var15;
            var5 += var17;
            var8 += var20;
            var2 += JagGraphics.drawingAreaWidth;
          }
        }
        var0 -= var1;
        var1 -= var2;
        var2 = anIntArray787[var2];

        while (true) {
          --var1;
          if (var1 < 0) {
            while (true) {
              --var0;
              if (var0 < 0) {
                return;
              }

              drawPixels(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var4 >> 14, var8, var19);
              var4 += var16;
              var5 += var17;
              var8 += var20;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          drawPixels(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var3 >> 14, var8, var19);
          var3 += var15;
          var5 += var17;
          var8 += var20;
          var2 += JagGraphics.drawingAreaWidth;
        }
      }
    }
  }

  public static void method499() {
    initialize(JagGraphics.drawingAreaLeft, JagGraphics.drawingAreaTop, JagGraphics.drawingAreaBottom, JagGraphics.drawingAreaRight);
  }

  public static void method637(int var0, int var1) {
    int var2 = anIntArray787[0];
    int var3 = var2 / JagGraphics.drawingAreaWidth;
    int var4 = var2 - var3 * JagGraphics.drawingAreaWidth;
    anInt386 = var0 - var4;
    anInt366 = var1 - var3;
    anInt788 = -anInt386;
    anInt666 = anInt696 - anInt386;
    anInt791 = -anInt366;
    anInt563 = anInt702 - anInt366;
  }

  public static void initialize(int startX, int startY, int endX, int endY) {
    anInt696 = endX - startX;
    anInt702 = endY - startY;
    method23();
    int var4;
    int var5;
    if (anIntArray787.length < anInt702) {
      var4 = anInt702;
      --var4;
      var4 |= var4 >>> 1;
      var4 |= var4 >>> 2;
      var4 |= var4 >>> 4;
      var4 |= var4 >>> 8;
      var4 |= var4 >>> 16;
      var5 = var4 + 1;
      anIntArray787 = new int[var5];
    }

    var5 = startX + JagGraphics.drawingAreaWidth * startY;

    for (var4 = 0; var4 < anInt702; ++var4) {
      anIntArray787[var4] = var5;
      var5 += JagGraphics.drawingAreaWidth;
    }
  }

  public static void renderTexturedTriangleWithShading(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
    int[] var19 = materialProvider.pixels(var18);
    int var20;
    if (var19 == null) {
      var20 = materialProvider.rgb(var18);
      rasterizeTriangleWithShading(var0, var1, var2, var3, var4, var5, adjustColorIntensity(var20, var6), adjustColorIntensity(var20, var7), adjustColorIntensity(var20, var8));
    } else {
      lowDetail = materialProvider.isLowDetail();
      aBoolean786 = materialProvider.method1423(var18);
      var20 = var4 - var3;
      int var21 = var1 - var0;
      int var22 = var5 - var3;
      int var23 = var2 - var0;
      int var24 = var7 - var6;
      int var25 = var8 - var6;
      int var26 = 0;
      if (var0 != var1) {
        var26 = (var4 - var3 << 14) / (var1 - var0);
      }

      int var27 = 0;
      if (var2 != var1) {
        var27 = (var5 - var4 << 14) / (var2 - var1);
      }

      int var28 = 0;
      if (var0 != var2) {
        var28 = (var3 - var5 << 14) / (var0 - var2);
      }

      int var29 = var20 * var23 - var22 * var21;
      if (var29 != 0) {
        int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
        int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
        var10 = var9 - var10;
        var13 = var12 - var13;
        var16 = var15 - var16;
        var11 -= var9;
        var14 -= var12;
        var17 -= var15;
        int var32 = var11 * var12 - var9 * var14 << 14;
        int var33 = (int) ((((long) var15 * var14 - (long) var17 * var12) << 3 << 14) / (long) scale);
        int var34 = (int) ((((long) var17 * var9 - (long) var11 * var15) << 14) / (long) scale);
        int var35 = var10 * var12 - var13 * var9 << 14;
        int var36 = (int) ((((long) var13 * var15 - (long) var16 * var12) << 3 << 14) / (long) scale);
        int var37 = (int) ((((long) var16 * var9 - (long) var10 * var15) << 14) / (long) scale);
        int var38 = var13 * var11 - var10 * var14 << 14;
        int var39 = (int) ((((long) var16 * var14 - (long) var13 * var17) << 3 << 14) / (long) scale);
        int var40 = (int) ((((long) var17 * var10 - (long) var11 * var16) << 14) / (long) scale);
        int var41;
        if (var0 <= var1 && var0 <= var2) {
          if (var0 < anInt702) {
            if (var1 > anInt702) {
              var1 = anInt702;
            }

            if (var2 > anInt702) {
              var2 = anInt702;
            }

            var6 = var30 + ((var6 << 9) - var3 * var30);
            if (var1 < var2) {
              var5 = var3 <<= 14;
              if (var0 < 0) {
                var5 -= var0 * var28;
                var3 -= var0 * var26;
                var6 -= var0 * var31;
                var0 = 0;
              }

              var4 <<= 14;
              if (var1 < 0) {
                var4 -= var27 * var1;
                var1 = 0;
              }

              var41 = var0 - anInt366;
              var32 += var34 * var41;
              var35 += var37 * var41;
              var38 += var40 * var41;
              if ((var0 == var1 || var28 >= var26) && (var0 != var1 || var28 <= var27)) {
                var2 -= var1;
                var1 -= var0;
                var0 = anIntArray787[var0];

                while (true) {
                  --var1;
                  if (var1 < 0) {
                    while (true) {
                      --var2;
                      if (var2 < 0) {
                        return;
                      }

                      applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                      var5 += var28;
                      var4 += var27;
                      var6 += var31;
                      var0 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var28;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }
              var2 -= var1;
              var1 -= var0;
              var0 = anIntArray787[var0];

              while (true) {
                --var1;
                if (var1 < 0) {
                  while (true) {
                    --var2;
                    if (var2 < 0) {
                      return;
                    }

                    applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var28;
                    var4 += var27;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                var5 += var28;
                var3 += var26;
                var6 += var31;
                var0 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var4 = var3 <<= 14;
            if (var0 < 0) {
              var4 -= var0 * var28;
              var3 -= var0 * var26;
              var6 -= var0 * var31;
              var0 = 0;
            }

            var5 <<= 14;
            if (var2 < 0) {
              var5 -= var27 * var2;
              var2 = 0;
            }

            var41 = var0 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if (var0 != var2 && var28 < var26 || var0 == var2 && var27 > var26) {
              var1 -= var2;
              var2 -= var0;
              var0 = anIntArray787[var0];

              while (true) {
                --var2;
                if (var2 < 0) {
                  while (true) {
                    --var1;
                    if (var1 < 0) {
                      return;
                    }

                    applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var27;
                    var3 += var26;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                var4 += var28;
                var3 += var26;
                var6 += var31;
                var0 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var1 -= var2;
            var2 -= var0;
            var0 = anIntArray787[var0];

            while (true) {
              --var2;
              if (var2 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var27;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
              var4 += var28;
              var3 += var26;
              var6 += var31;
              var0 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
        } else if (var1 <= var2) {
          if (var1 < anInt702) {
            if (var2 > anInt702) {
              var2 = anInt702;
            }

            if (var0 > anInt702) {
              var0 = anInt702;
            }

            var7 = var30 + ((var7 << 9) - var30 * var4);
            if (var2 < var0) {
              var3 = var4 <<= 14;
              if (var1 < 0) {
                var3 -= var26 * var1;
                var4 -= var27 * var1;
                var7 -= var31 * var1;
                var1 = 0;
              }

              var5 <<= 14;
              if (var2 < 0) {
                var5 -= var28 * var2;
                var2 = 0;
              }

              var41 = var1 - anInt366;
              var32 += var34 * var41;
              var35 += var37 * var41;
              var38 += var40 * var41;
              if ((var2 == var1 || var26 >= var27) && (var2 != var1 || var26 <= var28)) {
                var0 -= var2;
                var2 -= var1;
                var1 = anIntArray787[var1];

                while (true) {
                  --var2;
                  if (var2 < 0) {
                    while (true) {
                      --var0;
                      if (var0 < 0) {
                        return;
                      }

                      applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                      var3 += var26;
                      var5 += var28;
                      var7 += var31;
                      var1 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var26;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }
              var0 -= var2;
              var2 -= var1;
              var1 = anIntArray787[var1];

              while (true) {
                --var2;
                if (var2 < 0) {
                  while (true) {
                    --var0;
                    if (var0 < 0) {
                      return;
                    }

                    applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var26;
                    var5 += var28;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                var3 += var26;
                var4 += var27;
                var7 += var31;
                var1 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var5 = var4 <<= 14;
            if (var1 < 0) {
              var5 -= var26 * var1;
              var4 -= var27 * var1;
              var7 -= var31 * var1;
              var1 = 0;
            }

            var3 <<= 14;
            if (var0 < 0) {
              var3 -= var0 * var28;
              var0 = 0;
            }

            var41 = var1 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if (var26 < var27) {
              var2 -= var0;
              var0 -= var1;
              var1 = anIntArray787[var1];

              while (true) {
                --var0;
                if (var0 < 0) {
                  while (true) {
                    --var2;
                    if (var2 < 0) {
                      return;
                    }

                    applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var28;
                    var4 += var27;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                var5 += var26;
                var4 += var27;
                var7 += var31;
                var1 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var2 -= var0;
            var0 -= var1;
            var1 = anIntArray787[var1];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var2;
                  if (var2 < 0) {
                    return;
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var28;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
              var5 += var26;
              var4 += var27;
              var7 += var31;
              var1 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
        } else if (var2 < anInt702) {
          if (var0 > anInt702) {
            var0 = anInt702;
          }

          if (var1 > anInt702) {
            var1 = anInt702;
          }

          var8 = (var8 << 9) - var5 * var30 + var30;
          if (var0 < var1) {
            var4 = var5 <<= 14;
            if (var2 < 0) {
              var4 -= var27 * var2;
              var5 -= var28 * var2;
              var8 -= var31 * var2;
              var2 = 0;
            }

            var3 <<= 14;
            if (var0 < 0) {
              var3 -= var0 * var26;
              var0 = 0;
            }

            var41 = var2 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if (var27 < var28) {
              var1 -= var0;
              var0 -= var2;
              var2 = anIntArray787[var2];

              while (true) {
                --var0;
                if (var0 < 0) {
                  while (true) {
                    --var1;
                    if (var1 < 0) {
                      return;
                    }

                    applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                    var4 += var27;
                    var3 += var26;
                    var8 += var31;
                    var2 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var27;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var1 -= var0;
            var0 -= var2;
            var2 = anIntArray787[var2];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var27;
                  var3 += var26;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
              var4 += var27;
              var5 += var28;
              var8 += var31;
              var2 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
          var3 = var5 <<= 14;
          if (var2 < 0) {
            var3 -= var27 * var2;
            var5 -= var28 * var2;
            var8 -= var31 * var2;
            var2 = 0;
          }

          var4 <<= 14;
          if (var1 < 0) {
            var4 -= var26 * var1;
            var1 = 0;
          }

          var41 = var2 - anInt366;
          var32 += var34 * var41;
          var35 += var37 * var41;
          var38 += var40 * var41;
          if (var27 < var28) {
            var0 -= var1;
            var1 -= var2;
            var2 = anIntArray787[var2];

            while (true) {
              --var1;
              if (var1 < 0) {
                while (true) {
                  --var0;
                  if (var0 < 0) {
                    return;
                  }

                  applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var26;
                  var5 += var28;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
              var3 += var27;
              var5 += var28;
              var8 += var31;
              var2 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
          var0 -= var1;
          var1 -= var2;
          var2 = anIntArray787[var2];

          while (true) {
            --var1;
            if (var1 < 0) {
              while (true) {
                --var0;
                if (var0 < 0) {
                  return;
                }

                applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var26;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }

            applyTextureToPixels(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
            var3 += var27;
            var5 += var28;
            var8 += var31;
            var2 += JagGraphics.drawingAreaWidth;
            var32 += var34;
            var35 += var37;
            var38 += var40;
          }
        }
      }
    }
  }

  public static void applyColorToPixels(int[] var0, int startIndex, int color, int start, int end) {
    if (drawingOffscreen) {
      if (end > anInt696) {
        end = anInt696;
      }

      if (start < 0) {
        start = 0;
      }
    }

    if (start < end) {
      startIndex += start;
      int var3 = end - start >> 2;
      if (alpha != 0) {
        if (alpha == 254) {
          while (true) {
            --var3;
            if (var3 < 0) {
              var3 = end - start & 3;

              while (true) {
                --var3;
                if (var3 < 0) {
                  return;
                }

                var0[startIndex++] = var0[startIndex];
              }
            }

            var0[startIndex++] = var0[startIndex];
            var0[startIndex++] = var0[startIndex];
            var0[startIndex++] = var0[startIndex];
            var0[startIndex++] = var0[startIndex];
          }
        }
        int var6 = alpha;
        int var7 = 256 - alpha;
        color = (var7 * (color & 65280) >> 8 & 65280) + (var7 * (color & 16711935) >> 8 & 16711935);

        while (true) {
          --var3;
          int var8;
          if (var3 < 0) {
            var3 = end - start & 3;

            while (true) {
              --var3;
              if (var3 < 0) {
                return;
              }

              var8 = var0[startIndex];
              var0[startIndex++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
            }
          }

          var8 = var0[startIndex];
          var0[startIndex++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[startIndex];
          var0[startIndex++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[startIndex];
          var0[startIndex++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[startIndex];
          var0[startIndex++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
        }
      }
      while (true) {
        --var3;
        if (var3 < 0) {
          var3 = end - start & 3;

          while (true) {
            --var3;
            if (var3 < 0) {
              return;
            }

            var0[startIndex++] = color;
          }
        }

        var0[startIndex++] = color;
        var0[startIndex++] = color;
        var0[startIndex++] = color;
        var0[startIndex++] = color;
      }
    }
  }

  public static void method23() {
    anInt386 = anInt696 / 2;
    anInt366 = anInt702 / 2;
    anInt788 = -anInt386;
    anInt666 = anInt696 - anInt386;
    anInt791 = -anInt366;
    anInt563 = anInt702 - anInt366;
  }

  public static int adjustColorIntensity(int color, int intensity) {
    int red = (color & 127) * intensity >> 7;
    if (red < 2) {
      red = 2;
    } else if (red > 126) {
      red = 126;
    }

    return (color & 65408) + red;
  }

  public static int method629(int var0, int var1, int var2, int var3) {
    return var0 * var2 + var3 * var1 >> 16;
  }

  public static void drawPixels(int[] pixels, int startIndex, int endIndex, int yOffset, int width, int color) {
    if (drawingOffscreen) {
      if (yOffset > anInt696) {
        yOffset = anInt696;
      }

      if (endIndex < 0) {
        endIndex = 0;
      }
    }

    if (endIndex < yOffset) {
      startIndex += endIndex;
      width += endIndex * color;
      int var8;
      int var9;
      int var10;
      int var3;
      int var2;
      if (aBoolean789) {
        var3 = yOffset - endIndex >> 2;
        color <<= 2;
        if (alpha == 0) {
          if (var3 > 0) {
            do {
              var2 = COLOR_PALETTE[width >> 8];
              width += color;
              pixels[startIndex++] = var2;
              pixels[startIndex++] = var2;
              pixels[startIndex++] = var2;
              pixels[startIndex++] = var2;
              --var3;
            } while (var3 > 0);
          }

          var3 = yOffset - endIndex & 3;
          if (var3 > 0) {
            var2 = COLOR_PALETTE[width >> 8];

            do {
              pixels[startIndex++] = var2;
              --var3;
            } while (var3 > 0);
          }
        } else {
          var8 = alpha;
          var9 = 256 - alpha;
          if (var3 > 0) {
            do {
              var2 = COLOR_PALETTE[width >> 8];
              width += color;
              var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);
              var10 = pixels[startIndex];
              pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = pixels[startIndex];
              pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = pixels[startIndex];
              pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = pixels[startIndex];
              pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              --var3;
            } while (var3 > 0);
          }

          var3 = yOffset - endIndex & 3;
          if (var3 > 0) {
            var2 = COLOR_PALETTE[width >> 8];
            var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);

            do {
              var10 = pixels[startIndex];
              pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              --var3;
            } while (var3 > 0);
          }
        }

      } else {
        var3 = yOffset - endIndex;
        if (alpha == 0) {
          do {
            pixels[startIndex++] = COLOR_PALETTE[width >> 8];
            width += color;
            --var3;
          } while (var3 > 0);
        } else {
          var8 = alpha;
          var9 = 256 - alpha;

          do {
            var2 = COLOR_PALETTE[width >> 8];
            width += color;
            var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);
            var10 = pixels[startIndex];
            pixels[startIndex++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
            --var3;
          } while (var3 > 0);
        }
      }
    }
  }

  public static int method621(int var0, int var1, int var2, int var3) {
    return var2 * var1 - var3 * var0 >> 16;
  }

  public static void drawAndShadeTexturedTriangle(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
    int[] var19 = materialProvider.pixels(var18);
    int var20;
    if (var19 == null) {
      var20 = materialProvider.rgb(var18);
      rasterizeTriangleWithShading(var0, var1, var2, var3, var4, var5, adjustColorIntensity(var20, var6), adjustColorIntensity(var20, var7), adjustColorIntensity(var20, var8));
    } else {
      lowDetail = materialProvider.isLowDetail();
      aBoolean786 = materialProvider.method1423(var18);
      var20 = var4 - var3;
      int var21 = var1 - var0;
      int var22 = var5 - var3;
      int var23 = var2 - var0;
      int var24 = var7 - var6;
      int var25 = var8 - var6;
      int var26 = 0;
      if (var0 != var1) {
        var26 = (var4 - var3 << 14) / (var1 - var0);
      }

      int var27 = 0;
      if (var2 != var1) {
        var27 = (var5 - var4 << 14) / (var2 - var1);
      }

      int var28 = 0;
      if (var0 != var2) {
        var28 = (var3 - var5 << 14) / (var0 - var2);
      }

      int var29 = var20 * var23 - var22 * var21;
      if (var29 != 0) {
        int var30 = (var24 * var23 - var25 * var21 << 9) / var29;
        int var31 = (var25 * var20 - var24 * var22 << 9) / var29;
        var10 = var9 - var10;
        var13 = var12 - var13;
        var16 = var15 - var16;
        var11 -= var9;
        var14 -= var12;
        var17 -= var15;
        int var32 = var11 * var12 - var9 * var14 << 14;
        int var33 = (int) ((((long) var15 * var14 - (long) var17 * var12) << 14) / (long) scale);
        int var34 = (int) ((((long) var17 * var9 - (long) var11 * var15) << 14) / (long) scale);
        int var35 = var10 * var12 - var13 * var9 << 14;
        int var36 = (int) ((((long) var13 * var15 - (long) var16 * var12) << 14) / (long) scale);
        int var37 = (int) ((((long) var16 * var9 - (long) var10 * var15) << 14) / (long) scale);
        int var38 = var13 * var11 - var10 * var14 << 14;
        int var39 = (int) ((((long) var16 * var14 - (long) var13 * var17) << 14) / (long) scale);
        int var40 = (int) ((((long) var17 * var10 - (long) var11 * var16) << 14) / (long) scale);
        int var41;
        if (var0 <= var1 && var0 <= var2) {
          if (var0 < anInt702) {
            if (var1 > anInt702) {
              var1 = anInt702;
            }

            if (var2 > anInt702) {
              var2 = anInt702;
            }

            var6 = var30 + ((var6 << 9) - var3 * var30);
            if (var1 < var2) {
              var5 = var3 <<= 14;
              if (var0 < 0) {
                var5 -= var0 * var28;
                var3 -= var0 * var26;
                var6 -= var0 * var31;
                var0 = 0;
              }

              var4 <<= 14;
              if (var1 < 0) {
                var4 -= var27 * var1;
                var1 = 0;
              }

              var41 = var0 - anInt366;
              var32 += var34 * var41;
              var35 += var37 * var41;
              var38 += var40 * var41;
              if (var0 != var1 && var28 < var26 || var0 == var1 && var28 > var27) {
                var2 -= var1;
                var1 -= var0;
                var0 = anIntArray787[var0];

                while (true) {
                  --var1;
                  if (var1 < 0) {
                    while (true) {
                      --var2;
                      if (var2 < 0) {
                        return;
                      }

                      renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                      var5 += var28;
                      var4 += var27;
                      var6 += var31;
                      var0 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var28;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }
              var2 -= var1;
              var1 -= var0;
              var0 = anIntArray787[var0];

              while (true) {
                --var1;
                if (var1 < 0) {
                  while (true) {
                    --var2;
                    if (var2 < 0) {
                      return;
                    }

                    renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var28;
                    var4 += var27;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                var5 += var28;
                var3 += var26;
                var6 += var31;
                var0 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var4 = var3 <<= 14;
            if (var0 < 0) {
              var4 -= var0 * var28;
              var3 -= var0 * var26;
              var6 -= var0 * var31;
              var0 = 0;
            }

            var5 <<= 14;
            if (var2 < 0) {
              var5 -= var27 * var2;
              var2 = 0;
            }

            var41 = var0 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if ((var0 == var2 || var28 >= var26) && (var0 != var2 || var27 <= var26)) {
              var1 -= var2;
              var2 -= var0;
              var0 = anIntArray787[var0];

              while (true) {
                --var2;
                if (var2 < 0) {
                  while (true) {
                    --var1;
                    if (var1 < 0) {
                      return;
                    }

                    renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var27;
                    var3 += var26;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                var4 += var28;
                var3 += var26;
                var6 += var31;
                var0 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var1 -= var2;
            var2 -= var0;
            var0 = anIntArray787[var0];

            while (true) {
              --var2;
              if (var2 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var27;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
              var4 += var28;
              var3 += var26;
              var6 += var31;
              var0 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
        } else if (var1 <= var2) {
          if (var1 < anInt702) {
            if (var2 > anInt702) {
              var2 = anInt702;
            }

            if (var0 > anInt702) {
              var0 = anInt702;
            }

            var7 = var30 + ((var7 << 9) - var30 * var4);
            if (var2 < var0) {
              var3 = var4 <<= 14;
              if (var1 < 0) {
                var3 -= var26 * var1;
                var4 -= var27 * var1;
                var7 -= var31 * var1;
                var1 = 0;
              }

              var5 <<= 14;
              if (var2 < 0) {
                var5 -= var28 * var2;
                var2 = 0;
              }

              var41 = var1 - anInt366;
              var32 += var34 * var41;
              var35 += var37 * var41;
              var38 += var40 * var41;
              if (var2 != var1 && var26 < var27 || var2 == var1 && var26 > var28) {
                var0 -= var2;
                var2 -= var1;
                var1 = anIntArray787[var1];

                while (true) {
                  --var2;
                  if (var2 < 0) {
                    while (true) {
                      --var0;
                      if (var0 < 0) {
                        return;
                      }

                      renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                      var3 += var26;
                      var5 += var28;
                      var7 += var31;
                      var1 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var26;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }
              var0 -= var2;
              var2 -= var1;
              var1 = anIntArray787[var1];

              while (true) {
                --var2;
                if (var2 < 0) {
                  while (true) {
                    --var0;
                    if (var0 < 0) {
                      return;
                    }

                    renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var26;
                    var5 += var28;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                var3 += var26;
                var4 += var27;
                var7 += var31;
                var1 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var5 = var4 <<= 14;
            if (var1 < 0) {
              var5 -= var26 * var1;
              var4 -= var27 * var1;
              var7 -= var31 * var1;
              var1 = 0;
            }

            var3 <<= 14;
            if (var0 < 0) {
              var3 -= var0 * var28;
              var0 = 0;
            }

            var41 = var1 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if (var26 < var27) {
              var2 -= var0;
              var0 -= var1;
              var1 = anIntArray787[var1];

              while (true) {
                --var0;
                if (var0 < 0) {
                  while (true) {
                    --var2;
                    if (var2 < 0) {
                      return;
                    }

                    renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var28;
                    var4 += var27;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                var5 += var26;
                var4 += var27;
                var7 += var31;
                var1 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var2 -= var0;
            var0 -= var1;
            var1 = anIntArray787[var1];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var2;
                  if (var2 < 0) {
                    return;
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var28;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
              var5 += var26;
              var4 += var27;
              var7 += var31;
              var1 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
        } else if (var2 < anInt702) {
          if (var0 > anInt702) {
            var0 = anInt702;
          }

          if (var1 > anInt702) {
            var1 = anInt702;
          }

          var8 = (var8 << 9) - var5 * var30 + var30;
          if (var0 < var1) {
            var4 = var5 <<= 14;
            if (var2 < 0) {
              var4 -= var27 * var2;
              var5 -= var28 * var2;
              var8 -= var31 * var2;
              var2 = 0;
            }

            var3 <<= 14;
            if (var0 < 0) {
              var3 -= var0 * var26;
              var0 = 0;
            }

            var41 = var2 - anInt366;
            var32 += var34 * var41;
            var35 += var37 * var41;
            var38 += var40 * var41;
            if (var27 < var28) {
              var1 -= var0;
              var0 -= var2;
              var2 = anIntArray787[var2];

              while (true) {
                --var0;
                if (var0 < 0) {
                  while (true) {
                    --var1;
                    if (var1 < 0) {
                      return;
                    }

                    renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                    var4 += var27;
                    var3 += var26;
                    var8 += var31;
                    var2 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var27;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }
            var1 -= var0;
            var0 -= var2;
            var2 = anIntArray787[var2];

            while (true) {
              --var0;
              if (var0 < 0) {
                while (true) {
                  --var1;
                  if (var1 < 0) {
                    return;
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var27;
                  var3 += var26;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
              var4 += var27;
              var5 += var28;
              var8 += var31;
              var2 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
          var3 = var5 <<= 14;
          if (var2 < 0) {
            var3 -= var27 * var2;
            var5 -= var28 * var2;
            var8 -= var31 * var2;
            var2 = 0;
          }

          var4 <<= 14;
          if (var1 < 0) {
            var4 -= var26 * var1;
            var1 = 0;
          }

          var41 = var2 - anInt366;
          var32 += var34 * var41;
          var35 += var37 * var41;
          var38 += var40 * var41;
          if (var27 < var28) {
            var0 -= var1;
            var1 -= var2;
            var2 = anIntArray787[var2];

            while (true) {
              --var1;
              if (var1 < 0) {
                while (true) {
                  --var0;
                  if (var0 < 0) {
                    return;
                  }

                  renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var26;
                  var5 += var28;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
              var3 += var27;
              var5 += var28;
              var8 += var31;
              var2 += JagGraphics.drawingAreaWidth;
              var32 += var34;
              var35 += var37;
              var38 += var40;
            }
          }
          var0 -= var1;
          var1 -= var2;
          var2 = anIntArray787[var2];

          while (true) {
            --var1;
            if (var1 < 0) {
              while (true) {
                --var0;
                if (var0 < 0) {
                  return;
                }

                renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var26;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }

            renderPixelsWithTexture(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
            var3 += var27;
            var5 += var28;
            var8 += var31;
            var2 += JagGraphics.drawingAreaWidth;
            var32 += var34;
            var35 += var37;
            var38 += var40;
          }
        }
      }
    }
  }

  public static int method623(int var0, int var1, int var2, int var3) {
    return var0 * var2 - var3 * var1 >> 16;
  }

  public static int method630(int var0, int var1, int var2, int var3) {
    return var3 * var0 + var2 * var1 >> 16;
  }

  public static void generateColorPalette(double var0) {
    generateColorPallette(var0);
  }

  public static void setMaterialProvider(MaterialProvider var0) {
    materialProvider = var0;
  }

  public static void generateColorPallette(double var0) {
    int var4 = 0;

    for (int var5 = 0; var5 < 512; ++var5) {
      double var6 = (double) (var5 >> 3) / 64.0D + 0.0078125D;
      double var8 = (double) (var5 & 7) / 8.0D + 0.0625D;

      for (int var10 = 0; var10 < 128; ++var10) {
        double var11 = (double) var10 / 128.0D;
        double var13 = var11;
        double var15 = var11;
        double var17 = var11;
        if (var8 != 0.0D) {
          double var19;
          if (var11 < 0.5D) {
            var19 = var11 * (1.0D + var8);
          } else {
            var19 = var11 + var8 - var11 * var8;
          }

          double var21 = 2.0D * var11 - var19;
          double var23 = var6 + 0.3333333333333333D;
          if (var23 > 1.0D) {
            --var23;
          }

          double var27 = var6 - 0.3333333333333333D;
          if (var27 < 0.0D) {
            ++var27;
          }

          if (6.0D * var23 < 1.0D) {
            var13 = var21 + (var19 - var21) * 6.0D * var23;
          } else if (2.0D * var23 < 1.0D) {
            var13 = var19;
          } else if (3.0D * var23 < 2.0D) {
            var13 = var21 + (var19 - var21) * (0.6666666666666666D - var23) * 6.0D;
          } else {
            var13 = var21;
          }

          if (6.0D * var6 < 1.0D) {
            var15 = var21 + (var19 - var21) * 6.0D * var6;
          } else if (2.0D * var6 < 1.0D) {
            var15 = var19;
          } else if (3.0D * var6 < 2.0D) {
            var15 = var21 + (var19 - var21) * (0.6666666666666666D - var6) * 6.0D;
          } else {
            var15 = var21;
          }

          if (6.0D * var27 < 1.0D) {
            var17 = var21 + (var19 - var21) * 6.0D * var27;
          } else if (2.0D * var27 < 1.0D) {
            var17 = var19;
          } else if (3.0D * var27 < 2.0D) {
            var17 = var21 + (var19 - var21) * (0.6666666666666666D - var27) * 6.0D;
          } else {
            var17 = var21;
          }
        }

        int var29 = (int) (var13 * 256.0D);
        int var30 = (int) (var15 * 256.0D);
        int var31 = (int) (var17 * 256.0D);
        int var32 = var31 + (var30 << 8) + (var29 << 16);
        var32 = rgbPow(var32, var0);
        if (var32 == 0) {
          var32 = 1;
        }

        COLOR_PALETTE[var4++] = var32;
      }
    }
  }

  public static void applyTextureToPixels(int[] outputPixels, int[] texturePixels, int outputIndex, int textureIndex, int start, int end, int step, int textureStep, int textureOffset, int textureScale, int textureDelta, int deltaStep, int color) {
    if (drawingOffscreen) {
      if (end > anInt696) {
        end = anInt696;
      }
      if (start < 0) {
        start = 0;
      }
    }

    if (start < end) {
      outputIndex += start;
      int textureSteps = end - start;
      int textureIncrement;
      if (lowDetail) {
        int textureOffsetDelta = start - anInt386;
        textureScale += (textureDelta >> 3) * textureOffsetDelta;
        deltaStep += (textureDelta >> 3) * textureOffsetDelta;
        textureDelta += textureSteps * (textureDelta >> 3);
        int scaledTextureValue = textureDelta >> 14;

        if (scaledTextureValue != 0) {
          textureStep = textureScale / scaledTextureValue;
          textureOffset = deltaStep / scaledTextureValue;

          if (textureStep < 0) {
            textureStep = 0;
          } else if (textureStep > 4032) {
            textureStep = 4032;
          }
        } else {
          textureStep = 0;
          textureOffset = 0;
        }

        textureScale += textureDelta;
        deltaStep += textureDelta;
        textureDelta = textureStep;
        int textureValue1 = (textureOffset << 20) + textureScale;
        textureIncrement = (textureOffset - textureScale >> 3) + (textureStep - textureOffset >> 3 << 20);
        textureSteps >>= 3;
        int textureOffset1;

        while (textureSteps > 0) {
          int textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);

          textureOffset1 = textureStep;
          textureScale += textureDelta;
          deltaStep += textureDelta;
          textureOffset += textureScale / scaledTextureValue;
          textureStep = deltaStep / scaledTextureValue;

          if (textureStep < 0) {
            textureStep = 0;
          } else if (textureStep > 4032) {
            textureStep = 4032;
          }

          textureValue1 = (textureOffset << 20) + textureScale;
          textureIncrement = (textureStep - textureOffset1 >> 3) + (textureOffset - textureStep >> 3 << 20);
          textureSteps--;
        }

        textureSteps = end - start & 7;

        if (textureSteps > 0) {
          int textureColor;
          while (textureSteps > 0) {
            textureColor = texturePixels[(textureValue1 >>> 26) + (textureValue1 & 4032)];
            outputPixels[outputIndex++] = applyAlpha(color, textureColor);
            textureValue1 += textureIncrement;
            textureSteps--;
          }
        }
      } else {
        int textureOffsetDelta = start - anInt386;
        textureScale += (textureDelta >> 3) * textureOffsetDelta;
        deltaStep += (textureDelta >> 3) * textureOffsetDelta;
        textureDelta += textureSteps * (textureDelta >> 3);
        int scaledTextureValue = textureDelta >> 14;

        if (scaledTextureValue != 0) {
          textureStep = textureScale / scaledTextureValue;
          textureOffset = deltaStep / scaledTextureValue;

          if (textureStep < 0) {
            textureStep = 0;
          } else if (textureStep > 16256) {
            textureStep = 16256;
          }
        } else {
          textureStep = 0;
          textureOffset = 0;
        }

        textureScale += textureDelta;
        deltaStep += textureDelta;
        textureDelta = textureStep;
        int textureValue1 = (textureOffset << 18) + textureScale;
        textureIncrement = (textureStep - textureOffset >> 3) + (textureOffset - textureStep >> 3 << 18);
        textureSteps >>= 3;
        int textureOffset1;
        while (textureSteps > 0) {
          int textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);
          textureValue1 += textureIncrement;
          textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
          outputPixels[outputIndex++] = applyAlpha(color, textureColor);

          textureOffset1 = textureStep;
          textureScale += textureDelta;
          deltaStep += textureDelta;
          textureOffset += textureScale / scaledTextureValue;
          textureStep = deltaStep / scaledTextureValue;

          if (textureStep < 0) {
            textureStep = 0;
          } else if (textureStep > 16256) {
            textureStep = 16256;
          }

          textureValue1 = (textureOffset << 18) + textureScale;
          textureIncrement = (textureStep - textureOffset1 >> 3) + (textureOffset - textureStep >> 3 << 18);
          textureSteps--;
        }

        textureSteps = end - start & 7;

        if (textureSteps > 0) {
          int textureColor;
          while (textureSteps > 0) {
            textureColor = texturePixels[(textureValue1 & 16256) + (textureValue1 >>> 25)];
            outputPixels[outputIndex++] = applyAlpha(color, textureColor);
            textureValue1 += textureIncrement;
            textureSteps--;
          }
        }
      }
    }
  }

  private static int applyAlpha(int color, int textureColor) {
    int alpha = (textureColor >> 24 & 0xFF);
    int red = (textureColor >> 16 & 0xFF);
    int green = (textureColor >> 8 & 0xFF);
    int blue = (textureColor & 0xFF);

    alpha = alpha * (color >> 24 & 0xFF) >> 8;
    red = red * (color >> 16 & 0xFF) >> 8;
    green = green * (color >> 8 & 0xFF) >> 8;
    blue = blue * (color & 0xFF) >> 8;

    return (alpha << 24) | (red << 16) | (green << 8) | blue;
  }

  public static void renderPixelsWithTexture(int[] var0, int[] var1, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
    if (drawingOffscreen) {
      if (var6 > anInt696) {
        var6 = anInt696;
      }

      if (var5 < 0) {
        var5 = 0;
      }
    }

    if (var5 < var6) {
      var4 += var5;
      var7 += var5 * var8;
      int var15 = var6 - var5;
      int var16;
      int var17;
      int var18;
      int var19;
      int var20;
      int var21;
      int var22;
      int var23;
      int var3;
      int var2;
      if (lowDetail) {
        var16 = var5 - anInt386;
        var9 += var16 * var12;
        var10 += var13 * var16;
        var11 += var16 * var14;
        var17 = var11 >> 12;
        if (var17 != 0) {
          var18 = var9 / var17;
          var19 = var10 / var17;
        } else {
          var18 = 0;
          var19 = 0;
        }

        var9 += var15 * var12;
        var10 += var13 * var15;
        var11 += var15 * var14;
        var17 = var11 >> 12;
        if (var17 != 0) {
          var20 = var9 / var17;
          var21 = var10 / var17;
        } else {
          var20 = 0;
          var21 = 0;
        }

        var2 = (var18 << 20) + var19;
        var22 = (var21 - var19) / var15 + ((var20 - var18) / var15 << 20);
        var15 >>= 3;
        var8 <<= 3;
        var23 = var7 >> 8;
        if (aBoolean786) {
          if (var15 > 0) {
            do {
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var7 += var8;
              var23 = var7 >> 8;
              --var15;
            } while (var15 > 0);
          }

          var15 = var6 - var5 & 7;
          if (var15 > 0) {
            do {
              var3 = var1[(var2 >>> 26) + (var2 & 4032)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              --var15;
            } while (var15 > 0);
          }
        } else {
          if (var15 > 0) {
            do {
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              var7 += var8;
              var23 = var7 >> 8;
              --var15;
            } while (var15 > 0);
          }

          var15 = var6 - var5 & 7;
          if (var15 > 0) {
            do {
              if ((var3 = var1[(var2 >>> 26) + (var2 & 4032)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              --var15;
            } while (var15 > 0);
          }
        }
      } else {
        var16 = var5 - anInt386;
        var9 += var16 * var12;
        var10 += var13 * var16;
        var11 += var16 * var14;
        var17 = var11 >> 14;
        if (var17 != 0) {
          var18 = var9 / var17;
          var19 = var10 / var17;
        } else {
          var18 = 0;
          var19 = 0;
        }

        var9 += var15 * var12;
        var10 += var13 * var15;
        var11 += var15 * var14;
        var17 = var11 >> 14;
        if (var17 != 0) {
          var20 = var9 / var17;
          var21 = var10 / var17;
        } else {
          var20 = 0;
          var21 = 0;
        }

        var2 = (var18 << 18) + var19;
        var22 = (var21 - var19) / var15 + ((var20 - var18) / var15 << 18);
        var15 >>= 3;
        var8 <<= 3;
        var23 = var7 >> 8;
        if (aBoolean786) {
          if (var15 > 0) {
            do {
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              var7 += var8;
              var23 = var7 >> 8;
              --var15;
            } while (var15 > 0);
          }

          var15 = var6 - var5 & 7;
          if (var15 > 0) {
            do {
              var3 = var1[(var2 & 16256) + (var2 >>> 25)];
              var0[var4++] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              var2 += var22;
              --var15;
            } while (var15 > 0);
          }
        } else {
          if (var15 > 0) {
            do {
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              var7 += var8;
              var23 = var7 >> 8;
              --var15;
            } while (var15 > 0);
          }

          var15 = var6 - var5 & 7;
          if (var15 > 0) {
            do {
              if ((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
                var0[var4] = (var23 * (var3 & 65280) & 16711680) + ((var3 & 16711935) * var23 & -16711936) >> 8;
              }

              ++var4;
              var2 += var22;
              --var15;
            } while (var15 > 0);
          }
        }
      }
    }
  }

  public static void method639(int var0, int var1, int var2) {
    drawingOffscreen = var0 < 0 || var0 > anInt696 || var1 < 0 || var1 > anInt696 || var2 < 0 || var2 > anInt696;
  }
}
