package jagex.oldscape.stockmarket;

import jagex.jagex3.client.applet.GameShell;
import jagex.oldscape.*;
import jagex.jagex3.sound.ObjectSound;
import jagex.jagex3.util.Browser;
import jagex.core.stringtools.Base64;
import jagex.oldscape.client.*;
import jagex.oldscape.client.scene.*;
import jagex.oldscape.client.scene.entity.*;
import jagex.oldscape.client.type.ObjectDefinition;
import jagex.oldscape.client.fonts.BaseFont;
import jagex.jagex3.js5.BufferedFile;
import jagex.messaging.Buffer;
import jagex.statics.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.Random;

public final class StockmarketListingLifetimeComparator implements Comparator<StockmarketEvent> {
  public static boolean inFriendsChat;
  public static InterfaceComponent anInterfaceComponent585;

  public static void method411() {
    Login.username = Login.username.trim();
    if (Login.username.length() == 0) {
      Login.setMessages("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
    } else {
      long var6;
      try {
        URL var0 = new URL(GameShell.method611("services", false) + "m=accountappeal/login.ws");
        URLConnection var1 = var0.openConnection();
        var1.setRequestProperty("connection", "close");
        var1.setDoInput(true);
        var1.setDoOutput(true);
        var1.setConnectTimeout(5000);
        OutputStreamWriter var2 = new OutputStreamWriter(var1.getOutputStream());
        var2.write("data1=req");
        var2.flush();
        InputStream var3 = var1.getInputStream();
        Buffer var4 = new Buffer(new byte[1000]);

        while (true) {
          int var5 = var3.read(var4.payload, var4.pos, 1000 - var4.pos);
          if (var5 == -1) {
            var4.pos = 0;
            var6 = var4.g8();
            break;
          }

          var4.pos += var5;
          if (var4.pos >= 1000) {
            var6 = 0L;
            break;
          }
        }
      } catch (Exception var46) {
        var6 = 0L;
      }

      byte var12;
      if (0L == var6) {
        var12 = 5;
      } else {
        String var47 = Login.username;
        Random var48 = new Random();
        Buffer var13 = new Buffer(128);
        Buffer var14 = new Buffer(128);
        int[] var15 = new int[]{var48.nextInt(), var48.nextInt(), (int) (var6 >> 32), (int) var6};
        var13.p1(10);

        int var16;
        for (var16 = 0; var16 < 4; ++var16) {
          var13.p4(var48.nextInt());
        }

        var13.p4(var15[0]);
        var13.p4(var15[1]);
        var13.p8(var6);
        var13.p8(0L);

        for (var16 = 0; var16 < 4; ++var16) {
          var13.p4(var48.nextInt());
        }

        var13.pRsa(Statics54.aBigInteger628, Statics54.aBigInteger626);
        var14.p1(10);

        for (var16 = 0; var16 < 3; ++var16) {
          var14.p4(var48.nextInt());
        }

        var14.p8(var48.nextLong());
        var14.p6(var48.nextLong());
        if (client.random != null) {
          var14.pdata(client.random, 0, client.random.length);
        } else {
          byte[] var17 = new byte[24];

          try {
            BufferedFile.random.seek(0L);
            BufferedFile.random.readFully(var17);

            int var18;
            for (var18 = 0; var18 < 24 && var17[var18] == 0; ++var18) {
            }

            if (var18 >= 24) {
              throw new IOException();
            }
          } catch (Exception var45) {
            for (int var20 = 0; var20 < 24; ++var20) {
              var17[var20] = -1;
            }
          }

          var14.pdata(var17, 0, var17.length);
        }

        var14.p8(var48.nextLong());
        var14.pRsa(Statics54.aBigInteger628, Statics54.aBigInteger626);
        var16 = Buffer.stringLengthPlusOne(var47);
        if (var16 % 8 != 0) {
          var16 += 8 - var16 % 8;
        }

        Buffer var49 = new Buffer(var16);
        var49.pcstr(var47);
        var49.pos = var16;
        var49.tinyenc(var15);
        Buffer var19 = new Buffer(var49.pos + var13.pos + var14.pos + 5);
        var19.p1(2);
        var19.p1(var13.pos);
        var19.pdata(var13.payload, 0, var13.pos);
        var19.p1(var14.pos);
        var19.pdata(var14.payload, 0, var14.pos);
        var19.p2(var49.pos);
        var19.pdata(var49.payload, 0, var49.pos);
        byte[] var22 = var19.payload;

        byte var43;
        try {
          URL var25 = new URL(GameShell.method611("services", false) + "m=accountappeal/login.ws");
          URLConnection var26 = var25.openConnection();
          var26.setDoInput(true);
          var26.setDoOutput(true);
          var26.setConnectTimeout(5000);
          OutputStreamWriter var27 = new OutputStreamWriter(var26.getOutputStream());
          int var29 = Base64.encode(var22, 0, var22.length).length();
          StringBuilder var30 = new StringBuilder(var29);

          int var34;
          for (int var31 = 0; var31 < var29; ++var31) {
            char var32 = Base64.encode(var22, 0, var22.length).charAt(var31);
            if ((var32 < 'a' || var32 > 'z') && (var32 < 'A' || var32 > 'Z') && (var32 < '0' || var32 > '9') && var32 != '.' && var32 != '-' && var32 != '*' && var32 != '_') {
              if (var32 == ' ') {
                var30.append('+');
              } else {
                byte var33 = BaseFont.toCp1252Byte(var32);
                var30.append('%');
                var34 = var33 >> 4 & 15;
                if (var34 >= 10) {
                  var30.append((char) (var34 + 55));
                } else {
                  var30.append((char) (var34 + 48));
                }

                var34 = var33 & 15;
                if (var34 >= 10) {
                  var30.append((char) (var34 + 55));
                } else {
                  var30.append((char) (var34 + 48));
                }
              }
            } else {
              var30.append(var32);
            }
          }

          String var35 = var30.toString();
          String var50 = "data2=" + var35 + "&dest=";
          int var51 = "passwordchoice.ws".length();
          StringBuilder var37 = new StringBuilder(var51);

          for (var34 = 0; var34 < var51; ++var34) {
            char var38 = "passwordchoice.ws".charAt(var34);
            if ((var38 < 'a' || var38 > 'z') && (var38 < 'A' || var38 > 'Z') && (var38 < '0' || var38 > '9') && var38 != '.' && var38 != '-' && var38 != '*' && var38 != '_') {
              if (var38 == ' ') {
                var37.append('+');
              } else {
                byte var39 = BaseFont.toCp1252Byte(var38);
                var37.append('%');
                int var40 = var39 >> 4 & 15;
                if (var40 >= 10) {
                  var37.append((char) (var40 + 55));
                } else {
                  var37.append((char) (var40 + 48));
                }

                var40 = var39 & 15;
                if (var40 >= 10) {
                  var37.append((char) (var40 + 55));
                } else {
                  var37.append((char) (var40 + 48));
                }
              }
            } else {
              var37.append(var38);
            }
          }

          String var41 = var37.toString();
          var27.write(var50 + var41);
          var27.flush();
          InputStream var42 = var26.getInputStream();
          var19 = new Buffer(new byte[1000]);

          while (true) {
            int var52 = var42.read(var19.payload, var19.pos, 1000 - var19.pos);
            if (var52 == -1) {
              var27.close();
              var42.close();
              String var53 = new String(var19.payload);
              if (var53.startsWith("OFFLINE")) {
                var43 = 4;
              } else if (var53.startsWith("WRONG")) {
                var43 = 7;
              } else if (var53.startsWith("RELOAD")) {
                var43 = 3;
              } else if (var53.startsWith("Not permitted for social network accounts.")) {
                var43 = 6;
              } else {
                var19.tinydec(var15);

                while (var19.pos > 0 && var19.payload[var19.pos - 1] == 0) {
                  --var19.pos;
                }

                var53 = new String(var19.payload, 0, var19.pos);
                if (LoginScreenEffect.method286(var53)) {
                  Browser.openURL0(var53, true, false);
                  var43 = 2;
                } else {
                  var43 = 5;
                }
              }
              break;
            }

            var19.pos += var52;
            if (var19.pos >= 1000) {
              var43 = 5;
              break;
            }
          }
        } catch (Throwable var44) {
          var44.printStackTrace();
          var43 = 5;
        }

        var12 = var43;
      }

      switch (var12) {
        case 2:
          Login.setMessages(Statics24.aString1416, Statics24.aString1418, Statics24.aString1415);
          Login.step = 6;
          break;
        case 3:
        case 6:
          Login.setMessages("", "Error connecting to server.", "");
          break;
        case 4:
          Login.setMessages("The part of the website you are trying", "to connect to is offline at the moment.", "Please try again later.");
          break;
        case 5:
          Login.setMessages("Sorry, there was an error trying to", "log you in to this part of the website.", "Please try again later.");
          break;
        case 7:
          Login.setMessages("You must enter a valid login to proceed. For accounts", "created after 24th November 2010, please use your", "email address. Otherwise please use your username.");
      }

    }
  }

  public static void method414(int var0, int var1, int var2, int var3, int var4, int var5, SceneGraph var6, CollisionMap var7) {
    if (!client.lowMemory || (SceneGraphRenderData.sceneRenderRules[0][var1][var2] & 2) != 0 || (SceneGraphRenderData.sceneRenderRules[var0][var1][var2] & 16) == 0) {
      if (var0 < SceneGraphRenderData.minimumFloorLevel) {
        SceneGraphRenderData.minimumFloorLevel = var0;
      }

      ObjectDefinition var8 = ObjectDefinition.get(var3);
      int var9;
      int var10;
      if (var4 != 1 && var4 != 3) {
        var9 = var8.sizeX;
        var10 = var8.sizeY;
      } else {
        var9 = var8.sizeY;
        var10 = var8.sizeX;
      }

      int var11;
      int var12;
      if (var9 + var1 <= 104) {
        var11 = (var9 >> 1) + var1;
        var12 = (var9 + 1 >> 1) + var1;
      } else {
        var11 = var1;
        var12 = var1 + 1;
      }

      int var13;
      int var14;
      if (var10 + var2 <= 104) {
        var13 = (var10 >> 1) + var2;
        var14 = var2 + (var10 + 1 >> 1);
      } else {
        var13 = var2;
        var14 = var2 + 1;
      }

      int[][] var15 = SceneGraphRenderData.tileHeights[var0];
      int var16 = var15[var12][var13] + var15[var11][var13] + var15[var11][var14] + var15[var12][var14] >> 2;
      int var17 = (var1 << 7) + (var9 << 6);
      int var18 = (var2 << 7) + (var10 << 6);
      long var19 = EntityUID.build(var1, var2, 2, var8.mapDoorFlag == 0, var3);
      int var21 = var5 + (var4 << 6);
      if (var8.itemSupport == 1) {
        var21 += 256;
      }

      int var23;
      int var24;
      if (var8.method1099()) {
        ObjectSound var22 = new ObjectSound();
        var22.anInt378 = var0;
        var22.anInt377 = var1 * 16384;
        var22.anInt112 = var2 * 16384;
        var23 = var8.sizeX;
        var24 = var8.sizeY;
        if (var4 == 1 || var4 == 3) {
          var23 = var8.sizeY;
          var24 = var8.sizeX;
        }

        var22.anInt380 = (var23 + var1) * 128;
        var22.anInt375 = (var24 + var2) * 128;
        var22.ambientSoundId = var8.ambientSoundId;
        var22.anInt372 = var8.anInt1344 * 128;
        var22.anInt368 = var8.anInt1508;
        var22.anInt367 = var8.anInt1510;
        var22.effects = var8.soundEffects;
        if (var8.transformIds != null) {
          var22.definition = var8;
          var22.apply();
        }

        ObjectSound.OBJECT_SOUNDS.add(var22);
        if (var22.effects != null) {
          var22.anInt366 = var22.anInt368 + (int) (Math.random() * (double) (var22.anInt367 - var22.anInt368));
        }
      }

      Entity var34;
      if (var5 == 22) {
        if (!client.lowMemory || var8.mapDoorFlag != 0 || var8.anInt791 == 1 || var8.aBoolean1507) {
          if (var8.animation == -1 && var8.transformIds == null) {
            var34 = var8.method1105(22, var4, var15, var17, var16, var18);
          } else {
            var34 = new DynamicObject(var3, 22, var4, var0, var1, var2, var8.animation, true, null);
          }

          var6.addTileDecor(var0, var1, var2, var16, var34, var19, var21);
          if (var8.anInt791 == 1 && var7 != null) {
            var7.setBlockedByTileDecor(var1, var2);
          }

        }
      } else if (var5 != 10 && var5 != 11) {
        int[] var10000;
        if (var5 >= 12) {
          if (var8.animation == -1 && var8.transformIds == null) {
            var34 = var8.method1105(var5, var4, var15, var17, var16, var18);
          } else {
            var34 = new DynamicObject(var3, var5, var4, var0, var1, var2, var8.animation, true, null);
          }

          var6.method1470(var0, var1, var2, var16, 1, 1, var34, 0, var19, var21);
          if (var5 >= 12 && var5 <= 17 && var5 != 13 && var0 > 0) {
            var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
            var10000[var2] |= 2340;
          }

          if (var8.anInt791 != 0 && var7 != null) {
            var7.addObject(var1, var2, var9, var10, var8.impenetrable);
          }

        } else if (var5 == 0) {
          if (var8.animation == -1 && var8.transformIds == null) {
            var34 = var8.method1105(0, var4, var15, var17, var16, var18);
          } else {
            var34 = new DynamicObject(var3, 0, var4, var0, var1, var2, var8.animation, true, null);
          }

          var6.addBoundary(var0, var1, var2, var16, var34, null, SceneGraphRenderData.anIntArray406[var4], 0, var19, var21);
          if (var4 == 0) {
            if (var8.clipped) {
              SceneGraphRenderData.shadows[var0][var1][var2] = 50;
              SceneGraphRenderData.shadows[var0][var1][var2 + 1] = 50;
            }

            if (var8.projectileClipped) {
              var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
              var10000[var2] |= 585;
            }
          } else if (var4 == 1) {
            if (var8.clipped) {
              SceneGraphRenderData.shadows[var0][var1][var2 + 1] = 50;
              SceneGraphRenderData.shadows[var0][var1 + 1][var2 + 1] = 50;
            }

            if (var8.projectileClipped) {
              var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
              var10000[var2 + 1] |= 1170;
            }
          } else if (var4 == 2) {
            if (var8.clipped) {
              SceneGraphRenderData.shadows[var0][var1 + 1][var2] = 50;
              SceneGraphRenderData.shadows[var0][var1 + 1][var2 + 1] = 50;
            }

            if (var8.projectileClipped) {
              var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1 + 1];
              var10000[var2] |= 585;
            }
          } else if (var4 == 3) {
            if (var8.clipped) {
              SceneGraphRenderData.shadows[var0][var1][var2] = 50;
              SceneGraphRenderData.shadows[var0][var1 + 1][var2] = 50;
            }

            if (var8.projectileClipped) {
              var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
              var10000[var2] |= 1170;
            }
          }

          if (var8.anInt791 != 0 && var7 != null) {
            var7.method154(var1, var2, var5, var4, var8.impenetrable);
          }

          if (var8.anInt1369 != 16) {
            var6.method1474(var0, var1, var2, var8.anInt1369);
          }

        } else if (var5 == 1) {
          if (var8.animation == -1 && var8.transformIds == null) {
            var34 = var8.method1105(1, var4, var15, var17, var16, var18);
          } else {
            var34 = new DynamicObject(var3, 1, var4, var0, var1, var2, var8.animation, true, null);
          }

          var6.addBoundary(var0, var1, var2, var16, var34, null, SceneGraphRenderData.anIntArray395[var4], 0, var19, var21);
          if (var8.clipped) {
            if (var4 == 0) {
              SceneGraphRenderData.shadows[var0][var1][var2 + 1] = 50;
            } else if (var4 == 1) {
              SceneGraphRenderData.shadows[var0][var1 + 1][var2 + 1] = 50;
            } else if (var4 == 2) {
              SceneGraphRenderData.shadows[var0][var1 + 1][var2] = 50;
            } else if (var4 == 3) {
              SceneGraphRenderData.shadows[var0][var1][var2] = 50;
            }
          }

          if (var8.anInt791 != 0 && var7 != null) {
            var7.method154(var1, var2, var5, var4, var8.impenetrable);
          }

        } else {
          int var26;
          if (var5 == 2) {
            var26 = var4 + 1 & 3;
            Entity var27;
            Entity var28;
            if (var8.animation == -1 && var8.transformIds == null) {
              var27 = var8.method1105(2, var4 + 4, var15, var17, var16, var18);
              var28 = var8.method1105(2, var26, var15, var17, var16, var18);
            } else {
              var27 = new DynamicObject(var3, 2, var4 + 4, var0, var1, var2, var8.animation, true, null);
              var28 = new DynamicObject(var3, 2, var26, var0, var1, var2, var8.animation, true, null);
            }

            var6.addBoundary(var0, var1, var2, var16, var27, var28, SceneGraphRenderData.anIntArray406[var4], SceneGraphRenderData.anIntArray406[var26], var19, var21);
            if (var8.projectileClipped) {
              if (var4 == 0) {
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[var2] |= 585;
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[1 + var2] |= 1170;
              } else if (var4 == 1) {
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[var2 + 1] |= 1170;
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1 + 1];
                var10000[var2] |= 585;
              } else if (var4 == 2) {
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1 + 1];
                var10000[var2] |= 585;
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[var2] |= 1170;
              } else if (var4 == 3) {
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[var2] |= 1170;
                var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var0][var1];
                var10000[var2] |= 585;
              }
            }

            if (var8.anInt791 != 0 && var7 != null) {
              var7.method154(var1, var2, var5, var4, var8.impenetrable);
            }

            if (var8.anInt1369 != 16) {
              var6.method1474(var0, var1, var2, var8.anInt1369);
            }

          } else if (var5 == 3) {
            if (var8.animation == -1 && var8.transformIds == null) {
              var34 = var8.method1105(3, var4, var15, var17, var16, var18);
            } else {
              var34 = new DynamicObject(var3, 3, var4, var0, var1, var2, var8.animation, true, null);
            }

            var6.addBoundary(var0, var1, var2, var16, var34, null, SceneGraphRenderData.anIntArray395[var4], 0, var19, var21);
            if (var8.clipped) {
              if (var4 == 0) {
                SceneGraphRenderData.shadows[var0][var1][var2 + 1] = 50;
              } else if (var4 == 1) {
                SceneGraphRenderData.shadows[var0][var1 + 1][var2 + 1] = 50;
              } else if (var4 == 2) {
                SceneGraphRenderData.shadows[var0][var1 + 1][var2] = 50;
              } else if (var4 == 3) {
                SceneGraphRenderData.shadows[var0][var1][var2] = 50;
              }
            }

            if (var8.anInt791 != 0 && var7 != null) {
              var7.method154(var1, var2, var5, var4, var8.impenetrable);
            }

          } else if (var5 == 9) {
            if (var8.animation == -1 && var8.transformIds == null) {
              var34 = var8.method1105(var5, var4, var15, var17, var16, var18);
            } else {
              var34 = new DynamicObject(var3, var5, var4, var0, var1, var2, var8.animation, true, null);
            }

            var6.method1470(var0, var1, var2, var16, 1, 1, var34, 0, var19, var21);
            if (var8.anInt791 != 0 && var7 != null) {
              var7.addObject(var1, var2, var9, var10, var8.impenetrable);
            }

            if (var8.anInt1369 != 16) {
              var6.method1474(var0, var1, var2, var8.anInt1369);
            }

          } else if (var5 == 4) {
            if (var8.animation == -1 && var8.transformIds == null) {
              var34 = var8.method1105(4, var4, var15, var17, var16, var18);
            } else {
              var34 = new DynamicObject(var3, 4, var4, var0, var1, var2, var8.animation, true, null);
            }

            var6.addBoundaryDecor(var0, var1, var2, var16, var34, null, SceneGraphRenderData.anIntArray406[var4], 0, 0, 0, var19, var21);
          } else {
            long var29;
            Entity var31;
            if (var5 == 5) {
              var26 = 16;
              var29 = var6.getBoundaryUidAt(var0, var1, var2);
              if (0L != var29) {
                var26 = ObjectDefinition.get(EntityUID.getObjectId(var29)).anInt1369;
              }

              if (var8.animation == -1 && var8.transformIds == null) {
                var31 = var8.method1105(4, var4, var15, var17, var16, var18);
              } else {
                var31 = new DynamicObject(var3, 4, var4, var0, var1, var2, var8.animation, true, null);
              }

              var6.addBoundaryDecor(var0, var1, var2, var16, var31, null, SceneGraphRenderData.anIntArray406[var4], 0, var26 * SceneGraphRenderData.anIntArray402[var4], var26 * SceneGraphRenderData.anIntArray394[var4], var19, var21);
            } else if (var5 == 6) {
              var26 = 8;
              var29 = var6.getBoundaryUidAt(var0, var1, var2);
              if (0L != var29) {
                var26 = ObjectDefinition.get(EntityUID.getObjectId(var29)).anInt1369 / 2;
              }

              if (var8.animation == -1 && var8.transformIds == null) {
                var31 = var8.method1105(4, var4 + 4, var15, var17, var16, var18);
              } else {
                var31 = new DynamicObject(var3, 4, var4 + 4, var0, var1, var2, var8.animation, true, null);
              }

              var6.addBoundaryDecor(var0, var1, var2, var16, var31, null, 256, var4, var26 * SceneGraphRenderData.anIntArray397[var4], var26 * SceneGraphRenderData.anIntArray392[var4], var19, var21);
            } else if (var5 == 7) {
              var23 = var4 + 2 & 3;
              if (var8.animation == -1 && var8.transformIds == null) {
                var34 = var8.method1105(4, var23 + 4, var15, var17, var16, var18);
              } else {
                var34 = new DynamicObject(var3, 4, var23 + 4, var0, var1, var2, var8.animation, true, null);
              }

              var6.addBoundaryDecor(var0, var1, var2, var16, var34, null, 256, var23, 0, 0, var19, var21);
            } else if (var5 == 8) {
              var26 = 8;
              var29 = var6.getBoundaryUidAt(var0, var1, var2);
              if (0L != var29) {
                var26 = ObjectDefinition.get(EntityUID.getObjectId(var29)).anInt1369 / 2;
              }

              int var32 = var4 + 2 & 3;
              Entity var33;
              if (var8.animation == -1 && var8.transformIds == null) {
                var31 = var8.method1105(4, var4 + 4, var15, var17, var16, var18);
                var33 = var8.method1105(4, var32 + 4, var15, var17, var16, var18);
              } else {
                var31 = new DynamicObject(var3, 4, var4 + 4, var0, var1, var2, var8.animation, true, null);
                var33 = new DynamicObject(var3, 4, var32 + 4, var0, var1, var2, var8.animation, true, null);
              }

              var6.addBoundaryDecor(var0, var1, var2, var16, var31, var33, 256, var4, var26 * SceneGraphRenderData.anIntArray397[var4], var26 * SceneGraphRenderData.anIntArray392[var4], var19, var21);
            }
          }
        }
      } else {
        if (var8.animation == -1 && var8.transformIds == null) {
          var34 = var8.method1105(10, var4, var15, var17, var16, var18);
        } else {
          var34 = new DynamicObject(var3, 10, var4, var0, var1, var2, var8.animation, true, null);
        }

        if (var34 != null && var6.method1470(var0, var1, var2, var16, var9, var10, var34, var5 == 11 ? 256 : 0, var19, var21) && var8.clipped) {
          var23 = 15;
          if (var34 instanceof Model) {
            var23 = ((Model) var34).radius() / 4;
            if (var23 > 30) {
              var23 = 30;
            }
          }

          for (var24 = 0; var24 <= var9; ++var24) {
            for (int var25 = 0; var25 <= var10; ++var25) {
              if (var23 > SceneGraphRenderData.shadows[var0][var24 + var1][var25 + var2]) {
                SceneGraphRenderData.shadows[var0][var24 + var1][var25 + var2] = (byte) var23;
              }
            }
          }
        }

        if (var8.anInt791 != 0 && var7 != null) {
          var7.addObject(var1, var2, var9, var10, var8.impenetrable);
        }

      }
    }
  }

  public static int method412(int var0, int var1, int var2) {
    if (var2 > 179) {
      var1 /= 2;
    }

    if (var2 > 192) {
      var1 /= 2;
    }

    if (var2 > 217) {
      var1 /= 2;
    }

    if (var2 > 243) {
      var1 /= 2;
    }

    return (var1 / 32 << 7) + (var0 / 4 << 10) + var2 / 2;
  }

  int method413(StockmarketEvent var1, StockmarketEvent var2) {
    return Long.compare(var1.age, var2.age);
  }

  public boolean equals(Object var1) {
    return super.equals(var1);
  }

  public int compare(StockmarketEvent var1, StockmarketEvent var2) {
    return this.method413(var1, var2);
  }
}
