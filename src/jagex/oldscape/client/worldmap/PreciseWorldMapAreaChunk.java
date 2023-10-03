package jagex.oldscape.client.worldmap;

import jagex.messaging.Buffer;

public class PreciseWorldMapAreaChunk implements WorldMapAreaChunk {

  int regionX;
  int minLevel;
  int levelCount;
  int regionY;
  int offsetX;
  int offsetY;

  PreciseWorldMapAreaChunk() {

  }

  @Override
  public boolean contains(int x, int y) {
    return x >> 6 == this.regionX && y >> 6 == this.regionY;
  }

  @Override
  public boolean contains(int level, int x, int y) {
    if (level >= this.minLevel && level < this.levelCount + this.minLevel) {
      return x >> 6 == this.offsetX && y >> 6 == this.offsetY;
    }
    return false;
  }

  @Override
  public void adjustArea(WorldMapCacheArea area) {
    if (area.regionMinX > this.regionX) {
      area.regionMinX = this.regionX;
    }

    if (area.regionMaxX < this.regionX) {
      area.regionMaxX = this.regionX;
    }

    if (area.regionMinY > this.regionY) {
      area.regionMinY = this.regionY;
    }

    if (area.regionMaxY < this.regionY) {
      area.regionMaxY = this.regionY;
    }
  }

  @Override
  public WorldMapPosition getPosition(int x, int y) {
    if (!this.contains(x, y)) {
      return null;
    }

    int relativeX = this.offsetX * 64 - this.regionX * 64 + x;
    int relativeY = this.offsetY * 64 - this.regionY * 64 + y;
    return new WorldMapPosition(this.minLevel, relativeX, relativeY);
  }

  @Override
  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }

    return new int[]{
        x + (this.regionX * 64 - this.offsetX * 64),
        y + (this.regionY * 64 - this.offsetY * 64)
    };
  }

  void postDecode() {

  }

  @Override
  public void decode(Buffer buffer) {
    minLevel = buffer.g1();
    levelCount = buffer.g1();
    offsetX = buffer.g2();
    offsetY = buffer.g2();
    regionX = buffer.g2();
    regionY = buffer.g2();
    postDecode();
  }
}
