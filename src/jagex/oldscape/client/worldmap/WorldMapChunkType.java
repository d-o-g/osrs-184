package jagex.oldscape.client.worldmap;

import jagex.oldscape.EnumType;

public enum WorldMapChunkType implements EnumType {

  anEnum_Sub2_624(0, (byte) 0),
  anEnum_Sub2_625(3, (byte) 1),
  anEnum_Sub2_622(2, (byte) 2),
  anEnum_Sub2_621(1, (byte) 3);

  public final byte index;
  public final int identifier;

  WorldMapChunkType(int identifier, byte index) {
    this.identifier = identifier;
    this.index = index;
  }

  public int getOrdinal() {
    return index;
  }
}
