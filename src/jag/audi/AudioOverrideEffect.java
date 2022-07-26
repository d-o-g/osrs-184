package jag.audi;

import jag.game.client;
import jag.js5.Archive;
import jag.statics.Statics57;

public class AudioOverrideEffect {
    int anInt1133;
    int anInt1129;
    int anInt1126;
    byte[] aByteArray1132;
    int anInt1131;
    int anInt1125;
    byte[] aByteArray1134;
    int anInt1128;
    int anInt1127;

    AudioOverrideEffect() {
    }

    public static boolean method794(int var0) {
        for (int var1 = 0; var1 < client.anInt1092; ++var1) {
            if (client.anIntArray1096[var1] == var0) {
                return true;
            }
        }

        return false;
    }

    public static void method795(int var0) {
        if (var0 == -1 && !client.aBoolean904) {
            Statics57.method533();
        } else if (var0 != -1 && var0 != client.anInt898 && client.anInt900 != 0 && !client.aBoolean904) {
            Archive var1 = Archive.audioTracks;
            int var2 = client.anInt900;
            AudioSystem.state = 1;
            AudioSystem.tracks = var1;
            AudioSystem.trackGroup = var0;
            AudioSystem.trackFile = 0;
            AudioSystem.volume = var2;
            AudioSystem.aBoolean620 = false;
            AudioSystem.pcmSampleLength = 2;
        }

        client.anInt898 = var0;
    }
}
