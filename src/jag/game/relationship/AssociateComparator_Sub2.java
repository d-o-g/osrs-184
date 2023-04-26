package jag.game.relationship;

import jag.game.Server;
import jag.graphics.IndexedSprite;

public class AssociateComparator_Sub2<T extends Associate<T>> extends AssociateComparator<T> {

    public static IndexedSprite[] titleMuteSprites;
    public final boolean aBoolean764;

    public AssociateComparator_Sub2(boolean var1) {
        this.aBoolean764 = var1;
    }

    public static boolean method607(char var0) {
        return var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
    }

    public static int method606(Server var0, Server var1, int var2, boolean var3) {
        if (var2 == 1) {
            int var4 = var0.population;
            int var5 = var1.population;
            if (!var3) {
                if (var4 == -1) {
                    var4 = 2001;
                }

                if (var5 == -1) {
                    var5 = 2001;
                }
            }

            return var4 - var5;
        }
        if (var2 == 2) {
            return var0.location - var1.location;
        }
        if (var2 == 3) {
            if (var0.activity.equals("-")) {
                if (var1.activity.equals("-")) {
                    return 0;
                }
                return var3 ? -1 : 1;
            }
            if (var1.activity.equals("-")) {
                return var3 ? 1 : -1;
            }
            return var0.activity.compareTo(var1.activity);
        }
        if (var2 == 4) {
            return var0.method1350() ? (var1.method1350() ? 0 : 1) : (var1.method1350() ? -1 : 0);
        }
        if (var2 == 5) {
            return var0.method1346() ? (var1.method1346() ? 0 : 1) : (var1.method1346() ? -1 : 0);
        }
        if (var2 == 6) {
            return var0.isPVP() ? (var1.isPVP() ? 0 : 1) : (var1.isPVP() ? -1 : 0);
        }
        if (var2 == 7) {
            return var0.isMembers() ? (var1.isMembers() ? 0 : 1) : (var1.isMembers() ? -1 : 0);
        }
        return var0.id - var1.id;
    }

    int method604(T var1, T var2) {
        if (var1.world != 0 && var2.world != 0) {
            return this.aBoolean764 ? var1.index - var2.index : var2.index - var1.index;
        }
        return this.method1135(var1, var2);
    }

    public int compare(T var1, T var2) {
        return this.method604(var1, var2);
    }
}
