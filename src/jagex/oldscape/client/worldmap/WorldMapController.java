package jagex.oldscape.client.worldmap;

import jagex.oldscape.SerializableProcessor;
import jagex.jagex3.util.Jagexception;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

import java.io.*;
import java.util.*;

public final class WorldMapController {

  public static String aString264;
  public static String osName = "";

  final HashMap fonts;
  final ReferenceTable scenery;
  final ReferenceTable ground;
  final HashMap aHashMap270;
  final IndexedSprite[] mapSceneSprites;
  public int tileScale;
  boolean started;
  Sprite aSprite272;
  boolean initialized;
  HashMap<Integer, List<WorldMapIcon>> icons;
  int anInt261;
  int anInt268;
  WorldMapChunkDefinition[][] aWorldMapChunkDefinitionArrayArray269;
  WorldMapCacheArea_Sub1 aClass9_Sub1_273;
  int anInt260;
  int anInt263;

  public WorldMapController(IndexedSprite[] mapSceneSprites, HashMap fonts, ReferenceTable scenery, ReferenceTable ground) {
    this.initialized = false;
    this.started = false;
    this.aHashMap270 = new HashMap<>();
    this.tileScale = 0;
    this.mapSceneSprites = mapSceneSprites;
    this.fonts = fonts;
    this.scenery = scenery;
    this.ground = ground;
  }

  public static String method143(Throwable throwable) throws IOException {
    StringBuilder var2;
    if (throwable instanceof Jagexception) {
      Jagexception e = (Jagexception) throwable;
      var2 = new StringBuilder(e.message + " | ");
      throwable = e.throwable;
    } else {
      var2 = new StringBuilder();
    }

    StringWriter var12 = new StringWriter();
    PrintWriter var3 = new PrintWriter(var12);
    throwable.printStackTrace(var3);
    var3.close();
    String var4 = var12.toString();
    BufferedReader var5 = new BufferedReader(new StringReader(var4));
    String var6 = var5.readLine();

    while (true) {
      while (true) {
        String var7 = var5.readLine();
        if (var7 == null) {
          var2.append("| ").append(var6);
          return var2.toString();
        }

        int var8 = var7.indexOf(40);
        int var9 = var7.indexOf(41, var8 + 1);
        if (var8 >= 0 && var9 >= 0) {
          String var10 = var7.substring(var8 + 1, var9);
          int var11 = var10.indexOf(".java:");
          if (var11 >= 0) {
            var10 = var10.substring(0, var11) + var10.substring(var11 + 5);
            var2.append(var10).append(' ');
            continue;
          }

          var7 = var7.substring(0, var8);
        }

        var7 = var7.trim();
        var7 = var7.substring(var7.lastIndexOf(32) + 1);
        var7 = var7.substring(var7.lastIndexOf(9) + 1);
        var2.append(var7).append(' ');
      }
    }
  }

  void method134() {
    if (this.icons == null) {
      this.icons = new HashMap<>();
    }

    this.icons.clear();

    for (WorldMapChunkDefinition[] anAWorldMapChunkDefinitionArrayArray269 : this.aWorldMapChunkDefinitionArrayArray269) {
      for (WorldMapChunkDefinition anAnAWorldMapChunkDefinitionArrayArray269 : anAWorldMapChunkDefinitionArrayArray269) {
        List var3 = anAnAWorldMapChunkDefinitionArrayArray269.method36();

        for (Object aVar3 : var3) {
          WorldMapIcon var5 = (WorldMapIcon) aVar3;
          if (var5.isUsingMapFunction()) {
            int var6 = var5.getMapFunction();
            if (!this.icons.containsKey(var6)) {
              LinkedList<WorldMapIcon> var7 = new LinkedList<>();
              var7.add(var5);
              this.icons.put(var6, var7);
            } else {
              List<WorldMapIcon> var8 = this.icons.get(var6);
              var8.add(var5);
            }
          }
        }
      }
    }

  }

  WorldMapArea method139(int var1, int var2, int var3, int var4) {
    WorldMapArea var5 = new WorldMapArea(this);
    int var6 = this.anInt261 * 4096 + var1;
    int var7 = this.anInt268 * 4096 + var2;
    int var8 = var3 + this.anInt261 * 4096;
    int var9 = this.anInt268 * 4096 + var4;
    int var10 = var6 / 64;
    int var11 = var7 / 64;
    int var12 = var8 / 64;
    int var13 = var9 / 64;
    var5.width = var12 - var10 + 1;
    var5.height = var13 - var11 + 1;
    var5.x = var10 - this.aClass9_Sub1_273.getMinRegionX();
    var5.y = var11 - this.aClass9_Sub1_273.getMinRegionY();
    if (var5.x < 0) {
      var5.width += var5.x;
      var5.x = 0;
    }

    if (var5.x > this.aWorldMapChunkDefinitionArrayArray269.length - var5.width) {
      var5.width = this.aWorldMapChunkDefinitionArrayArray269.length - var5.x;
    }

    if (var5.y < 0) {
      var5.height += var5.y;
      var5.y = 0;
    }

    if (var5.y > this.aWorldMapChunkDefinitionArrayArray269[0].length - var5.height) {
      var5.height = this.aWorldMapChunkDefinitionArrayArray269[0].length - var5.y;
    }

    var5.width = Math.min(var5.width, this.aWorldMapChunkDefinitionArrayArray269.length);
    var5.height = Math.min(var5.height, this.aWorldMapChunkDefinitionArrayArray269[0].length);
    return var5;
  }

  float getTileScale(int var1, int var2) {
    float var3 = (float) var1 / (float) var2;
    if (var3 > 8.0F) {
      return 8.0F;
    }
    if (var3 < 1.0F) {
      return 1.0F;
    }
    int var4 = Math.round(var3);
    return Math.abs((float) var4 - var3) < 0.05F ? (float) var4 : var3;
  }

  public boolean isLoaded() {
    return this.initialized;
  }

  public HashMap<Integer, List<WorldMapIcon>> defineIcons() {
    this.method134();
    return this.icons;
  }

  public void method140(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
    if (this.aSprite272 != null) {
      this.aSprite272.method807(var1, var2, var3, var4);
      if (var6 > 0 && var6 % var7 < var7 / 2) {
        if (this.icons == null) {
          this.method134();
        }

        Iterator var8 = var5.iterator();

        while (true) {
          List var10;
          do {
            if (!var8.hasNext()) {
              return;
            }

            int var9 = (Integer) var8.next();
            var10 = this.icons.get(var9);
          } while (var10 == null);

          for (Object aVar10 : var10) {
            WorldMapIcon var12 = (WorldMapIcon) aVar10;
            int var13 = var3 * (var12.max.x - this.anInt261 * 4096) / (this.anInt260 * 4096);
            int var14 = var4 - (var12.max.y - this.anInt268 * 4096) * var4 / (this.anInt263 * 4096);
            JagGraphics.method1367(var13 + var1, var14 + var2, 2, 16776960, 256);
          }
        }
      }
    }
  }

  public void method145(ReferenceTable var1, String var2, boolean var3) {
    if (!this.started) {
      this.initialized = false;
      this.started = true;
      System.nanoTime();
      int var4 = var1.getGroup(WorldMapCacheFeature.DETAILS.name);
      int var5 = var1.getFile(var4, var2);
      Buffer var6 = new Buffer(var1.unpack(WorldMapCacheFeature.DETAILS.name, var2));
      Buffer var7 = new Buffer(var1.unpack(WorldMapCacheFeature.COMPOSITEMAP.name, var2));
      System.nanoTime();
      System.nanoTime();
      this.aClass9_Sub1_273 = new WorldMapCacheArea_Sub1();

      try {
        this.aClass9_Sub1_273.decode(var6, var7, var5, var3);
      } catch (IllegalStateException var19) {
        return;
      }

      this.aClass9_Sub1_273.getBaseX();
      this.aClass9_Sub1_273.getLevel();
      this.aClass9_Sub1_273.getBaseY();
      this.anInt261 = this.aClass9_Sub1_273.getMinRegionX() * 64;
      this.anInt268 = this.aClass9_Sub1_273.getMinRegionY() * 64;
      this.anInt260 = (this.aClass9_Sub1_273.method70() - this.aClass9_Sub1_273.getMinRegionX() + 1) * 64;
      this.anInt263 = (this.aClass9_Sub1_273.method72() - this.aClass9_Sub1_273.getMinRegionY() + 1) * 64;
      int var9 = this.aClass9_Sub1_273.method70() - this.aClass9_Sub1_273.getMinRegionX() + 1;
      int var10 = this.aClass9_Sub1_273.method72() - this.aClass9_Sub1_273.getMinRegionY() + 1;
      System.nanoTime();
      System.nanoTime();
      SerializableProcessor.method453();
      this.aWorldMapChunkDefinitionArrayArray269 = new WorldMapChunkDefinition[var9][var10];

      for (Object anAHashSet291 : this.aClass9_Sub1_273.aHashSet291) {
        WorldMapTileDecor_Sub2 var12 = (WorldMapTileDecor_Sub2) anAHashSet291;
        int var13 = var12.anInt517;
        int var14 = var12.anInt516;
        int var15 = var13 - this.aClass9_Sub1_273.getMinRegionX();
        int var16 = var14 - this.aClass9_Sub1_273.getMinRegionY();
        this.aWorldMapChunkDefinitionArrayArray269[var15][var16] = new WorldMapChunkDefinition(var13, var14, this.aClass9_Sub1_273.method67(), this.fonts);
        this.aWorldMapChunkDefinitionArrayArray269[var15][var16].method54(var12, this.aClass9_Sub1_273.icons);
      }

      for (int var17 = 0; var17 < var9; ++var17) {
        for (int var18 = 0; var18 < var10; ++var18) {
          if (this.aWorldMapChunkDefinitionArrayArray269[var17][var18] == null) {
            this.aWorldMapChunkDefinitionArrayArray269[var17][var18] = new WorldMapChunkDefinition(this.aClass9_Sub1_273.getMinRegionX() + var17, this.aClass9_Sub1_273.getMinRegionY() + var18, this.aClass9_Sub1_273.method67(), this.fonts);
            this.aWorldMapChunkDefinitionArrayArray269[var17][var18].method52(this.aClass9_Sub1_273.aHashSet289, this.aClass9_Sub1_273.icons);
          }
        }
      }

      System.nanoTime();
      System.nanoTime();
      if (var1.validate(WorldMapCacheFeature.COMPOSITETEXTURE.name, var2)) {
        byte[] var20 = var1.unpack(WorldMapCacheFeature.COMPOSITETEXTURE.name, var2);
        this.aSprite272 = WorldMapRenderRules.method130(var20);
      }

      System.nanoTime();
      var1.method903();
      var1.clear();
      this.initialized = true;
    }
  }

  public List<WorldMapIcon> getIcons(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
    java.util.LinkedList<WorldMapIcon> var11 = new java.util.LinkedList<>();
    if (!this.initialized) {
      return var11;
    }
    WorldMapArea var12 = this.method139(var1, var2, var3, var4);
    float var13 = this.getTileScale(var7, var3 - var1);
    int var14 = (int) (var13 * 64.0F);
    int var15 = this.anInt261 * 4096 + var1;
    int var16 = this.anInt268 * 4096 + var2;

    for (int var17 = var12.x; var17 < var12.x + var12.width; ++var17) {
      for (int var18 = var12.y; var18 < var12.y + var12.height; ++var18) {
        List<WorldMapIcon> var19 = this.aWorldMapChunkDefinitionArrayArray269[var17][var18].method44(var5 + var14 * (this.aWorldMapChunkDefinitionArrayArray269[var17][var18].anInt115 * 64 - var15) / 64, var8 + var6 - var14 * (this.aWorldMapChunkDefinitionArrayArray269[var17][var18].anInt116 * 64 - var16 + 64) / 64, var14, var9, var10);
        if (!var19.isEmpty()) {
          var11.addAll(var19);
        }
      }
    }

    return var11;
  }

  public void clearIcons() {
    this.icons = null;
  }

  public void method142(int var1, int var2, int var3, int var4, int var5, int var7, int var8) {
    int[] var9 = JagGraphics.drawingAreaPixels;
    int var10 = JagGraphics.drawingAreaWidth;
    int var11 = JagGraphics.drawingAreaHeight;
    int[] var12 = new int[4];
    JagGraphics.method1366(var12);
    WorldMapArea var13 = this.method139(var1, var2, var3, var4);
    float var14 = this.getTileScale(var7 - var5, var3 - var1);
    int var15 = (int) Math.ceil(var14);
    this.tileScale = var15;
    if (!this.aHashMap270.containsKey(var15)) {
      WorldMapRenderRules var16 = new WorldMapRenderRules(var15);
      var16.initialize();
      this.aHashMap270.put(var15, var16);
    }

    int var17 = var13.width + var13.x - 1;
    int var18 = var13.y + var13.height - 1;

    int var19;
    int var20;
    for (var19 = var13.x; var19 <= var17; ++var19) {
      for (var20 = var13.y; var20 <= var18; ++var20) {
        this.aWorldMapChunkDefinitionArrayArray269[var19][var20].method28(var15, (WorldMapRenderRules) this.aHashMap270.get(var15), this.mapSceneSprites, this.scenery, this.ground);
      }
    }

    JagGraphics.setTarget(var9, var10, var11);
    JagGraphics.method1373(var12);
    var19 = (int) (var14 * 64.0F);
    var20 = this.anInt261 * 4096 + var1;
    int var21 = this.anInt268 * 4096 + var2;

    for (int var22 = var13.x; var22 < var13.width + var13.x; ++var22) {
      for (int var23 = var13.y; var23 < var13.y + var13.height; ++var23) {
        this.aWorldMapChunkDefinitionArrayArray269[var22][var23].method55(var5 + var19 * (this.aWorldMapChunkDefinitionArrayArray269[var22][var23].anInt115 * 64 - var20) / 64, var8 - var19 * (this.aWorldMapChunkDefinitionArrayArray269[var22][var23].anInt116 * 64 - var21 + 64) / 64, var19);
      }
    }

  }

  public void method141(int var1, int var2, int var3, int var4, int var5, int var7, int var8, HashSet<Integer> var9, HashSet<Integer> var10, int var11, int var12, boolean var13) {
    WorldMapArea var14 = this.method139(var1, var2, var3, var4);
    float var15 = this.getTileScale(var7 - var5, var3 - var1);
    int var16 = (int) (64.0F * var15);
    int var17 = this.anInt261 * 4096 + var1;
    int var18 = this.anInt268 * 4096 + var2;

    int var19;
    int var20;
    for (var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
      for (var20 = var14.y; var20 < var14.y + var14.height; ++var20) {
        if (var13) {
          this.aWorldMapChunkDefinitionArrayArray269[var19][var20].method43();
        }

        this.aWorldMapChunkDefinitionArrayArray269[var19][var20].method47(var5 + var16 * (this.aWorldMapChunkDefinitionArrayArray269[var19][var20].anInt115 * 64 - var17) / 64, var8 - var16 * (this.aWorldMapChunkDefinitionArrayArray269[var19][var20].anInt116 * 64 - var18 + 64) / 64, var16, var9);
      }
    }

    if (var10 != null && var11 > 0) {
      for (var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
        for (var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
          this.aWorldMapChunkDefinitionArrayArray269[var19][var20].method26(var10, var11, var12);
        }
      }
    }

  }
}
