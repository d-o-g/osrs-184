package jagex.oldscape.client.scene;

import jagex.jagex3.client.applet.GameShell;
import jagex.jagex3.sound.*;
import jagex.datastructure.instrusive.linklist.NodeDeque;
import jagex.jagex3.client.input.mouse.Mouse;
import jagex.oldscape.*;
import jagex.oldscape.client.*;
import jagex.oldscape.client.fonts.NamedFont;
import jagex.oldscape.client.social.AssociateComparatorByWorld;
import jagex.oldscape.client.social.BefriendedPlayer;
import jagex.oldscape.client.scene.entity.*;
import jagex.oldscape.shared.prot.*;
import jagex.oldscape.stockmarket.*;
import jagex.oldscape.client.type.*;
import jagex.jagex3.graphics.*;
import jagex.jagex3.js5.Archive;
import jagex.jagex3.js5.LoadedArchive;
import jagex.messaging.*;
import jagex.oldscape.script.ScriptEvent;
import jagex.statics.*;
import jagex.oldscape.client.worldmap.*;

@SuppressWarnings("ALL")
public class SceneGraph {

  public static final int[] anIntArray1935;
  public static final int[] anIntArray1937;
  public static final int[] anIntArray1948;
  public static final int[] anIntArray1945;
  public static final int[] anIntArray1934;
  public static final int[] anIntArray1944;
  public static final int[] anIntArray1959;
  public static final int[] occluderCount;

  public static final NodeDeque<Tile> aNodeDeque1936;
  public static final SceneOccluder[] occluders;
  public static final SceneOccluder[][] A_SCENE_OCCLUDER_ARRAY_ARRAY_1939;
  public static final EntityMarker[] anEntityMarkerArray1940;

  public static final int anInt1938;

  public static final boolean[][][][] visibilityMap;

  public static int[][] xteaKeys;

  public static boolean[][] aBooleanArrayArray1951;

  public static boolean lowMemory;
  public static boolean viewportWalking;
  public static boolean acceptClick;

  public static int pendingDestinationX;
  public static int pendingDestinationY;
  public static int anInt1933;
  public static int renderCameraX;
  public static int renderFloor;
  public static int anInt1962;
  public static int renderCameraY;
  public static int renderTick;
  public static int anInt1931;
  public static int renderCameraZ;
  public static int walkingMouseX;
  public static int pitchSin;
  public static int walkingMouseY;
  public static int renderSceneX;
  public static int pitchCos;
  public static int renderSceneY;
  public static int yawSin;
  public static int renderAreaMinX;
  public static int yawCos;
  public static int renderAreaMaxX;
  public static int renderAreaMinY;
  public static int renderAreaMaxY;
  public static int viewportMinX;
  public static int viewportMinY;
  public static int viewportMaxX;
  public static int viewportMaxY;
  public static int viewportCenterX;
  public static int viewportCenterY;
  public static int floorLevel;
  public static int regionChunkX;
  public static int regionChunkY;
  public static int anInt1235;
  public static int anInt630;
  public static Sprite minimapSprite;

  static {
    lowMemory = true;
    anInt1962 = 0;
    renderFloor = 0;
    anEntityMarkerArray1940 = new EntityMarker[100];
    acceptClick = false;
    anInt1931 = 0;
    walkingMouseX = 0;
    walkingMouseY = 0;
    pendingDestinationX = -1;
    pendingDestinationY = -1;
    viewportWalking = false;
    anInt1938 = 4;
    occluderCount = new int[anInt1938];
    A_SCENE_OCCLUDER_ARRAY_ARRAY_1939 = new SceneOccluder[anInt1938][500];
    anInt1933 = 0;
    occluders = new SceneOccluder[500];
    aNodeDeque1936 = new NodeDeque<>();
    anIntArray1935 = new int[]{19, 55, 38, 155, 255, 110, 137, 205, 76};
    anIntArray1937 = new int[]{160, 192, 80, 96, 0, 144, 80, 48, 160};
    anIntArray1948 = new int[]{76, 8, 137, 4, 0, 1, 38, 2, 19};
    anIntArray1945 = new int[]{0, 0, 2, 0, 0, 2, 1, 1, 0};
    anIntArray1934 = new int[]{2, 0, 0, 2, 0, 0, 0, 4, 4};
    anIntArray1944 = new int[]{0, 4, 4, 8, 0, 0, 8, 0, 0};
    anIntArray1959 = new int[]{1, 1, 0, 0, 0, 8, 0, 0, 8};
    visibilityMap = new boolean[8][32][51][51];
  }

  int baseFloor;
  int tempMarkerCount;
  int[][][] renderCycles;
  int width;
  int floorCount;
  Tile[][][] tiles;
  EntityMarker[] tempMarkers;
  int length;
  int[][][] heights;
  int[][] tileShapes;
  int[][] tileRotations;

  public SceneGraph(int floorCount, int width, int length, int[][][] heights) {
    baseFloor = 0;
    tempMarkerCount = 0;
    tempMarkers = new EntityMarker[5000];
    tileShapes = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
    tileRotations = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
    this.floorCount = floorCount;
    this.width = width;
    this.length = length;
    tiles = new Tile[floorCount][width][length];
    renderCycles = new int[floorCount][width + 1][length + 1];
    this.heights = heights;
    initialize();
  }

  public static boolean isMovementPending() {
    return viewportWalking && pendingDestinationX != -1;
  }

  public static boolean contains(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
    if (var1 < var2 && var1 < var3 && var1 < var4) {
      return false;
    }
    if (var1 > var2 && var1 > var3 && var1 > var4) {
      return false;
    }
    if (var0 < var5 && var0 < var6 && var0 < var7) {
      return false;
    }
    if (var0 > var5 && var0 > var6 && var0 > var7) {
      return false;
    }
    int var8 = (var1 - var2) * (var6 - var5) - (var0 - var5) * (var3 - var2);
    int var9 = (var7 - var6) * (var1 - var3) - (var0 - var6) * (var4 - var3);
    int var10 = (var5 - var7) * (var1 - var4) - (var2 - var4) * (var0 - var7);
    if (var8 == 0) {
      if (var9 != 0) {
        return var9 < 0 ? var10 <= 0 : var10 >= 0;
      }
      return true;
    }
    return var8 < 0 ? var9 <= 0 && var10 <= 0 : var9 >= 0 && var10 >= 0;
  }

  public static int method1444(int var0, int var1) {
    var1 = (var0 & 127) * var1 >> 7;
    if (var1 < 2) {
      var1 = 2;
    } else if (var1 > 126) {
      var1 = 126;
    }

    return (var0 & 65408) + var1;
  }

  public static void defineVisibilityMap(int[] var0, int var1, int var2, int viewportMaxX, int viewportMaxY) {
    viewportMinX = 0;
    viewportMinY = 0;
    SceneGraph.viewportMaxX = viewportMaxX;
    SceneGraph.viewportMaxY = viewportMaxY;
    viewportCenterX = viewportMaxX / 2;
    viewportCenterY = viewportMaxY / 2;
    boolean[][][][] var5 = new boolean[var0.length][32][53][53];

    int var6;
    int var7;
    int var8;
    int var9;
    int var11;
    int var12;
    for (var6 = 128; var6 <= 383; var6 += 32) {
      for (var7 = 0; var7 < 2048; var7 += 64) {
        pitchSin = JagGraphics3D.SIN_TABLE[var6];
        pitchCos = JagGraphics3D.COS_TABLE[var6];
        yawSin = JagGraphics3D.SIN_TABLE[var7];
        yawCos = JagGraphics3D.COS_TABLE[var7];
        var8 = (var6 - 128) / 32;
        var9 = var7 / 64;

        for (int var10 = -26; var10 < 26; ++var10) {
          for (var11 = -26; var11 < 26; ++var11) {
            var12 = var10 * 128;
            int var13 = var11 * 128;
            boolean var14 = false;

            for (int var15 = -var1; var15 <= var2; var15 += 128) {
              if (method1455(var12, var0[var8] + var15, var13)) {
                var14 = true;
                break;
              }
            }

            var5[var8][var9][var10 + 1 + 25][var11 + 1 + 25] = var14;
          }
        }
      }
    }

    for (var6 = 0; var6 < 8; ++var6) {
      for (var7 = 0; var7 < 32; ++var7) {
        for (var8 = -25; var8 < 25; ++var8) {
          for (var9 = -25; var9 < 25; ++var9) {
            boolean var16 = false;

            label76:
            for (var11 = -1; var11 <= 1; ++var11) {
              for (var12 = -1; var12 <= 1; ++var12) {
                if (var5[var6][var7][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                  var16 = true;
                  break label76;
                }

                if (var5[var6][(var7 + 1) % 31][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                  var16 = true;
                  break label76;
                }

                if (var5[var6 + 1][var7][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                  var16 = true;
                  break label76;
                }

                if (var5[var6 + 1][(var7 + 1) % 31][var8 + var11 + 1 + 25][var9 + var12 + 1 + 25]) {
                  var16 = true;
                  break label76;
                }
              }
            }

            visibilityMap[var6][var7][var8 + 25][var9 + 25] = var16;
          }
        }
      }
    }

  }

  public static boolean method1455(int var0, int var1, int var2) {
    int var3 = var0 * yawCos + var2 * yawSin >> 16;
    int var4 = var2 * yawCos - var0 * yawSin >> 16;
    int var5 = var4 * pitchCos + pitchSin * var1 >> 16;
    int var6 = pitchCos * var1 - var4 * pitchSin >> 16;
    if (var5 >= 50 && var5 <= 3500) {
      int var7 = var3 * 128 / var5 + viewportCenterX;
      int var8 = var6 * 128 / var5 + viewportCenterY;
      return var7 >= viewportMinX && var7 <= viewportMaxX && var8 >= viewportMinY && var8 <= viewportMaxY;
    }
    return false;
  }

  public static void occlude(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
    SceneOccluder occluder = new SceneOccluder();
    occluder.minSceneX = var2 / 128;
    occluder.maxSceneX = var3 / 128;
    occluder.minSceneY = var4 / 128;
    occluder.maxSceneY = var5 / 128;
    occluder.flag = var1;
    occluder.minAbsoluteX = var2;
    occluder.maxAbsoluteX = var3;
    occluder.minAbsoluteY = var4;
    occluder.maxAbsoluteY = var5;
    occluder.componentHeight = var6;
    occluder.tileHeight = var7;
    A_SCENE_OCCLUDER_ARRAY_ARRAY_1939[var0][occluderCount[var0]++] = occluder;
  }

  public static void unsetPendingMovement() {
    pendingDestinationX = -1;
    viewportWalking = false;
  }

  public static void absoluteToViewport(int x, int y, int height) {
    if (x >= 128 && y >= 128 && x <= 13056 && y <= 13056) {
      int z = getTileHeight(x, y, floorLevel) - height;
      x -= Camera.x;
      z -= Camera.z;
      y -= Camera.y;
      int psin = JagGraphics3D.SIN_TABLE[Camera.pitch];
      int pcos = JagGraphics3D.COS_TABLE[Camera.pitch];
      int ysin = JagGraphics3D.SIN_TABLE[Camera.yaw];
      int ycos = JagGraphics3D.COS_TABLE[Camera.yaw];
      int angle = ysin * y + x * ycos >> 16;
      y = ycos * y - x * ysin >> 16;
      x = angle;
      angle = z * pcos - psin * y >> 16;
      y = psin * z + pcos * y >> 16;
      if (y >= 50) {
        client.viewportRenderX = x * client.viewportScale / y + client.viewportWidth / 2;
        client.viewportRenderY = angle * client.viewportScale / y + client.viewportHeight / 2;
      } else {
        client.viewportRenderX = -1;
        client.viewportRenderY = -1;
      }

    } else {
      client.viewportRenderX = -1;
      client.viewportRenderY = -1;
    }
  }

  public static int getTileHeight(int var0, int var1, int baseFloorLevel) {
    int var3 = var0 >> 7;
    int var4 = var1 >> 7;
    if (var3 >= 0 && var4 >= 0 && var3 <= 103 && var4 <= 103) {
      int var5 = baseFloorLevel;
      if (baseFloorLevel < 3 && (SceneGraphRenderData.sceneRenderRules[1][var3][var4] & 2) == 2) {
        var5 = baseFloorLevel + 1;
      }

      int var6 = var0 & 127;
      int var7 = var1 & 127;
      int var8 = (128 - var6) * SceneGraphRenderData.tileHeights[var5][var3][var4] + var6 * SceneGraphRenderData.tileHeights[var5][var3 + 1][var4] >> 7;
      int var9 = var6 * SceneGraphRenderData.tileHeights[var5][var3 + 1][var4 + 1] + SceneGraphRenderData.tileHeights[var5][var3][var4 + 1] * (128 - var6) >> 7;
      return var9 * var7 + var8 * (128 - var7) >> 7;
    }
    return 0;
  }

  public static int method848(int var0, int var1) {
    if (var0 == -1) {
      return 12345678;
    }
    var1 = (var0 & 127) * var1 / 128;
    if (var1 < 2) {
      var1 = 2;
    } else if (var1 > 126) {
      var1 = 126;
    }

    return (var0 & 65408) + var1;
  }

  public static void method517() {
    int var0 = anInt1235 * 16384 + 64;
    int var1 = Camera.anInt889 * 16384 + 64;
    int var2 = getTileHeight(var0, var1, floorLevel) - StockmarketListingWorldComparator.anInt347;
    if (Camera.x < var0) {
      Camera.x = (var0 - Camera.x) * Statics53.anInt520 / 1000 + Camera.x + MouseRecorder.anInt388;
      if (Camera.x > var0) {
        Camera.x = var0;
      }
    }

    if (Camera.x > var0) {
      Camera.x -= Statics53.anInt520 * (Camera.x - var0) / 1000 + MouseRecorder.anInt388;
      if (Camera.x < var0) {
        Camera.x = var0;
      }
    }

    if (Camera.z < var2) {
      Camera.z = (var2 - Camera.z) * Statics53.anInt520 / 1000 + Camera.z + MouseRecorder.anInt388;
      if (Camera.z > var2) {
        Camera.z = var2;
      }
    }

    if (Camera.z > var2) {
      Camera.z -= Statics53.anInt520 * (Camera.z - var2) / 1000 + MouseRecorder.anInt388;
      if (Camera.z < var2) {
        Camera.z = var2;
      }
    }

    if (Camera.y < var1) {
      Camera.y = (var1 - Camera.y) * Statics53.anInt520 / 1000 + Camera.y + MouseRecorder.anInt388;
      if (Camera.y > var1) {
        Camera.y = var1;
      }
    }

    if (Camera.y > var1) {
      Camera.y -= Statics53.anInt520 * (Camera.y - var1) / 1000 + MouseRecorder.anInt388;
      if (Camera.y < var1) {
        Camera.y = var1;
      }
    }

    var0 = GameShell.anInt1288 * 128 + 64;
    var1 = SecureRandomService.anInt458 * 16384 + 64;
    var2 = getTileHeight(var0, var1, floorLevel) - Statics52.anInt499;
    int var3 = var0 - Camera.x;
    int var4 = var2 - Camera.z;
    int var5 = var1 - Camera.y;
    int var6 = (int) Math.sqrt(var5 * var5 + var3 * var3);
    int var7 = (int) (Math.atan2(var4, var6) * 325.949D) & 2047;
    int var8 = (int) (Math.atan2(var3, var5) * -325.949D) & 2047;
    if (var7 < 128) {
      var7 = 128;
    }

    if (var7 > 383) {
      var7 = 383;
    }

    if (Camera.pitch < var7) {
      Camera.pitch = (var7 - Camera.pitch) * ScriptEvent.anInt1806 / 1000 + Camera.pitch + Statics54.anInt627;
      if (Camera.pitch > var7) {
        Camera.pitch = var7;
      }
    }

    if (Camera.pitch > var7) {
      Camera.pitch -= ScriptEvent.anInt1806 * (Camera.pitch - var7) / 1000 + Statics54.anInt627;
      if (Camera.pitch < var7) {
        Camera.pitch = var7;
      }
    }

    int var9 = var8 - Camera.yaw;
    if (var9 > 1024) {
      var9 -= 2048;
    }

    if (var9 < -1024) {
      var9 += 2048;
    }

    if (var9 > 0) {
      Camera.yaw = Camera.yaw + Statics54.anInt627 + var9 * ScriptEvent.anInt1806 / 1000;
      Camera.yaw &= 2047;
    }

    if (var9 < 0) {
      Camera.yaw -= Statics54.anInt627 + -var9 * ScriptEvent.anInt1806 / 1000;
      Camera.yaw &= 2047;
    }

    int var10 = var8 - Camera.yaw;
    if (var10 > 1024) {
      var10 -= 2048;
    }

    if (var10 < -1024) {
      var10 += 2048;
    }

    if (var10 < 0 && var9 > 0 || var10 > 0 && var9 < 0) {
      Camera.yaw = var8;
    }

  }

  public static void drawMinimapObjects(int var0, int var1, int var2, int var3, int var4) {
    long var5 = client.sceneGraph.getBoundaryUidAt(var0, var1, var2);
    int var7;
    int var8;
    int var9;
    int var10;
    int var12;
    int var13;
    if (var5 != 0L) {
      var7 = client.sceneGraph.getConfigAt(var0, var1, var2, var5);
      var8 = var7 >> 6 & 3;
      var9 = var7 & 31;
      var10 = var3;
      if (Model.method857(var5)) {
        var10 = var4;
      }

      int[] var11 = minimapSprite.pixels;
      var12 = var1 * 4 + (103 - var2) * 2048 + 24624;
      var13 = EntityUID.getObjectId(var5);
      ObjectDefinition var14 = ObjectDefinition.get(var13);
      if (var14.mapSceneId != -1) {
        IndexedSprite var15 = Statics52.mapscene[var14.mapSceneId];
        if (var15 != null) {
          int var16 = (var14.sizeX * 4 - var15.anInt378) / 2;
          int var17 = (var14.sizeY * 4 - var15.anInt377) / 2;
          var15.renderAt(var1 * 4 + var16 + 48, var17 + (104 - var2 - var14.sizeY) * 4 + 48);
        }
      } else {
        if (var9 == 0 || var9 == 2) {
          if (var8 == 0) {
            var11[var12] = var10;
            var11[var12 + 512] = var10;
            var11[var12 + 1024] = var10;
            var11[var12 + 1536] = var10;
          } else if (var8 == 1) {
            var11[var12] = var10;
            var11[var12 + 1] = var10;
            var11[var12 + 2] = var10;
            var11[var12 + 3] = var10;
          } else if (var8 == 2) {
            var11[var12 + 3] = var10;
            var11[var12 + 512 + 3] = var10;
            var11[var12 + 1024 + 3] = var10;
            var11[var12 + 1536 + 3] = var10;
          } else if (var8 == 3) {
            var11[var12 + 1536] = var10;
            var11[var12 + 1536 + 1] = var10;
            var11[var12 + 1536 + 2] = var10;
            var11[var12 + 1536 + 3] = var10;
          }
        }

        if (var9 == 3) {
          if (var8 == 0) {
            var11[var12] = var10;
          } else if (var8 == 1) {
            var11[var12 + 3] = var10;
          } else if (var8 == 2) {
            var11[var12 + 1536 + 3] = var10;
          } else if (var8 == 3) {
            var11[var12 + 1536] = var10;
          }
        }

        if (var9 == 2) {
          if (var8 == 3) {
            var11[var12] = var10;
            var11[var12 + 512] = var10;
            var11[var12 + 1024] = var10;
            var11[var12 + 1536] = var10;
          } else if (var8 == 0) {
            var11[var12] = var10;
            var11[var12 + 1] = var10;
            var11[var12 + 2] = var10;
            var11[var12 + 3] = var10;
          } else if (var8 == 1) {
            var11[var12 + 3] = var10;
            var11[var12 + 512 + 3] = var10;
            var11[var12 + 1024 + 3] = var10;
            var11[var12 + 1536 + 3] = var10;
          } else if (var8 == 2) {
            var11[var12 + 1536] = var10;
            var11[var12 + 1536 + 1] = var10;
            var11[var12 + 1536 + 2] = var10;
            var11[var12 + 1536 + 3] = var10;
          }
        }
      }
    }

    var5 = client.sceneGraph.method1461(var0, var1, var2);
    if (var5 != 0L) {
      var7 = client.sceneGraph.getConfigAt(var0, var1, var2, var5);
      var8 = var7 >> 6 & 3;
      var9 = var7 & 31;
      var10 = EntityUID.getObjectId(var5);
      ObjectDefinition var24 = ObjectDefinition.get(var10);
      int var19;
      if (var24.mapSceneId != -1) {
        IndexedSprite var18 = Statics52.mapscene[var24.mapSceneId];
        if (var18 != null) {
          var13 = (var24.sizeX * 4 - var18.anInt378) / 2;
          var19 = (var24.sizeY * 4 - var18.anInt377) / 2;
          var18.renderAt(var1 * 4 + var13 + 48, var19 + (104 - var2 - var24.sizeY) * 4 + 48);
        }
      } else if (var9 == 9) {
        var12 = 15658734;
        if (Model.method857(var5)) {
          var12 = 15597568;
        }

        int[] var23 = minimapSprite.pixels;
        var19 = var1 * 4 + (103 - var2) * 2048 + 24624;
        if (var8 != 0 && var8 != 2) {
          var23[var19] = var12;
          var23[var19 + 1 + 512] = var12;
          var23[var19 + 1024 + 2] = var12;
          var23[var19 + 1536 + 3] = var12;
        } else {
          var23[var19 + 1536] = var12;
          var23[var19 + 1 + 1024] = var12;
          var23[var19 + 512 + 2] = var12;
          var23[var19 + 3] = var12;
        }
      }
    }

    var5 = client.sceneGraph.method1457(var0, var1, var2);
    if (var5 != 0L) {
      var7 = EntityUID.getObjectId(var5);
      ObjectDefinition var20 = ObjectDefinition.get(var7);
      if (var20.mapSceneId != -1) {
        IndexedSprite var21 = Statics52.mapscene[var20.mapSceneId];
        if (var21 != null) {
          var10 = (var20.sizeX * 4 - var21.anInt378) / 2;
          int var22 = (var20.sizeY * 4 - var21.anInt377) / 2;
          var21.renderAt(var1 * 4 + var10 + 48, (104 - var2 - var20.sizeY) * 4 + var22 + 48);
        }
      }
    }

  }

  public static void method1(int var0, int var1, int var2, int var3, Sprite var4, ComponentSprite var5) {
    int var6 = var3 * var3 + var2 * var2;
    if (var6 > 4225 && var6 < 90000) {
      int var7 = Camera.yOffset & 2047;
      int var8 = JagGraphics3D.SIN_TABLE[var7];
      int var9 = JagGraphics3D.COS_TABLE[var7];
      int var10 = var9 * var2 + var3 * var8 >> 16;
      int var11 = var3 * var9 - var8 * var2 >> 16;
      double var12 = Math.atan2(var10, var11);
      int var14 = var5.anInt380 / 2 - 25;
      int var15 = (int) (Math.sin(var12) * (double) var14);
      int var16 = (int) (Math.cos(var12) * (double) var14);
      byte var17 = 20;
      PendingSpawn.mapedge.method831(var15 + (var0 + var5.anInt380 / 2 - var17 / 2), var5.anInt568 / 2 + var1 - var17 / 2 - var16 - 10, var17, var17, 15, 15, var12, 256);
    } else {
      Statics17.method891(var0, var1, var2, var3, var4, var5);
    }

  }

  public static int method800(int var0, int var1, int var2) {
    if ((SceneGraphRenderData.sceneRenderRules[var0][var1][var2] & 8) != 0) {
      return 0;
    }
    return var0 > 0 && (SceneGraphRenderData.sceneRenderRules[1][var1][var2] & 2) != 0 ? var0 - 1 : var0;
  }

  public static boolean method257(Model var0, int var1, int var2, int var3) {
    boolean var4 = CursorEntities.mouseInViewport;
    if (!var4) {
      return false;
    }
    int var5;
    int var6;
    int var7;
    int var8;
    int var11;
    int var12;
    int var13;
    int var16;
    int var17;
    if (!CursorEntities.aBoolean660) {
      var5 = pitchSin;
      var6 = pitchCos;
      var7 = yawSin;
      var8 = yawCos;
      byte var9 = 50;
      short var10 = 3500;
      var11 = (CursorEntities.viewportMouseX - JagGraphics3D.anInt386) * var9 / JagGraphics3D.scale;
      var12 = (CursorEntities.viewportMouseY - JagGraphics3D.anInt366) * var9 / JagGraphics3D.scale;
      var13 = (CursorEntities.viewportMouseX - JagGraphics3D.anInt386) * var10 / JagGraphics3D.scale;
      int var14 = (CursorEntities.viewportMouseY - JagGraphics3D.anInt366) * var10 / JagGraphics3D.scale;
      int var15 = JagGraphics3D.method629(var12, var9, var6, var5);
      var16 = JagGraphics3D.method621(var12, var9, var6, var5);
      var12 = var15;
      var15 = JagGraphics3D.method629(var14, var10, var6, var5);
      var17 = JagGraphics3D.method621(var14, var10, var6, var5);
      var14 = var15;
      var15 = JagGraphics3D.method623(var11, var16, var8, var7);
      var16 = JagGraphics3D.method630(var11, var16, var8, var7);
      var11 = var15;
      var15 = JagGraphics3D.method623(var13, var17, var8, var7);
      var17 = JagGraphics3D.method630(var13, var17, var8, var7);
      CursorEntities.anInt659 = (var15 + var11) / 2;
      CursorEntities.anInt657 = (var12 + var14) / 2;
      CursorEntities.anInt658 = (var16 + var17) / 2;
      anInt630 = (var15 - var11) / 2;
      CursorEntities.anInt662 = (var14 - var12) / 2;
      CursorEntities.anInt655 = (var17 - var16) / 2;
      SecureRandomCallable.anInt1341 = Math.abs(anInt630);
      AssociateComparatorByWorld.anInt822 = Math.abs(CursorEntities.anInt662);
      ParameterDefinition.anInt386 = Math.abs(CursorEntities.anInt655);
    }

    var5 = var0.anInt556 + var1;
    var6 = var2 + var0.anInt579;
    var7 = var3 + var0.anInt1437;
    var8 = var0.anInt711;
    var16 = var0.anInt709;
    var17 = var0.anInt1424;
    var11 = CursorEntities.anInt659 - var5;
    var12 = CursorEntities.anInt657 - var6;
    var13 = CursorEntities.anInt658 - var7;
    if (Math.abs(var11) > var8 + SecureRandomCallable.anInt1341) {
      return false;
    }
    if (Math.abs(var12) > var16 + AssociateComparatorByWorld.anInt822) {
      return false;
    }
    if (Math.abs(var13) > var17 + ParameterDefinition.anInt386) {
      return false;
    }
    if (Math.abs(var13 * CursorEntities.anInt662 - var12 * CursorEntities.anInt655) > var16 * ParameterDefinition.anInt386 + var17 * AssociateComparatorByWorld.anInt822) {
      return false;
    }
    if (Math.abs(var11 * CursorEntities.anInt655 - var13 * anInt630) > var17 * SecureRandomCallable.anInt1341 + var8 * ParameterDefinition.anInt386) {
      return false;
    }
    return Math.abs(var12 * anInt630 - var11 * CursorEntities.anInt662) <= var16 * SecureRandomCallable.anInt1341 + var8 * AssociateComparatorByWorld.anInt822;
  }

  public static void load() {
    client.writeKeepAlivePacket(false);
    client.missingRegionsCount = 0;
    boolean loadedSuccessfully = true;

    for (int i = 0; i < WorldMapObjectIcon.mapFiles.length; ++i) {
      if (Statics57.mapFileIds[i] != -1 && WorldMapObjectIcon.mapFiles[i] == null) {
        WorldMapObjectIcon.mapFiles[i] = Archive.landscape.unpack(Statics57.mapFileIds[i], 0);
        if (WorldMapObjectIcon.mapFiles[i] == null) {
          loadedSuccessfully = false;
          ++client.missingRegionsCount;
        }
      }

      if (Statics44.objectFileIds[i] != -1 && LoadedArchive.objectFiles[i] == null) {
        LoadedArchive.objectFiles[i] = Archive.landscape.unpack(Statics44.objectFileIds[i], 0, xteaKeys[i]);
        if (LoadedArchive.objectFiles[i] == null) {
          loadedSuccessfully = false;
          ++client.missingRegionsCount;
        }
      }
    }

    if (!loadedSuccessfully) {
      client.loadingMessageDrawState = 1;
    } else {
      client.missingObjectsCount = 0;
      loadedSuccessfully = true;

      for (int i = 0; i < WorldMapObjectIcon.mapFiles.length; ++i) {
        byte[] file = LoadedArchive.objectFiles[i];
        if (file != null) {
          int regionX = (Mouse.mapRegions[i] >> 8) * 64 - client.baseX;
          int regionY = (Mouse.mapRegions[i] & 255) * 64 - client.baseY;
          if (client.dynamicScene) {
            regionX = 10;
            regionY = 10;
          }

          boolean done = true;
          Buffer objectBuffer = new Buffer(file);
          int var8 = -1;

          label1329:
          while (true) {
            int var9 = objectBuffer.gSmartsSeq();
            if (var9 == 0) {
              loadedSuccessfully &= done;
              break;
            }

            var8 += var9;
            int var11 = 0;
            boolean var12 = false;

            while (true) {
              while (!var12) {
                int var13 = objectBuffer.gSmarts();
                if (var13 == 0) {
                  continue label1329;
                }

                var11 += var13 - 1;
                int var14 = var11 & 63;
                int var15 = var11 >> 6 & 63;
                int var16 = objectBuffer.g1() >> 2;
                int var17 = regionX + var15;
                int var18 = regionY + var14;
                if (var17 > 0 && var18 > 0 && var17 < 103 && var18 < 103) {
                  ObjectDefinition var19 = ObjectDefinition.get(var8);
                  if (var16 != 22 || !client.lowMemory || var19.mapDoorFlag != 0 || var19.anInt791 == 1 || var19.aBoolean1507) {
                    if (!var19.isValid()) {
                      ++client.missingObjectsCount;
                      done = false;
                    }

                    var12 = true;
                  }
                }
              }

              int var13 = objectBuffer.gSmarts();
              if (var13 == 0) {
                break;
              }

              objectBuffer.g1();
            }
          }
        }
      }

      if (!loadedSuccessfully) {
        client.loadingMessageDrawState = 2;
      } else {
        if (client.loadingMessageDrawState != 0) {
          client.drawLoadingMessage("Loading - please wait." + "<br>" + " (" + 100 + "%" + ")", true);
        }

        AudioSystem.process();
        client.sceneGraph.initialize();

        for (int i = 0; i < 4; ++i) {
          client.collisionMaps[i].initialize();
        }

        for (int z = 0; z < 4; ++z) {
          for (int x = 0; x < 104; ++x) {
            for (int y = 0; y < 104; ++y) {
              SceneGraphRenderData.sceneRenderRules[z][x][y] = 0;
            }
          }
        }

        AudioSystem.process();
        initializeTileRenderData();
        int mapCount = WorldMapObjectIcon.mapFiles.length;
        ClientProt.method4();
        client.writeKeepAlivePacket(true);
        if (!client.dynamicScene) {
          for (int i = 0; i < mapCount; ++i) {
            int x = (Mouse.mapRegions[i] >> 8) * 64 - client.baseX;
            int y = (Mouse.mapRegions[i] & 255) * 64 - client.baseY;
            byte[] data = WorldMapObjectIcon.mapFiles[i];
            if (data != null) {
              AudioSystem.process();
              WorldMapRenderRules.loadTile(data, x, y, ServerProt.chunkX * 8 - 48, Node_Sub19.chunkY * 8 - 48, client.collisionMaps);
            }
          }

          for (int i = 0; i < mapCount; ++i) {
            int x = (Mouse.mapRegions[i] >> 8) * 64 - client.baseX;
            int y = (Mouse.mapRegions[i] & 255) * 64 - client.baseY;
            byte[] data = WorldMapObjectIcon.mapFiles[i];
            if (data == null && Node_Sub19.chunkY < 800) {
              AudioSystem.process();
              SceneGraphRenderData.initializeTileHeights(x, y, 64, 64);
            }
          }

          client.writeKeepAlivePacket(true);

          for (int i = 0; i < mapCount; ++i) {
            byte[] data = LoadedArchive.objectFiles[i];
            if (data != null) {
              int x = (Mouse.mapRegions[i] >> 8) * 64 - client.baseX;
              int y = (Mouse.mapRegions[i] & 255) * 64 - client.baseY;
              AudioSystem.process();
              SceneGraph scene = client.sceneGraph;
              CollisionMap[] collisionMaps = client.collisionMaps;
              Buffer buffer = new Buffer(data);
              int objectId = -1;

              while (true) {
                int modifier = buffer.gSmartsSeq();
                if (modifier == 0) {
                  break;
                }

                objectId += modifier;
                int var11 = 0;

                while (true) {
                  int var66 = buffer.gSmarts();
                  if (var66 == 0) {
                    break;
                  }

                  var11 += var66 - 1;
                  int var13 = var11 & 63;
                  int var14 = var11 >> 6 & 63;
                  int var15 = var11 >> 12;
                  int var16 = buffer.g1();
                  int var17 = var16 >> 2;
                  int var18 = var16 & 3;
                  int var25 = var14 + x;
                  int var26 = y + var13;
                  if (var25 > 0 && var26 > 0 && var25 < 103 && var26 < 103) {
                    int var30 = var15;
                    if ((SceneGraphRenderData.sceneRenderRules[1][var25][var26] & 2) == 2) {
                      var30 = var15 - 1;
                    }

                    CollisionMap var47 = null;
                    if (var30 >= 0) {
                      var47 = collisionMaps[var30];
                    }

                    StockmarketListingLifetimeComparator.method414(var15, var25, var26, objectId, var18, var17, scene, var47);
                  }
                }
              }
            }
          }
        }

        if (client.dynamicScene) {
          for (int z = 0; z < 4; ++z) {
            AudioSystem.process();

            for (int var3 = 0; var3 < 13; ++var3) {
              for (int var4 = 0; var4 < 13; ++var4) {
                boolean var64 = false;
                int var10 = client.dynamicSceneData[z][var3][var4];
                if (var10 != -1) {
                  int var65 = var10 >> 24 & 3;
                  int var22 = var10 >> 1 & 3;
                  int var8 = var10 >> 14 & 1023;
                  int var9 = var10 >> 3 & 2047;
                  int var11 = (var8 / 8 << 8) + var9 / 8;

                  for (int var66 = 0; var66 < Mouse.mapRegions.length; ++var66) {
                    if (Mouse.mapRegions[var66] == var11 && WorldMapObjectIcon.mapFiles[var66] != null) {
                      byte[] var23 = WorldMapObjectIcon.mapFiles[var66];
                      int var14 = var3 * 8;
                      int var15 = var4 * 8;
                      int var16 = (var8 & 7) * 8;
                      int var17 = (var9 & 7) * 8;
                      CollisionMap[] map = client.collisionMaps;

                      for (int var25 = 0; var25 < 8; ++var25) {
                        for (int var26 = 0; var26 < 8; ++var26) {
                          if (var14 + var25 > 0 && var25 + var14 < 103 && var15 + var26 > 0 && var26 + var15 < 103) {
                            int[] flags = map[z].flags[var14 + var25];
                            flags[var15 + var26] &= -16777217;
                          }
                        }
                      }

                      Buffer var67 = new Buffer(var23);

                      for (int var26 = 0; var26 < 4; ++var26) {
                        for (int var30 = 0; var30 < 64; ++var30) {
                          for (int var31 = 0; var31 < 64; ++var31) {
                            if (var26 == var65 && var30 >= var16 && var30 < var16 + 8 && var31 >= var17 && var31 < var17 + 8) {
                              int var35 = var30 & 7;
                              int var36 = var31 & 7;
                              int var37 = var22 & 3;
                              int var38;
                              if (var37 == 0) {
                                var38 = var35;
                              } else if (var37 == 1) {
                                var38 = var36;
                              } else if (var37 == 2) {
                                var38 = 7 - var35;
                              } else {
                                var38 = 7 - var36;
                              }

                              int var41 = var14 + var38;
                              int var43 = var30 & 7;
                              int var44 = var31 & 7;
                              int var45 = var22 & 3;
                              int var46;
                              if (var45 == 0) {
                                var46 = var44;
                              } else if (var45 == 1) {
                                var46 = 7 - var43;
                              } else if (var45 == 2) {
                                var46 = 7 - var44;
                              } else {
                                var46 = var43;
                              }

                              method416(var67, z, var41, var46 + var15, 0, 0, var22);
                            } else {
                              method416(var67, 0, -1, -1, 0, 0, 0);
                            }
                          }
                        }
                      }

                      var64 = true;
                      break;
                    }
                  }
                }

                if (!var64) {
                  SceneGraphRenderData.method189(z, var3 * 8, var4 * 8);
                }
              }
            }
          }

          for (int var20 = 0; var20 < 13; ++var20) {
            for (int var3 = 0; var3 < 13; ++var3) {
              int var4 = client.dynamicSceneData[0][var20][var3];
              if (var4 == -1) {
                SceneGraphRenderData.initializeTileHeights(var20 * 8, var3 * 8, 8, 8);
              }
            }
          }

          client.writeKeepAlivePacket(true);

          for (int var20 = 0; var20 < 4; ++var20) {
            AudioSystem.process();

            for (int var3 = 0; var3 < 13; ++var3) {
              for (int var4 = 0; var4 < 13; ++var4) {
                int var5 = client.dynamicSceneData[var20][var3][var4];
                if (var5 != -1) {
                  int var10 = var5 >> 24 & 3;
                  int var65 = var5 >> 1 & 3;
                  int var22 = var5 >> 14 & 1023;
                  int var8 = var5 >> 3 & 2047;
                  int var9 = (var22 / 8 << 8) + var8 / 8;

                  for (int var11 = 0; var11 < Mouse.mapRegions.length; ++var11) {
                    if (Mouse.mapRegions[var11] == var9 && LoadedArchive.objectFiles[var11] != null) {
                      Statics54.method444(LoadedArchive.objectFiles[var11], var20, var3 * 8, var4 * 8, var10, (var22 & 7) * 8, (var8 & 7) * 8, var65, client.sceneGraph, client.collisionMaps);
                      break;
                    }
                  }
                }
              }
            }
          }
        }

        client.writeKeepAlivePacket(true);
        AudioSystem.process();
        SceneGraph scene = client.sceneGraph;
        CollisionMap[] var73 = client.collisionMaps;

        for (int var4 = 0; var4 < 4; ++var4) {
          for (int var5 = 0; var5 < 104; ++var5) {
            for (int var10 = 0; var10 < 104; ++var10) {
              if ((SceneGraphRenderData.sceneRenderRules[var4][var5][var10] & 1) == 1) {
                int var65 = var4;
                if ((SceneGraphRenderData.sceneRenderRules[1][var5][var10] & 2) == 2) {
                  var65 = var4 - 1;
                }

                if (var65 >= 0) {
                  var73[var65].setBlockedByTile(var5, var10);
                }
              }
            }
          }
        }

        SceneGraphRenderData.tileHueOffset += (int) (Math.random() * 5.0D) - 2;
        if (SceneGraphRenderData.tileHueOffset < -8) {
          SceneGraphRenderData.tileHueOffset = -8;
        }

        if (SceneGraphRenderData.tileHueOffset > 8) {
          SceneGraphRenderData.tileHueOffset = 8;
        }

        SceneGraphRenderData.tileLightnessOffset += (int) (Math.random() * 5.0D) - 2;
        if (SceneGraphRenderData.tileLightnessOffset < -16) {
          SceneGraphRenderData.tileLightnessOffset = -16;
        }

        if (SceneGraphRenderData.tileLightnessOffset > 16) {
          SceneGraphRenderData.tileLightnessOffset = 16;
        }

        for (int var4 = 0; var4 < 4; ++var4) {
          byte[][] var69 = SceneGraphRenderData.shadows[var4];
          int var11 = (int) Math.sqrt(5100.0D);
          int var66 = var11 * 768 >> 8;

          for (int var13 = 1; var13 < 103; ++var13) {
            for (int var14 = 1; var14 < 103; ++var14) {
              int var15 = SceneGraphRenderData.tileHeights[var4][var14 + 1][var13] - SceneGraphRenderData.tileHeights[var4][var14 - 1][var13];
              int var16 = SceneGraphRenderData.tileHeights[var4][var14][var13 + 1] - SceneGraphRenderData.tileHeights[var4][var14][var13 - 1];
              int var17 = (int) Math.sqrt(var16 * var16 + var15 * var15 + 65536);
              int var18 = (var15 << 8) / var17;
              int var25 = 65536 / var17;
              int var26 = (var16 << 8) / var17;
              int var30 = (var18 * -50 + var26 * -50 + var25 * -10) / var66 + 96;
              int var31 = (var69[var14][var13 + 1] >> 3) + (var69[var14 - 1][var13] >> 2) + (var69[var14][var13 - 1] >> 2) + (var69[var14 + 1][var13] >> 3) + (var69[var14][var13] >> 1);
              DefaultAudioSystemProvider.tileLighting[var14][var13] = var30 - var31;
            }
          }

          for (int var13 = 0; var13 < 104; ++var13) {
            SceneGraphRenderData.blendHue[var13] = 0;
            LoadedArchive.tileBlendSaturation[var13] = 0;
            SceneGraphRenderData.blendLightness[var13] = 0;
            NamedFont.tileBlendHueWeight[var13] = 0;
            SceneGraphRenderData.blendColorCount[var13] = 0;
          }

          for (int var13 = -5; var13 < 109; ++var13) {
            for (int var14 = 0; var14 < 104; ++var14) {
              int var15 = var13 + 5;
              int var10002;
              if (var15 >= 0 && var15 < 104) {
                int var16 = SceneGraphRenderData.underlays[var4][var15][var14] & 255;
                if (var16 > 0) {
                  TileUnderlay underlay = TileUnderlay.get(var16 - 1);
                  int[] var10000 = SceneGraphRenderData.blendHue;
                  var10000[var14] += underlay.blendHue;
                  var10000 = LoadedArchive.tileBlendSaturation;
                  var10000[var14] += underlay.blendSaturation;
                  var10000 = SceneGraphRenderData.blendLightness;
                  var10000[var14] += underlay.blendLightness;
                  var10000 = NamedFont.tileBlendHueWeight;
                  var10000[var14] += underlay.blendHueWeight;
                  var10002 = SceneGraphRenderData.blendColorCount[var14]++;
                }
              }

              int var16 = var13 - 5;
              if (var16 >= 0 && var16 < 104) {
                int var17 = SceneGraphRenderData.underlays[var4][var16][var14] & 255;
                if (var17 > 0) {
                  TileUnderlay underlay = TileUnderlay.get(var17 - 1);
                  int[] var10000 = SceneGraphRenderData.blendHue;
                  var10000[var14] -= underlay.blendHue;
                  var10000 = LoadedArchive.tileBlendSaturation;
                  var10000[var14] -= underlay.blendSaturation;
                  var10000 = SceneGraphRenderData.blendLightness;
                  var10000[var14] -= underlay.blendLightness;
                  var10000 = NamedFont.tileBlendHueWeight;
                  var10000[var14] -= underlay.blendHueWeight;
                  var10002 = SceneGraphRenderData.blendColorCount[var14]--;
                }
              }
            }

            if (var13 >= 1 && var13 < 103) {
              int var14 = 0;
              int var15 = 0;
              int var16 = 0;
              int var17 = 0;
              int var18 = 0;

              for (int var25 = -5; var25 < 109; ++var25) {
                int var26 = var25 + 5;
                if (var26 >= 0 && var26 < 104) {
                  var14 += SceneGraphRenderData.blendHue[var26];
                  var15 += LoadedArchive.tileBlendSaturation[var26];
                  var16 += SceneGraphRenderData.blendLightness[var26];
                  var17 += NamedFont.tileBlendHueWeight[var26];
                  var18 += SceneGraphRenderData.blendColorCount[var26];
                }

                int var30 = var25 - 5;
                if (var30 >= 0 && var30 < 104) {
                  var14 -= SceneGraphRenderData.blendHue[var30];
                  var15 -= LoadedArchive.tileBlendSaturation[var30];
                  var16 -= SceneGraphRenderData.blendLightness[var30];
                  var17 -= NamedFont.tileBlendHueWeight[var30];
                  var18 -= SceneGraphRenderData.blendColorCount[var30];
                }

                if (var25 >= 1 && var25 < 103 && (!client.lowMemory || (SceneGraphRenderData.sceneRenderRules[0][var13][var25] & 2) != 0 || (SceneGraphRenderData.sceneRenderRules[var4][var13][var25] & 16) == 0)) {
                  if (var4 < SceneGraphRenderData.minimumFloorLevel) {
                    SceneGraphRenderData.minimumFloorLevel = var4;
                  }

                  int var31 = SceneGraphRenderData.underlays[var4][var13][var25] & 255;
                  int var49 = SceneGraphRenderData.overlays[var4][var13][var25] & 255;
                  if (var31 > 0 || var49 > 0) {
                    int var33 = SceneGraphRenderData.tileHeights[var4][var13][var25];
                    int var34 = SceneGraphRenderData.tileHeights[var4][var13 + 1][var25];
                    int var38 = SceneGraphRenderData.tileHeights[var4][var13 + 1][var25 + 1];
                    int var35 = SceneGraphRenderData.tileHeights[var4][var13][var25 + 1];
                    int var36 = DefaultAudioSystemProvider.tileLighting[var13][var25];
                    int var37 = DefaultAudioSystemProvider.tileLighting[var13 + 1][var25];
                    int var50 = DefaultAudioSystemProvider.tileLighting[var13 + 1][var25 + 1];
                    int var40 = DefaultAudioSystemProvider.tileLighting[var13][var25 + 1];
                    int var41 = -1;
                    int var42 = -1;
                    if (var31 > 0) {
                      int var46 = var14 * 256 / var17;
                      int var43 = var15 / var18;
                      int var44 = var16 / var18;
                      var41 = StockmarketListingLifetimeComparator.method412(var46, var43, var44);
                      var46 = var46 + SceneGraphRenderData.tileHueOffset & 255;
                      var44 += SceneGraphRenderData.tileLightnessOffset;
                      if (var44 < 0) {
                        var44 = 0;
                      } else if (var44 > 255) {
                        var44 = 255;
                      }

                      var42 = StockmarketListingLifetimeComparator.method412(var46, var43, var44);
                    }

                    TileOverlay overlay;
                    if (var4 > 0) {
                      boolean var75 = true;
                      if (var31 == 0 && DefaultAudioSystemProvider.overlayShapes[var4][var13][var25] != 0) {
                        var75 = false;
                      }

                      if (var49 > 0) {
                        int var44 = var49 - 1;
                        overlay = TileOverlay.cache.get(var44);
                        TileOverlay var52;
                        if (overlay == null) {
                          byte[] var62 = TileOverlay.table.unpack(4, var44);
                          overlay = new TileOverlay();
                          if (var62 != null) {
                            overlay.decode(new Buffer(var62));
                          }

                          overlay.method499();
                          TileOverlay.cache.put(overlay, var44);
                        }
                        var52 = overlay;

                        if (!var52.hideUnderlay) {
                          var75 = false;
                        }
                      }

                      if (var75 && var33 == var34 && var33 == var38 && var35 == var33) {
                        int[] var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var4][var13];
                        var10000[var25] |= 2340;
                      }
                    }

                    int var46 = 0;
                    if (var42 != -1) {
                      var46 = JagGraphics3D.COLOR_PALETTE[method848(var42, 96)];
                    }

                    if (var49 == 0) {
                      scene.method1480(var4, var13, var25, 0, 0, -1, var33, var34, var38, var35, method848(var41, var36), method848(var41, var37), method848(var41, var50), method848(var41, var40), 0, 0, 0, 0, var46, 0);
                    } else {
                      int var43 = DefaultAudioSystemProvider.overlayShapes[var4][var13][var25] + 1;
                      byte var74 = Statics35.overlayOrientations[var4][var13][var25];
                      int var53 = var49 - 1;
                      TileOverlay var54 = TileOverlay.cache.get(var53);
                      if (var54 == null) {
                        byte[] var61 = TileOverlay.table.unpack(4, var53);
                        var54 = new TileOverlay();
                        if (var61 != null) {
                          var54.decode(new Buffer(var61));
                        }

                        var54.method499();
                        TileOverlay.cache.put(var54, var53);
                      }
                      overlay = var54;

                      int var55 = overlay.material;
                      int var56;
                      int var57;
                      int var58;
                      int var59;
                      if (var55 >= 0) {
                        var56 = JagGraphics3D.materialProvider.rgb(var55);
                        var57 = -1;
                      } else if (overlay.rgb == 16711935) {
                        var57 = -2;
                        var55 = -1;
                        var56 = -2;
                      } else {
                        var57 = StockmarketListingLifetimeComparator.method412(overlay.hue, overlay.saturation, overlay.lightness);
                        var58 = overlay.hue + SceneGraphRenderData.tileHueOffset & 255;
                        var59 = overlay.lightness + SceneGraphRenderData.tileLightnessOffset;
                        if (var59 < 0) {
                          var59 = 0;
                        } else if (var59 > 255) {
                          var59 = 255;
                        }

                        var56 = StockmarketListingLifetimeComparator.method412(var58, overlay.saturation, var59);
                      }

                      var58 = 0;
                      if (var56 != -2) {
                        var58 = JagGraphics3D.COLOR_PALETTE[WorldMapScriptEvent.method187(var56, 96)];
                      }

                      if (overlay.secondaryRgb != -1) {
                        var59 = overlay.secondaryHue + SceneGraphRenderData.tileHueOffset & 255;
                        int var60 = overlay.secondaryLightness + SceneGraphRenderData.tileLightnessOffset;
                        if (var60 < 0) {
                          var60 = 0;
                        } else if (var60 > 255) {
                          var60 = 255;
                        }

                        var56 = StockmarketListingLifetimeComparator.method412(var59, overlay.secondarySaturation, var60);
                        var58 = JagGraphics3D.COLOR_PALETTE[WorldMapScriptEvent.method187(var56, 96)];
                      }

                      scene.method1480(var4, var13, var25, var43, var74, var55, var33, var34, var38, var35, method848(var41, var36), method848(var41, var37), method848(var41, var50), method848(var41, var40), WorldMapScriptEvent.method187(var57, var36), WorldMapScriptEvent.method187(var57, var37), WorldMapScriptEvent.method187(var57, var50), WorldMapScriptEvent.method187(var57, var40), var46, var58);
                    }
                  }
                }
              }
            }
          }

          for (int var13 = 1; var13 < 103; ++var13) {
            for (int var14 = 1; var14 < 103; ++var14) {
              scene.setTileMinFloorLevel(var4, var14, var13, method800(var4, var14, var13));
            }
          }

          SceneGraphRenderData.underlays[var4] = null;
          SceneGraphRenderData.overlays[var4] = null;
          DefaultAudioSystemProvider.overlayShapes[var4] = null;
          Statics35.overlayOrientations[var4] = null;
          SceneGraphRenderData.shadows[var4] = null;
        }

        scene.setLightAt(-50, -10, -50);

        for (int x = 0; x < 104; ++x) {
          for (int y = 0; y < 104; ++y) {
            if ((SceneGraphRenderData.sceneRenderRules[1][x][y] & 2) == 2) {
              scene.method1484(x, y);
            }
          }
        }

        int var4 = 1;
        int var5 = 2;
        int var10 = 4;

        for (int var65 = 0; var65 < 4; ++var65) {
          if (var65 > 0) {
            var4 <<= 3;
            var5 <<= 3;
            var10 <<= 3;
          }

          for (int z = 0; z <= var65; ++z) {
            for (int x = 0; x <= 104; ++x) {
              for (int y = 0; y <= 104; ++y) {
                short var68;
                if ((SceneGraphRenderData.anIntArrayArrayArray393[z][y][x] & var4) != 0) {
                  int var11 = x;
                  int var66 = x;
                  int var13 = z;

                  int var14;
                  int var15;

                  for (var14 = z; var11 > 0 && (SceneGraphRenderData.anIntArrayArrayArray393[z][y][var11 - 1] & var4) != 0; --var11) {

                  }

                  while (var66 < 104 && (SceneGraphRenderData.anIntArrayArrayArray393[z][y][var66 + 1] & var4) != 0) {
                    ++var66;
                  }

                  label901:
                  while (var13 > 0) {
                    for (var15 = var11; var15 <= var66; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[var13 - 1][y][var15] & var4) == 0) {
                        break label901;
                      }
                    }

                    --var13;
                  }

                  label890:
                  while (var14 < var65) {
                    for (var15 = var11; var15 <= var66; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[var14 + 1][y][var15] & var4) == 0) {
                        break label890;
                      }
                    }

                    ++var14;
                  }

                  var15 = (var66 - var11 + 1) * (var14 + 1 - var13);
                  if (var15 >= 8) {
                    var68 = 240;
                    int var17 = SceneGraphRenderData.tileHeights[var14][y][var11] - var68;
                    int var18 = SceneGraphRenderData.tileHeights[var13][y][var11];
                    occlude(var65, 1, y * 128, y * 128, var11 * 128, var66 * 128 + 128, var17, var18);

                    for (int var25 = var13; var25 <= var14; ++var25) {
                      for (int var26 = var11; var26 <= var66; ++var26) {
                        int[] var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var25][y];
                        var10000[var26] &= ~var4;
                      }
                    }
                  }
                }

                if ((SceneGraphRenderData.anIntArrayArrayArray393[z][y][x] & var5) != 0) {
                  int var11 = y;
                  int var66 = y;
                  int var13 = z;

                  int var14;
                  int var15;
                  for (var14 = z; var11 > 0 && (SceneGraphRenderData.anIntArrayArrayArray393[z][var11 - 1][x] & var5) != 0; --var11) {

                  }

                  while (var66 < 104 && (SceneGraphRenderData.anIntArrayArrayArray393[z][var66 + 1][x] & var5) != 0) {
                    ++var66;
                  }

                  label954:
                  while (var13 > 0) {
                    for (var15 = var11; var15 <= var66; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[var13 - 1][var15][x] & var5) == 0) {
                        break label954;
                      }
                    }

                    --var13;
                  }

                  label943:
                  while (var14 < var65) {
                    for (var15 = var11; var15 <= var66; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[var14 + 1][var15][x] & var5) == 0) {
                        break label943;
                      }
                    }

                    ++var14;
                  }

                  var15 = (var14 + 1 - var13) * (var66 - var11 + 1);
                  if (var15 >= 8) {
                    var68 = 240;
                    int var17 = SceneGraphRenderData.tileHeights[var14][var11][x] - var68;
                    int var18 = SceneGraphRenderData.tileHeights[var13][var11][x];
                    occlude(var65, 2, var11 * 128, var66 * 128 + 128, x * 128, x * 128, var17, var18);

                    for (int var25 = var13; var25 <= var14; ++var25) {
                      for (int var26 = var11; var26 <= var66; ++var26) {
                        int[] var10000 = SceneGraphRenderData.anIntArrayArrayArray393[var25][var26];
                        var10000[x] &= ~var5;
                      }
                    }
                  }
                }

                if ((SceneGraphRenderData.anIntArrayArrayArray393[z][y][x] & var10) != 0) {
                  int var11 = y;
                  int var66 = y;
                  int var13 = x;

                  int var14;
                  int var15;

                  for (var14 = x; var13 > 0 && (SceneGraphRenderData.anIntArrayArrayArray393[z][y][var13 - 1] & var10) != 0; --var13) {

                  }

                  while (var14 < 104 && (SceneGraphRenderData.anIntArrayArrayArray393[z][y][var14 + 1] & var10) != 0) {
                    ++var14;
                  }

                  label1007:
                  while (var11 > 0) {
                    for (var15 = var13; var15 <= var14; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[z][var11 - 1][var15] & var10) == 0) {
                        break label1007;
                      }
                    }

                    --var11;
                  }

                  label996:
                  while (var66 < 104) {
                    for (var15 = var13; var15 <= var14; ++var15) {
                      if ((SceneGraphRenderData.anIntArrayArrayArray393[z][var66 + 1][var15] & var10) == 0) {
                        break label996;
                      }
                    }

                    ++var66;
                  }

                  if ((var14 - var13 + 1) * (var66 - var11 + 1) >= 4) {
                    var15 = SceneGraphRenderData.tileHeights[z][var11][var13];
                    occlude(var65, 4, var11 * 128, var66 * 128 + 128, var13 * 128, var14 * 128 + 128, var15, var15);

                    for (int var16 = var11; var16 <= var66; ++var16) {
                      for (int var17 = var13; var17 <= var14; ++var17) {
                        int[] var10000 = SceneGraphRenderData.anIntArrayArrayArray393[z][var16];
                        var10000[var17] &= ~var10;
                      }
                    }
                  }
                }
              }
            }
          }
        }

        client.writeKeepAlivePacket(true);
        var4 = SceneGraphRenderData.minimumFloorLevel;
        if (var4 > floorLevel) {
          var4 = floorLevel;
        }

        if (var4 < floorLevel - 1) {
        }

        if (client.lowMemory) {
          client.sceneGraph.method1478(SceneGraphRenderData.minimumFloorLevel);
        } else {
          client.sceneGraph.method1478(0);
        }

        for (var5 = 0; var5 < 104; ++var5) {
          for (var10 = 0; var10 < 104; ++var10) {
            Pickable.refresh(var5, var10);
          }
        }

        AudioSystem.process();

        for (PendingSpawn spawn = client.pendingSpawns.head(); spawn != null; spawn = client.pendingSpawns.next()) {
          if (spawn.endCycle == -1) {
            spawn.startCycle = 0;
            StockmarketEvent.method388(spawn);
          } else {
            spawn.unlink();
          }
        }

        ObjectDefinition.rawmodels.clear();
        OutgoingPacket packet;
        if (client.instance.hasFrame()) {
          packet = OutgoingPacket.prepare(ClientProt.USING_AWT_FRAME, client.stream.encryptor);
          packet.buffer.p4(1057001181);
          client.stream.writeLater(packet);
        }

        if (!client.dynamicScene) {
          var5 = (ServerProt.chunkX - 6) / 8;
          var10 = (ServerProt.chunkX + 6) / 8;
          int var65 = (Node_Sub19.chunkY - 6) / 8;
          int var22 = (Node_Sub19.chunkY + 6) / 8;

          for (int var8 = var5 - 1; var8 <= var10 + 1; ++var8) {
            for (int var9 = var65 - 1; var9 <= var22 + 1; ++var9) {
              if (var8 < var5 || var8 > var10 || var9 < var65 || var9 > var22) {
                Archive.landscape.method912("m" + var8 + "_" + var9);
                Archive.landscape.method912("l" + var8 + "_" + var9);
              }
            }
          }
        }

        client.setGameState(30);
        AudioSystem.process();
        BefriendedPlayer.method555();
        packet = OutgoingPacket.prepare(ClientProt.SCENE_LOADED, client.stream.encryptor);
        client.stream.writeLater(packet);
        GameShell.updateClock();
      }
    }
  }

  public static void renderMinimap(InterfaceComponent var0, int var1, int var2, int var3) {
    AudioSystem.process();
    ComponentSprite var4 = var0.getSprite(false);
    if (var4 != null) {
      JagGraphics.setClip(var1, var2, var4.anInt380 + var1, var2 + var4.anInt568);
      if (client.mapState != 2 && client.mapState != 5) {
        int var5 = Camera.yOffset & 2047;
        int var6 = PlayerEntity.local.absoluteX / 32 + 48;
        int var7 = 464 - PlayerEntity.local.absoluteY / 32;
        minimapSprite.rotate(var1, var2, var4.anInt380, var4.anInt568, var6, var7, var5, 256, var4.anIntArray1108, var4.anIntArray749);

        int var8;
        int var9;
        int var10;
        for (var8 = 0; var8 < client.anInt895; ++var8) {
          var9 = client.anIntArray892[var8] * 4 + 2 - PlayerEntity.local.absoluteX / 32;
          var10 = client.anIntArray894[var8] * 4 + 2 - PlayerEntity.local.absoluteY / 32;
          Statics17.method891(var1, var2, var9, var10, client.minimapFunctions[var8], var4);
        }

        int var12;
        int var13;
        for (var8 = 0; var8 < 104; ++var8) {
          for (var9 = 0; var9 < 104; ++var9) {
            NodeDeque<Pickable> var11 = client.pickables[floorLevel][var8][var9];
            if (var11 != null) {
              var12 = var8 * 4 + 2 - PlayerEntity.local.absoluteX / 32;
              var13 = var9 * 4 + 2 - PlayerEntity.local.absoluteY / 32;
              Statics17.method891(var1, var2, var12, var13, Statics47.mapmarkers[0], var4);
            }
          }
        }

        for (var8 = 0; var8 < client.npcCount; ++var8) {
          NpcEntity var14 = client.npcs[client.npcIndices[var8]];
          if (var14 != null && var14.isDefined()) {
            NpcDefinition var18 = var14.definition;
            if (var18 != null && var18.transformIds != null) {
              var18 = var18.transform();
            }

            if (var18 != null && var18.renderedOnMinimap && var18.interactable) {
              var12 = var14.absoluteX / 32 - PlayerEntity.local.absoluteX / 32;
              var13 = var14.absoluteY / 32 - PlayerEntity.local.absoluteY / 32;
              Statics17.method891(var1, var2, var12, var13, Statics47.mapmarkers[1], var4);
            }
          }
        }

        var8 = GPI.playerCount;
        int[] var21 = GPI.playerIndices;

        for (var10 = 0; var10 < var8; ++var10) {
          PlayerEntity var15 = client.players[var21[var10]];
          if (var15 != null && var15.isDefined() && !var15.hidden && var15 != PlayerEntity.local) {
            var13 = var15.absoluteX / 32 - PlayerEntity.local.absoluteX / 32;
            int var16 = var15.absoluteY / 32 - PlayerEntity.local.absoluteY / 32;
            boolean var17 = false;
            if (var15.team != 0 && var15.team == PlayerEntity.local.team) {
              var17 = true;
            }

            if (var15.method609()) {
              Statics17.method891(var1, var2, var13, var16, Statics47.mapmarkers[3], var4);
            } else if (var17) {
              Statics17.method891(var1, var2, var13, var16, Statics47.mapmarkers[4], var4);
            } else if (var15.method258()) {
              Statics17.method891(var1, var2, var13, var16, Statics47.mapmarkers[5], var4);
            } else {
              Statics17.method891(var1, var2, var13, var16, Statics47.mapmarkers[2], var4);
            }
          }
        }

        if (HintArrow.type != 0 && client.ticks % 20 < 10) {
          if (HintArrow.type == 1 && HintArrow.npc >= 0 && HintArrow.npc < client.npcs.length) {
            NpcEntity var19 = client.npcs[HintArrow.npc];
            if (var19 != null) {
              var12 = var19.absoluteX / 32 - PlayerEntity.local.absoluteX / 32;
              var13 = var19.absoluteY / 32 - PlayerEntity.local.absoluteY / 32;
              method1(var1, var2, var12, var13, Sprite.mapfunctions[1], var4);
            }
          }

          if (HintArrow.type == 2) {
            var10 = HintArrow.x * 4 - client.baseX * 256 + 2 - PlayerEntity.local.absoluteX / 32;
            var12 = HintArrow.y * 4 - client.baseY * 256 + 2 - PlayerEntity.local.absoluteY / 32;
            method1(var1, var2, var10, var12, Sprite.mapfunctions[1], var4);
          }

          if (HintArrow.type == 10 && HintArrow.player >= 0 && HintArrow.player < client.players.length) {
            PlayerEntity var20 = client.players[HintArrow.player];
            if (var20 != null) {
              var12 = var20.absoluteX / 32 - PlayerEntity.local.absoluteX / 32;
              var13 = var20.absoluteY / 32 - PlayerEntity.local.absoluteY / 32;
              method1(var1, var2, var12, var13, Sprite.mapfunctions[1], var4);
            }
          }
        }

        if (client.destinationX != 0) {
          var10 = client.destinationX * 4 + 2 - PlayerEntity.local.absoluteX / 32;
          var12 = client.destinationY * 4 + 2 - PlayerEntity.local.absoluteY / 32;
          Statics17.method891(var1, var2, var10, var12, Sprite.mapfunctions[0], var4);
        }

        if (!PlayerEntity.local.hidden) {
          JagGraphics.fillRect(var4.anInt380 / 2 + var1 - 1, var4.anInt568 / 2 + var2 - 1, 3, 3, 16777215);
        }
      } else {
        JagGraphics.method1362(var1, var2, 0, var4.anIntArray1108, var4.anIntArray749);
      }

      client.aBooleanArray1087[var3] = true;
    }
  }

  public static void initializeTileRenderData() {
    SceneGraphRenderData.minimumFloorLevel = 99;
    SceneGraphRenderData.underlays = new byte[4][104][104];
    SceneGraphRenderData.overlays = new byte[4][104][104];
    DefaultAudioSystemProvider.overlayShapes = new byte[4][104][104];
    Statics35.overlayOrientations = new byte[4][104][104];
    SceneGraphRenderData.anIntArrayArrayArray393 = new int[4][105][105];
    SceneGraphRenderData.shadows = new byte[4][105][105];
    DefaultAudioSystemProvider.tileLighting = new int[105][105];
    SceneGraphRenderData.blendHue = new int[104];
    LoadedArchive.tileBlendSaturation = new int[104];
    SceneGraphRenderData.blendLightness = new int[104];
    NamedFont.tileBlendHueWeight = new int[104];
    SceneGraphRenderData.blendColorCount = new int[104];
  }

  public static void method416(Buffer var0, int var1, int var2, int var3, int var4, int var5, int var6) {
    int var7;
    if (var2 >= 0 && var2 < 104 && var3 >= 0 && var3 < 104) {
      SceneGraphRenderData.sceneRenderRules[var1][var2][var3] = 0;

      while (true) {
        var7 = var0.g1();
        if (var7 == 0) {
          if (var1 == 0) {
            SceneGraphRenderData.tileHeights[0][var2][var3] = -WorldMapDecor.method378(var2 + 932731 + var4, var5 + var3 + 556238) * 8;
          } else {
            SceneGraphRenderData.tileHeights[var1][var2][var3] = SceneGraphRenderData.tileHeights[var1 - 1][var2][var3] - 240;
          }
          break;
        }

        if (var7 == 1) {
          int var8 = var0.g1();
          if (var8 == 1) {
            var8 = 0;
          }

          if (var1 == 0) {
            SceneGraphRenderData.tileHeights[0][var2][var3] = -var8 * 8;
          } else {
            SceneGraphRenderData.tileHeights[var1][var2][var3] = SceneGraphRenderData.tileHeights[var1 - 1][var2][var3] - var8 * 8;
          }
          break;
        }

        if (var7 <= 49) {
          SceneGraphRenderData.overlays[var1][var2][var3] = var0.g1b();
          DefaultAudioSystemProvider.overlayShapes[var1][var2][var3] = (byte) ((var7 - 2) / 4);
          Statics35.overlayOrientations[var1][var2][var3] = (byte) (var7 - 2 + var6 & 3);
        } else if (var7 <= 81) {
          SceneGraphRenderData.sceneRenderRules[var1][var2][var3] = (byte) (var7 - 49);
        } else {
          SceneGraphRenderData.underlays[var1][var2][var3] = (byte) (var7 - 81);
        }
      }
    } else {
      while (true) {
        var7 = var0.g1();
        if (var7 == 0) {
          break;
        }

        if (var7 == 1) {
          var0.g1();
          break;
        }

        if (var7 <= 49) {
          var0.g1();
        }
      }
    }

  }

  public static void method1187(int var0, int var1, boolean var2) {
    if (!var2 || var0 != ServerProt.chunkX || Node_Sub19.chunkY != var1) {
      ServerProt.chunkX = var0;
      Node_Sub19.chunkY = var1;
      client.setGameState(25);
      client.drawLoadingMessage("Loading - please wait.", true);
      int var3 = client.baseX;
      int var4 = client.baseY;
      client.baseX = (var0 - 6) * 8;
      client.baseY = (var1 - 6) * 8;
      int var5 = client.baseX - var3;
      int var6 = client.baseY - var4;

      int var7;
      int var9;
      int[] var10000;
      for (var7 = 0; var7 < 32768; ++var7) {
        NpcEntity var8 = client.npcs[var7];
        if (var8 != null) {
          for (var9 = 0; var9 < 10; ++var9) {
            var10000 = var8.pathXQueue;
            var10000[var9] -= var5;
            var10000 = var8.pathYQueue;
            var10000[var9] -= var6;
          }

          var8.absoluteX -= var5 * 128;
          var8.absoluteY -= var6 * 128;
        }
      }

      for (var7 = 0; var7 < 2048; ++var7) {
        PlayerEntity var21 = client.players[var7];
        if (var21 != null) {
          for (var9 = 0; var9 < 10; ++var9) {
            var10000 = var21.pathXQueue;
            var10000[var9] -= var5;
            var10000 = var21.pathYQueue;
            var10000[var9] -= var6;
          }

          var21.absoluteX -= var5 * 128;
          var21.absoluteY -= var6 * 128;
        }
      }

      byte var20 = 0;
      byte var10 = 104;
      byte var22 = 1;
      if (var5 < 0) {
        var20 = 103;
        var10 = -1;
        var22 = -1;
      }

      byte var11 = 0;
      byte var12 = 104;
      byte var13 = 1;
      if (var6 < 0) {
        var11 = 103;
        var12 = -1;
        var13 = -1;
      }

      for (int var14 = var20; var14 != var10; var14 += var22) {
        for (int var15 = var11; var15 != var12; var15 += var13) {
          int var16 = var5 + var14;
          int var17 = var6 + var15;

          for (int var18 = 0; var18 < 4; ++var18) {
            if (var16 >= 0 && var17 >= 0 && var16 < 104 && var17 < 104) {
              client.pickables[var18][var14][var15] = client.pickables[var18][var16][var17];
            } else {
              client.pickables[var18][var14][var15] = null;
            }
          }
        }
      }

      for (PendingSpawn spawn = client.pendingSpawns.head(); spawn != null; spawn = client.pendingSpawns.next()) {
        spawn.sceneX -= var5;
        spawn.sceneY -= var6;
        if (spawn.sceneX < 0 || spawn.sceneY < 0 || spawn.sceneX >= 104 || spawn.sceneY >= 104) {
          spawn.unlink();
        }
      }

      if (client.destinationX != 0) {
        client.destinationX -= var5;
        client.destinationY -= var6;
      }

      client.audioEffectCount = 0;
      client.cameraLocked = false;
      Camera.x -= var5 << 7;
      Camera.y -= var6 << 7;
      Camera.oculusOrbAbsoluteX -= var5 << 7;
      Camera.oculusOrbAbsoluteY -= var6 << 7;
      client.minimapFloorLevel = -1;
      client.effectObjects.clear();
      client.projectiles.clear();

      for (int level = 0; level < 4; ++level) {
        client.collisionMaps[level].initialize();
      }

    }
  }

  public static void method404(int var0, int var1, int x, int y, int var4, int var5, int var6) {
    if (x >= 1 && y >= 1 && x <= 102 && y <= 102) {
      if (client.lowMemory && var0 != floorLevel) {
        return;
      }

      long var7 = 0L;
      boolean var9 = true;
      boolean var10 = false;
      boolean var11 = false;
      if (var1 == 0) {
        var7 = client.sceneGraph.getBoundaryUidAt(var0, x, y);
      }

      if (var1 == 1) {
        var7 = client.sceneGraph.getBoundaryDecorUidAt(var0, x, y);
      }

      if (var1 == 2) {
        var7 = client.sceneGraph.method1461(var0, x, y);
      }

      if (var1 == 3) {
        var7 = client.sceneGraph.method1457(var0, x, y);
      }

      int var12;
      if (0L != var7) {
        var12 = client.sceneGraph.getConfigAt(var0, x, y, var7);
        int var14 = EntityUID.getObjectId(var7);
        int var15 = var12 & 31;
        int var16 = var12 >> 6 & 3;
        ObjectDefinition var13;
        if (var1 == 0) {
          client.sceneGraph.method1482(var0, x, y);
          var13 = ObjectDefinition.get(var14);
          if (var13.anInt791 != 0) {
            client.collisionMaps[var0].method157(x, y, var15, var16, var13.impenetrable);
          }
        }

        if (var1 == 1) {
          client.sceneGraph.method1473(var0, x, y);
        }

        if (var1 == 2) {
          client.sceneGraph.method1475(var0, x, y);
          var13 = ObjectDefinition.get(var14);
          if (x + var13.sizeX > 103 || y + var13.sizeX > 103 || x + var13.sizeY > 103 || y + var13.sizeY > 103) {
            return;
          }

          if (var13.anInt791 != 0) {
            client.collisionMaps[var0].setFlagOffNonSquare(x, y, var13.sizeX, var13.sizeY, var16, var13.impenetrable);
          }
        }

        if (var1 == 3) {
          client.sceneGraph.method1471(var0, x, y);
          var13 = ObjectDefinition.get(var14);
          if (var13.anInt791 == 1) {
            client.collisionMaps[var0].method152(x, y);
          }
        }
      }

      if (var4 >= 0) {
        var12 = var0;
        if (var0 < 3 && (SceneGraphRenderData.sceneRenderRules[1][x][y] & 2) == 2) {
          var12 = var0 + 1;
        }

        SerializableProcessor.loadObject(var0, var12, x, y, var4, var5, var6, client.sceneGraph, client.collisionMaps[var0]);
      }
    }

  }

  public static void renderEntities(int var0, int var1, int width, int height) {
    ++client.viewportRenderCount;
    if (PlayerEntity.local.absoluteX >> 7 == client.destinationX && PlayerEntity.local.absoluteY >> 7 == client.destinationY) {
      client.destinationX = 0;
    }

    if (client.displaySelf) {
      GPI.loadPlayerIntoScene(PlayerEntity.local, false);
    }

    GPI.loadPlayerIntoScene();
    SerializableString.loadNpcsIntoScene(true);
    Login.loadPlayersIntoScene();
    SerializableString.loadNpcsIntoScene(false);
    DynamicObject.loadProjectilesIntoScene();

    for (EffectObject object = client.effectObjects.head(); object != null; object = client.effectObjects.next()) {
      if (object.floorLevel == floorLevel && !object.finished) {
        if (client.ticks >= object.endCycle) {
          object.update(client.anInt972);
          if (object.finished) {
            object.unlink();
          } else {
            client.sceneGraph.addEntityMarker(object.floorLevel, object.absoluteX, object.absoluteY, object.height, 60, object, 0, -1L, false);
          }
        }
      } else {
        object.unlink();
      }
    }

    ScriptEvent.method867(var0, var1, width, height, true);
    var0 = client.anInt909;
    var1 = client.anInt908;
    width = client.viewportWidth;
    height = client.viewportHeight;
    JagGraphics.setClip(var0, var1, var0 + width, height + var1);
    JagGraphics3D.method499();
    int var5;
    int var6;
    int var7;
    int var8;
    int var9;
    int var12;
    if (!client.cameraLocked) {
      var5 = Camera.minimumPitch;
      if (client.anInt1008 / 256 > var5) {
        var5 = client.anInt1008 / 256;
      }

      if (client.aBooleanArray919[4] && client.anIntArray918[4] + 128 > var5) {
        var5 = client.anIntArray918[4] + 128;
      }

      var6 = Camera.yOffset & 2047;
      var7 = Camera.oculusOrbAbsoluteX;
      var8 = Camera.anInt802;
      var9 = Camera.oculusOrbAbsoluteY;
      var12 = var5 * 3 + 600;
      NpcDefinition.method505(var7, var8, var9, var5, var6, var12, height);

    }

    int var10;
    int var11;
    int var13;
    int var14;
    int var15;
    if (!client.cameraLocked) {
      if (client.preferences.roofsHidden) {
        var6 = floorLevel;
      } else {
        label409:
        {
          var7 = 3;
          if (Camera.pitch < 310) {
            if (Camera.oculusOrbMode == 1) {
              var8 = Camera.oculusOrbAbsoluteX >> 7;
              var9 = Camera.oculusOrbAbsoluteY >> 7;
            } else {
              var8 = PlayerEntity.local.absoluteX >> 7;
              var9 = PlayerEntity.local.absoluteY >> 7;
            }

            var10 = Camera.x >> 7;
            var11 = Camera.y >> 7;
            if (var10 < 0 || var11 < 0 || var10 >= 104 || var11 >= 104) {
              var6 = floorLevel;
              break label409;
            }

            if (var8 < 0 || var9 < 0 || var8 >= 104 || var9 >= 104) {
              var6 = floorLevel;
              break label409;
            }

            if ((SceneGraphRenderData.sceneRenderRules[floorLevel][var10][var11] & 4) != 0) {
              var7 = floorLevel;
            }

            if (var8 > var10) {
              var12 = var8 - var10;
            } else {
              var12 = var10 - var8;
            }

            if (var9 > var11) {
              var13 = var9 - var11;
            } else {
              var13 = var11 - var9;
            }

            if (var12 > var13) {
              var14 = var13 * 65536 / var12;
              var15 = 32768;

              while (var8 != var10) {
                if (var10 < var8) {
                  ++var10;
                } else if (var10 > var8) {
                  --var10;
                }

                if ((SceneGraphRenderData.sceneRenderRules[floorLevel][var10][var11] & 4) != 0) {
                  var7 = floorLevel;
                }

                var15 += var14;
                if (var15 >= 65536) {
                  var15 -= 65536;
                  if (var11 < var9) {
                    ++var11;
                  } else if (var11 > var9) {
                    --var11;
                  }

                  if ((SceneGraphRenderData.sceneRenderRules[floorLevel][var10][var11] & 4) != 0) {
                    var7 = floorLevel;
                  }
                }
              }
            } else if (var13 > 0) {
              var14 = var12 * 65536 / var13;
              var15 = 32768;

              while (var9 != var11) {
                if (var11 < var9) {
                  ++var11;
                } else if (var11 > var9) {
                  --var11;
                }

                if ((SceneGraphRenderData.sceneRenderRules[floorLevel][var10][var11] & 4) != 0) {
                  var7 = floorLevel;
                }

                var15 += var14;
                if (var15 >= 65536) {
                  var15 -= 65536;
                  if (var10 < var8) {
                    ++var10;
                  } else if (var10 > var8) {
                    --var10;
                  }

                  if ((SceneGraphRenderData.sceneRenderRules[floorLevel][var10][var11] & 4) != 0) {
                    var7 = floorLevel;
                  }
                }
              }
            }
          }

          if (PlayerEntity.local.absoluteX >= 0 && PlayerEntity.local.absoluteY >= 0 && PlayerEntity.local.absoluteX < 13312 && PlayerEntity.local.absoluteY < 13312) {
            if ((SceneGraphRenderData.sceneRenderRules[floorLevel][PlayerEntity.local.absoluteX >> 7][PlayerEntity.local.absoluteY >> 7] & 4) != 0) {
              var7 = floorLevel;
            }

            var6 = var7;
          } else {
            var6 = floorLevel;
          }
        }
      }

      var5 = var6;
    } else {
      var5 = Statics17.method892();
    }

    var6 = Camera.x;
    var7 = Camera.z;
    var8 = Camera.y;
    var9 = Camera.pitch;
    var10 = Camera.yaw;

    for (var11 = 0; var11 < 5; ++var11) {
      if (client.aBooleanArray919[var11]) {
        var12 = (int) (Math.random() * (double) (client.anIntArray917[var11] * 2 + 1) - (double) client.anIntArray917[var11] + Math.sin((double) client.anIntArray914[var11] / 100.0D * (double) client.anIntArray916[var11]) * (double) client.anIntArray918[var11]);
        if (var11 == 0) {
          Camera.x += var12;
        }

        if (var11 == 1) {
          Camera.z += var12;
        }

        if (var11 == 2) {
          Camera.y += var12;
        }

        if (var11 == 3) {
          Camera.yaw = var12 + Camera.yaw & 2047;
        }

        if (var11 == 4) {
          Camera.pitch += var12;
          if (Camera.pitch < 128) {
            Camera.pitch = 128;
          }

          if (Camera.pitch > 383) {
            Camera.pitch = 383;
          }
        }
      }
    }

    var11 = Mouse.x;
    var12 = Mouse.y;
    if (Mouse.clickMeta != 0) {
      var11 = Mouse.clickX;
      var12 = Mouse.clickY;
    }

    if (var11 >= var0 && var11 < var0 + width && var12 >= var1 && var12 < height + var1) {
      CursorEntities.method238(var11 - var0, var12 - var1);
    } else {
      CursorEntities.method419();
    }

    AudioSystem.process();
    JagGraphics.fillRect(var0, var1, width, height, 0);
    AudioSystem.process();
    var13 = JagGraphics3D.scale;
    JagGraphics3D.scale = client.viewportScale;
    client.sceneGraph.renderScene(Camera.x, Camera.z, Camera.y, Camera.pitch, Camera.yaw, var5);
    JagGraphics3D.scale = var13;
    AudioSystem.process();
    client.sceneGraph.method1472();
    PathingEntity.renderOverhead(var0, var1, width, height);
    HintArrow.draw(var0, var1);
    ((DefaultMaterialProvider) JagGraphics3D.materialProvider).rotate(client.anInt972);
    client.anInt1014 = 0;
    var14 = client.baseX + (PlayerEntity.local.absoluteX >> 7);
    var15 = client.baseY + (PlayerEntity.local.absoluteY >> 7);
    if (var14 >= 3053 && var14 <= 3156 && var15 >= 3056 && var15 <= 3136) {
      client.anInt1014 = 1;
    }

    if (var14 >= 3072 && var14 <= 3118 && var15 >= 9492 && var15 <= 9535) {
      client.anInt1014 = 1;
    }

    if (client.anInt1014 == 1 && var14 >= 3139 && var14 <= 3199 && var15 >= 3008 && var15 <= 3062) {
      client.anInt1014 = 0;
    }

    Camera.x = var6;
    Camera.z = var7;
    Camera.y = var8;
    Camera.pitch = var9;
    Camera.yaw = var10;
    if (client.loadingPleaseWait && SerializableInteger.method405() == 0) {
      client.loadingPleaseWait = false;
    }

    if (client.loadingPleaseWait) {
      JagGraphics.fillRect(var0, var1, width, height, 0);
      client.drawLoadingMessage("Loading - please wait.", false);
    }

  }

  public boolean method1464(int var1, int var2, int var3) {
    int var4 = renderCycles[var1][var2][var3];
    if (var4 == -renderTick) {
      return false;
    }
    if (var4 == renderTick) {
      return true;
    }
    int var5 = var2 << 7;
    int var6 = var3 << 7;
    if (this.method1465(var5 + 1, this.heights[var1][var2][var3], var6 + 1) && this.method1465(var5 + 128 - 1, this.heights[var1][var2 + 1][var3], var6 + 1) && this.method1465(var5 + 128 - 1, this.heights[var1][var2 + 1][var3 + 1], var6 + 128 - 1) && this.method1465(var5 + 1, this.heights[var1][var2][var3 + 1], var6 + 128 - 1)) {
      this.renderCycles[var1][var2][var3] = renderTick;
      return true;
    }
    this.renderCycles[var1][var2][var3] = -renderTick;
    return false;
  }

  public boolean addEntityMarker(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Entity var9, int var10, boolean var11, long var12, int var14) {
    int var16;
    for (int var15 = var2; var15 < var2 + var4; ++var15) {
      for (var16 = var3; var16 < var3 + var5; ++var16) {
        if (var15 < 0 || var16 < 0 || var15 >= this.width || var16 >= this.length) {
          return false;
        }

        Tile var21 = this.tiles[var1][var15][var16];
        if (var21 != null && var21.entityMarkerCount >= 5) {
          return false;
        }
      }
    }

    EntityMarker var17 = new EntityMarker();
    var17.uid = var12;
    var17.config = var14;
    var17.floorLevel = var1;
    var17.centerAbsoluteX = var6;
    var17.centerAbsoluteY = var7;
    var17.height = var8;
    var17.entity = var9;
    var17.orientation = var10;
    var17.sceneX = var2;
    var17.sceneY = var3;
    var17.maxSceneX = var2 + var4 - 1;
    var17.maxSceneY = var3 + var5 - 1;

    for (var16 = var2; var16 < var2 + var4; ++var16) {
      for (int var18 = var3; var18 < var3 + var5; ++var18) {
        int var19 = 0;
        if (var16 > var2) {
          ++var19;
        }

        if (var16 < var2 + var4 - 1) {
          var19 += 4;
        }

        if (var18 > var3) {
          var19 += 8;
        }

        if (var18 < var3 + var5 - 1) {
          var19 += 2;
        }

        for (int var20 = var1; var20 >= 0; --var20) {
          if (this.tiles[var20][var16][var18] == null) {
            this.tiles[var20][var16][var18] = new Tile(var20, var16, var18);
          }
        }

        Tile var22 = this.tiles[var1][var16][var18];
        var22.markers[var22.entityMarkerCount] = var17;
        var22.entityMarkerSideMasks[var22.entityMarkerCount] = var19;
        var22.entityMarkerSideFlags |= var19;
        ++var22.entityMarkerCount;
      }
    }

    if (var11) {
      this.tempMarkers[this.tempMarkerCount++] = var17;
    }

    return true;
  }

  public void method1485(EntityMarker var1) {
    for (int var2 = var1.sceneX; var2 <= var1.maxSceneX; ++var2) {
      for (int var3 = var1.sceneY; var3 <= var1.maxSceneY; ++var3) {
        Tile var4 = this.tiles[var1.floorLevel][var2][var3];
        if (var4 != null) {
          int var5;
          for (var5 = 0; var5 < var4.entityMarkerCount; ++var5) {
            if (var4.markers[var5] == var1) {
              --var4.entityMarkerCount;

              for (int var6 = var5; var6 < var4.entityMarkerCount; ++var6) {
                var4.markers[var6] = var4.markers[var6 + 1];
                var4.entityMarkerSideMasks[var6] = var4.entityMarkerSideMasks[var6 + 1];
              }

              var4.markers[var4.entityMarkerCount] = null;
              break;
            }
          }

          var4.entityMarkerSideFlags = 0;

          for (var5 = 0; var5 < var4.entityMarkerCount; ++var5) {
            var4.entityMarkerSideFlags |= var4.entityMarkerSideMasks[var5];
          }
        }
      }
    }

  }

  public void method1451(int var1, int var2, int var3, boolean var4) {
    if (!isMovementPending() || var4) {
      acceptClick = true;
      viewportWalking = var4;
      anInt1931 = var1;
      walkingMouseX = var2;
      walkingMouseY = var3;
      pendingDestinationX = -1;
      pendingDestinationY = -1;
    }
  }

  public boolean method1465(int var1, int var2, int var3) {
    for (int var4 = 0; var4 < anInt1933; ++var4) {
      SceneOccluder var5 = occluders[var4];
      int var6;
      int var7;
      int var8;
      int var9;
      int var10;
      if (var5.anInt1912 == 1) {
        var6 = var5.minAbsoluteX - var1;
        if (var6 > 0) {
          var7 = (var6 * var5.anInt1914 >> 8) + var5.minAbsoluteY;
          var8 = (var6 * var5.anInt1921 >> 8) + var5.maxAbsoluteY;
          var9 = (var6 * var5.anInt1918 >> 8) + var5.componentHeight;
          var10 = (var6 * var5.anInt1915 >> 8) + var5.tileHeight;
          if (var3 >= var7 && var3 <= var8 && var2 >= var9 && var2 <= var10) {
            return true;
          }
        }
      } else if (var5.anInt1912 == 2) {
        var6 = var1 - var5.minAbsoluteX;
        if (var6 > 0) {
          var7 = (var6 * var5.anInt1914 >> 8) + var5.minAbsoluteY;
          var8 = (var6 * var5.anInt1921 >> 8) + var5.maxAbsoluteY;
          var9 = (var6 * var5.anInt1918 >> 8) + var5.componentHeight;
          var10 = (var6 * var5.anInt1915 >> 8) + var5.tileHeight;
          if (var3 >= var7 && var3 <= var8 && var2 >= var9 && var2 <= var10) {
            return true;
          }
        }
      } else if (var5.anInt1912 == 3) {
        var6 = var5.minAbsoluteY - var3;
        if (var6 > 0) {
          var7 = (var6 * var5.anInt1917 >> 8) + var5.minAbsoluteX;
          var8 = (var6 * var5.anInt1911 >> 8) + var5.maxAbsoluteX;
          var9 = (var6 * var5.anInt1918 >> 8) + var5.componentHeight;
          var10 = (var6 * var5.anInt1915 >> 8) + var5.tileHeight;
          if (var1 >= var7 && var1 <= var8 && var2 >= var9 && var2 <= var10) {
            return true;
          }
        }
      } else if (var5.anInt1912 == 4) {
        var6 = var3 - var5.minAbsoluteY;
        if (var6 > 0) {
          var7 = (var6 * var5.anInt1917 >> 8) + var5.minAbsoluteX;
          var8 = (var6 * var5.anInt1911 >> 8) + var5.maxAbsoluteX;
          var9 = (var6 * var5.anInt1918 >> 8) + var5.componentHeight;
          var10 = (var6 * var5.anInt1915 >> 8) + var5.tileHeight;
          if (var1 >= var7 && var1 <= var8 && var2 >= var9 && var2 <= var10) {
            return true;
          }
        }
      } else if (var5.anInt1912 == 5) {
        var6 = var2 - var5.componentHeight;
        if (var6 > 0) {
          var7 = (var6 * var5.anInt1917 >> 8) + var5.minAbsoluteX;
          var8 = (var6 * var5.anInt1911 >> 8) + var5.maxAbsoluteX;
          var9 = (var6 * var5.anInt1914 >> 8) + var5.minAbsoluteY;
          var10 = (var6 * var5.anInt1921 >> 8) + var5.maxAbsoluteY;
          if (var1 >= var7 && var1 <= var8 && var3 >= var9 && var3 <= var10) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public void method1452(UnlitModel var1, int var2, int var3, int var4, int var5, int var6) {
    boolean var7 = true;
    int var8 = var3;
    int var9 = var3 + var5;
    int var10 = var4 - 1;
    int var11 = var4 + var6;

    for (int var12 = var2; var12 <= var2 + 1; ++var12) {
      if (var12 != this.floorCount) {
        for (int var13 = var8; var13 <= var9; ++var13) {
          if (var13 >= 0 && var13 < this.width) {
            for (int var14 = var10; var14 <= var11; ++var14) {
              if (var14 >= 0 && var14 < this.length && (!var7 || var13 >= var9 || var14 >= var11 || var14 < var4 && var3 != var13)) {
                Tile var15 = this.tiles[var12][var13][var14];
                if (var15 != null) {
                  int var16 = (this.heights[var12][var13 + 1][var14] + this.heights[var12][var13 + 1][var14 + 1] + this.heights[var12][var13][var14] + this.heights[var12][var13][var14 + 1]) / 4 - (this.heights[var2][var3 + 1][var4] + this.heights[var2][var3][var4] + this.heights[var2][var3 + 1][var4 + 1] + this.heights[var2][var3][var4 + 1]) / 4;
                  Boundary var17 = var15.boundary;
                  if (var17 != null) {
                    UnlitModel var18;
                    if (var17.entity instanceof UnlitModel) {
                      var18 = (UnlitModel) var17.entity;
                      UnlitModel.method970(var1, var18, (1 - var5) * 64 + (var13 - var3) * 128, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                    }

                    if (var17.linkedEntity instanceof UnlitModel) {
                      var18 = (UnlitModel) var17.linkedEntity;
                      UnlitModel.method970(var1, var18, (1 - var5) * 64 + (var13 - var3) * 128, var16, (var14 - var4) * 128 + (1 - var6) * 64, var7);
                    }
                  }

                  for (int var19 = 0; var19 < var15.entityMarkerCount; ++var19) {
                    EntityMarker var20 = var15.markers[var19];
                    if (var20 != null && var20.entity instanceof UnlitModel) {
                      UnlitModel var21 = (UnlitModel) var20.entity;
                      int var22 = var20.maxSceneX - var20.sceneX + 1;
                      int var23 = var20.maxSceneY - var20.sceneY + 1;
                      UnlitModel.method970(var1, var21, (var22 - var5) * 64 + (var20.sceneX - var3) * 128, var16, (var20.sceneY - var4) * 128 + (var23 - var6) * 64, var7);
                    }
                  }
                }
              }
            }
          }
        }

        --var8;
        var7 = false;
      }
    }

  }

  public boolean method1463(int var1, int var2, int var3, int var4) {
    if (!this.method1464(var1, var2, var3)) {
      return false;
    }
    int var5 = var2 << 7;
    int var6 = var3 << 7;
    int var7 = this.heights[var1][var2][var3] - 1;
    int var8 = var7 - 120;
    int var9 = var7 - 230;
    int var10 = var7 - 238;
    if (var4 < 16) {
      if (var4 == 1) {
        if (var5 > renderCameraX) {
          if (!this.method1465(var5, var7, var6)) {
            return false;
          }

          if (!this.method1465(var5, var7, var6 + 128)) {
            return false;
          }
        }

        if (var1 > 0) {
          if (!this.method1465(var5, var8, var6)) {
            return false;
          }

          if (!this.method1465(var5, var8, var6 + 128)) {
            return false;
          }
        }

        if (!this.method1465(var5, var9, var6)) {
          return false;
        }

        return this.method1465(var5, var9, var6 + 128);
      }

      if (var4 == 2) {
        if (var6 < renderCameraY) {
          if (!this.method1465(var5, var7, var6 + 128)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var7, var6 + 128)) {
            return false;
          }
        }

        if (var1 > 0) {
          if (!this.method1465(var5, var8, var6 + 128)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var8, var6 + 128)) {
            return false;
          }
        }

        if (!this.method1465(var5, var9, var6 + 128)) {
          return false;
        }

        return this.method1465(var5 + 128, var9, var6 + 128);
      }

      if (var4 == 4) {
        if (var5 < renderCameraX) {
          if (!this.method1465(var5 + 128, var7, var6)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var7, var6 + 128)) {
            return false;
          }
        }

        if (var1 > 0) {
          if (!this.method1465(var5 + 128, var8, var6)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var8, var6 + 128)) {
            return false;
          }
        }

        if (!this.method1465(var5 + 128, var9, var6)) {
          return false;
        }

        return this.method1465(var5 + 128, var9, var6 + 128);
      }

      if (var4 == 8) {
        if (var6 > renderCameraY) {
          if (!this.method1465(var5, var7, var6)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var7, var6)) {
            return false;
          }
        }

        if (var1 > 0) {
          if (!this.method1465(var5, var8, var6)) {
            return false;
          }

          if (!this.method1465(var5 + 128, var8, var6)) {
            return false;
          }
        }

        if (!this.method1465(var5, var9, var6)) {
          return false;
        }

        return this.method1465(var5 + 128, var9, var6);
      }
    }

    if (!this.method1465(var5 + 64, var10, var6 + 64)) {
      return false;
    }
    if (var4 == 16) {
      return this.method1465(var5, var9, var6 + 128);
    }
    if (var4 == 32) {
      return this.method1465(var5 + 128, var9, var6 + 128);
    }
    if (var4 == 64) {
      return this.method1465(var5 + 128, var9, var6);
    }
    if (var4 == 128) {
      return this.method1465(var5, var9, var6);
    }
    return true;
  }

  public long getBoundaryUidAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 != null && var4.boundary != null ? var4.boundary.uid : 0L;
  }

  public long getBoundaryDecorUidAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 != null && var4.boundaryDecor != null ? var4.boundaryDecor.uid : 0L;
  }

  public int getConfigAt(int var1, int var2, int var3, long var4) {
    Tile var6 = this.tiles[var1][var2][var3];
    if (var6 == null) {
      return -1;
    }
    if (var6.boundary != null && var6.boundary.uid == var4) {
      return var6.boundary.config & 255;
    }
    if (var6.boundaryDecor != null && var6.boundaryDecor.uid == var4) {
      return var6.boundaryDecor.config & 255;
    }
    if (var6.decor != null && var6.decor.uid == var4) {
      return var6.decor.config & 255;
    }
    for (int var7 = 0; var7 < var6.entityMarkerCount; ++var7) {
      if (var6.markers[var7].uid == var4) {
        return var6.markers[var7].config & 255;
      }
    }

    return -1;
  }

  void renderTilePaint(TilePaint var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
    int var9;
    int var10 = var9 = (var7 << 7) - renderCameraX;
    int var11;
    int var12 = var11 = (var8 << 7) - renderCameraY;
    int var13;
    int var14 = var13 = var10 + 128;
    int var15;
    int var16 = var15 = var12 + 128;
    int var17 = this.heights[var2][var7][var8] - renderCameraZ;
    int var18 = this.heights[var2][var7 + 1][var8] - renderCameraZ;
    int var19 = this.heights[var2][var7 + 1][var8 + 1] - renderCameraZ;
    int var20 = this.heights[var2][var7][var8 + 1] - renderCameraZ;
    int var21 = var10 * var6 + var5 * var12 >> 16;
    var12 = var12 * var6 - var5 * var10 >> 16;
    var10 = var21;
    var21 = var17 * var4 - var3 * var12 >> 16;
    var12 = var3 * var17 + var12 * var4 >> 16;
    var17 = var21;
    if (var12 >= 50) {
      var21 = var14 * var6 + var5 * var11 >> 16;
      var11 = var11 * var6 - var5 * var14 >> 16;
      var14 = var21;
      var21 = var18 * var4 - var3 * var11 >> 16;
      var11 = var3 * var18 + var11 * var4 >> 16;
      var18 = var21;
      if (var11 >= 50) {
        var21 = var13 * var6 + var5 * var16 >> 16;
        var16 = var16 * var6 - var5 * var13 >> 16;
        var13 = var21;
        var21 = var19 * var4 - var3 * var16 >> 16;
        var16 = var3 * var19 + var16 * var4 >> 16;
        var19 = var21;
        if (var16 >= 50) {
          var21 = var9 * var6 + var5 * var15 >> 16;
          var15 = var15 * var6 - var5 * var9 >> 16;
          var9 = var21;
          var21 = var20 * var4 - var3 * var15 >> 16;
          var15 = var3 * var20 + var15 * var4 >> 16;
          if (var15 >= 50) {
            int var22 = var10 * JagGraphics3D.scale / var12 + JagGraphics3D.anInt386;
            int var23 = var17 * JagGraphics3D.scale / var12 + JagGraphics3D.anInt366;
            int var24 = var14 * JagGraphics3D.scale / var11 + JagGraphics3D.anInt386;
            int var25 = var18 * JagGraphics3D.scale / var11 + JagGraphics3D.anInt366;
            int var26 = var13 * JagGraphics3D.scale / var16 + JagGraphics3D.anInt386;
            int var27 = var19 * JagGraphics3D.scale / var16 + JagGraphics3D.anInt366;
            int var28 = var9 * JagGraphics3D.scale / var15 + JagGraphics3D.anInt386;
            int var29 = var21 * JagGraphics3D.scale / var15 + JagGraphics3D.anInt366;
            JagGraphics3D.alpha = 0;
            int var30;
            if ((var26 - var28) * (var25 - var29) - (var27 - var29) * (var24 - var28) > 0) {
              JagGraphics3D.drawingOffscreen = var26 < 0 || var28 < 0 || var24 < 0 || var26 > JagGraphics3D.anInt696 || var28 > JagGraphics3D.anInt696 || var24 > JagGraphics3D.anInt696;

              if (acceptClick && contains(walkingMouseX, walkingMouseY, var27, var29, var25, var26, var28, var24)) {
                pendingDestinationX = var7;
                pendingDestinationY = var8;
              }

              if (var1.texture == -1) {
                if (var1.anInt2001 != 12345678) {
                  JagGraphics3D.method627(var27, var29, var25, var26, var28, var24, var1.anInt2001, var1.anInt2000, var1.anInt2003);
                }
              } else if (!lowMemory) {
                if (var1.flatShade) {
                  JagGraphics3D.method638(var27, var29, var25, var26, var28, var24, var1.anInt2001, var1.anInt2000, var1.anInt2003, var10, var14, var9, var17, var18, var21, var12, var11, var15, var1.texture);
                } else {
                  JagGraphics3D.method638(var27, var29, var25, var26, var28, var24, var1.anInt2001, var1.anInt2000, var1.anInt2003, var13, var9, var14, var19, var21, var18, var16, var15, var11, var1.texture);
                }
              } else {
                var30 = JagGraphics3D.materialProvider.rgb(var1.texture);
                JagGraphics3D.method627(var27, var29, var25, var26, var28, var24, method1444(var30, var1.anInt2001), method1444(var30, var1.anInt2000), method1444(var30, var1.anInt2003));
              }
            }

            if ((var22 - var24) * (var29 - var25) - (var23 - var25) * (var28 - var24) > 0) {
              JagGraphics3D.drawingOffscreen = var22 < 0 || var24 < 0 || var28 < 0 || var22 > JagGraphics3D.anInt696 || var24 > JagGraphics3D.anInt696 || var28 > JagGraphics3D.anInt696;

              if (acceptClick && contains(walkingMouseX, walkingMouseY, var23, var25, var29, var22, var24, var28)) {
                pendingDestinationX = var7;
                pendingDestinationY = var8;
              }

              if (var1.texture == -1) {
                if (var1.anInt2002 != 12345678) {
                  JagGraphics3D.method627(var23, var25, var29, var22, var24, var28, var1.anInt2002, var1.anInt2003, var1.anInt2000);
                }
              } else if (!lowMemory) {
                JagGraphics3D.method638(var23, var25, var29, var22, var24, var28, var1.anInt2002, var1.anInt2003, var1.anInt2000, var10, var14, var9, var17, var18, var21, var12, var11, var15, var1.texture);
              } else {
                var30 = JagGraphics3D.materialProvider.rgb(var1.texture);
                JagGraphics3D.method627(var23, var25, var29, var22, var24, var28, method1444(var30, var1.anInt2002), method1444(var30, var1.anInt2003), method1444(var30, var1.anInt2000));
              }
            }

          }
        }
      }
    }
  }

  public void renderTileModel(TileModel var1, int var2, int var3, int var4, int var5, int var6, int var7) {
    int var8 = var1.xVertices.length;

    int var9;
    int var10;
    int var11;
    int var12;
    int var13;
    for (var9 = 0; var9 < var8; ++var9) {
      var10 = var1.xVertices[var9] - renderCameraX;
      var11 = var1.yVertices[var9] - renderCameraZ;
      var12 = var1.zVertices[var9] - renderCameraY;
      var13 = var12 * var4 + var5 * var10 >> 16;
      var12 = var5 * var12 - var10 * var4 >> 16;
      var10 = var13;
      var13 = var3 * var11 - var12 * var2 >> 16;
      var12 = var11 * var2 + var3 * var12 >> 16;
      if (var12 < 50) {
        return;
      }

      if (var1.triangleTexture != null) {
        TileModel.anIntArray1537[var9] = var10;
        TileModel.anIntArray1550[var9] = var13;
        TileModel.anIntArray1538[var9] = var12;
      }

      TileModel.anIntArray1549[var9] = var10 * JagGraphics3D.scale / var12 + JagGraphics3D.anInt386;
      TileModel.anIntArray1546[var9] = var13 * JagGraphics3D.scale / var12 + JagGraphics3D.anInt366;
    }

    JagGraphics3D.alpha = 0;
    var8 = var1.anIntArray1543.length;

    for (var9 = 0; var9 < var8; ++var9) {
      var10 = var1.anIntArray1543[var9];
      var11 = var1.anIntArray1540[var9];
      var12 = var1.anIntArray1551[var9];
      var13 = TileModel.anIntArray1549[var10];
      int var14 = TileModel.anIntArray1549[var11];
      int var15 = TileModel.anIntArray1549[var12];
      int var16 = TileModel.anIntArray1546[var10];
      int var17 = TileModel.anIntArray1546[var11];
      int var18 = TileModel.anIntArray1546[var12];
      if ((var13 - var14) * (var18 - var17) - (var16 - var17) * (var15 - var14) > 0) {
        JagGraphics3D.drawingOffscreen = var13 < 0 || var14 < 0 || var15 < 0 || var13 > JagGraphics3D.anInt696 || var14 > JagGraphics3D.anInt696 || var15 > JagGraphics3D.anInt696;

        if (acceptClick && contains(walkingMouseX, walkingMouseY, var16, var17, var18, var13, var14, var15)) {
          pendingDestinationX = var6;
          pendingDestinationY = var7;
        }

        if (var1.triangleTexture != null && var1.triangleTexture[var9] != -1) {
          if (!lowMemory) {
            if (var1.flatShade) {
              JagGraphics3D.method638(var16, var17, var18, var13, var14, var15, var1.anIntArray1547[var9], var1.anIntArray1544[var9], var1.anIntArray1542[var9], TileModel.anIntArray1537[0], TileModel.anIntArray1537[1], TileModel.anIntArray1537[3], TileModel.anIntArray1550[0], TileModel.anIntArray1550[1], TileModel.anIntArray1550[3], TileModel.anIntArray1538[0], TileModel.anIntArray1538[1], TileModel.anIntArray1538[3], var1.triangleTexture[var9]);
            } else {
              JagGraphics3D.method638(var16, var17, var18, var13, var14, var15, var1.anIntArray1547[var9], var1.anIntArray1544[var9], var1.anIntArray1542[var9], TileModel.anIntArray1537[var10], TileModel.anIntArray1537[var11], TileModel.anIntArray1537[var12], TileModel.anIntArray1550[var10], TileModel.anIntArray1550[var11], TileModel.anIntArray1550[var12], TileModel.anIntArray1538[var10], TileModel.anIntArray1538[var11], TileModel.anIntArray1538[var12], var1.triangleTexture[var9]);
            }
          } else {
            int var19 = JagGraphics3D.materialProvider.rgb(var1.triangleTexture[var9]);
            JagGraphics3D.method627(var16, var17, var18, var13, var14, var15, method1444(var19, var1.anIntArray1547[var9]), method1444(var19, var1.anIntArray1544[var9]), method1444(var19, var1.anIntArray1542[var9]));
          }
        } else if (var1.anIntArray1547[var9] != 12345678) {
          JagGraphics3D.method627(var16, var17, var18, var13, var14, var15, var1.anIntArray1547[var9], var1.anIntArray1544[var9], var1.anIntArray1542[var9]);
        }
      }
    }

  }

  public void method1443() {
    viewportWalking = true;
  }

  public long method1461(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 == null) {
      return 0L;
    }
    for (int var5 = 0; var5 < var4.entityMarkerCount; ++var5) {
      EntityMarker var6 = var4.markers[var5];
      if (WorldMapDecor.method379(var6.uid) && var2 == var6.sceneX && var3 == var6.sceneY) {
        return var6.uid;
      }
    }

    return 0L;
  }

  public void method1442(UnlitModel var1, int var2, int var3, int var4) {
    Tile var5;
    UnlitModel var6;
    if (var3 < this.width) {
      var5 = this.tiles[var2][var3 + 1][var4];
      if (var5 != null && var5.decor != null && var5.decor.entity instanceof UnlitModel) {
        var6 = (UnlitModel) var5.decor.entity;
        UnlitModel.method970(var1, var6, 128, 0, 0, true);
      }
    }

    if (var4 < this.width) {
      var5 = this.tiles[var2][var3][var4 + 1];
      if (var5 != null && var5.decor != null && var5.decor.entity instanceof UnlitModel) {
        var6 = (UnlitModel) var5.decor.entity;
        UnlitModel.method970(var1, var6, 0, 0, 128, true);
      }
    }

    if (var3 < this.width && var4 < this.length) {
      var5 = this.tiles[var2][var3 + 1][var4 + 1];
      if (var5 != null && var5.decor != null && var5.decor.entity instanceof UnlitModel) {
        var6 = (UnlitModel) var5.decor.entity;
        UnlitModel.method970(var1, var6, 128, 0, 128, true);
      }
    }

    if (var3 < this.width && var4 > 0) {
      var5 = this.tiles[var2][var3 + 1][var4 - 1];
      if (var5 != null && var5.decor != null && var5.decor.entity instanceof UnlitModel) {
        var6 = (UnlitModel) var5.decor.entity;
        UnlitModel.method970(var1, var6, 128, 0, -128, true);
      }
    }

  }

  public void initialize() {
    for (int floor = 0; floor < this.floorCount; ++floor) {
      for (int x = 0; x < this.width; ++x) {
        for (int z = 0; z < this.length; ++z) {
          this.tiles[floor][x][z] = null;
        }
      }
    }

    for (int var1 = 0; var1 < anInt1938; ++var1) {
      for (int var2 = 0; var2 < occluderCount[var1]; ++var2) {
        A_SCENE_OCCLUDER_ARRAY_ARRAY_1939[var1][var2] = null;
      }

      occluderCount[var1] = 0;
    }

    for (int var1 = 0; var1 < this.tempMarkerCount; ++var1) {
      this.tempMarkers[var1] = null;
    }

    this.tempMarkerCount = 0;

    for (int var1 = 0; var1 < anEntityMarkerArray1940.length; ++var1) {
      anEntityMarkerArray1940[var1] = null;
    }

  }

  public long method1457(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 != null && var4.decor != null ? var4.decor.uid : 0L;
  }

  public boolean method1466(int var1, int var2, int var3, int var4) {
    if (!this.method1464(var1, var2, var3)) {
      return false;
    }
    int var5 = var2 << 7;
    int var6 = var3 << 7;
    return this.method1465(var5 + 1, this.heights[var1][var2][var3] - var4, var6 + 1) && this.method1465(var5 + 128 - 1, this.heights[var1][var2 + 1][var3] - var4, var6 + 1) && this.method1465(var5 + 128 - 1, this.heights[var1][var2 + 1][var3 + 1] - var4, var6 + 128 - 1) && this.method1465(var5 + 1, this.heights[var1][var2][var3 + 1] - var4, var6 + 128 - 1);
  }

  public Boundary getBoundaryAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 == null ? null : var4.boundary;
  }

  public BoundaryDecor getBoundaryDecorAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 == null ? null : var4.boundaryDecor;
  }

  public EntityMarker getEntityMarkerAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 == null) {
      return null;
    }
    for (int var5 = 0; var5 < var4.entityMarkerCount; ++var5) {
      EntityMarker var6 = var4.markers[var5];
      if (WorldMapDecor.method379(var6.uid) && var2 == var6.sceneX && var3 == var6.sceneY) {
        return var6;
      }
    }

    return null;
  }

  public TileDecor getTileDecorAt(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    return var4 != null && var4.decor != null ? var4.decor : null;
  }

  public boolean method1467(int var1, int var2, int var3, int var4, int var5, int var6) {
    int var7;
    int var8;
    if (var3 == var2 && var5 == var4) {
      if (!this.method1464(var1, var2, var4)) {
        return false;
      }
      var7 = var2 << 7;
      var8 = var4 << 7;
      return this.method1465(var7 + 1, this.heights[var1][var2][var4] - var6, var8 + 1) && this.method1465(var7 + 128 - 1, this.heights[var1][var2 + 1][var4] - var6, var8 + 1) && this.method1465(var7 + 128 - 1, this.heights[var1][var2 + 1][var4 + 1] - var6, var8 + 128 - 1) && this.method1465(var7 + 1, this.heights[var1][var2][var4 + 1] - var6, var8 + 128 - 1);
    }
    for (var7 = var2; var7 <= var3; ++var7) {
      for (var8 = var4; var8 <= var5; ++var8) {
        if (this.renderCycles[var1][var7][var8] == -renderTick) {
          return false;
        }
      }
    }

    var7 = (var2 << 7) + 1;
    var8 = (var4 << 7) + 2;
    int var9 = this.heights[var1][var2][var4] - var6;
    if (!this.method1465(var7, var9, var8)) {
      return false;
    }
    int var10 = (var3 << 7) - 1;
    if (!this.method1465(var10, var9, var8)) {
      return false;
    }
    int var11 = (var5 << 7) - 1;
    if (!this.method1465(var7, var9, var11)) {
      return false;
    }
    return this.method1465(var10, var9, var11);
  }

  public void method1468() {
    int var1 = occluderCount[renderFloor];
    SceneOccluder[] var2 = A_SCENE_OCCLUDER_ARRAY_ARRAY_1939[renderFloor];
    anInt1933 = 0;

    for (int var3 = 0; var3 < var1; ++var3) {
      SceneOccluder var4 = var2[var3];
      int var5;
      int var6;
      int var7;
      int var9;
      boolean var13;
      if (var4.flag == 1) {
        var5 = var4.minSceneX - renderSceneX + 25;
        if (var5 >= 0 && var5 <= 50) {
          var6 = var4.minSceneY - renderSceneY + 25;
          if (var6 < 0) {
            var6 = 0;
          }

          var7 = var4.maxSceneY - renderSceneY + 25;
          if (var7 > 50) {
            var7 = 50;
          }

          var13 = false;

          while (var6 <= var7) {
            if (aBooleanArrayArray1951[var5][var6++]) {
              var13 = true;
              break;
            }
          }

          if (var13) {
            var9 = renderCameraX - var4.minAbsoluteX;
            if (var9 > 32) {
              var4.anInt1912 = 1;
            } else {
              if (var9 >= -32) {
                continue;
              }

              var4.anInt1912 = 2;
              var9 = -var9;
            }

            var4.anInt1914 = (var4.minAbsoluteY - renderCameraY << 8) / var9;
            var4.anInt1921 = (var4.maxAbsoluteY - renderCameraY << 8) / var9;
            var4.anInt1918 = (var4.componentHeight - renderCameraZ << 8) / var9;
            var4.anInt1915 = (var4.tileHeight - renderCameraZ << 8) / var9;
            occluders[anInt1933++] = var4;
          }
        }
      } else if (var4.flag == 2) {
        var5 = var4.minSceneY - renderSceneY + 25;
        if (var5 >= 0 && var5 <= 50) {
          var6 = var4.minSceneX - renderSceneX + 25;
          if (var6 < 0) {
            var6 = 0;
          }

          var7 = var4.maxSceneX - renderSceneX + 25;
          if (var7 > 50) {
            var7 = 50;
          }

          var13 = false;

          while (var6 <= var7) {
            if (aBooleanArrayArray1951[var6++][var5]) {
              var13 = true;
              break;
            }
          }

          if (var13) {
            var9 = renderCameraY - var4.minAbsoluteY;
            if (var9 > 32) {
              var4.anInt1912 = 3;
            } else {
              if (var9 >= -32) {
                continue;
              }

              var4.anInt1912 = 4;
              var9 = -var9;
            }

            var4.anInt1917 = (var4.minAbsoluteX - renderCameraX << 8) / var9;
            var4.anInt1911 = (var4.maxAbsoluteX - renderCameraX << 8) / var9;
            var4.anInt1918 = (var4.componentHeight - renderCameraZ << 8) / var9;
            var4.anInt1915 = (var4.tileHeight - renderCameraZ << 8) / var9;
            occluders[anInt1933++] = var4;
          }
        }
      } else if (var4.flag == 4) {
        var5 = var4.componentHeight - renderCameraZ;
        if (var5 > 128) {
          var6 = var4.minSceneY - renderSceneY + 25;
          if (var6 < 0) {
            var6 = 0;
          }

          var7 = var4.maxSceneY - renderSceneY + 25;
          if (var7 > 50) {
            var7 = 50;
          }

          if (var6 <= var7) {
            int var8 = var4.minSceneX - renderSceneX + 25;
            if (var8 < 0) {
              var8 = 0;
            }

            var9 = var4.maxSceneX - renderSceneX + 25;
            if (var9 > 50) {
              var9 = 50;
            }

            boolean var10 = false;

            label144:
            for (int var11 = var8; var11 <= var9; ++var11) {
              for (int var12 = var6; var12 <= var7; ++var12) {
                if (aBooleanArrayArray1951[var11][var12]) {
                  var10 = true;
                  break label144;
                }
              }
            }

            if (var10) {
              var4.anInt1912 = 5;
              var4.anInt1917 = (var4.minAbsoluteX - renderCameraX << 8) / var5;
              var4.anInt1911 = (var4.maxAbsoluteX - renderCameraX << 8) / var5;
              var4.anInt1914 = (var4.minAbsoluteY - renderCameraY << 8) / var5;
              var4.anInt1921 = (var4.maxAbsoluteY - renderCameraY << 8) / var5;
              occluders[anInt1933++] = var4;
            }
          }
        }
      }
    }

  }

  public void deleteItemPile(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 != null) {
      var4.pickableStack = null;
    }
  }

  public void addPickable(int var1, int var2, int var3, int var4, Entity var5, long var6, Entity var8, Entity var9) {
    PickableStack var10 = new PickableStack();
    var10.bottom = var5;
    var10.absoluteX = var2 * 128 + 64;
    var10.absoluteY = var3 * 128 + 64;
    var10.anInt172 = var4;
    var10.uid = var6;
    var10.middle = var8;
    var10.top = var9;
    int var11 = 0;
    Tile var12 = this.tiles[var1][var2][var3];
    if (var12 != null) {
      for (int var13 = 0; var13 < var12.entityMarkerCount; ++var13) {
        if ((var12.markers[var13].config & 256) == 256 && var12.markers[var13].entity instanceof Model) {
          Model var14 = (Model) var12.markers[var13].entity;
          var14.computeBounds();
          if (var14.height > var11) {
            var11 = var14.height;
          }
        }
      }
    }

    var10.height = var11;
    if (this.tiles[var1][var2][var3] == null) {
      this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
    }

    this.tiles[var1][var2][var3].pickableStack = var10;
  }

  public void renderTile(Tile var1, boolean var2) {
    aNodeDeque1936.add(var1);
    while (true) {
      Tile renderingTile;
      int var4;
      int var5;
      int var6;
      int var7;
      Tile[][] var8;
      Tile var9;
      int var11;
      int var16;
      int var17;
      int var18;
      int var24;
      int var25;
      while (true) {
        Boundary var10;
        EntityMarker var12;
        boolean var13;
        int var14;
        int var19;
        int var20;
        Tile var36;
        while (true) {
          renderingTile = aNodeDeque1936.popFirst();
          if (renderingTile == null) {
            return;
          }

          while (!renderingTile.aBoolean1151) {
            renderingTile = aNodeDeque1936.popFirst();
            if (renderingTile == null) {

              return;
            }
          }

          var4 = renderingTile.sceneX;
          var5 = renderingTile.sceneY;
          var6 = renderingTile.floorLevel;
          var7 = renderingTile.renderLevel;
          var8 = this.tiles[var6];
          if (!renderingTile.aBoolean665) {
            break;
          }

          if (var2) {
            if (var6 > 0) {
              var9 = this.tiles[var6 - 1][var4][var5];
              if (var9 != null && var9.aBoolean1151) {
                continue;
              }
            }

            if (var4 <= renderSceneX && var4 > renderAreaMinX) {
              var9 = var8[var4 - 1][var5];
              if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                continue;
              }
            }

            if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
              var9 = var8[var4 + 1][var5];
              if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                continue;
              }
            }

            if (var5 <= renderSceneY && var5 > renderAreaMinY) {
              var9 = var8[var4][var5 - 1];
              if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                continue;
              }
            }

            if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
              var9 = var8[var4][var5 + 1];
              if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                continue;
              }
            }
          } else {
            var2 = true;
          }

          renderingTile.aBoolean665 = false;
          if (renderingTile.underneath != null) {
            var9 = renderingTile.underneath;
            if (var9.paint != null) {
              if (!this.method1464(0, var4, var5)) {

                this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
              }
            } else if (var9.model != null && !this.method1464(0, var4, var5)) {
              this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
            }

            var10 = var9.boundary;
            if (var10 != null) {
              var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX - renderCameraX, var10.height - renderCameraZ, var10.absoluteY - renderCameraY, var10.uid);
            }

            for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
              var12 = var9.markers[var11];
              if (var12 != null) {
                var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
              }
            }
          }

          var13 = false;
          if (renderingTile.paint != null) {
            if (!this.method1464(var7, var4, var5)) {
              var13 = true;
              if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
              }
            }
          } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
            var13 = true;
            this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
          }

          var14 = 0;
          var11 = 0;
          Boundary var31 = renderingTile.boundary;
          BoundaryDecor var15 = renderingTile.boundaryDecor;
          if (var31 != null || var15 != null) {
            if (var4 == renderSceneX) {
              ++var14;
            } else if (renderSceneX < var4) {
              var14 += 2;
            }

            if (var5 == renderSceneY) {
              var14 += 3;
            } else if (renderSceneY > var5) {
              var14 += 6;
            }

            var11 = anIntArray1935[var14];
            renderingTile.anInt1149 = anIntArray1948[var14];
          }

          if (var31 != null) {
            if ((var31.orientation & anIntArray1937[var14]) != 0) {
              if (var31.orientation == 16) {
                renderingTile.entityMarkerSideRenderConfig = 3;
                renderingTile.anInt563 = anIntArray1945[var14];
                renderingTile.anInt575 = 3 - renderingTile.anInt563;
              } else if (var31.orientation == 32) {
                renderingTile.entityMarkerSideRenderConfig = 6;
                renderingTile.anInt563 = anIntArray1934[var14];
                renderingTile.anInt575 = 6 - renderingTile.anInt563;
              } else if (var31.orientation == 64) {
                renderingTile.entityMarkerSideRenderConfig = 12;
                renderingTile.anInt563 = anIntArray1944[var14];
                renderingTile.anInt575 = 12 - renderingTile.anInt563;
              } else {
                renderingTile.entityMarkerSideRenderConfig = 9;
                renderingTile.anInt563 = anIntArray1959[var14];
                renderingTile.anInt575 = 9 - renderingTile.anInt563;
              }
            } else {
              renderingTile.entityMarkerSideRenderConfig = 0;
            }

            if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
              var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX - renderCameraX, var31.height - renderCameraZ, var31.absoluteY - renderCameraY, var31.uid);
            }

            if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
              var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX - renderCameraX, var31.height - renderCameraZ, var31.absoluteY - renderCameraY, var31.uid);
            }
          }

          if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
            if ((var15.renderConfig & var11) != 0) {
              var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX - renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY - renderCameraY), var15.uid);
            } else if (var15.renderConfig == 256) {
              var16 = var15.absoluteX - renderCameraX;
              var17 = var15.height - renderCameraZ;
              var18 = var15.absoluteY * renderCameraY;
              var19 = var15.orientation;
              if (var19 != 1 && var19 != 2) {
                var20 = var16;
              } else {
                var20 = -var16;
              }

              int var21;
              if (var19 != 2 && var19 != 3) {
                var21 = var18;
              } else {
                var21 = -var18;
              }

              if (var21 < var20) {
                var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
              } else if (var15.linkedEntity != null) {
                var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
              }
            }
          }

          if (var13) {
            TileDecor var22 = renderingTile.decor;
            if (var22 != null) {
              var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY - renderCameraY, var22.uid);
            }

            PickableStack var23 = renderingTile.pickableStack;
            if (var23 != null && var23.height == 0) {
              if (var23.middle != null) {
                var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX *
                                                                           renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
              }

              if (var23.top != null) {
                var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
              }

              if (var23.bottom != null) {
                var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
              }
            }
          }

          var16 = renderingTile.entityMarkerSideFlags;
          if (var16 != 0) {
            if (var4 < renderSceneX && (var16 & 4) != 0) {
              var36 = var8[var4 + 1][var5];
              if (var36 != null && var36.aBoolean1151) {
                aNodeDeque1936.add(var36);
              }
            }

            if (var5 < renderSceneY && (var16 & 2) != 0) {
              var36 = var8[var4][var5 + 1];
              if (var36 != null && var36.aBoolean1151) {
                aNodeDeque1936.add(var36);
              }
            }

            if (var4 > renderSceneX && (var16 & 1) != 0) {
              var36 = var8[var4 - 1][var5];
              if (var36 != null && var36.aBoolean1151) {
                aNodeDeque1936.add(var36);
              }
            }

            if (var5 > renderSceneY && (var16 & 8) != 0) {
              var36 = var8[var4][var5 - 1];
              if (var36 != null && var36.aBoolean1151) {
                aNodeDeque1936.add(var36);
              }
            }
          }
          break;
        }

        if (renderingTile.entityMarkerSideRenderConfig != 0) {
          var13 = true;

          for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
            if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
              var13 = false;
              break;
            }
          }

          if (var13) {
            var10 = renderingTile.boundary;
            if (!this.method1463(var7, var4, var5, var10.orientation)) {
              var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
            }

            renderingTile.entityMarkerSideRenderConfig = 0;
          }
        }

        if (!renderingTile.drawEntityMarkers) {
          break;
        }

        try {
          int var33 = renderingTile.entityMarkerCount;
          renderingTile.drawEntityMarkers = false;
          var14 = 0;

          label563:
          for (var11 = 0; var11 < var33; ++var11) {
            var12 = renderingTile.markers[var11];
            if (var12.anInt452 != renderTick) {
              for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                  var36 = var8[var24][var16];
                  if (var36.aBoolean665) {
                    renderingTile.drawEntityMarkers = true;
                    continue label563;
                  }

                  if (var36.entityMarkerSideRenderConfig != 0) {
                    var18 = 0;
                    if (var24 > var12.sceneX) {
                      ++var18;
                    }

                    if (var24 < var12.maxSceneX) {
                      var18 += 4;
                    }

                    if (var16 > var12.sceneY) {
                      var18 += 8;
                    }

                    if (var16 < var12.maxSceneY) {
                      var18 += 2;
                    }

                    if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                      renderingTile.drawEntityMarkers = true;
                      continue label563;
                    }
                  }
                }
              }

              anEntityMarkerArray1940[var14++] = var12;
              var24 = renderSceneX - var12.sceneX;
              var16 = var12.maxSceneX - renderSceneX;
              if (var16 > var24) {
                var24 = var16;
              }

              var17 = renderSceneY - var12.sceneY;
              var18 = var12.maxSceneY - renderSceneY;
              if (var18 > var17) {
                var12.anInt453 = var24 + var18;
              } else {
                var12.anInt453 = var24 + var17;
              }
            }
          }

          while (var14 > 0) {
            var11 = -50;
            var25 = -1;

            for (var24 = 0; var24 < var14; ++var24) {
              EntityMarker var35 = anEntityMarkerArray1940[var24];
              if (var35.anInt452 != renderTick) {
                if (var35.anInt453 > var11) {
                  var11 = var35.anInt453;
                  var25 = var24;
                } else if (var11 == var35.anInt453) {
                  var17 = var35.centerAbsoluteX - renderCameraX;
                  var18 = var35.centerAbsoluteY - renderCameraY;
                  var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                  var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                  if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                    var25 = var24;
                  }
                }
              }
            }

            if (var25 == -1) {
              break;
            }

            EntityMarker var34 = anEntityMarkerArray1940[var25];
            var34.anInt452 = renderTick;
            if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
              var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
            }

            for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
              for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                Tile var26 = var8[var16][var17];
                if (var26.entityMarkerSideRenderConfig != 0) {
                  aNodeDeque1936.add(var26);
                } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                  aNodeDeque1936.add(var26);
                }
              }
            }
          }

          if (!renderingTile.drawEntityMarkers) {
            break;
          }
        } catch (Exception var28) {
          renderingTile.drawEntityMarkers = false;
          break;
        }
      }
      while (!renderingTile.aBoolean1151) {
        while (true) {
          Boundary var10;
          EntityMarker var12;
          boolean var13;
          int var14;
          int var19;
          int var20;
          Tile var36;
          while (true) {
            do {
              renderingTile = aNodeDeque1936.popFirst();
              if (renderingTile == null) {

                return;
              }
            } while (!renderingTile.aBoolean1151);

            var4 = renderingTile.sceneX;
            var5 = renderingTile.sceneY;
            var6 = renderingTile.floorLevel;
            var7 = renderingTile.renderLevel;
            var8 = this.tiles[var6];
            if (!renderingTile.aBoolean665) {
              break;
            }

            if (var2) {
              if (var6 > 0) {
                var9 = this.tiles[var6 - 1][var4][var5];
                if (var9 != null && var9.aBoolean1151) {
                  continue;
                }
              }

              if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                var9 = var8[var4 - 1][var5];
                if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                  continue;
                }
              }

              if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                var9 = var8[var4 + 1][var5];
                if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                  continue;
                }
              }

              if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                var9 = var8[var4][var5 - 1];
                if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                  continue;
                }
              }

              if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                var9 = var8[var4][var5 + 1];
                if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                  continue;
                }
              }
            } else {
              var2 = true;
            }

            renderingTile.aBoolean665 = false;
            if (renderingTile.underneath != null) {
              var9 = renderingTile.underneath;
              if (var9.paint != null) {
                if (!this.method1464(0, var4, var5)) {
                  this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                }
              } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
              }

              var10 = var9.boundary;
              if (var10 != null) {
                var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
              }

              for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                var12 = var9.markers[var11];
                if (var12 != null) {
                  var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                }
              }
            }

            var13 = false;
            if (renderingTile.paint != null) {
              if (!this.method1464(var7, var4, var5)) {
                var13 = true;
                if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                  this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                }
              }
            } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
              var13 = true;
              this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
            }

            var14 = 0;
            var11 = 0;
            Boundary var31 = renderingTile.boundary;
            BoundaryDecor var15 = renderingTile.boundaryDecor;
            if (var31 != null || var15 != null) {
              if (var4 == renderSceneX) {
                ++var14;
              } else if (renderSceneX < var4) {
                var14 += 2;
              }

              if (var5 == renderSceneY) {
                var14 += 3;
              } else if (renderSceneY > var5) {
                var14 += 6;
              }

              var11 = anIntArray1935[var14];
              renderingTile.anInt1149 = anIntArray1948[var14];
            }

            if (var31 != null) {
              if ((var31.orientation & anIntArray1937[var14]) != 0) {
                if (var31.orientation == 16) {
                  renderingTile.entityMarkerSideRenderConfig = 3;
                  renderingTile.anInt563 = anIntArray1945[var14];
                  renderingTile.anInt575 = 3 - renderingTile.anInt563;
                } else if (var31.orientation == 32) {
                  renderingTile.entityMarkerSideRenderConfig = 6;
                  renderingTile.anInt563 = anIntArray1934[var14];
                  renderingTile.anInt575 = 6 - renderingTile.anInt563;
                } else if (var31.orientation == 64) {
                  renderingTile.entityMarkerSideRenderConfig = 12;
                  renderingTile.anInt563 = anIntArray1944[var14];
                  renderingTile.anInt575 = 12 - renderingTile.anInt563;
                } else {
                  renderingTile.entityMarkerSideRenderConfig = 9;
                  renderingTile.anInt563 = anIntArray1959[var14];
                  renderingTile.anInt575 = 9 - renderingTile.anInt563;
                }
              } else {
                renderingTile.entityMarkerSideRenderConfig = 0;
              }

              if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
              }

              if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
              }
            }

            if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
              if ((var15.renderConfig & var11) != 0) {
                var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
              } else if (var15.renderConfig == 256) {
                var16 = var15.absoluteX * renderCameraX;
                var17 = var15.height - renderCameraZ;
                var18 = var15.absoluteY * renderCameraY;
                var19 = var15.orientation;
                if (var19 != 1 && var19 != 2) {
                  var20 = var16;
                } else {
                  var20 = -var16;
                }

                int var21;
                if (var19 != 2 && var19 != 3) {
                  var21 = var18;
                } else {
                  var21 = -var18;
                }

                if (var21 < var20) {
                  var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                } else if (var15.linkedEntity != null) {
                  var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                }
              }
            }

            if (var13) {
              TileDecor var22 = renderingTile.decor;
              if (var22 != null) {
                var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
              }

              PickableStack var23 = renderingTile.pickableStack;
              if (var23 != null && var23.height == 0) {
                if (var23.middle != null) {
                  var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                }

                if (var23.top != null) {
                  var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                }

                if (var23.bottom != null) {
                  var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                }
              }
            }

            var16 = renderingTile.entityMarkerSideFlags;
            if (var16 != 0) {
              if (var4 < renderSceneX && (var16 & 4) != 0) {
                var36 = var8[var4 + 1][var5];
                if (var36 != null && var36.aBoolean1151) {
                  aNodeDeque1936.add(var36);
                }
              }

              if (var5 < renderSceneY && (var16 & 2) != 0) {
                var36 = var8[var4][var5 + 1];
                if (var36 != null && var36.aBoolean1151) {
                  aNodeDeque1936.add(var36);
                }
              }

              if (var4 > renderSceneX && (var16 & 1) != 0) {
                var36 = var8[var4 - 1][var5];
                if (var36 != null && var36.aBoolean1151) {
                  aNodeDeque1936.add(var36);
                }
              }

              if (var5 > renderSceneY && (var16 & 8) != 0) {
                var36 = var8[var4][var5 - 1];
                if (var36 != null && var36.aBoolean1151) {
                  aNodeDeque1936.add(var36);
                }
              }
            }
            break;
          }

          if (renderingTile.entityMarkerSideRenderConfig != 0) {
            var13 = true;

            for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
              if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                var13 = false;
                break;
              }
            }

            if (var13) {
              var10 = renderingTile.boundary;
              if (!this.method1463(var7, var4, var5, var10.orientation)) {
                var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
              }

              renderingTile.entityMarkerSideRenderConfig = 0;
            }
          }

          if (!renderingTile.drawEntityMarkers) {
            break;
          }

          try {
            int var33 = renderingTile.entityMarkerCount;
            renderingTile.drawEntityMarkers = false;
            var14 = 0;

            label563:
            for (var11 = 0; var11 < var33; ++var11) {
              var12 = renderingTile.markers[var11];
              if (var12.anInt452 != renderTick) {
                for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                  for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                    var36 = var8[var24][var16];
                    if (var36.aBoolean665) {
                      renderingTile.drawEntityMarkers = true;
                      continue label563;
                    }

                    if (var36.entityMarkerSideRenderConfig != 0) {
                      var18 = 0;
                      if (var24 > var12.sceneX) {
                        ++var18;
                      }

                      if (var24 < var12.maxSceneX) {
                        var18 += 4;
                      }

                      if (var16 > var12.sceneY) {
                        var18 += 8;
                      }

                      if (var16 < var12.maxSceneY) {
                        var18 += 2;
                      }

                      if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                        renderingTile.drawEntityMarkers = true;
                        continue label563;
                      }
                    }
                  }
                }

                anEntityMarkerArray1940[var14++] = var12;
                var24 = renderSceneX - var12.sceneX;
                var16 = var12.maxSceneX - renderSceneX;
                if (var16 > var24) {
                  var24 = var16;
                }

                var17 = renderSceneY - var12.sceneY;
                var18 = var12.maxSceneY - renderSceneY;
                if (var18 > var17) {
                  var12.anInt453 = var24 + var18;
                } else {
                  var12.anInt453 = var24 + var17;
                }
              }
            }

            while (var14 > 0) {
              var11 = -50;
              var25 = -1;

              for (var24 = 0; var24 < var14; ++var24) {
                EntityMarker var35 = anEntityMarkerArray1940[var24];
                if (var35.anInt452 != renderTick) {
                  if (var35.anInt453 > var11) {
                    var11 = var35.anInt453;
                    var25 = var24;
                  } else if (var11 == var35.anInt453) {
                    var17 = var35.centerAbsoluteX - renderCameraX;
                    var18 = var35.centerAbsoluteY - renderCameraY;
                    var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                    var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                    if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                      var25 = var24;
                    }
                  }
                }
              }

              if (var25 == -1) {
                break;
              }

              EntityMarker var34 = anEntityMarkerArray1940[var25];
              var34.anInt452 = renderTick;
              if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
              }

              for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                  Tile var26 = var8[var16][var17];
                  if (var26.entityMarkerSideRenderConfig != 0) {
                    aNodeDeque1936.add(var26);
                  } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                    aNodeDeque1936.add(var26);
                  }
                }
              }
            }

            if (!renderingTile.drawEntityMarkers) {
              break;
            }
          } catch (Exception var28) {
            renderingTile.drawEntityMarkers = false;
            break;
          }
        }
      }
      while (renderingTile.entityMarkerSideRenderConfig != 0) {
        do {
          while (true) {
            Boundary var10;
            EntityMarker var12;
            boolean var13;
            int var14;
            int var19;
            int var20;
            Tile var36;
            while (true) {
              do {
                renderingTile = aNodeDeque1936.popFirst();
                if (renderingTile == null) {

                  return;
                }
              } while (!renderingTile.aBoolean1151);

              var4 = renderingTile.sceneX;
              var5 = renderingTile.sceneY;
              var6 = renderingTile.floorLevel;
              var7 = renderingTile.renderLevel;
              var8 = this.tiles[var6];
              if (!renderingTile.aBoolean665) {
                break;
              }

              if (var2) {
                if (var6 > 0) {
                  var9 = this.tiles[var6 - 1][var4][var5];
                  if (var9 != null && var9.aBoolean1151) {
                    continue;
                  }
                }

                if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                  var9 = var8[var4 - 1][var5];
                  if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                    continue;
                  }
                }

                if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                  var9 = var8[var4 + 1][var5];
                  if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                    continue;
                  }
                }

                if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                  var9 = var8[var4][var5 - 1];
                  if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                    continue;
                  }
                }

                if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                  var9 = var8[var4][var5 + 1];
                  if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                    continue;
                  }
                }
              } else {
                var2 = true;
              }

              renderingTile.aBoolean665 = false;
              if (renderingTile.underneath != null) {
                var9 = renderingTile.underneath;
                if (var9.paint != null) {
                  if (!this.method1464(0, var4, var5)) {
                    this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }
                } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                  this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                }

                var10 = var9.boundary;
                if (var10 != null) {
                  var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                }

                for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                  var12 = var9.markers[var11];
                  if (var12 != null) {
                    var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                  }
                }
              }

              var13 = false;
              if (renderingTile.paint != null) {
                if (!this.method1464(var7, var4, var5)) {
                  var13 = true;
                  if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                    this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }
                }
              } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                var13 = true;
                this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
              }

              var14 = 0;
              var11 = 0;
              Boundary var31 = renderingTile.boundary;
              BoundaryDecor var15 = renderingTile.boundaryDecor;
              if (var31 != null || var15 != null) {
                if (var4 == renderSceneX) {
                  ++var14;
                } else if (renderSceneX < var4) {
                  var14 += 2;
                }

                if (var5 == renderSceneY) {
                  var14 += 3;
                } else if (renderSceneY > var5) {
                  var14 += 6;
                }

                var11 = anIntArray1935[var14];
                renderingTile.anInt1149 = anIntArray1948[var14];
              }

              if (var31 != null) {
                if ((var31.orientation & anIntArray1937[var14]) != 0) {
                  if (var31.orientation == 16) {
                    renderingTile.entityMarkerSideRenderConfig = 3;
                    renderingTile.anInt563 = anIntArray1945[var14];
                    renderingTile.anInt575 = 3 - renderingTile.anInt563;
                  } else if (var31.orientation == 32) {
                    renderingTile.entityMarkerSideRenderConfig = 6;
                    renderingTile.anInt563 = anIntArray1934[var14];
                    renderingTile.anInt575 = 6 - renderingTile.anInt563;
                  } else if (var31.orientation == 64) {
                    renderingTile.entityMarkerSideRenderConfig = 12;
                    renderingTile.anInt563 = anIntArray1944[var14];
                    renderingTile.anInt575 = 12 - renderingTile.anInt563;
                  } else {
                    renderingTile.entityMarkerSideRenderConfig = 9;
                    renderingTile.anInt563 = anIntArray1959[var14];
                    renderingTile.anInt575 = 9 - renderingTile.anInt563;
                  }
                } else {
                  renderingTile.entityMarkerSideRenderConfig = 0;
                }

                if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                  var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                }

                if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                  var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                }
              }

              if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                if ((var15.renderConfig & var11) != 0) {
                  var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                } else if (var15.renderConfig == 256) {
                  var16 = var15.absoluteX * renderCameraX;
                  var17 = var15.height - renderCameraZ;
                  var18 = var15.absoluteY * renderCameraY;
                  var19 = var15.orientation;
                  if (var19 != 1 && var19 != 2) {
                    var20 = var16;
                  } else {
                    var20 = -var16;
                  }

                  int var21;
                  if (var19 != 2 && var19 != 3) {
                    var21 = var18;
                  } else {
                    var21 = -var18;
                  }

                  if (var21 < var20) {
                    var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                  } else if (var15.linkedEntity != null) {
                    var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                  }
                }
              }

              if (var13) {
                TileDecor var22 = renderingTile.decor;
                if (var22 != null) {
                  var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                }

                PickableStack var23 = renderingTile.pickableStack;
                if (var23 != null && var23.height == 0) {
                  if (var23.middle != null) {
                    var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                  }

                  if (var23.top != null) {
                    var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                  }

                  if (var23.bottom != null) {
                    var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                  }
                }
              }

              var16 = renderingTile.entityMarkerSideFlags;
              if (var16 != 0) {
                if (var4 < renderSceneX && (var16 & 4) != 0) {
                  var36 = var8[var4 + 1][var5];
                  if (var36 != null && var36.aBoolean1151) {
                    aNodeDeque1936.add(var36);
                  }
                }

                if (var5 < renderSceneY && (var16 & 2) != 0) {
                  var36 = var8[var4][var5 + 1];
                  if (var36 != null && var36.aBoolean1151) {
                    aNodeDeque1936.add(var36);
                  }
                }

                if (var4 > renderSceneX && (var16 & 1) != 0) {
                  var36 = var8[var4 - 1][var5];
                  if (var36 != null && var36.aBoolean1151) {
                    aNodeDeque1936.add(var36);
                  }
                }

                if (var5 > renderSceneY && (var16 & 8) != 0) {
                  var36 = var8[var4][var5 - 1];
                  if (var36 != null && var36.aBoolean1151) {
                    aNodeDeque1936.add(var36);
                  }
                }
              }
              break;
            }

            if (renderingTile.entityMarkerSideRenderConfig != 0) {
              var13 = true;

              for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                  var13 = false;
                  break;
                }
              }

              if (var13) {
                var10 = renderingTile.boundary;
                if (!this.method1463(var7, var4, var5, var10.orientation)) {
                  var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                }

                renderingTile.entityMarkerSideRenderConfig = 0;
              }
            }

            if (!renderingTile.drawEntityMarkers) {
              break;
            }

            try {
              int var33 = renderingTile.entityMarkerCount;
              renderingTile.drawEntityMarkers = false;
              var14 = 0;

              label563:
              for (var11 = 0; var11 < var33; ++var11) {
                var12 = renderingTile.markers[var11];
                if (var12.anInt452 != renderTick) {
                  for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                    for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                      var36 = var8[var24][var16];
                      if (var36.aBoolean665) {
                        renderingTile.drawEntityMarkers = true;
                        continue label563;
                      }

                      if (var36.entityMarkerSideRenderConfig != 0) {
                        var18 = 0;
                        if (var24 > var12.sceneX) {
                          ++var18;
                        }

                        if (var24 < var12.maxSceneX) {
                          var18 += 4;
                        }

                        if (var16 > var12.sceneY) {
                          var18 += 8;
                        }

                        if (var16 < var12.maxSceneY) {
                          var18 += 2;
                        }

                        if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                          renderingTile.drawEntityMarkers = true;
                          continue label563;
                        }
                      }
                    }
                  }

                  anEntityMarkerArray1940[var14++] = var12;
                  var24 = renderSceneX - var12.sceneX;
                  var16 = var12.maxSceneX - renderSceneX;
                  if (var16 > var24) {
                    var24 = var16;
                  }

                  var17 = renderSceneY - var12.sceneY;
                  var18 = var12.maxSceneY - renderSceneY;
                  if (var18 > var17) {
                    var12.anInt453 = var24 + var18;
                  } else {
                    var12.anInt453 = var24 + var17;
                  }
                }
              }

              while (var14 > 0) {
                var11 = -50;
                var25 = -1;

                for (var24 = 0; var24 < var14; ++var24) {
                  EntityMarker var35 = anEntityMarkerArray1940[var24];
                  if (var35.anInt452 != renderTick) {
                    if (var35.anInt453 > var11) {
                      var11 = var35.anInt453;
                      var25 = var24;
                    } else if (var11 == var35.anInt453) {
                      var17 = var35.centerAbsoluteX - renderCameraX;
                      var18 = var35.centerAbsoluteY - renderCameraY;
                      var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                      var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                      if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                        var25 = var24;
                      }
                    }
                  }
                }

                if (var25 == -1) {
                  break;
                }

                EntityMarker var34 = anEntityMarkerArray1940[var25];
                var34.anInt452 = renderTick;
                if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                  var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                }

                for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                  for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                    Tile var26 = var8[var16][var17];
                    if (var26.entityMarkerSideRenderConfig != 0) {
                      aNodeDeque1936.add(var26);
                    } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                      aNodeDeque1936.add(var26);
                    }
                  }
                }
              }

              if (!renderingTile.drawEntityMarkers) {
                break;
              }
            } catch (Exception var28) {
              renderingTile.drawEntityMarkers = false;
              break;
            }
          }
        } while (!renderingTile.aBoolean1151);
      }

      if (var4 > renderSceneX || var4 <= renderAreaMinX) {
        break;
      }

      var9 = var8[var4 - 1][var5];
      while (var9 != null && var9.aBoolean1151) {
        do {
          do {
            while (true) {
              Boundary var10;
              EntityMarker var12;
              boolean var13;
              int var14;
              int var19;
              int var20;
              Tile var36;
              while (true) {
                do {
                  renderingTile = aNodeDeque1936.popFirst();
                  if (renderingTile == null) {

                    return;
                  }
                } while (!renderingTile.aBoolean1151);

                var4 = renderingTile.sceneX;
                var5 = renderingTile.sceneY;
                var6 = renderingTile.floorLevel;
                var7 = renderingTile.renderLevel;
                var8 = this.tiles[var6];
                if (!renderingTile.aBoolean665) {
                  break;
                }

                if (var2) {
                  if (var6 > 0) {
                    var9 = this.tiles[var6 - 1][var4][var5];
                    if (var9 != null && var9.aBoolean1151) {
                      continue;
                    }
                  }

                  if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                    var9 = var8[var4 - 1][var5];
                    if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                      continue;
                    }
                  }

                  if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                    var9 = var8[var4 + 1][var5];
                    if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                      continue;
                    }
                  }

                  if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                    var9 = var8[var4][var5 - 1];
                    if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                      continue;
                    }
                  }

                  if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                    var9 = var8[var4][var5 + 1];
                    if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                      continue;
                    }
                  }
                } else {
                  var2 = true;
                }

                renderingTile.aBoolean665 = false;
                if (renderingTile.underneath != null) {
                  var9 = renderingTile.underneath;
                  if (var9.paint != null) {
                    if (!this.method1464(0, var4, var5)) {
                      this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }
                  } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                    this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }

                  var10 = var9.boundary;
                  if (var10 != null) {
                    var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                  }

                  for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                    var12 = var9.markers[var11];
                    if (var12 != null) {
                      var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                    }
                  }
                }

                var13 = false;
                if (renderingTile.paint != null) {
                  if (!this.method1464(var7, var4, var5)) {
                    var13 = true;
                    if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                      this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }
                  }
                } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                  var13 = true;
                  this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                }

                var14 = 0;
                var11 = 0;
                Boundary var31 = renderingTile.boundary;
                BoundaryDecor var15 = renderingTile.boundaryDecor;
                if (var31 != null || var15 != null) {
                  if (var4 == renderSceneX) {
                    ++var14;
                  } else if (renderSceneX < var4) {
                    var14 += 2;
                  }

                  if (var5 == renderSceneY) {
                    var14 += 3;
                  } else if (renderSceneY > var5) {
                    var14 += 6;
                  }

                  var11 = anIntArray1935[var14];
                  renderingTile.anInt1149 = anIntArray1948[var14];
                }

                if (var31 != null) {
                  if ((var31.orientation & anIntArray1937[var14]) != 0) {
                    if (var31.orientation == 16) {
                      renderingTile.entityMarkerSideRenderConfig = 3;
                      renderingTile.anInt563 = anIntArray1945[var14];
                      renderingTile.anInt575 = 3 - renderingTile.anInt563;
                    } else if (var31.orientation == 32) {
                      renderingTile.entityMarkerSideRenderConfig = 6;
                      renderingTile.anInt563 = anIntArray1934[var14];
                      renderingTile.anInt575 = 6 - renderingTile.anInt563;
                    } else if (var31.orientation == 64) {
                      renderingTile.entityMarkerSideRenderConfig = 12;
                      renderingTile.anInt563 = anIntArray1944[var14];
                      renderingTile.anInt575 = 12 - renderingTile.anInt563;
                    } else {
                      renderingTile.entityMarkerSideRenderConfig = 9;
                      renderingTile.anInt563 = anIntArray1959[var14];
                      renderingTile.anInt575 = 9 - renderingTile.anInt563;
                    }
                  } else {
                    renderingTile.entityMarkerSideRenderConfig = 0;
                  }

                  if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                    var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                  }

                  if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                    var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                  }
                }

                if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                  if ((var15.renderConfig & var11) != 0) {
                    var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                  } else if (var15.renderConfig == 256) {
                    var16 = var15.absoluteX * renderCameraX;
                    var17 = var15.height - renderCameraZ;
                    var18 = var15.absoluteY * renderCameraY;
                    var19 = var15.orientation;
                    if (var19 != 1 && var19 != 2) {
                      var20 = var16;
                    } else {
                      var20 = -var16;
                    }

                    int var21;
                    if (var19 != 2 && var19 != 3) {
                      var21 = var18;
                    } else {
                      var21 = -var18;
                    }

                    if (var21 < var20) {
                      var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                    } else if (var15.linkedEntity != null) {
                      var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                    }
                  }
                }

                if (var13) {
                  TileDecor var22 = renderingTile.decor;
                  if (var22 != null) {
                    var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                  }

                  PickableStack var23 = renderingTile.pickableStack;
                  if (var23 != null && var23.height == 0) {
                    if (var23.middle != null) {
                      var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                    }

                    if (var23.top != null) {
                      var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                    }

                    if (var23.bottom != null) {
                      var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                    }
                  }
                }

                var16 = renderingTile.entityMarkerSideFlags;
                if (var16 != 0) {
                  if (var4 < renderSceneX && (var16 & 4) != 0) {
                    var36 = var8[var4 + 1][var5];
                    if (var36 != null && var36.aBoolean1151) {
                      aNodeDeque1936.add(var36);
                    }
                  }

                  if (var5 < renderSceneY && (var16 & 2) != 0) {
                    var36 = var8[var4][var5 + 1];
                    if (var36 != null && var36.aBoolean1151) {
                      aNodeDeque1936.add(var36);
                    }
                  }

                  if (var4 > renderSceneX && (var16 & 1) != 0) {
                    var36 = var8[var4 - 1][var5];
                    if (var36 != null && var36.aBoolean1151) {
                      aNodeDeque1936.add(var36);
                    }
                  }

                  if (var5 > renderSceneY && (var16 & 8) != 0) {
                    var36 = var8[var4][var5 - 1];
                    if (var36 != null && var36.aBoolean1151) {
                      aNodeDeque1936.add(var36);
                    }
                  }
                }
                break;
              }

              if (renderingTile.entityMarkerSideRenderConfig != 0) {
                var13 = true;

                for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                  if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                    var13 = false;
                    break;
                  }
                }

                if (var13) {
                  var10 = renderingTile.boundary;
                  if (!this.method1463(var7, var4, var5, var10.orientation)) {
                    var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                  }

                  renderingTile.entityMarkerSideRenderConfig = 0;
                }
              }

              if (!renderingTile.drawEntityMarkers) {
                break;
              }

              try {
                int var33 = renderingTile.entityMarkerCount;
                renderingTile.drawEntityMarkers = false;
                var14 = 0;

                label563:
                for (var11 = 0; var11 < var33; ++var11) {
                  var12 = renderingTile.markers[var11];
                  if (var12.anInt452 != renderTick) {
                    for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                      for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                        var36 = var8[var24][var16];
                        if (var36.aBoolean665) {
                          renderingTile.drawEntityMarkers = true;
                          continue label563;
                        }

                        if (var36.entityMarkerSideRenderConfig != 0) {
                          var18 = 0;
                          if (var24 > var12.sceneX) {
                            ++var18;
                          }

                          if (var24 < var12.maxSceneX) {
                            var18 += 4;
                          }

                          if (var16 > var12.sceneY) {
                            var18 += 8;
                          }

                          if (var16 < var12.maxSceneY) {
                            var18 += 2;
                          }

                          if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                            renderingTile.drawEntityMarkers = true;
                            continue label563;
                          }
                        }
                      }
                    }

                    anEntityMarkerArray1940[var14++] = var12;
                    var24 = renderSceneX - var12.sceneX;
                    var16 = var12.maxSceneX - renderSceneX;
                    if (var16 > var24) {
                      var24 = var16;
                    }

                    var17 = renderSceneY - var12.sceneY;
                    var18 = var12.maxSceneY - renderSceneY;
                    if (var18 > var17) {
                      var12.anInt453 = var24 + var18;
                    } else {
                      var12.anInt453 = var24 + var17;
                    }
                  }
                }

                while (var14 > 0) {
                  var11 = -50;
                  var25 = -1;

                  for (var24 = 0; var24 < var14; ++var24) {
                    EntityMarker var35 = anEntityMarkerArray1940[var24];
                    if (var35.anInt452 != renderTick) {
                      if (var35.anInt453 > var11) {
                        var11 = var35.anInt453;
                        var25 = var24;
                      } else if (var11 == var35.anInt453) {
                        var17 = var35.centerAbsoluteX - renderCameraX;
                        var18 = var35.centerAbsoluteY - renderCameraY;
                        var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                        var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                        if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                          var25 = var24;
                        }
                      }
                    }
                  }

                  if (var25 == -1) {
                    break;
                  }

                  EntityMarker var34 = anEntityMarkerArray1940[var25];
                  var34.anInt452 = renderTick;
                  if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                    var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                  }

                  for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                    for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                      Tile var26 = var8[var16][var17];
                      if (var26.entityMarkerSideRenderConfig != 0) {
                        aNodeDeque1936.add(var26);
                      } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                        aNodeDeque1936.add(var26);
                      }
                    }
                  }
                }

                if (!renderingTile.drawEntityMarkers) {
                  break;
                }
              } catch (Exception var28) {
                renderingTile.drawEntityMarkers = false;
                break;
              }
            }
          } while (!renderingTile.aBoolean1151);
        } while (renderingTile.entityMarkerSideRenderConfig != 0);

        if (var4 > renderSceneX || var4 <= renderAreaMinX) {
          break;
        }

        var9 = var8[var4 - 1][var5];
      }

      if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
        break;
      }

      var9 = var8[var4 + 1][var5];
      while (var9 != null && var9.aBoolean1151) {
        do {
          do {
            do {
              while (true) {
                Boundary var10;
                EntityMarker var12;
                boolean var13;
                int var14;
                int var19;
                int var20;
                Tile var36;
                while (true) {
                  do {
                    renderingTile = aNodeDeque1936.popFirst();
                    if (renderingTile == null) {

                      return;
                    }
                  } while (!renderingTile.aBoolean1151);

                  var4 = renderingTile.sceneX;
                  var5 = renderingTile.sceneY;
                  var6 = renderingTile.floorLevel;
                  var7 = renderingTile.renderLevel;
                  var8 = this.tiles[var6];
                  if (!renderingTile.aBoolean665) {
                    break;
                  }

                  if (var2) {
                    if (var6 > 0) {
                      var9 = this.tiles[var6 - 1][var4][var5];
                      if (var9 != null && var9.aBoolean1151) {
                        continue;
                      }
                    }

                    if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                      var9 = var8[var4 - 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                        continue;
                      }
                    }

                    if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                      var9 = var8[var4 + 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                        continue;
                      }
                    }

                    if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                      var9 = var8[var4][var5 - 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                        continue;
                      }
                    }

                    if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                      var9 = var8[var4][var5 + 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                        continue;
                      }
                    }
                  } else {
                    var2 = true;
                  }

                  renderingTile.aBoolean665 = false;
                  if (renderingTile.underneath != null) {
                    var9 = renderingTile.underneath;
                    if (var9.paint != null) {
                      if (!this.method1464(0, var4, var5)) {
                        this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                      this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var10 = var9.boundary;
                    if (var10 != null) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                      var12 = var9.markers[var11];
                      if (var12 != null) {
                        var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                      }
                    }
                  }

                  var13 = false;
                  if (renderingTile.paint != null) {
                    if (!this.method1464(var7, var4, var5)) {
                      var13 = true;
                      if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                        this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    }
                  } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                    var13 = true;
                    this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }

                  var14 = 0;
                  var11 = 0;
                  Boundary var31 = renderingTile.boundary;
                  BoundaryDecor var15 = renderingTile.boundaryDecor;
                  if (var31 != null || var15 != null) {
                    if (var4 == renderSceneX) {
                      ++var14;
                    } else if (renderSceneX < var4) {
                      var14 += 2;
                    }

                    if (var5 == renderSceneY) {
                      var14 += 3;
                    } else if (renderSceneY > var5) {
                      var14 += 6;
                    }

                    var11 = anIntArray1935[var14];
                    renderingTile.anInt1149 = anIntArray1948[var14];
                  }

                  if (var31 != null) {
                    if ((var31.orientation & anIntArray1937[var14]) != 0) {
                      if (var31.orientation == 16) {
                        renderingTile.entityMarkerSideRenderConfig = 3;
                        renderingTile.anInt563 = anIntArray1945[var14];
                        renderingTile.anInt575 = 3 - renderingTile.anInt563;
                      } else if (var31.orientation == 32) {
                        renderingTile.entityMarkerSideRenderConfig = 6;
                        renderingTile.anInt563 = anIntArray1934[var14];
                        renderingTile.anInt575 = 6 - renderingTile.anInt563;
                      } else if (var31.orientation == 64) {
                        renderingTile.entityMarkerSideRenderConfig = 12;
                        renderingTile.anInt563 = anIntArray1944[var14];
                        renderingTile.anInt575 = 12 - renderingTile.anInt563;
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 9;
                        renderingTile.anInt563 = anIntArray1959[var14];
                        renderingTile.anInt575 = 9 - renderingTile.anInt563;
                      }
                    } else {
                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }

                    if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                      var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }

                    if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                      var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }
                  }

                  if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                    if ((var15.renderConfig & var11) != 0) {
                      var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                    } else if (var15.renderConfig == 256) {
                      var16 = var15.absoluteX * renderCameraX;
                      var17 = var15.height - renderCameraZ;
                      var18 = var15.absoluteY * renderCameraY;
                      var19 = var15.orientation;
                      if (var19 != 1 && var19 != 2) {
                        var20 = var16;
                      } else {
                        var20 = -var16;
                      }

                      int var21;
                      if (var19 != 2 && var19 != 3) {
                        var21 = var18;
                      } else {
                        var21 = -var18;
                      }

                      if (var21 < var20) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                      } else if (var15.linkedEntity != null) {
                        var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                      }
                    }
                  }

                  if (var13) {
                    TileDecor var22 = renderingTile.decor;
                    if (var22 != null) {
                      var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                    }

                    PickableStack var23 = renderingTile.pickableStack;
                    if (var23 != null && var23.height == 0) {
                      if (var23.middle != null) {
                        var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.top != null) {
                        var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.bottom != null) {
                        var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }
                    }
                  }

                  var16 = renderingTile.entityMarkerSideFlags;
                  if (var16 != 0) {
                    if (var4 < renderSceneX && (var16 & 4) != 0) {
                      var36 = var8[var4 + 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 < renderSceneY && (var16 & 2) != 0) {
                      var36 = var8[var4][var5 + 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var4 > renderSceneX && (var16 & 1) != 0) {
                      var36 = var8[var4 - 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 > renderSceneY && (var16 & 8) != 0) {
                      var36 = var8[var4][var5 - 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }
                  }
                  break;
                }

                if (renderingTile.entityMarkerSideRenderConfig != 0) {
                  var13 = true;

                  for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                    if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                      var13 = false;
                      break;
                    }
                  }

                  if (var13) {
                    var10 = renderingTile.boundary;
                    if (!this.method1463(var7, var4, var5, var10.orientation)) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    renderingTile.entityMarkerSideRenderConfig = 0;
                  }
                }

                if (!renderingTile.drawEntityMarkers) {
                  break;
                }

                try {
                  int var33 = renderingTile.entityMarkerCount;
                  renderingTile.drawEntityMarkers = false;
                  var14 = 0;

                  label563:
                  for (var11 = 0; var11 < var33; ++var11) {
                    var12 = renderingTile.markers[var11];
                    if (var12.anInt452 != renderTick) {
                      for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                        for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                          var36 = var8[var24][var16];
                          if (var36.aBoolean665) {
                            renderingTile.drawEntityMarkers = true;
                            continue label563;
                          }

                          if (var36.entityMarkerSideRenderConfig != 0) {
                            var18 = 0;
                            if (var24 > var12.sceneX) {
                              ++var18;
                            }

                            if (var24 < var12.maxSceneX) {
                              var18 += 4;
                            }

                            if (var16 > var12.sceneY) {
                              var18 += 8;
                            }

                            if (var16 < var12.maxSceneY) {
                              var18 += 2;
                            }

                            if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }
                          }
                        }
                      }

                      anEntityMarkerArray1940[var14++] = var12;
                      var24 = renderSceneX - var12.sceneX;
                      var16 = var12.maxSceneX - renderSceneX;
                      if (var16 > var24) {
                        var24 = var16;
                      }

                      var17 = renderSceneY - var12.sceneY;
                      var18 = var12.maxSceneY - renderSceneY;
                      if (var18 > var17) {
                        var12.anInt453 = var24 + var18;
                      } else {
                        var12.anInt453 = var24 + var17;
                      }
                    }
                  }

                  while (var14 > 0) {
                    var11 = -50;
                    var25 = -1;

                    for (var24 = 0; var24 < var14; ++var24) {
                      EntityMarker var35 = anEntityMarkerArray1940[var24];
                      if (var35.anInt452 != renderTick) {
                        if (var35.anInt453 > var11) {
                          var11 = var35.anInt453;
                          var25 = var24;
                        } else if (var11 == var35.anInt453) {
                          var17 = var35.centerAbsoluteX - renderCameraX;
                          var18 = var35.centerAbsoluteY - renderCameraY;
                          var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                          var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                          if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                            var25 = var24;
                          }
                        }
                      }
                    }

                    if (var25 == -1) {
                      break;
                    }

                    EntityMarker var34 = anEntityMarkerArray1940[var25];
                    var34.anInt452 = renderTick;
                    if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                      var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                    }

                    for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                      for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                        Tile var26 = var8[var16][var17];
                        if (var26.entityMarkerSideRenderConfig != 0) {
                          aNodeDeque1936.add(var26);
                        } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                          aNodeDeque1936.add(var26);
                        }
                      }
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }
                } catch (Exception var28) {
                  renderingTile.drawEntityMarkers = false;
                  break;
                }
              }
            } while (!renderingTile.aBoolean1151);
          } while (renderingTile.entityMarkerSideRenderConfig != 0);

          if (var4 > renderSceneX || var4 <= renderAreaMinX) {
            break;
          }

          var9 = var8[var4 - 1][var5];
        } while (var9 != null && var9.aBoolean1151);

        if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
          break;
        }

        var9 = var8[var4 + 1][var5];
      }

      if (var5 > renderSceneY || var5 <= renderAreaMinY) {
        break;
      }

      var9 = var8[var4][var5 - 1];
      while (var9 != null && var9.aBoolean1151) {
        do {
          do {
            do {
              while (true) {
                Boundary var10;
                EntityMarker var12;
                boolean var13;
                int var14;
                int var19;
                int var20;
                Tile var36;
                while (true) {
                  do {
                    renderingTile = aNodeDeque1936.popFirst();
                    if (renderingTile == null) {

                      return;
                    }
                  } while (!renderingTile.aBoolean1151);

                  var4 = renderingTile.sceneX;
                  var5 = renderingTile.sceneY;
                  var6 = renderingTile.floorLevel;
                  var7 = renderingTile.renderLevel;
                  var8 = this.tiles[var6];
                  if (!renderingTile.aBoolean665) {
                    break;
                  }

                  if (var2) {
                    if (var6 > 0) {
                      var9 = this.tiles[var6 - 1][var4][var5];
                      if (var9 != null && var9.aBoolean1151) {
                        continue;
                      }
                    }

                    if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                      var9 = var8[var4 - 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                        continue;
                      }
                    }

                    if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                      var9 = var8[var4 + 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                        continue;
                      }
                    }

                    if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                      var9 = var8[var4][var5 - 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                        continue;
                      }
                    }

                    if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                      var9 = var8[var4][var5 + 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                        continue;
                      }
                    }
                  } else {
                    var2 = true;
                  }

                  renderingTile.aBoolean665 = false;
                  if (renderingTile.underneath != null) {
                    var9 = renderingTile.underneath;
                    if (var9.paint != null) {
                      if (!this.method1464(0, var4, var5)) {
                        this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                      this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var10 = var9.boundary;
                    if (var10 != null) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                      var12 = var9.markers[var11];
                      if (var12 != null) {
                        var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                      }
                    }
                  }

                  var13 = false;
                  if (renderingTile.paint != null) {
                    if (!this.method1464(var7, var4, var5)) {
                      var13 = true;
                      if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                        this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    }
                  } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                    var13 = true;
                    this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }

                  var14 = 0;
                  var11 = 0;
                  Boundary var31 = renderingTile.boundary;
                  BoundaryDecor var15 = renderingTile.boundaryDecor;
                  if (var31 != null || var15 != null) {
                    if (var4 == renderSceneX) {
                      ++var14;
                    } else if (renderSceneX < var4) {
                      var14 += 2;
                    }

                    if (var5 == renderSceneY) {
                      var14 += 3;
                    } else if (renderSceneY > var5) {
                      var14 += 6;
                    }

                    var11 = anIntArray1935[var14];
                    renderingTile.anInt1149 = anIntArray1948[var14];
                  }

                  if (var31 != null) {
                    if ((var31.orientation & anIntArray1937[var14]) != 0) {
                      if (var31.orientation == 16) {
                        renderingTile.entityMarkerSideRenderConfig = 3;
                        renderingTile.anInt563 = anIntArray1945[var14];
                        renderingTile.anInt575 = 3 - renderingTile.anInt563;
                      } else if (var31.orientation == 32) {
                        renderingTile.entityMarkerSideRenderConfig = 6;
                        renderingTile.anInt563 = anIntArray1934[var14];
                        renderingTile.anInt575 = 6 - renderingTile.anInt563;
                      } else if (var31.orientation == 64) {
                        renderingTile.entityMarkerSideRenderConfig = 12;
                        renderingTile.anInt563 = anIntArray1944[var14];
                        renderingTile.anInt575 = 12 - renderingTile.anInt563;
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 9;
                        renderingTile.anInt563 = anIntArray1959[var14];
                        renderingTile.anInt575 = 9 - renderingTile.anInt563;
                      }
                    } else {
                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }

                    if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                      var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }

                    if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                      var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }
                  }

                  if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                    if ((var15.renderConfig & var11) != 0) {
                      var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                    } else if (var15.renderConfig == 256) {
                      var16 = var15.absoluteX * renderCameraX;
                      var17 = var15.height - renderCameraZ;
                      var18 = var15.absoluteY * renderCameraY;
                      var19 = var15.orientation;
                      if (var19 != 1 && var19 != 2) {
                        var20 = var16;
                      } else {
                        var20 = -var16;
                      }

                      int var21;
                      if (var19 != 2 && var19 != 3) {
                        var21 = var18;
                      } else {
                        var21 = -var18;
                      }

                      if (var21 < var20) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                      } else if (var15.linkedEntity != null) {
                        var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                      }
                    }
                  }

                  if (var13) {
                    TileDecor var22 = renderingTile.decor;
                    if (var22 != null) {
                      var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                    }

                    PickableStack var23 = renderingTile.pickableStack;
                    if (var23 != null && var23.height == 0) {
                      if (var23.middle != null) {
                        var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.top != null) {
                        var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.bottom != null) {
                        var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }
                    }
                  }

                  var16 = renderingTile.entityMarkerSideFlags;
                  if (var16 != 0) {
                    if (var4 < renderSceneX && (var16 & 4) != 0) {
                      var36 = var8[var4 + 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 < renderSceneY && (var16 & 2) != 0) {
                      var36 = var8[var4][var5 + 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var4 > renderSceneX && (var16 & 1) != 0) {
                      var36 = var8[var4 - 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 > renderSceneY && (var16 & 8) != 0) {
                      var36 = var8[var4][var5 - 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }
                  }
                  break;
                }

                if (renderingTile.entityMarkerSideRenderConfig != 0) {
                  var13 = true;

                  for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                    if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                      var13 = false;
                      break;
                    }
                  }

                  if (var13) {
                    var10 = renderingTile.boundary;
                    if (!this.method1463(var7, var4, var5, var10.orientation)) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    renderingTile.entityMarkerSideRenderConfig = 0;
                  }
                }

                if (!renderingTile.drawEntityMarkers) {
                  break;
                }

                try {
                  int var33 = renderingTile.entityMarkerCount;
                  renderingTile.drawEntityMarkers = false;
                  var14 = 0;

                  label563:
                  for (var11 = 0; var11 < var33; ++var11) {
                    var12 = renderingTile.markers[var11];
                    if (var12.anInt452 != renderTick) {
                      for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                        for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                          var36 = var8[var24][var16];
                          if (var36.aBoolean665) {
                            renderingTile.drawEntityMarkers = true;
                            continue label563;
                          }

                          if (var36.entityMarkerSideRenderConfig != 0) {
                            var18 = 0;
                            if (var24 > var12.sceneX) {
                              ++var18;
                            }

                            if (var24 < var12.maxSceneX) {
                              var18 += 4;
                            }

                            if (var16 > var12.sceneY) {
                              var18 += 8;
                            }

                            if (var16 < var12.maxSceneY) {
                              var18 += 2;
                            }

                            if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }
                          }
                        }
                      }

                      anEntityMarkerArray1940[var14++] = var12;
                      var24 = renderSceneX - var12.sceneX;
                      var16 = var12.maxSceneX - renderSceneX;
                      if (var16 > var24) {
                        var24 = var16;
                      }

                      var17 = renderSceneY - var12.sceneY;
                      var18 = var12.maxSceneY - renderSceneY;
                      if (var18 > var17) {
                        var12.anInt453 = var24 + var18;
                      } else {
                        var12.anInt453 = var24 + var17;
                      }
                    }
                  }

                  while (var14 > 0) {
                    var11 = -50;
                    var25 = -1;

                    for (var24 = 0; var24 < var14; ++var24) {
                      EntityMarker var35 = anEntityMarkerArray1940[var24];
                      if (var35.anInt452 != renderTick) {
                        if (var35.anInt453 > var11) {
                          var11 = var35.anInt453;
                          var25 = var24;
                        } else if (var11 == var35.anInt453) {
                          var17 = var35.centerAbsoluteX - renderCameraX;
                          var18 = var35.centerAbsoluteY - renderCameraY;
                          var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                          var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                          if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                            var25 = var24;
                          }
                        }
                      }
                    }

                    if (var25 == -1) {
                      break;
                    }

                    EntityMarker var34 = anEntityMarkerArray1940[var25];
                    var34.anInt452 = renderTick;
                    if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                      var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                    }

                    for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                      for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                        Tile var26 = var8[var16][var17];
                        if (var26.entityMarkerSideRenderConfig != 0) {
                          aNodeDeque1936.add(var26);
                        } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                          aNodeDeque1936.add(var26);
                        }
                      }
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }
                } catch (Exception var28) {
                  renderingTile.drawEntityMarkers = false;
                  break;
                }
              }
            } while (!renderingTile.aBoolean1151);
          } while (renderingTile.entityMarkerSideRenderConfig != 0);

          if (var4 > renderSceneX || var4 <= renderAreaMinX) {
            break;
          }

          var9 = var8[var4 - 1][var5];
        } while (var9 != null && var9.aBoolean1151);

        if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
          break;
        }

        var9 = var8[var4 + 1][var5];
        while (var9 != null && var9.aBoolean1151) {
          do {
            do {
              while (true) {
                Boundary var10;
                EntityMarker var12;
                boolean var13;
                int var14;
                int var19;
                int var20;
                Tile var36;
                while (true) {
                  do {
                    renderingTile = aNodeDeque1936.popFirst();
                    if (renderingTile == null) {

                      return;
                    }
                  } while (!renderingTile.aBoolean1151);

                  var4 = renderingTile.sceneX;
                  var5 = renderingTile.sceneY;
                  var6 = renderingTile.floorLevel;
                  var7 = renderingTile.renderLevel;
                  var8 = this.tiles[var6];
                  if (!renderingTile.aBoolean665) {
                    break;
                  }

                  if (var2) {
                    if (var6 > 0) {
                      var9 = this.tiles[var6 - 1][var4][var5];
                      if (var9 != null && var9.aBoolean1151) {
                        continue;
                      }
                    }

                    if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                      var9 = var8[var4 - 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                        continue;
                      }
                    }

                    if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                      var9 = var8[var4 + 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                        continue;
                      }
                    }

                    if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                      var9 = var8[var4][var5 - 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                        continue;
                      }
                    }

                    if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                      var9 = var8[var4][var5 + 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                        continue;
                      }
                    }
                  } else {
                    var2 = true;
                  }

                  renderingTile.aBoolean665 = false;
                  if (renderingTile.underneath != null) {
                    var9 = renderingTile.underneath;
                    if (var9.paint != null) {
                      if (!this.method1464(0, var4, var5)) {
                        this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                      this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var10 = var9.boundary;
                    if (var10 != null) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                      var12 = var9.markers[var11];
                      if (var12 != null) {
                        var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                      }
                    }
                  }

                  var13 = false;
                  if (renderingTile.paint != null) {
                    if (!this.method1464(var7, var4, var5)) {
                      var13 = true;
                      if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                        this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    }
                  } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                    var13 = true;
                    this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }

                  var14 = 0;
                  var11 = 0;
                  Boundary var31 = renderingTile.boundary;
                  BoundaryDecor var15 = renderingTile.boundaryDecor;
                  if (var31 != null || var15 != null) {
                    if (var4 == renderSceneX) {
                      ++var14;
                    } else if (renderSceneX < var4) {
                      var14 += 2;
                    }

                    if (var5 == renderSceneY) {
                      var14 += 3;
                    } else if (renderSceneY > var5) {
                      var14 += 6;
                    }

                    var11 = anIntArray1935[var14];
                    renderingTile.anInt1149 = anIntArray1948[var14];
                  }

                  if (var31 != null) {
                    if ((var31.orientation & anIntArray1937[var14]) != 0) {
                      if (var31.orientation == 16) {
                        renderingTile.entityMarkerSideRenderConfig = 3;
                        renderingTile.anInt563 = anIntArray1945[var14];
                        renderingTile.anInt575 = 3 - renderingTile.anInt563;
                      } else if (var31.orientation == 32) {
                        renderingTile.entityMarkerSideRenderConfig = 6;
                        renderingTile.anInt563 = anIntArray1934[var14];
                        renderingTile.anInt575 = 6 - renderingTile.anInt563;
                      } else if (var31.orientation == 64) {
                        renderingTile.entityMarkerSideRenderConfig = 12;
                        renderingTile.anInt563 = anIntArray1944[var14];
                        renderingTile.anInt575 = 12 - renderingTile.anInt563;
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 9;
                        renderingTile.anInt563 = anIntArray1959[var14];
                        renderingTile.anInt575 = 9 - renderingTile.anInt563;
                      }
                    } else {
                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }

                    if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                      var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }

                    if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                      var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }
                  }

                  if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                    if ((var15.renderConfig & var11) != 0) {
                      var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                    } else if (var15.renderConfig == 256) {
                      var16 = var15.absoluteX * renderCameraX;
                      var17 = var15.height - renderCameraZ;
                      var18 = var15.absoluteY * renderCameraY;
                      var19 = var15.orientation;
                      if (var19 != 1 && var19 != 2) {
                        var20 = var16;
                      } else {
                        var20 = -var16;
                      }

                      int var21;
                      if (var19 != 2 && var19 != 3) {
                        var21 = var18;
                      } else {
                        var21 = -var18;
                      }

                      if (var21 < var20) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                      } else if (var15.linkedEntity != null) {
                        var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                      }
                    }
                  }

                  if (var13) {
                    TileDecor var22 = renderingTile.decor;
                    if (var22 != null) {
                      var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                    }

                    PickableStack var23 = renderingTile.pickableStack;
                    if (var23 != null && var23.height == 0) {
                      if (var23.middle != null) {
                        var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.top != null) {
                        var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.bottom != null) {
                        var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }
                    }
                  }

                  var16 = renderingTile.entityMarkerSideFlags;
                  if (var16 != 0) {
                    if (var4 < renderSceneX && (var16 & 4) != 0) {
                      var36 = var8[var4 + 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 < renderSceneY && (var16 & 2) != 0) {
                      var36 = var8[var4][var5 + 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var4 > renderSceneX && (var16 & 1) != 0) {
                      var36 = var8[var4 - 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 > renderSceneY && (var16 & 8) != 0) {
                      var36 = var8[var4][var5 - 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }
                  }
                  break;
                }

                if (renderingTile.entityMarkerSideRenderConfig != 0) {
                  var13 = true;

                  for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                    if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                      var13 = false;
                      break;
                    }
                  }

                  if (var13) {
                    var10 = renderingTile.boundary;
                    if (!this.method1463(var7, var4, var5, var10.orientation)) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    renderingTile.entityMarkerSideRenderConfig = 0;
                  }
                }

                if (!renderingTile.drawEntityMarkers) {
                  break;
                }

                try {
                  int var33 = renderingTile.entityMarkerCount;
                  renderingTile.drawEntityMarkers = false;
                  var14 = 0;

                  label563:
                  for (var11 = 0; var11 < var33; ++var11) {
                    var12 = renderingTile.markers[var11];
                    if (var12.anInt452 != renderTick) {
                      for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                        for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                          var36 = var8[var24][var16];
                          if (var36.aBoolean665) {
                            renderingTile.drawEntityMarkers = true;
                            continue label563;
                          }

                          if (var36.entityMarkerSideRenderConfig != 0) {
                            var18 = 0;
                            if (var24 > var12.sceneX) {
                              ++var18;
                            }

                            if (var24 < var12.maxSceneX) {
                              var18 += 4;
                            }

                            if (var16 > var12.sceneY) {
                              var18 += 8;
                            }

                            if (var16 < var12.maxSceneY) {
                              var18 += 2;
                            }

                            if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }
                          }
                        }
                      }

                      anEntityMarkerArray1940[var14++] = var12;
                      var24 = renderSceneX - var12.sceneX;
                      var16 = var12.maxSceneX - renderSceneX;
                      if (var16 > var24) {
                        var24 = var16;
                      }

                      var17 = renderSceneY - var12.sceneY;
                      var18 = var12.maxSceneY - renderSceneY;
                      if (var18 > var17) {
                        var12.anInt453 = var24 + var18;
                      } else {
                        var12.anInt453 = var24 + var17;
                      }
                    }
                  }

                  while (var14 > 0) {
                    var11 = -50;
                    var25 = -1;

                    for (var24 = 0; var24 < var14; ++var24) {
                      EntityMarker var35 = anEntityMarkerArray1940[var24];
                      if (var35.anInt452 != renderTick) {
                        if (var35.anInt453 > var11) {
                          var11 = var35.anInt453;
                          var25 = var24;
                        } else if (var11 == var35.anInt453) {
                          var17 = var35.centerAbsoluteX - renderCameraX;
                          var18 = var35.centerAbsoluteY - renderCameraY;
                          var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                          var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                          if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                            var25 = var24;
                          }
                        }
                      }
                    }

                    if (var25 == -1) {
                      break;
                    }

                    EntityMarker var34 = anEntityMarkerArray1940[var25];
                    var34.anInt452 = renderTick;
                    if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                      var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                    }

                    for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                      for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                        Tile var26 = var8[var16][var17];
                        if (var26.entityMarkerSideRenderConfig != 0) {
                          aNodeDeque1936.add(var26);
                        } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                          aNodeDeque1936.add(var26);
                        }
                      }
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }
                } catch (Exception var28) {
                  renderingTile.drawEntityMarkers = false;
                  break;
                }
              }
            } while (!renderingTile.aBoolean1151);
          } while (renderingTile.entityMarkerSideRenderConfig != 0);

          if (var4 > renderSceneX || var4 <= renderAreaMinX) {
            break;
          }

          var9 = var8[var4 - 1][var5];
          while (var9 != null && var9.aBoolean1151) {
            do {
              while (true) {
                Boundary var10;
                EntityMarker var12;
                boolean var13;
                int var14;
                int var19;
                int var20;
                Tile var36;
                while (true) {
                  do {
                    renderingTile = aNodeDeque1936.popFirst();
                    if (renderingTile == null) {

                      return;
                    }
                  } while (!renderingTile.aBoolean1151);

                  var4 = renderingTile.sceneX;
                  var5 = renderingTile.sceneY;
                  var6 = renderingTile.floorLevel;
                  var7 = renderingTile.renderLevel;
                  var8 = this.tiles[var6];
                  if (!renderingTile.aBoolean665) {
                    break;
                  }

                  if (var2) {
                    if (var6 > 0) {
                      var9 = this.tiles[var6 - 1][var4][var5];
                      if (var9 != null && var9.aBoolean1151) {
                        continue;
                      }
                    }

                    if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                      var9 = var8[var4 - 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                        continue;
                      }
                    }

                    if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                      var9 = var8[var4 + 1][var5];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                        continue;
                      }
                    }

                    if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                      var9 = var8[var4][var5 - 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                        continue;
                      }
                    }

                    if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                      var9 = var8[var4][var5 + 1];
                      if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                        continue;
                      }
                    }
                  } else {
                    var2 = true;
                  }

                  renderingTile.aBoolean665 = false;
                  if (renderingTile.underneath != null) {
                    var9 = renderingTile.underneath;
                    if (var9.paint != null) {
                      if (!this.method1464(0, var4, var5)) {
                        this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                      this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var10 = var9.boundary;
                    if (var10 != null) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                      var12 = var9.markers[var11];
                      if (var12 != null) {
                        var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                      }
                    }
                  }

                  var13 = false;
                  if (renderingTile.paint != null) {
                    if (!this.method1464(var7, var4, var5)) {
                      var13 = true;
                      if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                        this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }
                    }
                  } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                    var13 = true;
                    this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                  }

                  var14 = 0;
                  var11 = 0;
                  Boundary var31 = renderingTile.boundary;
                  BoundaryDecor var15 = renderingTile.boundaryDecor;
                  if (var31 != null || var15 != null) {
                    if (var4 == renderSceneX) {
                      ++var14;
                    } else if (renderSceneX < var4) {
                      var14 += 2;
                    }

                    if (var5 == renderSceneY) {
                      var14 += 3;
                    } else if (renderSceneY > var5) {
                      var14 += 6;
                    }

                    var11 = anIntArray1935[var14];
                    renderingTile.anInt1149 = anIntArray1948[var14];
                  }

                  if (var31 != null) {
                    if ((var31.orientation & anIntArray1937[var14]) != 0) {
                      if (var31.orientation == 16) {
                        renderingTile.entityMarkerSideRenderConfig = 3;
                        renderingTile.anInt563 = anIntArray1945[var14];
                        renderingTile.anInt575 = 3 - renderingTile.anInt563;
                      } else if (var31.orientation == 32) {
                        renderingTile.entityMarkerSideRenderConfig = 6;
                        renderingTile.anInt563 = anIntArray1934[var14];
                        renderingTile.anInt575 = 6 - renderingTile.anInt563;
                      } else if (var31.orientation == 64) {
                        renderingTile.entityMarkerSideRenderConfig = 12;
                        renderingTile.anInt563 = anIntArray1944[var14];
                        renderingTile.anInt575 = 12 - renderingTile.anInt563;
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 9;
                        renderingTile.anInt563 = anIntArray1959[var14];
                        renderingTile.anInt575 = 9 - renderingTile.anInt563;
                      }
                    } else {
                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }

                    if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                      var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }

                    if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                      var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                    }
                  }

                  if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                    if ((var15.renderConfig & var11) != 0) {
                      var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                    } else if (var15.renderConfig == 256) {
                      var16 = var15.absoluteX * renderCameraX;
                      var17 = var15.height - renderCameraZ;
                      var18 = var15.absoluteY * renderCameraY;
                      var19 = var15.orientation;
                      if (var19 != 1 && var19 != 2) {
                        var20 = var16;
                      } else {
                        var20 = -var16;
                      }

                      int var21;
                      if (var19 != 2 && var19 != 3) {
                        var21 = var18;
                      } else {
                        var21 = -var18;
                      }

                      if (var21 < var20) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                      } else if (var15.linkedEntity != null) {
                        var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                      }
                    }
                  }

                  if (var13) {
                    TileDecor var22 = renderingTile.decor;
                    if (var22 != null) {
                      var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                    }

                    PickableStack var23 = renderingTile.pickableStack;
                    if (var23 != null && var23.height == 0) {
                      if (var23.middle != null) {
                        var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.top != null) {
                        var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }

                      if (var23.bottom != null) {
                        var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                      }
                    }
                  }

                  var16 = renderingTile.entityMarkerSideFlags;
                  if (var16 != 0) {
                    if (var4 < renderSceneX && (var16 & 4) != 0) {
                      var36 = var8[var4 + 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 < renderSceneY && (var16 & 2) != 0) {
                      var36 = var8[var4][var5 + 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var4 > renderSceneX && (var16 & 1) != 0) {
                      var36 = var8[var4 - 1][var5];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }

                    if (var5 > renderSceneY && (var16 & 8) != 0) {
                      var36 = var8[var4][var5 - 1];
                      if (var36 != null && var36.aBoolean1151) {
                        aNodeDeque1936.add(var36);
                      }
                    }
                  }
                  break;
                }

                if (renderingTile.entityMarkerSideRenderConfig != 0) {
                  var13 = true;

                  for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                    if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                      var13 = false;
                      break;
                    }
                  }

                  if (var13) {
                    var10 = renderingTile.boundary;
                    if (!this.method1463(var7, var4, var5, var10.orientation)) {
                      var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                    }

                    renderingTile.entityMarkerSideRenderConfig = 0;
                  }
                }

                if (!renderingTile.drawEntityMarkers) {
                  break;
                }

                try {
                  int var33 = renderingTile.entityMarkerCount;
                  renderingTile.drawEntityMarkers = false;
                  var14 = 0;

                  label563:
                  for (var11 = 0; var11 < var33; ++var11) {
                    var12 = renderingTile.markers[var11];
                    if (var12.anInt452 != renderTick) {
                      for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                        for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                          var36 = var8[var24][var16];
                          if (var36.aBoolean665) {
                            renderingTile.drawEntityMarkers = true;
                            continue label563;
                          }

                          if (var36.entityMarkerSideRenderConfig != 0) {
                            var18 = 0;
                            if (var24 > var12.sceneX) {
                              ++var18;
                            }

                            if (var24 < var12.maxSceneX) {
                              var18 += 4;
                            }

                            if (var16 > var12.sceneY) {
                              var18 += 8;
                            }

                            if (var16 < var12.maxSceneY) {
                              var18 += 2;
                            }

                            if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }
                          }
                        }
                      }

                      anEntityMarkerArray1940[var14++] = var12;
                      var24 = renderSceneX - var12.sceneX;
                      var16 = var12.maxSceneX - renderSceneX;
                      if (var16 > var24) {
                        var24 = var16;
                      }

                      var17 = renderSceneY - var12.sceneY;
                      var18 = var12.maxSceneY - renderSceneY;
                      if (var18 > var17) {
                        var12.anInt453 = var24 + var18;
                      } else {
                        var12.anInt453 = var24 + var17;
                      }
                    }
                  }

                  while (var14 > 0) {
                    var11 = -50;
                    var25 = -1;

                    for (var24 = 0; var24 < var14; ++var24) {
                      EntityMarker var35 = anEntityMarkerArray1940[var24];
                      if (var35.anInt452 != renderTick) {
                        if (var35.anInt453 > var11) {
                          var11 = var35.anInt453;
                          var25 = var24;
                        } else if (var11 == var35.anInt453) {
                          var17 = var35.centerAbsoluteX - renderCameraX;
                          var18 = var35.centerAbsoluteY - renderCameraY;
                          var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                          var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                          if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                            var25 = var24;
                          }
                        }
                      }
                    }

                    if (var25 == -1) {
                      break;
                    }

                    EntityMarker var34 = anEntityMarkerArray1940[var25];
                    var34.anInt452 = renderTick;
                    if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                      var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                    }

                    for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                      for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                        Tile var26 = var8[var16][var17];
                        if (var26.entityMarkerSideRenderConfig != 0) {
                          aNodeDeque1936.add(var26);
                        } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                          aNodeDeque1936.add(var26);
                        }
                      }
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }
                } catch (Exception var28) {
                  renderingTile.drawEntityMarkers = false;
                  break;
                }
              }
              while (!renderingTile.aBoolean1151) {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
              }
            } while (renderingTile.entityMarkerSideRenderConfig != 0);

            if (var4 > renderSceneX || var4 <= renderAreaMinX) {
              break;
            }

            var9 = var8[var4 - 1][var5];
          }

          if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
            break;
          }

          var9 = var8[var4 + 1][var5];
        }

        if (var5 > renderSceneY || var5 <= renderAreaMinY) {
          break;
        }

        var9 = var8[var4][var5 - 1];
      }

      if (var5 < renderSceneY || var5 >= renderAreaMaxY - 1) {
        break;
      }

      var9 = var8[var4][var5 + 1];
      while (var9 != null && var9.aBoolean1151) {
        do {
          do {
            do {
              do {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
              } while (!renderingTile.aBoolean1151);
            } while (renderingTile.entityMarkerSideRenderConfig != 0);

            if (var4 > renderSceneX || var4 <= renderAreaMinX) {
              break;
            }

            var9 = var8[var4 - 1][var5];
          } while (var9 != null && var9.aBoolean1151);

          if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
            break;
          }

          var9 = var8[var4 + 1][var5];
        } while (var9 != null && var9.aBoolean1151);

        if (var5 > renderSceneY || var5 <= renderAreaMinY) {
          break;
        }

        var9 = var8[var4][var5 - 1];
        while (var9 != null && var9.aBoolean1151) {
          do {
            do {
              do {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
              } while (!renderingTile.aBoolean1151);
            } while (renderingTile.entityMarkerSideRenderConfig != 0);

            if (var4 > renderSceneX || var4 <= renderAreaMinX) {
              break;
            }

            var9 = var8[var4 - 1][var5];
          } while (var9 != null && var9.aBoolean1151);

          if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
            break;
          }

          var9 = var8[var4 + 1][var5];
          while (var9 != null && var9.aBoolean1151) {
            do {
              do {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
              } while (!renderingTile.aBoolean1151);
            } while (renderingTile.entityMarkerSideRenderConfig != 0);

            if (var4 > renderSceneX || var4 <= renderAreaMinX) {
              break;
            }

            var9 = var8[var4 - 1][var5];
            while (var9 != null && var9.aBoolean1151) {
              do {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
              } while (!renderingTile.aBoolean1151);
              while (renderingTile.entityMarkerSideRenderConfig != 0) {
                while (true) {
                  Boundary var10;
                  EntityMarker var12;
                  boolean var13;
                  int var14;
                  int var19;
                  int var20;
                  Tile var36;
                  while (true) {
                    do {
                      renderingTile = aNodeDeque1936.popFirst();
                      if (renderingTile == null) {

                        return;
                      }
                    } while (!renderingTile.aBoolean1151);

                    var4 = renderingTile.sceneX;
                    var5 = renderingTile.sceneY;
                    var6 = renderingTile.floorLevel;
                    var7 = renderingTile.renderLevel;
                    var8 = this.tiles[var6];
                    if (!renderingTile.aBoolean665) {
                      break;
                    }

                    if (var2) {
                      if (var6 > 0) {
                        var9 = this.tiles[var6 - 1][var4][var5];
                        if (var9 != null && var9.aBoolean1151) {
                          continue;
                        }
                      }

                      if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                        var9 = var8[var4 - 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                          continue;
                        }
                      }

                      if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                        var9 = var8[var4 + 1][var5];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                          continue;
                        }
                      }

                      if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                        var9 = var8[var4][var5 - 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                          continue;
                        }
                      }

                      if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                        var9 = var8[var4][var5 + 1];
                        if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                          continue;
                        }
                      }
                    } else {
                      var2 = true;
                    }

                    renderingTile.aBoolean665 = false;
                    if (renderingTile.underneath != null) {
                      var9 = renderingTile.underneath;
                      if (var9.paint != null) {
                        if (!this.method1464(0, var4, var5)) {
                          this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                        this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var10 = var9.boundary;
                      if (var10 != null) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                        var12 = var9.markers[var11];
                        if (var12 != null) {
                          var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                        }
                      }
                    }

                    var13 = false;
                    if (renderingTile.paint != null) {
                      if (!this.method1464(var7, var4, var5)) {
                        var13 = true;
                        if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                          this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }
                      }
                    } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                      var13 = true;
                      this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                    }

                    var14 = 0;
                    var11 = 0;
                    Boundary var31 = renderingTile.boundary;
                    BoundaryDecor var15 = renderingTile.boundaryDecor;
                    if (var31 != null || var15 != null) {
                      if (var4 == renderSceneX) {
                        ++var14;
                      } else if (renderSceneX < var4) {
                        var14 += 2;
                      }

                      if (var5 == renderSceneY) {
                        var14 += 3;
                      } else if (renderSceneY > var5) {
                        var14 += 6;
                      }

                      var11 = anIntArray1935[var14];
                      renderingTile.anInt1149 = anIntArray1948[var14];
                    }

                    if (var31 != null) {
                      if ((var31.orientation & anIntArray1937[var14]) != 0) {
                        if (var31.orientation == 16) {
                          renderingTile.entityMarkerSideRenderConfig = 3;
                          renderingTile.anInt563 = anIntArray1945[var14];
                          renderingTile.anInt575 = 3 - renderingTile.anInt563;
                        } else if (var31.orientation == 32) {
                          renderingTile.entityMarkerSideRenderConfig = 6;
                          renderingTile.anInt563 = anIntArray1934[var14];
                          renderingTile.anInt575 = 6 - renderingTile.anInt563;
                        } else if (var31.orientation == 64) {
                          renderingTile.entityMarkerSideRenderConfig = 12;
                          renderingTile.anInt563 = anIntArray1944[var14];
                          renderingTile.anInt575 = 12 - renderingTile.anInt563;
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 9;
                          renderingTile.anInt563 = anIntArray1959[var14];
                          renderingTile.anInt575 = 9 - renderingTile.anInt563;
                        }
                      } else {
                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }

                      if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                        var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }

                      if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                        var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                      }
                    }

                    if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                      if ((var15.renderConfig & var11) != 0) {
                        var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                      } else if (var15.renderConfig == 256) {
                        var16 = var15.absoluteX * renderCameraX;
                        var17 = var15.height - renderCameraZ;
                        var18 = var15.absoluteY * renderCameraY;
                        var19 = var15.orientation;
                        if (var19 != 1 && var19 != 2) {
                          var20 = var16;
                        } else {
                          var20 = -var16;
                        }

                        int var21;
                        if (var19 != 2 && var19 != 3) {
                          var21 = var18;
                        } else {
                          var21 = -var18;
                        }

                        if (var21 < var20) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                        } else if (var15.linkedEntity != null) {
                          var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                        }
                      }
                    }

                    if (var13) {
                      TileDecor var22 = renderingTile.decor;
                      if (var22 != null) {
                        var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                      }

                      PickableStack var23 = renderingTile.pickableStack;
                      if (var23 != null && var23.height == 0) {
                        if (var23.middle != null) {
                          var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.top != null) {
                          var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }

                        if (var23.bottom != null) {
                          var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                        }
                      }
                    }

                    var16 = renderingTile.entityMarkerSideFlags;
                    if (var16 != 0) {
                      if (var4 < renderSceneX && (var16 & 4) != 0) {
                        var36 = var8[var4 + 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 < renderSceneY && (var16 & 2) != 0) {
                        var36 = var8[var4][var5 + 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var4 > renderSceneX && (var16 & 1) != 0) {
                        var36 = var8[var4 - 1][var5];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }

                      if (var5 > renderSceneY && (var16 & 8) != 0) {
                        var36 = var8[var4][var5 - 1];
                        if (var36 != null && var36.aBoolean1151) {
                          aNodeDeque1936.add(var36);
                        }
                      }
                    }
                    break;
                  }

                  if (renderingTile.entityMarkerSideRenderConfig != 0) {
                    var13 = true;

                    for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                      if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                        var13 = false;
                        break;
                      }
                    }

                    if (var13) {
                      var10 = renderingTile.boundary;
                      if (!this.method1463(var7, var4, var5, var10.orientation)) {
                        var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                      }

                      renderingTile.entityMarkerSideRenderConfig = 0;
                    }
                  }

                  if (!renderingTile.drawEntityMarkers) {
                    break;
                  }

                  try {
                    int var33 = renderingTile.entityMarkerCount;
                    renderingTile.drawEntityMarkers = false;
                    var14 = 0;

                    label563:
                    for (var11 = 0; var11 < var33; ++var11) {
                      var12 = renderingTile.markers[var11];
                      if (var12.anInt452 != renderTick) {
                        for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                          for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                            var36 = var8[var24][var16];
                            if (var36.aBoolean665) {
                              renderingTile.drawEntityMarkers = true;
                              continue label563;
                            }

                            if (var36.entityMarkerSideRenderConfig != 0) {
                              var18 = 0;
                              if (var24 > var12.sceneX) {
                                ++var18;
                              }

                              if (var24 < var12.maxSceneX) {
                                var18 += 4;
                              }

                              if (var16 > var12.sceneY) {
                                var18 += 8;
                              }

                              if (var16 < var12.maxSceneY) {
                                var18 += 2;
                              }

                              if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }
                            }
                          }
                        }

                        anEntityMarkerArray1940[var14++] = var12;
                        var24 = renderSceneX - var12.sceneX;
                        var16 = var12.maxSceneX - renderSceneX;
                        if (var16 > var24) {
                          var24 = var16;
                        }

                        var17 = renderSceneY - var12.sceneY;
                        var18 = var12.maxSceneY - renderSceneY;
                        if (var18 > var17) {
                          var12.anInt453 = var24 + var18;
                        } else {
                          var12.anInt453 = var24 + var17;
                        }
                      }
                    }

                    while (var14 > 0) {
                      var11 = -50;
                      var25 = -1;

                      for (var24 = 0; var24 < var14; ++var24) {
                        EntityMarker var35 = anEntityMarkerArray1940[var24];
                        if (var35.anInt452 != renderTick) {
                          if (var35.anInt453 > var11) {
                            var11 = var35.anInt453;
                            var25 = var24;
                          } else if (var11 == var35.anInt453) {
                            var17 = var35.centerAbsoluteX - renderCameraX;
                            var18 = var35.centerAbsoluteY - renderCameraY;
                            var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                            var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                            if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                              var25 = var24;
                            }
                          }
                        }
                      }

                      if (var25 == -1) {
                        break;
                      }

                      EntityMarker var34 = anEntityMarkerArray1940[var25];
                      var34.anInt452 = renderTick;
                      if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                        var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                      }

                      for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                        for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                          Tile var26 = var8[var16][var17];
                          if (var26.entityMarkerSideRenderConfig != 0) {
                            aNodeDeque1936.add(var26);
                          } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                            aNodeDeque1936.add(var26);
                          }
                        }
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }
                  } catch (Exception var28) {
                    renderingTile.drawEntityMarkers = false;
                    break;
                  }
                }
                while (!renderingTile.aBoolean1151) {
                  while (true) {
                    Boundary var10;
                    EntityMarker var12;
                    boolean var13;
                    int var14;
                    int var19;
                    int var20;
                    Tile var36;
                    while (true) {
                      do {
                        renderingTile = aNodeDeque1936.popFirst();
                        if (renderingTile == null) {

                          return;
                        }
                      } while (!renderingTile.aBoolean1151);

                      var4 = renderingTile.sceneX;
                      var5 = renderingTile.sceneY;
                      var6 = renderingTile.floorLevel;
                      var7 = renderingTile.renderLevel;
                      var8 = this.tiles[var6];
                      if (!renderingTile.aBoolean665) {
                        break;
                      }

                      if (var2) {
                        if (var6 > 0) {
                          var9 = this.tiles[var6 - 1][var4][var5];
                          if (var9 != null && var9.aBoolean1151) {
                            continue;
                          }
                        }

                        if (var4 <= renderSceneX && var4 > renderAreaMinX) {
                          var9 = var8[var4 - 1][var5];
                          if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 1) == 0)) {
                            continue;
                          }
                        }

                        if (var4 >= renderSceneX && var4 < renderAreaMaxX - 1) {
                          var9 = var8[var4 + 1][var5];
                          if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 4) == 0)) {
                            continue;
                          }
                        }

                        if (var5 <= renderSceneY && var5 > renderAreaMinY) {
                          var9 = var8[var4][var5 - 1];
                          if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 8) == 0)) {
                            continue;
                          }
                        }

                        if (var5 >= renderSceneY && var5 < renderAreaMaxY - 1) {
                          var9 = var8[var4][var5 + 1];
                          if (var9 != null && var9.aBoolean1151 && (var9.aBoolean665 || (renderingTile.entityMarkerSideFlags & 2) == 0)) {
                            continue;
                          }
                        }
                      } else {
                        var2 = true;
                      }

                      renderingTile.aBoolean665 = false;
                      if (renderingTile.underneath != null) {
                        var9 = renderingTile.underneath;
                        if (var9.paint != null) {
                          if (!this.method1464(0, var4, var5)) {
                            this.renderTilePaint(var9.paint, 0, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                          }
                        } else if (var9.model != null && !this.method1464(0, var4, var5)) {
                          this.renderTileModel(var9.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                        }

                        var10 = var9.boundary;
                        if (var10 != null) {
                          var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                        }

                        for (var11 = 0; var11 < var9.entityMarkerCount; ++var11) {
                          var12 = var9.markers[var11];
                          if (var12 != null) {
                            var12.entity.render(var12.orientation, pitchSin, pitchCos, yawSin, yawCos, var12.centerAbsoluteX - renderCameraX, var12.height - renderCameraZ, var12.centerAbsoluteY - renderCameraY, var12.uid);
                          }
                        }
                      }

                      var13 = false;
                      if (renderingTile.paint != null) {
                        if (!this.method1464(var7, var4, var5)) {
                          var13 = true;
                          if (renderingTile.paint.anInt2001 != 12345678 || acceptClick && var6 <= anInt1931) {
                            this.renderTilePaint(renderingTile.paint, var7, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                          }
                        }
                      } else if (renderingTile.model != null && !this.method1464(var7, var4, var5)) {
                        var13 = true;
                        this.renderTileModel(renderingTile.model, pitchSin, pitchCos, yawSin, yawCos, var4, var5);
                      }

                      var14 = 0;
                      var11 = 0;
                      Boundary var31 = renderingTile.boundary;
                      BoundaryDecor var15 = renderingTile.boundaryDecor;
                      if (var31 != null || var15 != null) {
                        if (var4 == renderSceneX) {
                          ++var14;
                        } else if (renderSceneX < var4) {
                          var14 += 2;
                        }

                        if (var5 == renderSceneY) {
                          var14 += 3;
                        } else if (renderSceneY > var5) {
                          var14 += 6;
                        }

                        var11 = anIntArray1935[var14];
                        renderingTile.anInt1149 = anIntArray1948[var14];
                      }

                      if (var31 != null) {
                        if ((var31.orientation & anIntArray1937[var14]) != 0) {
                          if (var31.orientation == 16) {
                            renderingTile.entityMarkerSideRenderConfig = 3;
                            renderingTile.anInt563 = anIntArray1945[var14];
                            renderingTile.anInt575 = 3 - renderingTile.anInt563;
                          } else if (var31.orientation == 32) {
                            renderingTile.entityMarkerSideRenderConfig = 6;
                            renderingTile.anInt563 = anIntArray1934[var14];
                            renderingTile.anInt575 = 6 - renderingTile.anInt563;
                          } else if (var31.orientation == 64) {
                            renderingTile.entityMarkerSideRenderConfig = 12;
                            renderingTile.anInt563 = anIntArray1944[var14];
                            renderingTile.anInt575 = 12 - renderingTile.anInt563;
                          } else {
                            renderingTile.entityMarkerSideRenderConfig = 9;
                            renderingTile.anInt563 = anIntArray1959[var14];
                            renderingTile.anInt575 = 9 - renderingTile.anInt563;
                          }
                        } else {
                          renderingTile.entityMarkerSideRenderConfig = 0;
                        }

                        if ((var31.orientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.orientation)) {
                          var31.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                        }

                        if ((var31.linkedOrientation & var11) != 0 && !this.method1463(var7, var4, var5, var31.linkedOrientation)) {
                          var31.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var31.absoluteX * renderCameraX, var31.height - renderCameraZ, var31.absoluteY * renderCameraY, var31.uid);
                        }
                      }

                      if (var15 != null && !this.method1466(var7, var4, var5, var15.entity.height)) {
                        if ((var15.renderConfig & var11) != 0) {
                          var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var15.offsetX + (var15.absoluteX * renderCameraX), var15.height - renderCameraZ, var15.offsetY + (var15.absoluteY * renderCameraY), var15.uid);
                        } else if (var15.renderConfig == 256) {
                          var16 = var15.absoluteX * renderCameraX;
                          var17 = var15.height - renderCameraZ;
                          var18 = var15.absoluteY * renderCameraY;
                          var19 = var15.orientation;
                          if (var19 != 1 && var19 != 2) {
                            var20 = var16;
                          } else {
                            var20 = -var16;
                          }

                          int var21;
                          if (var19 != 2 && var19 != 3) {
                            var21 = var18;
                          } else {
                            var21 = -var18;
                          }

                          if (var21 < var20) {
                            var15.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16 + var15.offsetX, var17, var18 + var15.offsetY, var15.uid);
                          } else if (var15.linkedEntity != null) {
                            var15.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var16, var17, var18, var15.uid);
                          }
                        }
                      }

                      if (var13) {
                        TileDecor var22 = renderingTile.decor;
                        if (var22 != null) {
                          var22.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var22.absoluteX * renderCameraX, var22.height - renderCameraZ, var22.absoluteY * renderCameraY, var22.uid);
                        }

                        PickableStack var23 = renderingTile.pickableStack;
                        if (var23 != null && var23.height == 0) {
                          if (var23.middle != null) {
                            var23.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                          }

                          if (var23.top != null) {
                            var23.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                          }

                          if (var23.bottom != null) {
                            var23.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var23.absoluteX * renderCameraX, var23.anInt172 - renderCameraZ, var23.absoluteY * renderCameraY, var23.uid);
                          }
                        }
                      }

                      var16 = renderingTile.entityMarkerSideFlags;
                      if (var16 != 0) {
                        if (var4 < renderSceneX && (var16 & 4) != 0) {
                          var36 = var8[var4 + 1][var5];
                          if (var36 != null && var36.aBoolean1151) {
                            aNodeDeque1936.add(var36);
                          }
                        }

                        if (var5 < renderSceneY && (var16 & 2) != 0) {
                          var36 = var8[var4][var5 + 1];
                          if (var36 != null && var36.aBoolean1151) {
                            aNodeDeque1936.add(var36);
                          }
                        }

                        if (var4 > renderSceneX && (var16 & 1) != 0) {
                          var36 = var8[var4 - 1][var5];
                          if (var36 != null && var36.aBoolean1151) {
                            aNodeDeque1936.add(var36);
                          }
                        }

                        if (var5 > renderSceneY && (var16 & 8) != 0) {
                          var36 = var8[var4][var5 - 1];
                          if (var36 != null && var36.aBoolean1151) {
                            aNodeDeque1936.add(var36);
                          }
                        }
                      }
                      break;
                    }

                    if (renderingTile.entityMarkerSideRenderConfig != 0) {
                      var13 = true;

                      for (var14 = 0; var14 < renderingTile.entityMarkerCount; ++var14) {
                        if (renderingTile.markers[var14].anInt452 != renderTick && (renderingTile.entityMarkerSideMasks[var14] & renderingTile.entityMarkerSideRenderConfig) == renderingTile.anInt563) {
                          var13 = false;
                          break;
                        }
                      }

                      if (var13) {
                        var10 = renderingTile.boundary;
                        if (!this.method1463(var7, var4, var5, var10.orientation)) {
                          var10.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var10.absoluteX * renderCameraX, var10.height - renderCameraZ, var10.absoluteY * renderCameraY, var10.uid);
                        }

                        renderingTile.entityMarkerSideRenderConfig = 0;
                      }
                    }

                    if (!renderingTile.drawEntityMarkers) {
                      break;
                    }

                    try {
                      int var33 = renderingTile.entityMarkerCount;
                      renderingTile.drawEntityMarkers = false;
                      var14 = 0;

                      label563:
                      for (var11 = 0; var11 < var33; ++var11) {
                        var12 = renderingTile.markers[var11];
                        if (var12.anInt452 != renderTick) {
                          for (var24 = var12.sceneX; var24 <= var12.maxSceneX; ++var24) {
                            for (var16 = var12.sceneY; var16 <= var12.maxSceneY; ++var16) {
                              var36 = var8[var24][var16];
                              if (var36.aBoolean665) {
                                renderingTile.drawEntityMarkers = true;
                                continue label563;
                              }

                              if (var36.entityMarkerSideRenderConfig != 0) {
                                var18 = 0;
                                if (var24 > var12.sceneX) {
                                  ++var18;
                                }

                                if (var24 < var12.maxSceneX) {
                                  var18 += 4;
                                }

                                if (var16 > var12.sceneY) {
                                  var18 += 8;
                                }

                                if (var16 < var12.maxSceneY) {
                                  var18 += 2;
                                }

                                if ((var18 & var36.entityMarkerSideRenderConfig) == renderingTile.anInt575) {
                                  renderingTile.drawEntityMarkers = true;
                                  continue label563;
                                }
                              }
                            }
                          }

                          anEntityMarkerArray1940[var14++] = var12;
                          var24 = renderSceneX - var12.sceneX;
                          var16 = var12.maxSceneX - renderSceneX;
                          if (var16 > var24) {
                            var24 = var16;
                          }

                          var17 = renderSceneY - var12.sceneY;
                          var18 = var12.maxSceneY - renderSceneY;
                          if (var18 > var17) {
                            var12.anInt453 = var24 + var18;
                          } else {
                            var12.anInt453 = var24 + var17;
                          }
                        }
                      }

                      while (var14 > 0) {
                        var11 = -50;
                        var25 = -1;

                        for (var24 = 0; var24 < var14; ++var24) {
                          EntityMarker var35 = anEntityMarkerArray1940[var24];
                          if (var35.anInt452 != renderTick) {
                            if (var35.anInt453 > var11) {
                              var11 = var35.anInt453;
                              var25 = var24;
                            } else if (var11 == var35.anInt453) {
                              var17 = var35.centerAbsoluteX - renderCameraX;
                              var18 = var35.centerAbsoluteY - renderCameraY;
                              var19 = anEntityMarkerArray1940[var25].centerAbsoluteX - renderCameraX;
                              var20 = anEntityMarkerArray1940[var25].centerAbsoluteY - renderCameraY;
                              if (var17 * var17 + var18 * var18 > var19 * var19 + var20 * var20) {
                                var25 = var24;
                              }
                            }
                          }
                        }

                        if (var25 == -1) {
                          break;
                        }

                        EntityMarker var34 = anEntityMarkerArray1940[var25];
                        var34.anInt452 = renderTick;
                        if (!this.method1467(var7, var34.sceneX, var34.maxSceneX, var34.sceneY, var34.maxSceneY, var34.entity.height)) {
                          var34.entity.render(var34.orientation, pitchSin, pitchCos, yawSin, yawCos, var34.centerAbsoluteX - renderCameraX, var34.height - renderCameraZ, var34.centerAbsoluteY - renderCameraY, var34.uid);
                        }

                        for (var16 = var34.sceneX; var16 <= var34.maxSceneX; ++var16) {
                          for (var17 = var34.sceneY; var17 <= var34.maxSceneY; ++var17) {
                            Tile var26 = var8[var16][var17];
                            if (var26.entityMarkerSideRenderConfig != 0) {
                              aNodeDeque1936.add(var26);
                            } else if ((var16 != var4 || var17 != var5) && var26.aBoolean1151) {
                              aNodeDeque1936.add(var26);
                            }
                          }
                        }
                      }

                      if (!renderingTile.drawEntityMarkers) {
                        break;
                      }
                    } catch (Exception var28) {
                      renderingTile.drawEntityMarkers = false;
                      break;
                    }
                  }
                }
              }

              if (var4 > renderSceneX || var4 <= renderAreaMinX) {
                break;
              }

              var9 = var8[var4 - 1][var5];
            }

            if (var4 < renderSceneX || var4 >= renderAreaMaxX - 1) {
              break;
            }

            var9 = var8[var4 + 1][var5];
          }

          if (var5 > renderSceneY || var5 <= renderAreaMinY) {
            break;
          }

          var9 = var8[var4][var5 - 1];
        }

        if (var5 < renderSceneY || var5 >= renderAreaMaxY - 1) {
          break;
        }

        var9 = var8[var4][var5 + 1];
      }

      renderingTile.aBoolean1151 = false;
      --anInt1962;
      PickableStack var32 = renderingTile.pickableStack;
      if (var32 != null && var32.height != 0) {
        if (var32.middle != null) {
          var32.middle.render(0, pitchSin, pitchCos, yawSin, yawCos, var32.absoluteX * renderCameraX, var32.anInt172 - renderCameraZ - var32.height, var32.absoluteY * renderCameraY, var32.uid);
        }

        if (var32.top != null) {
          var32.top.render(0, pitchSin, pitchCos, yawSin, yawCos, var32.absoluteX * renderCameraX, var32.anInt172 - renderCameraZ - var32.height, var32.absoluteY * renderCameraY, var32.uid);
        }

        if (var32.bottom != null) {
          var32.bottom.render(0, pitchSin, pitchCos, yawSin, yawCos, var32.absoluteX * renderCameraX, var32.anInt172 - renderCameraZ - var32.height, var32.absoluteY * renderCameraY, var32.uid);
        }
      }

      if (renderingTile.anInt1149 != 0) {
        BoundaryDecor var29 = renderingTile.boundaryDecor;
        if (var29 != null && !this.method1466(var7, var4, var5, var29.entity.height)) {
          if ((var29.renderConfig & renderingTile.anInt1149) != 0) {
            var29.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var29.offsetX + (var29.absoluteX * renderCameraX), var29.height - renderCameraZ, var29.offsetY + (var29.absoluteY * renderCameraY), var29.uid);
          } else if (var29.renderConfig == 256) {
            var11 = var29.absoluteX * renderCameraX;
            var25 = var29.height - renderCameraZ;
            var24 = var29.absoluteY * renderCameraY;
            var16 = var29.orientation;
            if (var16 != 1 && var16 != 2) {
              var17 = var11;
            } else {
              var17 = -var11;
            }

            if (var16 != 2 && var16 != 3) {
              var18 = var24;
            } else {
              var18 = -var24;
            }

            if (var18 >= var17) {
              var29.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var11 + var29.offsetX, var25, var24 + var29.offsetY, var29.uid);
            } else if (var29.linkedEntity != null) {
              var29.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var11, var25, var24, var29.uid);
            }
          }
        }

        Boundary var27 = renderingTile.boundary;
        if (var27 != null) {
          if ((var27.linkedOrientation & renderingTile.anInt1149) != 0 && !this.method1463(var7, var4, var5, var27.linkedOrientation)) {
            var27.linkedEntity.render(0, pitchSin, pitchCos, yawSin, yawCos, var27.absoluteX * renderCameraX, var27.height - renderCameraZ, var27.absoluteY * renderCameraY, var27.uid);
          }

          if ((var27.orientation & renderingTile.anInt1149) != 0 && !this.method1463(var7, var4, var5, var27.orientation)) {
            var27.entity.render(0, pitchSin, pitchCos, yawSin, yawCos, var27.absoluteX * renderCameraX, var27.height - renderCameraZ, var27.absoluteY * renderCameraY, var27.uid);
          }
        }
      }

      Tile var30;
      if (var6 < this.floorCount - 1) {
        var30 = this.tiles[var6 + 1][var4][var5];
        if (var30 != null && var30.aBoolean1151) {
          aNodeDeque1936.add(var30);
        }
      }

      if (var4 < renderSceneX) {
        var30 = var8[var4 + 1][var5];
        if (var30 != null && var30.aBoolean1151) {
          aNodeDeque1936.add(var30);
        }
      }

      if (var5 < renderSceneY) {
        var30 = var8[var4][var5 + 1];
        if (var30 != null && var30.aBoolean1151) {
          aNodeDeque1936.add(var30);
        }
      }

      if (var4 > renderSceneX) {
        var30 = var8[var4 - 1][var5];
        if (var30 != null && var30.aBoolean1151) {
          aNodeDeque1936.add(var30);
        }
      }

      if (var5 > renderSceneY) {
        var30 = var8[var4][var5 - 1];
        if (var30 != null && var30.aBoolean1151) {
          aNodeDeque1936.add(var30);
        }
      }
    }
  }

  public void drawMinimapTile(int[] raster, int index, int width, int floorLevel, int sceneX, int sceneY) {
    Tile tile = this.tiles[floorLevel][sceneX][sceneY];

    if (tile != null) {
      TilePaint paintTile = tile.paint;
      if (paintTile != null) {
        int color = paintTile.rgb;
        if (color != 0) {
          for (int i = 0; i < 4; ++i) {
            raster[index] = color;
            raster[index + 1] = color;
            raster[index + 2] = color;
            raster[index + 3] = color;
            index += width;
          }

        }
      } else {
        TileModel model = tile.model;
        if (model != null) {
          int shape = model.shape;
          int rotation = model.rotation;
          int underlay = model.underlay;
          int overlay = model.overlay;
          int[] tileShapes = this.tileShapes[shape];
          int[] tileRotations = this.tileRotations[rotation];
          int var17 = 0;
          int var18;
          if (underlay != 0) {
            for (var18 = 0; var18 < 4; ++var18) {
              raster[index] = tileShapes[tileRotations[var17++]] == 0 ? underlay : overlay;
              raster[index + 1] = tileShapes[tileRotations[var17++]] == 0 ? underlay : overlay;
              raster[index + 2] = tileShapes[tileRotations[var17++]] == 0 ? underlay : overlay;
              raster[index + 3] = tileShapes[tileRotations[var17++]] == 0 ? underlay : overlay;
              index += width;
            }
          } else {
            for (var18 = 0; var18 < 4; ++var18) {
              if (tileShapes[tileRotations[var17++]] != 0) {
                raster[index] = overlay;
              }

              if (tileShapes[tileRotations[var17++]] != 0) {
                raster[index + 1] = overlay;
              }

              if (tileShapes[tileRotations[var17++]] != 0) {
                raster[index + 2] = overlay;
              }

              if (tileShapes[tileRotations[var17++]] != 0) {
                raster[index + 3] = overlay;
              }

              index += width;
            }
          }

        }
      }
    }
  }

  public boolean addEntityMarker(int var1, int var2, int var3, int var4, int var5, Entity var6, int var7, long var8, boolean var10) {
    if (var6 == null) {
      return true;
    }
    int var11 = var2 - var5;
    int var12 = var3 - var5;
    int var13 = var5 + var2;
    int var14 = var3 + var5;
    if (var10) {
      if (var7 > 640 && var7 < 1408) {
        var14 += 128;
      }

      if (var7 > 1152 && var7 < 1920) {
        var13 += 128;
      }

      if (var7 > 1664 || var7 < 384) {
        var12 -= 128;
      }

      if (var7 > 128 && var7 < 896) {
        var11 -= 128;
      }
    }

    var11 /= 128;
    var12 /= 128;
    var13 /= 128;
    var14 /= 128;
    return this.addEntityMarker(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
  }

  public boolean addPlayerObject(int var1, int var2, int var3, int var4, Entity var6, int var7, long var8, int var10, int var11, int var12, int var13) {
    return var6 == null || this.addEntityMarker(var1, var10, var11, var12 - var10 + 1, var13 - var11 + 1, var2, var3, var4, var6, var7, true, var8, 0);
  }

  public void setLightAt(int var1, int var2, int var3) {
    for (int var4 = 0; var4 < this.floorCount; ++var4) {
      for (int var5 = 0; var5 < this.width; ++var5) {
        for (int var6 = 0; var6 < this.length; ++var6) {
          Tile var7 = this.tiles[var4][var5][var6];
          if (var7 != null) {
            Boundary var8 = var7.boundary;
            UnlitModel var10;
            if (var8 != null && var8.entity instanceof UnlitModel) {
              UnlitModel var9 = (UnlitModel) var8.entity;
              this.method1452(var9, var4, var5, var6, 1, 1);
              if (var8.linkedEntity instanceof UnlitModel) {
                var10 = (UnlitModel) var8.linkedEntity;
                this.method1452(var10, var4, var5, var6, 1, 1);
                UnlitModel.method970(var9, var10, 0, 0, 0, false);
                var8.linkedEntity = var10.light(var10.ambience, var10.contrast, var1, var2, var3);
              }

              var8.entity = var9.light(var9.ambience, var9.contrast, var1, var2, var3);
            }

            for (int var11 = 0; var11 < var7.entityMarkerCount; ++var11) {
              EntityMarker var14 = var7.markers[var11];
              if (var14 != null && var14.entity instanceof UnlitModel) {
                UnlitModel var12 = (UnlitModel) var14.entity;
                this.method1452(var12, var4, var5, var6, var14.maxSceneX - var14.sceneX + 1, var14.maxSceneY - var14.sceneY + 1);
                var14.entity = var12.light(var12.ambience, var12.contrast, var1, var2, var3);
              }
            }

            TileDecor var13 = var7.decor;
            if (var13 != null && var13.entity instanceof UnlitModel) {
              var10 = (UnlitModel) var13.entity;
              this.method1442(var10, var4, var5, var6);
              var13.entity = var10.light(var10.ambience, var10.contrast, var1, var2, var3);
            }
          }
        }
      }
    }

  }

  public void method1484(int var1, int var2) {
    Tile var3 = this.tiles[0][var1][var2];

    for (int var4 = 0; var4 < 3; ++var4) {
      Tile var5 = this.tiles[var4][var1][var2] = this.tiles[var4 + 1][var1][var2];
      if (var5 != null) {
        --var5.floorLevel;

        for (int var6 = 0; var6 < var5.entityMarkerCount; ++var6) {
          EntityMarker var7 = var5.markers[var6];
          if (WorldMapDecor.method379(var7.uid) && var7.sceneX == var1 && var2 == var7.sceneY) {
            --var7.floorLevel;
          }
        }
      }
    }

    if (this.tiles[0][var1][var2] == null) {
      this.tiles[0][var1][var2] = new Tile(0, var1, var2);
    }

    this.tiles[0][var1][var2].underneath = var3;
    this.tiles[3][var1][var2] = null;
  }

  public void method1482(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 != null) {
      var4.boundary = null;
    }
  }

  public void setTileMinFloorLevel(int var1, int var2, int var3, int min) {
    Tile var5 = this.tiles[var1][var2][var3];
    if (var5 != null) {
      this.tiles[var1][var2][var3].minFloorLevel = min;
    }
  }

  public void method1473(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 != null) {
      var4.boundaryDecor = null;
    }
  }

  public void method1478(int var1) {
    this.baseFloor = var1;

    for (int var2 = 0; var2 < this.width; ++var2) {
      for (int var3 = 0; var3 < this.length; ++var3) {
        if (this.tiles[var1][var2][var3] == null) {
          this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
        }
      }
    }

  }

  public void method1475(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 != null) {
      for (int var5 = 0; var5 < var4.entityMarkerCount; ++var5) {
        EntityMarker var6 = var4.markers[var5];
        if (WorldMapDecor.method379(var6.uid) && var2 == var6.sceneX && var3 == var6.sceneY) {
          this.method1485(var6);
          return;
        }
      }

    }
  }

  public void method1471(int var1, int var2, int var3) {
    Tile var4 = this.tiles[var1][var2][var3];
    if (var4 != null) {
      var4.decor = null;
    }
  }

  public void renderScene(int camX, int camZ, int camY, int pitch, int yaw, int floor) {
    if (camX < 0) {
      camX = 0;
    } else if (camX >= this.width * 128) {
      camX = this.width * 128 - 1;
    }

    if (camY < 0) {
      camY = 0;
    } else if (camY >= this.length * 128) {
      camY = this.length * 128 - 1;
    }

    if (pitch < 128) {
      pitch = 128;
    } else if (pitch > 383) {
      pitch = 383;
    }

    ++renderTick;
    pitchSin = JagGraphics3D.SIN_TABLE[pitch];
    pitchCos = JagGraphics3D.COS_TABLE[pitch];
    yawSin = JagGraphics3D.SIN_TABLE[yaw];
    yawCos = JagGraphics3D.COS_TABLE[yaw];
    aBooleanArrayArray1951 = visibilityMap[(pitch - 128) / 32][yaw / 64]; //tile visibility maps
    renderCameraX = camX;
    renderCameraZ = camZ;
    renderCameraY = camY;
    renderSceneX = camX / 128;
    renderSceneY = camY / 128;
    renderFloor = floor;
    renderAreaMinX = renderSceneX - 25;
    if (renderAreaMinX < 0) {
      renderAreaMinX = 0;
    }

    renderAreaMinY = renderSceneY - 25;
    if (renderAreaMinY < 0) {
      renderAreaMinY = 0;
    }

    renderAreaMaxX = renderSceneX + 25;
    if (renderAreaMaxX > this.width) {
      renderAreaMaxX = this.width;
    }

    renderAreaMaxY = renderSceneY + 25;
    if (renderAreaMaxY > this.length) {
      renderAreaMaxY = this.length;
    }

    this.method1468();
    anInt1962 = 0;

    int var7;
    Tile[][] var8;
    int var9;
    int var10;
    for (var7 = this.baseFloor; var7 < this.floorCount; ++var7) {
      var8 = this.tiles[var7];

      for (var9 = renderAreaMinX; var9 < renderAreaMaxX; ++var9) {
        for (var10 = renderAreaMinY; var10 < renderAreaMaxY; ++var10) {
          Tile var11 = var8[var9][var10];
          if (var11 != null) {
            if (var11.minFloorLevel <= floor && (aBooleanArrayArray1951[var9 - renderSceneX + 25][var10 - renderSceneY + 25] || this.heights[var7][var9][var10] - camZ >= 2000)) {
              var11.aBoolean665 = true;
              var11.aBoolean1151 = true;
              var11.drawEntityMarkers = var11.entityMarkerCount > 0;

              ++anInt1962;
            } else {
              var11.aBoolean665 = false;
              var11.aBoolean1151 = false;
              var11.entityMarkerSideRenderConfig = 0;
            }
          }
        }
      }
    }

    int var12;
    int var13;
    int var14;
    int var15;
    Tile var16;
    for (var7 = this.baseFloor; var7 < this.floorCount; ++var7) {
      var8 = this.tiles[var7];

      for (var9 = -25; var9 <= 0; ++var9) {
        var10 = var9 + renderSceneX;
        var12 = renderSceneX - var9;
        if (var10 >= renderAreaMinX || var12 < renderAreaMaxX) {
          for (var13 = -25; var13 <= 0; ++var13) {
            var14 = var13 + renderSceneY;
            var15 = renderSceneY - var13;
            if (var10 >= renderAreaMinX) {
              if (var14 >= renderAreaMinY) {
                var16 = var8[var10][var14];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, true);
                }
              }

              if (var15 < renderAreaMaxY) {
                var16 = var8[var10][var15];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, true);
                }
              }
            }

            if (var12 < renderAreaMaxX) {
              if (var14 >= renderAreaMinY) {
                var16 = var8[var12][var14];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, true);
                }
              }

              if (var15 < renderAreaMaxY) {
                var16 = var8[var12][var15];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, true);
                }
              }
            }

            if (anInt1962 == 0) {
              acceptClick = false;
              return;
            }
          }
        }
      }
    }

    for (var7 = this.baseFloor; var7 < this.floorCount; ++var7) {
      var8 = this.tiles[var7];

      for (var9 = -25; var9 <= 0; ++var9) {
        var10 = var9 + renderSceneX;
        var12 = renderSceneX - var9;
        if (var10 >= renderAreaMinX || var12 < renderAreaMaxX) {
          for (var13 = -25; var13 <= 0; ++var13) {
            var14 = var13 + renderSceneY;
            var15 = renderSceneY - var13;
            if (var10 >= renderAreaMinX) {
              if (var14 >= renderAreaMinY) {
                var16 = var8[var10][var14];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, false);
                }
              }

              if (var15 < renderAreaMaxY) {
                var16 = var8[var10][var15];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, false);
                }
              }
            }

            if (var12 < renderAreaMaxX) {
              if (var14 >= renderAreaMinY) {
                var16 = var8[var12][var14];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, false);
                }
              }

              if (var15 < renderAreaMaxY) {
                var16 = var8[var12][var15];
                if (var16 != null && var16.aBoolean665) {
                  this.renderTile(var16, false);
                }
              }
            }

            if (anInt1962 == 0) {
              acceptClick = false;
              return;
            }
          }
        }
      }
    }

    acceptClick = false;
  }

  public void addTileDecor(int var1, int var2, int var3, int var4, Entity var5, long var6, int var8) {
    if (var5 != null) {
      TileDecor var9 = new TileDecor();
      var9.entity = var5;
      var9.absoluteX = var2 * 128 + 64;
      var9.absoluteY = var3 * 128 + 64;
      var9.height = var4;
      var9.uid = var6;
      var9.config = var8;
      if (this.tiles[var1][var2][var3] == null) {
        this.tiles[var1][var2][var3] = new Tile(var1, var2, var3);
      }

      this.tiles[var1][var2][var3].decor = var9;
    }
  }

  public boolean method1470(int var1, int var2, int var3, int var4, int var5, int var6, Entity var7, int var8, long var9, int var11) {
    if (var7 == null) {
      return true;
    }
    int var12 = var5 * 64 + var2 * 128;
    int var13 = var6 * 64 + var3 * 128;
    return this.addEntityMarker(var1, var2, var3, var5, var6, var12, var13, var4, var7, var8, false, var9, var11);
  }

  public void addBoundary(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, long var9, int var11) {
    if (var5 != null || var6 != null) {
      Boundary var12 = new Boundary();
      var12.uid = var9;
      var12.config = var11;
      var12.absoluteX = var2 * 128 + 64;
      var12.absoluteY = var3 * 128 + 64;
      var12.height = var4;
      var12.entity = var5;
      var12.linkedEntity = var6;
      var12.orientation = var7;
      var12.linkedOrientation = var8;

      for (int var13 = var1; var13 >= 0; --var13) {
        if (this.tiles[var13][var2][var3] == null) {
          this.tiles[var13][var2][var3] = new Tile(var13, var2, var3);
        }
      }

      this.tiles[var1][var2][var3].boundary = var12;
    }
  }

  public void addBoundaryDecor(int var1, int var2, int var3, int var4, Entity var5, Entity var6, int var7, int var8, int var9, int var10, long var11, int var13) {
    if (var5 != null) {
      BoundaryDecor var14 = new BoundaryDecor();
      var14.uid = var11;
      var14.config = var13;
      var14.absoluteX = var2 * 128 + 64;
      var14.absoluteY = var3 * 128 + 64;
      var14.height = var4;
      var14.entity = var5;
      var14.linkedEntity = var6;
      var14.renderConfig = var7;
      var14.orientation = var8;
      var14.offsetX = var9;
      var14.offsetY = var10;

      for (int var15 = var1; var15 >= 0; --var15) {
        if (this.tiles[var15][var2][var3] == null) {
          this.tiles[var15][var2][var3] = new Tile(var15, var2, var3);
        }
      }

      this.tiles[var1][var2][var3].boundaryDecor = var14;
    }
  }

  public void method1472() {
    for (int var1 = 0; var1 < this.tempMarkerCount; ++var1) {
      EntityMarker var2 = this.tempMarkers[var1];
      this.method1485(var2);
      this.tempMarkers[var1] = null;
    }

    this.tempMarkerCount = 0;
  }

  public void method1474(int var1, int var2, int var3, int var4) {
    Tile var5 = this.tiles[var1][var2][var3];
    if (var5 != null) {
      BoundaryDecor var6 = var5.boundaryDecor;
      if (var6 != null) {
        var6.offsetX = var4 * var6.offsetX / 16;
        var6.offsetY = var4 * var6.offsetY / 16;
      }
    }
  }

  public void method1480(int z, int x, int y, int shape, int rotation, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, int var19, int var20) {
    if (shape == 0) {
      TilePaint paint = new TilePaint(var11, var12, var13, var14, -1, var19, false);
      for (int cz = z; cz >= 0; --cz) {
        if (tiles[cz][x][y] == null) {
          tiles[cz][x][y] = new Tile(cz, x, y);
        }
      }

      tiles[z][x][y].paint = paint;
      return;
    }

    if (shape != 1) {
      TileModel model = new TileModel(shape, rotation, var6, x, y, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
      for (int cz = z; cz >= 0; --cz) {
        if (tiles[cz][x][y] == null) {
          tiles[cz][x][y] = new Tile(cz, x, y);
        }
      }

      tiles[z][x][y].model = model;
      return;
    }

    TilePaint paint = new TilePaint(var15, var16, var17, var18, var6, var20, var8 == var7 && var7 == var9 && var10 == var7);
    for (int cz = z; cz >= 0; --cz) {
      if (tiles[cz][x][y] == null) {
        tiles[cz][x][y] = new Tile(cz, x, y);
      }
    }

    tiles[z][x][y].paint = paint;
  }
}
