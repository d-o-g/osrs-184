package jag.statics;

import jag.audi.AudioSystem;
import jag.audi.AudioTrack;
import jag.js5.ReferenceTable;

public class Statics57 {
    public static AudioTrack anAudioTrack1158;
    public static ReferenceTable aReferenceTable1159;
    public static ReferenceTable aReferenceTable1160;
    public static String aString1162;
    public static int[] anIntArray1156;

    static {
        AudioSystem.state = 0;
    }

    public static void method533() {
        Statics50.aClass5_Sub6_Sub3_326.method756();
        AudioSystem.state = 1;
        AudioSystem.tracks = null;
    }
}
