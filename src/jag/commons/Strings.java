package jag.commons;

public class Strings {

    public static boolean parseInt(CharSequence var0) {
        boolean var3 = false;
        boolean var4 = false;
        int var5 = 0;
        int var6 = var0.length();

        for (int var7 = 0; var7 < var6; ++var7) {
            char var8 = var0.charAt(var7);
            if (var7 == 0) {
                if (var8 == '-') {
                    var3 = true;
                    continue;
                }

                if (var8 == '+') {
                    continue;
                }
            }

            int var10;
            if (var8 >= '0' && var8 <= '9') {
                var10 = var8 - '0';
            } else if (var8 >= 'A' && var8 <= 'Z') {
                var10 = var8 - '7';
            } else {
                if (var8 < 'a' || var8 > 'z') {
                    return false;
                }

                var10 = var8 - 'W';
            }

            if (var10 >= 10) {
                return false;
            }

            if (var3) {
                var10 = -var10;
            }

            int var9 = var10 + var5 * 10;
            if (var9 / 10 != var5) {
                return false;
            }

            var5 = var9;
            var4 = true;
        }

        return var4;
    }

    public static int method702(CharSequence var0) {
        int var1 = var0.length();
        int var2 = 0;

        for (int var3 = 0; var3 < var1; ++var3) {
            char var4 = var0.charAt(var3);
            if (var4 <= 127) {
                ++var2;
            } else if (var4 <= 2047) {
                var2 += 2;
            } else {
                var2 += 3;
            }
        }

        return var2;
    }
}
