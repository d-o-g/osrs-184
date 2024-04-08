package jagex.oldscape.client.scene.entity;

import jagex.oldscape.Face;
import jagex.oldscape.Vector3i;
import jagex.jagex3.graphics.JagGraphics3D;
import jagex.jagex3.js5.ReferenceTable;
import jagex.messaging.Buffer;

public class UnlitModel extends Entity {

  public static final int[] anIntArray1433 = JagGraphics3D.SIN_TABLE;
  public static final int[] anIntArray1434 = new int[10000];
  public static final int[] anIntArray1427 = JagGraphics3D.COS_TABLE;
  public static final int[] anIntArray1430 = new int[10000];

  public static int anInt1424 = 0;

  public Vector3i[] vertices;
  public Vector3i[] aClass96Array1422;

  public Face[] faces;

  public int[][] faceLabels;
  public int[][] vertexLabels;

  public int[] vertexSkins;
  public int[] xVertices;
  public int[] yVertices;
  public int[] zVertices;
  public int[] faceSkins;
  public int[] xTriangles;
  public int[] yTriangles;
  public int[] zTriangles;

  public short[] xTexturedTriangles;
  public short[] yTexturedTriangles;
  public short[] zTexturedTriangles;
  public short[] faceTextures;
  public short[] faceColors;

  public byte[] textureIndices;
  public byte[] faceAlphas;
  public byte[] textureRenderTypes;
  public byte[] faceRenderPriorities;
  public byte[] faceRenderTypes;

  public int anInt556;
  public int anInt1105;
  public int anInt1437;
  public int anInt579;
  public int texturedTriangleCount;
  public int vertexCount;
  public int faceCount;
  public int anInt1436;

  public short ambience;
  public short contrast;

  public byte defaultRenderPriority;

  public boolean aBoolean1420;

  public UnlitModel() {
    vertexCount = 0;
    faceCount = 0;
    defaultRenderPriority = 0;
    aBoolean1420 = false;
  }

  public UnlitModel(UnlitModel[] var1, int var2) {
    vertexCount = 0;
    faceCount = 0;
    defaultRenderPriority = 0;
    aBoolean1420 = false;
    boolean var3 = false;
    boolean var4 = false;
    boolean var5 = false;
    boolean var6 = false;
    boolean var7 = false;
    boolean var8 = false;
    vertexCount = 0;
    faceCount = 0;
    texturedTriangleCount = 0;
    defaultRenderPriority = -1;

    int var9;
    UnlitModel var10;
    for (var9 = 0; var9 < var2; ++var9) {
      var10 = var1[var9];
      if (var10 != null) {
        vertexCount += var10.vertexCount;
        faceCount += var10.faceCount;
        texturedTriangleCount += var10.texturedTriangleCount;
        if (var10.faceRenderPriorities != null) {
          var4 = true;
        } else {
          if (defaultRenderPriority == -1) {
            defaultRenderPriority = var10.defaultRenderPriority;
          }

          if (defaultRenderPriority != var10.defaultRenderPriority) {
            var4 = true;
          }
        }

        var3 |= var10.faceRenderTypes != null;
        var5 |= var10.faceAlphas != null;
        var6 |= var10.faceSkins != null;
        var7 |= var10.faceTextures != null;
        var8 |= var10.textureIndices != null;
      }
    }

    xVertices = new int[vertexCount];
    yVertices = new int[vertexCount];
    zVertices = new int[vertexCount];
    vertexSkins = new int[vertexCount];
    xTriangles = new int[faceCount];
    yTriangles = new int[faceCount];
    zTriangles = new int[faceCount];
    if (var3) {
      faceRenderTypes = new byte[faceCount];
    }

    if (var4) {
      faceRenderPriorities = new byte[faceCount];
    }

    if (var5) {
      faceAlphas = new byte[faceCount];
    }

    if (var6) {
      faceSkins = new int[faceCount];
    }

    if (var7) {
      faceTextures = new short[faceCount];
    }

    if (var8) {
      textureIndices = new byte[faceCount];
    }

    faceColors = new short[faceCount];
    if (texturedTriangleCount > 0) {
      textureRenderTypes = new byte[texturedTriangleCount];
      xTexturedTriangles = new short[texturedTriangleCount];
      yTexturedTriangles = new short[texturedTriangleCount];
      zTexturedTriangles = new short[texturedTriangleCount];
    }

    vertexCount = 0;
    faceCount = 0;
    texturedTriangleCount = 0;

    for (var9 = 0; var9 < var2; ++var9) {
      var10 = var1[var9];
      if (var10 != null) {
        int var11;
        for (var11 = 0; var11 < var10.faceCount; ++var11) {
          if (var3 && var10.faceRenderTypes != null) {
            faceRenderTypes[faceCount] = var10.faceRenderTypes[var11];
          }

          if (var4) {
            if (var10.faceRenderPriorities != null) {
              faceRenderPriorities[faceCount] = var10.faceRenderPriorities[var11];
            } else {
              faceRenderPriorities[faceCount] = var10.defaultRenderPriority;
            }
          }

          if (var5 && var10.faceAlphas != null) {
            faceAlphas[faceCount] = var10.faceAlphas[var11];
          }

          if (var6 && var10.faceSkins != null) {
            faceSkins[faceCount] = var10.faceSkins[var11];
          }

          if (var7) {
            if (var10.faceTextures != null) {
              faceTextures[faceCount] = var10.faceTextures[var11];
            } else {
              faceTextures[faceCount] = -1;
            }
          }

          if (var8) {
            if (var10.textureIndices != null && var10.textureIndices[var11] != -1) {
              textureIndices[faceCount] = (byte) (texturedTriangleCount + var10.textureIndices[var11]);
            } else {
              textureIndices[faceCount] = -1;
            }
          }

          faceColors[faceCount] = var10.faceColors[var11];
          xTriangles[faceCount] = method979(var10, var10.xTriangles[var11]);
          yTriangles[faceCount] = method979(var10, var10.yTriangles[var11]);
          zTriangles[faceCount] = method979(var10, var10.zTriangles[var11]);
          ++faceCount;
        }

        for (var11 = 0; var11 < var10.texturedTriangleCount; ++var11) {
          byte var12 = textureRenderTypes[texturedTriangleCount] = var10.textureRenderTypes[var11];
          if (var12 == 0) {
            xTexturedTriangles[texturedTriangleCount] = (short) method979(var10, var10.xTexturedTriangles[var11]);
            yTexturedTriangles[texturedTriangleCount] = (short) method979(var10, var10.yTexturedTriangles[var11]);
            zTexturedTriangles[texturedTriangleCount] = (short) method979(var10, var10.zTexturedTriangles[var11]);
          }

          ++texturedTriangleCount;
        }
      }
    }

  }

  public UnlitModel(byte[] var1) {
    vertexCount = 0;
    faceCount = 0;
    defaultRenderPriority = 0;
    aBoolean1420 = false;
    if (var1[var1.length - 1] == -1 && var1[var1.length - 2] == -1) {
      method975(var1);
    } else {
      method980(var1);
    }

  }

  public UnlitModel(UnlitModel var1, boolean var2, boolean var3, boolean var4) {
    vertexCount = 0;
    faceCount = 0;
    defaultRenderPriority = 0;
    aBoolean1420 = false;
    vertexCount = var1.vertexCount;
    faceCount = var1.faceCount;
    texturedTriangleCount = var1.texturedTriangleCount;
    int var6;
    if (var2) {
      xVertices = var1.xVertices;
      yVertices = var1.yVertices;
      zVertices = var1.zVertices;
    } else {
      xVertices = new int[vertexCount];
      yVertices = new int[vertexCount];
      zVertices = new int[vertexCount];

      for (var6 = 0; var6 < vertexCount; ++var6) {
        xVertices[var6] = var1.xVertices[var6];
        yVertices[var6] = var1.yVertices[var6];
        zVertices[var6] = var1.zVertices[var6];
      }
    }

    if (var3) {
      faceColors = var1.faceColors;
    } else {
      faceColors = new short[faceCount];

      for (var6 = 0; var6 < faceCount; ++var6) {
        faceColors[var6] = var1.faceColors[var6];
      }
    }

    if (!var4 && var1.faceTextures != null) {
      faceTextures = new short[faceCount];

      for (var6 = 0; var6 < faceCount; ++var6) {
        faceTextures[var6] = var1.faceTextures[var6];
      }
    } else {
      faceTextures = var1.faceTextures;
    }

    faceAlphas = var1.faceAlphas;
    xTriangles = var1.xTriangles;
    yTriangles = var1.yTriangles;
    zTriangles = var1.zTriangles;
    faceRenderTypes = var1.faceRenderTypes;
    faceRenderPriorities = var1.faceRenderPriorities;
    textureIndices = var1.textureIndices;
    defaultRenderPriority = var1.defaultRenderPriority;
    textureRenderTypes = var1.textureRenderTypes;
    xTexturedTriangles = var1.xTexturedTriangles;
    yTexturedTriangles = var1.yTexturedTriangles;
    zTexturedTriangles = var1.zTexturedTriangles;
    vertexSkins = var1.vertexSkins;
    faceSkins = var1.faceSkins;
    vertexLabels = var1.vertexLabels;
    faceLabels = var1.faceLabels;
    vertices = var1.vertices;
    faces = var1.faces;
    aClass96Array1422 = var1.aClass96Array1422;
    ambience = var1.ambience;
    contrast = var1.contrast;
  }

  public static UnlitModel unpack(ReferenceTable var0, int var1, int var2) {
    byte[] var3 = var0.unpack(var1, var2);
    return var3 == null ? null : new UnlitModel(var3);
  }

  public static void method970(UnlitModel var0, UnlitModel var1, int var2, int var3, int var4, boolean var5) {
    var0.method973();
    var0.method593();
    var1.method973();
    var1.method593();
    ++anInt1424;
    int var6 = 0;
    int[] var7 = var1.xVertices;
    int var8 = var1.vertexCount;

    int var9;
    for (var9 = 0; var9 < var0.vertexCount; ++var9) {
      Vector3i var10 = var0.vertices[var9];
      if (var10.magnitude != 0) {
        int var11 = var0.yVertices[var9] - var3;
        if (var11 <= var1.anInt1105) {
          int var12 = var0.xVertices[var9] - var2;
          if (var12 >= var1.anInt1436 && var12 <= var1.anInt556) {
            int var13 = var0.zVertices[var9] - var4;
            if (var13 >= var1.anInt1437 && var13 <= var1.anInt579) {
              for (int var14 = 0; var14 < var8; ++var14) {
                Vector3i var15 = var1.vertices[var14];
                if (var12 == var7[var14] && var13 == var1.zVertices[var14] && var11 == var1.yVertices[var14] && var15.magnitude != 0) {
                  if (var0.aClass96Array1422 == null) {
                    var0.aClass96Array1422 = new Vector3i[var0.vertexCount];
                  }

                  if (var1.aClass96Array1422 == null) {
                    var1.aClass96Array1422 = new Vector3i[var8];
                  }

                  Vector3i var16 = var0.aClass96Array1422[var9];
                  if (var16 == null) {
                    var16 = var0.aClass96Array1422[var9] = new Vector3i(var10);
                  }

                  Vector3i var17 = var1.aClass96Array1422[var14];
                  if (var17 == null) {
                    var17 = var1.aClass96Array1422[var14] = new Vector3i(var15);
                  }

                  var16.x += var15.x;
                  var16.y += var15.y;
                  var16.z += var15.z;
                  var16.magnitude += var15.magnitude;
                  var17.x += var10.x;
                  var17.y += var10.y;
                  var17.z += var10.z;
                  var17.magnitude += var10.magnitude;
                  ++var6;
                  anIntArray1434[var9] = anInt1424;
                  anIntArray1430[var14] = anInt1424;
                }
              }
            }
          }
        }
      }
    }

    if (var6 >= 3 && var5) {
      for (var9 = 0; var9 < var0.faceCount; ++var9) {
        if (anIntArray1434[var0.xTriangles[var9]] == anInt1424 && anIntArray1434[var0.yTriangles[var9]] == anInt1424 && anIntArray1434[var0.zTriangles[var9]] == anInt1424) {
          if (var0.faceRenderTypes == null) {
            var0.faceRenderTypes = new byte[var0.faceCount];
          }

          var0.faceRenderTypes[var9] = 2;
        }
      }

      for (var9 = 0; var9 < var1.faceCount; ++var9) {
        if (anInt1424 == anIntArray1430[var1.xTriangles[var9]] && anInt1424 == anIntArray1430[var1.yTriangles[var9]] && anInt1424 == anIntArray1430[var1.zTriangles[var9]]) {
          if (var1.faceRenderTypes == null) {
            var1.faceRenderTypes = new byte[var1.faceCount];
          }

          var1.faceRenderTypes[var9] = 2;
        }
      }

    }
  }

  public static int method968(int var0) {
    if (var0 < 2) {
      var0 = 2;
    } else if (var0 > 126) {
      var0 = 126;
    }

    return var0;
  }

  public static int method974(int var0, int var1) {
    var1 = (var0 & 127) * var1 >> 7;
    if (var1 < 2) {
      var1 = 2;
    } else if (var1 > 126) {
      var1 = 126;
    }

    return (var0 & 65408) + var1;
  }

  public void method973() {
    if (!aBoolean1420) {
      super.height = 0;
      anInt1105 = 0;
      anInt1436 = 999999;
      anInt556 = -999999;
      anInt579 = -99999;
      anInt1437 = 99999;

      for (int var1 = 0; var1 < vertexCount; ++var1) {
        int var2 = xVertices[var1];
        int var3 = yVertices[var1];
        int var4 = zVertices[var1];
        if (var2 < anInt1436) {
          anInt1436 = var2;
        }

        if (var2 > anInt556) {
          anInt556 = var2;
        }

        if (var4 < anInt1437) {
          anInt1437 = var4;
        }

        if (var4 > anInt579) {
          anInt579 = var4;
        }

        if (-var3 > super.height) {
          super.height = -var3;
        }

        if (var3 > anInt1105) {
          anInt1105 = var3;
        }
      }

      aBoolean1420 = true;
    }
  }

  public void method593() {
    if (vertices == null) {
      vertices = new Vector3i[vertexCount];

      int var1;
      for (var1 = 0; var1 < vertexCount; ++var1) {
        vertices[var1] = new Vector3i();
      }

      for (var1 = 0; var1 < faceCount; ++var1) {
        int var2 = xTriangles[var1];
        int var3 = yTriangles[var1];
        int var4 = zTriangles[var1];
        int var5 = xVertices[var3] - xVertices[var2];
        int var6 = yVertices[var3] - yVertices[var2];
        int var7 = zVertices[var3] - zVertices[var2];
        int var8 = xVertices[var4] - xVertices[var2];
        int var9 = yVertices[var4] - yVertices[var2];
        int var10 = zVertices[var4] - zVertices[var2];
        int var11 = var6 * var10 - var9 * var7;
        int var12 = var7 * var8 - var10 * var5;

        int var13;
        for (var13 = var5 * var9 - var8 * var6; var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192; var13 >>= 1) {
          var11 >>= 1;
          var12 >>= 1;
        }

        int var14 = (int) Math.sqrt(var11 * var11 + var12 * var12 + var13 * var13);
        if (var14 <= 0) {
          var14 = 1;
        }

        var11 = var11 * 256 / var14;
        var12 = var12 * 256 / var14;
        var13 = var13 * 256 / var14;
        byte var15;
        if (faceRenderTypes == null) {
          var15 = 0;
        } else {
          var15 = faceRenderTypes[var1];
        }

        if (var15 == 0) {
          Vector3i var16 = vertices[var2];
          var16.x += var11;
          var16.y += var12;
          var16.z += var13;
          ++var16.magnitude;
          var16 = vertices[var3];
          var16.x += var11;
          var16.y += var12;
          var16.z += var13;
          ++var16.magnitude;
          var16 = vertices[var4];
          var16.x += var11;
          var16.y += var12;
          var16.z += var13;
          ++var16.magnitude;
        } else if (var15 == 1) {
          if (faces == null) {
            faces = new Face[faceCount];
          }

          Face face = faces[var1] = new Face();
          face.x = var11;
          face.y = var12;
          face.z = var13;
        }
      }

    }
  }

  public void reset() {
    vertices = null;
    aClass96Array1422 = null;
    faces = null;
    aBoolean1420 = false;
  }

  public UnlitModel method978() {
    UnlitModel var1 = new UnlitModel();
    if (faceRenderTypes != null) {
      var1.faceRenderTypes = new byte[faceCount];

      System.arraycopy(faceRenderTypes, 0, var1.faceRenderTypes, 0, faceCount);
    }

    var1.vertexCount = vertexCount;
    var1.faceCount = faceCount;
    var1.texturedTriangleCount = texturedTriangleCount;
    var1.xVertices = xVertices;
    var1.yVertices = yVertices;
    var1.zVertices = zVertices;
    var1.xTriangles = xTriangles;
    var1.yTriangles = yTriangles;
    var1.zTriangles = zTriangles;
    var1.faceRenderPriorities = faceRenderPriorities;
    var1.faceAlphas = faceAlphas;
    var1.textureIndices = textureIndices;
    var1.faceColors = faceColors;
    var1.faceTextures = faceTextures;
    var1.defaultRenderPriority = defaultRenderPriority;
    var1.textureRenderTypes = textureRenderTypes;
    var1.xTexturedTriangles = xTexturedTriangles;
    var1.yTexturedTriangles = yTexturedTriangles;
    var1.zTexturedTriangles = zTexturedTriangles;
    var1.vertexSkins = vertexSkins;
    var1.faceSkins = faceSkins;
    var1.vertexLabels = vertexLabels;
    var1.faceLabels = faceLabels;
    var1.vertices = vertices;
    var1.faces = faces;
    var1.ambience = ambience;
    var1.contrast = contrast;
    return var1;
  }

  public void texturize(short var1, short var2) {
    for (int var3 = 0; var3 < faceCount; ++var3) {
      if (faceColors[var3] == var1) {
        faceColors[var3] = var2;
      }
    }

  }

  public void method764(int var1, int var2, int var3) {
    for (int var4 = 0; var4 < vertexCount; ++var4) {
      xVertices[var4] = xVertices[var4] * var1 / 128;
      yVertices[var4] = var2 * yVertices[var4] / 128;
      zVertices[var4] = var3 * zVertices[var4] / 128;
    }

    reset();
  }

  public void method980(byte[] var1) {
    boolean var2 = false;
    boolean var3 = false;
    Buffer var4 = new Buffer(var1);
    Buffer var5 = new Buffer(var1);
    Buffer var6 = new Buffer(var1);
    Buffer var7 = new Buffer(var1);
    Buffer var8 = new Buffer(var1);
    var4.pos = var1.length - 18;
    int var9 = var4.g2();
    int var10 = var4.g2();
    int var11 = var4.g1();
    int var12 = var4.g1();
    int var13 = var4.g1();
    int var14 = var4.g1();
    int var15 = var4.g1();
    int var16 = var4.g1();
    int var17 = var4.g2();
    int var18 = var4.g2();
    int var19 = var4.g2();
    int var20 = var4.g2();
    byte var21 = 0;
    int var45 = var21 + var9;
    int var23 = var45;
    var45 += var10;
    int var24 = var45;
    if (var13 == 255) {
      var45 += var10;
    }

    int var25 = var45;
    if (var15 == 1) {
      var45 += var10;
    }

    int var26 = var45;
    if (var12 == 1) {
      var45 += var10;
    }

    int var27 = var45;
    if (var16 == 1) {
      var45 += var9;
    }

    int var28 = var45;
    if (var14 == 1) {
      var45 += var10;
    }

    int var29 = var45;
    var45 += var20;
    int var30 = var45;
    var45 += var10 * 2;
    int var31 = var45;
    var45 += var11 * 6;
    int var32 = var45;
    var45 += var17;
    int var33 = var45;
    var45 += var18;

    vertexCount = var9;
    faceCount = var10;
    texturedTriangleCount = var11;
    xVertices = new int[var9];
    yVertices = new int[var9];
    zVertices = new int[var9];
    xTriangles = new int[var10];
    yTriangles = new int[var10];
    zTriangles = new int[var10];
    if (var11 > 0) {
      textureRenderTypes = new byte[var11];
      xTexturedTriangles = new short[var11];
      yTexturedTriangles = new short[var11];
      zTexturedTriangles = new short[var11];
    }

    if (var16 == 1) {
      vertexSkins = new int[var9];
    }

    if (var12 == 1) {
      faceRenderTypes = new byte[var10];
      textureIndices = new byte[var10];
      faceTextures = new short[var10];
    }

    if (var13 == 255) {
      faceRenderPriorities = new byte[var10];
    } else {
      defaultRenderPriority = (byte) var13;
    }

    if (var14 == 1) {
      faceAlphas = new byte[var10];
    }

    if (var15 == 1) {
      faceSkins = new int[var10];
    }

    faceColors = new short[var10];
    var4.pos = var21;
    var5.pos = var32;
    var6.pos = var33;
    var7.pos = var45;
    var8.pos = var27;
    int var35 = 0;
    int var36 = 0;
    int var37 = 0;

    int var38;
    int var39;
    int var40;
    int var41;
    int var42;
    for (var38 = 0; var38 < var9; ++var38) {
      var39 = var4.g1();
      var40 = 0;
      if ((var39 & 1) != 0) {
        var40 = var5.gSmart();
      }

      var41 = 0;
      if ((var39 & 2) != 0) {
        var41 = var6.gSmart();
      }

      var42 = 0;
      if ((var39 & 4) != 0) {
        var42 = var7.gSmart();
      }

      xVertices[var38] = var35 + var40;
      yVertices[var38] = var36 + var41;
      zVertices[var38] = var37 + var42;
      var35 = xVertices[var38];
      var36 = yVertices[var38];
      var37 = zVertices[var38];
      if (var16 == 1) {
        vertexSkins[var38] = var8.g1();
      }
    }

    var4.pos = var30;
    var5.pos = var26;
    var6.pos = var24;
    var7.pos = var28;
    var8.pos = var25;

    for (var38 = 0; var38 < var10; ++var38) {
      faceColors[var38] = (short) var4.g2();
      if (var12 == 1) {
        var39 = var5.g1();
        if ((var39 & 1) == 1) {
          faceRenderTypes[var38] = 1;
          var2 = true;
        } else {
          faceRenderTypes[var38] = 0;
        }

        if ((var39 & 2) == 2) {
          textureIndices[var38] = (byte) (var39 >> 2);
          faceTextures[var38] = faceColors[var38];
          faceColors[var38] = 127;
          if (faceTextures[var38] != -1) {
            var3 = true;
          }
        } else {
          textureIndices[var38] = -1;
          faceTextures[var38] = -1;
        }
      }

      if (var13 == 255) {
        faceRenderPriorities[var38] = var6.g1b();
      }

      if (var14 == 1) {
        faceAlphas[var38] = var7.g1b();
      }

      if (var15 == 1) {
        faceSkins[var38] = var8.g1();
      }
    }

    var4.pos = var29;
    var5.pos = var23;
    var38 = 0;
    var39 = 0;
    var40 = 0;
    var41 = 0;

    int var43;
    int var44;
    for (var42 = 0; var42 < var10; ++var42) {
      var43 = var5.g1();
      if (var43 == 1) {
        var38 = var4.gSmart() + var41;
        var39 = var4.gSmart() + var38;
        var40 = var4.gSmart() + var39;
        var41 = var40;
        xTriangles[var42] = var38;
        yTriangles[var42] = var39;
        zTriangles[var42] = var40;
      }

      if (var43 == 2) {
        var39 = var40;
        var40 = var4.gSmart() + var41;
        var41 = var40;
        xTriangles[var42] = var38;
        yTriangles[var42] = var39;
        zTriangles[var42] = var40;
      }

      if (var43 == 3) {
        var38 = var40;
        var40 = var4.gSmart() + var41;
        var41 = var40;
        xTriangles[var42] = var38;
        yTriangles[var42] = var39;
        zTriangles[var42] = var40;
      }

      if (var43 == 4) {
        var44 = var38;
        var38 = var39;
        var39 = var44;
        var40 = var4.gSmart() + var41;
        var41 = var40;
        xTriangles[var42] = var38;
        yTriangles[var42] = var44;
        zTriangles[var42] = var40;
      }
    }

    var4.pos = var31;

    for (var42 = 0; var42 < var11; ++var42) {
      textureRenderTypes[var42] = 0;
      xTexturedTriangles[var42] = (short) var4.g2();
      yTexturedTriangles[var42] = (short) var4.g2();
      zTexturedTriangles[var42] = (short) var4.g2();
    }

    if (textureIndices != null) {
      boolean var46 = false;

      for (var43 = 0; var43 < var10; ++var43) {
        var44 = textureIndices[var43] & 255;
        if (var44 != 255) {
          if (xTriangles[var43] == (xTexturedTriangles[var44] & '\uffff') && yTriangles[var43] == (yTexturedTriangles[var44] & '\uffff') && zTriangles[var43] == (zTexturedTriangles[var44] & '\uffff')) {
            textureIndices[var43] = -1;
          } else {
            var46 = true;
          }
        }
      }

      if (!var46) {
        textureIndices = null;
      }
    }

    if (!var3) {
      faceTextures = null;
    }

    if (!var2) {
      faceRenderTypes = null;
    }

  }

  public void colorize(short var1, short var2) {
    if (faceTextures != null) {
      for (int var3 = 0; var3 < faceCount; ++var3) {
        if (faceTextures[var3] == var1) {
          faceTextures[var3] = var2;
        }
      }

    }
  }

  public final Model light(int var1, int var2, int var3, int var4, int var5) {
    method593();
    int var6 = (int) Math.sqrt(var5 * var5 + var3 * var3 + var4 * var4);
    int var7 = var6 * var2 >> 8;
    Model var8 = new Model();
    var8.xFaceColors = new int[faceCount];
    var8.yFaceColors = new int[faceCount];
    var8.zFaceColors = new int[faceCount];
    if (texturedTriangleCount > 0 && textureIndices != null) {
      int[] var9 = new int[texturedTriangleCount];

      int var10;
      for (var10 = 0; var10 < faceCount; ++var10) {
        if (textureIndices[var10] != -1) {
          ++var9[textureIndices[var10] & 255];
        }
      }

      var8.texturedTriangleCount = 0;

      for (var10 = 0; var10 < texturedTriangleCount; ++var10) {
        if (var9[var10] > 0 && textureRenderTypes[var10] == 0) {
          ++var8.texturedTriangleCount;
        }
      }

      var8.anIntArray782 = new int[var8.texturedTriangleCount];
      var8.anIntArray1103 = new int[var8.texturedTriangleCount];
      var8.anIntArray781 = new int[var8.texturedTriangleCount];
      var10 = 0;

      int var12;
      for (var12 = 0; var12 < texturedTriangleCount; ++var12) {
        if (var9[var12] > 0 && textureRenderTypes[var12] == 0) {
          var8.anIntArray782[var10] = xTexturedTriangles[var12] & '\uffff';
          var8.anIntArray1103[var10] = yTexturedTriangles[var12] & '\uffff';
          var8.anIntArray781[var10] = zTexturedTriangles[var12] & '\uffff';
          var9[var12] = var10++;
        } else {
          var9[var12] = -1;
        }
      }

      var8.aByteArray1752 = new byte[faceCount];

      for (var12 = 0; var12 < faceCount; ++var12) {
        if (textureIndices[var12] != -1) {
          var8.aByteArray1752[var12] = (byte) var9[textureIndices[var12] & 255];
        } else {
          var8.aByteArray1752[var12] = -1;
        }
      }
    }

    for (int var11 = 0; var11 < faceCount; ++var11) {
      byte var17;
      if (faceRenderTypes == null) {
        var17 = 0;
      } else {
        var17 = faceRenderTypes[var11];
      }

      byte var18;
      if (faceAlphas == null) {
        var18 = 0;
      } else {
        var18 = faceAlphas[var11];
      }

      short var13;
      if (faceTextures == null) {
        var13 = -1;
      } else {
        var13 = faceTextures[var11];
      }

      if (var18 == -2) {
        var17 = 3;
      }

      if (var18 == -1) {
        var17 = 2;
      }

      Vector3i var15;
      int var16;
      Face var19;
      if (var13 == -1) {
        if (var17 != 0) {
          if (var17 == 1) {
            var19 = faces[var11];
            var16 = (var4 * var19.y + var5 * var19.z + var3 * var19.x) / (var7 / 2 + var7) + var1;
            var8.xFaceColors[var11] = method974(faceColors[var11] & '\uffff', var16);
            var8.zFaceColors[var11] = -1;
          } else if (var17 == 3) {
            var8.xFaceColors[var11] = 128;
            var8.zFaceColors[var11] = -1;
          } else {
            var8.zFaceColors[var11] = -2;
          }
        } else {
          int var14 = faceColors[var11] & '\uffff';
          if (aClass96Array1422 != null && aClass96Array1422[xTriangles[var11]] != null) {
            var15 = aClass96Array1422[xTriangles[var11]];
          } else {
            var15 = vertices[xTriangles[var11]];
          }

          var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
          var8.xFaceColors[var11] = method974(var14, var16);
          if (aClass96Array1422 != null && aClass96Array1422[yTriangles[var11]] != null) {
            var15 = aClass96Array1422[yTriangles[var11]];
          } else {
            var15 = vertices[yTriangles[var11]];
          }

          var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
          var8.yFaceColors[var11] = method974(var14, var16);
          if (aClass96Array1422 != null && aClass96Array1422[zTriangles[var11]] != null) {
            var15 = aClass96Array1422[zTriangles[var11]];
          } else {
            var15 = vertices[zTriangles[var11]];
          }

          var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
          var8.zFaceColors[var11] = method974(var14, var16);
        }
      } else if (var17 != 0) {
        if (var17 == 1) {
          var19 = faces[var11];
          var16 = (var4 * var19.y + var5 * var19.z + var3 * var19.x) / (var7 / 2 + var7) + var1;
          var8.xFaceColors[var11] = method968(var16);
          var8.zFaceColors[var11] = -1;
        } else {
          var8.zFaceColors[var11] = -2;
        }
      } else {
        if (aClass96Array1422 != null && aClass96Array1422[xTriangles[var11]] != null) {
          var15 = aClass96Array1422[xTriangles[var11]];
        } else {
          var15 = vertices[xTriangles[var11]];
        }

        var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
        var8.xFaceColors[var11] = method968(var16);
        if (aClass96Array1422 != null && aClass96Array1422[yTriangles[var11]] != null) {
          var15 = aClass96Array1422[yTriangles[var11]];
        } else {
          var15 = vertices[yTriangles[var11]];
        }

        var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
        var8.yFaceColors[var11] = method968(var16);
        if (aClass96Array1422 != null && aClass96Array1422[zTriangles[var11]] != null) {
          var15 = aClass96Array1422[zTriangles[var11]];
        } else {
          var15 = vertices[zTriangles[var11]];
        }

        var16 = (var4 * var15.y + var5 * var15.z + var3 * var15.x) / (var7 * var15.magnitude) + var1;
        var8.zFaceColors[var11] = method968(var16);
      }
    }

    method828();
    var8.vertexCount = vertexCount;
    var8.xVertices = xVertices;
    var8.yVertices = yVertices;
    var8.zVertices = zVertices;
    var8.triangleCount = faceCount;
    var8.xTriangles = xTriangles;
    var8.yTriangles = yTriangles;
    var8.zTriangles = zTriangles;
    var8.faceRenderPriorities = faceRenderPriorities;
    var8.faceAlphas = faceAlphas;
    var8.aByte1753 = defaultRenderPriority;
    var8.vertexLabels = vertexLabels;
    var8.faceLabels = faceLabels;
    var8.faceTextures = faceTextures;
    return var8;
  }

  public void method975(byte[] var1) {
    Buffer var2 = new Buffer(var1);
    Buffer var3 = new Buffer(var1);
    Buffer var4 = new Buffer(var1);
    Buffer var5 = new Buffer(var1);
    Buffer var6 = new Buffer(var1);
    Buffer var7 = new Buffer(var1);
    Buffer var8 = new Buffer(var1);
    var2.pos = var1.length - 23;
    int var9 = var2.g2();
    int var10 = var2.g2();
    int var11 = var2.g1();
    int var12 = var2.g1();
    int var13 = var2.g1();
    int var14 = var2.g1();
    int var15 = var2.g1();
    int var16 = var2.g1();
    int var17 = var2.g1();
    int var18 = var2.g2();
    int var19 = var2.g2();
    int var20 = var2.g2();
    int var21 = var2.g2();
    int var22 = var2.g2();
    int var23 = 0;
    int var24 = 0;
    int var25 = 0;
    int var26;
    if (var11 > 0) {
      textureRenderTypes = new byte[var11];
      var2.pos = 0;

      for (var26 = 0; var26 < var11; ++var26) {
        byte var27 = textureRenderTypes[var26] = var2.g1b();
        if (var27 == 0) {
          ++var23;
        }

        if (var27 >= 1 && var27 <= 3) {
          ++var24;
        }

        if (var27 == 2) {
          ++var25;
        }
      }
    }

    var26 = var11 + var9;
    int var28 = var26;
    if (var12 == 1) {
      var26 += var10;
    }

    int var29 = var26;
    var26 += var10;
    int var30 = var26;
    if (var13 == 255) {
      var26 += var10;
    }

    int var31 = var26;
    if (var15 == 1) {
      var26 += var10;
    }

    int var32 = var26;
    if (var17 == 1) {
      var26 += var9;
    }

    int var33 = var26;
    if (var14 == 1) {
      var26 += var10;
    }

    int var34 = var26;
    var26 += var21;
    int var35 = var26;
    if (var16 == 1) {
      var26 += var10 * 2;
    }

    int var36 = var26;
    var26 += var22;
    int var37 = var26;
    var26 += var10 * 2;
    int var38 = var26;
    var26 += var18;
    int var39 = var26;
    var26 += var19;
    int var40 = var26;
    var26 += var20;
    int var41 = var26;
    var26 += var23 * 6;
    int var42 = var26;
    var26 += var24 * 6;
    int var43 = var26;
    var26 += var24 * 6;
    int var44 = var26;
    var26 += var24 * 2;
    int var45 = var26;
    var26 += var24;
    int var46 = var26;
    var26 += var24 * 2 + var25 * 2;
    vertexCount = var9;
    faceCount = var10;
    texturedTriangleCount = var11;
    xVertices = new int[var9];
    yVertices = new int[var9];
    zVertices = new int[var9];
    xTriangles = new int[var10];
    yTriangles = new int[var10];
    zTriangles = new int[var10];
    if (var17 == 1) {
      vertexSkins = new int[var9];
    }

    if (var12 == 1) {
      faceRenderTypes = new byte[var10];
    }

    if (var13 == 255) {
      faceRenderPriorities = new byte[var10];
    } else {
      defaultRenderPriority = (byte) var13;
    }

    if (var14 == 1) {
      faceAlphas = new byte[var10];
    }

    if (var15 == 1) {
      faceSkins = new int[var10];
    }

    if (var16 == 1) {
      faceTextures = new short[var10];
    }

    if (var16 == 1 && var11 > 0) {
      textureIndices = new byte[var10];
    }

    faceColors = new short[var10];
    if (var11 > 0) {
      xTexturedTriangles = new short[var11];
      yTexturedTriangles = new short[var11];
      zTexturedTriangles = new short[var11];
    }

    var2.pos = var11;
    var3.pos = var38;
    var4.pos = var39;
    var5.pos = var40;
    var6.pos = var32;
    int var48 = 0;
    int var49 = 0;
    int var50 = 0;

    int var51;
    int var52;
    int var53;
    int var54;
    int var55;
    for (var51 = 0; var51 < var9; ++var51) {
      var52 = var2.g1();
      var53 = 0;
      if ((var52 & 1) != 0) {
        var53 = var3.gSmart();
      }

      var54 = 0;
      if ((var52 & 2) != 0) {
        var54 = var4.gSmart();
      }

      var55 = 0;
      if ((var52 & 4) != 0) {
        var55 = var5.gSmart();
      }

      xVertices[var51] = var48 + var53;
      yVertices[var51] = var49 + var54;
      zVertices[var51] = var50 + var55;
      var48 = xVertices[var51];
      var49 = yVertices[var51];
      var50 = zVertices[var51];
      if (var17 == 1) {
        vertexSkins[var51] = var6.g1();
      }
    }

    var2.pos = var37;
    var3.pos = var28;
    var4.pos = var30;
    var5.pos = var33;
    var6.pos = var31;
    var7.pos = var35;
    var8.pos = var36;

    for (var51 = 0; var51 < var10; ++var51) {
      faceColors[var51] = (short) var2.g2();
      if (var12 == 1) {
        faceRenderTypes[var51] = var3.g1b();
      }

      if (var13 == 255) {
        faceRenderPriorities[var51] = var4.g1b();
      }

      if (var14 == 1) {
        faceAlphas[var51] = var5.g1b();
      }

      if (var15 == 1) {
        faceSkins[var51] = var6.g1();
      }

      if (var16 == 1) {
        faceTextures[var51] = (short) (var7.g2() - 1);
      }

      if (textureIndices != null && faceTextures[var51] != -1) {
        textureIndices[var51] = (byte) (var8.g1() - 1);
      }
    }

    var2.pos = var34;
    var3.pos = var29;
    var51 = 0;
    var52 = 0;
    var53 = 0;
    var54 = 0;

    int var56;
    for (var55 = 0; var55 < var10; ++var55) {
      var56 = var3.g1();
      if (var56 == 1) {
        var51 = var2.gSmart() + var54;
        var52 = var2.gSmart() + var51;
        var53 = var2.gSmart() + var52;
        var54 = var53;
        xTriangles[var55] = var51;
        yTriangles[var55] = var52;
        zTriangles[var55] = var53;
      }

      if (var56 == 2) {
        var52 = var53;
        var53 = var2.gSmart() + var54;
        var54 = var53;
        xTriangles[var55] = var51;
        yTriangles[var55] = var52;
        zTriangles[var55] = var53;
      }

      if (var56 == 3) {
        var51 = var53;
        var53 = var2.gSmart() + var54;
        var54 = var53;
        xTriangles[var55] = var51;
        yTriangles[var55] = var52;
        zTriangles[var55] = var53;
      }

      if (var56 == 4) {
        int var57 = var51;
        var51 = var52;
        var52 = var57;
        var53 = var2.gSmart() + var54;
        var54 = var53;
        xTriangles[var55] = var51;
        yTriangles[var55] = var57;
        zTriangles[var55] = var53;
      }
    }

    var2.pos = var41;
    var3.pos = var42;
    var4.pos = var43;
    var5.pos = var44;
    var6.pos = var45;
    var7.pos = var46;

    for (var55 = 0; var55 < var11; ++var55) {
      var56 = textureRenderTypes[var55] & 255;
      if (var56 == 0) {
        xTexturedTriangles[var55] = (short) var2.g2();
        yTexturedTriangles[var55] = (short) var2.g2();
        zTexturedTriangles[var55] = (short) var2.g2();
      }
    }

    var2.pos = var26;
    var55 = var2.g1();
    if (var55 != 0) {
      var2.g2();
      var2.g2();
      var2.g2();
      var2.g4();
    }

  }

  public void method976(int var1, int var2, int var3) {
    for (int var4 = 0; var4 < vertexCount; ++var4) {
      int[] var10000 = xVertices;
      var10000[var4] += var1;
      var10000 = yVertices;
      var10000[var4] += var2;
      var10000 = zVertices;
      var10000[var4] += var3;
    }

    reset();
  }

  public UnlitModel method977(int[][] var1, int var2, int var3, int var4, int var6) {
    method973();
    int var7 = var2 + anInt1436;
    int var8 = var2 + anInt556;
    int var9 = var4 + anInt1437;
    int var10 = var4 + anInt579;
    if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
      var7 >>= 7;
      var8 = var8 + 127 >> 7;
      var9 >>= 7;
      var10 = var10 + 127 >> 7;
      if (var3 == var1[var7][var9] && var3 == var1[var8][var9] && var3 == var1[var7][var10] && var3 == var1[var8][var10]) {
        return this;
      }
      UnlitModel var11 = new UnlitModel();
      var11.vertexCount = vertexCount;
      var11.faceCount = faceCount;
      var11.texturedTriangleCount = texturedTriangleCount;
      var11.xVertices = xVertices;
      var11.zVertices = zVertices;
      var11.xTriangles = xTriangles;
      var11.yTriangles = yTriangles;
      var11.zTriangles = zTriangles;
      var11.faceRenderTypes = faceRenderTypes;
      var11.faceRenderPriorities = faceRenderPriorities;
      var11.faceAlphas = faceAlphas;
      var11.textureIndices = textureIndices;
      var11.faceColors = faceColors;
      var11.faceTextures = faceTextures;
      var11.defaultRenderPriority = defaultRenderPriority;
      var11.textureRenderTypes = textureRenderTypes;
      var11.xTexturedTriangles = xTexturedTriangles;
      var11.yTexturedTriangles = yTexturedTriangles;
      var11.zTexturedTriangles = zTexturedTriangles;
      var11.vertexSkins = vertexSkins;
      var11.faceSkins = faceSkins;
      var11.vertexLabels = vertexLabels;
      var11.faceLabels = faceLabels;
      var11.ambience = ambience;
      var11.contrast = contrast;
      var11.yVertices = new int[var11.vertexCount];
      int var12;
      int var13;
      int var14;
      int var15;
      int var16;
      int var17;
      int var18;
      int var19;
      int var20;
      int var21;
      if (var6 == 0) {
        for (var12 = 0; var12 < var11.vertexCount; ++var12) {
          var13 = var2 + xVertices[var12];
          var14 = var4 + zVertices[var12];
          var15 = var13 & 127;
          var16 = var14 & 127;
          var17 = var13 >> 7;
          var18 = var14 >> 7;
          var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
          var20 = var1[var17][var18 + 1] * (128 - var15) + var15 * var1[var17 + 1][var18 + 1] >> 7;
          var21 = var19 * (128 - var16) + var20 * var16 >> 7;
          var11.yVertices[var12] = var21 + yVertices[var12] - var3;
        }
      } else {
        for (var12 = 0; var12 < var11.vertexCount; ++var12) {
          var13 = (-yVertices[var12] << 16) / super.height;
          if (var13 < var6) {
            var14 = var2 + xVertices[var12];
            var15 = var4 + zVertices[var12];
            var16 = var14 & 127;
            var17 = var15 & 127;
            var18 = var14 >> 7;
            var19 = var15 >> 7;
            var20 = var1[var18][var19] * (128 - var16) + var1[var18 + 1][var19] * var16 >> 7;
            var21 = var1[var18][var19 + 1] * (128 - var16) + var16 * var1[var18 + 1][var19 + 1] >> 7;
            int var22 = var20 * (128 - var17) + var21 * var17 >> 7;
            var11.yVertices[var12] = (var6 - var13) * (var22 - var3) / var6 + yVertices[var12];
          }
        }
      }

      var11.reset();
      return var11;
    }
    return this;
  }

  public void method828() {
    int[] var1;
    int var2;
    int var10002;
    int var3;
    int var4;
    if (vertexSkins != null) {
      var1 = new int[256];
      var2 = 0;

      for (var3 = 0; var3 < vertexCount; ++var3) {
        var4 = vertexSkins[var3];
        var10002 = var1[var4]++;
        if (var4 > var2) {
          var2 = var4;
        }
      }

      vertexLabels = new int[var2 + 1][];

      for (var3 = 0; var3 <= var2; ++var3) {
        vertexLabels[var3] = new int[var1[var3]];
        var1[var3] = 0;
      }

      for (var3 = 0; var3 < vertexCount; vertexLabels[var4][var1[var4]++] = var3++) {
        var4 = vertexSkins[var3];
      }

      vertexSkins = null;
    }

    if (faceSkins != null) {
      var1 = new int[256];
      var2 = 0;

      for (var3 = 0; var3 < faceCount; ++var3) {
        var4 = faceSkins[var3];
        var10002 = var1[var4]++;
        if (var4 > var2) {
          var2 = var4;
        }
      }

      faceLabels = new int[var2 + 1][];

      for (var3 = 0; var3 <= var2; ++var3) {
        faceLabels[var3] = new int[var1[var3]];
        var1[var3] = 0;
      }

      for (var3 = 0; var3 < faceCount; faceLabels[var4][var1[var4]++] = var3++) {
        var4 = faceSkins[var3];
      }

      faceSkins = null;
    }

  }

  public void method981() {
    int var1;
    for (var1 = 0; var1 < vertexCount; ++var1) {
      zVertices[var1] = -zVertices[var1];
    }

    for (var1 = 0; var1 < faceCount; ++var1) {
      int var2 = xTriangles[var1];
      xTriangles[var1] = zTriangles[var1];
      zTriangles[var1] = var2;
    }

    reset();
  }

  public void method302(int var1) {
    int var2 = anIntArray1433[var1];
    int var3 = anIntArray1427[var1];

    for (int var4 = 0; var4 < vertexCount; ++var4) {
      int var5 = var2 * zVertices[var4] + var3 * xVertices[var4] >> 16;
      zVertices[var4] = var3 * zVertices[var4] - var2 * xVertices[var4] >> 16;
      xVertices[var4] = var5;
    }

    reset();
  }

  public void method886() {
    for (int var1 = 0; var1 < vertexCount; ++var1) {
      int var2 = xVertices[var1];
      xVertices[var1] = zVertices[var1];
      zVertices[var1] = -var2;
    }

    reset();
  }

  public void method972() {
    for (int var1 = 0; var1 < vertexCount; ++var1) {
      xVertices[var1] = -xVertices[var1];
      zVertices[var1] = -zVertices[var1];
    }

    reset();
  }

  public void method969() {
    for (int var1 = 0; var1 < vertexCount; ++var1) {
      int var2 = zVertices[var1];
      zVertices[var1] = xVertices[var1];
      xVertices[var1] = -var2;
    }

    reset();
  }

  public final int method979(UnlitModel var1, int var2) {
    int var3 = -1;
    int var4 = var1.xVertices[var2];
    int var5 = var1.yVertices[var2];
    int var6 = var1.zVertices[var2];

    for (int var7 = 0; var7 < vertexCount; ++var7) {
      if (var4 == xVertices[var7] && var5 == yVertices[var7] && var6 == zVertices[var7]) {
        var3 = var7;
        break;
      }
    }

    if (var3 == -1) {
      xVertices[vertexCount] = var4;
      yVertices[vertexCount] = var5;
      zVertices[vertexCount] = var6;
      if (var1.vertexSkins != null) {
        vertexSkins[vertexCount] = var1.vertexSkins[var2];
      }

      var3 = vertexCount++;
    }

    return var3;
  }
}
