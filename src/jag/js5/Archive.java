package jag.js5;

import jag.ByteBufferProvider;
import jag.game.client;
import jag.graphics.DefaultMaterialProvider;
import jag.opcode.Buffer;
import jag.worldmap.WorldMap;
import jag.worldmap.WorldMapCacheArea;
import jag.worldmap.WorldMapIcon;

import java.util.zip.CRC32;

public class Archive extends ReferenceTable {

    static final CRC32 crc32 = new CRC32();
    public static DefaultMaterialProvider materialProvider;

    public static Archive bootSprites;
    public static Archive huffman;
    public static Archive models;
    public static Archive config;
    public static Archive interfaces;
    public static Archive skeletons;
    public static Archive landscape;
    public static Archive fonts;
    public static Archive audioTracks2;
    public static Archive textures;
    public static Archive cs2;
    public static Archive audioEffects;
    public static Archive sprites;
    public static Archive mapscene;
    public static Archive audioEffects3;
    public static Archive audioTracks;
    public static Archive worldmap;
    public static Archive skins;
    public static Archive audioEffects2;
    public static Archive mapland;

    ResourceCache meta;
    int crc;
    ResourceCache cache;
    volatile boolean loaded;
    volatile boolean[] valid;
    int index;
    int version;
    int anInt670;
    boolean forceRequest;

    public Archive(ResourceCache cache, ResourceCache meta, int index, boolean soft, boolean shallow, boolean forceRequest) {
        super(soft, shallow);
        this.cache = cache;
        this.meta = meta;
        loaded = false;
        this.forceRequest = false;
        anInt670 = -1;
        this.index = index;
        this.forceRequest = forceRequest;
        int var8 = this.index;
        if (WorldMapIcon.aBuffer314 != null) {
            WorldMapIcon.aBuffer314.pos = var8 * 8 + 5;
            int var9 = WorldMapIcon.aBuffer314.g4();
            int var10 = WorldMapIcon.aBuffer314.g4();
            method493(var9, var10);
        } else {
            Js5Worker.request(null, 255, 255, 0, (byte) 0, true);
            Js5Worker.ARCHIVES[var8] = this;
        }

    }

    public static void method485() {
        if (WorldMap.heatmap != null) {
            client.anInt929 = client.engineCycle;
            WorldMap.heatmap.method993();

            for (int var0 = 0; var0 < client.players.length; ++var0) {
                if (client.players[var0] != null) {
                    WorldMap.heatmap.method994(client.baseX + (client.players[var0].absoluteX >> 7), client.baseY + (client.players[var0].absoluteY >> 7));
                }
            }
        }

    }

    void load(int i) {
        if (cache != null && valid != null && valid[i]) {
            WorldMapCacheArea.method77(i, cache, this);
        } else {
            Js5Worker.request(this, index, i, super.groupCrcs[i], (byte) 2, true);
        }

    }

    public boolean isPresent(int file) {
        return getFileIds(file) != null;
    }

    int getLoadingPercent(int group) {
        if (super.groups[group] != null) {
            return 100;
        }
        if (valid[group]) {
            return 100;
        }
        int var2 = index;
        long var3 = (var2 << 16) + group;
        int var5;
        if (Js5Worker.current != null && Js5Worker.current.key == var3) {
            var5 = Js5Worker.archiveBuffer.pos * 99 / (Js5Worker.archiveBuffer.payload.length - Js5Worker.current.padding) + 1;
        } else {
            var5 = 0;
        }

        return var5;
    }

    void method487() {
        valid = new boolean[super.groups.length];

        int var1;
        for (var1 = 0; var1 < valid.length; ++var1) {
            valid[var1] = false;
        }

        if (cache == null) {
            loaded = true;
        } else {
            anInt670 = -1;

            for (var1 = 0; var1 < valid.length; ++var1) {
                if (super.childrenCounts[var1] > 0) {
                    ResourceCache cache = this.cache;
                    CacheRequest req = new CacheRequest();
                    req.type = 1;
                    req.key = var1;
                    req.cache = cache;
                    req.archive = this;
                    synchronized (CacheRequestWorker.requests) {
                        CacheRequestWorker.requests.add(req);
                    }

                    synchronized (CacheRequestWorker.mutex) {
                        if (CacheRequestWorker.state == 0) {
                            CacheRequestWorker.thread = new Thread(new CacheRequestWorker());
                            CacheRequestWorker.thread.setDaemon(true);
                            CacheRequestWorker.thread.start();
                            CacheRequestWorker.thread.setPriority(5);
                        }

                        CacheRequestWorker.state = 600;
                    }

                    anInt670 = var1;
                }
            }

            if (anInt670 == -1) {
                loaded = true;
            }

        }
    }

    void method490(int var1) {
        int var2 = index;
        long var3 = (var2 << 16) + var1;
        ResourceRequest var5 = Js5Worker.pending.lookup(var3);
        if (var5 != null) {
            Js5Worker.pendingQueue.add(var5);
        }
    }

    public boolean isValid(int var1) {
        return valid[var1];
    }

    public void method486(ResourceCache var1, int var2, byte[] var3, boolean var4) {
        int var5;
        if (var1 == meta) {
            if (loaded) {
                throw new RuntimeException();
            }

            if (var3 == null) {
                Js5Worker.request(this, 255, index, crc, (byte) 0, true);
                return;
            }

            crc32.reset();
            crc32.update(var3, 0, var3.length);
            var5 = (int) crc32.getValue();
            if (var5 != crc) {
                Js5Worker.request(this, 255, index, crc, (byte) 0, true);
                return;
            }

            Buffer buffer = new Buffer(decodeContainer(var3));
            int format = buffer.g1();
            if (format != 5 && format != 6) {
                throw new RuntimeException(format + "," + index + "," + var2);
            }

            int version = 0;
            if (format >= 6) {
                version = buffer.g4();
            }

            if (version != this.version) {
                Js5Worker.request(this, 255, index, crc, (byte) 0, true);
                return;
            }

            decode(var3);
            method487();
        } else {
            if (!var4 && var2 == anInt670) {
                loaded = true;
            }

            if (var3 == null || var3.length <= 2) {
                valid[var2] = false;
                if (forceRequest || var4) {
                    Js5Worker.request(this, index, var2, super.groupCrcs[var2], (byte) 2, var4);
                }

                return;
            }

            crc32.reset();
            crc32.update(var3, 0, var3.length - 2);
            var5 = (int) crc32.getValue();
            int var6 = ((var3[var3.length - 2] & 255) << 8) + (var3[var3.length - 1] & 255);
            if (var5 != super.groupCrcs[var2] || var6 != super.groupVersions[var2]) {
                valid[var2] = false;
                if (forceRequest || var4) {
                    Js5Worker.request(this, index, var2, super.groupCrcs[var2], (byte) 2, var4);
                }

                return;
            }

            valid[var2] = true;
            if (var4) {
                super.groups[var2] = ByteBufferProvider.allocateDirect(var3);
            }
        }

    }

    public void method493(int crc, int version) {
        this.crc = crc;
        this.version = version;
        if (meta != null) {
            WorldMapCacheArea.method77(index, meta, this);
        } else {
            Js5Worker.request(this, 255, index, this.crc, (byte) 0, true);
        }

    }

    public void method498(int var1, byte[] var2, boolean var3, boolean var4) {
        if (var3) {
            if (loaded) {
                throw new RuntimeException();
            }

            if (meta != null) {
                CacheRequest.write(index, var2, meta);
            }

            decode(var2);
            method487();
        } else {
            var2[var2.length - 2] = (byte) (super.groupVersions[var1] >> 8);
            var2[var2.length - 1] = (byte) super.groupVersions[var1];
            if (cache != null) {
                CacheRequest.write(var1, var2, cache);
                valid[var1] = true;
            }

            if (var4) {
                super.groups[var1] = ByteBufferProvider.allocateDirect(var2);
            }
        }

    }

    public int method494() {
        if (loaded) {
            return 100;
        }
        if (super.groups != null) {
            return 99;
        }
        int var1 = index;
        long var2 = var1 + 16711680;
        int var4;
        if (Js5Worker.current != null && Js5Worker.current.key == var2) {
            var4 = Js5Worker.archiveBuffer.pos * 99 / (Js5Worker.archiveBuffer.payload.length - Js5Worker.current.padding) + 1;
        } else {
            var4 = 0;
        }

        int var5 = var4;
        if (var4 >= 100) {
            var5 = 99;
        }

        return var5;
    }

    public int method489() {
        int var1 = 0;
        int var2 = 0;

        int var3;
        for (var3 = 0; var3 < super.groups.length; ++var3) {
            if (super.childrenCounts[var3] > 0) {
                var1 += 100;
                var2 += getLoadingPercent(var3);
            }
        }

        if (var1 == 0) {
            return 100;
        }
        var3 = var2 * 100 / var1;
        return var3;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
