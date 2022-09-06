package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.InterfaceComponent;
import jag.game.scene.entity.UnlitModel;
import jag.game.stockmarket.StockMarketOfferWorldComparator;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;

public class IdentikitDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<IdentikitDefinition> cache = new ReferenceCache<>(64);

    public static ReferenceTable table;

    public static InterfaceComponent anInterfaceComponent1518;

    public int index;

    public boolean hidden;

    int[] anIntArray747;
    int[] models;

    short[] aShortArray1516;
    short[] aShortArray1462;
    short[] aShortArray1461;
    short[] aShortArray1460;

    public IdentikitDefinition() {
        index = -1;
        models = new int[]{-1, -1, -1, -1, -1};
        hidden = false;
    }

    public static IdentikitDefinition get(int id) {
        IdentikitDefinition kit = cache.get(id);
        if (kit != null) {
            return kit;
        }
        byte[] data = table.unpack(3, id);
        kit = new IdentikitDefinition();
        if (data != null) {
            kit.decode(new Buffer(data));
        }

        cache.put(kit, id);
        return kit;
    }

    void decode(Buffer var1, int var2) {
        if (var2 == 1) {
            index = var1.g1();
        } else {
            if (var2 == 2) {
                int var3 = var1.g1();
                anIntArray747 = new int[var3];

                for (int var4 = 0; var4 < var3; ++var4) {
                    anIntArray747[var4] = var1.g2();
                }
            } else if (var2 == 3) {
                hidden = true;
            } else if (var2 == 40) {
                int var3 = var1.g1();
                aShortArray1516 = new short[var3];
                aShortArray1461 = new short[var3];

                for (int var4 = 0; var4 < var3; ++var4) {
                    aShortArray1516[var4] = (short) var1.g2();
                    aShortArray1461[var4] = (short) var1.g2();
                }
            } else if (var2 == 41) {
                int var3 = var1.g1();
                aShortArray1462 = new short[var3];
                aShortArray1460 = new short[var3];

                for (int var4 = 0; var4 < var3; ++var4) {
                    aShortArray1462[var4] = (short) var1.g2();
                    aShortArray1460[var4] = (short) var1.g2();
                }
            } else if (var2 >= 60 && var2 < 70) {
                models[var2 - 60] = var1.g2();
            }
        }

    }

    public boolean method882() {
        boolean var1 = true;

        for (int var2 = 0; var2 < 5; ++var2) {
            if (models[var2] != -1 && !StockMarketOfferWorldComparator.aReferenceTable350.load(models[var2], 0)) {
                var1 = false;
            }
        }

        return var1;
    }

    public UnlitModel method1113() {
        UnlitModel[] var1 = new UnlitModel[5];
        int var2 = 0;

        for (int var3 = 0; var3 < 5; ++var3) {
            if (models[var3] != -1) {
                var1[var2++] = UnlitModel.method982(StockMarketOfferWorldComparator.aReferenceTable350, models[var3], 0);
            }
        }

        UnlitModel var4 = new UnlitModel(var1, var2);
        int var5;
        if (aShortArray1516 != null) {
            for (var5 = 0; var5 < aShortArray1516.length; ++var5) {
                var4.texturize(aShortArray1516[var5], aShortArray1461[var5]);
            }
        }

        if (aShortArray1462 != null) {
            for (var5 = 0; var5 < aShortArray1462.length; ++var5) {
                var4.colorize(aShortArray1462[var5], aShortArray1460[var5]);
            }
        }

        return var4;
    }

    public boolean method1114() {
        if (anIntArray747 == null) {
            return true;
        }

        boolean present = true;
        for (int i : anIntArray747) {
            if (!StockMarketOfferWorldComparator.aReferenceTable350.load(i, 0)) {
                present = false;
            }
        }

        return present;
    }

    public void decode(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }

            decode(var1, var2);
        }
    }

    public UnlitModel method978() {
        if (anIntArray747 == null) {
            return null;
        }
        UnlitModel[] var1 = new UnlitModel[anIntArray747.length];

        for (int var2 = 0; var2 < anIntArray747.length; ++var2) {
            var1[var2] = UnlitModel.method982(StockMarketOfferWorldComparator.aReferenceTable350, anIntArray747[var2], 0);
        }

        UnlitModel var3;
        if (var1.length == 1) {
            var3 = var1[0];
        } else {
            var3 = new UnlitModel(var1, var1.length);
        }

        int var4;
        if (aShortArray1516 != null) {
            for (var4 = 0; var4 < aShortArray1516.length; ++var4) {
                var3.texturize(aShortArray1516[var4], aShortArray1461[var4]);
            }
        }

        if (aShortArray1462 != null) {
            for (var4 = 0; var4 < aShortArray1462.length; ++var4) {
                var3.colorize(aShortArray1462[var4], aShortArray1460[var4]);
            }
        }

        return var3;
    }
}
