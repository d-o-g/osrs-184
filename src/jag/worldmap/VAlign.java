package jag.worldmap;

import jag.EnumType;

public enum VAlign implements EnumType {

    anEnum_Sub8_1480(0, 0),
    anEnum_Sub8_1481(2, 1),
    anEnum_Sub8_1479(1, 2);

    public static long aLong1478;

    public final int type;
    final int ordinal;

    VAlign(int type, int ordinal) {
        this.type = type;
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return this.ordinal;
    }
}
