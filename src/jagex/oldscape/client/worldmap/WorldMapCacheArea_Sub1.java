package jagex.oldscape.client.worldmap;

import jagex.messaging.Buffer;

import java.util.HashSet;
import java.util.List;

public class WorldMapCacheArea_Sub1 extends WorldMapCacheArea {

  List<WorldMapIcon> icons;
  HashSet<WorldMapTileDecor> decor1;
  HashSet<WorldMapTileDecor> decor2;

  WorldMapCacheArea_Sub1() {

  }

  public void decode(Buffer var1, boolean var2) {
    this.icons = new java.util.LinkedList<>();
    int iconCount = var1.g2();

    for (int i = 0; i < iconCount; ++i) {
      int id = var1.method1051();
      WorldMapPosition position = new WorldMapPosition(var1.g4());
      boolean shown = var1.g1() == 1;
      if (var2 || !shown) {
        this.icons.add(new WorldMapLabelIcon(null, position, id, null));
      }
    }
  }

  public void decode(Buffer buffer1, Buffer buffer2, int var3, boolean var4) {
    this.decode(buffer1, var3);
    int decor1size = buffer2.g2();
    this.decor1 = new HashSet<>(decor1size);

    for (int i = 0; i < decor1size; ++i) {
      WorldMapTileDecor_Sub2 decor = new WorldMapTileDecor_Sub2();

      try {
        decor.decode2(buffer2);
      } catch (IllegalStateException var12) {
        continue;
      }

      this.decor1.add(decor);
    }

    int decor2size = buffer2.g2();
    this.decor2 = new HashSet<>(decor2size);

    for (int i = 0; i < decor2size; ++i) {
      WorldMapTileDecor_Sub1 decor = new WorldMapTileDecor_Sub1();

      try {
        decor.decode2(buffer2);
      } catch (IllegalStateException var11) {
        continue;
      }

      this.decor2.add(decor);
    }

    this.decode(buffer2, var4);
  }
}
