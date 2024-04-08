package jagex.jagex3.sound;

import jagex.jagex3.sound.vorbis.RawAudioOverride;
import jagex.datastructure.Node;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.type.VarDefinition;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class AudioOverride extends Node {
  final RawAudioOverride[] aClass5_Sub10_Sub1Array1143;
  final short[] aShortArray1142;
  final byte[] aByteArray1141;
  final byte[] aByteArray1140;
  final AudioOverrideEffect[] aAudioOverrideEffectArray1139;
  final byte[] aByteArray572;
  final int anInt380;
  int[] anIntArray692;

  public AudioOverride(byte[] var1) {
    aClass5_Sub10_Sub1Array1143 = new RawAudioOverride[128];
    aShortArray1142 = new short[128];
    aByteArray1141 = new byte[128];
    aByteArray1140 = new byte[128];
    aAudioOverrideEffectArray1139 = new AudioOverrideEffect[128];
    aByteArray572 = new byte[128];
    anIntArray692 = new int[128];
    Buffer var2 = new Buffer(var1);

    int var3 = 0;
    while (var2.payload[var3 + var2.pos] != 0) {
      ++var3;
    }

    byte[] var4 = new byte[var3];

    for (int var5 = 0; var5 < var3; ++var5) {
      var4[var5] = var2.g1b();
    }

    ++var2.pos;
    ++var3;
    int var5 = var2.pos;
    var2.pos += var3;

    int var6 = 0;
    while (var2.payload[var6 + var2.pos] != 0) {
      ++var6;
    }

    byte[] var7 = new byte[var6];

    for (int var8 = 0; var8 < var6; ++var8) {
      var7[var8] = var2.g1b();
    }

    ++var2.pos;
    ++var6;
    int var8 = var2.pos;
    var2.pos += var6;

    int var9 = 0;
    while (var2.payload[var9 + var2.pos] != 0) {
      ++var9;
    }

    byte[] var10 = new byte[var9];

    for (int var11 = 0; var11 < var9; ++var11) {
      var10[var11] = var2.g1b();
    }

    ++var2.pos;
    ++var9;
    byte[] var12 = new byte[var9];
    int var14;
    int var15;
    if (var9 > 1) {
      var12[1] = 1;
      int var13 = 1;
      var14 = 2;

      for (var15 = 2; var15 < var9; ++var15) {
        int var16 = var2.g1();
        if (var16 == 0) {
          var13 = var14++;
        } else {
          if (var16 <= var13) {
            --var16;
          }

          var13 = var16;
        }

        var12[var15] = (byte) var13;
      }
    } else {
      var14 = var9;
    }

    AudioOverrideEffect[] var17 = new AudioOverrideEffect[var14];

    AudioOverrideEffect var18;
    for (var15 = 0; var15 < var17.length; ++var15) {
      var18 = var17[var15] = new AudioOverrideEffect();
      int var19 = var2.g1();
      if (var19 > 0) {
        var18.aByteArray1132 = new byte[var19 * 2];
      }

      var19 = var2.g1();
      if (var19 > 0) {
        var18.aByteArray1134 = new byte[var19 * 2 + 2];
        var18.aByteArray1134[1] = 64;
      }
    }

    var15 = var2.g1();
    byte[] var42 = var15 > 0 ? new byte[var15 * 2] : null;
    var15 = var2.g1();
    byte[] var20 = var15 > 0 ? new byte[var15 * 2] : null;

    int var21;
    for (var21 = 0; var2.payload[var21 + var2.pos] != 0; ++var21) {
    }

    byte[] var22 = new byte[var21];

    int var23;
    for (var23 = 0; var23 < var21; ++var23) {
      var22[var23] = var2.g1b();
    }

    ++var2.pos;
    ++var21;
    var23 = 0;

    int var24;
    for (var24 = 0; var24 < 128; ++var24) {
      var23 += var2.g1();
      aShortArray1142[var24] = (short) var23;
    }

    var23 = 0;

    short[] var48;
    for (var24 = 0; var24 < 128; ++var24) {
      var23 += var2.g1();
      var48 = aShortArray1142;
      var48[var24] = (short) (var48[var24] + (var23 << 8));
    }

    var24 = 0;
    int var25 = 0;
    int var26 = 0;

    int var27;
    for (var27 = 0; var27 < 128; ++var27) {
      if (var24 == 0) {
        if (var25 < var22.length) {
          var24 = var22[var25++];
        } else {
          var24 = -1;
        }

        var26 = var2.method1044();
      }

      var48 = aShortArray1142;
      var48[var27] = (short) (var48[var27] + ((var26 - 1 & 2) << 14));
      anIntArray692[var27] = var26;
      --var24;
    }

    var24 = 0;
    var25 = 0;
    var27 = 0;

    int var28;
    for (var28 = 0; var28 < 128; ++var28) {
      if (anIntArray692[var28] != 0) {
        if (var24 == 0) {
          if (var25 < var4.length) {
            var24 = var4[var25++];
          } else {
            var24 = -1;
          }

          var27 = var2.payload[var5++] - 1;
        }

        aByteArray572[var28] = (byte) var27;
        --var24;
      }
    }

    var24 = 0;
    var25 = 0;
    var28 = 0;

    for (int var29 = 0; var29 < 128; ++var29) {
      if (anIntArray692[var29] != 0) {
        if (var24 == 0) {
          if (var25 < var7.length) {
            var24 = var7[var25++];
          } else {
            var24 = -1;
          }

          var28 = var2.payload[var8++] + 16 << 2;
        }

        aByteArray1140[var29] = (byte) var28;
        --var24;
      }
    }

    var24 = 0;
    var25 = 0;
    AudioOverrideEffect var30 = null;

    int var31;
    for (var31 = 0; var31 < 128; ++var31) {
      if (anIntArray692[var31] != 0) {
        if (var24 == 0) {
          var30 = var17[var12[var25]];
          if (var25 < var10.length) {
            var24 = var10[var25++];
          } else {
            var24 = -1;
          }
        }

        aAudioOverrideEffectArray1139[var31] = var30;
        --var24;
      }
    }

    var24 = 0;
    var25 = 0;
    var31 = 0;

    int var32;
    for (var32 = 0; var32 < 128; ++var32) {
      if (var24 == 0) {
        if (var25 < var22.length) {
          var24 = var22[var25++];
        } else {
          var24 = -1;
        }

        if (anIntArray692[var32] > 0) {
          var31 = var2.g1() + 1;
        }
      }

      aByteArray1141[var32] = (byte) var31;
      --var24;
    }

    anInt380 = var2.g1() + 1;

    AudioOverrideEffect var33;
    int var34;
    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.aByteArray1132 != null) {
        for (var34 = 1; var34 < var33.aByteArray1132.length; var34 += 2) {
          var33.aByteArray1132[var34] = var2.g1b();
        }
      }

      if (var33.aByteArray1134 != null) {
        for (var34 = 3; var34 < var33.aByteArray1134.length - 2; var34 += 2) {
          var33.aByteArray1134[var34] = var2.g1b();
        }
      }
    }

    if (var42 != null) {
      for (var32 = 1; var32 < var42.length; var32 += 2) {
        var42[var32] = var2.g1b();
      }
    }

    if (var20 != null) {
      for (var32 = 1; var32 < var20.length; var32 += 2) {
        var20[var32] = var2.g1b();
      }
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.aByteArray1134 != null) {
        var23 = 0;

        for (var34 = 2; var34 < var33.aByteArray1134.length; var34 += 2) {
          var23 = 1 + var23 + var2.g1();
          var33.aByteArray1134[var34] = (byte) var23;
        }
      }
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.aByteArray1132 != null) {
        var23 = 0;

        for (var34 = 2; var34 < var33.aByteArray1132.length; var34 += 2) {
          var23 = 1 + var23 + var2.g1();
          var33.aByteArray1132[var34] = (byte) var23;
        }
      }
    }

    byte var36;
    int var38;
    int var39;
    int var40;
    int var45;
    byte var46;
    if (var42 != null) {
      var23 = var2.g1();
      var42[0] = (byte) var23;

      for (var32 = 2; var32 < var42.length; var32 += 2) {
        var23 = var23 + 1 + var2.g1();
        var42[var32] = (byte) var23;
      }

      var46 = var42[0];
      byte var35 = var42[1];

      for (var34 = 0; var34 < var46; ++var34) {
        aByteArray1141[var34] = (byte) (var35 * aByteArray1141[var34] + 32 >> 6);
      }

      for (var34 = 2; var34 < var42.length; var34 += 2) {
        var36 = var42[var34];
        byte var37 = var42[var34 + 1];
        var38 = var35 * (var36 - var46) + (var36 - var46) / 2;

        for (var39 = var46; var39 < var36; ++var39) {
          var40 = SceneGraph.method1351(var38, var36 - var46);
          aByteArray1141[var39] = (byte) (var40 * aByteArray1141[var39] + 32 >> 6);
          var38 += var37 - var35;
        }

        var46 = var36;
        var35 = var37;
      }

      for (var45 = var46; var45 < 128; ++var45) {
        aByteArray1141[var45] = (byte) (var35 * aByteArray1141[var45] + 32 >> 6);
      }

    }

    if (var20 != null) {
      var23 = var2.g1();
      var20[0] = (byte) var23;

      for (var32 = 2; var32 < var20.length; var32 += 2) {
        var23 = var23 + 1 + var2.g1();
        var20[var32] = (byte) var23;
      }

      var46 = var20[0];
      int var44 = var20[1] << 1;

      for (var34 = 0; var34 < var46; ++var34) {
        var45 = var44 + (aByteArray1140[var34] & 255);
        if (var45 < 0) {
          var45 = 0;
        }

        if (var45 > 128) {
          var45 = 128;
        }

        aByteArray1140[var34] = (byte) var45;
      }

      int var47;
      for (var34 = 2; var34 < var20.length; var34 += 2) {
        var36 = var20[var34];
        var47 = var20[var34 + 1] << 1;
        var38 = var44 * (var36 - var46) + (var36 - var46) / 2;

        for (var39 = var46; var39 < var36; ++var39) {
          var40 = SceneGraph.method1351(var38, var36 - var46);
          int var41 = var40 + (aByteArray1140[var39] & 255);
          if (var41 < 0) {
            var41 = 0;
          }

          if (var41 > 128) {
            var41 = 128;
          }

          aByteArray1140[var39] = (byte) var41;
          var38 += var47 - var44;
        }

        var46 = var36;
        var44 = var47;
      }

      for (var45 = var46; var45 < 128; ++var45) {
        var47 = var44 + (aByteArray1140[var45] & 255);
        if (var47 < 0) {
          var47 = 0;
        }

        if (var47 > 128) {
          var47 = 128;
        }

        aByteArray1140[var45] = (byte) var47;
      }

      Object var43 = null;
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var17[var32].anInt1129 = var2.g1();
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.aByteArray1132 != null) {
        var33.anInt1128 = var2.g1();
      }

      if (var33.aByteArray1134 != null) {
        var33.anInt1127 = var2.g1();
      }

      if (var33.anInt1129 > 0) {
        var33.anInt1125 = var2.g1();
      }
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var17[var32].anInt1133 = var2.g1();
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.anInt1133 > 0) {
        var33.anInt1126 = var2.g1();
      }
    }

    for (var32 = 0; var32 < var14; ++var32) {
      var33 = var17[var32];
      if (var33.anInt1126 > 0) {
        var33.anInt1131 = var2.g1();
      }
    }

  }

  public static void clear() {
    VarDefinition.cache.clear();
  }

  public static AudioOverride method59(ReferenceTable var0, int var1) {
    byte[] var2 = var0.unpack(var1);
    return var2 == null ? null : new AudioOverride(var2);
  }

  void method23() {
    anIntArray692 = null;
  }

  boolean method805(AudioManager var1, byte[] var2, int[] var3) {
    boolean var4 = true;
    int var5 = 0;
    RawAudioOverride var6 = null;

    for (int var7 = 0; var7 < 128; ++var7) {
      if (var2 == null || var2[var7] != 0) {
        int var8 = anIntArray692[var7];
        if (var8 != 0) {
          if (var5 != var8) {
            var5 = var8--;
            if ((var8 & 1) == 0) {
              var6 = var1.getAudioEffectOverride(var8 >> 2, var3);
            } else {
              var6 = var1.getRawAudioOverride(var8 >> 2, var3);
            }

            if (var6 == null) {
              var4 = false;
            }
          }

          if (var6 != null) {
            aClass5_Sub10_Sub1Array1143[var7] = var6;
            anIntArray692[var7] = 0;
          }
        }
      }
    }

    return var4;
  }
}
