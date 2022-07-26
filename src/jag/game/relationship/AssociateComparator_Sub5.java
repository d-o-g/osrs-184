package jag.game.relationship;

public class AssociateComparator_Sub5 extends AssociateComparator {

    public final boolean aBoolean764;

    public AssociateComparator_Sub5(boolean var1) {
        this.aBoolean764 = var1;
    }

    int method604(Associate var1, Associate var2) {
        if (var1.world != 0) {
            if (var2.world == 0) {
                return this.aBoolean764 ? -1 : 1;
            }
        } else if (var2.world != 0) {
            return this.aBoolean764 ? 1 : -1;
        }

        return this.method1135(var1, var2);
    }

    public int compare(Associate var1, Associate var2) {
        return this.method604(var1, var2);
    }
}
