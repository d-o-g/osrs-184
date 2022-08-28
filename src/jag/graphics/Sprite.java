package jag.graphics;

import jag.js5.ReferenceTable;

public final class Sprite extends JagGraphics {

    public static Sprite[] mapfunctions;

    public int[] pixels;

    public int width;
    public int height;
    public int anInt112;
    public int anInt377;
    public int anInt375;
    public int anInt574;

    public Sprite(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = this.anInt112 = width;
        this.height = this.anInt375 = height;
        this.anInt574 = 0;
        this.anInt377 = 0;
    }

    public Sprite(int width, int height) {
        this(new int[height * width], width, height);
    }

    public Sprite() {

    }

    static void method812(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        for (int var8 = -var5; var8 < 0; ++var8) {
            int var9 = var3 + var4 - 3;
            while (var3 < var9) {
                var0[var3++] = var1[var2++];
                var0[var3++] = var1[var2++];
                var0[var3++] = var1[var2++];
                var0[var3++] = var1[var2++];
            }

            var9 += 3;
            while (var3 < var9) {
                var0[var3++] = var1[var2++];
            }

            var3 += var6;
            var2 += var7;
        }

    }

    static void method815(int[] var0, int[] var1, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        int var10 = 256 - var9;

        for (int var11 = -var6; var11 < 0; ++var11) {
            for (int var12 = -var5; var12 < 0; ++var12) {
                int var2 = var1[var3++];
                if (var2 != 0) {
                    int var13 = var0[var4];
                    var0[var4++] = ((var13 & 16711935) * var10 + var9 * (var2 & 16711935) & -16711936) + ((var2 & 65280) * var9 + var10 * (var13 & 65280) & 16711680) >> 8;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method825(int[] var0, int[] var1, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = -(var5 >> 2);
        var5 = -(var5 & 3);

        for (int var10 = -var6; var10 < 0; ++var10) {
            for (int var11 = var9; var11 < 0; ++var11) {
                int var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }

                var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            for (int var11 = var5; var11 < 0; ++var11) {
                int var2 = var1[var3++];
                if (var2 != 0) {
                    var0[var4++] = var2;
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method820(int[] var3, int[] var4, int var5, int var7, int var9, int var10, int var11, int var12) {
        int var8;
        for (var8 = -var10; var8 < 0; ++var8) {
            int var6;
            for (var6 = -var9; var6 < 0; ++var6) {
                int var0 = var4[var5++];
                if (var0 != 0) {
                    int var1 = var3[var7];
                    int var2 = var0 + var1;
                    var0 = (var0 & 16711935) + (var1 & 16711935);
                    var1 = (var0 & 16777472) + (var2 - var0 & 65536);
                    var3[var7++] = var2 - var1 | var1 - (var1 >>> 8);
                } else {
                    ++var7;
                }
            }

            var7 += var11;
            var5 += var12;
        }

    }

    static void method816(int[] var0, int[] var1, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
        int var11 = 256 - var9;
        int var12 = (var10 & 16711935) * var11 & -16711936;
        int var13 = (var10 & 65280) * var11 & 16711680;
        var10 = (var12 | var13) >>> 8;

        for (int var14 = -var6; var14 < 0; ++var14) {
            for (int var15 = -var5; var15 < 0; ++var15) {
                int var2 = var1[var3++];
                if (var2 != 0) {
                    var12 = var9 * (var2 & 16711935) & -16711936;
                    var13 = (var2 & 65280) * var9 & 16711680;
                    var0[var4++] = var10 + ((var12 | var13) >>> 8);
                } else {
                    ++var4;
                }
            }

            var4 += var7;
            var3 += var8;
        }

    }

    static void method810(int[] var3, int[] var4, int var5, int var7, int var9, int var10, int var11, int var12, int var13) {
        int var8;
        for (var8 = -var10; var8 < 0; ++var8) {
            int var6;
            for (var6 = -var9; var6 < 0; ++var6) {
                int var0 = var4[var5++];
                if (var0 != 0) {
                    int var1 = var13 * (var0 & 16711935);
                    var0 = (var1 & -16711936) + (var13 * var0 - var1 & 16711680) >>> 8;
                    var1 = var3[var7];
                    int var2 = var0 + var1;
                    var0 = (var0 & 16711935) + (var1 & 16711935);
                    var1 = (var0 & 16777472) + (var2 - var0 & 65536);
                    var3[var7++] = var2 - var1 | var1 - (var1 >>> 8);
                } else {
                    ++var7;
                }
            }

            var7 += var11;
            var5 += var12;
        }

    }

    static void method813(int[] var0, int[] var1, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
        int var13 = 256 - var12;
        int var14 = var3;

        for (int var15 = -var8; var15 < 0; ++var15) {
            int var16 = var11 * (var4 >> 16);

            for (int var17 = -var7; var17 < 0; ++var17) {
                int var2 = var1[(var3 >> 16) + var16];
                if (var2 != 0) {
                    int var18 = var0[var5];
                    var0[var5++] = ((var2 & 65280) * var12 + var13 * (var18 & 65280) & 16711680) + ((var18 & 16711935) * var13 + var12 * (var2 & 16711935) & -16711936) >> 8;
                } else {
                    ++var5;
                }

                var3 += var9;
            }

            var4 += var10;
            var3 = var14;
            var5 += var6;
        }

    }

    static void method814(int[] var0, int[] var1, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
        int var12 = var3;

        for (int var13 = -var8; var13 < 0; ++var13) {
            int var14 = var11 * (var4 >> 16);

            for (int var15 = -var7; var15 < 0; ++var15) {
                int var2 = var1[(var3 >> 16) + var14];
                if (var2 != 0) {
                    var0[var5++] = var2;
                } else {
                    ++var5;
                }

                var3 += var9;
            }

            var4 += var10;
            var3 = var12;
            var5 += var6;
        }

    }

    static void method826(int var3, int[] var4, int[] var5, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15) {
        for (int var16 = var3; var8 < 0; ++var8) {
            int var7 = var15 * (var9 >> 16);

            int var6;
            for (var6 = -var12; var6 < 0; ++var6) {
                int var0 = var4[(var3 >> 16) + var7];
                if (var0 != 0) {
                    int var1 = var5[var10];
                    int var2 = var0 + var1;
                    var0 = (var0 & 16711935) + (var1 & 16711935);
                    var1 = (var0 & 16777472) + (var2 - var0 & 65536);
                    var5[var10++] = var2 - var1 | var1 - (var1 >>> 8);
                } else {
                    ++var10;
                }

                var3 += var13;
            }

            var9 += var14;
            var3 = var16;
            var10 += var11;
        }

    }

    static void method829(int var3, int[] var4, int[] var5, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16) {
        for (int var17 = var3; var8 < 0; ++var8) {
            int var7 = var15 * (var9 >> 16);

            int var6;
            for (var6 = -var12; var6 < 0; ++var6) {
                int var0 = var4[(var3 >> 16) + var7];
                if (var0 != 0) {
                    int var1 = (var0 & 16711935) * var16;
                    var0 = (var1 & -16711936) + (var0 * var16 - var1 & 16711680) >>> 8;
                    var1 = var5[var10];
                    int var2 = var0 + var1;
                    var0 = (var0 & 16711935) + (var1 & 16711935);
                    var1 = (var0 & 16777472) + (var2 - var0 & 65536);
                    var5[var10++] = var2 - var1 | var1 - (var1 >>> 8);
                } else {
                    ++var10;
                }

                var3 += var13;
            }

            var9 += var14;
            var3 = var17;
            var10 += var11;
        }

    }

    public static Sprite get(ReferenceTable var0, int var1, int var2) {
        return !SpriteProvider.loadSprites(var0, var1, var2) ? null : method1336();
    }

    public static Sprite[] method194() {
        Sprite[] var0 = new Sprite[SpriteProvider.count];

        for (int var1 = 0; var1 < SpriteProvider.count; ++var1) {
            Sprite var2 = var0[var1] = new Sprite();
            var2.anInt112 = SpriteProvider.width;
            var2.anInt375 = SpriteProvider.height;
            var2.anInt377 = SpriteProvider.offsetsX[var1];
            var2.anInt574 = SpriteProvider.offsetsY[var1];
            var2.width = SpriteProvider.sizesX[var1];
            var2.height = SpriteProvider.sizesY[var1];
            int var3 = var2.height * var2.width;
            byte[] var4 = SpriteProvider.pixels[var1];
            var2.pixels = new int[var3];

            for (int var5 = 0; var5 < var3; ++var5) {
                var2.pixels[var5] = SpriteProvider.palette[var4[var5] & 255];
            }
        }

        SpriteProvider.offsetsX = null;
        SpriteProvider.offsetsY = null;
        SpriteProvider.sizesX = null;
        SpriteProvider.sizesY = null;
        SpriteProvider.palette = null;
        SpriteProvider.pixels = null;
        return var0;
    }

    public static Sprite method1336() {
        Sprite var0 = new Sprite();
        var0.anInt112 = SpriteProvider.width;
        var0.anInt375 = SpriteProvider.height;
        var0.anInt377 = SpriteProvider.offsetsX[0];
        var0.anInt574 = SpriteProvider.offsetsY[0];
        var0.width = SpriteProvider.sizesX[0];
        var0.height = SpriteProvider.sizesY[0];
        int var1 = var0.height * var0.width;
        byte[] var2 = SpriteProvider.pixels[0];
        var0.pixels = new int[var1];

        for (int var3 = 0; var3 < var1; ++var3) {
            var0.pixels[var3] = SpriteProvider.palette[var2[var3] & 255];
        }

        SpriteProvider.offsetsX = null;
        SpriteProvider.offsetsY = null;
        SpriteProvider.sizesX = null;
        SpriteProvider.sizesY = null;
        SpriteProvider.palette = null;
        SpriteProvider.pixels = null;
        return var0;
    }

    public static long hash(int var0, int var1, int var2) {
        return var2 << 16 | var0 << 8 | var1;
    }

    public void renderAlphaAt(int var1, int var2) {
        var1 += this.anInt377;
        var2 += this.anInt574;
        int var3 = var1 + var2 * JagGraphics.drawingAreaWidth;
        int var4 = 0;
        int var5 = this.height;
        int var6 = this.width;
        int var7 = JagGraphics.drawingAreaWidth - var6;
        int var8 = 0;
        int var9;
        if (var2 < JagGraphics.drawingAreaTop) {
            var9 = JagGraphics.drawingAreaTop - var2;
            var5 -= var9;
            var2 = JagGraphics.drawingAreaTop;
            var4 += var9 * var6;
            var3 += var9 * JagGraphics.drawingAreaWidth;
        }

        if (var5 + var2 > JagGraphics.drawingAreaRight) {
            var5 -= var5 + var2 - JagGraphics.drawingAreaRight;
        }

        if (var1 < JagGraphics.drawingAreaLeft) {
            var9 = JagGraphics.drawingAreaLeft - var1;
            var6 -= var9;
            var1 = JagGraphics.drawingAreaLeft;
            var4 += var9;
            var3 += var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 + var1 > JagGraphics.drawingAreaBottom) {
            var9 = var6 + var1 - JagGraphics.drawingAreaBottom;
            var6 -= var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 > 0 && var5 > 0) {
            method825(JagGraphics.drawingAreaPixels, this.pixels, var4, var3, var6, var5, var7, var8);
        }
    }

    void method819(int var1, int var2, int var3, int var4, int var5, int var6) {
        if (var6 != 0) {
            var1 -= this.anInt377 << 4;
            var2 -= this.anInt574 << 4;
            double var7 = (double) (var5 & 65535) * 9.587379924285257E-5D;
            int var9 = (int) Math.floor(Math.sin(var7) * (double) var6 + 0.5D);
            int var10 = (int) Math.floor(Math.cos(var7) * (double) var6 + 0.5D);
            int var11 = var10 * -var1 + -var2 * var9;
            int var12 = -var2 * var10 + var9 * -(-var1);
            int var13 = var10 * ((this.width << 4) - var1) + -var2 * var9;
            int var14 = var9 * -((this.width << 4) - var1) + -var2 * var10;
            int var15 = ((this.height << 4) - var2) * var9 + var10 * -var1;
            int var16 = ((this.height << 4) - var2) * var10 + var9 * -(-var1);
            int var17 = ((this.height << 4) - var2) * var9 + var10 * ((this.width << 4) - var1);
            int var18 = ((this.height << 4) - var2) * var10 + var9 * -((this.width << 4) - var1);
            int var19;
            int var20;
            if (var11 < var13) {
                var19 = var11;
                var20 = var13;
            } else {
                var19 = var13;
                var20 = var11;
            }

            if (var15 < var19) {
                var19 = var15;
            }

            if (var17 < var19) {
                var19 = var17;
            }

            if (var15 > var20) {
                var20 = var15;
            }

            if (var17 > var20) {
                var20 = var17;
            }

            int var21;
            int var22;
            if (var12 < var14) {
                var21 = var12;
                var22 = var14;
            } else {
                var21 = var14;
                var22 = var12;
            }

            if (var16 < var21) {
                var21 = var16;
            }

            if (var18 < var21) {
                var21 = var18;
            }

            if (var16 > var22) {
                var22 = var16;
            }

            if (var18 > var22) {
                var22 = var18;
            }

            var19 >>= 12;
            var20 = var20 + 4095 >> 12;
            var21 >>= 12;
            var22 = var22 + 4095 >> 12;
            var19 += var3;
            var20 += var3;
            var21 += var4;
            var22 += var4;
            var19 >>= 4;
            var20 = var20 + 15 >> 4;
            var21 >>= 4;
            var22 = var22 + 15 >> 4;
            if (var19 < JagGraphics.drawingAreaLeft) {
                var19 = JagGraphics.drawingAreaLeft;
            }

            if (var20 > JagGraphics.drawingAreaBottom) {
                var20 = JagGraphics.drawingAreaBottom;
            }

            if (var21 < JagGraphics.drawingAreaTop) {
                var21 = JagGraphics.drawingAreaTop;
            }

            if (var22 > JagGraphics.drawingAreaRight) {
                var22 = JagGraphics.drawingAreaRight;
            }

            var20 = var19 - var20;
            if (var20 < 0) {
                var22 = var21 - var22;
                if (var22 < 0) {
                    int var23 = var19 + var21 * JagGraphics.drawingAreaWidth;
                    double var24 = 1.6777216E7D / (double) var6;
                    int var26 = (int) Math.floor(Math.sin(var7) * var24 + 0.5D);
                    int var27 = (int) Math.floor(Math.cos(var7) * var24 + 0.5D);
                    int var28 = (var19 << 4) + 8 - var3;
                    int var29 = (var21 << 4) + 8 - var4;
                    int var30 = (var1 << 8) - (var29 * var26 >> 4);
                    int var31 = (var29 * var27 >> 4) + (var2 << 8);
                    int var32;
                    int var33;
                    int var34;
                    int var35;
                    int var36;
                    int var37;
                    int var38;
                    if (var27 == 0) {
                        if (var26 == 0) {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = var30;
                                var35 = var31;
                                var36 = var20;
                                if (var30 >= 0 && var31 >= 0 && var30 - (this.width << 12) < 0 && var31 - (this.height << 12) < 0) {
                                    while (var36 < 0) {
                                        var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                        if (var38 != 0) {
                                            JagGraphics.drawingAreaPixels[var33++] = var38;
                                        } else {
                                            ++var33;
                                        }
                                        ++var36;
                                    }
                                }

                                ++var32;
                            }
                        } else if (var26 < 0) {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = var30;
                                var35 = (var28 * var26 >> 4) + var31;
                                var36 = var20;
                                if (var30 >= 0 && var30 - (this.width << 12) < 0) {
                                    if ((var37 = var35 - (this.height << 12)) >= 0) {
                                        var37 = (var26 - var37) / var26;
                                        var36 = var20 + var37;
                                        var35 += var26 * var37;
                                        var33 = var23 + var37;
                                    }

                                    if ((var37 = (var35 - var26) / var26) > var36) {
                                        var36 = var37;
                                    }

                                    while (var36 < 0) {
                                        var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                        if (var38 != 0) {
                                            JagGraphics.drawingAreaPixels[var33++] = var38;
                                        } else {
                                            ++var33;
                                        }

                                        var35 += var26;
                                        ++var36;
                                    }
                                }

                                ++var32;
                                var30 -= var26;
                            }
                        } else {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = var30;
                                var35 = (var28 * var26 >> 4) + var31;
                                var36 = var20;
                                if (var30 >= 0 && var30 - (this.width << 12) < 0) {
                                    if (var35 < 0) {
                                        var37 = (var26 - 1 - var35) / var26;
                                        var36 = var20 + var37;
                                        var35 += var26 * var37;
                                        var33 = var23 + var37;
                                    }

                                    if ((var37 = (var35 + 1 - (this.height << 12) - var26) / var26) > var36) {
                                        var36 = var37;
                                    }

                                    while (var36 < 0) {
                                        var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                        if (var38 != 0) {
                                            JagGraphics.drawingAreaPixels[var33++] = var38;
                                        } else {
                                            ++var33;
                                        }

                                        var35 += var26;
                                        ++var36;
                                    }
                                }

                                ++var32;
                                var30 -= var26;
                            }
                        }
                    } else if (var27 < 0) {
                        if (var26 == 0) {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = (var28 * var27 >> 4) + var30;
                                var35 = var31;
                                var36 = var20;
                                if (var31 >= 0 && var31 - (this.height << 12) < 0) {
                                    if ((var37 = var34 - (this.width << 12)) >= 0) {
                                        var37 = (var27 - var37) / var27;
                                        var36 = var20 + var37;
                                        var34 += var27 * var37;
                                        var33 = var23 + var37;
                                    }

                                    if ((var37 = (var34 - var27) / var27) > var36) {
                                        var36 = var37;
                                    }

                                    while (var36 < 0) {
                                        var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                        if (var38 != 0) {
                                            JagGraphics.drawingAreaPixels[var33++] = var38;
                                        } else {
                                            ++var33;
                                        }

                                        var34 += var27;
                                        ++var36;
                                    }
                                }

                                ++var32;
                                var31 += var27;
                            }
                        } else if (var26 < 0) {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = (var28 * var27 >> 4) + var30;
                                var35 = (var28 * var26 >> 4) + var31;
                                var36 = var20;
                                if ((var37 = var34 - (this.width << 12)) >= 0) {
                                    var37 = (var27 - var37) / var27;
                                    var36 = var20 + var37;
                                    var34 += var27 * var37;
                                    var35 += var26 * var37;
                                    var33 = var23 + var37;
                                }

                                if ((var37 = (var34 - var27) / var27) > var36) {
                                    var36 = var37;
                                }

                                if ((var37 = var35 - (this.height << 12)) >= 0) {
                                    var37 = (var26 - var37) / var26;
                                    var36 += var37;
                                    var34 += var27 * var37;
                                    var35 += var26 * var37;
                                    var33 += var37;
                                }

                                if ((var37 = (var35 - var26) / var26) > var36) {
                                    var36 = var37;
                                }

                                while (var36 < 0) {
                                    var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                    if (var38 != 0) {
                                        JagGraphics.drawingAreaPixels[var33++] = var38;
                                    } else {
                                        ++var33;
                                    }

                                    var34 += var27;
                                    var35 += var26;
                                    ++var36;
                                }

                                ++var32;
                                var30 -= var26;
                                var31 += var27;
                            }
                        } else {
                            for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                                var33 = var23;
                                var34 = (var28 * var27 >> 4) + var30;
                                var35 = (var28 * var26 >> 4) + var31;
                                var36 = var20;
                                if ((var37 = var34 - (this.width << 12)) >= 0) {
                                    var37 = (var27 - var37) / var27;
                                    var36 = var20 + var37;
                                    var34 += var27 * var37;
                                    var35 += var26 * var37;
                                    var33 = var23 + var37;
                                }

                                if ((var37 = (var34 - var27) / var27) > var36) {
                                    var36 = var37;
                                }

                                if (var35 < 0) {
                                    var37 = (var26 - 1 - var35) / var26;
                                    var36 += var37;
                                    var34 += var27 * var37;
                                    var35 += var26 * var37;
                                    var33 += var37;
                                }

                                if ((var37 = (var35 + 1 - (this.height << 12) - var26) / var26) > var36) {
                                    var36 = var37;
                                }

                                while (var36 < 0) {
                                    var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                    if (var38 != 0) {
                                        JagGraphics.drawingAreaPixels[var33++] = var38;
                                    } else {
                                        ++var33;
                                    }

                                    var34 += var27;
                                    var35 += var26;
                                    ++var36;
                                }

                                ++var32;
                                var30 -= var26;
                                var31 += var27;
                            }
                        }
                    } else if (var26 == 0) {
                        for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                            var33 = var23;
                            var34 = (var28 * var27 >> 4) + var30;
                            var35 = var31;
                            var36 = var20;
                            if (var31 >= 0 && var31 - (this.height << 12) < 0) {
                                if (var34 < 0) {
                                    var37 = (var27 - 1 - var34) / var27;
                                    var36 = var20 + var37;
                                    var34 += var27 * var37;
                                    var33 = var23 + var37;
                                }

                                if ((var37 = (var34 + 1 - (this.width << 12) - var27) / var27) > var36) {
                                    var36 = var37;
                                }

                                while (var36 < 0) {
                                    var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                    if (var38 != 0) {
                                        JagGraphics.drawingAreaPixels[var33++] = var38;
                                    } else {
                                        ++var33;
                                    }

                                    var34 += var27;
                                    ++var36;
                                }
                            }

                            ++var32;
                            var31 += var27;
                        }
                    } else if (var26 < 0) {
                        for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                            var33 = var23;
                            var34 = (var28 * var27 >> 4) + var30;
                            var35 = (var28 * var26 >> 4) + var31;
                            var36 = var20;
                            if (var34 < 0) {
                                var37 = (var27 - 1 - var34) / var27;
                                var36 = var20 + var37;
                                var34 += var27 * var37;
                                var35 += var26 * var37;
                                var33 = var23 + var37;
                            }

                            if ((var37 = (var34 + 1 - (this.width << 12) - var27) / var27) > var36) {
                                var36 = var37;
                            }

                            if ((var37 = var35 - (this.height << 12)) >= 0) {
                                var37 = (var26 - var37) / var26;
                                var36 += var37;
                                var34 += var27 * var37;
                                var35 += var26 * var37;
                                var33 += var37;
                            }

                            if ((var37 = (var35 - var26) / var26) > var36) {
                                var36 = var37;
                            }

                            while (var36 < 0) {
                                var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                if (var38 != 0) {
                                    JagGraphics.drawingAreaPixels[var33++] = var38;
                                } else {
                                    ++var33;
                                }

                                var34 += var27;
                                var35 += var26;
                                ++var36;
                            }

                            ++var32;
                            var30 -= var26;
                            var31 += var27;
                        }
                    } else {
                        for (var32 = var22; var32 < 0; var23 += JagGraphics.drawingAreaWidth) {
                            var33 = var23;
                            var34 = (var28 * var27 >> 4) + var30;
                            var35 = (var28 * var26 >> 4) + var31;
                            var36 = var20;
                            if (var34 < 0) {
                                var37 = (var27 - 1 - var34) / var27;
                                var36 = var20 + var37;
                                var34 += var27 * var37;
                                var35 += var26 * var37;
                                var33 = var23 + var37;
                            }

                            if ((var37 = (var34 + 1 - (this.width << 12) - var27) / var27) > var36) {
                                var36 = var37;
                            }

                            if (var35 < 0) {
                                var37 = (var26 - 1 - var35) / var26;
                                var36 += var37;
                                var34 += var27 * var37;
                                var35 += var26 * var37;
                                var33 += var37;
                            }

                            if ((var37 = (var35 + 1 - (this.height << 12) - var26) / var26) > var36) {
                                var36 = var37;
                            }

                            while (var36 < 0) {
                                var38 = this.pixels[(var34 >> 12) + (var35 >> 12) * this.width];
                                if (var38 != 0) {
                                    JagGraphics.drawingAreaPixels[var33++] = var38;
                                } else {
                                    ++var33;
                                }

                                var34 += var27;
                                var35 += var26;
                                ++var36;
                            }

                            ++var32;
                            var30 -= var26;
                            var31 += var27;
                        }
                    }

                }
            }
        }
    }

    public void method807(int var1, int var2, int var3, int var4) {
        if (var3 > 0 && var4 > 0) {
            int var5 = this.width;
            int var6 = this.height;
            int var7 = 0;
            int var8 = 0;
            int var9 = this.anInt112;
            int var10 = this.anInt375;
            int var11 = (var9 << 16) / var3;
            int var12 = (var10 << 16) / var4;
            int var13;
            if (this.anInt377 > 0) {
                var13 = (var11 + (this.anInt377 << 16) - 1) / var11;
                var1 += var13;
                var7 += var13 * var11 - (this.anInt377 << 16);
            }

            if (this.anInt574 > 0) {
                var13 = (var12 + (this.anInt574 << 16) - 1) / var12;
                var2 += var13;
                var8 += var13 * var12 - (this.anInt574 << 16);
            }

            if (var5 < var9) {
                var3 = (var11 + ((var5 << 16) - var7) - 1) / var11;
            }

            if (var6 < var10) {
                var4 = (var12 + ((var6 << 16) - var8) - 1) / var12;
            }

            var13 = var1 + var2 * JagGraphics.drawingAreaWidth;
            int var14 = JagGraphics.drawingAreaWidth - var3;
            if (var2 + var4 > JagGraphics.drawingAreaRight) {
                var4 -= var2 + var4 - JagGraphics.drawingAreaRight;
            }

            int var15;
            if (var2 < JagGraphics.drawingAreaTop) {
                var15 = JagGraphics.drawingAreaTop - var2;
                var4 -= var15;
                var13 += var15 * JagGraphics.drawingAreaWidth;
                var8 += var12 * var15;
            }

            if (var3 + var1 > JagGraphics.drawingAreaBottom) {
                var15 = var3 + var1 - JagGraphics.drawingAreaBottom;
                var3 -= var15;
                var14 += var15;
            }

            if (var1 < JagGraphics.drawingAreaLeft) {
                var15 = JagGraphics.drawingAreaLeft - var1;
                var3 -= var15;
                var13 += var15;
                var7 += var11 * var15;
                var14 += var15;
            }

            method814(JagGraphics.drawingAreaPixels, this.pixels, var7, var8, var13, var14, var3, var4, var11, var12, var5);
        }
    }

    public void method811(int var1, int var2, int var3, int var4) {
        if (var3 <= this.anInt112 && var4 <= this.anInt375) {
            int var5 = var3 * this.anInt377 / this.anInt112 + var1;
            int var6 = (var3 * (this.anInt377 + this.width) + this.anInt112 - 1) / this.anInt112 + var1;
            int var7 = var2 + var4 * this.anInt574 / this.anInt375;
            int var8 = var2 + (this.anInt375 + (this.anInt574 + this.height) * var4 - 1) / this.anInt375;
            if (var5 < JagGraphics.drawingAreaLeft) {
                var5 = JagGraphics.drawingAreaLeft;
            }

            if (var6 > JagGraphics.drawingAreaBottom) {
                var6 = JagGraphics.drawingAreaBottom;
            }

            if (var7 < JagGraphics.drawingAreaTop) {
                var7 = JagGraphics.drawingAreaTop;
            }

            if (var8 > JagGraphics.drawingAreaRight) {
                var8 = JagGraphics.drawingAreaRight;
            }

            if (var5 < var6 && var7 < var8) {
                int var9 = var5 + var7 * JagGraphics.drawingAreaWidth;
                int var10 = JagGraphics.drawingAreaWidth - (var6 - var5);
                if (var9 < JagGraphics.drawingAreaPixels.length) {
                    for (int var11 = var7; var11 < var8; ++var11) {
                        for (int var12 = var5; var12 < var6; ++var12) {
                            int var13 = var12 - var1 << 4;
                            int var14 = var11 - var2 << 4;
                            int var15 = var13 * this.anInt112 / var3 - (this.anInt377 << 4);
                            int var16 = (var13 + 16) * this.anInt112 / var3 - (this.anInt377 << 4);
                            int var17 = var14 * this.anInt375 / var4 - (this.anInt574 << 4);
                            int var18 = (var14 + 16) * this.anInt375 / var4 - (this.anInt574 << 4);
                            int var19 = (var16 - var15) * (var18 - var17) >> 1;
                            if (var19 != 0) {
                                if (var15 < 0) {
                                    var15 = 0;
                                }

                                if (var16 >= this.width << 4) {
                                    var16 = this.width << 4;
                                }

                                if (var17 < 0) {
                                    var17 = 0;
                                }

                                if (var18 >= this.height << 4) {
                                    var18 = this.height << 4;
                                }

                                --var16;
                                --var18;
                                int var20 = 16 - (var15 & 15);
                                int var21 = (var16 & 15) + 1;
                                int var22 = 16 - (var17 & 15);
                                int var23 = (var18 & 15) + 1;
                                var15 >>= 4;
                                var16 >>= 4;
                                var17 >>= 4;
                                var18 >>= 4;
                                int var24 = 0;
                                int var25 = 0;
                                int var26 = 0;
                                int var27 = 0;

                                int var28;
                                for (var28 = var17; var28 <= var18; ++var28) {
                                    int var29 = 16;
                                    if (var28 == var17) {
                                        var29 = var22;
                                    }

                                    if (var28 == var18) {
                                        var29 = var23;
                                    }

                                    for (int var30 = var15; var30 <= var16; ++var30) {
                                        int var31 = this.pixels[var30 + var28 * this.width];
                                        if (var31 != 0) {
                                            int var32;
                                            if (var30 == var15) {
                                                var32 = var29 * var20;
                                            } else if (var30 == var16) {
                                                var32 = var29 * var21;
                                            } else {
                                                var32 = var29 << 4;
                                            }

                                            var27 += var32;
                                            var24 += var32 * (var31 >> 16 & 255);
                                            var25 += var32 * (var31 >> 8 & 255);
                                            var26 += (var31 & 255) * var32;
                                        }
                                    }
                                }

                                if (var27 >= var19) {
                                    var28 = var26 / var27 + (var24 / var27 << 16) + (var25 / var27 << 8);
                                    if (var28 == 0) {
                                        var28 = 1;
                                    }

                                    JagGraphics.drawingAreaPixels[var9] = var28;
                                }

                                ++var9;
                            }
                        }

                        var9 += var10;
                    }

                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void renderAt(int var1, int var2) {
        var1 += this.anInt377;
        var2 += this.anInt574;
        int var3 = var1 + var2 * JagGraphics.drawingAreaWidth;
        int var4 = 0;
        int var5 = this.height;
        int var6 = this.width;
        int var7 = JagGraphics.drawingAreaWidth - var6;
        int var8 = 0;
        int var9;
        if (var2 < JagGraphics.drawingAreaTop) {
            var9 = JagGraphics.drawingAreaTop - var2;
            var5 -= var9;
            var2 = JagGraphics.drawingAreaTop;
            var4 += var9 * var6;
            var3 += var9 * JagGraphics.drawingAreaWidth;
        }

        if (var5 + var2 > JagGraphics.drawingAreaRight) {
            var5 -= var5 + var2 - JagGraphics.drawingAreaRight;
        }

        if (var1 < JagGraphics.drawingAreaLeft) {
            var9 = JagGraphics.drawingAreaLeft - var1;
            var6 -= var9;
            var1 = JagGraphics.drawingAreaLeft;
            var4 += var9;
            var3 += var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 + var1 > JagGraphics.drawingAreaBottom) {
            var9 = var6 + var1 - JagGraphics.drawingAreaBottom;
            var6 -= var9;
            var8 += var9;
            var7 += var9;
        }

        if (var6 > 0 && var5 > 0) {
            method812(JagGraphics.drawingAreaPixels, this.pixels, var4, var3, var6, var5, var7, var8);
        }
    }

    public Sprite method823() {
        Sprite var1 = new Sprite(this.anInt112, this.anInt375);

        for (int var2 = 0; var2 < this.height; ++var2) {
            for (int var3 = 0; var3 < this.width; ++var3) {
                var1.pixels[var3 + (var2 + this.anInt574) * this.anInt112 + this.anInt377] = this.pixels[var3 + var2 * this.width];
            }
        }

        return var1;
    }

    public void method828() {
        int[] var1 = new int[this.width * this.height];
        int var2 = 0;

        for (int var3 = this.height - 1; var3 >= 0; --var3) {
            for (int var4 = 0; var4 < this.width; ++var4) {
                var1[var2++] = this.pixels[var4 + var3 * this.width];
            }
        }

        this.pixels = var1;
        this.anInt574 = this.anInt375 - this.height - this.anInt574;
    }

    public void method827() {
        int[] var1 = new int[this.width * this.height];
        int var2 = 0;

        for (int var3 = 0; var3 < this.height; ++var3) {
            for (int var4 = this.width - 1; var4 >= 0; --var4) {
                var1[var2++] = this.pixels[var4 + var3 * this.width];
            }
        }

        this.pixels = var1;
        this.anInt377 = this.anInt112 - this.width - this.anInt377;
    }

    public void method23() {
        JagGraphics.setTarget(this.pixels, this.width, this.height);
    }

    public void method830(int var1) {
        if (this.width != this.anInt112 || this.height != this.anInt375) {
            int var2 = var1;
            if (var1 > this.anInt377) {
                var2 = this.anInt377;
            }

            int var3 = var1;
            if (var1 + this.anInt377 + this.width > this.anInt112) {
                var3 = this.anInt112 - this.anInt377 - this.width;
            }

            int var4 = var1;
            if (var1 > this.anInt574) {
                var4 = this.anInt574;
            }

            int var5 = var1;
            if (var1 + this.anInt574 + this.height > this.anInt375) {
                var5 = this.anInt375 - this.anInt574 - this.height;
            }

            int var6 = var2 + var3 + this.width;
            int var7 = var4 + var5 + this.height;
            int[] var8 = new int[var6 * var7];

            for (int var9 = 0; var9 < this.height; ++var9) {
                for (int var10 = 0; var10 < this.width; ++var10) {
                    var8[var6 * (var9 + var4) + var10 + var2] = this.pixels[var10 + var9 * this.width];
                }
            }

            this.pixels = var8;
            this.width = var6;
            this.height = var7;
            this.anInt377 -= var2;
            this.anInt574 -= var4;
        }
    }

    public void method822(int var1) {
        int[] var2 = new int[this.width * this.height];
        int var3 = 0;

        for (int var4 = 0; var4 < this.height; ++var4) {
            for (int var5 = 0; var5 < this.width; ++var5) {
                int var6 = this.pixels[var3];
                if (var6 == 0) {
                    if (var5 > 0 && this.pixels[var3 - 1] != 0) {
                        var6 = var1;
                    } else if (var4 > 0 && this.pixels[var3 - this.width] != 0) {
                        var6 = var1;
                    } else if (var5 < this.width - 1 && this.pixels[var3 + 1] != 0) {
                        var6 = var1;
                    } else if (var4 < this.height - 1 && this.pixels[var3 + this.width] != 0) {
                        var6 = var1;
                    }
                }

                var2[var3++] = var6;
            }
        }

        this.pixels = var2;
    }

    public void method821(int var1, int var2, int var3) {
        var1 += this.anInt377;
        var2 += this.anInt574;
        int var4 = var1 + var2 * JagGraphics.drawingAreaWidth;
        int var5 = 0;
        int var6 = this.height;
        int var7 = this.width;
        int var8 = JagGraphics.drawingAreaWidth - var7;
        int var9 = 0;
        int var10;
        if (var2 < JagGraphics.drawingAreaTop) {
            var10 = JagGraphics.drawingAreaTop - var2;
            var6 -= var10;
            var2 = JagGraphics.drawingAreaTop;
            var5 += var10 * var7;
            var4 += var10 * JagGraphics.drawingAreaWidth;
        }

        if (var6 + var2 > JagGraphics.drawingAreaRight) {
            var6 -= var6 + var2 - JagGraphics.drawingAreaRight;
        }

        if (var1 < JagGraphics.drawingAreaLeft) {
            var10 = JagGraphics.drawingAreaLeft - var1;
            var7 -= var10;
            var1 = JagGraphics.drawingAreaLeft;
            var5 += var10;
            var4 += var10;
            var9 += var10;
            var8 += var10;
        }

        if (var7 + var1 > JagGraphics.drawingAreaBottom) {
            var10 = var7 + var1 - JagGraphics.drawingAreaBottom;
            var7 -= var10;
            var9 += var10;
            var8 += var10;
        }

        if (var7 > 0 && var6 > 0) {
            if (var3 == 256) {
                method820(JagGraphics.drawingAreaPixels, this.pixels, var5, var4, var7, var6, var8, var9);
            } else {
                method810(JagGraphics.drawingAreaPixels, this.pixels, var5, var4, var7, var6, var8, var9, var3);
            }

        }
    }

    public void method806(int var1, int var2, int var3, int var4, int var5) {
        if (var3 > 0 && var4 > 0) {
            int var6 = this.width;
            int var7 = this.height;
            int var8 = 0;
            int var9 = 0;
            int var10 = this.anInt112;
            int var11 = this.anInt375;
            int var12 = (var10 << 16) / var3;
            int var13 = (var11 << 16) / var4;
            int var14;
            if (this.anInt377 > 0) {
                var14 = (var12 + (this.anInt377 << 16) - 1) / var12;
                var1 += var14;
                var8 += var14 * var12 - (this.anInt377 << 16);
            }

            if (this.anInt574 > 0) {
                var14 = (var13 + (this.anInt574 << 16) - 1) / var13;
                var2 += var14;
                var9 += var14 * var13 - (this.anInt574 << 16);
            }

            if (var6 < var10) {
                var3 = (var12 + ((var6 << 16) - var8) - 1) / var12;
            }

            if (var7 < var11) {
                var4 = (var13 + ((var7 << 16) - var9) - 1) / var13;
            }

            var14 = var1 + var2 * JagGraphics.drawingAreaWidth;
            int var15 = JagGraphics.drawingAreaWidth - var3;
            if (var2 + var4 > JagGraphics.drawingAreaRight) {
                var4 -= var2 + var4 - JagGraphics.drawingAreaRight;
            }

            int var16;
            if (var2 < JagGraphics.drawingAreaTop) {
                var16 = JagGraphics.drawingAreaTop - var2;
                var4 -= var16;
                var14 += var16 * JagGraphics.drawingAreaWidth;
                var9 += var13 * var16;
            }

            if (var3 + var1 > JagGraphics.drawingAreaBottom) {
                var16 = var3 + var1 - JagGraphics.drawingAreaBottom;
                var3 -= var16;
                var15 += var16;
            }

            if (var1 < JagGraphics.drawingAreaLeft) {
                var16 = JagGraphics.drawingAreaLeft - var1;
                var3 -= var16;
                var14 += var16;
                var8 += var12 * var16;
                var15 += var16;
            }

            if (var5 == 256) {
                method826(var8, this.pixels, JagGraphics.drawingAreaPixels, -var4, var9, var14, var15, var3, var12, var13, var6);
            } else {
                method829(var8, this.pixels, JagGraphics.drawingAreaPixels, -var4, var9, var14, var15, var3, var12, var13, var6, var5);
            }

        }
    }

    public void method835(int var1) {
        for (int var2 = this.height - 1; var2 > 0; --var2) {
            int var3 = var2 * this.width;

            for (int var4 = this.width - 1; var4 > 0; --var4) {
                if (this.pixels[var4 + var3] == 0 && this.pixels[var4 + var3 - 1 - this.width] != 0) {
                    this.pixels[var4 + var3] = var1;
                }
            }
        }

    }

    public void rotate(int centerX, int centerY, int width, int height, int angle, int var6, int var7, int var8, int[] var9, int[] var10) {
        try {
            int var11 = -width / 2;
            int var12 = -height / 2;
            int var13 = (int) (Math.sin((double) var7 / 326.11D) * 65536.0D);
            int var14 = (int) (Math.cos((double) var7 / 326.11D) * 65536.0D);
            var13 = var13 * var8 >> 8;
            var14 = var14 * var8 >> 8;
            int var15 = var12 * var13 + var11 * var14 + (angle << 16);
            int var16 = var12 * var14 - var11 * var13 + (var6 << 16);
            int var17 = centerX + centerY * JagGraphics.drawingAreaWidth;

            for (centerY = 0; centerY < height; ++centerY) {
                int var18 = var9[centerY];
                int var19 = var17 + var18;
                int var20 = var15 + var14 * var18;
                int var21 = var16 - var13 * var18;

                for (centerX = -var10[centerY]; centerX < 0; ++centerX) {
                    JagGraphics.drawingAreaPixels[var19++] = this.pixels[this.width * (var21 >> 16) + (var20 >> 16)];
                    var20 += var14;
                    var21 -= var13;
                }

                var15 += var13;
                var16 += var14;
                var17 += JagGraphics.drawingAreaWidth;
            }
        } catch (Exception ignored) {
        }

    }

    public void method818(int var1, int var2, int var3, int var4, int var5) {
        if (var3 > 0 && var4 > 0) {
            int var6 = this.width;
            int var7 = this.height;
            int var8 = 0;
            int var9 = 0;
            int var10 = this.anInt112;
            int var11 = this.anInt375;
            int var12 = (var10 << 16) / var3;
            int var13 = (var11 << 16) / var4;
            int var14;
            if (this.anInt377 > 0) {
                var14 = (var12 + (this.anInt377 << 16) - 1) / var12;
                var1 += var14;
                var8 += var14 * var12 - (this.anInt377 << 16);
            }

            if (this.anInt574 > 0) {
                var14 = (var13 + (this.anInt574 << 16) - 1) / var13;
                var2 += var14;
                var9 += var14 * var13 - (this.anInt574 << 16);
            }

            if (var6 < var10) {
                var3 = (var12 + ((var6 << 16) - var8) - 1) / var12;
            }

            if (var7 < var11) {
                var4 = (var13 + ((var7 << 16) - var9) - 1) / var13;
            }

            var14 = var1 + var2 * JagGraphics.drawingAreaWidth;
            int var15 = JagGraphics.drawingAreaWidth - var3;
            if (var2 + var4 > JagGraphics.drawingAreaRight) {
                var4 -= var2 + var4 - JagGraphics.drawingAreaRight;
            }

            int var16;
            if (var2 < JagGraphics.drawingAreaTop) {
                var16 = JagGraphics.drawingAreaTop - var2;
                var4 -= var16;
                var14 += var16 * JagGraphics.drawingAreaWidth;
                var9 += var13 * var16;
            }

            if (var3 + var1 > JagGraphics.drawingAreaBottom) {
                var16 = var3 + var1 - JagGraphics.drawingAreaBottom;
                var3 -= var16;
                var15 += var16;
            }

            if (var1 < JagGraphics.drawingAreaLeft) {
                var16 = JagGraphics.drawingAreaLeft - var1;
                var3 -= var16;
                var14 += var16;
                var8 += var12 * var16;
                var15 += var16;
            }

            method813(JagGraphics.drawingAreaPixels, this.pixels, var8, var9, var14, var15, var3, var4, var12, var13, var6, var5);
        }
    }

    public void method824(int var1, int var2, int var3, int var4) {
        this.method819(this.anInt112 << 3, this.anInt375 << 3, var1 << 4, var2 << 4, var3, var4);
    }

    public void method832(int var1, int var2, int var3) {
        var1 += this.anInt377;
        var2 += this.anInt574;
        int var4 = var1 + var2 * JagGraphics.drawingAreaWidth;
        int var5 = 0;
        int var6 = this.height;
        int var7 = this.width;
        int var8 = JagGraphics.drawingAreaWidth - var7;
        int var9 = 0;
        int var10;
        if (var2 < JagGraphics.drawingAreaTop) {
            var10 = JagGraphics.drawingAreaTop - var2;
            var6 -= var10;
            var2 = JagGraphics.drawingAreaTop;
            var5 += var10 * var7;
            var4 += var10 * JagGraphics.drawingAreaWidth;
        }

        if (var6 + var2 > JagGraphics.drawingAreaRight) {
            var6 -= var6 + var2 - JagGraphics.drawingAreaRight;
        }

        if (var1 < JagGraphics.drawingAreaLeft) {
            var10 = JagGraphics.drawingAreaLeft - var1;
            var7 -= var10;
            var1 = JagGraphics.drawingAreaLeft;
            var5 += var10;
            var4 += var10;
            var9 += var10;
            var8 += var10;
        }

        if (var7 + var1 > JagGraphics.drawingAreaBottom) {
            var10 = var7 + var1 - JagGraphics.drawingAreaBottom;
            var7 -= var10;
            var9 += var10;
            var8 += var10;
        }

        if (var7 > 0 && var6 > 0) {
            method815(JagGraphics.drawingAreaPixels, this.pixels, var5, var4, var7, var6, var8, var9, var3);
        }
    }

    public void method775() {
        if (this.width != this.anInt112 || this.height != this.anInt375) {
            int[] var1 = new int[this.anInt112 * this.anInt375];

            for (int var2 = 0; var2 < this.height; ++var2) {
                for (int var3 = 0; var3 < this.width; ++var3) {
                    var1[var3 + (var2 + this.anInt574) * this.anInt112 + this.anInt377] = this.pixels[var3 + var2 * this.width];
                }
            }

            this.pixels = var1;
            this.width = this.anInt112;
            this.height = this.anInt375;
            this.anInt377 = 0;
            this.anInt574 = 0;
        }
    }

    public void method808(int var1, int var2, int var3, int var4, int var5, int var6, int[] var7, int[] var8) {
        int var9 = var2 < 0 ? -var2 : 0;
        int var10 = var2 + this.height <= var6 ? this.height : var6 - var2;
        int var11 = var1 < 0 ? -var1 : 0;
        int var10000;
        if (this.width + var1 <= var5) {
        } else {
        }

        int var13 = var3 + var11 + (var9 + var2 + var4) * JagGraphics.drawingAreaWidth + var1;
        int var14 = var9 + var2;

        for (int var15 = var9; var15 < var10; ++var15) {
            int var16 = var7[var14];
            int var17 = var8[var14++];
            int var18 = var13;
            int var19;
            if (var1 < var16) {
                var19 = var16 - var1;
                var18 = var13 + (var19 - var11);
            } else {
                var19 = var11;
            }

            int var12;
            if (this.width + var1 <= var16 + var17) {
                var12 = this.width;
            } else {
                var12 = var16 + var17 - var1;
            }

            for (int var20 = var19; var20 < var12; ++var20) {
                int var21 = this.pixels[var20 + var15 * this.width];
                if (var21 != 0) {
                    JagGraphics.drawingAreaPixels[var18++] = var21;
                } else {
                    ++var18;
                }
            }

            var13 += JagGraphics.drawingAreaWidth;
        }

    }

    public void method831(int var1, int var2, int var3, int var4, int var5, int var6, double var7, int var9) {
        try {
            int var10 = -var3 / 2;
            int var11 = -var4 / 2;
            int var12 = (int) (Math.sin(var7) * 65536.0D);
            int var13 = (int) (Math.cos(var7) * 65536.0D);
            var12 = var12 * var9 >> 8;
            var13 = var13 * var9 >> 8;
            int var14 = var11 * var12 + var10 * var13 + (var5 << 16);
            int var15 = var11 * var13 - var10 * var12 + (var6 << 16);
            int var16 = var1 + var2 * JagGraphics.drawingAreaWidth;

            for (var2 = 0; var2 < var4; ++var2) {
                int var17 = var16;
                int var18 = var14;
                int var19 = var15;

                for (var1 = -var3; var1 < 0; ++var1) {
                    int var20 = this.pixels[this.width * (var19 >> 16) + (var18 >> 16)];
                    if (var20 != 0) {
                        JagGraphics.drawingAreaPixels[var17++] = var20;
                    } else {
                        ++var17;
                    }

                    var18 += var13;
                    var19 -= var12;
                }

                var14 += var12;
                var15 += var13;
                var16 += JagGraphics.drawingAreaWidth;
            }
        } catch (Exception ignored) {
        }

    }

    public Sprite method836() {
        Sprite var1 = new Sprite(this.width, this.height);
        var1.anInt112 = this.anInt112;
        var1.anInt375 = this.anInt375;
        var1.anInt377 = this.anInt112 - this.width - this.anInt377;
        var1.anInt574 = this.anInt574;

        for (int var2 = 0; var2 < this.height; ++var2) {
            for (int var3 = 0; var3 < this.width; ++var3) {
                var1.pixels[var3 + var2 * this.width] = this.pixels[var2 * this.width + this.width - 1 - var3];
            }
        }

        return var1;
    }

    public void method834(int var1, int var2, int var3, int var4) {
        if (var3 == 256) {
            this.renderAlphaAt(var1, var2);
        } else {
            var1 += this.anInt377;
            var2 += this.anInt574;
            int var5 = var1 + var2 * JagGraphics.drawingAreaWidth;
            int var6 = 0;
            int var7 = this.height;
            int var8 = this.width;
            int var9 = JagGraphics.drawingAreaWidth - var8;
            int var10 = 0;
            int var11;
            if (var2 < JagGraphics.drawingAreaTop) {
                var11 = JagGraphics.drawingAreaTop - var2;
                var7 -= var11;
                var2 = JagGraphics.drawingAreaTop;
                var6 += var11 * var8;
                var5 += var11 * JagGraphics.drawingAreaWidth;
            }

            if (var7 + var2 > JagGraphics.drawingAreaRight) {
                var7 -= var7 + var2 - JagGraphics.drawingAreaRight;
            }

            if (var1 < JagGraphics.drawingAreaLeft) {
                var11 = JagGraphics.drawingAreaLeft - var1;
                var8 -= var11;
                var1 = JagGraphics.drawingAreaLeft;
                var6 += var11;
                var5 += var11;
                var10 += var11;
                var9 += var11;
            }

            if (var8 + var1 > JagGraphics.drawingAreaBottom) {
                var11 = var8 + var1 - JagGraphics.drawingAreaBottom;
                var8 -= var11;
                var10 += var11;
                var9 += var11;
            }

            if (var8 > 0 && var7 > 0) {
                method816(JagGraphics.drawingAreaPixels, this.pixels, var6, var5, var8, var7, var9, var10, var3, var4);
            }
        }
    }
}
