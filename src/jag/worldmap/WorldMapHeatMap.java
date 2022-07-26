package jag.worldmap;

import jag.Bounds;
import jag.Login;
import jag.graphics.Sprite;
import jag.script.ClientScriptFrame;
import jag.statics.Statics35;

import java.util.HashMap;

public class WorldMapHeatMap {
    public static int[] anIntArray1520;
    final HashMap<Integer, Sprite> sprites;
    final int[] anIntArray1449;
    final int[] anIntArray1448;
    final Bounds size;
    int anInt1447;

    public WorldMapHeatMap() {
        sprites = new HashMap<>();
        size = new Bounds(0, 0);
        anIntArray1449 = new int[2048];
        anIntArray1448 = new int[2048];
        anInt1447 = 0;
        anIntArray1520 = new int[2000];
        int var1 = 0;
        int var2 = 240;

        int var4;
        for (byte var3 = 12; var1 < 16; var2 -= var3) {
            var4 = ClientScriptFrame.method295((float) var2 / 360.0F, 0.9998999834060669D, 0.075F + 0.425F * (float) var1 / 16.0F);
            anIntArray1520[var1] = var4;
            ++var1;
        }

        var2 = 48;

        for (int var6 = var2 / 6; var1 < anIntArray1520.length; var2 -= var6) {
            var4 = var1 * 2;

            for (int var5 = ClientScriptFrame.method295((float) var2 / 360.0F, 0.9998999834060669D, 0.5D); var1 < var4 && var1 < anIntArray1520.length; ++var1) {
                anIntArray1520[var1] = var5;
            }
        }

    }

    public static void method1402() {
        Login.worldSelectorOpen = false;
        Login.leftTitleSprite.renderAt(Login.paddingX, 0);
        Login.rightTitleSprite.renderAt(Login.paddingX + 382, 0);
        Login.logoSprite.renderAt(Login.paddingX + 382 - Login.logoSprite.anInt378 / 2, 18);
    }

    Sprite computeIfAbsent(int id) {
        if (!sprites.containsKey(id)) {
            compute(id);
        }

        return sprites.get(id);
    }

    void compute(int var1) {
        int var2 = var1 * 2 + 1;
        double[] var3 = Statics35.method1172(0.0D, (float) var1 / 3.0F, var1);
        double var4 = var3[var1] * var3[var1];
        int[] var6 = new int[var2 * var2];
        boolean var7 = false;

        for (int var8 = 0; var8 < var2; ++var8) {
            for (int var9 = 0; var9 < var2; ++var9) {
                int var10 = var6[var9 + var8 * var2] = (int) (256.0D * (var3[var8] * var3[var9] / var4));
                if (!var7 && var10 > 0) {
                    var7 = true;
                }
            }
        }

        Sprite var11 = new Sprite(var6, var2, var2);
        sprites.put(var1, var11);
    }

    void method991(Sprite var1, Sprite var2, Bounds var3) {
        if (var3.width != 0 && var3.height != 0) {
            int var4 = 0;
            int var5 = 0;
            if (var3.x == 0) {
                var4 = var1.width - var3.width;
            }

            if (var3.y == 0) {
                var5 = var1.height - var3.height;
            }

            int var6 = var4 + var5 * var1.width;
            int var7 = var3.x + var2.width * var3.y;

            for (int var8 = 0; var8 < var3.height; ++var8) {
                for (int var9 = 0; var9 < var3.width; ++var9) {
                    int[] var10000 = var2.pixels;
                    int var10001 = var7++;
                    var10000[var10001] += var1.pixels[var6++];
                }

                var6 += var1.width - var3.width;
                var7 += var2.width - var3.width;
            }

        }
    }

    public final void method992(int var1, int var2, Sprite var3, float var4) {
        int var5 = (int) (var4 * 18.0F);
        Sprite var6 = computeIfAbsent(var5);
        int var7 = var5 * 2 + 1;
        Bounds var8 = new Bounds(0, 0, var3.width, var3.height);
        Bounds var9 = new Bounds(0, 0);
        size.setSize(var7, var7);
        System.nanoTime();

        int var10;
        int var11;
        int var12;
        for (var10 = 0; var10 < anInt1447; ++var10) {
            var11 = anIntArray1449[var10];
            var12 = anIntArray1448[var10];
            int var13 = (int) ((float) (var11 - var1) * var4) - var5;
            int var14 = (int) ((float) var3.height - var4 * (float) (var12 - var2)) - var5;
            size.setLocation(var13, var14);
            size.add(var8, var9);
            method991(var6, var3, var9);
        }

        System.nanoTime();
        System.nanoTime();

        for (var10 = 0; var10 < var3.pixels.length; ++var10) {
            if (var3.pixels[var10] == 0) {
                var3.pixels[var10] = -16777216;
            } else {
                var11 = (var3.pixels[var10] + 64 - 1) / 256;
                if (var11 <= 0) {
                    var3.pixels[var10] = -16777216;
                } else {
                    if (var11 > anIntArray1520.length) {
                        var11 = anIntArray1520.length;
                    }

                    var12 = anIntArray1520[var11 - 1];
                    var3.pixels[var10] = -16777216 | var12;
                }
            }
        }

        System.nanoTime();
    }

    public final void method993() {
        anInt1447 = 0;
    }

    public final void method994(int var1, int var2) {
        if (anInt1447 < anIntArray1449.length) {
            anIntArray1449[anInt1447] = var1;
            anIntArray1448[anInt1447] = var2;
            ++anInt1447;
        }
    }
}
