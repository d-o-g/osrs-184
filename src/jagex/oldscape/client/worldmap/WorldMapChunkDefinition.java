package jagex.oldscape.client.worldmap;

import jagex.oldscape.DefaultRouteStrategy;
import jagex.datastructure.instrusive.hashtable.ReferenceNodeTable;
import jagex.oldscape.client.fonts.Font;
import jagex.oldscape.client.type.ObjectDefinition;
import jagex.oldscape.client.type.TileOverlay;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;
import jagex.statics.Statics24;

import java.util.*;
import java.util.Map.Entry;

public class WorldMapChunkDefinition {

  public static final ReferenceNodeTable A_REFERENCE_NODE_TABLE___118 = new ReferenceNodeTable(37748736, 256);
  public static Sprite[] prayerIconSprites;

  final HashMap aHashMap117;
  List aList119;
  java.util.LinkedList aLinkedList122;
  HashMap aHashMap125;
  int anInt115;
  WorldMapTileDecor_Sub2 aClass63_Sub2_124;
  int anInt120;
  int anInt116;
  int anInt123;

  WorldMapChunkDefinition(int var1, int var2, int var3, HashMap var4) {
    anInt115 = var1;
    anInt116 = var2;
    aLinkedList122 = new java.util.LinkedList();
    aList119 = new java.util.LinkedList();
    aHashMap125 = new HashMap();
    anInt123 = var3 | -16777216;
    aHashMap117 = var4;
  }

  void draw(int x, int y, WorldMapTileDecor tile) {
    for (int level = 0; level < tile.levelCount; ++level) {
      WorldMapDecor[] decors = tile.aWorldMapDecorArrayArrayArrayArray511[level][x][y];
      if (decors != null && decors.length != 0) {

        for (WorldMapDecor decor : decors) {
          int var9 = decor.decorationId;
          boolean var10 = var9 >= WorldMapDecorType.anEnum_Sub7_1287.id && var9 <= WorldMapDecorType.anEnum_Sub7_1282.id || var9 == WorldMapDecorType.anEnum_Sub7_1279.id;
          if (var10) {
            ObjectDefinition definition = ObjectDefinition.get(decor.objectId);
            int rgb = definition.mapDoorFlag != 0 ? -3407872 : -3355444;
            if (decor.decorationId == WorldMapDecorType.anEnum_Sub7_1287.id) {
              draw(x, y, decor.rotation, rgb);
            }

            if (decor.decorationId == WorldMapDecorType.anEnum_Sub7_1283.id) {
              draw(x, y, decor.rotation, -3355444);
              draw(x, y, decor.rotation + 1, rgb);
            }

            if (decor.decorationId == WorldMapDecorType.anEnum_Sub7_1282.id) {
              if (decor.rotation == 0) {
                JagGraphics.drawHorizontalLine(anInt120 * x, anInt120 * (63 - y), 1, rgb);
              }

              if (decor.rotation == 1) {
                JagGraphics.drawHorizontalLine(anInt120 * x + anInt120 - 1, anInt120 * (63 - y), 1, rgb);
              }

              if (decor.rotation == 2) {
                JagGraphics.drawHorizontalLine(anInt120 + anInt120 * x - 1, anInt120 * (63 - y) + anInt120 - 1, 1, rgb);
              }

              if (decor.rotation == 3) {
                JagGraphics.drawHorizontalLine(anInt120 * x, anInt120 * (63 - y) + anInt120 - 1, 1, rgb);
              }
            }

            if (decor.decorationId == WorldMapDecorType.anEnum_Sub7_1279.id) {
              int var13 = decor.rotation % 2;
              int var14;
              if (var13 == 0) {
                for (var14 = 0; var14 < anInt120; ++var14) {
                  JagGraphics.drawHorizontalLine(var14 + anInt120 * x, (64 - y) * anInt120 - 1 - var14, 1, rgb);
                }
              } else {
                for (var14 = 0; var14 < anInt120; ++var14) {
                  JagGraphics.drawHorizontalLine(var14 + anInt120 * x, var14 + anInt120 * (63 - y), 1, rgb);
                }
              }
            }
          }
        }
      }
    }

  }

  void method40(int var1, int var2, HashSet var3, int var4) {
    float var5 = (float) var4 / 64.0F;
    float var6 = var5 / 2.0F;

    for (Object o : aHashMap125.entrySet()) {
      Entry var8 = (Entry) o;
      WorldMapPosition var9 = (WorldMapPosition) var8.getKey();
      int var10 = (int) ((float) var1 + var5 * (float) var9.x - var6);
      int var11 = (int) ((float) (var2 + var4) - var5 * (float) var9.y - var6);
      WorldMapIcon var12 = (WorldMapIcon) var8.getValue();
      if (var12 != null && var12.isUsingMapFunction()) {
        var12.anInt315 = var10;
        var12.anInt316 = var11;
        WorldMapFunction var13 = WorldMapFunction.get(var12.getMapFunction());
        if (!var3.contains(var13.method1082())) {
          method46(var12, var10, var11, var5);
        }
      }
    }

  }

  void drawMapScene(int var1, int var2, WorldMapTileDecor var3, IndexedSprite[] var4) {
    for (int var5 = 0; var5 < var3.levelCount; ++var5) {
      WorldMapDecor[] var6 = var3.aWorldMapDecorArrayArrayArrayArray511[var5][var1][var2];
      if (var6 != null && var6.length != 0) {

        for (WorldMapDecor var9 : var6) {
          if (!Statics24.method966(var9.decorationId)) {
            int var10 = var9.decorationId;
            boolean var11 = var10 == WorldMapDecorType.anEnum_Sub7_1273.id;
            if (!var11) {
              continue;
            }
          }

          ObjectDefinition var12 = ObjectDefinition.get(var9.objectId);
          if (var12.mapSceneId != -1) {
            if (var12.mapSceneId != 46 && var12.mapSceneId != 52) {
              var4[var12.mapSceneId].method1322(anInt120 * var1, anInt120 * (63 - var2), anInt120 * 2, anInt120 * 2);
            } else {
              var4[var12.mapSceneId].method1322(anInt120 * var1, anInt120 * (63 - var2), anInt120 * 2 + 1, anInt120 * 2 + 1);
            }
          }
        }
      }
    }

  }

  void method53(int x, int y, WorldMapTileDecor decor, WorldMapRenderRules rules, WorldMapElement sprite) {
    int var6 = decor.aShortArrayArrayArray518[0][x][y] - 1;
    int var7 = decor.aShortArrayArrayArray508[0][x][y] - 1;
    if (var6 == -1 && var7 == -1) {
      JagGraphics.fillRect(anInt120 * x, anInt120 * (63 - y), anInt120, anInt120, anInt123);
    }

    int var8 = 16711935;
    int var12;
    if (var7 != -1) {
      int var9 = anInt123;
      TileOverlay var10 = TileOverlay.cache.get(var7);
      TileOverlay var11;
      if (var10 == null) {
        byte[] var13 = TileOverlay.table.unpack(4, var7);
        var10 = new TileOverlay();
        if (var13 != null) {
          var10.decode(new Buffer(var13));
        }

        var10.method499();
        TileOverlay.cache.put(var10, var7);
      }
      var11 = var10;

      if (var11 == null) {
        var12 = var9;
      } else if (var11.secondaryRgb >= 0) {
        var12 = var11.secondaryRgb | -16777216;
      } else {
        int var14;
        byte var15;
        int var16;
        int var18;
        if (var11.material >= 0) {
          var14 = JagGraphics3D.materialProvider.rgb(var11.material);
          var15 = 96;
          if (var14 == -2) {
            var16 = 12345678;
          } else if (var14 == -1) {
            if (var15 < 0) {
              var15 = 0;
            } else if (var15 > 127) {
              var15 = 127;
            }

            var18 = 127 - var15;
            var16 = var18;
          } else {
            var18 = var15 * (var14 & 127) / 128;
            if (var18 < 2) {
              var18 = 2;
            } else if (var18 > 126) {
              var18 = 126;
            }

            var16 = var18 + (var14 & 65408);
          }

          var12 = JagGraphics3D.COLOR_PALETTE[var16] | -16777216;
        } else if (var11.rgb == 16711935) {
          var12 = var9;
        } else {
          var16 = DefaultRouteStrategy.convertHslToRgb(var11.hue, var11.saturation, var11.lightness);
          var15 = 96;
          if (var16 == -2) {
            var14 = 12345678;
          } else if (var16 == -1) {
            if (var15 < 0) {
              var15 = 0;
            } else if (var15 > 127) {
              var15 = 127;
            }

            var18 = 127 - var15;
            var14 = var18;
          } else {
            var18 = var15 * (var16 & 127) / 128;
            if (var18 < 2) {
              var18 = 2;
            } else if (var18 > 126) {
              var18 = 126;
            }

            var14 = var18 + (var16 & 65408);
          }

          var12 = JagGraphics3D.COLOR_PALETTE[var14] | -16777216;
        }
      }

      var8 = var12;
    }

    if (var7 > -1 && decor.aByteArrayArrayArray506[0][x][y] == 0) {
      JagGraphics.fillRect(anInt120 * x, anInt120 * (63 - y), anInt120, anInt120, var8);
    } else {
      var12 = method38(x, y, decor, sprite);
      if (var7 == -1) {
        JagGraphics.fillRect(anInt120 * x, anInt120 * (63 - y), anInt120, anInt120, var12);
      } else {
        rules.method129(anInt120 * x, anInt120 * (63 - y), var12, var8, anInt120, anInt120, decor.aByteArrayArrayArray506[0][x][y], decor.aByteArrayArrayArray505[0][x][y]);
      }
    }
  }

  void method56(int var1, int var2, HashSet var3, int var4) {
    float var5 = (float) var4 / 64.0F;

    for (Object anAList119 : aList119) {
      WorldMapIcon var7 = (WorldMapIcon) anAList119;
      if (var7.isUsingMapFunction()) {
        int var8 = var7.max.x % 64;
        int var9 = var7.max.y % 64;
        var7.anInt315 = (int) (var5 * (float) var8 + (float) var1);
        var7.anInt316 = (int) ((float) var2 + (float) (63 - var9) * var5);
        if (!var3.contains(var7.getMapFunction())) {
          method46(var7, var7.anInt315, var7.anInt316, var5);
        }
      }
    }

  }

  int method51(Sprite var1, HAlign var2) {
    switch (var2.type) {
      case 1:
        return 0;
      case 2:
        return -var1.width / 2;
      default:
        return -var1.width;
    }
  }

  void method58(List var1) {

    for (Object aVar1 : var1) {
      WorldMapLabelIcon var3 = (WorldMapLabelIcon) aVar1;
      if (var3.max.x >> 6 == anInt115 && var3.max.y >> 6 == anInt116) {
        WorldMapLabelIcon var4 = new WorldMapLabelIcon(var3.max, var3.max, var3.mapFunction, createLabel(var3.mapFunction));
        aList119.add(var4);
      }
    }

  }

  void draw(int var1, int var2, WorldMapTileDecor var3, IndexedSprite[] var5) {
    draw(var1, var2, var3);
    drawMapScene(var1, var2, var3, var5);
  }

  public WorldMapLabel createLabel(WorldMapFunction function) {
    if (function.aString1474 != null && aHashMap117 != null && aHashMap117.get(WorldMapLabelSize.SMALL) != null) {
      int var2 = function.fontSize;
      WorldMapLabelSize[] var3 = WorldMapLabelSize.method364();
      int var4 = 0;

      WorldMapLabelSize var6;
      while (true) {
        if (var4 >= var3.length) {
          var6 = null;
          break;
        }

        WorldMapLabelSize var5 = var3[var4];
        if (var2 == var5.anInt527) {
          var6 = var5;
          break;
        }

        ++var4;
      }

      if (var6 == null) {
        return null;
      }
      Font var7 = (Font) aHashMap117.get(var6);
      if (var7 == null) {
        return null;
      }
      int var8 = var7.method1150(function.aString1474, 1000000);
      String[] var9 = new String[var8];
      var7.method1161(function.aString1474, null, var9);
      int var10 = var9.length * var7.anInt375 / 2;
      int var11 = 0;

      for (String var14 : var9) {
        int var15 = var7.textWidth(var14);
        if (var15 > var11) {
          var11 = var15;
        }
      }

      return new WorldMapLabel(function.aString1474, var11, var10, var6);
    }
    return null;
  }

  void method27(HashSet var1, int var2, int var3) {

    for (Object anAList119 : aList119) {
      WorldMapIcon var5 = (WorldMapIcon) anAList119;
      if (var5.isUsingMapFunction()) {
        WorldMapFunction var6 = WorldMapFunction.get(var5.getMapFunction());
        if (var6 != null && var1.contains(var6.method1082())) {
          method24(var6, var5.anInt315, var5.anInt316, var2, var3);
        }
      }
    }

  }

  void method34(int var1, int var2, WorldMapTileDecor var3, WorldMapRenderRules rules) {
    for (int var5 = 1; var5 < var3.levelCount; ++var5) {
      int var6 = var3.aShortArrayArrayArray508[var5][var1][var2] - 1;
      if (var6 > -1) {
        int var7 = anInt123;
        TileOverlay var8 = TileOverlay.cache.get(var6);
        TileOverlay var9;
        if (var8 == null) {
          byte[] var12 = TileOverlay.table.unpack(4, var6);
          var8 = new TileOverlay();
          if (var12 != null) {
            var8.decode(new Buffer(var12));
          }

          var8.method499();
          TileOverlay.cache.put(var8, var6);
        }
        var9 = var8;

        int var10;
        if (var9 == null) {
          var10 = var7;
        } else if (var9.secondaryRgb >= 0) {
          var10 = var9.secondaryRgb | -16777216;
        } else {
          int var13;
          byte var14;
          int var15;
          int var17;
          if (var9.material >= 0) {
            var13 = JagGraphics3D.materialProvider.rgb(var9.material);
            var14 = 96;
            if (var13 == -2) {
              var15 = 12345678;
            } else if (var13 == -1) {
              if (var14 < 0) {
                var14 = 0;
              } else if (var14 > 127) {
                var14 = 127;
              }

              var17 = 127 - var14;
              var15 = var17;
            } else {
              var17 = var14 * (var13 & 127) / 128;
              if (var17 < 2) {
                var17 = 2;
              } else if (var17 > 126) {
                var17 = 126;
              }

              var15 = var17 + (var13 & 65408);
            }

            var10 = JagGraphics3D.COLOR_PALETTE[var15] | -16777216;
          } else if (var9.rgb == 16711935) {
            var10 = var7;
          } else {
            var15 = DefaultRouteStrategy.convertHslToRgb(var9.hue, var9.saturation, var9.lightness);
            var14 = 96;
            if (var15 == -2) {
              var13 = 12345678;
            } else if (var15 == -1) {
              if (var14 < 0) {
                var14 = 0;
              } else if (var14 > 127) {
                var14 = 127;
              }

              var17 = 127 - var14;
              var13 = var17;
            } else {
              var17 = var14 * (var15 & 127) / 128;
              if (var17 < 2) {
                var17 = 2;
              } else if (var17 > 126) {
                var17 = 126;
              }

              var13 = var17 + (var15 & 65408);
            }

            var10 = JagGraphics3D.COLOR_PALETTE[var13] | -16777216;
          }
        }

        if (var3.aByteArrayArrayArray506[var5][var1][var2] == 0) {
          JagGraphics.fillRect(anInt120 * var1, anInt120 * (63 - var2), anInt120, anInt120, var10);
        } else {
          rules.method129(anInt120 * var1, anInt120 * (63 - var2), 0, var10, anInt120, anInt120, var3.aByteArrayArrayArray506[var5][var1][var2], var3.aByteArrayArrayArray505[var5][var1][var2]);
        }
      }
    }

  }

  void method49(WorldMapFunction var1, int var2, int var3) {
    Sprite var4 = var1.getSprite();
    if (var4 != null) {
      int var5 = method51(var4, var1.hAlign);
      int var6 = method45(var4, var1.vAlign);
      var4.renderAlphaAt(var5 + var2, var3 + var6);
    }

  }

  int method45(Sprite var1, VAlign var2) {
    switch (var2.type) {
      case 0:
        return 0;
      case 2:
        return -var1.height / 2;
      default:
        return -var1.height;
    }
  }

  void method25(WorldMapIcon var1, WorldMapFunction var2, int var3, int var4, float var5) {
    WorldMapLabel var6 = var1.getLabel();
    if (var6 != null) {
      if (var6.aWorldMapLabelSize_338.method366(var5)) {
        Font var7 = (Font) aHashMap117.get(var6.aWorldMapLabelSize_338);
        var7.method1149(var6.aString340, var3 - var6.anInt342 / 2, var4, var6.anInt342, var6.anInt339, -16777216 | var2.anInt379, 0, 1, 0, var7.anInt375 / 2);
      }
    }
  }

  void method48(int var1, int var2, int var3, int var4, WorldMapTileDecor var5) {
    for (int var6 = var1; var6 < var3 + var1; ++var6) {
      label73:
      for (int var7 = var2; var7 < var2 + var4; ++var7) {
        for (int var8 = 0; var8 < var5.levelCount; ++var8) {
          WorldMapDecor[] var9 = var5.aWorldMapDecorArrayArrayArrayArray511[var8][var6][var7];
          if (var9 != null && var9.length != 0) {

            for (WorldMapDecor aVar10 : var9) {
              ObjectDefinition var13;
              boolean var18;
              label64:
              {
                var13 = ObjectDefinition.get(aVar10.objectId);
                if (var13.transformIds != null) {
                  int[] var14 = var13.transformIds;

                  for (int var16 : var14) {
                    ObjectDefinition var17 = ObjectDefinition.get(var16);
                    if (var17.mapFunction != -1) {
                      var18 = true;
                      break label64;
                    }
                  }
                } else if (var13.mapFunction != -1) {
                  var18 = true;
                  break label64;
                }

                var18 = false;
              }

              if (var18) {
                method50(var13, var8, var6, var7, var5);
                continue label73;
              }
            }
          }
        }
      }
    }

  }

  boolean method30(ReferenceTable var1) {
    aHashMap125.clear();
    if (aClass63_Sub2_124 != null) {
      aClass63_Sub2_124.method354(var1);
      if (aClass63_Sub2_124.method359()) {
        method48(0, 0, 64, 64, aClass63_Sub2_124);
        return true;
      }
      return false;
    }
    boolean var2 = true;

    Iterator var3;
    WorldMapTileDecor_Sub1 var4;
    for (var3 = aLinkedList122.iterator(); var3.hasNext(); var2 &= var4.method359()) {
      var4 = (WorldMapTileDecor_Sub1) var3.next();
      var4.method354(var1);
    }

    if (var2) {
      var3 = aLinkedList122.iterator();

      while (var3.hasNext()) {
        var4 = (WorldMapTileDecor_Sub1) var3.next();
        method48(var4.method110() * 8, var4.method108() * 8, 8, 8, var4);
      }
    }

    return var2;
  }

  public List method36() {
    java.util.LinkedList var1 = new java.util.LinkedList();
    var1.addAll(aList119);
    var1.addAll(aHashMap125.values());
    return var1;
  }

  WorldMapLabel createLabel(int mapFunction) {
    WorldMapFunction var2 = WorldMapFunction.get(mapFunction);
    return createLabel(var2);
  }

  int method38(int var1, int var2, WorldMapTileDecor var3, WorldMapElement var4) {
    return var3.aShortArrayArrayArray518[0][var1][var2] == 0 ? anInt123 : var4.getTileColor(var1, var2);
  }

  void method50(ObjectDefinition var1, int var2, int var3, int var4, WorldMapTileDecor var5) {
    WorldMapPosition var6 = new WorldMapPosition(var2, var3 + anInt115 * 64, anInt116 * 64 + var4);
    WorldMapPosition var7;
    if (aClass63_Sub2_124 != null) {
      var7 = new WorldMapPosition(aClass63_Sub2_124.anInt515 + var2, var3 + aClass63_Sub2_124.anInt519 * 64, aClass63_Sub2_124.anInt510 * 4096 + var4);
    } else {
      WorldMapTileDecor_Sub1 var8 = (WorldMapTileDecor_Sub1) var5;
      var7 = new WorldMapPosition(var2 + var8.anInt515, var8.anInt519 * 64 + var3 + var8.method107() * 8, var8.anInt510 * 4096 + var4 + var8.method106() * 8);
    }

    Object var10;
    if (var1.transformIds != null) {
      var10 = new WorldMapObjectIcon(var7, var6, var1.id, this);
    } else {
      WorldMapFunction var9 = WorldMapFunction.get(var1.mapFunction);
      var10 = new WorldMapLabelIcon(var7, var6, var9.objectId, createLabel(var9));
    }

    aHashMap125.put(new WorldMapPosition(0, var3, var4), var10);
  }

  void method24(WorldMapFunction var1, int var2, int var3, int var4, int var5) {
    Sprite var6 = var1.getSprite();
    if (var6 != null) {
      var6.renderAlphaAt(var2 - var6.width / 2, var3 - var6.height / 2);
      if (var4 % var5 < var5 / 2) {
        JagGraphics.method1367(var2, var3, 15, 16776960, 128);
        JagGraphics.method1367(var2, var3, 7, 16777215, 256);
      }

    }
  }

  void method43() {

    for (Object o : aHashMap125.values()) {
      WorldMapIcon var2 = (WorldMapIcon) o;
      if (var2 instanceof WorldMapObjectIcon) {
        ((WorldMapObjectIcon) var2).initialize();
      }
    }

  }

  void method46(WorldMapIcon var1, int var2, int var3, float var4) {
    WorldMapFunction var5 = WorldMapFunction.get(var1.getMapFunction());
    method49(var5, var2, var3);
    method25(var1, var5, var2, var3, var4);
  }

  void method26(HashSet<Integer> var1, int var2, int var3) {

    for (Object o : aHashMap125.values()) {
      WorldMapIcon var5 = (WorldMapIcon) o;
      if (var5.isUsingMapFunction()) {
        int var6 = var5.getMapFunction();
        if (var1.contains(var6)) {
          WorldMapFunction var7 = WorldMapFunction.get(var6);
          method24(var7, var5.anInt315, var5.anInt316, var2, var3);
        }
      }
    }

    method27(var1, var2, var3);
  }

  void draw(int x, int y, int direction, int color) {
    direction %= 4;
    if (direction == 0) {
      JagGraphics.drawVerticalLine(anInt120 * x, anInt120 * (63 - y), anInt120, color);
    }

    if (direction == 1) {
      JagGraphics.drawHorizontalLine(anInt120 * x, anInt120 * (63 - y), anInt120, color);
    }

    if (direction == 2) {
      JagGraphics.drawVerticalLine(anInt120 * x + anInt120 - 1, anInt120 * (63 - y), anInt120, color);
    }

    if (direction == 3) {
      JagGraphics.drawHorizontalLine(anInt120 * x, anInt120 * (63 - y) + anInt120 - 1, anInt120, color);
    }

  }

  void method57(WorldMapRenderRules rules, IndexedSprite[] var2, WorldMapElement var3) {
    Iterator var4 = aLinkedList122.iterator();

    WorldMapTileDecor_Sub1 var5;
    int var6;
    int var7;
    while (var4.hasNext()) {
      var5 = (WorldMapTileDecor_Sub1) var4.next();

      for (var6 = var5.method110() * 8; var6 < var5.method110() * 8 + 8; ++var6) {
        for (var7 = var5.method108() * 8; var7 < var5.method108() * 8 + 8; ++var7) {
          method53(var6, var7, var5, rules, var3);
          method34(var6, var7, var5, rules);
        }
      }
    }

    var4 = aLinkedList122.iterator();

    while (var4.hasNext()) {
      var5 = (WorldMapTileDecor_Sub1) var4.next();

      for (var6 = var5.method110() * 8; var6 < var5.method110() * 8 + 8; ++var6) {
        for (var7 = var5.method108() * 8; var7 < var5.method108() * 8 + 8; ++var7) {
          draw(var6, var7, var5, var2);
        }
      }
    }

  }

  void method33(WorldMapRenderRules var1, IndexedSprite[] var2, WorldMapElement var3) {
    int var4;
    int var5;
    for (var4 = 0; var4 < 64; ++var4) {
      for (var5 = 0; var5 < 64; ++var5) {
        method53(var4, var5, aClass63_Sub2_124, var1, var3);
        method34(var4, var5, aClass63_Sub2_124, var1);
      }
    }

    for (var4 = 0; var4 < 64; ++var4) {
      for (var5 = 0; var5 < 64; ++var5) {
        draw(var4, var5, aClass63_Sub2_124, var2);
      }
    }

  }

  void method47(int var1, int var2, int var3, HashSet<Integer> var4) {
    if (var4 == null) {
      var4 = new HashSet<>();
    }

    method40(var1, var2, var4, var3);
    method56(var1, var2, var4, var3);
  }

  List<WorldMapIcon> method44(int var1, int var2, int var3, int var4, int var5) {
    java.util.LinkedList<WorldMapIcon> var6 = new java.util.LinkedList<>();
    if (var4 >= var1 && var5 >= var2) {
      if (var4 < var3 + var1 && var5 < var3 + var2) {
        Iterator var7 = aHashMap125.values().iterator();

        WorldMapIcon var8;
        while (var7.hasNext()) {
          var8 = (WorldMapIcon) var7.next();
          if (var8.isUsingMapFunction() && var8.method200(var4, var5)) {
            var6.add(var8);
          }
        }

        var7 = aList119.iterator();

        while (var7.hasNext()) {
          var8 = (WorldMapIcon) var7.next();
          if (var8.isUsingMapFunction() && var8.method200(var4, var5)) {
            var6.add(var8);
          }
        }

        return var6;
      }
      return var6;
    }
    return var6;
  }

  void method37() {
    if (aClass63_Sub2_124 != null) {
      aClass63_Sub2_124.method352();
    } else {

      for (Object anALinkedList122 : aLinkedList122) {
        WorldMapTileDecor_Sub1 var2 = (WorldMapTileDecor_Sub1) anALinkedList122;
        var2.method352();
      }
    }

  }

  void method28(int var1, WorldMapRenderRules rules, IndexedSprite[] var3, ReferenceTable var4, ReferenceTable var5) {
    anInt120 = var1;
    if (aClass63_Sub2_124 != null || !aLinkedList122.isEmpty()) {
      int var6 = anInt115;
      int var7 = anInt116;
      Sprite var8 = (Sprite) A_REFERENCE_NODE_TABLE___118.get(Sprite.hash(var6, var7, var1));
      if (var8 == null) {
        boolean var9 = true;
        var9 &= method30(var4);
        int var10;
        if (aClass63_Sub2_124 != null) {
          var10 = aClass63_Sub2_124.anInt514;
        } else {
          var10 = ((WorldMapTileDecor) aLinkedList122.getFirst()).anInt514;
        }

        var9 &= var5.loadGroup(var10);
        if (var9) {
          byte[] var11 = var5.unpack(var10);
          WorldMapElement element;
          if (var11 == null) {
            element = new WorldMapElement();
          } else {
            element = new WorldMapElement(WorldMapRenderRules.method130(var11).pixels);
          }

          Sprite var14 = new Sprite(anInt120 * 64, anInt120 * 64);
          var14.method23();
          if (aClass63_Sub2_124 != null) {
            method33(rules, var3, element);
          } else {
            method57(rules, var3, element);
          }

          int var15 = anInt115;
          int var16 = anInt116;
          int var17 = anInt120;
          A_REFERENCE_NODE_TABLE___118.put(var14, Sprite.hash(var15, var16, var17), var14.pixels.length * 4);
          method37();
        }
      }
    }
  }

  void method55(int var1, int var2, int var3) {
    int var4 = anInt115;
    int var5 = anInt116;
    int var6 = anInt120;
    Sprite var7 = (Sprite) A_REFERENCE_NODE_TABLE___118.get(Sprite.hash(var4, var5, var6));
    if (var7 != null) {
      if (var3 == anInt120 * 64) {
        var7.renderAt(var1, var2);
      } else {
        var7.method811(var1, var2, var3, var3);
      }

    }
  }

  void method54(WorldMapTileDecor_Sub2 var1, List var2) {
    aHashMap125.clear();
    aClass63_Sub2_124 = var1;
    method58(var2);
  }

  void method52(HashSet var1, List var2) {
    aHashMap125.clear();

    for (Object aVar1 : var1) {
      WorldMapTileDecor_Sub1 var4 = (WorldMapTileDecor_Sub1) aVar1;
      if (var4.method353() == anInt115 && var4.method358() == anInt116) {
        aLinkedList122.add(var4);
      }
    }

    method58(var2);
  }
}
