package jagex.oldscape.client.worldmap;

import jagex.oldscape.Bounds;
import jagex.jagex3.graphics.Sprite;
import jagex.oldscape.script.ClientScriptFrame;
import jagex.statics.Gaussian;

import java.util.HashMap;

public class WorldMapHeatMap {

  public static int[] anIntArray1520;
  final HashMap<Integer, Sprite> sprites;
  final int[] anIntArray1449;
  final int[] anIntArray1448;
  final Bounds size;
  int anInt1447;

  public WorldMapHeatMap() {
    sprites = new HashMap<>();
    size = new Bounds(0, 0);
    anIntArray1449 = new int[2048];
    anIntArray1448 = new int[2048];
    anInt1447 = 0;
    anIntArray1520 = new int[2000];
    int var1 = 0;
    int var2 = 240;

    int var4;
    for (byte var3 = 12; var1 < 16; var2 -= var3) {
      var4 = ClientScriptFrame.method295((float) var2 / 360.0F, 0.9998999834060669D, 0.075F + 0.425F * (float) var1 / 16.0F);
      anIntArray1520[var1] = var4;
      ++var1;
    }

    var2 = 48;

    for (int var6 = var2 / 6; var1 < anIntArray1520.length; var2 -= var6) {
      var4 = var1 * 2;

      for (int var5 = ClientScriptFrame.method295((float) var2 / 360.0F, 0.9998999834060669D, 0.5D); var1 < var4 && var1 < anIntArray1520.length; ++var1) {
        anIntArray1520[var1] = var5;
      }
    }

  }

  Sprite computeIfAbsent(int id) {
    if (!sprites.containsKey(id)) {
      compute(id);
    }

    return sprites.get(id);
  }

  void compute(int id) {
    int size = id * 2 + 1;
    double[] gaussian = Gaussian.generate(0.0D, (float) id / 3.0F, id);
    double normalizationFactor = gaussian[id] * gaussian[id];
    int[] pixels = new int[size * size];
    boolean filled = false;

    for (int x = 0; x < size; ++x) {
      for (int y = 0; y < size; ++y) {
        int pixel = pixels[y + x * size] = (int) (256.0D * (gaussian[x] * gaussian[y] / normalizationFactor));
        if (!filled && pixel > 0) {
          filled = true;
        }
      }
    }

    Sprite sprite = new Sprite(pixels, size, size);
    sprites.put(id, sprite);
  }

  void blend(Sprite s1, Sprite s2, Bounds bounds) {
    if (bounds.width != 0 && bounds.height != 0) {
      int xOffset = 0;
      int yOffset = 0;
      if (bounds.x == 0) {
        xOffset = s1.width - bounds.width;
      }

      if (bounds.y == 0) {
        yOffset = s1.height - bounds.height;
      }

      int destOffset = xOffset + yOffset * s1.width;
      int srcOffset = bounds.x + s2.width * bounds.y;

      for (int x = 0; x < bounds.height; ++x) {
        for (int y = 0; y < bounds.width; ++y) {
          int[] pixels = s2.pixels;
          int next = srcOffset++;
          pixels[next] += s1.pixels[destOffset++];
        }

        destOffset += s1.width - bounds.width;
        srcOffset += s2.width - bounds.width;
      }
    }
  }

  public final void transform(int x, int y, Sprite base, float intensity) {
    int radius = (int) (intensity * 18.0F);
    Sprite heatmap = computeIfAbsent(radius);
    int size = radius * 2 + 1;
    Bounds srcBounds = new Bounds(0, 0, base.width, base.height);
    Bounds dstBounds = new Bounds(0, 0);
    this.size.setSize(size, size);

    for (int i = 0; i < anInt1447; ++i) {
      int posX = anIntArray1449[i];
      int posY = anIntArray1448[i];
      int srcX = (int) ((float) (posX - x) * intensity) - radius;
      int srcY = (int) ((float) base.height - intensity * (float) (posY - y)) - radius;
      this.size.setLocation(srcX, srcY);
      this.size.add(srcBounds, dstBounds);
      blend(heatmap, base, dstBounds);
    }

    for (int i = 0; i < base.pixels.length; ++i) {
      if (base.pixels[i] == 0) {
        base.pixels[i] = 0xff000000;
      } else {
        int var11 = (base.pixels[i] + 64 - 1) / 256;
        if (var11 <= 0) {
          base.pixels[i] = -16777216;
        } else {
          if (var11 > anIntArray1520.length) {
            var11 = anIntArray1520.length;
          }

          int var12 = anIntArray1520[var11 - 1];
          base.pixels[i] = -16777216 | var12;
        }
      }
    }
  }

  public final void method993() {
    anInt1447 = 0;
  }

  public final void method994(int var1, int var2) {
    if (anInt1447 < anIntArray1449.length) {
      anIntArray1449[anInt1447] = var1;
      anIntArray1448[anInt1447] = var2;
      ++anInt1447;
    }
  }
}
