package jagex.core.stringtools;

import java.util.Arrays;

public class Base64 {

  private static final char[] STANDARD_BASE64_CHARS;

  private static final char[] URL_SAFE_BASE64_CHARS;
  private static final char[] FILE_SAFE_BASE64_CHARS;
  private static final int[] BASE64_DECODE_TABLE;

  static {
    STANDARD_BASE64_CHARS = initializeStandardBase64Chars();
    URL_SAFE_BASE64_CHARS = initializeUrlSafeBase64Chars();
    FILE_SAFE_BASE64_CHARS = initializeFileSafeBase64Chars();
    BASE64_DECODE_TABLE = initializeBase64DecodeTable();
  }

  private static char[] initializeStandardBase64Chars() {
    char[] chars = new char[64];
    int index = 0;

    for (int i = 0; i < 26; ++i) {
      chars[index++] = (char) (i + 'A');
    }

    for (int i = 26; i < 52; ++i) {
      chars[index++] = (char) (i + 'a' - 26);
    }

    for (int i = 52; i < 62; ++i) {
      chars[index++] = (char) (i + '0' - 52);
    }

    chars[62] = '+';
    chars[63] = '/';

    return chars;
  }

  private static char[] initializeUrlSafeBase64Chars() {
    char[] chars = new char[64];
    int index = 0;

    for (int i = 0; i < 26; ++i) {
      chars[index++] = (char) (i + 'A');
    }

    for (int i = 26; i < 52; ++i) {
      chars[index++] = (char) (i + 'a' - 26);
    }

    for (int i = 52; i < 62; ++i) {
      chars[index++] = (char) (i + '0' - 52);
    }

    chars[62] = '*';
    chars[63] = '-';

    return chars;
  }

  private static char[] initializeFileSafeBase64Chars() {
    char[] chars = new char[64];
    int index = 0;

    for (int i = 0; i < 26; ++i) {
      chars[index++] = (char) (i + 'A');
    }

    for (int i = 26; i < 52; ++i) {
      chars[index++] = (char) (i + 'a' - 26);
    }

    for (int i = 52; i < 62; ++i) {
      chars[index++] = (char) (i + '0' - 52);
    }

    chars[62] = '-';
    chars[63] = '_';

    return chars;
  }

  private static int[] initializeBase64DecodeTable() {
    int[] decodeTable = new int[128];

    Arrays.fill(decodeTable, -1);

    for (int i = 'A'; i <= 'Z'; ++i) {
      decodeTable[i] = i - 'A';
    }

    for (int i = 'a'; i <= 'z'; ++i) {
      decodeTable[i] = i - 'a' + 26;
    }

    for (int i = '0'; i <= '9'; ++i) {
      decodeTable[i] = i - '0' + 52;
    }

    decodeTable['+'] = 62;
    decodeTable['*'] = 62;
    decodeTable['/'] = 63;
    decodeTable['-'] = 63;

    return decodeTable;
  }

  public static String encode(byte[] data, int start, int len) {
    StringBuilder encoded = new StringBuilder();

    for (int i = start; i < len + start; i += 3) {
      int b1 = data[i] & 0xFF;
      encoded.append(STANDARD_BASE64_CHARS[b1 >>> 2]);

      if (i < len - 1) {
        int b2 = data[i + 1] & 0xFF;
        encoded.append(STANDARD_BASE64_CHARS[(b1 & 3) << 4 | b2 >>> 4]);

        if (i < len - 2) {
          int b3 = data[i + 2] & 0xFF;
          encoded.append(STANDARD_BASE64_CHARS[(b2 & 15) << 2 | b3 >>> 6]);
          encoded.append(STANDARD_BASE64_CHARS[b3 & 63]);
        } else {
          encoded.append(STANDARD_BASE64_CHARS[(b2 & 15) << 2]);
          encoded.append("=");
        }
      } else {
        encoded.append(STANDARD_BASE64_CHARS[(b1 & 3) << 4]);
        encoded.append("==");
      }
    }

    return encoded.toString();
  }
}
