package jagex.jagex3.graphics;

import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class SpriteProvider {

  public static int count;
  public static int width;
  public static int height;

  public static int[] offsetsX;
  public static int[] offsetsY;

  public static int[] sizesX;
  public static int[] sizesY;

  public static int[] palette;

  public static byte[][] pixels;

  public static void loadSprites(byte[] payload) {
    Buffer buf = new Buffer(payload);
    buf.pos = payload.length - 2;

    count = buf.g2();
    offsetsX = new int[count];
    offsetsY = new int[count];
    sizesX = new int[count];
    sizesY = new int[count];
    pixels = new byte[count][];

    buf.pos = payload.length - 7 - count * 8;

    width = buf.g2();
    height = buf.g2();

    int palSize = (buf.g1() & 255) + 1;

    int i;
    for (i = 0; i < count; ++i) {
      offsetsX[i] = buf.g2();
    }
    for (i = 0; i < count; ++i) {
      offsetsY[i] = buf.g2();
    }
    for (i = 0; i < count; ++i) {
      sizesX[i] = buf.g2();
    }
    for (i = 0; i < count; ++i) {
      sizesY[i] = buf.g2();
    }

    buf.pos = payload.length - 7 - count * 8 - (palSize - 1) * 3;

    palette = new int[palSize];

    for (i = 1; i < palSize; ++i) {
      palette[i] = buf.g3();
      if (palette[i] == 0) {
        palette[i] = 1;
      }
    }

    buf.pos = 0;

    for (i = 0; i < count; ++i) {
      int sizeX = sizesX[i];
      int sizeY = sizesY[i];
      int area = sizeX * sizeY;
      byte[] spriteBuf = new byte[area];
      pixels[i] = spriteBuf;
      int fill = buf.g1();
      int j;
      if (fill == 0) {
        for (j = 0; j < area; ++j) {
          spriteBuf[j] = buf.g1b();
        }
      } else if (fill == 1) {
        for (j = 0; j < sizeX; ++j) {
          for (int k = 0; k < sizeY; ++k) {
            spriteBuf[j + sizeX * k] = buf.g1b();
          }
        }
      }
    }
  }

  public static boolean loadSprites(ReferenceTable table, int group, int file) {
    byte[] data = table.unpack(group, file);
    if (data == null) {
      return false;
    }
    loadSprites(data);
    return true;
  }
}
