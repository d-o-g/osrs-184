package jagex.core.stringtools;

import jagex.core.compression.huffman.Huffman;
import jagex.messaging.Buffer;
import jagex.oldscape.ClientParameter;
import jagex.oldscape.client.client;
import jagex.oldscape.script.ScriptEvent;

public class Strings {

  public static final char[] SPECIAL_CHARACTERS = new char[]{' ', ' ', '_', '-', 'à', 'á', 'â', 'ä', 'ã', 'À', 'Á', 'Â', 'Ä', 'Ã', 'è', 'é', 'ê', 'ë', 'È', 'É', 'Ê', 'Ë', 'í', 'î', 'ï', 'Í', 'Î', 'Ï', 'ò', 'ó', 'ô', 'ö', 'õ', 'Ò', 'Ó', 'Ô', 'Ö', 'Õ', 'ù', 'ú', 'û', 'ü', 'Ù', 'Ú', 'Û', 'Ü', 'ç', 'Ç', 'ÿ', 'Ÿ', 'ñ', 'Ñ', 'ß'};
  public static final char[] FORMATTING_CHARACTERS = new char[]{'[', ']', '#'};
  public static final char[] cp1252AsciiExtension = new char[]{'€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ'};

  public static boolean isInteger(CharSequence input) {
    boolean isNegative = false;
    boolean digitsPresent = false;
    int value = 0;
    int length = input.length();

    for (int i = 0; i < length; ++i) {
      char character = input.charAt(i);
      if (i == 0) {
        if (character == '-') {
          isNegative = true;
          continue;
        } else if (character == '+') {
          continue;
        }
      }

      int digitValue;
      if (character >= '0' && character <= '9') {
        digitValue = character - '0';
      } else if (character >= 'A' && character <= 'Z') {
        digitValue = character - '7';
      } else {
        if (character < 'a' || character > 'z') {
          return false; //Invalid character
        }

        digitValue = character - 'W';
      }

      if (digitValue >= 10) {
        return false; //Not a digit
      }

      if (isNegative) {
        digitValue = -digitValue;
      }

      int newValue = digitValue + value * 10;
      if (newValue / 10 != value) {
        return false; //Overflow
      }

      value = newValue;
      digitsPresent = true;
    }

    return digitsPresent;
  }

  public static String escapeAngleBrackets(String input) {
    int inputLength = input.length();
    int escapedCount = 0;

    for (int i = 0; i < inputLength; ++i) {
      char character = input.charAt(i);
      if (character == '<' || character == '>') {
        escapedCount += 3;
      }
    }

    StringBuilder result = new StringBuilder(inputLength + escapedCount);
    for (int i = 0; i < inputLength; ++i) {
      char character = input.charAt(i);
      if (character == '<') {
        result.append("<lt>");
      } else if (character == '>') {
        result.append("<gt>");
      } else {
        result.append(character);
      }
    }

    return result.toString();
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

  public static String toString(int number, boolean includePlusSign) {
    if (includePlusSign && number >= 0) {
      int value = number;
      int numDigits = 1;

      while (value >= 10) {
        value /= 10;
        numDigits++;
      }

      char[] result = new char[numDigits + 1];
      result[0] = '+';

      for (int i = numDigits; i > 0; i--) {
        int digit = number % 10;
        result[i] = (char) (digit + '0');
        number /= 10;
      }

      return new String(result);
    }

    return Integer.toString(number);
  }

  public static String formatNumber(int value) {
    String var1 = Integer.toString(value);

    for (int var2 = var1.length() - 3; var2 > 0; var2 -= 3) {
      var1 = var1.substring(0, var2) + "," + var1.substring(var2);
    }

    if (var1.length() > 9) {
      return " " + client.getColorTags(65408) + var1.substring(0, var1.length() - 8) + "M" + " " + " (" + var1 + ")" + "</col>";
    }
    return var1.length() > 6 ? " " + client.getColorTags(16777215) + var1.substring(0, var1.length() - 4) + "K" + " " + " (" + var1 + ")" + "</col>" : " " + client.getColorTags(16776960) + var1 + "</col>";
  }

  public static String concat(CharSequence[] sequences, int startIndex, int length) {
    if (length == 0) {
      return "";
    }

    if (length == 1) {
      CharSequence sequence = sequences[startIndex];
      return (sequence == null) ? "null" : sequence.toString();
    }

    int endIndex = startIndex + length;
    int cumulativeLength = 0;
    for (int i = startIndex; i < endIndex; ++i) {
      CharSequence sequence = sequences[i];
      if (sequence == null) {
        cumulativeLength += 4;
      } else {
        cumulativeLength += sequence.length();
      }
    }

    StringBuilder result = new StringBuilder(cumulativeLength);
    for (int i = startIndex; i < endIndex; ++i) {
      CharSequence sequence = sequences[i];
      if (sequence == null) {
        result.append("null");
      } else {
        result.append(sequence);
      }
    }

    return result.toString();
  }

  public static int getUtf8Length(CharSequence input) {
    int inputLength = input.length();
    int utf8Length = 0;

    for (int i = 0; i < inputLength; ++i) {
      char character = input.charAt(i);
      if (character <= 127) {
        utf8Length += 1;
      } else if (character <= 2047) {
        utf8Length += 2;
      } else {
        utf8Length += 3;
      }
    }

    return utf8Length;
  }

  public static String decodeUtf8(byte[] data, int startIndex, int length) {
    char[] result = new char[length];
    int charIndex = 0;
    int dataIndex = startIndex;

    while (charIndex < length) {
      int byteValue = data[dataIndex++] & 0xFF;
      int codePoint;

      if (byteValue < 128) {
        codePoint = (byteValue == 0) ? 0xFFFD : byteValue;
      } else if (byteValue < 192) {
        codePoint = 0xFFFD;
      } else if (byteValue < 224) {
        if (dataIndex < startIndex + length && (data[dataIndex] & 0xC0) == 0x80) {
          codePoint = ((byteValue & 0x1F) << 6) | (data[dataIndex++] & 0x3F);
          if (codePoint < 128) {
            codePoint = 0xFFFD;
          }
        } else {
          codePoint = 0xFFFD;
        }
      } else if (byteValue < 240) {
        if (dataIndex + 1 < startIndex + length && (data[dataIndex] & 0xC0) == 0x80 && (data[dataIndex + 1] & 0xC0) == 0x80) {
          codePoint = ((byteValue & 0xF) << 12) | ((data[dataIndex++] & 0x3F) << 6) | (data[dataIndex++] & 0x3F);
          if (codePoint < 0x800) {
            codePoint = 0xFFFD;
          }
        } else {
          codePoint = 0xFFFD;
        }
      } else if (byteValue < 248) {
        if (dataIndex + 2 < startIndex + length && (data[dataIndex] & 0xC0) == 0x80 && (data[dataIndex + 1] & 0xC0) == 0x80 && (data[dataIndex + 2] & 0xC0) == 0x80) {
          codePoint = ((byteValue & 0x7) << 18) | ((data[dataIndex++] & 0x3F) << 12) | ((data[dataIndex++] & 0x3F) << 6) | (data[dataIndex++] & 0x3F);
        } else {
          codePoint = 0xFFFD;
        }
      } else {
        codePoint = 0xFFFD;
      }

      result[charIndex++] = (char) codePoint;
    }

    return new String(result, 0, charIndex);
  }

  public static byte toCp1252Byte(char var0) {
    byte var1;
    if (var0 > 0 && var0 < 128 || var0 >= 160 && var0 <= 255) {
      var1 = (byte) var0;
    } else if (var0 == 8364) {
      var1 = -128;
    } else if (var0 == 8218) {
      var1 = -126;
    } else if (var0 == 402) {
      var1 = -125;
    } else if (var0 == 8222) {
      var1 = -124;
    } else if (var0 == 8230) {
      var1 = -123;
    } else if (var0 == 8224) {
      var1 = -122;
    } else if (var0 == 8225) {
      var1 = -121;
    } else if (var0 == 710) {
      var1 = -120;
    } else if (var0 == 8240) {
      var1 = -119;
    } else if (var0 == 352) {
      var1 = -118;
    } else if (var0 == 8249) {
      var1 = -117;
    } else if (var0 == 338) {
      var1 = -116;
    } else if (var0 == 381) {
      var1 = -114;
    } else if (var0 == 8216) {
      var1 = -111;
    } else if (var0 == 8217) {
      var1 = -110;
    } else if (var0 == 8220) {
      var1 = -109;
    } else if (var0 == 8221) {
      var1 = -108;
    } else if (var0 == 8226) {
      var1 = -107;
    } else if (var0 == 8211) {
      var1 = -106;
    } else if (var0 == 8212) {
      var1 = -105;
    } else if (var0 == 732) {
      var1 = -104;
    } else if (var0 == 8482) {
      var1 = -103;
    } else if (var0 == 353) {
      var1 = -102;
    } else if (var0 == 8250) {
      var1 = -101;
    } else if (var0 == 339) {
      var1 = -100;
    } else if (var0 == 382) {
      var1 = -98;
    } else if (var0 == 376) {
      var1 = -97;
    } else {
      var1 = 63;
    }

    return var1;
  }

  public static int parseInt(CharSequence seq) {
    return parseIntBase(seq, 10);
  }

  public static int parseIntBase(CharSequence seq, int radix) {
    if (radix >= 2 && radix <= 36) {
      boolean negative = false;
      boolean valid = false;
      int result = 0;
      int length = seq.length();

      for (int i = 0; i < length; ++i) {
        char character = seq.charAt(i);
        if (i == 0) {
          if (character == '-') {
            negative = true;
            continue;
          } else if (character == '+') {
            continue;
          }
        }

        int digit;
        if (character >= '0' && character <= '9') {
          digit = character - '0';
        } else if (character >= 'A' && character <= 'Z') {
          digit = character - '7';
        } else if (character >= 'a' && character <= 'z') {
          digit = character - 'W';
        } else {
          throw new NumberFormatException();
        }

        if (digit >= radix) {
          throw new NumberFormatException();
        }

        if (negative) {
          digit = -digit;
        }

        int tempResult = result * radix + digit;
        if (tempResult / radix != result) {
          throw new NumberFormatException();
        }

        result = tempResult;
        valid = true;
      }

      if (!valid) {
        throw new NumberFormatException();
      }

      return result;
    }

    throw new IllegalArgumentException("Invalid radix: " + radix);
  }

  private static boolean isWhitespaceOrSpecialSpace(char character) {
    //160 = nb space
    return Character.isWhitespace(character) || character == '\u00A0' || character == '_' || character == '-';
  }

  public static char convertDiacritic(char var0) {
    switch (var0) {
      case ' ':
      case '-':
      case '_':
      case ' ':
        return '_';
      case '#':
      case '[':
      case ']':
        return var0;
      case 'À':
      case 'Á':
      case 'Â':
      case 'Ã':
      case 'Ä':
      case 'à':
      case 'á':
      case 'â':
      case 'ã':
      case 'ä':
        return 'a';
      case 'Ç':
      case 'ç':
        return 'c';
      case 'È':
      case 'É':
      case 'Ê':
      case 'Ë':
      case 'è':
      case 'é':
      case 'ê':
      case 'ë':
        return 'e';
      case 'Í':
      case 'Î':
      case 'Ï':
      case 'í':
      case 'î':
      case 'ï':
        return 'i';
      case 'Ñ':
      case 'ñ':
        return 'n';
      case 'Ò':
      case 'Ó':
      case 'Ô':
      case 'Õ':
      case 'Ö':
      case 'ò':
      case 'ó':
      case 'ô':
      case 'õ':
      case 'ö':
        return 'o';
      case 'Ù':
      case 'Ú':
      case 'Û':
      case 'Ü':
      case 'ù':
      case 'ú':
      case 'û':
      case 'ü':
        return 'u';
      case 'ß':
        return 'b';
      case 'ÿ':
      case 'Ÿ':
        return 'y';
      default: {
        return Character.toLowerCase(var0);
      }
    }
  }

  public static String format(CharSequence input, ClientParameter parameter) {
    if (input == null) {
      return null;
    }

    int startIdx = 0;
    int endIdx = input.length();
    while (startIdx < endIdx && isWhitespaceOrSpecialSpace(input.charAt(startIdx))) {
      startIdx++;
    }

    while (endIdx > startIdx && isWhitespaceOrSpecialSpace(input.charAt(endIdx - 1))) {
      endIdx--;
    }

    int length = endIdx - startIdx;

    if (length >= 1 && length <= getMaxNameLength(parameter)) {
      StringBuilder formatted = new StringBuilder(length);

      for (int i = startIdx; i < endIdx; i++) {
        char character = input.charAt(i);
        if (isValidCharacter(character)) {
          char convertedCharacter = convertDiacritic(character);
          if (convertedCharacter != 0) {
            formatted.append(convertedCharacter);
          }
        }
      }

      if (formatted.length() == 0) {
        return null;
      }

      return formatted.toString();
    }

    return null;
  }

  private static boolean isValidCharacter(char character) {
    if (Character.isISOControl(character)) {
      return false;
    } else if (ScriptEvent.isAlphaNumeric(character)) {
      return true;
    }

    for (char specialCharacter : SPECIAL_CHARACTERS) {
      if (character == specialCharacter) {
        return true;
      }
    }

    for (char formattingCharacter : FORMATTING_CHARACTERS) {
      if (character == formattingCharacter) {
        return true;
      }
    }

    return false;
  }

  public static int getMaxNameLength(ClientParameter parameter) {
    if (parameter == null) {
      return 12;
    } else if (parameter.id == 0) {
      return 20;
    }

    return 12;
  }
}
