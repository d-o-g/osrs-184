package jagex.oldscape.client.worldmap;

import jagex.messaging.*;

public class WorldMapAreaChunk_Sub3 implements WorldMapAreaChunk {

  int anInt535;
  int anInt285;
  int anInt279;
  int anInt287;
  int anInt536;
  int anInt278;
  int anInt281;
  int anInt534;
  int anInt282;
  int anInt277;
  int anInt533;
  int anInt286;
  int anInt280;
  int anInt283;

  @Override
  public boolean contains(int x, int y) {
    return x >= (this.anInt279 << 6) + (this.anInt535 << 3) && x <= (this.anInt279 << 6) + (this.anInt536 << 3) + 7 && y >= (this.anInt277 << 6) + (this.anInt534 << 3) && y <= (this.anInt277 << 6) + (this.anInt533 << 3) + 7;
  }

  @Override
  public boolean contains(int level, int x, int y) {
    if (level >= this.anInt285 && level < this.anInt285 + this.anInt287) {
      return x >= (this.anInt281 << 6) + (this.anInt278 << 3) && x <= (this.anInt281 << 6) + (this.anInt282 << 3) + 7 && y >= (this.anInt280 << 6) + (this.anInt286 << 3) && y <= (this.anInt280 << 6) + (this.anInt283 << 3) + 7;
    }
    return false;
  }

  @Override
  public void adjustArea(WorldMapCacheArea area) {
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

  @Override
  public WorldMapPosition getPosition(int x, int y) {
    if (!this.contains(x, y)) {
      return null;
    }

    int var3 = this.anInt281 * 64 - this.anInt279 * 64 + (this.anInt278 * 8 - this.anInt535 * 8) + x;
    int var4 = this.anInt280 * 64 - this.anInt277 * 64 + y + (this.anInt286 * 8 - this.anInt534 * 8);
    return new WorldMapPosition(this.anInt285, var3, var4);
  }

  @Override
  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }

    return new int[]{this.anInt279 * 64 - this.anInt281 * 64 + x + (this.anInt535 * 8 - this.anInt278 * 8), y + (this.anInt277 * 64 - this.anInt280 * 64) + (this.anInt534 * 8 - this.anInt286 * 8)};
  }

  public void decode(Buffer buffer) {
    anInt285 = buffer.g1();
    anInt287 = buffer.g1();
    anInt281 = buffer.g2();
    anInt278 = buffer.g1();
    anInt282 = buffer.g1();
    anInt280 = buffer.g2();
    anInt286 = buffer.g1();
    anInt283 = buffer.g1();
    anInt279 = buffer.g2();
    anInt535 = buffer.g1();
    anInt536 = buffer.g1();
    anInt277 = buffer.g2();
    anInt534 = buffer.g1();
    anInt533 = buffer.g1();
    postDecode();
  }

  void postDecode() {

  }
}
