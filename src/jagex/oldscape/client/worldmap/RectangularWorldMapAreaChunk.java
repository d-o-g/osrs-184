package jagex.oldscape.client.worldmap;

import jagex.messaging.Buffer;

public class RectangularWorldMapAreaChunk implements WorldMapAreaChunk {

  int minRegionX;
  int minLevel;
  int levelCount;
  int maxRegionX;
  int minRegionY;
  int minX;
  int maxRegionY;
  int maxX;
  int minY;
  int maxY;

  RectangularWorldMapAreaChunk() {

  }

  @Override
  public boolean contains(int x, int y) {
    return x >> 6 >= this.minRegionX && x >> 6 <= this.maxRegionX && y >> 6 >= this.minRegionY && y >> 6 <= this.maxRegionY;
  }

  @Override
  public boolean contains(int level, int x, int y) {
    if (level >= this.minLevel && level < this.levelCount + this.minLevel) {
      return x >> 6 >= this.minX && x >> 6 <= this.maxX && y >> 6 >= this.minY && y >> 6 <= this.maxY;
    }

    return false;
  }

  @Override
  public void adjustArea(WorldMapCacheArea area) {
    if (area.regionMinX > this.minRegionX) {
      area.regionMinX = this.minRegionX;
    }

    if (area.regionMaxX < this.maxRegionX) {
      area.regionMaxX = this.maxRegionX;
    }

    if (area.regionMinY > this.minRegionY) {
      area.regionMinY = this.minRegionY;
    }

    if (area.regionMaxY < this.maxRegionY) {
      area.regionMaxY = this.maxRegionY;
    }
  }

  @Override
  public WorldMapPosition getPosition(int x, int y) {
    if (!this.contains(x, y)) {
      return null;
    }

    int relativeX = this.minX * 64 - this.minRegionX * 64 + x;
    int relativeY = this.minY * 64 - this.minRegionY * 64 + y;
    return new WorldMapPosition(this.minLevel, relativeX, relativeY);
  }

  @Override
  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }

    return new int[]{this.minRegionX * 64 - this.minX * 64 + x, y + (this.minRegionY * 64 - this.minY * 64)};
  }

  @Override
  public void decode(Buffer buffer) {
    minLevel = buffer.g1();
    levelCount = buffer.g1();
    minX = buffer.g2();
    minY = buffer.g2();
    maxX = buffer.g2();
    maxY = buffer.g2();
    minRegionX = buffer.g2();
    minRegionY = buffer.g2();
    maxRegionX = buffer.g2();
    maxRegionY = buffer.g2();
    postDecode();
  }

  void postDecode() {

  }
}
