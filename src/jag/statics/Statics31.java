package jag.statics;

public class Statics31 {

    public static void method1139(int[] var0, int var1, int var2) {
        for (var2 = var2 + var1 - 7; var1 < var2; var0[var1++] = 0) {
            var0[var1++] = 0;
            var0[var1++] = 0;
            var0[var1++] = 0;
            var0[var1++] = 0;
            var0[var1++] = 0;
            var0[var1++] = 0;
            var0[var1++] = 0;
        }

        for (var2 += 7; var1 < var2; var0[var1++] = 0) {
        }
    }

    public static void method1140(byte[] array, int var1, byte[] var2, int var3, int length) {
        if (var2 == array) {
            if (var3 == var1) {
                return;
            }

            if (var3 > var1 && var3 < length + var1) {
                --length;
                var1 += length;
                var3 += length;
                length = var1 - length;

                length += 7;
                while (var1 >= length) {
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                    var2[var3--] = array[var1--];
                }

                length -= 7;
                while (var1 >= length) {
                    var2[var3--] = array[var1--];
                }

                return;
            }
        }

        length += var1;

        length -= 7;
        while (var1 < length) {
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
            var2[var3++] = array[var1++];
        }

        length += 7;
        while (var1 < length) {
            var2[var3++] = array[var1++];
        }

    }
}
