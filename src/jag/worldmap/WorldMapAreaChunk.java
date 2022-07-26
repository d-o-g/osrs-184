package jag.worldmap;

import jag.opcode.Buffer;

public interface WorldMapAreaChunk {

    boolean contains(int x, int y);

    boolean contains(int level, int x, int y);

    void method93(WorldMapCacheArea var1);

    WorldMapPosition getPosition(int x, int y);

    int[] outline(int level, int x, int y);

    void decode(Buffer var1);
}
