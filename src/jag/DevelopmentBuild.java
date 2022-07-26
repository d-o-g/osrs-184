package jag;

public class DevelopmentBuild {

    public static final DevelopmentBuild LIVE = new DevelopmentBuild("LIVE", 0);
    public static final DevelopmentBuild BUILDLIVE = new DevelopmentBuild("BUILDLIVE", 3);
    public static final DevelopmentBuild RC = new DevelopmentBuild("RC", 1);
    public static final DevelopmentBuild WIP = new DevelopmentBuild("WIP", 2);

    public final String name;
    public final int index;

    DevelopmentBuild(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static DevelopmentBuild valueOf(int index) {
        for (DevelopmentBuild value : new DevelopmentBuild[]{BUILDLIVE, LIVE, RC, WIP}) {
            if (index == value.index) {
                return value;
            }
        }
        return null;
    }
}
