package jag.worldmap;

import jag.EnumType;
import jag.game.stockmarket.StockMarketOfferNameComparator;
import jag.game.type.HitsplatDefinition;
import jag.js5.*;
import jag.opcode.Buffer;

import java.util.Iterator;

public class WorldMapCacheArea {

    public static int anInt130;
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

    public static void method77(int var0, ResourceCache var1, Archive var2) {
        byte[] var3 = null;
        synchronized (CacheRequestWorker.requests) {
            for (CacheRequest req = CacheRequestWorker.requests.head(); req != null; req = CacheRequestWorker.requests.next()) {
                if ((long) var0 == req.key && var1 == req.cache && req.type == 0) {
                    var3 = req.data;
                    break;
                }
            }
        }

        if (var3 != null) {
            var2.method486(var1, var0, var3, true);
        } else {
            byte[] var4 = var1.read(var0);
            var2.method486(var1, var0, var4, true);
        }
    }

    public static void method88(ReferenceTable var0, ReferenceTable var1, ReferenceTable var2) {
        HitsplatDefinition.table = var0;
        StockMarketOfferNameComparator.aReferenceTable480 = var1;
        HitsplatDefinition.aReferenceTable1515 = var2;
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
        int var3 = buffer.g1();
        this.chunks = new java.util.LinkedList<>();

        for (int var4 = 0; var4 < var3; ++var4) {
            this.chunks.add(this.decodeChunk(buffer));
        }

        this.method80();
    }

    public boolean contains(int var1, int var2) {
        int var3 = var1 / 64;
        int var4 = var2 / 64;
        if (var3 >= this.regionMinX && var3 <= this.regionMaxX) {
            if (var4 >= this.regionMinY && var4 <= this.regionMaxY) {
                Iterator<WorldMapAreaChunk> var5 = this.chunks.iterator();

                WorldMapAreaChunk var6;
                do {
                    if (!var5.hasNext()) {
                        return false;
                    }

                    var6 = var5.next();
                } while (!var6.contains(var1, var2));

                return true;
            }
            return false;
        }
        return false;
    }

    public int getId() {
        return this.id;
    }

    public int[] toScreen(int var1, int var2, int var3) {
        Iterator<WorldMapAreaChunk> var4 = this.chunks.iterator();

        WorldMapAreaChunk var5;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            var5 = var4.next();
        } while (!var5.contains(var1, var2, var3));

        return var5.outline(var1, var2, var3);
    }

    public int getMinRegionX() {
        return this.regionMinX;
    }

    public int getMinRegionY() {
        return this.regionMinY;
    }

    public boolean contains(int level, int x, int y) {
        Iterator<WorldMapAreaChunk> var4 = this.chunks.iterator();

        WorldMapAreaChunk var5;
        do {
            if (!var4.hasNext()) {
                return false;
            }

            var5 = var4.next();
        } while (!var5.contains(level, x, level));

        return true;
    }

    public WorldMapPosition getPosition(int x, int y) {
        Iterator<WorldMapAreaChunk> var3 = this.chunks.iterator();

        WorldMapAreaChunk var4;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            var4 = var3.next();
        } while (!var4.contains(x, y));

        return var4.getPosition(x, y);
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

    void method80() {

        for (WorldMapAreaChunk var2 : this.chunks) {
            var2.method93(this);
        }

    }

    WorldMapAreaChunk decodeChunk(Buffer var1) {
        int var2 = var1.g1();
        WorldMapChunkType[] types = new WorldMapChunkType[]{WorldMapChunkType.anEnum_Sub2_624, WorldMapChunkType.anEnum_Sub2_622, WorldMapChunkType.anEnum_Sub2_621, WorldMapChunkType.anEnum_Sub2_625};
        WorldMapChunkType type = (WorldMapChunkType) EnumType.getByOrdinal(types, var2);
        WorldMapAreaChunk chunk;
        switch (type.type) {
            case 0:
                chunk = new RectangularWorldMapAreaChunk();
                break;
            case 1:
                chunk = new WorldMapAreaChunk_Sub2();
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

        chunk.decode(var1);
        return chunk;
    }

    public int method70() {
        return this.regionMaxX;
    }

    public boolean isMainland() {
        return this.mainland;
    }

    public int method72() {
        return this.regionMaxY;
    }

    public int method67() {
        return this.background;
    }

    public String getName() {
        return this.name;
    }

    public WorldMapPosition method69() {
        return new WorldMapPosition(this.base);
    }
}
