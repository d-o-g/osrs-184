package jagex.oldscape.client.type;

import jagex.datastructure.DoublyLinkedNode;
import jagex.datastructure.instrusive.cache.ReferenceCache;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

import java.awt.*;

public class TileOverlay extends DoublyLinkedNode {

  public static final ReferenceCache<TileOverlay> cache = new ReferenceCache<>(64);

  public static ReferenceTable table;
  public static FontMetrics aFontMetrics1467;

  public boolean hideUnderlay;

  public int secondaryRgb;
  public int rgb;
  public int material;
  public int hue;
  public int secondaryHue;
  public int saturation;
  public int secondarySaturation;
  public int lightness;
  public int secondaryLightness;

  public TileOverlay() {
    rgb = 0;
    material = -1;
    hideUnderlay = true;
    secondaryRgb = -1;
  }

  void decode(Buffer var1, int var2) {
    if (var2 == 1) {
      rgb = var1.g3();
    } else if (var2 == 2) {
      material = var1.g1();
    } else if (var2 == 5) {
      hideUnderlay = false;
    } else if (var2 == 7) {
      secondaryRgb = var1.g3();
    } else if (var2 == 8) {

    }
  }

  void method1007(int var1) {
    double var2 = (double) (var1 >> 16 & 255) / 256.0D;
    double var4 = (double) (var1 >> 8 & 255) / 256.0D;
    double var6 = (double) (var1 & 255) / 256.0D;
    double var8 = var2;
    if (var4 < var2) {
      var8 = var4;
    }

    if (var6 < var8) {
      var8 = var6;
    }

    double var10 = var2;
    if (var4 > var2) {
      var10 = var4;
    }

    if (var6 > var10) {
      var10 = var6;
    }

    double var12 = 0.0D;
    double var14 = 0.0D;
    double var16 = (var8 + var10) / 2.0D;
    if (var8 != var10) {
      if (var16 < 0.5D) {
        var14 = (var10 - var8) / (var8 + var10);
      }

      if (var16 >= 0.5D) {
        var14 = (var10 - var8) / (2.0D - var10 - var8);
      }

      if (var10 == var2) {
        var12 = (var4 - var6) / (var10 - var8);
      } else if (var10 == var4) {
        var12 = (var6 - var2) / (var10 - var8) + 2.0D;
      } else if (var6 == var10) {
        var12 = (var2 - var4) / (var10 - var8) + 4.0D;
      }
    }

    var12 /= 6.0D;
    hue = (int) (256.0D * var12);
    saturation = (int) (256.0D * var14);
    lightness = (int) (var16 * 256.0D);
    if (saturation < 0) {
      saturation = 0;
    } else if (saturation > 255) {
      saturation = 255;
    }

    if (lightness < 0) {
      lightness = 0;
    } else if (lightness > 255) {
      lightness = 255;
    }

  }

  public void method499() {
    if (secondaryRgb != -1) {
      method1007(secondaryRgb);
      secondaryHue = hue;
      secondarySaturation = saturation;
      secondaryLightness = lightness;
    }

    method1007(rgb);
  }

  public void decode(Buffer buffer) {
    while (true) {
      int opcode = buffer.g1();
      if (opcode == 0) {
        return;
      }
      decode(buffer, opcode);
    }
  }
}
