package jagex.core.stringtools;

import jagex.core.compression.huffman.Huffman;
import jagex.messaging.Buffer;

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

  public static String escapeAngleBrackets(String var0) {
    int var1 = var0.length();
    int var2 = 0;

    for (int var3 = 0; var3 < var1; ++var3) {
      char var4 = var0.charAt(var3);
      if (var4 == '<' || var4 == '>') {
        var2 += 3;
      }
    }

    StringBuilder var5 = new StringBuilder(var1 + var2);

    for (int var7 = 0; var7 < var1; ++var7) {
      char var6 = var0.charAt(var7);
      if (var6 == '<') {
        var5.append("<lt>");
      } else if (var6 == '>') {
        var5.append("<gt>");
      } else {
        var5.append(var6);
      }
    }

    return var5.toString();
  }

  public static String toTitleCase(String text) {
    int textLength = text.length();
    char[] transformedText = new char[textLength];
    int capitalizationState = 2;

    for (int index = 0; index < textLength; ++index) {
      char currentChar = text.charAt(index);
      if (capitalizationState == 0) {
        currentChar = Character.toLowerCase(currentChar);
      } else if (capitalizationState == 2 || Character.isUpperCase(currentChar)) {
        currentChar = toTitleCase(currentChar);
      }

      if (Character.isLetter(currentChar)) {
        capitalizationState = 0;
      } else if (currentChar != '.' && currentChar != '?' && currentChar != '!') {
        if (Character.isWhitespace(currentChar)) {
          if (capitalizationState != 2) {
            capitalizationState = 1;
          }
        } else {
          capitalizationState = 1;
        }
      } else {
        capitalizationState = 2;
      }

      transformedText[index] = currentChar;
    }

    return new String(transformedText);
  }

  public static String decompressText(Buffer buffer) {
    try {
      int length = buffer.gSmarts();
      if (length > 32767) {
        length = 32767;
      }

      byte[] compressedData = new byte[length];
      buffer.pos += Huffman.instance.decompress(buffer.payload, buffer.pos, compressedData, 0, length);
      return Buffer.readStringFromBytes(compressedData, 0, length);
    } catch (Exception exception) {
      return "Cabbage";
    }
  }

  public static char toTitleCase(char c) {
    // Preserve special characters 'µ' (Unicode 181) and 'ƒ' (Unicode 402)
    return (c != 181 && c != 402) ? Character.toTitleCase(c) : c;
  }
}
