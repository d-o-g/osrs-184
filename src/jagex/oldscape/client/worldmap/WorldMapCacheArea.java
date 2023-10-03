package jagex.oldscape.client.worldmap;

import jagex.oldscape.EnumType;
import jagex.messaging.Buffer;

import java.util.Iterator;

public class WorldMapCacheArea {

  public String name;
  public int background;
  public String jagName;
  public boolean mainland;
  public int id;
  public int baseZoom;
  public WorldMapPosition base;
  public java.util.LinkedList<WorldMapAreaChunk> chunks;
  public int regionMaxX;
  public int regionMinX;
  public int regionMaxY;
  public int regionMinY;

  public WorldMapCacheArea() {
    this.id = -1;
    this.background = -1;
    this.baseZoom = -1;
    this.base = null;
    this.regionMinX = Integer.MAX_VALUE;
    this.regionMaxX = 0;
    this.regionMinY = Integer.MAX_VALUE;
    this.regionMaxY = 0;
    this.mainland = false;
  }

  public void decode(Buffer buffer, int id) {
    this.id = id;
    this.jagName = buffer.gstr();
    this.name = buffer.gstr();
    this.base = new WorldMapPosition(buffer.g4());
    this.background = buffer.g4();
    buffer.g1();
    this.mainland = buffer.g1() == 1;
    this.baseZoom = buffer.g1();
    int chunkCount = buffer.g1();
    this.chunks = new java.util.LinkedList<>();

    for (int i = 0; i < chunkCount; ++i) {
      this.chunks.add(this.decodeChunk(buffer));
    }

    this.adjustChunkAreas();
  }

  public boolean contains(int x, int y) {
    int regionX = x / 64;
    int regionY = y / 64;
    if (regionX < this.regionMinX || regionX > this.regionMaxX) {
      return false;
    }

    if (regionY < this.regionMinY || regionY > this.regionMaxY) {
      return false;
    }

    for (WorldMapAreaChunk chunk : this.chunks) {
      if (chunk.contains(x, y)) {
        return true;
      }
    }

    return false;
  }


  public int getId() {
    return this.id;
  }

  public int[] toScreen(int x, int y, int level) {
    for (WorldMapAreaChunk chunk : this.chunks) {
      if (chunk.contains(level, x, y)) {
        return chunk.outline(level, x, y);
      }
    }

    return null;
  }

  public int getMinRegionX() {
    return this.regionMinX;
  }

  public int getMinRegionY() {
    return this.regionMinY;
  }

  public boolean contains(int level, int x, int y) {
    for (WorldMapAreaChunk chunk : this.chunks) {
      if (chunk.contains(level, x, y)) {
        return true;
      }
    }

    return false;
  }

  public WorldMapPosition getPosition(int x, int y) {
    for (WorldMapAreaChunk chunk : this.chunks) {
      if (chunk.contains(x, y)) {
        return chunk.getPosition(x, y);
      }
    }

    return null;
  }

  public int getLevel() {
    return this.base.floorLevel;
  }

  public String getJagName() {
    return this.jagName;
  }

  public int getBaseX() {
    return this.base.x;
  }

  public int getBaseY() {
    return this.base.y;
  }

  public int getZoomPercent() {
    return this.baseZoom;
  }

  void adjustChunkAreas() {
    for (WorldMapAreaChunk chunk : this.chunks) {
      chunk.adjustArea(this);
    }
  }

  WorldMapAreaChunk decodeChunk(Buffer buffer) {
    int ordinal = buffer.g1();
    WorldMapChunkType[] types = new WorldMapChunkType[]{WorldMapChunkType.anEnum_Sub2_624, WorldMapChunkType.anEnum_Sub2_622, WorldMapChunkType.anEnum_Sub2_621, WorldMapChunkType.anEnum_Sub2_625};
    WorldMapChunkType type = (WorldMapChunkType) EnumType.getByOrdinal(types, ordinal);
    WorldMapAreaChunk chunk;
    switch (type.identifier) {
      case 0:
        chunk = new RectangularWorldMapAreaChunk();
        break;
      case 1:
        chunk = new WorldMapChunkRegion();
        break;
      case 2:
        chunk = new WorldMapAreaChunk_Sub3();
        break;
      case 3:
        chunk = new PreciseWorldMapAreaChunk();
        break;
      default:
        throw new IllegalStateException("");
    }

    chunk.decode(buffer);
    return chunk;
  }

  public int getRegionMaxX() {
    return this.regionMaxX;
  }

  public boolean isMainland() {
    return this.mainland;
  }

  public int getRegionMaxY() {
    return this.regionMaxY;
  }

  public int getBackgroundColor() {
    return this.background;
  }

  public String getName() {
    return this.name;
  }

  public WorldMapPosition getBasePosition() {
    return new WorldMapPosition(this.base);
  }
}
