package jag.game.relationship;

import jag.game.client;

public class AssociateComparatorByWorldAndName extends AssociateComparator {

    public final boolean aBoolean764;

    public AssociateComparatorByWorldAndName(boolean var1) {
        this.aBoolean764 = var1;
    }

    int method604(Associate var1, Associate var2) {
        if (client.currentWorld == var1.world && var2.world == client.currentWorld) {
            return this.aBoolean764 ? var1.getDisplayName().compare0(var2.getDisplayName()) : var2.getDisplayName().compare0(var1.getDisplayName());
        }
        return this.method1135(var1, var2);
    }

    public int compare(Associate var1, Associate var2) {
        return this.method604(var1, var2);
    }
}
