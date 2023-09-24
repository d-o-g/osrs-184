package jagex.oldscape.client.worldmap;

public class WorldMapPosition {

  public final int floorLevel;
  public int y;
  public int x;

  public WorldMapPosition(WorldMapPosition pos) {
    floorLevel = pos.floorLevel;
    x = pos.x;
    y = pos.y;
  }

  public WorldMapPosition(int floorLevel, int x, int y) {
    this.floorLevel = floorLevel;
    this.x = x;
    this.y = y;
  }

  public WorldMapPosition(int hash) {
    if (hash == -1) {
      floorLevel = -1;
    } else {
      floorLevel = hash >> 28 & 3;
      x = hash >> 14 & 16383;
      y = hash & 16383;
    }

  }

  public int getHash() {
    return floorLevel << 28 | x << 14 | y;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof WorldMapPosition)) {
      return false;
    }

    WorldMapPosition pos = (WorldMapPosition) o;
    if (floorLevel != pos.floorLevel) {
      return false;
    }
    if (x != pos.x) {
      return false;
    }
    return y == pos.y;
  }

  public int hashCode() {
    return getHash();
  }

  public String toString() {
    return floorLevel + "," + (x >> 6) + "," + (y >> 6) + "," + (x & 63) + "," + (y & 63);
  }
}
