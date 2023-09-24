package jagex.core.compression.huffman;

public class Huffman {

  public static Huffman instance;

  public final int[] masks;

  public final byte[] bits;

  public int[] keys;

  public Huffman(byte[] bits) {
    int size = bits.length;
    masks = new int[size];
    this.bits = bits;
    int[] var3 = new int[33];
    keys = new int[8];
    int var4 = 0;

    for (int i = 0; i < size; ++i) {
      byte bit = bits[i];
      if (bit != 0) {
        int var7 = 1 << 32 - bit;
        int mask = var3[bit];
        masks[i] = mask;
        int var9;
        if ((mask & var7) != 0) {
          var9 = var3[bit - 1];
        } else {
          var9 = mask | var7;

          for (int j = bit - 1; j >= 1; --j) {
            int var11 = var3[j];
            if (mask != var11) {
              break;
            }

            int var12 = 1 << 32 - j;
            if ((var11 & var12) != 0) {
              var3[j] = var3[j - 1];
              break;
            }

            var3[j] = var11 | var12;
          }
        }

        var3[bit] = var9;

        for (int j = bit + 1; j <= 32; ++j) {
          if (mask == var3[j]) {
            var3[j] = var9;
          }
        }

        int var10 = 0;
        for (int j = 0; j < bit; ++j) {
          int var12 = Integer.MIN_VALUE >>> j;
          if ((mask & var12) != 0) {
            if (keys[var10] == 0) {
              keys[var10] = var4;
            }

            var10 = keys[var10];
          } else {
            ++var10;
          }

          if (var10 >= keys.length) {
            int[] var13 = new int[keys.length * 2];
            System.arraycopy(keys, 0, var13, 0, keys.length);
            keys = var13;
          }
        }

        keys[var10] = ~i;
        if (var10 >= var4) {
          var4 = var10 + 1;
        }
      }
    }

  }

  public int decompress(byte[] var1, int var2, byte[] var3, int var4, int var5) {
    if (var5 == 0) {
      return 0;
    }
    int var6 = 0;
    var5 += var4;
    int var7 = var2;

    while (true) {
      byte var8 = var1[var7];
      if (var8 < 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      int var9;
      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 64) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 32) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 16) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 8) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 4) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 2) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      if ((var8 & 1) != 0) {
        var6 = keys[var6];
      } else {
        ++var6;
      }

      if ((var9 = keys[var6]) < 0) {
        var3[var4++] = (byte) (~var9);
        if (var4 >= var5) {
          break;
        }

        var6 = 0;
      }

      ++var7;
    }

    return var7 + 1 - var2;
  }

  public int compress(byte[] var1, int var2, int var3, byte[] var4, int var5) {
    int var6 = 0;
    int var7 = var5 << 3;

    for (var3 += var2; var2 < var3; ++var2) {
      int var8 = var1[var2] & 255;
      int var9 = masks[var8];
      byte var10 = bits[var8];
      if (var10 == 0) {
        throw new RuntimeException("" + var8);
      }

      int var11 = var7 >> 3;
      int var12 = var7 & 7;
      var6 &= -var12 >> 31;
      int var13 = (var12 + var10 - 1 >> 3) + var11;
      var12 += 24;
      var4[var11] = (byte) (var6 |= var9 >>> var12);
      if (var11 < var13) {
        ++var11;
        var12 -= 8;
        var4[var11] = (byte) (var6 = var9 >>> var12);
        if (var11 < var13) {
          ++var11;
          var12 -= 8;
          var4[var11] = (byte) (var6 = var9 >>> var12);
          if (var11 < var13) {
            ++var11;
            var12 -= 8;
            var4[var11] = (byte) (var6 = var9 >>> var12);
            if (var11 < var13) {
              ++var11;
              var12 -= 8;
              var4[var11] = (byte) (var6 = var9 << -var12);
            }
          }
        }
      }

      var7 += var10;
    }

    return (var7 + 7 >> 3) - var5;
  }
}
