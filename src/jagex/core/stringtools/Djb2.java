package jagex.core.stringtools;

public class Djb2 {

  public static int hash(CharSequence entry) {
    int len = entry.length();
    int hash = 0;

    for (int i = 0; i < len; ++i) {
      hash = (hash << 5) - hash + entry.charAt(i);
    }

    return hash;
  }
}
