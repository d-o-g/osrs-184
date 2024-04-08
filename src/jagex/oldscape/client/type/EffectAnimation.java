package jagex.oldscape.client.type;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.oldscape.client.scene.entity.Model;
import jagex.oldscape.client.scene.entity.UnlitModel;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;
import jagex.statics.Statics52;

public class EffectAnimation extends DoublyLinkedNode {

  public static final ReferenceCache<Model> modelCache = new ReferenceCache<>(30);
  public static final ReferenceCache<EffectAnimation> cache = new ReferenceCache<>(64);
  public static ReferenceTable table;

  public int animation;
  public int id;
  public int modelId;
  public int scaleXY;
  public int scaleZ;
  public int orientation;
  public int ambience;
  public int contrast;
  public short[] aShortArray1461;
  public short[] aShortArray1460;
  public short[] aShortArray1462;
  public short[] aShortArray1459;

  public EffectAnimation() {
    animation = -1;
    scaleXY = 128;
    scaleZ = 128;
    orientation = 0;
    ambience = 0;
    contrast = 0;
  }

  public static void clear() {
    cache.clear();
    modelCache.clear();
  }

  public static EffectAnimation get(int id) {
    EffectAnimation anim = cache.get(id);
    if (anim != null) {
      return anim;
    }
    byte[] buffer = Statics52.aReferenceTable500.unpack(13, id);
    anim = new EffectAnimation();
    anim.id = id;
    if (buffer != null) {
      anim.decode(new Buffer(buffer));
    }

    cache.put(anim, id);
    return anim;
  }

  public void method988(Buffer var1, int var2) {
    if (var2 == 1) {
      modelId = var1.g2();
    } else if (var2 == 2) {
      animation = var1.g2();
    } else if (var2 == 4) {
      scaleXY = var1.g2();
    } else if (var2 == 5) {
      scaleZ = var1.g2();
    } else if (var2 == 6) {
      orientation = var1.g2();
    } else if (var2 == 7) {
      ambience = var1.g1();
    } else if (var2 == 8) {
      contrast = var1.g1();
    } else {
      int var3;
      int var4;
      if (var2 == 40) {
        var3 = var1.g1();
        aShortArray1461 = new short[var3];
        aShortArray1462 = new short[var3];

        for (var4 = 0; var4 < var3; ++var4) {
          aShortArray1461[var4] = (short) var1.g2();
          aShortArray1462[var4] = (short) var1.g2();
        }
      } else if (var2 == 41) {
        var3 = var1.g1();
        aShortArray1460 = new short[var3];
        aShortArray1459 = new short[var3];

        for (var4 = 0; var4 < var3; ++var4) {
          aShortArray1460[var4] = (short) var1.g2();
          aShortArray1459[var4] = (short) var1.g2();
        }
      }
    }

  }

  public final Model getModel(int frame) {
    Model cached = modelCache.get(id);
    if (cached == null) {
      UnlitModel unlit = UnlitModel.unpack(table, modelId, 0);
      if (unlit == null) {
        return null;
      }

      if (aShortArray1461 != null) {
        for (int i = 0; i < aShortArray1461.length; ++i) {
          unlit.texturize(aShortArray1461[i], aShortArray1462[i]);
        }
      }

      if (aShortArray1460 != null) {
        for (int i = 0; i < aShortArray1460.length; ++i) {
          unlit.colorize(aShortArray1460[i], aShortArray1459[i]);
        }
      }

      cached = unlit.light(ambience + 64, contrast + 850, -30, -50, -30);
      modelCache.put(cached, id);
    }

    Model animated;
    if (animation != -1 && frame != -1) {
      animated = AnimationSequence.get(animation).getAnimatedModel(cached, frame);
    } else {
      animated = cached.method1294(true);
    }

    if (scaleXY != 128 || scaleZ != 128) {
      animated.scale(scaleXY, scaleZ, scaleXY);
    }

    if (orientation != 0) {
      if (orientation == 90) {
        animated.rotate90Y();
      }

      if (orientation == 180) {
        animated.rotate90Y();
        animated.rotate90Y();
      }

      if (orientation == 270) {
        animated.rotate90Y();
        animated.rotate90Y();
        animated.rotate90Y();
      }
    }

    return animated;
  }

  public void decode(Buffer var1) {
    while (true) {
      int var2 = var1.g1();
      if (var2 == 0) {
        return;
      }

      method988(var1, var2);
    }
  }
}
