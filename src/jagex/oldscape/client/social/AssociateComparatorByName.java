package jagex.oldscape.client.social;

public class AssociateComparatorByName<T extends Associate<T>> extends AssociateComparator<T> {

  public final boolean aBoolean764;

  public AssociateComparatorByName(boolean var1) {
    this.aBoolean764 = var1;
  }

  public static String method719(byte[] var0, int var1, int var2) {
    char[] var3 = new char[var2];
    int var4 = 0;
    int var5 = var1;

    int var8;
    for (int var6 = var2 + var1; var5 < var6; var3[var4++] = (char) var8) {
      int var7 = var0[var5++] & 255;
      if (var7 < 128) {
        if (var7 == 0) {
          var8 = 65533;
        } else {
          var8 = var7;
        }
      } else if (var7 < 192) {
        var8 = 65533;
      } else if (var7 < 224) {
        if (var5 < var6 && (var0[var5] & 192) == 128) {
          var8 = (var7 & 31) << 6 | var0[var5++] & 63;
          if (var8 < 128) {
            var8 = 65533;
          }
        } else {
          var8 = 65533;
        }
      } else if (var7 < 240) {
        if (var5 + 1 < var6 && (var0[var5] & 192) == 128 && (var0[var5 + 1] & 192) == 128) {
          var8 = (var7 & 15) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
          if (var8 < 2048) {
            var8 = 65533;
          }
        } else {
          var8 = 65533;
        }
      } else if (var7 < 248) {
        if (var5 + 2 < var6 && (var0[var5] & 192) == 128 && (var0[var5 + 1] & 192) == 128 && (var0[var5 + 2] & 192) == 128) {
          var8 = (var7 & 7) << 18 | (var0[var5++] & 63) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
        }
        var8 = 65533;
      } else {
        var8 = 65533;
      }
    }

    return new String(var3, 0, var4);
  }

  int method604(T var1, T var2) {
    if (var1.world != 0 && var2.world != 0) {
      return this.aBoolean764 ? var1.getDisplayName().compare0(var2.getDisplayName()) : var2.getDisplayName().compare0(var1.getDisplayName());
    }
    return this.method1135(var1, var2);
  }

  public int compare(T var1, T var2) {
    return this.method604(var1, var2);
  }
}
