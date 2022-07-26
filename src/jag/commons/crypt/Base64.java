package jag.commons.crypt;

public class Base64 {

    public static final char[] BASE64;

    static final char[] aCharArray1635;
    static final char[] aCharArray1633;
    static final int[] anIntArray1632;

    static {
        BASE64 = new char[64];

        int var0;
        for (var0 = 0; var0 < 26; ++var0) {
            BASE64[var0] = (char) (var0 + 65);
        }

        for (var0 = 26; var0 < 52; ++var0) {
            BASE64[var0] = (char) (var0 + 97 - 26);
        }

        for (var0 = 52; var0 < 62; ++var0) {
            BASE64[var0] = (char) (var0 + 48 - 52);
        }

        BASE64[62] = '+';
        BASE64[63] = '/';
        aCharArray1635 = new char[64];

        for (var0 = 0; var0 < 26; ++var0) {
            aCharArray1635[var0] = (char) (var0 + 65);
        }

        for (var0 = 26; var0 < 52; ++var0) {
            aCharArray1635[var0] = (char) (var0 + 97 - 26);
        }

        for (var0 = 52; var0 < 62; ++var0) {
            aCharArray1635[var0] = (char) (var0 + 48 - 52);
        }

        aCharArray1635[62] = '*';
        aCharArray1635[63] = '-';
        aCharArray1633 = new char[64];

        for (var0 = 0; var0 < 26; ++var0) {
            aCharArray1633[var0] = (char) (var0 + 65);
        }

        for (var0 = 26; var0 < 52; ++var0) {
            aCharArray1633[var0] = (char) (var0 + 97 - 26);
        }

        for (var0 = 52; var0 < 62; ++var0) {
            aCharArray1633[var0] = (char) (var0 + 48 - 52);
        }

        aCharArray1633[62] = '-';
        aCharArray1633[63] = '_';
        anIntArray1632 = new int[128];

        for (var0 = 0; var0 < anIntArray1632.length; ++var0) {
            anIntArray1632[var0] = -1;
        }

        for (var0 = 65; var0 <= 90; ++var0) {
            anIntArray1632[var0] = var0 - 65;
        }

        for (var0 = 97; var0 <= 122; ++var0) {
            anIntArray1632[var0] = var0 - 97 + 26;
        }

        for (var0 = 48; var0 <= 57; ++var0) {
            anIntArray1632[var0] = var0 - 48 + 52;
        }

        anIntArray1632[43] = 62;
        anIntArray1632[42] = 62;
        anIntArray1632[47] = 63;
        anIntArray1632[45] = 63;
    }

    public static String encode(byte[] data, int start, int len) {
        StringBuilder var3 = new StringBuilder();

        for (int i = start; i < len + start; i += 3) {
            int var5 = data[i] & 255;
            var3.append(BASE64[var5 >>> 2]);
            if (i < len - 1) {
                int var6 = data[i + 1] & 255;
                var3.append(BASE64[(var5 & 3) << 4 | var6 >>> 4]);
                if (i < len - 2) {
                    int var7 = data[i + 2] & 255;
                    var3.append(BASE64[(var6 & 15) << 2 | var7 >>> 6]).append(BASE64[var7 & 63]);
                } else {
                    var3.append(BASE64[(var6 & 15) << 2]).append("=");
                }
            } else {
                var3.append(BASE64[(var5 & 3) << 4]).append("==");
            }
        }

        return var3.toString();
    }
}
