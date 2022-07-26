package jag.game.relationship;

import jag.game.PlayerModel;

import java.util.Comparator;

public class Class223 implements Comparator {

    final boolean aBoolean1868;

    public Class223(boolean var1) {
        this.aBoolean1868 = var1;
    }

    public static void clear() {
        PlayerModel.models.clear();
    }

    int method1342(Chatter var1, Chatter var2) {
        return this.aBoolean1868 ? var1.compare0(var2) : var2.compare0(var1);
    }

    public int compare(Object var1, Object var2) {
        return this.method1342((Chatter) var1, (Chatter) var2);
    }

    public boolean equals(Object var1) {
        return super.equals(var1);
    }
}
