package jagex.jagex3.graphics;

import jagex.datastructure.DoublyLinkedNode;

public class ComponentSprite extends DoublyLinkedNode {

  public final int[] pixels;
  public final int[] scanlineOffsets;

  public final int width;
  public final int height;

  public ComponentSprite(int var1, int var2, int[] var3, int[] var4) {
    this.width = var1;
    this.height = var2;
    this.scanlineOffsets = var3;
    this.pixels = var4;
  }

  public boolean contains(int var1, int var2) {
    if (var2 >= 0 && var2 < this.pixels.length) {
      int var3 = this.pixels[var2];
      return var1 >= var3 && var1 <= var3 + this.scanlineOffsets[var2];
    }

    return false;
  }
}
