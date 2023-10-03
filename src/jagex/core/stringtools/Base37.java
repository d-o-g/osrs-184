package jagex.core.stringtools;

public class Base37 {

  public static final char[] CHARSET = new char[]{'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static final long[] POWERS_OF_37 = new long[12];

  static {
    for (int i = 0; i < POWERS_OF_37.length; ++i) {
      POWERS_OF_37[i] = (long) Math.pow(37.0D, i);
    }
  }

  public static long encode(CharSequence entry) {
    long base = 0L;
    int length = entry.length();
    for (int i = 0; i < length; ++i) {
      base *= 37L;
      char c = entry.charAt(i);
      if (c >= 'A' && c <= 'Z') {
        base += c - 'A' + 1;
      } else if (c >= 'a' && c <= 'z') {
        base += c - 'a' + 1;
      } else if (c >= '0' && c <= '9') {
        base += c - '0' + 27;
      }

      if (base >= 0x27817226572713dL) {
        break;
      }
    }

    while (base % 37L == 0L && 0L != base) {
      base /= 37L;
    }

    return base;
  }

  public static String decode(long encoded) {
    if (encoded > 0L && encoded < 0x5b5b57f8a98a5dd1L) {
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
