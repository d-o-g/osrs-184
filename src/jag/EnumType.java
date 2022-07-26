package jag;

public interface EnumType {

    static EnumType getByOrdinal(EnumType[] var0, int ordinal) {
        for (EnumType type : var0) {
            if (ordinal == type.getOrdinal()) {
                return type;
            }
        }
        return null;
    }

    int getOrdinal();
}
