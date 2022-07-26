package jag.game.relationship;

import jag.audi.DefaultAudioSystemProvider;
import jag.game.client;
import jag.js5.LoadedArchive;
import jag.graphics.NamedFont;
import jag.statics.*;

public class BefriendedPlayer extends Associate {

    public boolean aBoolean745;
    public boolean aBoolean746;

    BefriendedPlayer() {
    }

    public static void method555() {
        Statics45.aByteArrayArrayArray404 = null;
        Statics45.aByteArrayArrayArray401 = null;
        DefaultAudioSystemProvider.aByteArrayArrayArray141 = null;
        Statics35.aByteArrayArrayArray1615 = null;
        Statics45.anIntArrayArrayArray393 = null;
        Statics45.aByteArrayArrayArray400 = null;
        DefaultAudioSystemProvider.anIntArrayArray146 = null;
        Statics45.anIntArray396 = null;
        LoadedArchive.anIntArray426 = null;
        Statics45.anIntArray390 = null;
        NamedFont.anIntArray1626 = null;
        Statics45.anIntArray389 = null;
    }

    int method552(BefriendedPlayer var1) {
        if (super.world == client.currentWorld && client.currentWorld != var1.world) {
            return -1;
        }
        if (client.currentWorld == var1.world && super.world != client.currentWorld) {
            return 1;
        }
        if (super.world != 0 && var1.world == 0) {
            return -1;
        }
        if (var1.world != 0 && super.world == 0) {
            return 1;
        }
        if (this.aBoolean745 && !var1.aBoolean745) {
            return -1;
        }
        if (!this.aBoolean745 && var1.aBoolean745) {
            return 1;
        }
        if (this.aBoolean746 && !var1.aBoolean746) {
            return -1;
        }
        if (!this.aBoolean746 && var1.aBoolean746) {
            return 1;
        }
        return super.world != 0 ? super.index - var1.index : var1.index - super.index;
    }

    public int compare0(Chatter var1) {
        return this.method552((BefriendedPlayer) var1);
    }

    public int compareTo(Object var1) {
        return this.method552((BefriendedPlayer) var1);
    }
}
