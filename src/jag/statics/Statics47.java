package jag.statics;

import jag.ClientLocale;
import jag.game.InterfaceComponent;
import jag.game.relationship.ChatLine;
import jag.graphics.Sprite;

public class Statics47 {
    public static Sprite[] mapmarkers;

    public static void method320(InterfaceComponent var0, int var1) {
        if (var0.aByteArrayArray1365 == null) {
            throw new RuntimeException();
        }
        if (var0.keyCycles == null) {
            var0.keyCycles = new int[var0.aByteArrayArray1365.length];
        }

        var0.keyCycles[var1] = Integer.MAX_VALUE;
    }

    public static int method321(int key) {
        ChatLine line = Statics53.CHAT_LINE_TABLE.lookup(key);
        if (line == null) {
            return -1;
        }
        return line.nextDoubly == Statics53.CHAT_LINE_QUEUE.sentinel ? -1 : ((ChatLine) line.nextDoubly).index;
    }

    static char method322(char var0, ClientLocale var1) {
        if (var0 >= 192 && var0 <= 255) {
            if (var0 >= 192 && var0 <= 198) {
                return 'A';
            }

            if (var0 == 199) {
                return 'C';
            }

            if (var0 >= 200 && var0 <= 203) {
                return 'E';
            }

            if (var0 >= 204 && var0 <= 207) {
                return 'I';
            }

            if (var0 == 209 && var1 != ClientLocale.ES) {
                return 'N';
            }

            if (var0 >= 210 && var0 <= 214) {
                return 'O';
            }

            if (var0 >= 217 && var0 <= 220) {
                return 'U';
            }

            if (var0 == 221) {
                return 'Y';
            }

            if (var0 == 223) {
                return 's';
            }

            if (var0 >= 224 && var0 <= 230) {
                return 'a';
            }

            if (var0 == 231) {
                return 'c';
            }

            if (var0 >= 232 && var0 <= 235) {
                return 'e';
            }

            if (var0 >= 236 && var0 <= 239) {
                return 'i';
            }

            if (var0 == 241 && var1 != ClientLocale.ES) {
                return 'n';
            }

            if (var0 >= 242 && var0 <= 246) {
                return 'o';
            }

            if (var0 >= 249 && var0 <= 252) {
                return 'u';
            }

            if (var0 == 253 || var0 == 255) {
                return 'y';
            }
        }

        if (var0 == 338) {
            return 'O';
        }
        if (var0 == 339) {
            return 'o';
        }
        if (var0 == 376) {
            return 'Y';
        }
        return var0;
    }
}
