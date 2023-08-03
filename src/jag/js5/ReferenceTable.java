package jag.js5;

import jag.ByteBufferProvider;
import jag.game.stockmarket.StockmarketListingPriceComparator;
import jag.graphics.BaseFont;
import jag.opcode.Buffer;
import jag.worldmap.WorldMapTileDecor_Sub2;

import java.util.Arrays;

public abstract class ReferenceTable {

    public int hash;

    int entryCount;
    int[] entryIndices;

    boolean soft;

    int[] groupCrcs;
    int[] groupNames;
    int[] groupVersions;
    Object[] groups;

    int[] childrenCounts;
    int[][] childrenNames;
    int[][] childrenIndices;
    Object[][] childrenSizes;
    IdentityTable[] children;

    IdentityTable entry;

    boolean shallow;

    static final int maximumContainerSize = 0;
    static final GzipDecompressor gzipdecompressor = new GzipDecompressor();

    ReferenceTable(boolean soft, boolean shallow) {
        this.soft = soft;
        this.shallow = shallow;
    }

    static byte[] decodeContainer(byte[] data) {
        Buffer buffer = new Buffer(data);
        int type = buffer.g1();
        int compressed = buffer.g4();
        if (compressed < 0 || maximumContainerSize != 0 && compressed > maximumContainerSize) {
            throw new RuntimeException();
        }

        if (type == 0) { //uncompressed
            byte[] decoded = new byte[compressed];
            buffer.gdata(decoded, 0, compressed);
            return decoded;
        }

        int length = buffer.g4();
        if (length < 0 || maximumContainerSize != 0 && length > maximumContainerSize) {
            throw new RuntimeException();
        }

        byte[] decoded = new byte[length];
        if (type == 1) {
            Bzip2Decompressor.decompress(decoded, length, data, 9);
        } else {
            gzipdecompressor.decompress(buffer, decoded);
        }
        return decoded;
    }

    public static Archive get(int idx, boolean soft, boolean shallow) {
        ResourceCache cache = null;
        if (BufferedFile.dataFile != null) {
            cache = new ResourceCache(idx, BufferedFile.dataFile, BufferedFile.indexes[idx], 1000000);
        }
        return new Archive(cache, WorldMapTileDecor_Sub2.referenceCache, idx, soft, shallow, true);
    }

    public static int djb2(CharSequence var0) {
        int var1 = var0.length();
        int var2 = 0;

        for (int var3 = 0; var3 < var1; ++var3) {
            var2 = (var2 << 5) - var2 + BaseFont.toCp1252Byte(var0.charAt(var3));
        }

        return var2;
    }

    public int[] getFileIds(int var1) {
        return var1 >= 0 && var1 < childrenIndices.length ? childrenIndices[var1] : null;
    }

    public byte[] unpack(int group, int file, int[] xtea) {
        if (group >= 0 && group < childrenSizes.length && childrenSizes[group] != null && file >= 0 && file < childrenSizes[group].length) {
            if (childrenSizes[group][file] == null) {
                boolean init = init(group, xtea);
                if (!init) {
                    load(group);
                    init = init(group, xtea);
                    if (!init) {
                        return null;
                    }
                }
            }

            byte[] var6 = StockmarketListingPriceComparator.method333(childrenSizes[group][file], false);
            if (shallow) {
                childrenSizes[group][file] = null;
            }

            return var6;
        }
        return null;
    }

    public byte[] unpack(int group, int file) {
        return unpack(group, file, null);
    }

    public int childrenCount() {
        return childrenSizes.length;
    }

    public byte[] unpack(int file) {
        if (childrenSizes.length == 1) {
            return unpack(0, file);
        }
        if (childrenSizes[file].length == 1) {
            return unpack(file, 0);
        }
        throw new RuntimeException();
    }

    public byte[] getFile(int group, int file) {
        if (group >= 0 && group < childrenSizes.length && childrenSizes[group] != null && file >= 0 && file < childrenSizes[group].length) {
            if (childrenSizes[group][file] == null) {
                boolean var3 = init(group, null);
                if (!var3) {
                    load(group);
                    var3 = init(group, null);
                    if (!var3) {
                        return null;
                    }
                }
            }

            return StockmarketListingPriceComparator.method333(childrenSizes[group][file], false);
        }
        return null;
    }

    public boolean load(int group, int file) {
        if (group >= 0 && group < childrenSizes.length && childrenSizes[group] != null && file >= 0 && file < childrenSizes[group].length) {
            if (childrenSizes[group][file] != null) {
                return true;
            }
            if (groups[group] != null) {
                return true;
            }
            load(group);
            return groups[group] != null;
        }
        return false;
    }

    void load(int i) {

    }

    public byte[] method896(int file) {
        if (childrenSizes.length == 1) {
            return getFile(0, file);
        }
        if (childrenSizes[file].length == 1) {
            return getFile(file, 0);
        }
        throw new RuntimeException();
    }

    public boolean loadGroup(int group) {
        if (groups[group] != null) {
            return true;
        }
        load(group);
        return groups[group] != null;
    }

    public int getFileCount(int file) {
        return childrenSizes[file].length;
    }

    int getLoadingPercent(int group) {
        return groups[group] != null ? 100 : 0;
    }

    void decode(byte[] payload) {
        hash = Buffer.crc32(payload, payload.length);
        Buffer buffer = new Buffer(decodeContainer(payload));

        int var0 = buffer.g1();
        if (var0 >= 5 && var0 <= 7) {
            if (var0 >= 6) {
                buffer.g4();
            }

            int var1 = buffer.g1();
            if (var0 >= 7) {
                entryCount = buffer.g2or4();
            } else {
                entryCount = buffer.g2();
            }

            int var2 = 0;
            int entryCount = -1;
            entryIndices = new int[this.entryCount];
            int entry;
            if (var0 >= 7) {
                for (entry = 0; entry < this.entryCount; ++entry) {
                    entryIndices[entry] = var2 += buffer.g2or4();
                    if (entryIndices[entry] > entryCount) {
                        entryCount = entryIndices[entry];
                    }
                }
            } else {
                for (entry = 0; entry < this.entryCount; ++entry) {
                    entryIndices[entry] = var2 += buffer.g2();
                    if (entryIndices[entry] > entryCount) {
                        entryCount = entryIndices[entry];
                    }
                }
            }

            groupCrcs = new int[entryCount + 1];
            groupVersions = new int[entryCount + 1];
            childrenCounts = new int[entryCount + 1];
            childrenIndices = new int[entryCount + 1][];
            groups = new Object[entryCount + 1];
            childrenSizes = new Object[entryCount + 1][];
            if (var1 != 0) {
                groupNames = new int[entryCount + 1];

                for (entry = 0; entry < this.entryCount; ++entry) {
                    groupNames[entryIndices[entry]] = buffer.g4();
                }

                this.entry = new IdentityTable(groupNames);
            }

            for (entry = 0; entry < this.entryCount; ++entry) {
                groupCrcs[entryIndices[entry]] = buffer.g4();
            }

            for (entry = 0; entry < this.entryCount; ++entry) {
                groupVersions[entryIndices[entry]] = buffer.g4();
            }

            for (entry = 0; entry < this.entryCount; ++entry) {
                childrenCounts[entryIndices[entry]] = buffer.g2();
            }

            int entryIdx;
            int childIndexCount;
            int childSizeCount;
            int childIdx;
            int var12;
            if (var0 >= 7) {
                for (entry = 0; entry < this.entryCount; ++entry) {
                    entryIdx = entryIndices[entry];
                    childIndexCount = childrenCounts[entryIdx];
                    var2 = 0;
                    childSizeCount = -1;
                    childrenIndices[entryIdx] = new int[childIndexCount];

                    for (childIdx = 0; childIdx < childIndexCount; ++childIdx) {
                        var12 = childrenIndices[entryIdx][childIdx] = var2 += buffer.g2or4();
                        if (var12 > childSizeCount) {
                            childSizeCount = var12;
                        }
                    }

                    childrenSizes[entryIdx] = new Object[childSizeCount + 1];
                }
            } else {
                for (entry = 0; entry < this.entryCount; ++entry) {
                    entryIdx = entryIndices[entry];
                    childIndexCount = childrenCounts[entryIdx];
                    var2 = 0;
                    childSizeCount = -1;
                    childrenIndices[entryIdx] = new int[childIndexCount];

                    for (childIdx = 0; childIdx < childIndexCount; ++childIdx) {
                        var12 = childrenIndices[entryIdx][childIdx] = var2 += buffer.g2();
                        if (var12 > childSizeCount) {
                            childSizeCount = var12;
                        }
                    }

                    childrenSizes[entryIdx] = new Object[childSizeCount + 1];
                }
            }

            if (var1 != 0) {
                childrenNames = new int[entryCount + 1][];
                children = new IdentityTable[entryCount + 1];

                for (entry = 0; entry < this.entryCount; ++entry) {
                    entryIdx = entryIndices[entry];
                    childIndexCount = childrenCounts[entryIdx];
                    childrenNames[entryIdx] = new int[childrenSizes[entryIdx].length];

                    for (childSizeCount = 0; childSizeCount < childIndexCount; ++childSizeCount) {
                        childrenNames[entryIdx][childrenIndices[entryIdx][childSizeCount]] = buffer.g4();
                    }

                    children[entryIdx] = new IdentityTable(childrenNames[entryIdx]);
                }
            }

        } else {
            throw new RuntimeException("");
        }
    }

    public boolean load(String groupName, String fileName) {
        groupName = groupName.toLowerCase();
        fileName = fileName.toLowerCase();
        int group = entry.get(djb2(groupName));
        int file = children[group].get(djb2(fileName));
        return load(group, file);
    }

    void method490(int var1) {

    }

    public boolean validate(String groupName, String fileName) {
        groupName = groupName.toLowerCase();
        fileName = fileName.toLowerCase();
        int group = entry.get(djb2(groupName));
        if (group < 0) {
            return false;
        }
        int file = children[group].get(djb2(fileName));
        return file >= 0;
    }

    public int getGroup(String name) {
        name = name.toLowerCase();
        return entry.get(djb2(name));
    }

    public int getFile(int index, String name) {
        name = name.toLowerCase();
        return children[index].get(djb2(name));
    }

    public boolean loadDynamic(int var1) {
        if (childrenSizes.length == 1) {
            return load(0, var1);
        }
        if (childrenSizes[var1].length == 1) {
            return load(var1, 0);
        }
        throw new RuntimeException();
    }

    boolean init(int group, int[] xtea) {
        if (groups[group] == null) {
            return false;
        }

        int childrenCount = childrenCounts[group];
        int[] childrenIndex = childrenIndices[group];
        Object[] childrenSize = childrenSizes[group];
        boolean present = true;
        for (int i = 0; i < childrenCount; ++i) {
            if (childrenSize[childrenIndex[i]] == null) {
                present = false;
                break;
            }
        }

        if (present) {
            return true;
        }

        byte[] data;
        if (xtea != null && (xtea[0] != 0 || xtea[1] != 0 || xtea[2] != 0 || xtea[3] != 0)) {
            data = StockmarketListingPriceComparator.method333(groups[group], true);
            Buffer buffer = new Buffer(data);
            buffer.tinyenc(xtea, 5, buffer.payload.length);
        } else {
            data = StockmarketListingPriceComparator.method333(groups[group], false);
        }

        byte[] var20 = decodeContainer(data);
        if (soft) {
            groups[group] = null;
        }

        if (childrenCount > 1) {
            int var10 = var20.length;
            --var10;
            int var11 = var20[var10] & 255;
            var10 -= var11 * childrenCount * 4;
            Buffer var12 = new Buffer(var20);
            int[] var13 = new int[childrenCount];
            var12.pos = var10;

            int var15;
            int var16;
            for (int var14 = 0; var14 < var11; ++var14) {
                var15 = 0;

                for (var16 = 0; var16 < childrenCount; ++var16) {
                    var15 += var12.g4();
                    var13[var16] += var15;
                }
            }

            byte[][] var17 = new byte[childrenCount][];

            for (var15 = 0; var15 < childrenCount; ++var15) {
                var17[var15] = new byte[var13[var15]];
                var13[var15] = 0;
            }

            var12.pos = var10;
            var15 = 0;

            for (var16 = 0; var16 < var11; ++var16) {
                int var18 = 0;

                for (int var19 = 0; var19 < childrenCount; ++var19) {
                    var18 += var12.g4();
                    System.arraycopy(var20, var15, var17[var19], var13[var19], var18);
                    var13[var19] += var18;
                    var15 += var18;
                }
            }

            for (var16 = 0; var16 < childrenCount; ++var16) {
                if (!shallow) {
                    childrenSize[childrenIndex[var16]] = ByteBufferProvider.allocateDirect(var17[var16]);
                } else {
                    childrenSize[childrenIndex[var16]] = var17[var16];
                }
            }
        } else if (!shallow) {
            childrenSize[childrenIndex[0]] = ByteBufferProvider.allocateDirect(var20);
        } else {
            childrenSize[childrenIndex[0]] = var20;
        }

        return true;
    }

    public byte[] unpack(String groupName, String fileName) {
        groupName = groupName.toLowerCase();
        fileName = fileName.toLowerCase();
        int group = entry.get(djb2(groupName));
        int file = children[group].get(djb2(fileName));
        return unpack(group, file);
    }

    public void method903() {
        Arrays.fill(groups, null);

    }

    public void clear() {
        for (Object[] childrenSize : childrenSizes) {
            if (childrenSize != null) {
                Arrays.fill(childrenSize, null);
            }
        }

    }

    public boolean method908() {
        boolean var1 = true;

        for (int var3 : entryIndices) {
            if (groups[var3] == null) {
                load(var3);
                if (groups[var3] == null) {
                    var1 = false;
                }
            }
        }

        return var1;
    }

    public boolean method909(String var1) {
        var1 = var1.toLowerCase();
        int var2 = entry.get(djb2(var1));
        return loadGroup(var2);
    }

    public int method895(String var1) {
        var1 = var1.toLowerCase();
        int var2 = entry.get(djb2(var1));
        return getLoadingPercent(var2);
    }

    public void method914(int var1) {
        Arrays.fill(childrenSizes[var1], null);

    }

    public void method912(String var1) {
        var1 = var1.toLowerCase();
        int var2 = entry.get(djb2(var1));
        if (var2 >= 0) {
            method490(var2);
        }
    }
}
