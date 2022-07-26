package jag.game.type;

import jag.commons.collection.DoublyLinkedNode;
import jag.commons.collection.ReferenceCache;
import jag.game.relationship.ChatHistory;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;
import jag.worldmap.WorldMapScriptEvent;

public class EnumDefinition extends DoublyLinkedNode {

    public static final ReferenceCache<EnumDefinition> cache = new ReferenceCache<>(64);
    public static ReferenceTable table;
    public static WorldMapScriptEvent aWorldMapScriptEvent_1443;

    public int anInt375;
    public String aString1442;
    public char aChar1445;
    public char aChar1444;
    public int anInt112;
    public int[] anIntArray692;
    public String[] aStringArray1446;
    public int[] anIntArray691;

    public EnumDefinition() {
        aString1442 = "null";
        anInt375 = 0;
    }

    public static void sendFullIgnoreListMessage() {
        ChatHistory.messageReceived(30, "", "Your ignore list is full. Max of 100 for free users, and 400 for members");
    }

    void decode(Buffer var1, int var2) {
        if (var2 == 1) {
            aChar1445 = (char) var1.g1();
        } else if (var2 == 2) {
            aChar1444 = (char) var1.g1();
        } else if (var2 == 3) {
            aString1442 = var1.gstr();
        } else if (var2 == 4) {
            anInt112 = var1.g4();
        } else {
            int var3;
            if (var2 == 5) {
                anInt375 = var1.g2();
                anIntArray692 = new int[anInt375];
                aStringArray1446 = new String[anInt375];

                for (var3 = 0; var3 < anInt375; ++var3) {
                    anIntArray692[var3] = var1.g4();
                    aStringArray1446[var3] = var1.gstr();
                }
            } else if (var2 == 6) {
                anInt375 = var1.g2();
                anIntArray692 = new int[anInt375];
                anIntArray691 = new int[anInt375];

                for (var3 = 0; var3 < anInt375; ++var3) {
                    anIntArray692[var3] = var1.g4();
                    anIntArray691[var3] = var1.g4();
                }
            }
        }

    }

    public int method987() {
        return anInt375;
    }

    public void decode(Buffer var1) {
        while (true) {
            int var2 = var1.g1();
            if (var2 == 0) {
                return;
            }
            decode(var1, var2);
        }
    }
}
