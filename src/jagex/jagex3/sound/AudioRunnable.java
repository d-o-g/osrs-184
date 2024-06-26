package jagex.jagex3.sound;

import jagex.oldscape.client.*;
import jagex.oldscape.client.option.AttackOptionPriority;
import jagex.jagex3.graphics.ComponentSprite;
import jagex.jagex3.graphics.JagGraphics;
import jagex.jagex3.js5.BufferedFile;
import jagex.messaging.Buffer;

public class AudioRunnable implements Runnable {

  public final AudioSystem[] systems;

  public AudioRunnable() {
    this.systems = new AudioSystem[2];
  }

  public static void renderCompass(Component var0, int midX, int midY) {
    ComponentSprite sprite = var0.getSprite(false);
    if (sprite != null) {
      if (client.mapState < 3) {
        AttackOptionPriority.compass.rotate(midX, midY, sprite.width, sprite.height, 25, 25, Camera.yOffset, 256, sprite.pixels, sprite.scanlineOffsets);
      } else {
        JagGraphics.fillRectangles(midX, midY, 0, sprite.pixels, sprite.scanlineOffsets);
      }

    }
  }

  public static void writeRandom(Buffer buffer, int var1) {
    if (BufferedFile.random != null) {
      try {
        BufferedFile.random.seek(0L);
        BufferedFile.random.write(buffer.payload, var1, 24);
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
