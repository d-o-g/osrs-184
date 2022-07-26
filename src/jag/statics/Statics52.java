package jag.statics;

import jag.commons.Jagexception;
import jag.commons.input.Keyboard;
import jag.game.client;
import jag.graphics.IndexedSprite;
import jag.js5.ReferenceTable;

public class Statics52 {
    public static ReferenceTable aReferenceTable500;
    public static IndexedSprite[] mapscene;
    public static int anInt499;

    public static Jagexception method348(Throwable var0, String var1) {
        Jagexception var2;
        if (var0 instanceof Jagexception) {
            var2 = (Jagexception) var0;
            var2.message = var2.message + ' ' + var1;
        } else {
            var2 = new Jagexception(var0, var1);
        }

        return var2;
    }

    public static boolean method345() {
        return client.aBoolean1037 || Keyboard.heldKeys[81];
    }

    static char method347(char var0) {
        if (var0 == 198) {
            return 'E';
        }
        if (var0 == 230) {
            return 'e';
        }
        if (var0 == 223) {
            return 's';
        }
        if (var0 == 338) {
            return 'E';
        }
        return (var0 == 339 ? 'e' : '\u0000');
    }
}
