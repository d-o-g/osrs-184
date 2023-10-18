package jagex.oldscape;

import jagex.jagex3.sound.*;
import jagex.jagex3.sound.vorbis.RawAudioOverride;
import jagex.oldscape.client.scene.SceneGraphRenderData;
import jagex.oldscape.client.client;
import jagex.oldscape.client.minimenu.ContextMenu;
import jagex.oldscape.client.minimenu.MenuItemNode;
import jagex.oldscape.client.scene.CollisionMap;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.scene.entity.*;
import jagex.oldscape.client.type.ObjectDefinition;
import jagex.oldscape.client.type.ParameterDefinition;
import jagex.jagex3.js5.Archive;
import jagex.messaging.Buffer;
import jagex.statics.Statics46;
import jagex.oldscape.client.worldmap.WorldMapChunkDefinition;
import jagex.oldscape.client.worldmap.WorldMapLabelSize;

public class SerializableProcessor implements EnumType {

  public static final SerializableProcessor INTEGER_PROCESSOR = new SerializableProcessor(1, 0, Integer.class, new SerializableInteger());
  public static final SerializableProcessor LONG_PROCESSOR = new SerializableProcessor(0, 1, Long.class, new SerializableLong());
  public static final SerializableProcessor STRING_PROCESSOR = new SerializableProcessor(2, 2, String.class, new SerializableString());

  public static String aString636;

  public final Serializable value;
  public final int ordinal;
  public final int type;
  public final Class<?> targetType;

  public SerializableProcessor(int type, int ordinal, Class<?> targetType, Serializable value) {
    this.type = type;
    this.ordinal = ordinal;
    this.targetType = targetType;
    this.value = value;
  }

  public static SerializableProcessor[] getValues() {
    return new SerializableProcessor[]{STRING_PROCESSOR, LONG_PROCESSOR, INTEGER_PROCESSOR};
  }

  public static SerializableProcessor valueOf(Class<?> type) {
    SerializableProcessor[] values = getValues();

    for (SerializableProcessor processor : values) {
      if (processor.targetType == type) {
        return processor;
      }
    }

    return null;
  }

  public static void method459(int var0, int var1) {
    MenuItemNode var2 = ParameterDefinition.aMenuItemNode_384;
    ContextMenu.doAction(var2.secondaryArg, var2.tertiaryArg, var2.opcode, var2.primaryArg, var2.action, var0, var1);
    ParameterDefinition.aMenuItemNode_384 = null;
  }

  public static void method456(int var0, int var1, int var2, int var3) {
    for (ObjectSound sound = ObjectSound.OBJECT_SOUNDS.head(); sound != null; sound = ObjectSound.OBJECT_SOUNDS.next()) {
      if (sound.ambientSoundId != -1 || sound.effects != null) {
        int var5 = 0;
        if (var1 > sound.anInt380 * 16384) {
          var5 += var1 - sound.anInt380 * 16384;
        } else if (var1 < sound.anInt377 * 128) {
          var5 += sound.anInt377 * 128 - var1;
        }

        if (var2 > sound.anInt375 * 16384) {
          var5 += var2 - sound.anInt375 * 16384;
        } else if (var2 < sound.anInt112 * 128) {
          var5 += sound.anInt112 * 128 - var2;
        }

        if (var5 - 64 <= sound.anInt372 && client.areaSoundEffectVolume != 0 && var0 == sound.anInt378) {
          var5 -= 64;
          if (var5 < 0) {
            var5 = 0;
          }

          int var6 = (sound.anInt372 - var5) * client.areaSoundEffectVolume / sound.anInt372;
          if (sound.aClass5_Sub6_Sub2_370 == null) {
            if (sound.ambientSoundId >= 0) {
              AudioEffect var7 = AudioEffect.load(Archive.audioEffects, sound.ambientSoundId, 0);
              if (var7 != null) {
                RawAudioOverride var8 = var7.createOverride().resample(Statics46.aClass98_446);
                PcmStream_Sub2 var9 = PcmStream_Sub2.method598(var8, 100, var6);
                var9.method585(-1);
                WorldMapLabelSize.aClass5_Sub6_Sub1_528.method312(var9);
                sound.aClass5_Sub6_Sub2_370 = var9;
              }
            }
          } else {
            sound.aClass5_Sub6_Sub2_370.method302(var6);
          }

          if (sound.aClass5_Sub6_Sub2_369 == null) {
            if (sound.effects != null && (sound.anInt366 -= var3) <= 0) {
              int var10 = (int) (Math.random() * (double) sound.effects.length);
              AudioEffect var12 = AudioEffect.load(Archive.audioEffects, sound.effects[var10], 0);
              if (var12 != null) {
                RawAudioOverride var13 = var12.createOverride().resample(Statics46.aClass98_446);
                PcmStream_Sub2 var11 = PcmStream_Sub2.method598(var13, 100, var6);
                var11.method585(0);
                WorldMapLabelSize.aClass5_Sub6_Sub1_528.method312(var11);
                sound.aClass5_Sub6_Sub2_369 = var11;
                sound.anInt366 = sound.anInt368 + (int) (Math.random() * (double) (sound.anInt367 - sound.anInt368));
              }
            }
          } else {
            sound.aClass5_Sub6_Sub2_369.method302(var6);
            if (!sound.aClass5_Sub6_Sub2_369.isLinked()) {
              sound.aClass5_Sub6_Sub2_369 = null;
            }
          }
        } else {
          if (sound.aClass5_Sub6_Sub2_370 != null) {
            WorldMapLabelSize.aClass5_Sub6_Sub1_528.removeDelegate(sound.aClass5_Sub6_Sub2_370);
            sound.aClass5_Sub6_Sub2_370 = null;
          }

          if (sound.aClass5_Sub6_Sub2_369 != null) {
            WorldMapLabelSize.aClass5_Sub6_Sub1_528.removeDelegate(sound.aClass5_Sub6_Sub2_369);
            sound.aClass5_Sub6_Sub2_369 = null;
          }
        }
      }
    }

  }

  public static void method453() {
    WorldMapChunkDefinition.A_REFERENCE_NODE_TABLE___118.clear();
  }

  public static void method450() {
    Login.step = 24;
    Login.setMessages("The game servers are currently being updated.", "Please wait a few minutes and try again.", "");
  }

  public static void loadObject(int var0, int var1, int x, int y, int id, int orientation, int type, SceneGraph scene, CollisionMap map) {
    ObjectDefinition def = ObjectDefinition.get(id);
    int width;
    int height;
    if (orientation != 1 && orientation != 3) {
      width = def.sizeX;
      height = def.sizeY;
    } else {
      width = def.sizeY;
      height = def.sizeX;
    }

    int endX;
    int startX;
    if (width + x <= 104) {
      endX = (width >> 1) + x;
      startX = x + (width + 1 >> 1);
    } else {
      endX = x;
      startX = x + 1;
    }

    int endY;
    int startY;
    if (y + height <= 104) {
      endY = y + (height >> 1);
      startY = y + (height + 1 >> 1);
    } else {
      endY = y;
      startY = y + 1;
    }

    int[][] heights = SceneGraphRenderData.tileHeights[var1];
    int var17 = heights[startX][startY] + heights[endX][endY] + heights[startX][endY] + heights[endX][startY] >> 2;
    int var18 = (x << 7) + (width << 6);
    int var19 = (y << 7) + (height << 6);
    long uid = EntityUID.build(x, y, 2, def.mapDoorFlag == 0, id);
    int var22 = (orientation << 6) + type;
    if (def.itemSupport == 1) {
      var22 += 256;
    }

    Entity var23;
    if (type == 22) {
      if (def.animation == -1 && def.transformIds == null) {
        var23 = def.getLitModel(22, orientation, heights, var18, var17, var19);
      } else {
        var23 = new DynamicObject(id, 22, orientation, var1, x, y, def.animation, true, null);
      }

      scene.addTileDecor(var0, x, y, var17, var23, uid, var22);
      if (def.anInt791 == 1) {
        map.setBlockedByTileDecor(x, y);
      }

    } else if (type != 10 && type != 11) {
      if (type >= 12) {
        if (def.animation == -1 && def.transformIds == null) {
          var23 = def.getLitModel(type, orientation, heights, var18, var17, var19);
        } else {
          var23 = new DynamicObject(id, type, orientation, var1, x, y, def.animation, true, null);
        }

        scene.method1470(var0, x, y, var17, 1, 1, var23, 0, uid, var22);
        if (def.anInt791 != 0) {
          map.addObject(x, y, width, height, def.impenetrable);
        }

      } else if (type == 0) {
        if (def.animation == -1 && def.transformIds == null) {
          var23 = def.getLitModel(0, orientation, heights, var18, var17, var19);
        } else {
          var23 = new DynamicObject(id, 0, orientation, var1, x, y, def.animation, true, null);
        }

        scene.addBoundary(var0, x, y, var17, var23, null, SceneGraphRenderData.anIntArray406[orientation], 0, uid, var22);
        if (def.anInt791 != 0) {
          map.method154(x, y, type, orientation, def.impenetrable);
        }

      } else if (type == 1) {
        if (def.animation == -1 && def.transformIds == null) {
          var23 = def.getLitModel(1, orientation, heights, var18, var17, var19);
        } else {
          var23 = new DynamicObject(id, 1, orientation, var1, x, y, def.animation, true, null);
        }

        scene.addBoundary(var0, x, y, var17, var23, null, SceneGraphRenderData.anIntArray395[orientation], 0, uid, var22);
        if (def.anInt791 != 0) {
          map.method154(x, y, type, orientation, def.impenetrable);
        }

      } else {
        int var24;
        if (type == 2) {
          var24 = orientation + 1 & 3;
          Entity var25;
          Entity var26;
          if (def.animation == -1 && def.transformIds == null) {
            var25 = def.getLitModel(2, orientation + 4, heights, var18, var17, var19);
            var26 = def.getLitModel(2, var24, heights, var18, var17, var19);
          } else {
            var25 = new DynamicObject(id, 2, orientation + 4, var1, x, y, def.animation, true, null);
            var26 = new DynamicObject(id, 2, var24, var1, x, y, def.animation, true, null);
          }

          scene.addBoundary(var0, x, y, var17, var25, var26, SceneGraphRenderData.anIntArray406[orientation], SceneGraphRenderData.anIntArray406[var24], uid, var22);
          if (def.anInt791 != 0) {
            map.method154(x, y, type, orientation, def.impenetrable);
          }

        } else if (type == 3) {
          if (def.animation == -1 && def.transformIds == null) {
            var23 = def.getLitModel(3, orientation, heights, var18, var17, var19);
          } else {
            var23 = new DynamicObject(id, 3, orientation, var1, x, y, def.animation, true, null);
          }

          scene.addBoundary(var0, x, y, var17, var23, null, SceneGraphRenderData.anIntArray395[orientation], 0, uid, var22);
          if (def.anInt791 != 0) {
            map.method154(x, y, type, orientation, def.impenetrable);
          }

        } else if (type == 9) {
          if (def.animation == -1 && def.transformIds == null) {
            var23 = def.getLitModel(type, orientation, heights, var18, var17, var19);
          } else {
            var23 = new DynamicObject(id, type, orientation, var1, x, y, def.animation, true, null);
          }

          scene.method1470(var0, x, y, var17, 1, 1, var23, 0, uid, var22);
          if (def.anInt791 != 0) {
            map.addObject(x, y, width, height, def.impenetrable);
          }

        } else if (type == 4) {
          if (def.animation == -1 && def.transformIds == null) {
            var23 = def.getLitModel(4, orientation, heights, var18, var17, var19);
          } else {
            var23 = new DynamicObject(id, 4, orientation, var1, x, y, def.animation, true, null);
          }

          scene.addBoundaryDecor(var0, x, y, var17, var23, null, SceneGraphRenderData.anIntArray406[orientation], 0, 0, 0, uid, var22);
        } else {
          long var27;
          Entity var29;
          if (type == 5) {
            var24 = 16;
            var27 = scene.getBoundaryUidAt(var0, x, y);
            if (var27 != 0L) {
              var24 = ObjectDefinition.get(EntityUID.getObjectId(var27)).anInt1369;
            }

            if (def.animation == -1 && def.transformIds == null) {
              var29 = def.getLitModel(4, orientation, heights, var18, var17, var19);
            } else {
              var29 = new DynamicObject(id, 4, orientation, var1, x, y, def.animation, true, null);
            }

            scene.addBoundaryDecor(var0, x, y, var17, var29, null, SceneGraphRenderData.anIntArray406[orientation], 0, var24 * SceneGraphRenderData.anIntArray402[orientation], var24 * SceneGraphRenderData.anIntArray394[orientation], uid, var22);
          } else if (type == 6) {
            var24 = 8;
            var27 = scene.getBoundaryUidAt(var0, x, y);
            if (var27 != 0L) {
              var24 = ObjectDefinition.get(EntityUID.getObjectId(var27)).anInt1369 / 2;
            }

            if (def.animation == -1 && def.transformIds == null) {
              var29 = def.getLitModel(4, orientation + 4, heights, var18, var17, var19);
            } else {
              var29 = new DynamicObject(id, 4, orientation + 4, var1, x, y, def.animation, true, null);
            }

            scene.addBoundaryDecor(var0, x, y, var17, var29, null, 256, orientation, var24 * SceneGraphRenderData.anIntArray397[orientation], var24 * SceneGraphRenderData.anIntArray392[orientation], uid, var22);
          } else if (type == 7) {
            int var30 = orientation + 2 & 3;
            if (def.animation == -1 && def.transformIds == null) {
              var23 = def.getLitModel(4, var30 + 4, heights, var18, var17, var19);
            } else {
              var23 = new DynamicObject(id, 4, var30 + 4, var1, x, y, def.animation, true, null);
            }

            scene.addBoundaryDecor(var0, x, y, var17, var23, null, 256, var30, 0, 0, uid, var22);
          } else if (type == 8) {
            var24 = 8;
            var27 = scene.getBoundaryUidAt(var0, x, y);
            if (var27 != 0L) {
              var24 = ObjectDefinition.get(EntityUID.getObjectId(var27)).anInt1369 / 2;
            }

            int var31 = orientation + 2 & 3;
            Entity var32;
            if (def.animation == -1 && def.transformIds == null) {
              var29 = def.getLitModel(4, orientation + 4, heights, var18, var17, var19);
              var32 = def.getLitModel(4, var31 + 4, heights, var18, var17, var19);
            } else {
              var29 = new DynamicObject(id, 4, orientation + 4, var1, x, y, def.animation, true, null);
              var32 = new DynamicObject(id, 4, var31 + 4, var1, x, y, def.animation, true, null);
            }

            scene.addBoundaryDecor(var0, x, y, var17, var29, var32, 256, orientation, var24 * SceneGraphRenderData.anIntArray397[orientation], var24 * SceneGraphRenderData.anIntArray392[orientation], uid, var22);
          }
        }
      }
    } else {
      if (def.animation == -1 && def.transformIds == null) {
        var23 = def.getLitModel(10, orientation, heights, var18, var17, var19);
      } else {
        var23 = new DynamicObject(id, 10, orientation, var1, x, y, def.animation, true, null);
      }

      if (var23 != null) {
        scene.method1470(var0, x, y, var17, width, height, var23, type == 11 ? 256 : 0, uid, var22);
      }

      if (def.anInt791 != 0) {
        map.addObject(x, y, width, height, def.impenetrable);
      }

    }
  }

  public int getOrdinal() {
    return ordinal;
  }

  public Object decode(Buffer buffer) {
    return value.decode(buffer);
  }
}
