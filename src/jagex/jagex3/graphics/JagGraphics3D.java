package jagex.jagex3.graphics;

public class JagGraphics3D extends JagGraphics {

  public static final int[] SIN_TABLE = new int[2048];
  public static final int[] COS_TABLE = new int[2048];
  public static final int[] COLOR_PALETTE = new int[65536];
  public static final int[] anIntArray781 = new int[512];
  public static final int[] anIntArray785 = new int[2048];

  public static int[] anIntArray787 = new int[1024];

  public static MaterialProvider materialProvider;

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

  static {
    for (int i = 1; i < 512; ++i) {
      anIntArray781[i] = 32768 / i;
    }

    for (int i = 1; i < 2048; ++i) {
      anIntArray785[i] = 65536 / i;
    }

    for (int i = 0; i < 2048; ++i) {
      SIN_TABLE[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
      COS_TABLE[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
    }
  }

  public static int method625(int var0, int var1, int var2, int var3) {
    return var0 * var2 + var3 * var1 >> 16;
  }

  public static int method628(int var0, int var1, int var2, int var3) {
    return var2 * var1 - var3 * var0 >> 16;
  }

  public static int method631(int var0, double var1) {
    double var3 = (double) (var0 >> 16) / 256.0D;
    double var5 = (double) (var0 >> 8 & 255) / 256.0D;
    double var7 = (double) (var0 & 255) / 256.0D;
    var3 = Math.pow(var3, var1);
    var5 = Math.pow(var5, var1);
    var7 = Math.pow(var7, var1);
    int var9 = (int) (var3 * 256.0D);
    int var10 = (int) (var5 * 256.0D);
    int var11 = (int) (var7 * 256.0D);
    return var11 + (var10 << 8) + (var9 << 16);
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

                  method633(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var4 >> 14);
                  var5 += var9;
                  var4 += var8;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              method633(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var3 >> 14);
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

                method633(JagGraphics.drawingAreaPixels, var0, color, var4 >> 14, var5 >> 14);
                var5 += var9;
                var4 += var8;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            method633(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var5 >> 14);
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

                method633(JagGraphics.drawingAreaPixels, var0, color, var5 >> 14, var3 >> 14);
                var5 += var8;
                var3 += var7;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            method633(JagGraphics.drawingAreaPixels, var0, color, var4 >> 14, var3 >> 14);
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

              method633(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var5 >> 14);
              var5 += var8;
              var3 += var7;
              var0 += JagGraphics.drawingAreaWidth;
            }
          }

          method633(JagGraphics.drawingAreaPixels, var0, color, var3 >> 14, var4 >> 14);
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

                  method633(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var5 >> 14);
                  var3 += var7;
                  var5 += var9;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              method633(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var4 >> 14);
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

                method633(JagGraphics.drawingAreaPixels, var1, color, var5 >> 14, var3 >> 14);
                var3 += var7;
                var5 += var9;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            method633(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var3 >> 14);
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

                method633(JagGraphics.drawingAreaPixels, var1, color, var3 >> 14, var4 >> 14);
                var3 += var9;
                var4 += var8;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            method633(JagGraphics.drawingAreaPixels, var1, color, var5 >> 14, var4 >> 14);
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

              method633(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var3 >> 14);
              var3 += var9;
              var4 += var8;
              var1 += JagGraphics.drawingAreaWidth;
            }
          }

          method633(JagGraphics.drawingAreaPixels, var1, color, var4 >> 14, var5 >> 14);
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

                method633(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var3 >> 14);
                var4 += var8;
                var3 += var7;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            method633(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var5 >> 14);
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

              method633(JagGraphics.drawingAreaPixels, var2, color, var3 >> 14, var4 >> 14);
              var4 += var8;
              var3 += var7;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          method633(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var4 >> 14);
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

              method633(JagGraphics.drawingAreaPixels, var2, color, var4 >> 14, var5 >> 14);
              var4 += var7;
              var5 += var9;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          method633(JagGraphics.drawingAreaPixels, var2, color, var3 >> 14, var5 >> 14);
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

            method633(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var4 >> 14);
            var4 += var7;
            var5 += var9;
            var2 += JagGraphics.drawingAreaWidth;
          }
        }

        method633(JagGraphics.drawingAreaPixels, var2, color, var5 >> 14, var3 >> 14);
        var3 += var8;
        var5 += var9;
        var2 += JagGraphics.drawingAreaWidth;
      }
    }
  }

  public static void method627(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
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

                    method622(JagGraphics.drawingAreaPixels, var0, var4 >> 14, var5 >> 14, var6, var19);
                    var5 += var17;
                    var4 += var15;
                    var6 += var20;
                    var0 += JagGraphics.drawingAreaWidth;
                  }
                }

                method622(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var5 >> 14, var6, var19);
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

                  method622(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var4 >> 14, var6, var19);
                  var5 += var17;
                  var4 += var15;
                  var6 += var20;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              method622(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var3 >> 14, var6, var19);
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

                  method622(JagGraphics.drawingAreaPixels, var0, var5 >> 14, var3 >> 14, var6, var19);
                  var5 += var15;
                  var3 += var16;
                  var6 += var20;
                  var0 += JagGraphics.drawingAreaWidth;
                }
              }

              method622(JagGraphics.drawingAreaPixels, var0, var4 >> 14, var3 >> 14, var6, var19);
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

                method622(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var5 >> 14, var6, var19);
                var5 += var15;
                var3 += var16;
                var6 += var20;
                var0 += JagGraphics.drawingAreaWidth;
              }
            }

            method622(JagGraphics.drawingAreaPixels, var0, var3 >> 14, var4 >> 14, var6, var19);
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

                    method622(JagGraphics.drawingAreaPixels, var1, var5 >> 14, var3 >> 14, var7, var19);
                    var3 += var16;
                    var5 += var17;
                    var7 += var20;
                    var1 += JagGraphics.drawingAreaWidth;
                  }
                }

                method622(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var3 >> 14, var7, var19);
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

                  method622(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var5 >> 14, var7, var19);
                  var3 += var16;
                  var5 += var17;
                  var7 += var20;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              method622(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var4 >> 14, var7, var19);
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

                  method622(JagGraphics.drawingAreaPixels, var1, var3 >> 14, var4 >> 14, var7, var19);
                  var3 += var17;
                  var4 += var15;
                  var7 += var20;
                  var1 += JagGraphics.drawingAreaWidth;
                }
              }

              method622(JagGraphics.drawingAreaPixels, var1, var5 >> 14, var4 >> 14, var7, var19);
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

                method622(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var3 >> 14, var7, var19);
                var3 += var17;
                var4 += var15;
                var7 += var20;
                var1 += JagGraphics.drawingAreaWidth;
              }
            }

            method622(JagGraphics.drawingAreaPixels, var1, var4 >> 14, var5 >> 14, var7, var19);
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

                  method622(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var3 >> 14, var8, var19);
                  var4 += var15;
                  var3 += var16;
                  var8 += var20;
                  var2 += JagGraphics.drawingAreaWidth;
                }
              }

              method622(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var5 >> 14, var8, var19);
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

                method622(JagGraphics.drawingAreaPixels, var2, var3 >> 14, var4 >> 14, var8, var19);
                var4 += var15;
                var3 += var16;
                var8 += var20;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            method622(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var4 >> 14, var8, var19);
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

                method622(JagGraphics.drawingAreaPixels, var2, var4 >> 14, var5 >> 14, var8, var19);
                var4 += var16;
                var5 += var17;
                var8 += var20;
                var2 += JagGraphics.drawingAreaWidth;
              }
            }

            method622(JagGraphics.drawingAreaPixels, var2, var3 >> 14, var5 >> 14, var8, var19);
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

              method622(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var4 >> 14, var8, var19);
              var4 += var16;
              var5 += var17;
              var8 += var20;
              var2 += JagGraphics.drawingAreaWidth;
            }
          }

          method622(JagGraphics.drawingAreaPixels, var2, var5 >> 14, var3 >> 14, var8, var19);
          var3 += var15;
          var5 += var17;
          var8 += var20;
          var2 += JagGraphics.drawingAreaWidth;
        }
      }
    }
  }

  public static void method499() {
    method632(JagGraphics.drawingAreaLeft, JagGraphics.drawingAreaTop, JagGraphics.drawingAreaBottom, JagGraphics.drawingAreaRight);
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

  public static void method632(int var0, int var1, int var2, int var3) {
    anInt696 = var2 - var0;
    anInt702 = var3 - var1;
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

    var5 = var0 + JagGraphics.drawingAreaWidth * var1;

    for (var4 = 0; var4 < anInt702; ++var4) {
      anIntArray787[var4] = var5;
      var5 += JagGraphics.drawingAreaWidth;
    }

  }

  public static void method619(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
    int[] var19 = materialProvider.pixels(var18);
    int var20;
    if (var19 == null) {
      var20 = materialProvider.rgb(var18);
      method627(var0, var1, var2, var3, var4, var5, method527(var20, var6), method527(var20, var7), method527(var20, var8));
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
        int var33 = (int) (((long) ((long) var15 * var14 - (long) var17 * var12) << 3 << 14) / (long) scale);
        int var34 = (int) (((long) ((long) var17 * var9 - (long) var11 * var15) << 14) / (long) scale);
        int var35 = var10 * var12 - var13 * var9 << 14;
        int var36 = (int) (((long) ((long) var13 * var15 - (long) var16 * var12) << 3 << 14) / (long) scale);
        int var37 = (int) (((long) ((long) var16 * var9 - (long) var10 * var15) << 14) / (long) scale);
        int var38 = var13 * var11 - var10 * var14 << 14;
        int var39 = (int) (((long) ((long) var16 * var14 - (long) var13 * var17) << 3 << 14) / (long) scale);
        int var40 = (int) (((long) ((long) var17 * var10 - (long) var11 * var16) << 14) / (long) scale);
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

                      method624(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                      var5 += var28;
                      var4 += var27;
                      var6 += var31;
                      var0 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  method624(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                    method624(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var28;
                    var4 += var27;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method624(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                    method624(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var27;
                    var3 += var26;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method624(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                  method624(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var27;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method624(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                      method624(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                      var3 += var26;
                      var5 += var28;
                      var7 += var31;
                      var1 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  method624(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method624(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var26;
                    var5 += var28;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method624(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method624(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var28;
                    var4 += var27;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method624(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                  method624(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var28;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method624(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method624(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                    var4 += var27;
                    var3 += var26;
                    var8 += var31;
                    var2 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method624(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                  method624(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var27;
                  var3 += var26;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method624(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                  method624(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var26;
                  var5 += var28;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method624(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                method624(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var26;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }

            method624(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

  public static void method633(int[] var0, int var1, int color, int var4, int var5) {
    if (drawingOffscreen) {
      if (var5 > anInt696) {
        var5 = anInt696;
      }

      if (var4 < 0) {
        var4 = 0;
      }
    }

    if (var4 < var5) {
      var1 += var4;
      int var3 = var5 - var4 >> 2;
      if (alpha != 0) {
        if (alpha == 254) {
          while (true) {
            --var3;
            if (var3 < 0) {
              var3 = var5 - var4 & 3;

              while (true) {
                --var3;
                if (var3 < 0) {
                  return;
                }

                var0[var1++] = var0[var1];
              }
            }

            var0[var1++] = var0[var1];
            var0[var1++] = var0[var1];
            var0[var1++] = var0[var1];
            var0[var1++] = var0[var1];
          }
        }
        int var6 = alpha;
        int var7 = 256 - alpha;
        color = (var7 * (color & 65280) >> 8 & 65280) + (var7 * (color & 16711935) >> 8 & 16711935);

        while (true) {
          --var3;
          int var8;
          if (var3 < 0) {
            var3 = var5 - var4 & 3;

            while (true) {
              --var3;
              if (var3 < 0) {
                return;
              }

              var8 = var0[var1];
              var0[var1++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
            }
          }

          var8 = var0[var1];
          var0[var1++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[var1];
          var0[var1++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[var1];
          var0[var1++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
          var8 = var0[var1];
          var0[var1++] = ((var8 & 16711935) * var6 >> 8 & 16711935) + color + (var6 * (var8 & 65280) >> 8 & 65280);
        }
      }
      while (true) {
        --var3;
        if (var3 < 0) {
          var3 = var5 - var4 & 3;

          while (true) {
            --var3;
            if (var3 < 0) {
              return;
            }

            var0[var1++] = color;
          }
        }

        var0[var1++] = color;
        var0[var1++] = color;
        var0[var1++] = color;
        var0[var1++] = color;
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

  public static int method527(int var0, int var1) {
    var1 = (var0 & 127) * var1 >> 7;
    if (var1 < 2) {
      var1 = 2;
    } else if (var1 > 126) {
      var1 = 126;
    }

    return (var0 & 65408) + var1;
  }

  public static int method629(int var0, int var1, int var2, int var3) {
    return var0 * var2 + var3 * var1 >> 16;
  }

  public static void method622(int[] var0, int var1, int var4, int var5, int var6, int var7) {
    if (drawingOffscreen) {
      if (var5 > anInt696) {
        var5 = anInt696;
      }

      if (var4 < 0) {
        var4 = 0;
      }
    }

    if (var4 < var5) {
      var1 += var4;
      var6 += var4 * var7;
      int var8;
      int var9;
      int var10;
      int var3;
      int var2;
      if (aBoolean789) {
        var3 = var5 - var4 >> 2;
        var7 <<= 2;
        if (alpha == 0) {
          if (var3 > 0) {
            do {
              var2 = COLOR_PALETTE[var6 >> 8];
              var6 += var7;
              var0[var1++] = var2;
              var0[var1++] = var2;
              var0[var1++] = var2;
              var0[var1++] = var2;
              --var3;
            } while (var3 > 0);
          }

          var3 = var5 - var4 & 3;
          if (var3 > 0) {
            var2 = COLOR_PALETTE[var6 >> 8];

            do {
              var0[var1++] = var2;
              --var3;
            } while (var3 > 0);
          }
        } else {
          var8 = alpha;
          var9 = 256 - alpha;
          if (var3 > 0) {
            do {
              var2 = COLOR_PALETTE[var6 >> 8];
              var6 += var7;
              var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);
              var10 = var0[var1];
              var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = var0[var1];
              var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = var0[var1];
              var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              var10 = var0[var1];
              var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              --var3;
            } while (var3 > 0);
          }

          var3 = var5 - var4 & 3;
          if (var3 > 0) {
            var2 = COLOR_PALETTE[var6 >> 8];
            var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);

            do {
              var10 = var0[var1];
              var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
              --var3;
            } while (var3 > 0);
          }
        }

      } else {
        var3 = var5 - var4;
        if (alpha == 0) {
          do {
            var0[var1++] = COLOR_PALETTE[var6 >> 8];
            var6 += var7;
            --var3;
          } while (var3 > 0);
        } else {
          var8 = alpha;
          var9 = 256 - alpha;

          do {
            var2 = COLOR_PALETTE[var6 >> 8];
            var6 += var7;
            var2 = (var9 * (var2 & 65280) >> 8 & 65280) + (var9 * (var2 & 16711935) >> 8 & 16711935);
            var10 = var0[var1];
            var0[var1++] = ((var10 & 16711935) * var8 >> 8 & 16711935) + var2 + (var8 * (var10 & 65280) >> 8 & 65280);
            --var3;
          } while (var3 > 0);
        }

      }
    }
  }

  public static int method621(int var0, int var1, int var2, int var3) {
    return var2 * var1 - var3 * var0 >> 16;
  }

  public static void method638(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
    int[] var19 = materialProvider.pixels(var18);
    int var20;
    if (var19 == null) {
      var20 = materialProvider.rgb(var18);
      method627(var0, var1, var2, var3, var4, var5, method527(var20, var6), method527(var20, var7), method527(var20, var8));
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
        int var33 = (int) (((long) ((long) var15 * var14 - (long) var17 * var12) << 14) / (long) scale);
        int var34 = (int) (((long) ((long) var17 * var9 - (long) var11 * var15) << 14) / (long) scale);
        int var35 = var10 * var12 - var13 * var9 << 14;
        int var36 = (int) (((long) ((long) var13 * var15 - (long) var16 * var12) << 14) / (long) scale);
        int var37 = (int) (((long) ((long) var16 * var9 - (long) var10 * var15) << 14) / (long) scale);
        int var38 = var13 * var11 - var10 * var14 << 14;
        int var39 = (int) (((long) ((long) var16 * var14 - (long) var13 * var17) << 14) / (long) scale);
        int var40 = (int) (((long) ((long) var17 * var10 - (long) var11 * var16) << 14) / (long) scale);
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

                      method626(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                      var5 += var28;
                      var4 += var27;
                      var6 += var31;
                      var0 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  method626(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                    method626(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var28;
                    var4 += var27;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method626(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                    method626(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var5 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                    var5 += var27;
                    var3 += var26;
                    var6 += var31;
                    var0 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method626(JagGraphics.drawingAreaPixels, var19, var0, var3 >> 14, var4 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                  method626(JagGraphics.drawingAreaPixels, var19, var0, var5 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
                  var5 += var27;
                  var3 += var26;
                  var6 += var31;
                  var0 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method626(JagGraphics.drawingAreaPixels, var19, var0, var4 >> 14, var3 >> 14, var6, var30, var32, var35, var38, var33, var36, var39);
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

                      method626(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                      var3 += var26;
                      var5 += var28;
                      var7 += var31;
                      var1 += JagGraphics.drawingAreaWidth;
                      var32 += var34;
                      var35 += var37;
                      var38 += var40;
                    }
                  }

                  method626(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method626(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var26;
                    var5 += var28;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method626(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method626(JagGraphics.drawingAreaPixels, var19, var1, var3 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                    var3 += var28;
                    var4 += var27;
                    var7 += var31;
                    var1 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method626(JagGraphics.drawingAreaPixels, var19, var1, var5 >> 14, var4 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                  method626(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var3 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
                  var3 += var28;
                  var4 += var27;
                  var7 += var31;
                  var1 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method626(JagGraphics.drawingAreaPixels, var19, var1, var4 >> 14, var5 >> 14, var7, var30, var32, var35, var38, var33, var36, var39);
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

                    method626(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                    var4 += var27;
                    var3 += var26;
                    var8 += var31;
                    var2 += JagGraphics.drawingAreaWidth;
                    var32 += var34;
                    var35 += var37;
                    var38 += var40;
                  }
                }

                method626(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                  method626(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var27;
                  var3 += var26;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method626(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                  method626(JagGraphics.drawingAreaPixels, var19, var2, var4 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                  var4 += var26;
                  var5 += var28;
                  var8 += var31;
                  var2 += JagGraphics.drawingAreaWidth;
                  var32 += var34;
                  var35 += var37;
                  var38 += var40;
                }
              }

              method626(JagGraphics.drawingAreaPixels, var19, var2, var3 >> 14, var5 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

                method626(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var4 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
                var4 += var26;
                var5 += var28;
                var8 += var31;
                var2 += JagGraphics.drawingAreaWidth;
                var32 += var34;
                var35 += var37;
                var38 += var40;
              }
            }

            method626(JagGraphics.drawingAreaPixels, var19, var2, var5 >> 14, var3 >> 14, var8, var30, var32, var35, var38, var33, var36, var39);
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

  public static void method634(double var0) {
    method635(var0);
  }

  public static void method636(MaterialProvider var0) {
    materialProvider = var0;
  }

  public static void method635(double var0) {
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
        var32 = method631(var32, var0);
        if (var32 == 0) {
          var32 = 1;
        }

        COLOR_PALETTE[var4++] = var32;
      }
    }

  }

  public static void method624(int[] var0, int[] var1, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
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
      int var10000;
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
        var9 += var16 * (var12 >> 3);
        var10 += (var13 >> 3) * var16;
        var11 += var16 * (var14 >> 3);
        var17 = var11 >> 12;
        if (var17 != 0) {
          var18 = var9 / var17;
          var19 = var10 / var17;
          if (var18 < 0) {
            var18 = 0;
          } else if (var18 > 4032) {
            var18 = 4032;
          }
        } else {
          var18 = 0;
          var19 = 0;
        }

        var9 += var12;
        var10 += var13;
        var11 += var14;
        var17 = var11 >> 12;
        if (var17 != 0) {
          var20 = var9 / var17;
          var21 = var10 / var17;
          if (var20 < 0) {
            var20 = 0;
          } else if (var20 > 4032) {
            var20 = 4032;
          }
        } else {
          var20 = 0;
          var21 = 0;
        }

        var2 = (var18 << 20) + var19;
        var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 20);
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
              var18 = var20;
              var19 = var21;
              var9 += var12;
              var10 += var13;
              var11 += var14;
              var17 = var11 >> 12;
              if (var17 != 0) {
                var20 = var9 / var17;
                var21 = var10 / var17;
                if (var20 < 0) {
                  var20 = 0;
                } else if (var20 > 4032) {
                  var20 = 4032;
                }
              } else {
                var20 = 0;
                var21 = 0;
              }

              var2 = (var18 << 20) + var19;
              var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 20);
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
              var18 = var20;
              var19 = var21;
              var9 += var12;
              var10 += var13;
              var11 += var14;
              var17 = var11 >> 12;
              if (var17 != 0) {
                var20 = var9 / var17;
                var21 = var10 / var17;
                if (var20 < 0) {
                  var20 = 0;
                } else if (var20 > 4032) {
                  var20 = 4032;
                }
              } else {
                var20 = 0;
                var21 = 0;
              }

              var2 = (var18 << 20) + var19;
              var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 20);
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
        var9 += var16 * (var12 >> 3);
        var10 += (var13 >> 3) * var16;
        var11 += var16 * (var14 >> 3);
        var17 = var11 >> 14;
        if (var17 != 0) {
          var18 = var9 / var17;
          var19 = var10 / var17;
          if (var18 < 0) {
            var18 = 0;
          } else if (var18 > 16256) {
            var18 = 16256;
          }
        } else {
          var18 = 0;
          var19 = 0;
        }

        var9 += var12;
        var10 += var13;
        var11 += var14;
        var17 = var11 >> 14;
        if (var17 != 0) {
          var20 = var9 / var17;
          var21 = var10 / var17;
          if (var20 < 0) {
            var20 = 0;
          } else if (var20 > 16256) {
            var20 = 16256;
          }
        } else {
          var20 = 0;
          var21 = 0;
        }

        var2 = (var18 << 18) + var19;
        var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 18);
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
              var18 = var20;
              var19 = var21;
              var9 += var12;
              var10 += var13;
              var11 += var14;
              var17 = var11 >> 14;
              if (var17 != 0) {
                var20 = var9 / var17;
                var21 = var10 / var17;
                if (var20 < 0) {
                  var20 = 0;
                } else if (var20 > 16256) {
                  var20 = 16256;
                }
              } else {
                var20 = 0;
                var21 = 0;
              }

              var2 = (var18 << 18) + var19;
              var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 18);
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
              var18 = var20;
              var19 = var21;
              var9 += var12;
              var10 += var13;
              var11 += var14;
              var17 = var11 >> 14;
              if (var17 != 0) {
                var20 = var9 / var17;
                var21 = var10 / var17;
                if (var20 < 0) {
                  var20 = 0;
                } else if (var20 > 16256) {
                  var20 = 16256;
                }
              } else {
                var20 = 0;
                var21 = 0;
              }

              var2 = (var18 << 18) + var19;
              var22 = (var21 - var19 >> 3) + (var20 - var18 >> 3 << 18);
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

  public static void method626(int[] var0, int[] var1, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
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
