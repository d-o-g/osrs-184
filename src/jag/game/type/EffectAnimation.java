package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.scene.entity.Model;
import jag.game.scene.entity.UnlitModel;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;
import jag.statics.Statics52;

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

    public final Model method1004(int var1) {
        Model cached = modelCache.get(id);
        if (cached == null) {
            UnlitModel var3 = UnlitModel.method982(table, modelId, 0);
            if (var3 == null) {
                return null;
            }

            int var4;
            if (aShortArray1461 != null) {
                for (var4 = 0; var4 < aShortArray1461.length; ++var4) {
                    var3.texturize(aShortArray1461[var4], aShortArray1462[var4]);
                }
            }

            if (aShortArray1460 != null) {
                for (var4 = 0; var4 < aShortArray1460.length; ++var4) {
                    var3.colorize(aShortArray1460[var4], aShortArray1459[var4]);
                }
            }

            cached = var3.light(ambience + 64, contrast + 850, -30, -50, -30);
            modelCache.put(cached, id);
        }

        Model animated;
        if (animation != -1 && var1 != -1) {
            animated = AnimationSequence.get(animation).method880(cached, var1);
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
