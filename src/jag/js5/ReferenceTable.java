package jag.js5;

import jag.ByteBufferProvider;
import jag.game.stockmarket.StockMarketOfferPriceComparator;
import jag.graphics.BaseFont;
import jag.opcode.Buffer;
import jag.statics.Statics45;
import jag.worldmap.WorldMapTileDecor_Sub2;

import java.util.Arrays;

public abstract class ReferenceTable {

    static final GzipDecompressor gzipdecompressor = new GzipDecompressor();
    static final int maximumContainerSize = 0;

    public int hash;
    int[] entryIndices;
    Object[] groups;
    Object[][] childrenSizes;
    int[][] childrenIndices;
    boolean soft;
    IdentityTable[] children;
    IdentityTable entry;
    int[] groupVersions;
    int[] childrenCounts;
    boolean shallow;
    int[] groupCrcs;
    int entryCount;
    int[] groupNames;
    int[][] childrenNames;

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

    public static Archive get(int var0, boolean var1, boolean var2) {
        ResourceCache cache = null;
        if (BufferedFile.dataFile != null) {
            cache = new ResourceCache(var0, BufferedFile.dataFile, BufferedFile.indexes[var0], 1000000);
        }

        return new Archive(cache, WorldMapTileDecor_Sub2.aResourceCache649, var0, var1, var2, true);
    }

    public static boolean method534(ReferenceTable table, int var1, int var2) {
        byte[] data = table.unpack(var1, var2);
        if (data == null) {
            return false;
        }
        Statics45.method267(data);
        return true;
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

            byte[] var6 = StockMarketOfferPriceComparator.method333(childrenSizes[group][file], false);
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

            return StockMarketOfferPriceComparator.method333(childrenSizes[group][file], false);
        }
        return null;
    }

    public boolean load(int var1, int var2) {
        if (var1 >= 0 && var1 < childrenSizes.length && childrenSizes[var1] != null && var2 >= 0 && var2 < childrenSizes[var1].length) {
            if (childrenSizes[var1][var2] != null) {
                return true;
            }
            if (groups[var1] != null) {
                return true;
            }
            load(var1);
            return groups[var1] != null;
        }
        return false;
    }

    void load(int var1) {

    }

    public byte[] method896(int var1) {
        if (childrenSizes.length == 1) {
            return getFile(0, var1);
        }
        if (childrenSizes[var1].length == 1) {
            return getFile(var1, 0);
        }
        throw new RuntimeException();
    }

    public boolean method911(int var1) {
        if (groups[var1] != null) {
            return true;
        }
        load(var1);
        return groups[var1] != null;
    }

    public int getFileCount(int var1) {
        return childrenSizes[var1].length;
    }

    int getLoadingPercent(int var1) {
        return groups[var1] != null ? 100 : 0;
    }

    void decode(byte[] var1) {
        hash = Buffer.crc32(var1, var1.length);
        Buffer buffer = new Buffer(decodeContainer(var1));
        int var3 = buffer.g1();
        if (var3 >= 5 && var3 <= 7) {
            if (var3 >= 6) {
                buffer.g4();
            }

            int var4 = buffer.g1();
            if (var3 >= 7) {
                entryCount = buffer.g2else4();
            } else {
                entryCount = buffer.g2();
            }

            int var5 = 0;
            int var6 = -1;
            entryIndices = new int[entryCount];
            int var7;
            if (var3 >= 7) {
                for (var7 = 0; var7 < entryCount; ++var7) {
                    entryIndices[var7] = var5 += buffer.g2else4();
                    if (entryIndices[var7] > var6) {
                        var6 = entryIndices[var7];
                    }
                }
            } else {
                for (var7 = 0; var7 < entryCount; ++var7) {
                    entryIndices[var7] = var5 += buffer.g2();
                    if (entryIndices[var7] > var6) {
                        var6 = entryIndices[var7];
                    }
                }
            }

            groupCrcs = new int[var6 + 1];
            groupVersions = new int[var6 + 1];
            childrenCounts = new int[var6 + 1];
            childrenIndices = new int[var6 + 1][];
            groups = new Object[var6 + 1];
            childrenSizes = new Object[var6 + 1][];
            if (var4 != 0) {
                groupNames = new int[var6 + 1];

                for (var7 = 0; var7 < entryCount; ++var7) {
                    groupNames[entryIndices[var7]] = buffer.g4();
                }

                entry = new IdentityTable(groupNames);
            }

            for (var7 = 0; var7 < entryCount; ++var7) {
                groupCrcs[entryIndices[var7]] = buffer.g4();
            }

            for (var7 = 0; var7 < entryCount; ++var7) {
                groupVersions[entryIndices[var7]] = buffer.g4();
            }

            for (var7 = 0; var7 < entryCount; ++var7) {
                childrenCounts[entryIndices[var7]] = buffer.g2();
            }

            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            if (var3 >= 7) {
                for (var7 = 0; var7 < entryCount; ++var7) {
                    var8 = entryIndices[var7];
                    var9 = childrenCounts[var8];
                    var5 = 0;
                    var10 = -1;
                    childrenIndices[var8] = new int[var9];

                    for (var11 = 0; var11 < var9; ++var11) {
                        var12 = childrenIndices[var8][var11] = var5 += buffer.g2else4();
                        if (var12 > var10) {
                            var10 = var12;
                        }
                    }

                    childrenSizes[var8] = new Object[var10 + 1];
                }
            } else {
                for (var7 = 0; var7 < entryCount; ++var7) {
                    var8 = entryIndices[var7];
                    var9 = childrenCounts[var8];
                    var5 = 0;
                    var10 = -1;
                    childrenIndices[var8] = new int[var9];

                    for (var11 = 0; var11 < var9; ++var11) {
                        var12 = childrenIndices[var8][var11] = var5 += buffer.g2();
                        if (var12 > var10) {
                            var10 = var12;
                        }
                    }

                    childrenSizes[var8] = new Object[var10 + 1];
                }
            }

            if (var4 != 0) {
                childrenNames = new int[var6 + 1][];
                children = new IdentityTable[var6 + 1];

                for (var7 = 0; var7 < entryCount; ++var7) {
                    var8 = entryIndices[var7];
                    var9 = childrenCounts[var8];
                    childrenNames[var8] = new int[childrenSizes[var8].length];

                    for (var10 = 0; var10 < var9; ++var10) {
                        childrenNames[var8][childrenIndices[var8][var10]] = buffer.g4();
                    }

                    children[var8] = new IdentityTable(childrenNames[var8]);
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
            data = StockMarketOfferPriceComparator.method333(groups[group], true);
            Buffer buffer = new Buffer(data);
            buffer.tinyenc(xtea, 5, buffer.payload.length);
        } else {
            data = StockMarketOfferPriceComparator.method333(groups[group], false);
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
        return method911(var2);
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
