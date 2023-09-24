package jagex.oldscape.client.worldmap;

import jagex.oldscape.BootSprites;
import jagex.jagex3.sound.AudioSystem;
import jagex.jagex3.graphics.IndexedSprite;
import jagex.jagex3.js5.ReferenceTable;
import jagex.jagex3.js5.ResourceCache;
import jagex.messaging.Buffer;

public class WorldMapTileDecor_Sub2 extends WorldMapTileDecor {

  public static ResourceCache referenceCache;
  public static BootSprites aBootSprites_647;
  public static IndexedSprite[] aDoublyNode_Sub24_Sub4Array648;

  WorldMapTileDecor_Sub2() {

  }

  public static void method470(int var0, ReferenceTable var1, String var2, String var3, int var4, boolean var5) {
    int var6 = var1.getGroup(var2);
    int var7 = var1.getFile(var6, var3);
    AudioSystem.state = 1;
    AudioSystem.tracks = var1;
    AudioSystem.trackGroup = var6;
    AudioSystem.trackFile = var7;
    AudioSystem.volume = var4;
    AudioSystem.aBoolean620 = var5;
    AudioSystem.pcmSampleLength = var0;
  }

  void method109(Buffer var1) {
    int var2 = var1.g1();
    if (var2 != WorldMapGroundDecorType.A_WORLD_MAP_TYPE___311.index) {
      throw new IllegalStateException("");
    }
    this.anInt515 = var1.g1();
    this.levelCount = var1.g1();
    this.anInt519 = var1.g2() * 4096;
    this.anInt510 = var1.g2() * 64;
    this.anInt517 = var1.g2();
    this.anInt516 = var1.g2();
    this.anInt514 = var1.method1051();
    this.anInt509 = var1.method1051();
  }

  void decode(Buffer buffer) {
    levelCount = Math.min(levelCount, 4);
    aShortArrayArrayArray518 = new short[1][64][64];
    aShortArrayArrayArray508 = new short[levelCount][64][64];
    aByteArrayArrayArray506 = new byte[levelCount][64][64];
    aByteArrayArrayArray505 = new byte[levelCount][64][64];
    aWorldMapDecorArrayArrayArrayArray511 = new WorldMapDecor[levelCount][64][64][];
    int var2 = buffer.g1();
    if (var2 != WorldMapGroundDecorType2.A_WORLD_MAP_GROUND_DECOR_TYPE_2___301.index) {
      throw new IllegalStateException("");
    }
    int var3 = buffer.g1();
    int var4 = buffer.g1();
    if (var3 == anInt517 && var4 == anInt516) {
      for (int x = 0; x < 64; ++x) {
        for (int y = 0; y < 64; ++y) {
          decode(x, y, buffer);
        }
      }

    } else {
      throw new IllegalStateException("");
    }
  }

  public boolean equals(Object var1) {
    if (!(var1 instanceof WorldMapTileDecor_Sub2)) {
      return false;
    }

    WorldMapTileDecor_Sub2 var2 = (WorldMapTileDecor_Sub2) var1;
    return var2.anInt517 == this.anInt517 && var2.anInt516 == this.anInt516;
  }

  public int hashCode() {
    return this.anInt517 | this.anInt516 << 8;
  }
}
