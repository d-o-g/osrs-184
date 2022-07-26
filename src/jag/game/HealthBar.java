package jag.game;

import jag.commons.collection.LinkedList;
import jag.commons.collection.Node;
import jag.game.scene.entity.PathingEntity;
import jag.game.type.AnimationSequence;
import jag.game.type.HealthBarDefinition;

public class HealthBar extends Node {

    public final LinkedList<HitUpdate> hitsplats = new LinkedList<>();

    public final HealthBarDefinition definition;

    public HealthBar(HealthBarDefinition definition) {
        this.definition = definition;
    }

    public static String toString(int var0, boolean var1) {
        if (var1 && var0 >= 0) {
            int var2 = var0;
            String var3;
            if (var1) {
                int var4 = 2;

                for (int var5 = var0 / 10; var5 != 0; ++var4) {
                    var5 /= 10;
                }

                char[] var6 = new char[var4];
                var6[0] = '+';

                for (int var7 = var4 - 1; var7 > 0; --var7) {
                    int var8 = var2;
                    var2 /= 10;
                    int var9 = var8 - var2 * 10;
                    if (var9 >= 10) {
                        var6[var7] = (char) (var9 + 87);
                    } else {
                        var6[var7] = (char) (var9 + 48);
                    }
                }

                var3 = new String(var6);
            } else {
                var3 = Integer.toString(var0, 10);
            }

            return var3;
        }
        return Integer.toString(var0);
    }

    public static void method694(PathingEntity entity) {
        if (entity.forceMovementEndCycle == client.engineCycle
                || entity.animation == -1
                || entity.animationDelay != 0
                || entity.animationFrameCycle + 1 > AnimationSequence.get(entity.animation).frameLengths[entity.animationFrame]) {
            int var1 = entity.forceMovementEndCycle - entity.forceMovementStartCycle;
            int var2 = client.engineCycle - entity.forceMovementStartCycle;
            int var3 = entity.boundSize + entity.startX * 128;
            int var4 = entity.boundSize + entity.startY * 128;
            int var5 = entity.boundSize + entity.endX * 128;
            int var6 = entity.boundSize + entity.endY * 128;
            entity.absoluteX = (var2 * var5 + var3 * (var1 - var2)) / var1;
            entity.absoluteY = (var6 * var2 + var4 * (var1 - var2)) / var1;
        }

        entity.anInt2022 = 0;
        entity.orientation = entity.anInt2019;
        entity.turnOrientation = entity.orientation;
    }

    public void update(int cycle, int width, int currWidth, int currCycle) {
        HitUpdate pending = null;
        int var6 = 0;

        for (HitUpdate h = hitsplats.head(); h != null; h = hitsplats.next()) {
            ++var6;
            if (h.startCycle == cycle) {
                h.set(cycle, width, currWidth, currCycle);
                return;
            }

            if (h.startCycle <= cycle) {
                pending = h;
            }
        }

        if (pending == null) {
            if (var6 < 4) {
                hitsplats.addLast(new HitUpdate(cycle, width, currWidth, currCycle));
            }

        } else {
            LinkedList.insertBefore(new HitUpdate(cycle, width, currWidth, currCycle), pending);
            if (var6 >= 4) {
                hitsplats.head().unlink();
            }

        }
    }

    public HitUpdate method695(int var1) {
        HitUpdate var2 = hitsplats.head();
        if (var2 != null && var2.startCycle <= var1) {
            for (HitUpdate var3 = hitsplats.next(); var3 != null && var3.startCycle <= var1; var3 = hitsplats.next()) {
                var2.unlink();
                var2 = var3;
            }

            if (definition.anInt367 + var2.currentCycle + var2.startCycle > var1) {
                return var2;
            }
            var2.unlink();
            return null;
        }
        return null;
    }

    public boolean method693() {
        return hitsplats._isEmpty();
    }
}
