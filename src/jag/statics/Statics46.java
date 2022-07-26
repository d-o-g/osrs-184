package jag.statics;

import jag.ClientLocale;
import jag.audi.Node_Sub19;
import jag.audi.vorbis.Decimator;
import jag.game.GameShell;
import jag.script.ClientScriptFrame;
import jag.script.ScriptEvent;

import java.util.Calendar;

public class Statics46 {
    public static final String[] aStringArray448 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final double aDouble437 = Math.log(2.0D);
    public static final int[] anIntArray445 = new int[5];
    public static final int[][] anIntArrayArray443 = new int[5][5000];
    public static final ClientScriptFrame[] A_RUNE_SCRIPT_FRAME_ARRAY_435 = new ClientScriptFrame[50];
    public static final Calendar aCalendar438 = Calendar.getInstance();
    public static int anInt436 = 0;
    public static boolean aBoolean439 = false;
    public static boolean aBoolean449 = false;
    public static int anInt440 = 0;
    public static Decimator aClass98_446;
    public static int anInt442;
    public static int anInt441;
    public static int[] anIntArray447;

    static {
        ScriptEvent.intStack = new int[1000];
        ScriptEvent.stringStack = new String[1000];
    }

    public static boolean method297(String var0, int var1) {
        return GameShell.method191(var0, var1);
    }

    public static int method298(CharSequence var0, CharSequence var1, ClientLocale var2) {
        int var3 = var0.length();
        int var4 = var1.length();
        int var5 = 0;
        int var6 = 0;
        char var7 = 0;
        char var8 = 0;

        while (var5 - var7 < var3 || var6 - var8 < var4) {
            if (var5 - var7 >= var3) {
                return -1;
            }

            if (var6 - var8 >= var4) {
                return 1;
            }

            char var9;
            if (var7 != 0) {
                var9 = var7;
                boolean var14 = false;
            } else {
                var9 = var0.charAt(var5++);
            }

            char var10;
            if (var8 != 0) {
                var10 = var8;
                boolean var15 = false;
            } else {
                var10 = var1.charAt(var6++);
            }

            var7 = Statics52.method347(var9);
            var8 = Statics52.method347(var10);
            var9 = Statics47.method322(var9, var2);
            var10 = Statics47.method322(var10, var2);
            if (var10 != var9 && Character.toUpperCase(var9) != Character.toUpperCase(var10)) {
                var9 = Character.toLowerCase(var9);
                var10 = Character.toLowerCase(var10);
                if (var10 != var9) {
                    return Node_Sub19.method858(var9, var2) - Node_Sub19.method858(var10, var2);
                }
            }
        }

        int var16 = Math.min(var3, var4);

        for (int var17 = 0; var17 < var16; ++var17) {
            if (var2 == ClientLocale.FR) {
                var5 = var3 - 1 - var17;
                var6 = var4 - 1 - var17;
            } else {
                var6 = var17;
                var5 = var17;
            }

            char var11 = var0.charAt(var5);
            char var12 = var1.charAt(var6);
            if (var11 != var12 && Character.toUpperCase(var11) != Character.toUpperCase(var12)) {
                var11 = Character.toLowerCase(var11);
                var12 = Character.toLowerCase(var12);
                if (var12 != var11) {
                    return Node_Sub19.method858(var11, var2) - Node_Sub19.method858(var12, var2);
                }
            }
        }

        int var17 = var3 - var4;
        if (var17 != 0) {
            return var17;
        }

        for (int var18 = 0; var18 < var16; ++var18) {
            char var12 = var0.charAt(var18);
            char var13 = var1.charAt(var18);
            if (var13 != var12) {
                return Node_Sub19.method858(var12, var2) - Node_Sub19.method858(var13, var2);
            }
        }

        return 0;
    }
}
