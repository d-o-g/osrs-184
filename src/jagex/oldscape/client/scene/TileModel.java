package jagex.oldscape.client.scene;

public final class TileModel {

  public static final int[][] anIntArrayArray1545 = new int[][]{{1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 6}, {1, 3, 5, 7, 2, 6}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 2, 8}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 11, 12}, {1, 3, 5, 7, 13, 14}};
  public static final int[][] anIntArrayArray1536 = new int[][]{{0, 1, 2, 3, 0, 0, 1, 3}, {1, 1, 2, 3, 1, 0, 1, 3}, {0, 1, 2, 3, 1, 0, 1, 3}, {0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3}, {0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4}, {0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4}, {0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3}, {0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3}, {0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5}, {0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5}, {0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3}, {1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3}, {1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5}};

  public static final int[] anIntArray1549 = new int[6];
  public static final int[] anIntArray1546 = new int[6];
  public static final int[] anIntArray1537 = new int[6];
  public static final int[] anIntArray1550 = new int[6];
  public static final int[] anIntArray1538 = new int[6];

  public int[] xVertices;
  public int[] yVertices;
  public int[] zVertices;
  public int[] triangleTexture;
  public int[] anIntArray1543;
  public int[] anIntArray1551;
  public int[] anIntArray1540;
  public int[] anIntArray1547;
  public int[] anIntArray1544;
  public int[] anIntArray1542;

  public int shape;
  public int rotation;
  public int underlay;
  public int overlay;

  public boolean flatShade;

  public TileModel(int shape, int rotation, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int underlay, int overlay) {
    flatShade = var7 == var6 && var8 == var6 && var9 == var6;

    this.shape = shape;
    this.rotation = rotation;
    this.underlay = underlay;
    this.overlay = overlay;

    short var20 = 128;
    int var21 = var20 / 2;
    int var22 = var20 / 4;
    int var23 = var20 * 3 / 4;
    int[] var24 = anIntArrayArray1545[shape];
    int var25 = var24.length;
    xVertices = new int[var25];
    yVertices = new int[var25];
    zVertices = new int[var25];
    int[] var26 = new int[var25];
    int[] var27 = new int[var25];
    int var28 = var20 * var4;
    int var29 = var5 * var20;

    int var31;
    int var32;
    int var33;
    int var34;
    int var35;
    int var36;
    for (int var30 = 0; var30 < var25; ++var30) {
      var31 = var24[var30];
      if ((var31 & 1) == 0 && var31 <= 8) {
        var31 = (var31 - rotation - rotation - 1 & 7) + 1;
      }

      if (var31 > 8 && var31 <= 12) {
        var31 = (var31 - 9 - rotation & 3) + 9;
      }

      if (var31 > 12 && var31 <= 16) {
        var31 = (var31 - 13 - rotation & 3) + 13;
      }

      if (var31 == 1) {
        var32 = var28;
        var33 = var29;
        var34 = var6;
        var35 = var10;
        var36 = var14;
      } else if (var31 == 2) {
        var32 = var28 + var21;
        var33 = var29;
        var34 = var7 + var6 >> 1;
        var35 = var11 + var10 >> 1;
        var36 = var15 + var14 >> 1;
      } else if (var31 == 3) {
        var32 = var28 + var20;
        var33 = var29;
        var34 = var7;
        var35 = var11;
        var36 = var15;
      } else if (var31 == 4) {
        var32 = var28 + var20;
        var33 = var29 + var21;
        var34 = var8 + var7 >> 1;
        var35 = var11 + var12 >> 1;
        var36 = var15 + var16 >> 1;
      } else if (var31 == 5) {
        var32 = var28 + var20;
        var33 = var29 + var20;
        var34 = var8;
        var35 = var12;
        var36 = var16;
      } else if (var31 == 6) {
        var32 = var28 + var21;
        var33 = var29 + var20;
        var34 = var8 + var9 >> 1;
        var35 = var13 + var12 >> 1;
        var36 = var17 + var16 >> 1;
      } else if (var31 == 7) {
        var32 = var28;
        var33 = var29 + var20;
        var34 = var9;
        var35 = var13;
        var36 = var17;
      } else if (var31 == 8) {
        var32 = var28;
        var33 = var29 + var21;
        var34 = var9 + var6 >> 1;
        var35 = var13 + var10 >> 1;
        var36 = var17 + var14 >> 1;
      } else if (var31 == 9) {
        var32 = var28 + var21;
        var33 = var29 + var22;
        var34 = var7 + var6 >> 1;
        var35 = var11 + var10 >> 1;
        var36 = var15 + var14 >> 1;
      } else if (var31 == 10) {
        var32 = var28 + var23;
        var33 = var29 + var21;
        var34 = var8 + var7 >> 1;
        var35 = var11 + var12 >> 1;
        var36 = var15 + var16 >> 1;
      } else if (var31 == 11) {
        var32 = var28 + var21;
        var33 = var29 + var23;
        var34 = var8 + var9 >> 1;
        var35 = var13 + var12 >> 1;
        var36 = var17 + var16 >> 1;
      } else if (var31 == 12) {
        var32 = var28 + var22;
        var33 = var29 + var21;
        var34 = var9 + var6 >> 1;
        var35 = var13 + var10 >> 1;
        var36 = var17 + var14 >> 1;
      } else if (var31 == 13) {
        var32 = var28 + var22;
        var33 = var29 + var22;
        var34 = var6;
        var35 = var10;
        var36 = var14;
      } else if (var31 == 14) {
        var32 = var28 + var23;
        var33 = var29 + var22;
        var34 = var7;
        var35 = var11;
        var36 = var15;
      } else if (var31 == 15) {
        var32 = var28 + var23;
        var33 = var29 + var23;
        var34 = var8;
        var35 = var12;
        var36 = var16;
      } else {
        var32 = var28 + var22;
        var33 = var29 + var23;
        var34 = var9;
        var35 = var13;
        var36 = var17;
      }

      xVertices[var30] = var32;
      yVertices[var30] = var34;
      zVertices[var30] = var33;
      var26[var30] = var35;
      var27[var30] = var36;
    }

    int[] var37 = anIntArrayArray1536[shape];
    var31 = var37.length / 4;
    anIntArray1543 = new int[var31];
    anIntArray1540 = new int[var31];
    anIntArray1551 = new int[var31];
    anIntArray1547 = new int[var31];
    anIntArray1544 = new int[var31];
    anIntArray1542 = new int[var31];
    if (var3 != -1) {
      triangleTexture = new int[var31];
    }

    var32 = 0;

    for (var33 = 0; var33 < var31; ++var33) {
      var34 = var37[var32];
      var35 = var37[var32 + 1];
      var36 = var37[var32 + 2];
      int var38 = var37[var32 + 3];
      var32 += 4;
      if (var35 < 4) {
        var35 = var35 - rotation & 3;
      }

      if (var36 < 4) {
        var36 = var36 - rotation & 3;
      }

      if (var38 < 4) {
        var38 = var38 - rotation & 3;
      }

      anIntArray1543[var33] = var35;
      anIntArray1540[var33] = var36;
      anIntArray1551[var33] = var38;
      if (var34 == 0) {
        anIntArray1547[var33] = var26[var35];
        anIntArray1544[var33] = var26[var36];
        anIntArray1542[var33] = var26[var38];
        if (triangleTexture != null) {
          triangleTexture[var33] = -1;
        }
      } else {
        anIntArray1547[var33] = var27[var35];
        anIntArray1544[var33] = var27[var36];
        anIntArray1542[var33] = var27[var38];
        if (triangleTexture != null) {
          triangleTexture[var33] = var3;
        }
      }
    }

    var33 = var6;
    var34 = var7;
    if (var7 < var6) {
      var33 = var7;
    }

    if (var7 > var7) {
      var34 = var7;
    }

    if (var8 < var33) {
      var33 = var8;
    }

    if (var8 > var34) {
      var34 = var8;
    }

    if (var9 < var33) {
      var33 = var9;
    }

    if (var9 > var34) {
      var34 = var9;
    }

  }
}
