package jag;

public enum ComponentFillType implements EnumType {

    SOLID(0, 0),
    anEnum_Sub11_1863(1, 1),
    anEnum_Sub11_1861(2, 2);

    public final int type;
    final int index;

    ComponentFillType(int type, int index) {
        this.type = type;
        this.index = index;
    }

    public static ComponentFillType[] getValues() {
        return new ComponentFillType[]{anEnum_Sub11_1863, SOLID, anEnum_Sub11_1861};
    }

    public int getOrdinal() {
        return index;
    }
}
