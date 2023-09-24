package jagex.oldscape.client.worldmap;

import jagex.statics.Statics35;

public class WorldMapScriptEvent {
  public final int anInt306;
  public final WorldMapPosition aWorldMapPosition307;
  public final WorldMapPosition aWorldMapPosition305;

  public WorldMapScriptEvent(int var1, WorldMapPosition var2, WorldMapPosition var3) {
    this.anInt306 = var1;
    this.aWorldMapPosition307 = var2;
    this.aWorldMapPosition305 = var3;
  }

  public static char method186(byte var0) {
    int var1 = var0 & 255;
    if (var1 == 0) {
      throw new IllegalArgumentException("" + Integer.toString(var1, 16));
    }
    if (var1 >= 128 && var1 < 160) {
      char var2 = Statics35.cp1252AsciiExtension[var1 - 128];
      if (var2 == 0) {
        var2 = '?';
      }

      var1 = var2;
    }

    return (char) var1;
  }

  public static int method187(int var0, int var1) {
    if (var0 == -2) {
      return 12345678;
    }
    if (var0 == -1) {
      if (var1 < 2) {
        var1 = 2;
      } else if (var1 > 126) {
        var1 = 126;
      }

      return var1;
    }
    var1 = (var0 & 127) * var1 / 128;
    if (var1 < 2) {
      var1 = 2;
    } else if (var1 > 126) {
      var1 = 126;
    }

    return (var0 & 65408) + var1;
  }
}
