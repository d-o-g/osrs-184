package jag.opcode.login;

import jag.EnumType;

public enum LoginHeaderType implements EnumType {

    anEnum_Sub10_1767(4, 0),
    anEnum_Sub10_1769(1, 1),
    anEnum_Sub10_1765(8, 2),
    anEnum_Sub10_1764(2, 3),
    anEnum_Sub10_1762(6, 4),
    anEnum_Sub10_1760(5, 5),
    anEnum_Sub10_1761(0, 6),
    anEnum_Sub10_1768(7, 7),
    anEnum_Sub10_1766(3, 8);

    final int anInt1763;
    final int anInt1180;

    LoginHeaderType(int var3, int var4) {
        this.anInt1180 = var3;
        this.anInt1763 = var4;
    }

    public int getOrdinal() {
        return this.anInt1763;
    }
}
