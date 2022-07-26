package jag.graphics;

import jag.commons.collection.DoublyLinkedNode;

public class ComponentSprite extends DoublyLinkedNode {

    public final int[] anIntArray1108;
    public final int anInt380;
    public final int anInt568;
    public final int[] anIntArray749;

    public ComponentSprite(int var1, int var2, int[] var3, int[] var4) {
        this.anInt380 = var1;
        this.anInt568 = var2;
        this.anIntArray749 = var3;
        this.anIntArray1108 = var4;
    }

    public boolean contains(int var1, int var2) {
        if (var2 >= 0 && var2 < this.anIntArray1108.length) {
            int var3 = this.anIntArray1108[var2];
            return var1 >= var3 && var1 <= var3 + this.anIntArray749[var2];
        }

        return false;
    }
}
