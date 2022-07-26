package jag.statics;

import jag.game.client;
import jag.game.relationship.Class114;
import jag.js5.DiskFile;
import jag.opcode.Buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Statics5 {

    public static String userhome;
    public static int anInt838;
    public static File aFile837;

    public static File method60(String var0, String var1, int var2) {
        String var3 = var2 == 0 ? "" : "" + var2;
        aFile837 = new File(userhome, "jagex_cl_" + var0 + "_" + var1 + var3 + ".dat");
        String var4 = null;
        String var5 = null;
        boolean var6 = false;
        Buffer var8;
        int var12;
        File var27;
        if (aFile837.exists()) {
            try {
                DiskFile var7 = new DiskFile(aFile837, "rw", 10000L);

                int var9;
                for (var8 = new Buffer((int) var7.length()); var8.pos < var8.payload.length; var8.pos += var9) {
                    var9 = var7.read(var8.payload, var8.pos, var8.payload.length - var8.pos);
                    if (var9 == -1) {
                        throw new IOException();
                    }
                }

                var8.pos = 0;
                var9 = var8.g1();
                if (var9 < 1 || var9 > 3) {
                    throw new IOException("" + var9);
                }

                int var10 = 0;
                if (var9 > 1) {
                    var10 = var8.g1();
                }

                if (var9 <= 2) {
                    var4 = var8.checkedgstr();
                    if (var10 == 1) {
                        var5 = var8.checkedgstr();
                    }
                } else {
                    var4 = var8.method1045();
                    if (var10 == 1) {
                        var5 = var8.method1045();
                    }
                }

                var7.close();
            } catch (IOException var25) {
                var25.printStackTrace();
            }

            if (var4 != null) {
                var27 = new File(var4);
                if (!var27.exists()) {
                    var4 = null;
                }
            }

            if (var4 != null) {
                var27 = new File(var4, "test.dat");

                boolean var28;
                try {
                    RandomAccessFile var11 = new RandomAccessFile(var27, "rw");
                    var12 = var11.read();
                    var11.seek(0L);
                    var11.write(var12);
                    var11.seek(0L);
                    var11.close();
                    var27.delete();
                    var28 = true;
                } catch (Exception var23) {
                    var28 = false;
                }

                if (!var28) {
                    var4 = null;
                }
            }
        }

        if (var4 == null && var2 == 0) {
            label159:
            for (int var13 = 0; var13 < client.aStringArray1533.length; ++var13) {
                for (int var14 = 0; var14 < Class114.cacheDirectories.length; ++var14) {
                    File var15 = new File(Class114.cacheDirectories[var14] + client.aStringArray1533[var13] + File.separatorChar + var0 + File.separatorChar);
                    if (var15.exists()) {
                        File var16 = new File(var15, "test.dat");

                        boolean var30;
                        try {
                            RandomAccessFile var17 = new RandomAccessFile(var16, "rw");
                            int var18 = var17.read();
                            var17.seek(0L);
                            var17.write(var18);
                            var17.seek(0L);
                            var17.close();
                            var16.delete();
                            var30 = true;
                        } catch (Exception var22) {
                            var30 = false;
                        }

                        if (var30) {
                            var4 = var15.toString();
                            var6 = true;
                            break label159;
                        }
                    }
                }
            }
        }

        if (var4 == null) {
            var4 = userhome + File.separatorChar + "jagexcache" + var3 + File.separatorChar + var0 + File.separatorChar + var1 + File.separatorChar;
            var6 = true;
        }

        File var26;
        if (var5 != null) {
            var26 = new File(var5);
            var27 = new File(var4);

            try {

                for (var12 = 0; var12 < var26.listFiles().length; ++var12) {
                    File var34 = var26.listFiles()[var12];
                    File var19 = new File(var27, var34.getName());
                    boolean var20 = var34.renameTo(var19);
                    if (!var20) {
                        throw new IOException();
                    }
                }
            } catch (Exception var24) {
                var24.printStackTrace();
            }

            var6 = true;
        }

        if (var6) {
            var26 = new File(var4);
            var8 = null;

            try {
                DiskFile var33 = new DiskFile(aFile837, "rw", 10000L);
                Buffer var31 = new Buffer(500);
                var31.p1(3);
                var31.p1(var8 != null ? 1 : 0);
                var31.method1052(var26.getPath());
                if (var8 != null) {
                    var31.method1052("");
                }

                var33.write(var31.payload, 0, var31.pos);
                var33.close();
            } catch (IOException var21) {
                var21.printStackTrace();
            }
        }

        return new File(var4);
    }
}
