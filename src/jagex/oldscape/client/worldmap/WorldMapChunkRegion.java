package jagex.oldscape.client.worldmap;

import jagex.messaging.Buffer;

public class WorldMapChunkRegion implements WorldMapAreaChunk {

  int regionLevel;
  int baseY;
  int regionX;
  int regionCount;
  int offsetX;
  int baseX;
  int levelCount;
  int regionY;
  int offsetY;
  int baseLevel;

  @Override
  public boolean contains(int x, int y) {
    int minX = (this.baseX << 6) + (this.baseY << 3);
    int maxX = minX + 7;
    int minY = (this.baseLevel << 6) + (this.levelCount << 3);
    int maxY = minY + 7;

    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  @Override
  public boolean contains(int level, int x, int y) {
    if (level >= this.regionLevel && level < this.regionLevel + this.regionCount) {
      int minX = (this.offsetX << 6) + (this.baseX << 3);
      int maxX = minX + 7;
      int minY = (this.offsetY << 6) + (this.baseY << 3);
      int maxY = minY + 7;
      return x >= minX && x <= maxX && y >= minY && y <= maxY;
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

    int relativeX = this.offsetX * 64 - this.regionX * 64 + (this.baseX * 8 - this.baseY * 8) + x;
    int relativeY = this.offsetY * 64 - this.regionY * 64 + y + (this.baseLevel * 8 - this.levelCount * 8);
    return new WorldMapPosition(this.regionLevel, relativeX, relativeY);
  }

  @Override
  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }

    return new int[]{this.regionX * 64 - this.offsetX * 64 + x + (this.baseY * 8 - this.baseX * 8), y + (this.regionY * 64 - this.offsetY * 64) + (this.levelCount * 8 - this.baseLevel * 8)};
  }

  @Override
  public void decode(Buffer buffer) {
    regionLevel = buffer.g1();
    regionCount = buffer.g1();
    offsetX = buffer.g2();
    baseX = buffer.g1();
    offsetY = buffer.g2();
    baseLevel = buffer.g1();
    regionX = buffer.g2();
    baseY = buffer.g1();
    regionY = buffer.g2();
    levelCount = buffer.g1();
    postDecode();
  }

  void postDecode() {

  }
}
