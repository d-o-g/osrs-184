package jagex.oldscape.client.worldmap;

import jagex.oldscape.EnumType;

public enum HAlign implements EnumType {

  anEnum_Sub9_1484(1, 0),
  anEnum_Sub9_1485(2, 1),
  anEnum_Sub9_1483(0, 2);

  public static short[] aShortArray1482;

  public final int type;
  final int ordinal;

  HAlign(int type, int ordinal) {
    this.type = type;
    this.ordinal = ordinal;
  }

  public static HAlign[] getValues() {
    return new HAlign[]{anEnum_Sub9_1483, anEnum_Sub9_1484, anEnum_Sub9_1485};
  }

  public int getOrdinal() {
    return this.ordinal;
  }
}
