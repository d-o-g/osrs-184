package jagex.oldscape.client.worldmap;

import jagex.oldscape.ClientParameter;
import jagex.messaging.Buffer;

public class PreciseWorldMapAreaChunk implements WorldMapAreaChunk {

  public static ClientParameter loginTypeParameter;

  int anInt279;
  int minLevel;
  int levelCount;
  int anInt277;
  int x;
  int y;

  PreciseWorldMapAreaChunk() {

  }

  public boolean contains(int x, int y) {
    return x >> 6 == this.anInt279 && y >> 6 == this.anInt277;
  }

  public boolean contains(int level, int x, int y) {
    if (level >= this.minLevel && level < this.levelCount + this.minLevel) {
      return x >> 6 == this.x && y >> 6 == this.y;
    }
    return false;
  }

  public void method93(WorldMapCacheArea area) {
    if (area.regionMinX > this.anInt279) {
      area.regionMinX = this.anInt279;
    }

    if (area.regionMaxX < this.anInt279) {
      area.regionMaxX = this.anInt279;
    }

    if (area.regionMinY > this.anInt277) {
      area.regionMinY = this.anInt277;
    }

    if (area.regionMaxY < this.anInt277) {
      area.regionMaxY = this.anInt277;
    }

  }

  public WorldMapPosition getPosition(int x, int y) {
    if (!this.contains(x, y)) {
      return null;
    }
    int var3 = this.x * 64 - this.anInt279 * 64 + x;
    int var4 = this.y * 64 - this.anInt277 * 64 + y;
    return new WorldMapPosition(this.minLevel, var3, var4);
  }

  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }
    return new int[]{
        x + (this.anInt279 * 64 - this.x * 64),
        y + (this.anInt277 * 64 - this.y * 64)
    };
  }

  void method148() {

  }

  public void decode(Buffer buffer) {
    minLevel = buffer.g1();
    levelCount = buffer.g1();
    x = buffer.g2();
    y = buffer.g2();
    anInt279 = buffer.g2();
    anInt277 = buffer.g2();
    method148();
  }
}
