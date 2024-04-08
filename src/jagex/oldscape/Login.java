package jagex.oldscape;

import jagex.core.compression.huffman.Huffman;
import jagex.core.stringtools.Strings;
import jagex.jagex3.client.applet.GameShell;
import jagex.jagex3.sound.*;
import jagex.jagex3.sound.vorbis.Decimator;
import jagex.jagex3.util.Browser;
import jagex.jagex3.client.input.keyboard.Keyboard;
import jagex.jagex3.client.input.mouse.Mouse;
import jagex.core.time.Clock;
import jagex.oldscape.client.*;
import jagex.oldscape.client.fonts.*;
import jagex.oldscape.client.option.AttackOptionPriority;
import jagex.oldscape.client.option.ClientPreferences;
import jagex.oldscape.client.social.AssociateComparator_Sub2;
import jagex.oldscape.client.social.Class114;
import jagex.oldscape.client.scene.*;
import jagex.oldscape.client.scene.entity.PendingSpawn;
import jagex.oldscape.stockmarket.*;
import jagex.oldscape.client.type.*;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.*;
import jagex.messaging.*;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.login.LoginProt;
import jagex.statics.*;
import jagex.oldscape.client.worldmap.*;

import java.text.DecimalFormat;

public class Login {

  public static OldConnectionTask js5task;

  public static Connection js5connection;

  public static String warningText = "";
  public static String loadingStateText = "";
  public static String username = "";
  public static String password = "";
  public static String totp;
  public static String loginResponse1 = "";
  public static String loginResponse2 = "";
  public static String loginResponse3 = "";

  public static int paddingX;
  public static int credentialsBoxX;
  public static int credentialsBoxCenterX;
  public static int step = 0;
  public static int anInt473 = 10;
  public static int anInt468 = -1;
  public static int anInt469 = 1;
  public static int anInt461 = 0;
  public static int anInt463 = -1;
  public static int anInt460 = 0;
  public static int anInt466 = 0;
  public static int anInt603;

  public static boolean worldSelectorOpen = false;
  public static boolean clear;
  public static boolean aBoolean477 = false;
  public static boolean aBoolean472 = false;
  public static boolean aBoolean462 = true;

  public static long aLong467 = -1L;
  public static long aLong465 = -1L;

  public static IndexedSprite[] worldSelectorFlags;
  public static IndexedSprite[] runeSprites;

  public static IndexedSprite aDoublyNode_Sub24_Sub4_470;
  public static IndexedSprite leftArrow;
  public static IndexedSprite rightArrow;
  public static IndexedSprite slbutton;
  public static IndexedSprite logoSprite;
  public static IndexedSprite aDoublyNode_Sub24_Sub4_1148;

  public static Sprite leftTitleSprite;
  public static Sprite rightTitleSprite;
  public static int parsedTotp;
  public static IndexedSprite aDoublyNode_Sub24_Sub4_288;

  static {
    paddingX = 0;
    credentialsBoxX = paddingX + 202;
    new DecimalFormat("##0.00");
  }

  public static void loadPlayersIntoScene() {
    int count = GPI.playerCount;
    int[] indices = GPI.playerIndices;

    for (int i = 0; i < count; ++i) {
      if (indices[i] != client.combatTargetPlayerIndex && indices[i] != client.playerIndex) {
        GPI.loadPlayerIntoScene(client.players[indices[i]], true);
      }
    }

  }

  public static void draw(Font var0, Font var1, Font var2) {
    paddingX = (client.canvasWidth - 765) / 2;
    credentialsBoxX = paddingX + 202;
    credentialsBoxCenterX = credentialsBoxX + 180;
    byte var7;
    int var8;
    int var10;
    int var12;
    int var19;
    int var20;
    int var21;
    int var22;
    int var23;
    int var24;
    int var41;
    if (worldSelectorOpen) {
      int var36;
      int var39;
      if (LoadedArchive.aSpriteArray429 == null) {
        Archive var35 = Archive.sprites;
        var39 = var35.getGroup("sl_back");
        var36 = var35.getFile(var39, "");
        LoadedArchive.aSpriteArray429 = Canvas.method643(var35, var39, var36);
      }

      if (worldSelectorFlags == null) {
        worldSelectorFlags = IndexedSprite.method474(Archive.sprites, "sl_flags", "");
      }

      if (StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653 == null) {
        StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653 = IndexedSprite.method474(Archive.sprites, "sl_arrows", "");
      }

      if (AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801 == null) {
        AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801 = IndexedSprite.method474(Archive.sprites, "sl_stars", "");
      }

      if (leftArrow == null) {
        leftArrow = WorldMapArea.loadIndexedSprite(Archive.sprites, "leftarrow", "");
      }

      if (rightArrow == null) {
        rightArrow = WorldMapArea.loadIndexedSprite(Archive.sprites, "rightarrow", "");
      }

      JagGraphics.fillRect(paddingX, 23, 765, 480, 0);
      JagGraphics.blitRectWithAlpha(paddingX, 0, 125, 23, 12425273, 9135624);
      JagGraphics.blitRectWithAlpha(paddingX + 125, 0, 640, 23, 5197647, 2697513);
      var0.method1154("Select a world", paddingX + 62, 15, 0, -1);
      if (AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801 != null) {
        AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801[1].renderAt(paddingX + 140, 1);
        var1.drawString("Members only world", paddingX + 152, 10, 16777215, -1);
        AnimationFrameGroup.aDoublyNode_Sub24_Sub4Array801[0].renderAt(paddingX + 140, 12);
        var1.drawString("Free world", paddingX + 152, 21, 16777215, -1);
      }

      if (StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653 != null) {
        var41 = paddingX + 280;
        if (Server.comparator[0] == 0 && Server.reverseFlags[0] == 0) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[2].renderAt(var41, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[0].renderAt(var41, 4);
        }

        if (Server.comparator[0] == 0 && Server.reverseFlags[0] == 1) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[3].renderAt(var41 + 15, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[1].renderAt(var41 + 15, 4);
        }

        var0.drawString("World", var41 + 32, 17, 16777215, -1);
        var8 = paddingX + 390;
        if (Server.comparator[0] == 1 && Server.reverseFlags[0] == 0) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[2].renderAt(var8, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[0].renderAt(var8, 4);
        }

        if (Server.comparator[0] == 1 && Server.reverseFlags[0] == 1) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[3].renderAt(var8 + 15, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[1].renderAt(var8 + 15, 4);
        }

        var0.drawString("Players", var8 + 32, 17, 16777215, -1);
        var39 = paddingX + 500;
        if (Server.comparator[0] == 2 && Server.reverseFlags[0] == 0) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[2].renderAt(var39, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[0].renderAt(var39, 4);
        }

        if (Server.comparator[0] == 2 && Server.reverseFlags[0] == 1) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[3].renderAt(var39 + 15, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[1].renderAt(var39 + 15, 4);
        }

        var0.drawString("Location", var39 + 32, 17, 16777215, -1);
        var36 = paddingX + 610;
        if (Server.comparator[0] == 3 && Server.reverseFlags[0] == 0) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[2].renderAt(var36, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[0].renderAt(var36, 4);
        }

        if (Server.comparator[0] == 3 && Server.reverseFlags[0] == 1) {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[3].renderAt(var36 + 15, 4);
        } else {
          StockmarketListingQuantityComparator.aDoublyNode_Sub24_Sub4Array653[1].renderAt(var36 + 15, 4);
        }

        var0.drawString("Type", var36 + 32, 17, 16777215, -1);
      }

      JagGraphics.fillRect(paddingX + 708, 4, 50, 16, 0);
      var1.method1154("Cancel", paddingX + 708 + 25, 16, 16777215, -1);
      anInt463 = -1;
      if (LoadedArchive.aSpriteArray429 != null) {
        var7 = 88;
        byte var44 = 19;
        var39 = 765 / (var7 + 1) - 1;
        var36 = 480 / (var44 + 1);

        do {
          var20 = var36;
          var21 = var39;
          if (var36 * (var39 - 1) >= Server.count) {
            --var39;
          }

          if (var39 * (var36 - 1) >= Server.count) {
            --var36;
          }

          if (var39 * (var36 - 1) >= Server.count) {
            --var36;
          }
        } while (var20 != var36 || var39 != var21);

        var20 = (765 - var39 * var7) / (var39 + 1);
        if (var20 > 5) {
          var20 = 5;
        }

        var21 = (480 - var44 * var36) / (var36 + 1);
        if (var21 > 5) {
          var21 = 5;
        }

        var22 = (765 - var7 * var39 - var20 * (var39 - 1)) / 2;
        var10 = (480 - var36 * var44 - var21 * (var36 - 1)) / 2;
        var23 = (var36 + Server.count - 1) / var36;
        anInt466 = var23 - var39;
        if (leftArrow != null && anInt460 > 0) {
          leftArrow.renderAt(8, client.canvasHeight / 2 - leftArrow.anInt377 / 2);
        }

        if (rightArrow != null && anInt460 < anInt466) {
          rightArrow.renderAt(client.canvasWidth - rightArrow.anInt378 - 8, client.canvasHeight / 2 - rightArrow.anInt377 / 2);
        }

        var12 = var10 + 23;
        var24 = var22 + paddingX;
        var19 = 0;
        boolean var25 = false;
        int var26 = anInt460;

        int var27;
        for (var27 = var36 * var26; var27 < Server.count && var26 - anInt460 < var39; ++var27) {
          Server var30 = Server.servers[var27];
          boolean var29 = true;
          String var31 = Integer.toString(var30.population);
          if (var30.population == -1) {
            var31 = "OFF";
            var29 = false;
          } else if (var30.population > 1980) {
            var31 = "FULL";
            var29 = false;
          }

          int var32 = 0;
          byte var33;
          if (var30.isTournament()) {
            if (var30.isMembers()) {
              var33 = 7;
            } else {
              var33 = 6;
            }
          } else if (var30.isDeadman()) {
            var32 = 16711680;
            if (var30.isMembers()) {
              var33 = 5;
            } else {
              var33 = 4;
            }
          } else if (var30.isPVP()) {
            if (var30.isMembers()) {
              var33 = 3;
            } else {
              var33 = 2;
            }
          } else if (var30.isMembers()) {
            var33 = 1;
          } else {
            var33 = 0;
          }

          if (Mouse.x >= var24 && Mouse.y >= var12 && Mouse.x < var7 + var24 && Mouse.y < var44 + var12 && var29) {
            anInt463 = var27;
            LoadedArchive.aSpriteArray429[var33].method834(var24, var12, 128, 16777215);
            var25 = true;
          } else {
            LoadedArchive.aSpriteArray429[var33].renderAt(var24, var12);
          }

          if (worldSelectorFlags != null) {
            worldSelectorFlags[(var30.isMembers() ? 8 : 0) + var30.location].renderAt(var24 + 29, var12);
          }

          var0.method1154(Integer.toString(var30.id), var24 + 15, var44 / 2 + var12 + 5, var32, -1);
          var1.method1154(var31, var24 + 60, var44 / 2 + var12 + 5, 268435455, -1);
          var12 = var12 + var21 + var44;
          ++var19;
          if (var19 >= var36) {
            var12 = var10 + 23;
            var24 = var24 + var7 + var20;
            var19 = 0;
            ++var26;
          }
        }

        if (var25) {
          var27 = var1.textWidth(Server.servers[anInt463].activity) + 6;
          int var28 = var1.anInt375 + 8;
          int var50 = Mouse.y + 25;
          if (var28 + var50 > 480) {
            var50 = Mouse.y - 25 - var28;
          }

          JagGraphics.fillRect(Mouse.x - var27 / 2, var50, var27, var28, 16777120);
          JagGraphics.drawRectOutline(Mouse.x - var27 / 2, var50, var27, var28, 0);
          var1.method1154(Server.servers[anInt463].activity, Mouse.x, var50 + var1.anInt375 + 4, 0, -1);
        }
      }

      client.graphicsProvider.drawGame(0, 0);
    } else {
      leftTitleSprite.renderAt(paddingX, 0);
      rightTitleSprite.renderAt(paddingX + 382, 0);
      logoSprite.renderAt(paddingX + 382 - logoSprite.anInt378 / 2, 18);
      if (client.gameState == 0 || client.gameState == 5) {
        var7 = 20;
        var0.method1154("RuneScape is loading - please wait...", credentialsBoxX + 180, 245 - var7, 16777215, -1);
        var8 = 253 - var7;
        JagGraphics.drawRectOutline(credentialsBoxX + 180 - 152, var8, 304, 34, 9179409);
        JagGraphics.drawRectOutline(credentialsBoxX + 180 - 151, var8 + 1, 302, 32, 0);
        JagGraphics.fillRect(credentialsBoxX + 180 - 150, var8 + 2, anInt473 * 3, 30, 9179409);
        JagGraphics.fillRect(anInt473 * 3 + (credentialsBoxX + 180 - 150), var8 + 2, 300 - anInt473 * 3, 30, 0);
        var0.method1154(loadingStateText, credentialsBoxX + 180, 276 - var7, 16777215, -1);
      }

      String var9;
      char[] var11;
      String var13;
      String var15;
      String var17;
      char[] var18;
      short var40;
      short var42;
      String var43;
      if (client.gameState == 20) {
        Statics49.titleboxSprite.renderAt(credentialsBoxX + 180 - Statics49.titleboxSprite.anInt378 / 2, 271 - Statics49.titleboxSprite.anInt377 / 2);
        var40 = 201;
        var0.method1154(loginResponse1, credentialsBoxX + 180, var40, 16776960, 0);
        var41 = var40 + 15;
        var0.method1154(loginResponse2, credentialsBoxX + 180, var41, 16776960, 0);
        var41 += 15;
        var0.method1154(loginResponse3, credentialsBoxX + 180, var41, 16776960, 0);
        var41 += 15;
        var41 += 7;
        if (step != 4) {
          var0.drawString("Login: ", credentialsBoxX + 180 - 110, var41, 16777215, 0);
          var42 = 200;
          if (!client.preferences.rememberMe) {
            var15 = username;
          } else {
            var9 = username;
            var10 = var9.length();
            var11 = new char[var10];

            for (var12 = 0; var12 < var10; ++var12) {
              var11[var12] = '*';
            }

            var13 = new String(var11);
            var15 = var13;
          }

          while (var0.textWidth(var15) > var42) {
            var15 = var15.substring(0, var15.length() - 1);
          }

          var0.drawString(Strings.escapeAngleBrackets(var15), credentialsBoxX + 180 - 70, var41, 16777215, 0);
          var41 += 15;
          var9 = "Password: ";
          var17 = password;
          var12 = var17.length();
          var18 = new char[var12];

          for (var19 = 0; var19 < var12; ++var19) {
            var18[var19] = '*';
          }

          var43 = new String(var18);
          var0.drawString(var9 + var43, credentialsBoxX + 180 - 108, var41, 16777215, 0);
        }
      }

      if (client.gameState == 10 || client.gameState == 11) {
        Statics49.titleboxSprite.renderAt(credentialsBoxX, 171);
        short var4;
        if (step == 0) {
          var40 = 251;
          var0.method1154("Welcome to RuneScape", credentialsBoxX + 180, var40, 16776960, 0);
          var8 = credentialsBoxX + 180 - 80;
          var4 = 291;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var0.method1149("New User", var8 - 73, var4 - 20, 144, 40, 16777215, 0, 1, 1, 0);
          var8 = credentialsBoxX + 180 + 80;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var0.method1149("Existing User", var8 - 73, var4 - 20, 144, 40, 16777215, 0, 1, 1, 0);
        } else if (step == 1) {
          var0.method1154(warningText, credentialsBoxX + 180, 201, 16776960, 0);
          var40 = 236;
          var0.method1154(loginResponse1, credentialsBoxX + 180, var40, 16777215, 0);
          var41 = var40 + 15;
          var0.method1154(loginResponse2, credentialsBoxX + 180, var41, 16777215, 0);
          var41 += 15;
          var0.method1154(loginResponse3, credentialsBoxX + 180, var41, 16777215, 0);
          var8 = credentialsBoxX + 180 - 80;
          var4 = 321;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var0.method1154("Continue", var8, var4 + 5, 16777215, 0);
          var8 = credentialsBoxX + 180 + 80;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var0.method1154("Cancel", var8, var4 + 5, 16777215, 0);
        } else if (step == 2) {
          var40 = 201;
          var0.method1154(loginResponse1, credentialsBoxCenterX, var40, 16776960, 0);
          var41 = var40 + 15;
          var0.method1154(loginResponse2, credentialsBoxCenterX, var41, 16776960, 0);
          var41 += 15;
          var0.method1154(loginResponse3, credentialsBoxCenterX, var41, 16776960, 0);
          var41 += 15;
          var41 += 7;
          var0.drawString("Login: ", credentialsBoxCenterX - 110, var41, 16777215, 0);
          var42 = 200;
          if (!client.preferences.rememberMe) {
            var15 = username;
          } else {
            var9 = username;
            var10 = var9.length();
            var11 = new char[var10];

            for (var12 = 0; var12 < var10; ++var12) {
              var11[var12] = '*';
            }

            var13 = new String(var11);
            var15 = var13;
          }

          while (var0.textWidth(var15) > var42) {
            var15 = var15.substring(1);
          }

          var0.drawString(Strings.escapeAngleBrackets(var15) + (anInt461 == 0 & client.ticks % 40 < 20 ? client.getColorTags(16776960) + "|" : ""), credentialsBoxCenterX - 70, var41, 16777215, 0);
          var41 += 15;
          var9 = "Password: ";
          var17 = password;
          var12 = var17.length();
          var18 = new char[var12];

          for (var19 = 0; var19 < var12; ++var19) {
            var18[var19] = '*';
          }

          var43 = new String(var18);
          var0.drawString(var9 + var43 + (anInt461 == 1 & client.ticks % 40 < 20 ? client.getColorTags(16776960) + "|" : ""), credentialsBoxCenterX - 108, var41, 16777215, 0);
          var40 = 277;
          var23 = credentialsBoxCenterX + -117;
          IndexedSprite var34 = ClientProt.method6(client.rememberUsername, aBoolean477);
          var34.renderAt(var23, var40);
          var23 = var23 + var34.anInt378 + 5;
          var1.drawString("Remember username", var23, var40 + 13, 16776960, 0);
          var23 = credentialsBoxCenterX + 24;
          var34 = ClientProt.method6(client.preferences.rememberMe, aBoolean472);
          var34.renderAt(var23, var40);
          var23 = var23 + var34.anInt378 + 5;
          var1.drawString("Hide username", var23, var40 + 13, 16776960, 0);
          var24 = credentialsBoxCenterX - 80;
          short var48 = 321;
          GraphicsProvider.titlebuttonSprite.renderAt(var24 - 73, var48 - 20);
          var0.method1154("Login", var24, var48 + 5, 16777215, 0);
          var24 = credentialsBoxCenterX + 80;
          GraphicsProvider.titlebuttonSprite.renderAt(var24 - 73, var48 - 20);
          var0.method1154("Cancel", var24, var48 + 5, 16777215, 0);
          var40 = 357;
            if (anInt469 == 2) {
                SerializableProcessor.aString636 = "Having trouble logging in?";
            } else {
                SerializableProcessor.aString636 = "Can't login? Click here.";
            }

          LoginScreenEffect.credentialsBoxBounds = new Bounds(credentialsBoxCenterX, var40, var1.textWidth(SerializableProcessor.aString636), 11);
          LoginProt.aBounds_846 = new Bounds(credentialsBoxCenterX, var40, var1.textWidth("Still having trouble logging in?"), 11);
          var1.method1154(SerializableProcessor.aString636, credentialsBoxCenterX, var40, 16777215, 0);
        } else if (step == 3) {
          var40 = 201;
          var0.method1154("Invalid credentials.", credentialsBoxX + 180, var40, 16776960, 0);
          var41 = var40 + 20;
          var1.method1154("For accounts created after 24th November 2010, please use your", credentialsBoxX + 180, var41, 16776960, 0);
          var41 += 15;
          var1.method1154("email address to login. Otherwise please login with your username.", credentialsBoxX + 180, var41, 16776960, 0);
          var8 = credentialsBoxX + 180;
          var4 = 276;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var2.method1154("Try again", var8, var4 + 5, 16777215, 0);
          var8 = credentialsBoxX + 180;
          var4 = 326;
          GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
          var2.method1154("Forgotten password?", var8, var4 + 5, 16777215, 0);
        } else {
          String var16;
          if (step == 4) {
            var0.method1154("Authenticator", credentialsBoxX + 180, 201, 16776960, 0);
            var40 = 236;
            var0.method1154(loginResponse1, credentialsBoxX + 180, var40, 16777215, 0);
            var41 = var40 + 15;
            var0.method1154(loginResponse2, credentialsBoxX + 180, var41, 16777215, 0);
            var41 += 15;
            var0.method1154(loginResponse3, credentialsBoxX + 180, var41, 16777215, 0);
            var41 += 15;
            var16 = "PIN: ";
            String totp = Login.totp;
            var22 = totp.length();
            char[] var46 = new char[var22];

            for (var23 = 0; var23 < var22; ++var23) {
              var46[var23] = '*';
            }

            var9 = new String(var46);
            var0.drawString(var16 + var9 + (client.ticks % 40 < 20 ? client.getColorTags(16776960) + "|" : ""), credentialsBoxX + 180 - 108, var41, 16777215, 0);
            var41 -= 8;
            var0.drawString("Trust this computer", credentialsBoxX + 180 - 9, var41, 16776960, 0);
            var41 += 15;
            var0.drawString("for 30 days: ", credentialsBoxX + 180 - 9, var41, 16776960, 0);
            var21 = credentialsBoxX + 180 - 9 + var0.textWidth("for 30 days: ") + 15;
            var22 = var41 - var0.anInt375;
            IndexedSprite var47;
            if (aBoolean462) {
              var47 = aDoublyNode_Sub24_Sub4_288;
            } else {
              var47 = aDoublyNode_Sub24_Sub4_1148;
            }

            var47.renderAt(var21, var22);
            var23 = credentialsBoxX + 180 - 80;
            short var45 = 321;
            GraphicsProvider.titlebuttonSprite.renderAt(var23 - 73, var45 - 20);
            var0.method1154("Continue", var23, var45 + 5, 16777215, 0);
            var23 = credentialsBoxX + 180 + 80;
            GraphicsProvider.titlebuttonSprite.renderAt(var23 - 73, var45 - 20);
            var0.method1154("Cancel", var23, var45 + 5, 16777215, 0);
            var1.method1154("<u=ff>Can't Log In?</u>", credentialsBoxX + 180, var45 + 36, 255, 0);
          } else {
            short var49;
            if (step == 5) {
              var0.method1154("Forgotten your password?", credentialsBoxX + 180, 201, 16776960, 0);
              var40 = 221;
              var2.method1154(loginResponse1, credentialsBoxX + 180, var40, 16776960, 0);
              var41 = var40 + 15;
              var2.method1154(loginResponse2, credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var2.method1154(loginResponse3, credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var41 += 14;
              var0.drawString("Username/email: ", credentialsBoxX + 180 - 145, var41, 16777215, 0);
              var42 = 174;
              if (!client.preferences.rememberMe) {
                var15 = username;
              } else {
                var9 = username;
                var10 = var9.length();
                var11 = new char[var10];

                for (var12 = 0; var12 < var10; ++var12) {
                  var11[var12] = '*';
                }

                var13 = new String(var11);
                var15 = var13;
              }

              while (var0.textWidth(var15) > var42) {
                var15 = var15.substring(1);
              }

              var0.drawString(Strings.escapeAngleBrackets(var15) + (client.ticks % 40 < 20 ? client.getColorTags(16776960) + "|" : ""), credentialsBoxX + 180 - 34, var41, 16777215, 0);
              var20 = credentialsBoxX + 180 - 80;
              var49 = 321;
              GraphicsProvider.titlebuttonSprite.renderAt(var20 - 73, var49 - 20);
              var0.method1154("Recover", var20, var49 + 5, 16777215, 0);
              var20 = credentialsBoxX + 180 + 80;
              GraphicsProvider.titlebuttonSprite.renderAt(var20 - 73, var49 - 20);
              var0.method1154("Back", var20, var49 + 5, 16777215, 0);
              var49 = 356;
              var1.method1154("Still having trouble logging in?", credentialsBoxCenterX, var49, 268435455, 0);
            } else if (step == 6) {
              var40 = 201;
              var0.method1154(loginResponse1, credentialsBoxX + 180, var40, 16776960, 0);
              var41 = var40 + 15;
              var0.method1154(loginResponse2, credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var0.method1154(loginResponse3, credentialsBoxX + 180, var41, 16776960, 0);
              var8 = credentialsBoxX + 180;
              var4 = 321;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Back", var8, var4 + 5, 16777215, 0);
            } else if (step == 7) {
              var40 = 216;
              var0.method1154("Your date of birth isn't set.", credentialsBoxX + 180, var40, 16776960, 0);
              var41 = var40 + 15;
              var2.method1154("Please verify your account status by", credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var2.method1154("setting your date of birth.", credentialsBoxX + 180, var41, 16776960, 0);
              var8 = credentialsBoxX + 180 - 80;
              var4 = 321;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Set Date of Birth", var8, var4 + 5, 16777215, 0);
              var8 = credentialsBoxX + 180 + 80;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Back", var8, var4 + 5, 16777215, 0);
            } else if (step == 8) {
              var40 = 216;
              var0.method1154("Sorry, but your account is not eligible to play.", credentialsBoxX + 180, var40, 16776960, 0);
              var41 = var40 + 15;
              var2.method1154("For more information, please take a look at", credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var2.method1154("our privacy policy.", credentialsBoxX + 180, var41, 16776960, 0);
              var8 = credentialsBoxX + 180 - 80;
              var4 = 321;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Privacy Policy", var8, var4 + 5, 16777215, 0);
              var8 = credentialsBoxX + 180 + 80;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Back", var8, var4 + 5, 16777215, 0);
            } else if (step == 12) {
              var40 = 201;
              String var3 = "";
              var16 = "";
              var15 = "";
              switch (anInt468) {
                case 0:
                  var3 = "Your account has been disabled.";
                  var16 = Statics24.aString1419;
                  var15 = "";
                  break;
                case 1:
                  var3 = "Account locked as we suspect it has been stolen.";
                  var16 = Statics24.aString1414;
                  var15 = "";
                  break;
                default:
                  method1500(false);
              }

              var0.method1154(var3, credentialsBoxX + 180, var40, 16776960, 0);
              var41 = var40 + 15;
              var2.method1154(var16, credentialsBoxX + 180, var41, 16776960, 0);
              var41 += 15;
              var2.method1154(var15, credentialsBoxX + 180, var41, 16776960, 0);
              var20 = credentialsBoxX + 180;
              var49 = 276;
              GraphicsProvider.titlebuttonSprite.renderAt(var20 - 73, var49 - 20);
              var0.method1154("Support Page", var20, var49 + 5, 16777215, 0);
              var20 = credentialsBoxX + 180;
              var49 = 326;
              GraphicsProvider.titlebuttonSprite.renderAt(var20 - 73, var49 - 20);
              var0.method1154("Back", var20, var49 + 5, 16777215, 0);
            } else if (step == 24) {
              var40 = 221;
              var0.method1154(loginResponse1, credentialsBoxX + 180, var40, 16777215, 0);
              var41 = var40 + 15;
              var0.method1154(loginResponse2, credentialsBoxX + 180, var41, 16777215, 0);
              var41 += 15;
              var0.method1154(loginResponse3, credentialsBoxX + 180, var41, 16777215, 0);
              var8 = credentialsBoxX + 180;
              var4 = 301;
              GraphicsProvider.titlebuttonSprite.renderAt(var8 - 73, var4 - 20);
              var0.method1154("Ok", var8, var4 + 5, 16777215, 0);
            }
          }
        }
      }

      if (client.gameState >= 10) {
        int[] var6 = new int[4];
        JagGraphics.method1366(var6);

        JagGraphics.setClip(paddingX, 0, paddingX + 765, client.canvasHeight);
        AsyncOutputStream.loginScreenEffect.render(paddingX - 22, client.ticks);
        AsyncOutputStream.loginScreenEffect.render(paddingX + 22 + 765 - 128, client.ticks);
        JagGraphics.method1373(var6);
      }

      AssociateComparator_Sub2.titleMuteSprites[client.preferences.loginScreenAudioDisabled ? 1 : 0].renderAt(paddingX + 765 - 40, 463);
      if (client.gameState > 5 && ClientLocale.GB == WorldMapLabelSize.locale) {
        if (slbutton != null) {
          var41 = paddingX + 5;
          var42 = 463;
          byte var38 = 100;
          byte var5 = 35;
          slbutton.renderAt(var41, var42);
          var0.method1154("World" + " " + client.currentWorld, var38 / 2 + var41, var5 / 2 + var42 - 2, 16777215, 0);
          if (Server.request != null) {
            var1.method1154("Loading...", var38 / 2 + var41, var5 / 2 + var42 + 12, 16777215, 0);
          } else {
            var1.method1154("Click to switch", var38 / 2 + var41, var5 / 2 + var42 + 12, 16777215, 0);
          }
        } else {
          slbutton = WorldMapArea.loadIndexedSprite(Archive.sprites, "sl_button", "");
        }
      }

    }
  }

  public static void method23() {
    if (username == null || username.length() <= 0) {
      if (client.preferences.rememberedUsername != null) {
        username = client.preferences.rememberedUsername;
        client.rememberUsername = true;
      } else {
        client.rememberUsername = false;
      }

    }
  }

  public static void method1500(boolean var0) {
    loginResponse1 = "";
    loginResponse2 = "Enter your username/email & password.";
    loginResponse3 = "";
    step = 2;
    if (var0) {
      password = "";
    }

    method23();
    if (client.rememberUsername && username != null && username.length() > 0) {
      anInt461 = 1;
    } else {
      anInt461 = 0;
    }

  }

  public static void processLoadingScreen() {
    if (client.bootState == 0) {
      client.sceneGraph = new SceneGraph(4, 104, 104, SceneGraphRenderData.tileHeights);

      for (int var0 = 0; var0 < 4; ++var0) {
        client.collisionMaps[var0] = new CollisionMap(104, 104);
      }

      SceneGraph.minimapSprite = new Sprite(512, 512);
      loadingStateText = "Starting game engine...";
      anInt473 = 5;
      client.bootState = 20;
    } else if (client.bootState == 20) {
      loadingStateText = "Prepared visibility map";
      anInt473 = 10;
      client.bootState = 30;
    } else if (client.bootState == 30) {
      Archive.skeletons = ReferenceTable.get(0, false, true);
      Archive.skins = ReferenceTable.get(1, false, true);
      Archive.config = ReferenceTable.get(2, true, false);
      Archive.interfaces = ReferenceTable.get(3, false, true);
      Archive.audioEffects = ReferenceTable.get(4, false, true);
      Archive.landscape = ReferenceTable.get(5, true, true);
      Archive.audioTracks = ReferenceTable.get(6, true, true);
      Archive.models = ReferenceTable.get(7, false, true);
      Archive.sprites = ReferenceTable.get(8, false, true);
      Archive.textures = ReferenceTable.get(9, false, true);
      Archive.huffman = ReferenceTable.get(10, false, true);
      Archive.audioTracks2 = ReferenceTable.get(11, false, true);
      Archive.cs2 = ReferenceTable.get(12, false, true);
      Archive.fonts = ReferenceTable.get(13, true, false);
      Archive.audioEffects2 = ReferenceTable.get(14, false, true);
      Archive.audioEffects3 = ReferenceTable.get(15, false, true);
      Archive.bootSprites = ReferenceTable.get(17, true, true);
      Archive.mapscene = ReferenceTable.get(18, false, true);
      Archive.worldmap = ReferenceTable.get(19, false, true);
      Archive.mapland = ReferenceTable.get(20, false, true);
      loadingStateText = "Connecting to update server";
      anInt473 = 20;
      client.bootState = 40;
    } else if (client.bootState == 40) {
      byte var30 = 0;
      int progress = var30 + Archive.skeletons.method494() * 4 / 100;
      progress += Archive.skins.method494() * 4 / 100;
      progress += Archive.config.method494() * 2 / 100;
      progress += Archive.interfaces.method494() * 2 / 100;
      progress += Archive.audioEffects.method494() * 6 / 100;
      progress += Archive.landscape.method494() * 4 / 100;
      progress += Archive.audioTracks.method494() * 2 / 100;
      progress += Archive.models.method494() * 56 / 100;
      progress += Archive.sprites.method494() * 2 / 100;
      progress += Archive.textures.method494() * 2 / 100;
      progress += Archive.huffman.method494() * 2 / 100;
      progress += Archive.audioTracks2.method494() * 2 / 100;
      progress += Archive.cs2.method494() * 2 / 100;
      progress += Archive.fonts.method494() * 2 / 100;
      progress += Archive.audioEffects2.method494() * 2 / 100;
      progress += Archive.audioEffects3.method494() * 2 / 100;
      progress += Archive.worldmap.method494() / 100;
      progress += Archive.mapscene.method494() / 100;
      progress += Archive.mapland.method494() / 100;
      progress += Archive.bootSprites.isLoaded() && Archive.bootSprites.method908() ? 1 : 0;
      if (progress != 100) {
        if (progress != 0) {
          loadingStateText = "Checking for updates - " + progress + "%";
        }

        anInt473 = 30;
      } else {
        LoadedArchive.add(Archive.skeletons);
        LoadedArchive.add(Archive.skins);
        LoadedArchive.add(Archive.audioEffects);
        LoadedArchive.add(Archive.landscape);
        LoadedArchive.add(Archive.audioTracks);
        LoadedArchive.add(Archive.models);
        LoadedArchive.add(Archive.sprites);
        LoadedArchive.add(Archive.audioTracks2);
        LoadedArchive.add(Archive.audioEffects2);
        LoadedArchive.add(Archive.audioEffects3);
        LoadedArchive.add(Archive.worldmap);
        LoadedArchive.add(Archive.mapscene);
        LoadedArchive.add(Archive.mapland);
        WorldMapTileDecor_Sub2.aBootSprites_647 = new BootSprites();
        WorldMapTileDecor_Sub2.aBootSprites_647.decode(Archive.bootSprites);
        loadingStateText = "Loaded update list";
        anInt473 = 30;
        client.bootState = 45;
      }
    } else if (client.bootState == 45) {
      boolean var28 = !client.lowMemory;
      URLRequest.audioSampleRate = 22050;
      AudioSystem.useTwoChannels = var28;
      anInt603 = 2;
      PcmStream_Sub3 pcmstream = new PcmStream_Sub3();
      pcmstream.init(9, 128);
      AudioSystem.anAudioSystem1236 = AudioSystem.create(0, 22050);
      AudioSystem.anAudioSystem1236.method1330(pcmstream);
      Archive eff3 = Archive.audioEffects3;
      Archive eff2 = Archive.audioEffects2;
      Archive eff = Archive.audioEffects;
      Statics57.aReferenceTable1160 = eff3;
      SecureRandomCallable.aReferenceTable382 = eff2;
      Statics57.aReferenceTable1159 = eff;
      Statics50.aClass5_Sub6_Sub3_326 = pcmstream;
      AudioSystem.anAudioSystem599 = AudioSystem.create(1, 2048);
      WorldMapLabelSize.aClass5_Sub6_Sub1_528 = new PcmStream_Sub1();
      AudioSystem.anAudioSystem599.method1330(WorldMapLabelSize.aClass5_Sub6_Sub1_528);
      Statics46.aClass98_446 = new Decimator(22050, URLRequest.audioSampleRate);
      loadingStateText = "Prepared sound engine";
      anInt473 = 35;
      client.bootState = 50;
      NamedFont.cache = new FontCache(Archive.sprites, Archive.fonts);
    } else if (client.bootState == 50) {
      int expectedFontCount = NamedFont.values().length;
      client.fonts = NamedFont.cache.lookup(NamedFont.values());
      if (client.fonts.size() < expectedFontCount) {
        loadingStateText = "Loading fonts - " + client.fonts.size() * 100 / expectedFontCount + "%";
        anInt473 = 40;
      } else {
        Font.p11 = (Font) client.fonts.get(NamedFont.P11);
        Font.p12full = (Font) client.fonts.get(NamedFont.P12);
        Font.b12full = (Font) client.fonts.get(NamedFont.B12);
        client.operatingSystemNode = client.operatingSystemProvider.provide();
        loadingStateText = "Loaded fonts";
        anInt473 = 40;
        client.bootState = 60;
      }
    } else {
      if (client.bootState == 60) {
        Archive huffman = Archive.huffman;
        Archive sprites = Archive.sprites;
        int spritesLoaded = 0;
        if (huffman.load("title.jpg", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("logo", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("logo_deadman_mode", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("titlebox", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("titlebutton", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("runes", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("title_mute", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("options_radio_buttons,0", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("options_radio_buttons,2", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("options_radio_buttons,4", "")) {
          ++spritesLoaded;
        }

        if (sprites.load("options_radio_buttons,6", "")) {
          ++spritesLoaded;
        }

        sprites.load("sl_back", "");
        sprites.load("sl_flags", "");
        sprites.load("sl_arrows", "");
        sprites.load("sl_stars", "");
        sprites.load("sl_button", "");
        byte expectedSpriteCount = 11;
        if (spritesLoaded < expectedSpriteCount) {
          loadingStateText = "Loading title screen - " + spritesLoaded * 100 / expectedSpriteCount + "%";
          anInt473 = 50;
        } else {
          loadingStateText = "Loaded title screen";
          anInt473 = 50;
          client.setGameState(5);
          client.bootState = 70;
        }
      } else if (client.bootState == 70) {
        if (!Archive.config.method908()) {
          loadingStateText = "Loading config - " + Archive.config.method489() + "%";
          anInt473 = 60;
        } else {
          TileOverlay.table = Archive.config;
          TileUnderlay.table = Archive.config;
          Archive cfg = Archive.config;
          Archive models = Archive.models;
          IdentikitDefinition.table = cfg;
          StockmarketListingWorldComparator.aReferenceTable350 = models;
          PlayerModel.identikitCount = IdentikitDefinition.table.getFileCount(3);
          boolean lowMemory = client.lowMemory;
          ObjectDefinition.configTable = cfg;
          ObjectDefinition.modelsTable = models;
          ObjectDefinition.aBoolean792 = lowMemory;
          NpcDefinition.table = cfg;
          NpcDefinition.modelTable = models;
          StructDefinition.table = Archive.config;
          ItemDefinition.method240(Archive.config, Archive.models, client.membersWorld, Font.p11);
          AnimationSequence.method1202(Archive.config, Archive.skeletons, Archive.skins);
          Statics52.aReferenceTable500 = cfg;
          EffectAnimation.table = models;
          StockmarketListingNameComparator.method327(Archive.config);
          VarDefinition.table = Archive.config;
          VarDefinition.count = VarDefinition.table.getFileCount(16);
          MouseRecorder.method263(Archive.interfaces, Archive.models, Archive.sprites, Archive.fonts);
          InventoryDefinition.setTable(Archive.config);
          EnumDefinition.table = Archive.config;
          VarcInteger.table = Archive.config;
          ParameterDefinition.table = Archive.config;
          client.varcs = new Varcs();
          HitsplatDefinition.method88(Archive.config, Archive.sprites, Archive.fonts);
          HealthBarDefinition.method296(Archive.config, Archive.sprites);
          WorldMapFunction.table = Archive.sprites;
          if (cfg.method908()) {
            WorldMapFunction.count = cfg.getFileCount(35);
            WorldMapFunction.loaded = new WorldMapFunction[WorldMapFunction.count];
            for (int i = 0; i < WorldMapFunction.count; ++i) {
              byte[] data = cfg.unpack(35, i);
              WorldMapFunction.loaded[i] = new WorldMapFunction(i);
              if (data != null) {
                WorldMapFunction.loaded[i].decode(new Buffer(data));
                WorldMapFunction.loaded[i].method775();
              }
            }
          }

          loadingStateText = "Loaded config";
          anInt473 = 60;
          client.bootState = 80;
        }
      } else if (client.bootState == 80) {
        int loadedSprites = 0;
        if (AttackOptionPriority.compass == null) {
          AttackOptionPriority.compass = Sprite.get(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.compass, 0);
        } else {
          ++loadedSprites;
        }

        if (PendingSpawn.mapedge == null) {
          PendingSpawn.mapedge = Sprite.get(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1658, 0);
        } else {
          ++loadedSprites;
        }

        if (Statics52.mapscene == null) {
          Statics52.mapscene = Statics38.method1194(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.mapScenes, 0);
        } else {
          ++loadedSprites;
        }

        if (StructDefinition.skullIconSprites == null) {
          StructDefinition.skullIconSprites = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.skullIcons, 0);
        } else {
          ++loadedSprites;
        }

        if (WorldMapChunkDefinition.prayerIconSprites == null) {
          WorldMapChunkDefinition.prayerIconSprites = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.prayerIcons, 0);
        } else {
          ++loadedSprites;
        }

        if (HintArrow.overheadSprites == null) {
          HintArrow.overheadSprites = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1648, 0);
        } else {
          ++loadedSprites;
        }

        if (Sprite.mapfunctions == null) {
          Sprite.mapfunctions = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1649, 0);
        } else {
          ++loadedSprites;
        }

        if (HintArrow.sprites == null) {
          HintArrow.sprites = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1657, 0);
        } else {
          ++loadedSprites;
        }

        if (Statics47.mapmarkers == null) {
          Statics47.mapmarkers = Canvas.method643(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1654, 0);
        } else {
          ++loadedSprites;
        }

        if (SerializableString.scrollbars == null) {
          SerializableString.scrollbars = Statics38.method1194(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1655, 0);
        } else {
          ++loadedSprites;
        }

        if (WorldMapTileDecor_Sub2.aDoublyNode_Sub24_Sub4Array648 == null) {
          WorldMapTileDecor_Sub2.aDoublyNode_Sub24_Sub4Array648 = Statics38.method1194(Archive.sprites, WorldMapTileDecor_Sub2.aBootSprites_647.anInt1651, 0);
        } else {
          ++loadedSprites;
        }

        if (loadedSprites < 11) {
          loadingStateText = "Loading sprites - " + loadedSprites * 100 / 12 + "%";
          anInt473 = 70;
        } else {
          BaseFont.aDoublyNode_Sub24_Sub4Array1573 = WorldMapTileDecor_Sub2.aDoublyNode_Sub24_Sub4Array648;
          PendingSpawn.mapedge.method775();
          int var24 = (int) (Math.random() * 21.0D) - 10;
          int var25 = (int) (Math.random() * 21.0D) - 10;
          int var5 = (int) (Math.random() * 21.0D) - 10;
          int var6 = (int) (Math.random() * 41.0D) - 20;
          Statics52.mapscene[0].method1325(var6 + var24, var6 + var25, var6 + var5);
          loadingStateText = "Loaded sprites";
          anInt473 = 70;
          client.bootState = 90;
        }
      } else if (client.bootState == 90) {
        if (!Archive.textures.method908()) {
          loadingStateText = "Loading textures - " + "0%";
          anInt473 = 90;
        } else {
          Archive.materialProvider = new DefaultMaterialProvider(Archive.textures, Archive.sprites, 20, 0.8D, client.lowMemory ? 64 : 128);
          JagGraphics3D.setMaterialProvider(Archive.materialProvider);
          JagGraphics3D.generateColorPalette(0.8D);
          client.bootState = 100;
        }
      } else if (client.bootState == 100) {
        int perc = Archive.materialProvider.loadPercent();
        if (perc < 100) {
          loadingStateText = "Loading textures - " + perc + "%";
          anInt473 = 90;
        } else {
          loadingStateText = "Loaded textures";
          anInt473 = 90;
          client.bootState = 110;
        }
      } else if (client.bootState == 110) {
        client.mouseRecorder = new MouseRecorder();
        client.taskProcessor.method697(client.mouseRecorder, 10);
        loadingStateText = "Loaded input handler";
        anInt473 = 92;
        client.bootState = 120;
      } else if (client.bootState == 120) {
        if (!Archive.huffman.load("huffman", "")) {
          loadingStateText = "Loading wordpack - " + 0 + "%";
          anInt473 = 94;
        } else {
          Huffman.instance = new Huffman(Archive.huffman.unpack("huffman", ""));
          loadingStateText = "Loaded wordpack";
          anInt473 = 94;
          client.bootState = 130;
        }
      } else if (client.bootState == 130) {
        if (!Archive.interfaces.method908()) {
          loadingStateText = "Loading interfaces - " + Archive.interfaces.method489() * 4 / 5 + "%";
          anInt473 = 96;
        } else if (!Archive.cs2.method908()) {
          loadingStateText = "Loading interfaces - " + (80 + Archive.cs2.method489() / 6) + "%";
          anInt473 = 96;
        } else if (!Archive.fonts.method908()) {
          loadingStateText = "Loading interfaces - " + (96 + Archive.fonts.method489() / 50) + "%";
          anInt473 = 96;
        } else {
          loadingStateText = "Loaded interfaces";
          anInt473 = 98;
          client.bootState = 140;
        }
      } else if (client.bootState == 140) {
        anInt473 = 100;
        if (!Archive.worldmap.method909(WorldMapCacheFeature.DETAILS.name)) {
          loadingStateText = "Loading world map - " + Archive.worldmap.method895(WorldMapCacheFeature.DETAILS.name) / 10 + "%";
        } else {
          if (client.worldMap == null) {
            client.worldMap = new WorldMap();
            client.worldMap.initialize(Archive.worldmap, Archive.mapscene, Archive.mapland, Font.b12full, client.fonts, Statics52.mapscene);
          }

          loadingStateText = "Loaded world map";
          client.bootState = 150;
        }
      } else if (client.bootState == 150) {
        client.setGameState(10);
      }
    }
  }

  public static void processResponseCode(int code) {
    if (code == -3) {
      setMessages("Connection timed out.", "Please try using a different world.", "");
    } else if (code == -2) {
      setMessages("", "Error connecting to server.", "");
    } else if (code == -1) {
      setMessages("No response from server.", "Please try using a different world.", "");
    } else if (code == 3) {
      step = 3;
      anInt469 = 1;
    } else if (code == 4) {
      step = 12;
      anInt468 = 0;
    } else if (code == 5) {
      anInt469 = 2;
      setMessages("Your account has not logged out from its last", "session or the server is too busy right now.", "Please try again in a few minutes.");
    } else if (code == 68 || !client.mobile && code == 6) {
      setMessages("RuneScape has been updated!", "Please reload this page.", "");
    } else if (code == 7) {
      setMessages("This world is full.", "Please use a different world.", "");
    } else if (code == 8) {
      setMessages("Unable to connect.", "Login server offline.", "");
    } else if (code == 9) {
      setMessages("Login limit exceeded.", "Too many connections from your address.", "");
    } else if (code == 10) {
      setMessages("Unable to connect.", "Bad session id.", "");
    } else if (code == 11) {
      setMessages("We suspect someone knows your password.", "Press 'change your password' on front page.", "");
    } else if (code == 12) {
      setMessages("You need a members account to login to this world.", "Please subscribe, or use a different world.", "");
    } else if (code == 13) {
      setMessages("Could not complete login.", "Please try using a different world.", "");
    } else if (code == 14) {
      setMessages("The server is being updated.", "Please wait 1 minute and try again.", "");
    } else if (code == 16) {
      setMessages("Too many login attempts.", "Please wait a few minutes before trying again.", "");
    } else if (code == 17) {
      setMessages("You are standing in a members-only area.", "To play on this world move to a free area first", "");
    } else if (code == 18) {
      step = 12;
      anInt468 = 1;
    } else if (code == 19) {
      setMessages("This world is running a closed Beta.", "Sorry invited players only.", "Please use a different world.");
    } else if (code == 20) {
      setMessages("Invalid loginserver requested.", "Please try using a different world.", "");
    } else if (code == 22) {
      setMessages("Malformed login packet.", "Please try again.", "");
    } else if (code == 23) {
      setMessages("No reply from loginserver.", "Please wait 1 minute and try again.", "");
    } else if (code == 24) {
      setMessages("Error loading your profile.", "Please contact customer support.", "");
    } else if (code == 25) {
      setMessages("Unexpected loginserver response.", "Please try using a different world.", "");
    } else if (code == 26) {
      setMessages("This computers address has been blocked", "as it was used to break our rules.", "");
    } else if (code == 27) {
      setMessages("", "Service unavailable.", "");
    } else if (code == 31) {
      setMessages("Your account must have a displayname set", "in order to play the game.  Please set it", "via the website, or the main game.");
    } else if (code == 32) {
      setMessages("Your attempt to log into your account was", "unsuccessful.  Don't worry, you can sort", "this out by visiting the billing system.");
    } else if (code == 37) {
      setMessages("Your account is currently inaccessible.", "Please try again in a few minutes.", "");
    } else if (code == 38) {
      setMessages("You need to vote to play!", "Visit runescape.com and vote,", "and then come back here!");
    } else if (code == 55) {
      step = 8;
    } else {
      if (code == 56) {
        setMessages("Enter the 6-digit code generated by your", "authenticator app.", "");
        client.setGameState(11);
        return;
      }

      if (code == 57) {
        setMessages("The code you entered was incorrect.", "Please try again.", "");
        client.setGameState(11);
        return;
      }

      if (code == 61) {
        step = 7;
      } else {
        setMessages("Unexpected server response", "Please try using a different world.", "");
      }
    }

    client.setGameState(10);
  }

  public static void setMessages(String var0, String var1, String var2) {
    loginResponse1 = var0;
    loginResponse2 = var1;
    loginResponse3 = var2;
  }

  public static void processLoginScreen() {
    int var6;
    if (worldSelectorOpen) {
      while (true) {
        if (!Keyboard.isKeyHeld()) {
          if (Mouse.clickMeta != 1 && (WorldMapObjectIcon.mouseCameraEnabled || Mouse.clickMeta != 4)) {
            break;
          }

          int var1 = paddingX + 280;
          if (Mouse.clickX >= var1 && Mouse.clickX <= var1 + 14 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(0, 0);
            break;
          }

          if (Mouse.clickX >= var1 + 15 && Mouse.clickX <= var1 + 80 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(0, 1);
            break;
          }

          int var2 = paddingX + 390;
          if (Mouse.clickX >= var2 && Mouse.clickX <= var2 + 14 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(1, 0);
            break;
          }

          if (Mouse.clickX >= var2 + 15 && Mouse.clickX <= var2 + 80 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(1, 1);
            break;
          }

          int var17 = paddingX + 500;
          if (Mouse.clickX >= var17 && Mouse.clickX <= var17 + 14 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(2, 0);
            break;
          }

          if (Mouse.clickX >= var17 + 15 && Mouse.clickX <= var17 + 80 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(2, 1);
            break;
          }

          var6 = paddingX + 610;
          if (Mouse.clickX >= var6 && Mouse.clickX <= var6 + 14 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(3, 0);
            break;
          }

          if (Mouse.clickX >= var6 + 15 && Mouse.clickX <= var6 + 80 && Mouse.clickY >= 4 && Mouse.clickY <= 18) {
            Server.sort(3, 1);
            break;
          }

          if (Mouse.clickX >= paddingX + 708 && Mouse.clickY >= 4 && Mouse.clickX <= paddingX + 708 + 50 && Mouse.clickY <= 20) {
            method1402();
            break;
          }

          if (anInt463 != -1) {
            Server var7 = Server.servers[anInt463];
            Server.setCurrent(var7);
            method1402();
          } else {
            if (anInt460 > 0 && leftArrow != null && Mouse.clickX >= 0 && Mouse.clickX <= leftArrow.anInt378 && Mouse.clickY >= client.canvasHeight / 2 - 50 && Mouse.clickY <= client.canvasHeight / 2 + 50) {
              --anInt460;
            }

            if (anInt460 < anInt466 && rightArrow != null && Mouse.clickX >= client.canvasWidth - rightArrow.anInt378 - 5 && Mouse.clickX <= client.canvasWidth && Mouse.clickY >= client.canvasHeight / 2 - 50 && Mouse.clickY <= client.canvasHeight / 2 + 50) {
              ++anInt460;
            }
          }
          break;
        }

        if (SecureRandomService.anInt457 == 13) {
          method1402();
          break;
        }

        if (SecureRandomService.anInt457 == 96) {
          if (anInt460 > 0 && leftArrow != null) {
            --anInt460;
          }
        } else if (SecureRandomService.anInt457 == 97 && anInt460 < anInt466 && rightArrow != null) {
          ++anInt460;
        }
      }

    } else {
      if ((Mouse.clickMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.clickMeta == 4) && Mouse.clickX >= paddingX + 765 - 50 && Mouse.clickY >= 453) {
        client.preferences.loginScreenAudioDisabled = !client.preferences.loginScreenAudioDisabled;
        ClientPreferences.method854();
        if (!client.preferences.loginScreenAudioDisabled) {
          Class114.method674(Archive.audioTracks, "scape main", "", 255, false);
        } else {
          Statics57.method533();
        }
      }

      if (client.gameState != 5) {
        if (aLong467 == -1L) {
          aLong467 = Clock.now() + 1000L;
        }

        long var4 = Clock.now();
        boolean var3;
        if (client.archives != null && client.anInt926 < client.archives.size()) {
          while (true) {
            if (client.anInt926 >= client.archives.size()) {
              var3 = true;
              break;
            }

            LoadedArchive var13 = client.archives.get(client.anInt926);
            if (!var13.method291()) {
              var3 = false;
              break;
            }

            ++client.anInt926;
          }
        } else {
          var3 = true;
        }

        if (var3 && aLong465 == -1L) {
          aLong465 = var4;
          if (aLong465 > aLong467) {
            aLong467 = aLong465;
          }
        }

        if (client.gameState == 10 || client.gameState == 11) {
          if (WorldMapLabelSize.locale == ClientLocale.GB) {
            if (Mouse.clickMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.clickMeta == 4) {
              var6 = paddingX + 5;
              short var8 = 463;
              byte var9 = 100;
              byte var10 = 35;
              if (Mouse.clickX >= var6 && Mouse.clickX <= var9 + var6 && Mouse.clickY >= var8 && Mouse.clickY <= var8 + var10) {
                if (Server.load()) {
                  worldSelectorOpen = true;
                  anInt460 = 0;
                  anInt466 = 0;
                }

                return;
              }
            }

            if (Server.request != null && Server.load()) {
              worldSelectorOpen = true;
              anInt460 = 0;
              anInt466 = 0;
            }
          }

          var6 = Mouse.clickMeta;
          int var18 = Mouse.clickX;
          int var19 = Mouse.clickY;
          if (var6 == 0) {
            var18 = Mouse.x;
            var19 = Mouse.y;
          }

          if (!WorldMapObjectIcon.mouseCameraEnabled && var6 == 4) {
            var6 = 1;
          }

          int var11;
          short var12;
          if (step == 0) {
            boolean var20 = false;

            while (Keyboard.isKeyHeld()) {
              if (SecureRandomService.anInt457 == 84) {
                var20 = true;
              }
            }

            var11 = credentialsBoxCenterX - 80;
            var12 = 291;
            if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20) {
              Browser.openURL0(GameShell.buildUrl("secure", true) + "m=account-creation/g=oldscape/create_account_funnel.ws", true, false);
            }

            var11 = credentialsBoxCenterX + 80;
            if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20 || var20) {
              if ((client.currentWorldMask & 33554432) != 0) {
                warningText = "";
                loginResponse1 = "This is a <col=00ffff>Beta<col=ffffff> world.";
                loginResponse2 = "Your normal account will not be affected.";
                loginResponse3 = "";
                step = 1;
                if (client.rememberUsername && username != null && username.length() > 0) {
                  anInt461 = 1;
                } else {
                  anInt461 = 0;
                }
              } else if ((client.currentWorldMask & 4) != 0) {
                if ((client.currentWorldMask & 1024) != 0) {
                  loginResponse1 = "This is a <col=ffff00>High Risk <col=ff0000>PvP<col=ffffff> world.";
                  loginResponse2 = "Players can attack each other almost everywhere";
                  loginResponse3 = "and the Protect Item prayer won't work.";
                } else {
                  loginResponse1 = "This is a <col=ff0000>PvP<col=ffffff> world.";
                  loginResponse2 = "Players can attack each other";
                  loginResponse3 = "almost everywhere.";
                }

                warningText = "Warning!";
                step = 1;
                if (client.rememberUsername && username != null && username.length() > 0) {
                  anInt461 = 1;
                } else {
                  anInt461 = 0;
                }
              } else if ((client.currentWorldMask & 1024) != 0) {
                loginResponse1 = "This is a <col=ffff00>High Risk<col=ffffff> world.";
                loginResponse2 = "The Protect Item prayer will";
                loginResponse3 = "not work on this world.";
                warningText = "Warning!";
                step = 1;
                if (client.rememberUsername && username != null && username.length() > 0) {
                  anInt461 = 1;
                } else {
                  anInt461 = 0;
                }
              } else {
                method1500(false);
              }
            }
          } else {
            int var21;
            short var23;
            if (step != 1) {
              boolean var14;
              int var15;
              short var22;
              if (step == 2) {
                var22 = 201;
                var21 = var22 + 52;
                if (var6 == 1 && var19 >= var21 - 12 && var19 < var21 + 2) {
                  anInt461 = 0;
                }

                var21 += 15;
                if (var6 == 1 && var19 >= var21 - 12 && var19 < var21 + 2) {
                  anInt461 = 1;
                }

                var22 = 361;
                if (LoginScreenEffect.credentialsBoxBounds != null) {
                  var11 = LoginScreenEffect.credentialsBoxBounds.width / 2;
                  if (var6 == 1 && var18 >= LoginScreenEffect.credentialsBoxBounds.x - var11 && var18 <= var11 + LoginScreenEffect.credentialsBoxBounds.x && var19 >= var22 - 15 && var19 < var22) {
                    switch (anInt469) {
                      case 1:
                        setMessages("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                        step = 5;
                        return;
                      case 2:
                        Browser.openURL0("https://support.runescape.com/hc/en-gb", true, false);
                    }
                  }
                }

                var11 = credentialsBoxCenterX - 80;
                var12 = 321;
                if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20) {
                  username = username.trim();
                  if (username.length() == 0) {
                    setMessages("", "Please enter your username/email address.", "");
                    return;
                  }

                  if (password.length() == 0) {
                    setMessages("", "Please enter your password.", "");
                    return;
                  }

                  setMessages("", "Connecting to server...", "");
                  PlayerAccountType.updateLoginStep(false);
                  client.setGameState(20);
                  return;
                }

                var11 = credentialsBoxX + 180 + 80;
                if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20) {
                  step = 0;
                  username = "";
                  password = "";
                  parsedTotp = 0;
                  totp = "";
                  aBoolean462 = true;
                }

                var11 = credentialsBoxCenterX + -117;
                var12 = 277;
                aBoolean477 = var18 >= var11 && var18 < var11 + Statics51.anInt495 && var19 >= var12 && var19 < var12 + WorldMapCacheFeature.anInt296;
                if (var6 == 1 && aBoolean477) {
                  client.rememberUsername = !client.rememberUsername;
                  if (!client.rememberUsername && client.preferences.rememberedUsername != null) {
                    client.preferences.rememberedUsername = null;
                    ClientPreferences.method854();
                  }
                }

                var11 = credentialsBoxCenterX + 24;
                var12 = 277;
                aBoolean472 = var18 >= var11 && var18 < var11 + Statics51.anInt495 && var19 >= var12 && var19 < var12 + WorldMapCacheFeature.anInt296;
                if (var6 == 1 && aBoolean472) {
                  client.preferences.rememberMe = !client.preferences.rememberMe;
                  if (!client.preferences.rememberMe) {
                    username = "";
                    client.preferences.rememberedUsername = null;
                    if (client.rememberUsername && username != null && username.length() > 0) {
                      anInt461 = 1;
                    } else {
                      anInt461 = 0;
                    }
                  }

                  ClientPreferences.method854();
                }

                while (true) {
                  while (Keyboard.isKeyHeld()) {
                    var14 = false;

                    for (var15 = 0; var15 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".length(); ++var15) {
                      if (Keyboard.aChar151 == "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".charAt(var15)) {
                        var14 = true;
                        break;
                      }
                    }

                    if (SecureRandomService.anInt457 == 13) {
                      step = 0;
                      username = "";
                      password = "";
                      parsedTotp = 0;
                      totp = "";
                      aBoolean462 = true;
                    } else if (anInt461 == 0) {
                      if (SecureRandomService.anInt457 == 85 && username.length() > 0) {
                        username = username.substring(0, username.length() - 1);
                      }

                      if (SecureRandomService.anInt457 == 84 || SecureRandomService.anInt457 == 80) {
                        anInt461 = 1;
                      }

                      if (var14 && username.length() < 320) {
                        username = username + Keyboard.aChar151;
                      }
                    } else if (anInt461 == 1) {
                      if (SecureRandomService.anInt457 == 85 && password.length() > 0) {
                        password = password.substring(0, password.length() - 1);
                      }

                      if (SecureRandomService.anInt457 == 84 || SecureRandomService.anInt457 == 80) {
                        anInt461 = 0;
                      }

                      if (SecureRandomService.anInt457 == 84) {
                        username = username.trim();
                        if (username.length() == 0) {
                          setMessages("", "Please enter your username/email address.", "");
                          return;
                        }

                        if (password.length() == 0) {
                          setMessages("", "Please enter your password.", "");
                          return;
                        }

                        setMessages("", "Connecting to server...", "");
                        PlayerAccountType.updateLoginStep(false);
                        client.setGameState(20);
                        return;
                      }

                      if (var14 && password.length() < 20) {
                        password = password + Keyboard.aChar151;
                      }
                    }
                  }

                  return;
                }
              }
              if (step == 3) {
                var21 = credentialsBoxX + 180;
                var23 = 276;
                if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                  method1500(false);
                }

                var21 = credentialsBoxX + 180;
                var23 = 326;
                if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                  setMessages("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                  step = 5;
                }
              } else {
                int var25;
                if (step == 4) {
                  var21 = credentialsBoxX + 180 - 80;
                  var23 = 321;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    totp.trim();
                    if (totp.length() != 6) {
                      setMessages("", "Please enter a 6-digit PIN.", "");
                      return;
                    }

                    parsedTotp = Integer.parseInt(totp);
                    totp = "";
                    PlayerAccountType.updateLoginStep(true);
                    setMessages("", "Connecting to server...", "");
                    client.setGameState(20);
                    return;
                  }

                  if (var6 == 1 && var18 >= credentialsBoxX + 180 - 9 && var18 <= credentialsBoxX + 180 + 130 && var19 >= 263 && var19 <= 296) {
                    aBoolean462 = !aBoolean462;
                  }

                  if (var6 == 1 && var18 >= credentialsBoxX + 180 - 34 && var18 <= credentialsBoxX + 34 + 180 && var19 >= 351 && var19 <= 363) {
                    Browser.openURL0(GameShell.buildUrl("secure", true) + "m=totp-authenticator/disableTOTPRequest", true, false);
                  }

                  var21 = credentialsBoxX + 180 + 80;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    step = 0;
                    username = "";
                    password = "";
                    parsedTotp = 0;
                    totp = "";
                  }

                  while (Keyboard.isKeyHeld()) {
                    boolean var24 = false;

                    for (var25 = 0; var25 < "1234567890".length(); ++var25) {
                      if (Keyboard.aChar151 == "1234567890".charAt(var25)) {
                        var24 = true;
                        break;
                      }
                    }

                    if (SecureRandomService.anInt457 == 13) {
                      step = 0;
                      username = "";
                      password = "";
                      parsedTotp = 0;
                      totp = "";
                    } else {
                      if (SecureRandomService.anInt457 == 85 && totp.length() > 0) {
                        totp = totp.substring(0, totp.length() - 1);
                      }

                      if (SecureRandomService.anInt457 == 84) {
                        totp.trim();
                        if (totp.length() != 6) {
                          setMessages("", "Please enter a 6-digit PIN.", "");
                          return;
                        }

                        parsedTotp = Integer.parseInt(totp);
                        totp = "";
                        PlayerAccountType.updateLoginStep(true);
                        setMessages("", "Connecting to server...", "");
                        client.setGameState(20);
                        return;
                      }

                      if (var24 && totp.length() < 6) {
                        totp = totp + Keyboard.aChar151;
                      }
                    }
                  }
                } else if (step == 5) {
                  var21 = credentialsBoxX + 180 - 80;
                  var23 = 321;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    StockmarketListingLifetimeComparator.method411();
                    return;
                  }

                  var21 = credentialsBoxX + 180 + 80;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    method1500(true);
                  }

                  var12 = 361;
                  if (LoginProt.aBounds_846 != null) {
                    var25 = LoginProt.aBounds_846.width / 2;
                    if (var6 == 1 && var18 >= LoginProt.aBounds_846.x - var25 && var18 <= var25 + LoginProt.aBounds_846.x && var19 >= var12 - 15 && var19 < var12) {
                      Browser.openURL0(GameShell.buildUrl("secure", true) + "m=weblogin/g=oldscape/cant_log_in", true, false);
                    }
                  }

                  while (Keyboard.isKeyHeld()) {
                    var14 = false;

                    for (var15 = 0; var15 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".length(); ++var15) {
                      if (Keyboard.aChar151 == "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".charAt(var15)) {
                        var14 = true;
                        break;
                      }
                    }

                    if (SecureRandomService.anInt457 == 13) {
                      method1500(true);
                    } else {
                      if (SecureRandomService.anInt457 == 85 && username.length() > 0) {
                        username = username.substring(0, username.length() - 1);
                      }

                      if (SecureRandomService.anInt457 == 84) {
                        StockmarketListingLifetimeComparator.method411();
                        return;
                      }

                      if (var14 && username.length() < 320) {
                        username = username + Keyboard.aChar151;
                      }
                    }
                  }
                } else if (step == 6) {
                  while (true) {
                    do {
                      if (!Keyboard.isKeyHeld()) {
                        var22 = 321;
                        if (var6 == 1 && var19 >= var22 - 20 && var19 <= var22 + 20) {
                          method1500(true);
                        }

                        return;
                      }
                    } while (SecureRandomService.anInt457 != 84 && SecureRandomService.anInt457 != 13);

                    method1500(true);
                  }
                } else if (step == 7) {
                  var21 = credentialsBoxX + 180 - 80;
                  var23 = 321;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    Browser.openURL0(GameShell.buildUrl("secure", true) + "m=dob/set_dob.ws", true, false);
                    setMessages("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                    step = 6;
                    return;
                  }

                  var21 = credentialsBoxX + 180 + 80;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    method1500(true);
                  }
                } else if (step == 8) {
                  var21 = credentialsBoxX + 180 - 80;
                  var23 = 321;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    Browser.openURL0("https://www.jagex.com/terms/privacy/#eight", true, false);
                    setMessages("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                    step = 6;
                    return;
                  }

                  var21 = credentialsBoxX + 180 + 80;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    method1500(true);
                  }
                } else if (step == 12) {
                  String var16 = "";
                  switch (anInt468) {
                    case 0:
                      var16 = "https://support.runescape.com/hc/en-gb/articles/115002238729-Account-Bans";
                      break;
                    case 1:
                      var16 = "https://support.runescape.com/hc/en-gb/articles/206103939-My-account-is-locked";
                      break;
                    default:
                      method1500(false);
                  }

                  var11 = credentialsBoxX + 180;
                  var12 = 276;
                  if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20) {
                    Browser.openURL0(var16, true, false);
                    setMessages("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                    step = 6;
                    return;
                  }

                  var11 = credentialsBoxX + 180;
                  var12 = 326;
                  if (var6 == 1 && var18 >= var11 - 75 && var18 <= var11 + 75 && var19 >= var12 - 20 && var19 <= var12 + 20) {
                    method1500(false);
                  }
                } else if (step == 24) {
                  var21 = credentialsBoxX + 180;
                  var23 = 301;
                  if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                    method1500(false);
                  }
                }
              }
            } else {
              while (Keyboard.isKeyHeld()) {
                if (SecureRandomService.anInt457 == 84) {
                  method1500(false);
                } else if (SecureRandomService.anInt457 == 13) {
                  step = 0;
                }
              }

              var21 = credentialsBoxCenterX - 80;
              var23 = 321;
              if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                method1500(false);
              }

              var21 = credentialsBoxCenterX + 80;
              if (var6 == 1 && var18 >= var21 - 75 && var18 <= var21 + 75 && var19 >= var23 - 20 && var19 <= var23 + 20) {
                step = 0;
              }
            }
          }

        }
      }
    }
  }

  public static void prepare(ReferenceTable var0, ReferenceTable var1, boolean var2, int step) {
    if (clear) {
      if (step == 4) {
        Login.step = 4;
      }

    } else {
      Login.step = step;
      JagGraphics.clear();
      byte[] var4 = var0.unpack("title.jpg", "");
      leftTitleSprite = WorldMapRenderRules.method130(var4);
      rightTitleSprite = leftTitleSprite.method836();
      if ((client.currentWorldMask & 536870912) != 0) {
        logoSprite = WorldMapArea.loadIndexedSprite(var1, "logo_deadman_mode", "");
      } else {
        logoSprite = WorldMapArea.loadIndexedSprite(var1, "logo", "");
      }

      Statics49.titleboxSprite = WorldMapArea.loadIndexedSprite(var1, "titlebox", "");
      GraphicsProvider.titlebuttonSprite = WorldMapArea.loadIndexedSprite(var1, "titlebutton", "");
      runeSprites = IndexedSprite.method474(var1, "runes", "");
      AssociateComparator_Sub2.titleMuteSprites = IndexedSprite.method474(var1, "title_mute", "");
      aDoublyNode_Sub24_Sub4_1148 = WorldMapArea.loadIndexedSprite(var1, "options_radio_buttons,0", "");
      WorldMapElement.aDoublyNode_Sub24_Sub4_363 = WorldMapArea.loadIndexedSprite(var1, "options_radio_buttons,4", "");
      aDoublyNode_Sub24_Sub4_288 = WorldMapArea.loadIndexedSprite(var1, "options_radio_buttons,2", "");
      aDoublyNode_Sub24_Sub4_470 = WorldMapArea.loadIndexedSprite(var1, "options_radio_buttons,6", "");
      Statics51.anInt495 = aDoublyNode_Sub24_Sub4_1148.anInt378;
      WorldMapCacheFeature.anInt296 = aDoublyNode_Sub24_Sub4_1148.anInt377;
      AsyncOutputStream.loginScreenEffect = new LoginScreenEffect(runeSprites);
      if (var2) {
        username = "";
        password = "";
      }

      parsedTotp = 0;
      totp = "";
      aBoolean462 = true;
      worldSelectorOpen = false;
      if (!client.preferences.loginScreenAudioDisabled) {
        WorldMapTileDecor_Sub2.method470(2, Archive.audioTracks, "scape main", "", 255, false);
      } else {
        AudioSystem.state = 1;
        AudioSystem.tracks = null;
        AudioSystem.trackGroup = -1;
        AudioSystem.trackFile = -1;
        AudioSystem.volume = 0;
        AudioSystem.aBoolean620 = false;
        AudioSystem.pcmSampleLength = 2;
      }

      Js5Worker.kill(false);
      clear = true;
      paddingX = (client.canvasWidth - 765) / 2;
      credentialsBoxX = paddingX + 202;
      credentialsBoxCenterX = credentialsBoxX + 180;
      leftTitleSprite.renderAt(paddingX, 0);
      rightTitleSprite.renderAt(paddingX + 382, 0);
      logoSprite.renderAt(paddingX + 382 - logoSprite.anInt378 / 2, 18);
    }
  }

  public static void method1402() {
    worldSelectorOpen = false;
    leftTitleSprite.renderAt(paddingX, 0);
    rightTitleSprite.renderAt(paddingX + 382, 0);
    logoSprite.renderAt(paddingX + 382 - logoSprite.anInt378 / 2, 18);
  }
}
