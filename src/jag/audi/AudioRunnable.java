package jag.audi;

import jag.game.*;
import jag.game.option.AttackOptionPriority;
import jag.graphics.ComponentSprite;
import jag.graphics.JagGraphics;
import jag.js5.BufferedFile;
import jag.opcode.Buffer;

public class AudioRunnable implements Runnable {

    public final AudioSystem[] systems;

    public AudioRunnable() {
        this.systems = new AudioSystem[2];
    }

    public static void renderCompass(InterfaceComponent var0, int var1, int var2) {
        ComponentSprite var4 = var0.getSprite(false);
        if (var4 != null) {
            if (client.mapState < 3) {
                AttackOptionPriority.compass.rotate(var1, var2, var4.anInt380, var4.anInt568, 25, 25, Camera.yOffset, 256, var4.anIntArray1108, var4.anIntArray749);
            } else {
                JagGraphics.method1362(var1, var2, 0, var4.anIntArray1108, var4.anIntArray749);
            }

        }
    }

    public static void method986(Buffer var0, int var1) {
        if (BufferedFile.random != null) {
            try {
                BufferedFile.random.seek(0L);
                BufferedFile.random.write(var0.payload, var1, 24);
            } catch (Exception ignored) {
            }
        }
    }

    public void run() {
        try {
            for (int i = 0; i < 2; ++i) {
                AudioSystem system = systems[i];
                if (system != null) {
                    system.poll();
                }
            }
        } catch (Exception var4) {
            client.sendError(null, var4);
        }
    }
}
