package jag.commons.crypt;

public class Base37 {

    public static final char[] CHARSET = new char[]{'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final long[] aLongArray1571;

    static {
        aLongArray1571 = new long[12];
        for (int i = 0; i < aLongArray1571.length; ++i) {
            aLongArray1571[i] = (long) Math.pow(37.0D, i);
        }
    }

    public static long encode(CharSequence entry) {
        long base = 0L;
        int length = entry.length();
        for (int i = 0; i < length; ++i) {
            base *= 37L;
            char c = entry.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                base += c + 1 - 65;
            } else if (c >= 'a' && c <= 'z') {
                base += c + 1 - 97;
            } else if (c >= '0' && c <= '9') {
                base += c + 27 - 48;
            }

            if (base >= 177917621779460413L) {
                break;
            }
        }

        while (base % 37L == 0L && 0L != base) {
            base /= 37L;
        }
        return base;
    }

    public static String decode(long encoded) {
        if (encoded > 0L && encoded < 6582952005840035281L) {
            if (0L == encoded % 37L) {
                return null;
            }

            int len = 0;
            for (long var3 = encoded; 0L != var3; var3 /= 37L) {
                ++len;
            }

            StringBuilder builder = new StringBuilder(len);
            while (encoded != 0L) {
                long old = encoded;
                encoded /= 37L;
                char character = CHARSET[(int) (old - encoded * 37L)];
                if (character == '_') {
                    int idx = builder.length() - 1;
                    builder.setCharAt(idx, Character.toUpperCase(builder.charAt(idx)));
                    character = 160;
                }
                builder.append(character);
            }

            builder.reverse();
            builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));
            return builder.toString();
        }
        return null;
    }
}
