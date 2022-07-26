package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.graphics.Sprite;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class HealthBarDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<HealthBarDefinition> cache = new ReferenceCache<>(64);
    public static final ReferenceCache<Sprite> sprites = new ReferenceCache<>(64);
    public static ReferenceTable table;
    public static ReferenceTable aReferenceTable697;

    public int orderPriority;
    public int anInt564;
    public int hidePriority;
    public int anInt367;
    public int anInt368;
    public int anInt574;
    public int maxWidth;
    public int anInt702;
    public int underlaySpriteId;
    public int overlaySpriteId;

    public HealthBarDefinition() {
        orderPriority = 255;
        hidePriority = 255;
        anInt564 = -1;
        anInt368 = 1;
        anInt367 = 70;
        overlaySpriteId = -1;
        underlaySpriteId = -1;
        maxWidth = 30;
        anInt702 = 0;
    }

    public static void method296(ReferenceTable var0, ReferenceTable var1) {
        table = var0;
        aReferenceTable697 = var1;
    }

    public void decode(Buffer var1, int var2) {
        if (var2 == 1) {
            var1.g2();
        } else if (var2 == 2) {
            orderPriority = var1.g1();
        } else if (var2 == 3) {
            hidePriority = var1.g1();
        } else if (var2 == 4) {
            anInt564 = 0;
        } else if (var2 == 5) {
            anInt367 = var1.g2();
        } else if (var2 == 6) {
            var1.g1();
        } else if (var2 == 7) {
            overlaySpriteId = var1.method1051();
        } else if (var2 == 8) {
            underlaySpriteId = var1.method1051();
        } else if (var2 == 11) {
            anInt564 = var1.g2();
        } else if (var2 == 14) {
            maxWidth = var1.g1();
        } else if (var2 == 15) {
            anInt702 = var1.g1();
        }

    }

    public void decode(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }

            decode(var1, var2);
        }
    }

    public Sprite method1378() {
        if (underlaySpriteId < 0) {
            return null;
        }
        Sprite var1 = sprites.get(underlaySpriteId);
        if (var1 != null) {
            return var1;
        }
        var1 = Sprite.get(aReferenceTable697, underlaySpriteId, 0);
        if (var1 != null) {
            sprites.put(var1, underlaySpriteId);
        }

        return var1;
    }

    public Sprite method1379() {
        if (overlaySpriteId < 0) {
            return null;
        }
        Sprite var1 = sprites.get(overlaySpriteId);
        if (var1 != null) {
            return var1;
        }
        var1 = Sprite.get(aReferenceTable697, overlaySpriteId, 0);
        if (var1 != null) {
            sprites.put(var1, overlaySpriteId);
        }

        return var1;
    }
}
