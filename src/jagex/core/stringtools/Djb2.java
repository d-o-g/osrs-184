package jagex.core.stringtools;

public class Djb2 {

  public static int ascii(CharSequence entry) {
    int len = entry.length();
    int hash = 0;

    for (int i = 0; i < len; ++i) {
      hash = (hash << 5) - hash + entry.charAt(i);
    }

    return hash;
  }

  public static int cp1252(CharSequence entry) {
    int len = entry.length();
    int hash = 0;

    for (int i = 0; i < len; ++i) {
      hash = (hash << 5) - hash + Strings.toCp1252Byte(entry.charAt(i));
    }

    return hash;
  }
}
