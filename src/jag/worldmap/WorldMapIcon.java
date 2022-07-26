package jag.worldmap;

import jag.opcode.Buffer;

public abstract class WorldMapIcon {
    public static Buffer aBuffer314;
    public final WorldMapPosition min;
    public final WorldMapPosition max;
    public int anInt315;
    public int anInt316;

    WorldMapIcon(WorldMapPosition min, WorldMapPosition max) {
        this.min = min;
        this.max = max;
    }

    public static int method202(byte[] var0, int var1, CharSequence var2) {
        int var3 = var2.length();
        int var4 = var1;

        for (int var5 = 0; var5 < var3; ++var5) {
            char var6 = var2.charAt(var5);
            if (var6 <= 127) {
                var0[var4++] = (byte) var6;
            } else if (var6 <= 2047) {
                var0[var4++] = (byte) (192 | var6 >> 6);
                var0[var4++] = (byte) (128 | var6 & '?');
            } else {
                var0[var4++] = (byte) (224 | var6 >> '\f');
                var0[var4++] = (byte) (128 | var6 >> 6 & 63);
                var0[var4++] = (byte) (128 | var6 & '?');
            }
        }

        return var4 - var1;
    }

    public static boolean method196(char var0) {
        return var0 >= '0' && var0 <= '9';
    }

    boolean method193(int var1, int var2) {
        if (!isUsingMapFunction()) {
            return false;
        }

        WorldMapFunction function = WorldMapFunction.get(getMapFunction());
        int var4 = getWidth();
        int var5 = getHeight();
        switch (function.hAlign.type) {
            case 0:
                if (var1 >= anInt315 && var1 < var4 + anInt315) {
                    break;
                }

                return false;
            case 1:
                if (var1 > anInt315 - var4 && var1 <= anInt315) {
                    break;
                }

                return false;
            case 2:
                if (var1 < anInt315 - var4 / 2 || var1 > var4 / 2 + anInt315) {
                    return false;
                }
        }

        switch (function.vAlign.type) {
            case 0:
                if (var2 <= anInt316 - var5 || var2 > anInt316) {
                    return false;
                }
                break;
            case 1:
                if (var2 < anInt316 || var2 >= var5 + anInt316) {
                    return false;
                }
                break;
            case 2:
                if (var2 < anInt316 - var5 / 2 || var2 > var5 / 2 + anInt316) {
                    return false;
                }
        }

        return true;
    }

    public abstract WorldMapLabel getLabel();

    public boolean isUsingMapFunction() {
        return getMapFunction() >= 0;
    }

    public abstract int getMapFunction();

    boolean method203(int var1, int var2) {
        WorldMapLabel var3 = getLabel();
        if (var3 == null) {
            return false;
        }
        if (var1 >= anInt315 - var3.anInt342 / 2 && var1 <= var3.anInt342 / 2 + anInt315) {
            return var2 >= anInt316 && var2 <= anInt316 + var3.anInt339;
        }
        return false;
    }

    abstract int getWidth();

    abstract int getHeight();

    boolean method200(int var1, int var2) {
        if (method193(var1, var2)) {
            return true;
        }
        return method203(var1, var2);
    }
}
