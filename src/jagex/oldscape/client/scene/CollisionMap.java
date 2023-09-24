package jagex.oldscape.client.scene;

import jagex.jagex3.client.input.mouse.Mouse;

import java.awt.*;

public class CollisionMap {

  public final int[][] flags;
  public final int width;
  public final int insetX;
  public final int height;
  public final int insetY;

  public CollisionMap(int width, int height) {
    insetX = 0;
    insetY = 0;
    this.width = width;
    this.height = height;
    flags = new int[width][height];
    initialize();
  }

  public static void method155(Component var0) {
    var0.removeMouseListener(Mouse.instance);
    var0.removeMouseMotionListener(Mouse.instance);
    var0.removeFocusListener(Mouse.instance);
    Mouse.pendingPressMeta = 0;
  }

  public void invert(int x, int y, int flag) {
    flags[x][y] &= ~flag;
  }

  public void add(int x, int y, int var3) {
    flags[x][y] |= var3;
  }

  public void initialize() {
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        if (x != 0 && y != 0 && x < width - 5 && y < height - 5) {
          flags[x][y] = 16777216;
        } else {
          flags[x][y] = 16777215;
        }
      }
    }

  }

  public void setBlockedByTile(int var1, int var2) {
    var1 -= insetX;
    var2 -= insetY;
    int[] var10000 = flags[var1];
    var10000[var2] |= 2097152;
  }

  public void method157(int x, int y, int var3, int var4, boolean impenetrable) {
    x -= insetX;
    y -= insetY;
    if (var3 == 0) {
      if (var4 == 0) {
        invert(x, y, 128);
        invert(x - 1, y, 8);
      }

      if (var4 == 1) {
        invert(x, y, 2);
        invert(x, y + 1, 32);
      }

      if (var4 == 2) {
        invert(x, y, 8);
        invert(x + 1, y, 128);
      }

      if (var4 == 3) {
        invert(x, y, 32);
        invert(x, y - 1, 2);
      }
    }

    if (var3 == 1 || var3 == 3) {
      if (var4 == 0) {
        invert(x, y, 1);
        invert(x - 1, y + 1, 16);
      }

      if (var4 == 1) {
        invert(x, y, 4);
        invert(x + 1, y + 1, 64);
      }

      if (var4 == 2) {
        invert(x, y, 16);
        invert(x + 1, y - 1, 1);
      }

      if (var4 == 3) {
        invert(x, y, 64);
        invert(x - 1, y - 1, 4);
      }
    }

    if (var3 == 2) {
      if (var4 == 0) {
        invert(x, y, 130);
        invert(x - 1, y, 8);
        invert(x, y + 1, 32);
      }

      if (var4 == 1) {
        invert(x, y, 10);
        invert(x, y + 1, 32);
        invert(x + 1, y, 128);
      }

      if (var4 == 2) {
        invert(x, y, 40);
        invert(x + 1, y, 128);
        invert(x, y - 1, 2);
      }

      if (var4 == 3) {
        invert(x, y, 160);
        invert(x, y - 1, 2);
        invert(x - 1, y, 8);
      }
    }

    if (impenetrable) {
      if (var3 == 0) {
        if (var4 == 0) {
          invert(x, y, 65536);
          invert(x - 1, y, 4096);
        }

        if (var4 == 1) {
          invert(x, y, 1024);
          invert(x, y + 1, 16384);
        }

        if (var4 == 2) {
          invert(x, y, 4096);
          invert(x + 1, y, 65536);
        }

        if (var4 == 3) {
          invert(x, y, 16384);
          invert(x, y - 1, 1024);
        }
      }

      if (var3 == 1 || var3 == 3) {
        if (var4 == 0) {
          invert(x, y, 512);
          invert(x - 1, y + 1, 8192);
        }

        if (var4 == 1) {
          invert(x, y, 2048);
          invert(x + 1, y + 1, 32768);
        }

        if (var4 == 2) {
          invert(x, y, 8192);
          invert(x + 1, y - 1, 512);
        }

        if (var4 == 3) {
          invert(x, y, 32768);
          invert(x - 1, y - 1, 2048);
        }
      }

      if (var3 == 2) {
        if (var4 == 0) {
          invert(x, y, 66560);
          invert(x - 1, y, 4096);
          invert(x, y + 1, 16384);
        }

        if (var4 == 1) {
          invert(x, y, 5120);
          invert(x, y + 1, 16384);
          invert(x + 1, y, 65536);
        }

        if (var4 == 2) {
          invert(x, y, 20480);
          invert(x + 1, y, 65536);
          invert(x, y - 1, 1024);
        }

        if (var4 == 3) {
          invert(x, y, 81920);
          invert(x, y - 1, 1024);
          invert(x - 1, y, 4096);
        }
      }
    }
  }

  public void method152(int x, int y) {
    x -= insetX;
    y -= insetY;
    flags[x][y] &= -262145;
  }

  public void setFlagOffNonSquare(int var1, int var2, int var3, int var4, int var5, boolean var6) {
    int var7 = 256;
    if (var6) {
      var7 += 131072;
    }

    var1 -= insetX;
    var2 -= insetY;
    int var8;
    if (var5 == 1 || var5 == 3) {
      var8 = var3;
      var3 = var4;
      var4 = var8;
    }

    for (var8 = var1; var8 < var3 + var1; ++var8) {
      if (var8 >= 0 && var8 < width) {
        for (int var9 = var2; var9 < var2 + var4; ++var9) {
          if (var9 >= 0 && var9 < height) {
            invert(var8, var9, var7);
          }
        }
      }
    }

  }

  public void setBlockedByTileDecor(int x, int y) {
    x -= insetX;
    y -= insetY;
    flags[x][y] |= 262144;
  }

  public void addObject(int x, int y, int var3, int var4, boolean var5) {
    int var6 = 256;
    if (var5) {
      var6 += 131072;
    }

    x -= insetX;
    y -= insetY;

    for (int var7 = x; var7 < var3 + x; ++var7) {
      if (var7 >= 0 && var7 < width) {
        for (int var8 = y; var8 < y + var4; ++var8) {
          if (var8 >= 0 && var8 < height) {
            add(var7, var8, var6);
          }
        }
      }
    }

  }

  public void method154(int x, int y, int var3, int var4, boolean var5) {
    x -= insetX;
    y -= insetY;
    if (var3 == 0) {
      if (var4 == 0) {
        add(x, y, 128);
        add(x - 1, y, 8);
      }

      if (var4 == 1) {
        add(x, y, 2);
        add(x, y + 1, 32);
      }

      if (var4 == 2) {
        add(x, y, 8);
        add(x + 1, y, 128);
      }

      if (var4 == 3) {
        add(x, y, 32);
        add(x, y - 1, 2);
      }
    }

    if (var3 == 1 || var3 == 3) {
      if (var4 == 0) {
        add(x, y, 1);
        add(x - 1, y + 1, 16);
      }

      if (var4 == 1) {
        add(x, y, 4);
        add(x + 1, y + 1, 64);
      }

      if (var4 == 2) {
        add(x, y, 16);
        add(x + 1, y - 1, 1);
      }

      if (var4 == 3) {
        add(x, y, 64);
        add(x - 1, y - 1, 4);
      }
    }

    if (var3 == 2) {
      if (var4 == 0) {
        add(x, y, 130);
        add(x - 1, y, 8);
        add(x, y + 1, 32);
      }

      if (var4 == 1) {
        add(x, y, 10);
        add(x, y + 1, 32);
        add(x + 1, y, 128);
      }

      if (var4 == 2) {
        add(x, y, 40);
        add(x + 1, y, 128);
        add(x, y - 1, 2);
      }

      if (var4 == 3) {
        add(x, y, 160);
        add(x, y - 1, 2);
        add(x - 1, y, 8);
      }
    }

    if (var5) {
      if (var3 == 0) {
        if (var4 == 0) {
          add(x, y, 65536);
          add(x - 1, y, 4096);
        }

        if (var4 == 1) {
          add(x, y, 1024);
          add(x, y + 1, 16384);
        }

        if (var4 == 2) {
          add(x, y, 4096);
          add(x + 1, y, 65536);
        }

        if (var4 == 3) {
          add(x, y, 16384);
          add(x, y - 1, 1024);
        }
      }

      if (var3 == 1 || var3 == 3) {
        if (var4 == 0) {
          add(x, y, 512);
          add(x - 1, y + 1, 8192);
        }

        if (var4 == 1) {
          add(x, y, 2048);
          add(x + 1, y + 1, 32768);
        }

        if (var4 == 2) {
          add(x, y, 8192);
          add(x + 1, y - 1, 512);
        }

        if (var4 == 3) {
          add(x, y, 32768);
          add(x - 1, y - 1, 2048);
        }
      }

      if (var3 == 2) {
        if (var4 == 0) {
          add(x, y, 66560);
          add(x - 1, y, 4096);
          add(x, y + 1, 16384);
        }

        if (var4 == 1) {
          add(x, y, 5120);
          add(x, y + 1, 16384);
          add(x + 1, y, 65536);
        }

        if (var4 == 2) {
          add(x, y, 20480);
          add(x + 1, y, 65536);
          add(x, y - 1, 1024);
        }

        if (var4 == 3) {
          add(x, y, 81920);
          add(x, y - 1, 1024);
          add(x - 1, y, 4096);
        }
      }
    }
  }
}
