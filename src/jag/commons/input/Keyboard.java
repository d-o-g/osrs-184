package jag.commons.input;

import jag.LoginScreenEffect;
import jag.SecureRandomService;
import jag.game.relationship.ChatLine;
import jag.opcode.OldConnectionTaskProcessor;
import jag.statics.Statics53;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Keyboard implements KeyListener, FocusListener {

    public static final boolean[] heldKeys = new boolean[112];
    public static final int[] anIntArray154 = new int[128];
    public static final int[] pressedKeyIndices = new int[128];
    public static final int[] KEYCODES = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, 85, 80, 84, -1, 91, -1, -1, -1, 81, 82, 86, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, 83, 104, 105, 103, 102, 96, 98, 97, 99, -1, -1, -1, -1, -1, -1, -1, 25, 16, 17, 18, 19, 20, 21, 22, 23, 24, -1, -1, -1, -1, -1, -1, -1, 48, 68, 66, 50, 34, 51, 52, 53, 39, 54, 55, 56, 70, 69, 40, 41, 32, 35, 49, 36, 38, 67, 33, 65, 37, 64, -1, -1, -1, -1, -1, 228, 231, 227, 233, 224, 219, 225, 230, 226, 232, 89, 87, -1, 88, 229, 90, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, -1, -1, -1, 101, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 100, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public static final char[] aCharArray156 = new char[128];
    public static final int[] anIntArray148 = new int[128];
    public static Keyboard instance = new Keyboard();
    public static int meta = 0;
    public static int pendingMeta = 0;
    public static int anInt153 = 0;
    public static int anInt157 = 0;
    public static int pressedKeysCount = 0;
    public static volatile int idleTime = 0;
    public static int anInt162 = 0;
    public static char aChar151;

    public Keyboard() {
    }

    public static void method100() {
        for (ChatLine line : Statics53.CHAT_LINE_TABLE) {
            line.method254();
        }
    }

    public static void destroy() {
        if (instance != null) {
            synchronized (instance) {
                instance = null;
            }
        }
    }

    public static int getIdleTime() {
        return idleTime;
    }

    public static void method921() {
        if (OldConnectionTaskProcessor.javaVendor.toLowerCase().contains("microsoft")) {
            KEYCODES[186] = 57;
            KEYCODES[187] = 27;
            KEYCODES[188] = 71;
            KEYCODES[189] = 26;
            KEYCODES[190] = 72;
            KEYCODES[191] = 73;
            KEYCODES[192] = 58;
            KEYCODES[219] = 42;
            KEYCODES[220] = 74;
            KEYCODES[221] = 43;
            KEYCODES[222] = 59;
            KEYCODES[223] = 28;
        } else {
            KEYCODES[44] = 71;
            KEYCODES[45] = 26;
            KEYCODES[46] = 72;
            KEYCODES[47] = 73;
            KEYCODES[59] = 57;
            KEYCODES[61] = 27;
            KEYCODES[91] = 42;
            KEYCODES[92] = 74;
            KEYCODES[93] = 43;
            KEYCODES[192] = 28;
            KEYCODES[222] = 58;
            KEYCODES[520] = 59;
        }

    }

    public static boolean isKeyHeld() {
        synchronized (instance) {
            if (anInt162 == anInt157) {
                return false;
            }
            SecureRandomService.anInt457 = anIntArray148[anInt157];
            aChar151 = aCharArray156[anInt157];
            anInt157 = anInt157 + 1 & 127;
            return true;
        }
    }

    public static void attachListeners(Component target) {
        target.removeKeyListener(instance);
        target.removeFocusListener(instance);
        meta = -1;
    }

    public synchronized void keyReleased(KeyEvent event) {
        if (instance != null) {
            int code = event.getKeyCode();
            if (code >= 0 && code < KEYCODES.length) {
                code = KEYCODES[code] & -129;
            } else {
                code = -1;
            }

            if (meta >= 0 && code >= 0) {
                anIntArray154[meta] = ~code;
                meta = meta + 1 & 127;
                if (meta == pendingMeta) {
                    meta = -1;
                }
            }
        }

        event.consume();
    }

    public void focusGained(FocusEvent e) {
    }

    public synchronized void focusLost(FocusEvent e) {
        if (instance != null) {
            meta = -1;
        }
    }

    public synchronized void keyPressed(KeyEvent e) {
        if (instance != null) {
            int code = e.getKeyCode();
            if (code >= 0 && code < KEYCODES.length) {
                code = KEYCODES[code];
                if ((code & 128) != 0) {
                    code = -1;
                }
            } else {
                code = -1;
            }

            if (meta >= 0 && code >= 0) {
                anIntArray154[meta] = code;
                meta = meta + 1 & 127;
                if (meta == pendingMeta) {
                    meta = -1;
                }
            }

            int var3;
            if (code >= 0) {
                var3 = anInt153 + 1 & 127;
                if (var3 != anInt157) {
                    anIntArray148[anInt153] = code;
                    aCharArray156[anInt153] = 0;
                    anInt153 = var3;
                }
            }

            var3 = e.getModifiers();
            if ((var3 & 10) != 0 || code == 85 || code == 10) {
                e.consume();
            }
        }

    }

    public void keyTyped(KeyEvent e) {
        if (instance != null) {
            char c = e.getKeyChar();
            if (c != 0 && c != '\uffff' && LoginScreenEffect.method278(c)) {
                int var3 = anInt153 + 1 & 127;
                if (var3 != anInt157) {
                    anIntArray148[anInt153] = -1;
                    aCharArray156[anInt153] = c;
                    anInt153 = var3;
                }
            }
        }
        e.consume();
    }
}
