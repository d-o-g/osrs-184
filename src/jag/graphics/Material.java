package jag.graphics;

import jag.audi.PcmStream_Sub4;
import jag.commons.Jagexception;
import jag.commons.collection.Node;
import jag.game.type.AnimationFrameGroup;
import jag.js5.ReferenceTable;
import jag.opcode.Buffer;
import jag.opcode.OutgoingPacket;
import jag.statics.Statics41;
import jag.statics.Statics45;

public class Material extends Node {

    static int[] anIntArray689;

    public final boolean aBoolean571;
    public final int rgb;
    public int[] files;
    public int[] pixels;
    public boolean loaded;
    public int direction;
    int velocity;
    int[] anIntArray690;
    int[] anIntArray692;
    int[] anIntArray691;

    public Material(Buffer buffer) {
        loaded = false;
        rgb = buffer.g2();
        aBoolean571 = buffer.g1() == 1;

        int count = buffer.g1();
        if (count >= 1 && count <= 4) {
            files = new int[count];

            for (int i = 0; i < count; ++i) {
                files[i] = buffer.g2();
            }

            if (count > 1) {
                anIntArray692 = new int[count - 1];

                for (int i = 0; i < count - 1; ++i) {
                    anIntArray692[i] = buffer.g1();
                }
            }

            if (count > 1) {
                anIntArray691 = new int[count - 1];
                for (int i = 0; i < count - 1; ++i) {
                    anIntArray691[i] = buffer.g1();
                }
            }

            anIntArray690 = new int[count];
            for (int i = 0; i < count; ++i) {
                anIntArray690[i] = buffer.g4();
            }

            direction = buffer.g1();
            velocity = buffer.g1();
            pixels = null;
        } else {
            throw new RuntimeException();
        }
    }

    public void reset() {
        pixels = null;
    }

    public boolean index(double var1, int var3, ReferenceTable table) {
        for (int file : files) {
            if (table.method896(file) == null) {
                return false;
            }
        }

        int var5 = var3 * var3;
        pixels = new int[var5];

        for (int i = 0; i < files.length; ++i) {
            int var7 = files[i];
            byte[] var8 = table.unpack(var7);
            boolean index;
            if (var8 == null) {
                index = false;
            } else {
                Statics45.method267(var8);
                index = true;
            }

            IndexedSprite sprite;
            if (!index) {
                sprite = null;
            } else {
                IndexedSprite tmp = new IndexedSprite();
                tmp.anInt375 = Statics41.anInt1824;
                tmp.anInt372 = AnimationFrameGroup.anInt378;
                tmp.insetX = Statics41.anIntArray1821[0];
                tmp.insetY = PcmStream_Sub4.anIntArray1107[0];
                tmp.anInt378 = Statics41.anIntArray1820[0];
                tmp.anInt377 = Jagexception.anIntArray1878[0];
                tmp.palette = Statics41.anIntArray1823;
                tmp.indices = OutgoingPacket.aByteArrayArray114[0];
                Statics41.anIntArray1821 = null;
                PcmStream_Sub4.anIntArray1107 = null;
                Statics41.anIntArray1820 = null;
                Jagexception.anIntArray1878 = null;
                Statics41.anIntArray1823 = null;
                OutgoingPacket.aByteArrayArray114 = null;
                sprite = tmp;
            }

            sprite.method499();
            var8 = sprite.indices;
            int var13 = anIntArray690[i];
            int[] var18 = sprite.palette;
            if ((var13 & -16777216) == 50331648) {
                int var14 = var13 & 16711935;
                int var15 = var13 >> 8 & 255;

                for (int j = 0; j < var18.length; ++j) {
                    int var17 = var18[j];
                    if (var17 >> 8 == (var17 & 65535)) {
                        var17 &= 255;
                        var18[j] = var14 * var17 >> 8 & 16711935 | var15 * var17 & 65280;
                    }
                }
            }

            for (int j = 0; j < var18.length; ++j) {
                var18[j] = JagGraphics3D.method631(var18[j], var1);
            }

            int var14;
            if (i == 0) {
                var14 = 0;
            } else {
                var14 = anIntArray692[i - 1];
            }

            if (var14 == 0) {
                if (var3 == sprite.anInt378) {
                    for (int j = 0; j < var5; ++j) {
                        pixels[j] = var18[var8[j] & 255];
                    }
                } else if (sprite.anInt378 == 64 && var3 == 128) {
                    int var15 = 0;
                    for (int j = 0; j < var3; ++j) {
                        for (int k = 0; k < var3; ++k) {
                            pixels[var15++] = var18[var8[(j >> 1 << 6) + (k >> 1)] & 255];
                        }
                    }
                } else {
                    if (sprite.anInt378 != 128 || var3 != 64) {
                        throw new RuntimeException();
                    }

                    int var15 = 0;
                    for (int j = 0; j < var3; ++j) {
                        for (int k = 0; k < var3; ++k) {
                            pixels[var15++] = var18[var8[(k << 1) + (j << 1 << 7)] & 255];
                        }
                    }
                }
            }
        }
        return true;
    }

    public void rotate(int var1) {
        if (pixels != null) {
            if (direction == 1 || direction == 3) {
                if (anIntArray689 == null || anIntArray689.length < pixels.length) {
                    anIntArray689 = new int[pixels.length];
                }

                short var2;
                if (pixels.length == 4096) {
                    var2 = 64;
                } else {
                    var2 = 128;
                }

                int var3 = pixels.length;
                int var4 = var2 * velocity * var1;
                int var5 = var3 - 1;
                if (direction == 1) {
                    var4 = -var4;
                }

                for (int i = 0; i < var3; ++i) {
                    int var7 = i + var4 & var5;
                    anIntArray689[i] = pixels[var7];
                }

                int[] var8 = pixels;
                pixels = anIntArray689;
                anIntArray689 = var8;
            }

            if (direction == 2 || direction == 4) {
                if (anIntArray689 == null || anIntArray689.length < pixels.length) {
                    anIntArray689 = new int[pixels.length];
                }

                short var2;
                if (pixels.length == 4096) {
                    var2 = 64;
                } else {
                    var2 = 128;
                }

                int var3 = pixels.length;
                int var4 = velocity * var1;
                int var5 = var2 - 1;
                if (direction == 2) {
                    var4 = -var4;
                }

                for (int i = 0; i < var3; i += var2) {
                    for (int j = 0; j < var2; ++j) {
                        int var9 = i + j;
                        int var10 = i + (j + var4 & var5);
                        anIntArray689[var9] = pixels[var10];
                    }
                }

                int[] var8 = pixels;
                pixels = anIntArray689;
                anIntArray689 = var8;
            }
        }
    }
}
