package jag.game.relationship;

import jag.audi.DefaultAudioSystemProvider;
import jag.game.client;
import jag.js5.LoadedArchive;
import jag.graphics.NamedFont;
import jag.statics.*;

public class BefriendedPlayer extends Associate<BefriendedPlayer> {

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

    int compare(BefriendedPlayer friend) {
        if (super.world == client.currentWorld && client.currentWorld != friend.world) {
            return -1;
        }
        if (client.currentWorld == friend.world && super.world != client.currentWorld) {
            return 1;
        }
        if (super.world != 0 && friend.world == 0) {
            return -1;
        }
        if (friend.world != 0 && super.world == 0) {
            return 1;
        }
        if (this.aBoolean745 && !friend.aBoolean745) {
            return -1;
        }
        if (!this.aBoolean745 && friend.aBoolean745) {
            return 1;
        }
        if (this.aBoolean746 && !friend.aBoolean746) {
            return -1;
        }
        if (!this.aBoolean746 && friend.aBoolean746) {
            return 1;
        }
        return super.world != 0 ? super.index - friend.index : friend.index - super.index;
    }

    public int compare0(BefriendedPlayer var1) {
        return this.compare(var1);
    }

    public int compareTo(BefriendedPlayer var1) {
        return this.compare(var1);
    }
}
