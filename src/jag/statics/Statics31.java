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

    public static void generateRandom(byte[] payload, int offset, byte[] random, int start, int length) {
        if (random == payload) {
            if (start == offset) {
                return;
            }

            if (start > offset && start < length + offset) {
                --length;
                offset += length;
                start += length;
                length = offset - length;

                length += 7;
                while (offset >= length) {
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                    random[start--] = payload[offset--];
                }

                length -= 7;
                while (offset >= length) {
                    random[start--] = payload[offset--];
                }

                return;
            }
        }

        length += offset;

        length -= 7;
        while (offset < length) {
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
            random[start++] = payload[offset++];
        }

        length += 7;
        while (offset < length) {
            random[start++] = payload[offset++];
        }

    }
}
