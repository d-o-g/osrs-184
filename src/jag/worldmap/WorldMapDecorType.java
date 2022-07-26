package jag.worldmap;

import jag.EnumType;

public enum WorldMapDecorType implements EnumType {
    anEnum_Sub7_1287(0),
    anEnum_Sub7_1275(1),
    anEnum_Sub7_1283(2),
    anEnum_Sub7_1282(3),
    anEnum_Sub7_1279(9),
    anEnum_Sub7_1277(4),
    anEnum_Sub7_1278(5),
    anEnum_Sub7_1274(6),
    anEnum_Sub7_1286(7),
    anEnum_Sub7_1272(8),
    anEnum_Sub7_1266(12),
    anEnum_Sub7_1265(13),
    anEnum_Sub7_1276(14),
    anEnum_Sub7_1264(15),
    anEnum_Sub7_1268(16),
    anEnum_Sub7_1284(17),
    anEnum_Sub7_1281(18),
    anEnum_Sub7_1270(19),
    anEnum_Sub7_1285(20),
    anEnum_Sub7_1271(21),
    anEnum_Sub7_1280(10),
    anEnum_Sub7_1269(11),
    anEnum_Sub7_1273(22);

    public final int id;

    WorldMapDecorType(int var3) {
        this.id = var3;
    }

    public int getOrdinal() {
        return this.id;
    }
}
