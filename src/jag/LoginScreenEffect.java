package jag;

import jag.game.client;
import jag.graphics.IndexedSprite;
import jag.graphics.JagGraphics;
import jag.statics.Statics35;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginScreenEffect {

    public static Bounds credentialsBoxBounds;

    final IndexedSprite[] sprites;

    final int[] argb;

    int[] anIntArray410;
    int[] anIntArray423;
    int[] anIntArray414;
    int[] anIntArray411;
    int[] anIntArray420;
    int[] anIntArray409;
    int[] anIntArray412;
    int[] anIntArray421;

    int anInt422;
    int anInt416;
    int anInt419;
    int anInt415;
    int anInt408;

    public LoginScreenEffect(IndexedSprite[] sprites) {
        argb = new int[256];
        anInt415 = 0;
        anInt416 = 0;
        anInt408 = 0;
        anInt422 = 0;
        anInt419 = 0;
        this.sprites = sprites;
        method285();
    }

    public static boolean method278(char var0) {
        if ((var0 <= 0 || var0 >= 128) && (var0 < 160 || var0 > 255)) {
            if (var0 != 0) {
                char[] var1 = Statics35.cp1252AsciiExtension;

                for (char var3 : var1) {
                    if (var0 == var3) {
                        return true;
                    }
                }
            }

            return false;
        }
        return true;
    }

    public static boolean method286(String var0) {
        if (var0 == null) {
            return false;
        }
        try {
            new URL(var0);
            return true;
        } catch (MalformedURLException var2) {
            return false;
        }
    }

    void method285() {
        anIntArray414 = new int[256];

        for (int i = 0; i < 64; ++i) {
            anIntArray414[i] = i * 262144;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray414[i + 64] = i * 1024 + 16711680;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray414[i + 128] = i * 4 + 16776960;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray414[i + 192] = 16777215;
        }

        anIntArray410 = new int[256];

        for (int i = 0; i < 64; ++i) {
            anIntArray410[i] = i * 1024;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray410[i + 64] = i * 4 + 65280;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray410[i + 128] = i * 262144 + 65535;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray410[i + 192] = 16777215;
        }

        anIntArray409 = new int[256];

        for (int i = 0; i < 64; ++i) {
            anIntArray409[i] = i * 4;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray409[i + 64] = i * 262144 + 255;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray409[i + 128] = i * 1024 + 16711935;
        }

        for (int i = 0; i < 64; ++i) {
            anIntArray409[i + 192] = 16777215;
        }

        anIntArray423 = new int[256];
        anInt422 = 0;
        anIntArray420 = new int[32768];
        anIntArray412 = new int[32768];
        method284(null);
        anIntArray411 = new int[32768];
        anIntArray421 = new int[32768];
    }

    final void method280(int var1, int[] var2) {
        int var3 = anIntArray423.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            if (var1 > 768) {
                anIntArray423[var4] = method281(anIntArray414[var4], var2[var4], 1024 - var1);
            } else if (var1 > 256) {
                anIntArray423[var4] = var2[var4];
            } else {
                anIntArray423[var4] = method281(var2[var4], anIntArray414[var4], 256 - var1);
            }
        }

    }

    final void method275(int var1) {
        int var2 = 0;

        for (int var3 = 1; var3 < 255; ++var3) {
            int var4 = (256 - var3) * argb[var3] / 256;
            int var5 = var4 + var1;
            int var6 = 0;
            int var7 = 128;
            if (var5 < 0) {
                var6 = -var5;
                var5 = 0;
            }

            if (var5 + 128 >= client.graphicsProvider.width) {
                var7 = client.graphicsProvider.width - var5;
            }

            int var8 = var5 + (var3 + 8) * client.graphicsProvider.width;
            var2 += var6;

            for (int var9 = var6; var9 < var7; ++var9) {
                int var10 = anIntArray411[var2++];
                int var11 = var8 % JagGraphics.drawingAreaWidth;
                if (var10 != 0 && var11 >= JagGraphics.drawingAreaLeft && var11 < JagGraphics.drawingAreaBottom) {
                    int var12 = var10;
                    int var13 = 256 - var10;
                    var10 = anIntArray423[var10];
                    int var14 = client.graphicsProvider.anIntArray1818[var8];
                    client.graphicsProvider.anIntArray1818[var8++] = -16777216 | ((var14 & 16711935) * var13 + (var10 & 16711935) * var12 & -16711936) + (var12 * (var10 & 65280) + var13 * (var14 & 65280) & 16711680) >> 8;
                } else {
                    ++var8;
                }
            }

            var2 += 128 - var7;
        }

    }

    final int method281(int var1, int var2, int var3) {
        int var4 = 256 - var3;
        return (var3 * (var2 & 65280) + var4 * (var1 & 65280) & 16711680) + (var4 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
    }

    final void method282(int alpha) {
        anInt422 += alpha * 128;
        int var2;
        if (anInt422 > anIntArray420.length) {
            anInt422 -= anIntArray420.length;
            var2 = (int) (Math.random() * 12.0D);
            method284(sprites[var2]);
        }

        var2 = 0;
        int var3 = alpha * 128;
        int var4 = (256 - alpha) * 128;

        int var6;
        for (int var5 = 0; var5 < var4; ++var5) {
            var6 = anIntArray411[var2 + var3] - anIntArray420[var2 + anInt422 & anIntArray420.length - 1] * alpha / 6;
            if (var6 < 0) {
                var6 = 0;
            }

            anIntArray411[var2++] = var6;
        }

        byte var15 = 10;
        var6 = 128 - var15;

        int var7;
        int var10;
        for (var7 = 256 - alpha; var7 < 256; ++var7) {
            int var8 = var7 * 128;

            for (int var9 = 0; var9 < 128; ++var9) {
                var10 = (int) (Math.random() * 100.0D);
                if (var10 < 50 && var9 > var15 && var9 < var6) {
                    anIntArray411[var9 + var8] = 255;
                } else {
                    anIntArray411[var8 + var9] = 0;
                }
            }
        }

        if (anInt416 * 16 > 0) {
            anInt416 = anInt416 * 16 - alpha * 4;
        }

        if (anInt408 * 16 > 0) {
            anInt408 = anInt408 * 16 - alpha * 4;
        }

        if (anInt416 * 16 == 0 && anInt408 * 16 == 0) {
            var7 = (int) (Math.random() * (double) (2000 / alpha));
            if (var7 == 0) {
                anInt416 = 1024;
            }

            if (var7 == 1) {
                anInt408 = 1024;
            }
        }

        for (int i = 0; i < 256 - alpha; i++) {
            argb[i] = argb[i + alpha];
        }

        for (int i = 256 - alpha; i < 256; i++) {
            argb[i] = (int) (Math.sin((double) anInt415 / 14.0D) * 16.0D + Math.sin((double) anInt415 / 15.0D) * 14.0D + Math.sin((double) anInt415 / 16.0D) * 12.0D);
            ++anInt415;
        }

        var7 = ((client.ticks & 1) + alpha) / 2;

        if (var7 > 0) {
            short var16 = 128;
            byte var17 = 2;
            var10 = 128 - var17 - var17;

            int var11;
            int var12;
            int var13;
            for (var11 = 0; var11 < alpha * 100; ++var11) {
                var12 = (int) (Math.random() * (double) var10) + var17;
                var13 = (int) (Math.random() * (double) var16) + var16;
                anIntArray411[var12 + (var13 << 7)] = 192;
            }


            int var14;
            for (var11 = 0; var11 < 256; ++var11) {
                var12 = 0;
                var13 = var11 * 128;

                for (var14 = -var7; var14 < 128; ++var14) {
                    if (var14 + var7 < 128) {
                        var12 += anIntArray411[var7 + var14 + var13];
                    }

                    if (var14 - (var7 + 1) >= 0) {
                        var12 -= anIntArray411[var13 + var14 - (var7 + 1)];
                    }

                    if (var14 >= 0) {
                        anIntArray421[var14 + var13] = var12 / (var7 * 2 + 1);
                    }
                }
            }

            for (var11 = 0; var11 < 128; ++var11) {
                var12 = 0;

                for (var13 = -var7; var13 < 256; ++var13) {
                    var14 = var13 * 128;
                    if (var7 + var13 < 256) {
                        var12 += anIntArray421[var7 * 128 + var14 + var11];
                    }

                    if (var13 - (var7 + 1) >= 0) {
                        var12 -= anIntArray421[var14 + var11 - (var7 + 1) * 128];
                    }

                    if (var13 >= 0) {
                        anIntArray411[var11 + var14] = var12 / (var7 * 2 + 1);
                    }
                }
            }
        }


    }

    final void method279(int var1) {
        int var2 = anIntArray423.length;
        if (anInt416 * 16 > 0) {
            method280(anInt416 * 16, anIntArray410);
        } else if (anInt408 * 16 > 0) {
            method280(anInt408 * 16, anIntArray409);
        } else {
            System.arraycopy(anIntArray414, 0, anIntArray423, 0, var2);
        }

        method275(var1);
    }

    final void method284(IndexedSprite var1) {
        int var2;
        for (var2 = 0; var2 < anIntArray420.length; ++var2) {
            anIntArray420[var2] = 0;
        }

        int var3;
        for (var2 = 0; var2 < 5000; ++var2) {
            var3 = (int) (Math.random() * 128.0D * 256.0D);
            anIntArray420[var3] = (int) (Math.random() * 256.0D);
        }

        int var4;
        int var5;
        for (var2 = 0; var2 < 20; ++var2) {
            for (var3 = 1; var3 < 255; ++var3) {
                for (var4 = 1; var4 < 127; ++var4) {
                    var5 = var4 + (var3 << 7);
                    anIntArray412[var5] = (anIntArray420[var5 + 1] + anIntArray420[var5 + 128] + anIntArray420[var5 - 128] + anIntArray420[var5 - 1]) / 4;
                }
            }

            int[] var8 = anIntArray420;
            anIntArray420 = anIntArray412;
            anIntArray412 = var8;
        }

        if (var1 != null) {
            var2 = 0;

            for (var3 = 0; var3 < var1.anInt377; ++var3) {
                for (var4 = 0; var4 < var1.anInt378; ++var4) {
                    if (var1.indices[var2++] != 0) {
                        var5 = var4 + var1.insetX + 16;
                        int var6 = var3 + var1.insetY + 16;
                        int var7 = var5 + (var6 << 7);
                        anIntArray420[var7] = 0;
                    }
                }
            }
        }

    }

    public void destroy() {
        anIntArray414 = null;
        anIntArray410 = null;
        anIntArray409 = null;
        anIntArray423 = null;
        anIntArray420 = null;
        anIntArray412 = null;
        anIntArray411 = null;
        anIntArray421 = null;
        anInt422 = 0;
    }

    public void render(int x, int cycle) {

        if (anIntArray411 == null) {
            method285();
        }

        if (anInt419 == 0) {
            anInt419 = cycle;
        }

        int var3 = cycle - anInt419;
        if (var3 >= 256) {
            var3 = 0;
        }

        anInt419 = cycle;
        if (var3 > 0) {
            method282(var3);
        }

        method279(x);
    }
}
