package jag.game.scene.entity;

import jag.game.InterfaceComponent;
import jag.game.type.AnimationSequence;
import jag.game.type.EffectAnimation;

public final class Projectile extends Entity {

    public final AnimationSequence sequence;

    public final int anInt693;
    public final int startX;
    public final int endCycle;
    public final int startY;
    public final int targetDistance;
    public final int floorLevel;
    public final int height;
    public final int startCycle;
    public final int slope;
    public final int targetIndex;
    public final int anInt112;

    public boolean inMotion;

    public double absoluteX;
    public double speedX;
    public double absoluteY;
    public double speedY;
    public double aDouble1660;
    public double heightOffset;
    public double speedZ;
    public double speed;

    public int anInt1659;
    public int anInt1661;
    public int yRotation;
    public int xRotation;

    public Projectile(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
        inMotion = false;
        anInt1659 = 0;
        anInt1661 = 0;
        anInt693 = var1;
        floorLevel = var2;
        startX = var3;
        startY = var4;
        height = var5;
        startCycle = var6;
        endCycle = var7;
        slope = var8;
        targetDistance = var9;
        targetIndex = var10;
        anInt112 = var11;
        inMotion = false;
        int id = EffectAnimation.get(anInt693).animation;
        if (id != -1) {
            sequence = AnimationSequence.get(id);
        } else {
            sequence = null;
        }

    }

    public static boolean method1192(InterfaceComponent var0) {
        if (var0.cs1Types == null) {
            return false;
        }
        for (int var1 = 0; var1 < var0.cs1Types.length; ++var1) {
            int var2 = InterfaceComponent.processCs1(var0, var1);
            int var3 = var0.cs1Values[var1];
            if (var0.cs1Types[var1] == 2) {
                if (var2 >= var3) {
                    return false;
                }
            } else if (var0.cs1Types[var1] == 3) {
                if (var2 <= var3) {
                    return false;
                }
            } else if (var0.cs1Types[var1] == 4) {
                if (var2 == var3) {
                    return false;
                }
            } else if (var3 != var2) {
                return false;
            }
        }

        return true;
    }

    protected Model getModel() {
        EffectAnimation var1 = EffectAnimation.get(anInt693);
        Model var2 = var1.method1004(anInt1659);
        if (var2 == null) {
            return null;
        }
        var2.method582(yRotation);
        return var2;
    }

    public void method1193(int var1) {
        inMotion = true;
        absoluteX += speedX * (double) var1;
        absoluteY += speedY * (double) var1;
        aDouble1660 += (double) var1 * (double) var1 * 0.5D * heightOffset + (double) var1 * speedZ;
        speedZ += heightOffset * (double) var1;
        xRotation = (int) (Math.atan2(speedX, speedY) * 325.949D) + 1024 & 2047;
        yRotation = (int) (Math.atan2(speedZ, speed) * 325.949D) & 2047;
        if (sequence != null) {
            anInt1661 += var1;

            while (true) {
                do {
                    do {
                        if (anInt1661 <= sequence.frameLengths[anInt1659]) {
                            return;
                        }

                        anInt1661 -= sequence.frameLengths[anInt1659];
                        ++anInt1659;
                    } while (anInt1659 < sequence.frameIds.length);

                    anInt1659 -= sequence.loopOffset;
                } while (anInt1659 >= 0 && anInt1659 < sequence.frameIds.length);

                anInt1659 = 0;
            }
        }
    }

    public void target(int var1, int var2, int var3, int var4) {
        double var5;
        if (!inMotion) {
            var5 = var1 - startX;
            double var7 = var2 - startY;
            double var9 = Math.sqrt(var7 * var7 + var5 * var5);
            absoluteX = (double) targetDistance * var5 / var9 + (double) startX;
            absoluteY = (double) targetDistance * var7 / var9 + (double) startY;
            aDouble1660 = height;
        }

        var5 = endCycle + 1 - var4;
        speedX = ((double) var1 - absoluteX) / var5;
        speedY = ((double) var2 - absoluteY) / var5;
        speed = Math.sqrt(speedX * speedX + speedY * speedY);
        if (!inMotion) {
            speedZ = -speed * Math.tan(0.02454369D * (double) slope);
        }

        heightOffset = 2.0D * ((double) var3 - aDouble1660 - var5 * speedZ) / (var5 * var5);
    }
}
