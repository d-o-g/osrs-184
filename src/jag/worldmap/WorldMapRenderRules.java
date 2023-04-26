package jag.worldmap;

import jag.game.scene.CollisionMap;
import jag.game.scene.SceneGraph;
import jag.graphics.JagGraphics;
import jag.graphics.Sprite;
import jag.opcode.Buffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WorldMapRenderRules {
    public static int anInt168;
    final int anInt170;
    byte[][][] data;

    WorldMapRenderRules(int var1) {
        this.anInt170 = var1;
    }

    public static Sprite method130(byte[] var0) {
        BufferedImage var1;

        try {
            var1 = ImageIO.read(new ByteArrayInputStream(var0));
            int var2 = var1.getWidth();
            int var3 = var1.getHeight();
            int[] var4 = new int[var2 * var3];
            PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var2, var3, var4, 0, var2);
            var5.grabPixels();
            return new Sprite(var4, var2, var3);
        } catch (IOException | InterruptedException ignored) {
        }

        return new Sprite(0, 0);
    }

    public static void loadTile(byte[] var0, int var1, int var2, int var3, int var4, CollisionMap[] collision) {
        for (int level = 0; level < 4; ++level) {
            for (int x = 0; x < 64; ++x) {
                for (int y = 0; y < 64; ++y) {
                    if (x + var1 > 0 && x + var1 < 103 && y + var2 > 0 && y + var2 < 103) {
                        int[] flag = collision[level].flags[x + var1];
                        flag[var2 + y] &= -16777217;
                    }
                }
            }
        }

        Buffer buffer = new Buffer(var0);
        for (int level = 0; level < 4; ++level) {
            for (int x = 0; x < 64; ++x) {
                for (int y = 0; y < 64; ++y) {
                    SceneGraph.method416(buffer, level, x + var1, y + var2, var3, var4, 0);
                }
            }
        }

    }

    void method124() {
        byte[] var1 = new byte[this.anInt170 * this.anInt170];
        int var2 = 0;

        for (int var3 = 0; var3 < this.anInt170; ++var3) {
            for (int var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[0][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (int var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (int var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[0][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (int var3 = 0; var3 < this.anInt170; ++var3) {
            for (int var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[0][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (int var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (int var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[0][3] = var1;
    }

    int method121(int var1, int var2) {
        if (var2 == 9) {
            var1 = var1 + 1 & 3;
        }

        if (var2 == 10) {
            var1 = var1 + 3 & 3;
        }

        if (var2 == 11) {
            var1 = var1 + 3 & 3;
        }

        return var1;
    }

    void method122() {
        byte[] var1 = new byte[this.anInt170 * this.anInt170];
        int var2 = 0;

        int var3;
        int var4;
        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[1][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var2 >= 0 && var2 < var1.length) {
                    if (var4 >= var3 << 1) {
                        var1[var2] = -1;
                    }

                }
                ++var2;
            }
        }

        this.data[1][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[1][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[1][3] = var1;
    }

    int method127(int var1) {
        if (var1 != 9 && var1 != 10) {
            return var1 == 11 ? 8 : var1;
        }
        return 1;
    }

    void method123() {
        byte[] var1 = new byte[this.anInt170 * this.anInt170];
        int var2 = 0;

        int var3;
        int var4;
        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[2][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[2][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[2][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[2][3] = var1;
    }

    void method119() {
        byte[] var1 = new byte[this.anInt170 * this.anInt170];
        int var2 = 0;

        int var3;
        int var4;
        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[3][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[3][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[3][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[3][3] = var1;
    }

    void method128() {
        byte[] var1 = new byte[this.anInt170 * this.anInt170];
        int var2 = 0;

        int var3;
        int var4;
        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[4][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[4][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3 >> 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[4][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var2 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 << 1) {
                    var1[var2] = -1;
                }

                ++var2;
            }
        }

        this.data[4][3] = var1;
    }

    void method117() {
        byte[] var1;
        boolean var2 = false;
        var1 = new byte[this.anInt170 * this.anInt170];
        int var5 = 0;

        int var3;
        int var4;
        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[5][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var3 <= this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[5][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[5][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var3 >= this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[5][3] = var1;
    }

    void method116() {
        byte[] var1;
        boolean var2 = false;
        var1 = new byte[this.anInt170 * this.anInt170];
        int var5 = 0;

        int var3;
        int var4;
        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[6][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 <= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[6][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[6][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 <= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[6][3] = var1;
    }

    void method115() {
        byte[] var1;
        boolean var2 = false;
        var1 = new byte[this.anInt170 * this.anInt170];
        int var5 = 0;

        int var3;
        int var4;
        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[7][0] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = 0; var4 < this.anInt170; ++var4) {
                if (var4 >= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[7][1] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = this.anInt170 - 1; var3 >= 0; --var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[7][2] = var1;
        var1 = new byte[this.anInt170 * this.anInt170];
        var5 = 0;

        for (var3 = 0; var3 < this.anInt170; ++var3) {
            for (var4 = this.anInt170 - 1; var4 >= 0; --var4) {
                if (var4 >= var3 - this.anInt170 / 2) {
                    var1[var5] = -1;
                }

                ++var5;
            }
        }

        this.data[7][3] = var1;
    }

    void initialize() {
        if (this.data == null) {
            this.data = new byte[8][4][];
            this.method124();
            this.method122();
            this.method123();
            this.method119();
            this.method128();
            this.method117();
            this.method116();
            this.method115();
        }
    }

    public void method129(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        if (var7 != 0 && this.anInt170 != 0 && this.data != null) {
            var8 = this.method121(var8, var7);
            var7 = this.method127(var7);
            JagGraphics.method1361(var1, var2, var5, var6, var3, var4, this.data[var7 - 1][var8], this.anInt170);
        }
    }
}
