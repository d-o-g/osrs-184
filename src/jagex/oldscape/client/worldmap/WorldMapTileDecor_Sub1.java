package jagex.oldscape.client.worldmap;

import jagex.messaging.Buffer;

public class WorldMapTileDecor_Sub1 extends WorldMapTileDecor {

  int anInt163;
  int anInt164;
  int anInt167;
  int anInt165;

  WorldMapTileDecor_Sub1() {

  }

  int method110() {
    return anInt165;
  }

  int method108() {
    return anInt167;
  }

  int method107() {
    return anInt164;
  }

  void decode(Buffer var1) {
    levelCount = Math.min(levelCount, 4);
    aShortArrayArrayArray518 = new short[1][64][64];
    aShortArrayArrayArray508 = new short[levelCount][64][64];
    aByteArrayArrayArray506 = new byte[levelCount][64][64];
    aByteArrayArrayArray505 = new byte[levelCount][64][64];
    aWorldMapDecorArrayArrayArrayArray511 = new WorldMapDecor[levelCount][64][64][];
    int var2 = var1.g1();
    if (var2 != WorldMapGroundDecorType2.A_WORLD_MAP_GROUND_DECOR_TYPE_2___302.index) {
      throw new IllegalStateException("");
    }
    int var3 = var1.g1();
    int var4 = var1.g1();
    int var5 = var1.g1();
    int var6 = var1.g1();
    if (var3 == anInt517 && var4 == anInt516 && var5 == anInt165 && var6 == anInt167) {
      for (int var7 = 0; var7 < 8; ++var7) {
        for (int var8 = 0; var8 < 8; ++var8) {
          decode(var7 + anInt165 * 8, var8 + anInt167 * 8, var1);
        }
      }

    } else {
      throw new IllegalStateException("");
    }
  }

  public void method109(Buffer buffer) {
    int var2 = buffer.g1();
    if (var2 != WorldMapGroundDecorType.A_WORLD_MAP_TYPE___312.index) {
      throw new IllegalStateException("");
    }
    anInt515 = buffer.g1();
    levelCount = buffer.g1();
    anInt519 = buffer.g2() * 4096;
    anInt510 = buffer.g2() * 64;
    anInt164 = buffer.g1();
    anInt163 = buffer.g1();
    anInt517 = buffer.g2();
    anInt516 = buffer.g2();
    anInt165 = buffer.g1();
    anInt167 = buffer.g1();
    anInt514 = buffer.method1051();
    anInt509 = buffer.method1051();
  }

  int method106() {
    return anInt163;
  }

  public boolean equals(Object o) {
    if (!(o instanceof WorldMapTileDecor_Sub1)) {
      return false;
    }

    WorldMapTileDecor_Sub1 other = (WorldMapTileDecor_Sub1) o;
    if (other.anInt517 == anInt517 && anInt516 == other.anInt516) {
      return other.anInt165 == anInt165 && anInt167 == other.anInt167;
    }
    return false;
  }

  public int hashCode() {
    return anInt517 | anInt516 << 8 | anInt165 << 16 | anInt167 << 24;
  }
}
