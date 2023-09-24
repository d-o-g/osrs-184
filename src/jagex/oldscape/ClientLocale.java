package jagex.oldscape;

import java.util.Locale;

public class ClientLocale implements EnumType {

  public static final ClientLocale GB = new ClientLocale("EN", "en", 0, "GB");
  public static final ClientLocale FR = new ClientLocale("FR", "fr", 2, "FR");
  public static final ClientLocale ES = new ClientLocale("ES", "es", 5, "ES");

  static final ClientLocale DE = new ClientLocale("DE", "de", 1, "DE");
  static final ClientLocale BR = new ClientLocale("PT", "pt", 3, "BR");
  static final ClientLocale NL = new ClientLocale("NL", "nl", 4, "NL");
  static final ClientLocale MX = new ClientLocale("ES_MX", "es-mx", 6, "MX");

  static final ClientLocale[] VALUES;

  static {

    ClientLocale[] values = values();
    VALUES = new ClientLocale[values.length];

    for (ClientLocale locale : values) {
      if (VALUES[locale.index] != null) {
        throw new IllegalStateException();
      }

      VALUES[locale.index] = locale;
    }

  }

  final String languageMajor;
  final String languageMinor;
  final int index;

  ClientLocale(String languageMajor, String languageMinor, int index, String country) {
    this.languageMajor = languageMajor;
    this.languageMinor = languageMinor;
    this.index = index;

    if (country != null) {
      new Locale(languageMinor.substring(0, 2), country);
    } else {
      new Locale(languageMinor.substring(0, 2));
    }
  }

  public static ClientLocale valueOf(int index) {
    return index >= 0 && index < VALUES.length ? VALUES[index] : null;
  }

  static ClientLocale[] values() {
    return new ClientLocale[]{BR, GB, NL, ES, FR, MX, DE};
  }

  String getLanguageMinor() {
    return languageMinor;
  }

  public int getOrdinal() {
    return index;
  }

  public String toString() {
    return getLanguageMinor().toLowerCase(Locale.ENGLISH);
  }
}
