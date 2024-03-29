package jagex.jagex3.sound;

import jagex.datastructure.Node;
import jagex.datastructure.instrusive.hashtable.NodeTable;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class AudioTrack extends Node {

  public NodeTable<AudioBuffer> aNodeTable1112;
  public byte[] aByteArray503;

  public AudioTrack(Buffer var1) {
    var1.pos = var1.payload.length - 3;
    int var2 = var1.g1();
    int var3 = var1.g2();
    int var4 = var2 * 10 + 14;
    var1.pos = 0;
    int var5 = 0;
    int var6 = 0;
    int var7 = 0;
    int var8 = 0;
    int var9 = 0;
    int var10 = 0;
    int var11 = 0;
    int var12 = 0;

    int var13;
    int var14;
    int var15;
    for (var13 = 0; var13 < var2; ++var13) {
      var14 = -1;

      while (true) {
        var15 = var1.g1();
        if (var15 != var14) {
          ++var4;
        }

        var14 = var15 & 15;
        if (var15 == 7) {
          break;
        }

        if (var15 == 23) {
          ++var5;
        } else if (var14 == 0) {
          ++var7;
        } else if (var14 == 1) {
          ++var8;
        } else if (var14 == 2) {
          ++var6;
        } else if (var14 == 3) {
          ++var9;
        } else if (var14 == 4) {
          ++var10;
        } else if (var14 == 5) {
          ++var11;
        } else {
          if (var14 != 6) {
            throw new RuntimeException();
          }

          ++var12;
        }
      }
    }

    var4 += var5 * 5;
    var4 += (var7 + var8 + var6 + var9 + var11) * 2;
    var4 = var4 + var10 + var12;
    var13 = var1.pos;
    var14 = var2 + var5 + var6 + var7 + var8 + var9 + var10 + var11 + var12;

    for (var15 = 0; var15 < var14; ++var15) {
      var1.method1044();
    }

    var4 += var1.pos - var13;
    var15 = var1.pos;
    int var16 = 0;
    int var17 = 0;
    int var18 = 0;
    int var19 = 0;
    int var20 = 0;
    int var21 = 0;
    int var22 = 0;
    int var23 = 0;
    int var24 = 0;
    int var25 = 0;
    int var26 = 0;
    int var27 = 0;
    int var28 = 0;

    int var29;
    for (var29 = 0; var29 < var6; ++var29) {
      var28 = var28 + var1.g1() & 127;
      if (var28 != 0 && var28 != 32) {
        if (var28 == 1) {
          ++var16;
        } else if (var28 == 33) {
          ++var17;
        } else if (var28 == 7) {
          ++var18;
        } else if (var28 == 39) {
          ++var19;
        } else if (var28 == 10) {
          ++var20;
        } else if (var28 == 42) {
          ++var21;
        } else if (var28 == 99) {
          ++var22;
        } else if (var28 == 98) {
          ++var23;
        } else if (var28 == 101) {
          ++var24;
        } else if (var28 == 100) {
          ++var25;
        } else if (var28 != 64 && var28 != 65 && var28 != 120 && var28 != 121 && var28 != 123) {
          ++var27;
        } else {
          ++var26;
        }
      } else {
        ++var12;
      }
    }

    var29 = 0;
    int var30 = var1.pos;
    var1.pos += var26;
    int var31 = var1.pos;
    var1.pos += var11;
    int var32 = var1.pos;
    var1.pos += var10;
    int var33 = var1.pos;
    var1.pos += var9;
    int var34 = var1.pos;
    var1.pos += var16;
    int var35 = var1.pos;
    var1.pos += var18;
    int var36 = var1.pos;
    var1.pos += var20;
    int var37 = var1.pos;
    var1.pos += var7 + var8 + var11;
    int var38 = var1.pos;
    var1.pos += var7;
    int var39 = var1.pos;
    var1.pos += var27;
    int var40 = var1.pos;
    var1.pos += var8;
    int var41 = var1.pos;
    var1.pos += var17;
    int var42 = var1.pos;
    var1.pos += var19;
    int var43 = var1.pos;
    var1.pos += var21;
    int var44 = var1.pos;
    var1.pos += var12;
    int var45 = var1.pos;
    var1.pos += var9;
    int var46 = var1.pos;
    var1.pos += var22;
    int var47 = var1.pos;
    var1.pos += var23;
    int var48 = var1.pos;
    var1.pos += var24;
    int var49 = var1.pos;
    var1.pos += var25;
    int var50 = var1.pos;
    var1.pos += var5 * 3;
    this.aByteArray503 = new byte[var4];
    Buffer var51 = new Buffer(this.aByteArray503);
    var51.p4(1297377380);
    var51.p4(6);
    var51.p2(var2 > 1 ? 1 : 0);
    var51.p2(var2);
    var51.p2(var3);
    var1.pos = var13;
    int var52 = 0;
    int var53 = 0;
    int var54 = 0;
    int var55 = 0;
    int var56 = 0;
    int var57 = 0;
    int var58 = 0;
    int[] var59 = new int[128];
    var28 = 0;

    label221:
    for (int var60 = 0; var60 < var2; ++var60) {
      var51.p4(1297379947);
      var51.pos += 4;
      int var61 = var51.pos;
      int var62 = -1;

      while (true) {
        while (true) {
          int var63 = var1.method1044();
          var51.pFlag(var63);
          int var64 = var1.payload[var29++] & 255;
          boolean var65 = var64 != var62;
          var62 = var64 & 15;
          if (var64 == 7) {
            if (var65) {
              var51.p1(255);
            }

            var51.p1(47);
            var51.p1(0);
            var51.pSize4(var51.pos - var61);
            continue label221;
          }

          if (var64 == 23) {
            if (var65) {
              var51.p1(255);
            }

            var51.p1(81);
            var51.p1(3);
            var51.p1(var1.payload[var50++]);
            var51.p1(var1.payload[var50++]);
            var51.p1(var1.payload[var50++]);
          } else {
            var52 ^= var64 >> 4;
            if (var62 == 0) {
              if (var65) {
                var51.p1(var52 + 144);
              }

              var53 += var1.payload[var37++];
              var54 += var1.payload[var38++];
              var51.p1(var53 & 127);
              var51.p1(var54 & 127);
            } else if (var62 == 1) {
              if (var65) {
                var51.p1(var52 + 128);
              }

              var53 += var1.payload[var37++];
              var55 += var1.payload[var40++];
              var51.p1(var53 & 127);
              var51.p1(var55 & 127);
            } else if (var62 == 2) {
              if (var65) {
                var51.p1(var52 + 176);
              }

              var28 = var28 + var1.payload[var15++] & 127;
              var51.p1(var28);
              byte var66;
              if (var28 != 0 && var28 != 32) {
                if (var28 == 1) {
                  var66 = var1.payload[var34++];
                } else if (var28 == 33) {
                  var66 = var1.payload[var41++];
                } else if (var28 == 7) {
                  var66 = var1.payload[var35++];
                } else if (var28 == 39) {
                  var66 = var1.payload[var42++];
                } else if (var28 == 10) {
                  var66 = var1.payload[var36++];
                } else if (var28 == 42) {
                  var66 = var1.payload[var43++];
                } else if (var28 == 99) {
                  var66 = var1.payload[var46++];
                } else if (var28 == 98) {
                  var66 = var1.payload[var47++];
                } else if (var28 == 101) {
                  var66 = var1.payload[var48++];
                } else if (var28 == 100) {
                  var66 = var1.payload[var49++];
                } else if (var28 != 64 && var28 != 65 && var28 != 120 && var28 != 121 && var28 != 123) {
                  var66 = var1.payload[var39++];
                } else {
                  var66 = var1.payload[var30++];
                }
              } else {
                var66 = var1.payload[var44++];
              }

              int var67 = var66 + var59[var28];
              var59[var28] = var67;
              var51.p1(var67 & 127);
            } else if (var62 == 3) {
              if (var65) {
                var51.p1(var52 + 224);
              }

              var56 += var1.payload[var45++];
              var56 += var1.payload[var33++] << 7;
              var51.p1(var56 & 127);
              var51.p1(var56 >> 7 & 127);
            } else if (var62 == 4) {
              if (var65) {
                var51.p1(var52 + 208);
              }

              var57 += var1.payload[var32++];
              var51.p1(var57 & 127);
            } else if (var62 == 5) {
              if (var65) {
                var51.p1(var52 + 160);
              }

              var53 += var1.payload[var37++];
              var58 += var1.payload[var31++];
              var51.p1(var53 & 127);
              var51.p1(var58 & 127);
            } else {
              if (var62 != 6) {
                throw new RuntimeException();
              }

              if (var65) {
                var51.p1(var52 + 192);
              }

              var51.p1(var1.payload[var44++]);
            }
          }
        }
      }
    }

  }

  public static AudioTrack load(ReferenceTable var0, int var1, int var2) {
    byte[] var3 = var0.unpack(var1, var2);
    return var3 == null ? null : new AudioTrack(new Buffer(var3));
  }

  public void method254() {
    if (this.aNodeTable1112 == null) {
      this.aNodeTable1112 = new NodeTable<>(16);
      int[] var1 = new int[16];
      int[] var2 = new int[16];
      var2[9] = 128;
      var1[9] = 128;
      MidiFileDecoder var4 = new MidiFileDecoder(this.aByteArray503);
      int var5 = var4.method790();

      int var6;
      for (var6 = 0; var6 < var5; ++var6) {
        var4.method789(var6);
        var4.method784(var6);
        var4.method787(var6);
      }

      label56:
      do {
        while (true) {
          var6 = var4.method786();
          int var7 = var4.anIntArray1115[var6];

          while (var7 == var4.anIntArray1115[var6]) {
            var4.method789(var6);
            int var8 = var4.method792(var6);
            if (var8 == 1) {
              var4.method788();
              var4.method787(var6);
              continue label56;
            }

            int var9 = var8 & 240;
            int var10;
            int var11;
            int var12;
            if (var9 == 176) {
              var10 = var8 & 15;
              var11 = var8 >> 8 & 127;
              var12 = var8 >> 16 & 127;
              if (var11 == 0) {
                var1[var10] = (var12 << 14) + (var1[var10] & -2080769);
              }

              if (var11 == 32) {
                var1[var10] = (var1[var10] & -16257) + (var12 << 7);
              }
            }

            if (var9 == 192) {
              var10 = var8 & 15;
              var11 = var8 >> 8 & 127;
              var2[var10] = var11 + var1[var10];
            }

            if (var9 == 144) {
              var10 = var8 & 15;
              var11 = var8 >> 8 & 127;
              var12 = var8 >> 16 & 127;
              if (var12 > 0) {
                int var13 = var2[var10];
                AudioBuffer var14 = this.aNodeTable1112.lookup(var13);
                if (var14 == null) {
                  var14 = new AudioBuffer(new byte[128]);
                  this.aNodeTable1112.put(var14, var13);
                }

                var14.payload[var11] = 1;
              }
            }

            var4.method784(var6);
            var4.method787(var6);
          }
        }
      } while (!var4.method779());

    }
  }

  public void method23() {
    this.aNodeTable1112 = null;
  }
}
