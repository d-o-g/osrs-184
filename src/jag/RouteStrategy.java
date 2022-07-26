package jag;

import jag.game.relationship.AssociateComparator_Sub4;
import jag.game.scene.CollisionMap;

public abstract class RouteStrategy {

    public static final int[][] prev = new int[128][128];
    public static final int[][] dist = new int[128][128];
    public static final int[] pathX = new int[4096];
    public static final int[] pathY = new int[4096];

    public static int anInt858;

    public int destinationX;
    public int destinationY;
    public int destinationSizeX;
    public int destinationSizeY;

    protected RouteStrategy() {

    }

    public static boolean findPathRickkSize(int destX, int destY, RouteStrategy strategy, CollisionMap collision) {
        int currentX = destX;
        int currentY = destY;

        byte var6 = 64;
        byte var7 = 64;

        int diffX = destX - var6;
        int diffY = destY - var7;

        prev[var6][var7] = 99;
        dist[var6][var7] = 0;

        byte var10 = 0;
        int step = 0;

        pathX[var10] = destX;
        pathY[var10] = destY;

        int path = var10 + 1;
        int[][] flags = collision.flags;
        while (step != path) {
            currentX = pathX[step];
            currentY = pathY[step];
            step = step + 1 & 4095;
            int var16 = currentX - diffX;
            int var17 = currentY - diffY;
            int var13 = currentX - collision.insetX;
            int var14 = currentY - collision.insetY;
            if (strategy.isDestination(currentX, currentY)) {
                AssociateComparator_Sub4.anInt803 = currentX;
                anInt858 = currentY;
                return true;
            }

            int var15 = dist[var16][var17] + 1;
            if (var16 > 0 && prev[var16 - 1][var17] == 0 && (flags[var13 - 1][var14] & 19136782) == 0 && (flags[var13 - 1][var14 + 1] & 19136824) == 0) {
                pathX[path] = currentX - 1;
                pathY[path] = currentY;
                path = path + 1 & 4095;
                prev[var16 - 1][var17] = 2;
                dist[var16 - 1][var17] = var15;
            }

            if (var16 < 126 && prev[var16 + 1][var17] == 0 && (flags[var13 + 2][var14] & 19136899) == 0 && (flags[var13 + 2][var14 + 1] & 19136992) == 0) {
                pathX[path] = currentX + 1;
                pathY[path] = currentY;
                path = path + 1 & 4095;
                prev[var16 + 1][var17] = 8;
                dist[var16 + 1][var17] = var15;
            }

            if (var17 > 0 && prev[var16][var17 - 1] == 0 && (flags[var13][var14 - 1] & 19136782) == 0 && (flags[var13 + 1][var14 - 1] & 19136899) == 0) {
                pathX[path] = currentX;
                pathY[path] = currentY - 1;
                path = path + 1 & 4095;
                prev[var16][var17 - 1] = 1;
                dist[var16][var17 - 1] = var15;
            }

            if (var17 < 126 && prev[var16][var17 + 1] == 0 && (flags[var13][var14 + 2] & 19136824) == 0 && (flags[var13 + 1][var14 + 2] & 19136992) == 0) {
                pathX[path] = currentX;
                pathY[path] = currentY + 1;
                path = path + 1 & 4095;
                prev[var16][var17 + 1] = 4;
                dist[var16][var17 + 1] = var15;
            }

            if (var16 > 0 && var17 > 0 && prev[var16 - 1][var17 - 1] == 0 && (flags[var13 - 1][var14] & 19136830) == 0 && (flags[var13 - 1][var14 - 1] & 19136782) == 0 && (flags[var13][var14 - 1] & 19136911) == 0) {
                pathX[path] = currentX - 1;
                pathY[path] = currentY - 1;
                path = path + 1 & 4095;
                prev[var16 - 1][var17 - 1] = 3;
                dist[var16 - 1][var17 - 1] = var15;
            }

            if (var16 < 126 && var17 > 0 && prev[var16 + 1][var17 - 1] == 0 && (flags[var13 + 1][var14 - 1] & 19136911) == 0 && (flags[var13 + 2][var14 - 1] & 19136899) == 0 && (flags[var13 + 2][var14] & 19136995) == 0) {
                pathX[path] = currentX + 1;
                pathY[path] = currentY - 1;
                path = path + 1 & 4095;
                prev[var16 + 1][var17 - 1] = 9;
                dist[var16 + 1][var17 - 1] = var15;
            }

            if (var16 > 0 && var17 < 126 && prev[var16 - 1][var17 + 1] == 0 && (flags[var13 - 1][var14 + 1] & 19136830) == 0 && (flags[var13 - 1][var14 + 2] & 19136824) == 0 && (flags[var13][var14 + 2] & 19137016) == 0) {
                pathX[path] = currentX - 1;
                pathY[path] = currentY + 1;
                path = path + 1 & 4095;
                prev[var16 - 1][var17 + 1] = 6;
                dist[var16 - 1][var17 + 1] = var15;
            }

            if (var16 < 126 && var17 < 126 && prev[var16 + 1][var17 + 1] == 0 && (flags[var13 + 1][var14 + 2] & 19137016) == 0 && (flags[var13 + 2][var14 + 2] & 19136992) == 0 && (flags[var13 + 2][var14 + 1] & 19136995) == 0) {
                pathX[path] = currentX + 1;
                pathY[path] = currentY + 1;
                path = path + 1 & 4095;
                prev[var16 + 1][var17 + 1] = 12;
                dist[var16 + 1][var17 + 1] = var15;
            }
        }

        AssociateComparator_Sub4.anInt803 = currentX;
        anInt858 = currentY;
        return false;
    }

    public static boolean findPathSphiinxSize(int var0, int var1, int var2, RouteStrategy var3, CollisionMap var4) {
        int var5 = var0;
        int var6 = var1;
        byte var7 = 64;
        byte var8 = 64;
        int var9 = var0 - var7;
        int var10 = var1 - var8;
        prev[var7][var8] = 99;
        dist[var7][var8] = 0;
        byte var11 = 0;
        int var12 = 0;
        pathX[var11] = var0;
        int var20 = var11 + 1;
        pathY[var11] = var1;
        int[][] var13 = var4.flags;

        while (true) {
            label303:
            while (true) {
                int var14;
                int var15;
                int var16;
                int var17;
                int var18;
                int var19;
                do {
                    do {
                        do {
                            label280:
                            do {
                                if (var12 == var20) {
                                    AssociateComparator_Sub4.anInt803 = var5;
                                    anInt858 = var6;
                                    return false;
                                }

                                var5 = pathX[var12];
                                var6 = pathY[var12];
                                var12 = var12 + 1 & 4095;
                                var18 = var5 - var9;
                                var19 = var6 - var10;
                                var14 = var5 - var4.insetX;
                                var15 = var6 - var4.insetY;
                                if (var3.isDestination(var5, var6)) {
                                    AssociateComparator_Sub4.anInt803 = var5;
                                    anInt858 = var6;
                                    return true;
                                }

                                var16 = dist[var18][var19] + 1;
                                if (var18 > 0 && prev[var18 - 1][var19] == 0 && (var13[var14 - 1][var15] & 19136782) == 0 && (var13[var14 - 1][var15 + var2 - 1] & 19136824) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2 - 1) {
                                            pathX[var20] = var5 - 1;
                                            pathY[var20] = var6;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18 - 1][var19] = 2;
                                            dist[var18 - 1][var19] = var16;
                                            break;
                                        }

                                        if ((var13[var14 - 1][var15 + var17] & 19136830) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var18 < 128 - var2 && prev[var18 + 1][var19] == 0 && (var13[var14 + var2][var15] & 19136899) == 0 && (var13[var14 + var2][var15 + var2 - 1] & 19136992) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2 - 1) {
                                            pathX[var20] = var5 + 1;
                                            pathY[var20] = var6;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18 + 1][var19] = 8;
                                            dist[var18 + 1][var19] = var16;
                                            break;
                                        }

                                        if ((var13[var14 + var2][var17 + var15] & 19136995) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var19 > 0 && prev[var18][var19 - 1] == 0 && (var13[var14][var15 - 1] & 19136782) == 0 && (var13[var14 + var2 - 1][var15 - 1] & 19136899) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2 - 1) {
                                            pathX[var20] = var5;
                                            pathY[var20] = var6 - 1;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18][var19 - 1] = 1;
                                            dist[var18][var19 - 1] = var16;
                                            break;
                                        }

                                        if ((var13[var17 + var14][var15 - 1] & 19136911) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var19 < 128 - var2 && prev[var18][var19 + 1] == 0 && (var13[var14][var15 + var2] & 19136824) == 0 && (var13[var14 + var2 - 1][var15 + var2] & 19136992) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2 - 1) {
                                            pathX[var20] = var5;
                                            pathY[var20] = var6 + 1;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18][var19 + 1] = 4;
                                            dist[var18][var19 + 1] = var16;
                                            break;
                                        }

                                        if ((var13[var14 + var17][var15 + var2] & 19137016) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var18 > 0 && var19 > 0 && prev[var18 - 1][var19 - 1] == 0 && (var13[var14 - 1][var15 - 1] & 19136782) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2) {
                                            pathX[var20] = var5 - 1;
                                            pathY[var20] = var6 - 1;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18 - 1][var19 - 1] = 3;
                                            dist[var18 - 1][var19 - 1] = var16;
                                            break;
                                        }

                                        if ((var13[var14 - 1][var17 + (var15 - 1)] & 19136830) != 0 || (var13[var17 + (var14 - 1)][var15 - 1] & 19136911) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var18 < 128 - var2 && var19 > 0 && prev[var18 + 1][var19 - 1] == 0 && (var13[var14 + var2][var15 - 1] & 19136899) == 0) {
                                    var17 = 1;

                                    while (true) {
                                        if (var17 >= var2) {
                                            pathX[var20] = var5 + 1;
                                            pathY[var20] = var6 - 1;
                                            var20 = var20 + 1 & 4095;
                                            prev[var18 + 1][var19 - 1] = 9;
                                            dist[var18 + 1][var19 - 1] = var16;
                                            break;
                                        }

                                        if ((var13[var14 + var2][var17 + (var15 - 1)] & 19136995) != 0 || (var13[var17 + var14][var15 - 1] & 19136911) != 0) {
                                            break;
                                        }

                                        ++var17;
                                    }
                                }

                                if (var18 > 0 && var19 < 128 - var2 && prev[var18 - 1][var19 + 1] == 0 && (var13[var14 - 1][var15 + var2] & 19136824) == 0) {
                                    for (var17 = 1; var17 < var2; ++var17) {
                                        if ((var13[var14 - 1][var17 + var15] & 19136830) != 0 || (var13[var17 + (var14 - 1)][var15 + var2] & 19137016) != 0) {
                                            continue label280;
                                        }
                                    }

                                    pathX[var20] = var5 - 1;
                                    pathY[var20] = var6 + 1;
                                    var20 = var20 + 1 & 4095;
                                    prev[var18 - 1][var19 + 1] = 6;
                                    dist[var18 - 1][var19 + 1] = var16;
                                }
                            } while (var18 >= 128 - var2);
                        } while (var19 >= 128 - var2);
                    } while (prev[var18 + 1][var19 + 1] != 0);
                } while ((var13[var14 + var2][var15 + var2] & 19136992) != 0);

                for (var17 = 1; var17 < var2; ++var17) {
                    if ((var13[var17 + var14][var15 + var2] & 19137016) != 0 || (var13[var14 + var2][var15 + var17] & 19136995) != 0) {
                        continue label303;
                    }
                }

                pathX[var20] = var5 + 1;
                pathY[var20] = var6 + 1;
                var20 = var20 + 1 & 4095;
                prev[var18 + 1][var19 + 1] = 12;
                dist[var18 + 1][var19 + 1] = var16;
            }
        }
    }

    public static boolean findPathGengSize(int var0, int var1, RouteStrategy var2, CollisionMap var3) {
        int var4 = var0;
        int var5 = var1;
        byte var6 = 64;
        byte var7 = 64;
        int var8 = var0 - var6;
        int var9 = var1 - var7;
        prev[var6][var7] = 99;
        dist[var6][var7] = 0;
        byte var10 = 0;
        int var11 = 0;
        pathX[var10] = var0;
        int var18 = var10 + 1;
        pathY[var10] = var1;
        int[][] var12 = var3.flags;

        while (var11 != var18) {
            var4 = pathX[var11];
            var5 = pathY[var11];
            var11 = var11 + 1 & 4095;
            int var16 = var4 - var8;
            int var17 = var5 - var9;
            int var13 = var4 - var3.insetX;
            int var14 = var5 - var3.insetY;
            if (var2.isDestination(var4, var5)) {
                AssociateComparator_Sub4.anInt803 = var4;
                anInt858 = var5;
                return true;
            }

            int var15 = dist[var16][var17] + 1;
            if (var16 > 0 && prev[var16 - 1][var17] == 0 && (var12[var13 - 1][var14] & 19136776) == 0) {
                pathX[var18] = var4 - 1;
                pathY[var18] = var5;
                var18 = var18 + 1 & 4095;
                prev[var16 - 1][var17] = 2;
                dist[var16 - 1][var17] = var15;
            }

            if (var16 < 127 && prev[var16 + 1][var17] == 0 && (var12[var13 + 1][var14] & 19136896) == 0) {
                pathX[var18] = var4 + 1;
                pathY[var18] = var5;
                var18 = var18 + 1 & 4095;
                prev[var16 + 1][var17] = 8;
                dist[var16 + 1][var17] = var15;
            }

            if (var17 > 0 && prev[var16][var17 - 1] == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
                pathX[var18] = var4;
                pathY[var18] = var5 - 1;
                var18 = var18 + 1 & 4095;
                prev[var16][var17 - 1] = 1;
                dist[var16][var17 - 1] = var15;
            }

            if (var17 < 127 && prev[var16][var17 + 1] == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
                pathX[var18] = var4;
                pathY[var18] = var5 + 1;
                var18 = var18 + 1 & 4095;
                prev[var16][var17 + 1] = 4;
                dist[var16][var17 + 1] = var15;
            }

            if (var16 > 0 && var17 > 0 && prev[var16 - 1][var17 - 1] == 0 && (var12[var13 - 1][var14 - 1] & 19136782) == 0 && (var12[var13 - 1][var14] & 19136776) == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
                pathX[var18] = var4 - 1;
                pathY[var18] = var5 - 1;
                var18 = var18 + 1 & 4095;
                prev[var16 - 1][var17 - 1] = 3;
                dist[var16 - 1][var17 - 1] = var15;
            }

            if (var16 < 127 && var17 > 0 && prev[var16 + 1][var17 - 1] == 0 && (var12[var13 + 1][var14 - 1] & 19136899) == 0 && (var12[var13 + 1][var14] & 19136896) == 0 && (var12[var13][var14 - 1] & 19136770) == 0) {
                pathX[var18] = var4 + 1;
                pathY[var18] = var5 - 1;
                var18 = var18 + 1 & 4095;
                prev[var16 + 1][var17 - 1] = 9;
                dist[var16 + 1][var17 - 1] = var15;
            }

            if (var16 > 0 && var17 < 127 && prev[var16 - 1][var17 + 1] == 0 && (var12[var13 - 1][var14 + 1] & 19136824) == 0 && (var12[var13 - 1][var14] & 19136776) == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
                pathX[var18] = var4 - 1;
                pathY[var18] = var5 + 1;
                var18 = var18 + 1 & 4095;
                prev[var16 - 1][var17 + 1] = 6;
                dist[var16 - 1][var17 + 1] = var15;
            }

            if (var16 < 127 && var17 < 127 && prev[var16 + 1][var17 + 1] == 0 && (var12[var13 + 1][var14 + 1] & 19136992) == 0 && (var12[var13 + 1][var14] & 19136896) == 0 && (var12[var13][var14 + 1] & 19136800) == 0) {
                pathX[var18] = var4 + 1;
                pathY[var18] = var5 + 1;
                var18 = var18 + 1 & 4095;
                prev[var16 + 1][var17 + 1] = 12;
                dist[var16 + 1][var17 + 1] = var15;
            }
        }

        AssociateComparator_Sub4.anInt803 = var4;
        anInt858 = var5;
        return false;
    }

    public static int getPathLength(int x, int y, int size, RouteStrategy strategy, CollisionMap collision, int[] pathX, int[] pathY) {
        for (int foundX = 0; foundX < 128; ++foundX) {
            for (int foundY = 0; foundY < 128; ++foundY) {
                prev[foundX][foundY] = 0;
                dist[foundX][foundY] = 99999999;
            }
        }

        boolean foundPath;
        if (size == 1) {
            foundPath = findPathGengSize(x, y, strategy, collision);
        } else if (size == 2) {
            foundPath = findPathRickkSize(x, y, strategy, collision);
        } else {
            foundPath = findPathSphiinxSize(x, y, size, strategy, collision);
        }

        int var9 = x - 64;
        int var10 = y - 64;
        int var11 = AssociateComparator_Sub4.anInt803;
        int var12 = anInt858;
        int var13;
        int var14;
        int var16;
        if (!foundPath) {
            var13 = Integer.MAX_VALUE;
            var14 = Integer.MAX_VALUE;
            byte var15 = 10;
            var16 = strategy.destinationX;
            int var17 = strategy.destinationY;
            int var18 = strategy.destinationSizeX;
            int var19 = strategy.destinationSizeY;

            for (int var20 = var16 - var15; var20 <= var16 + var15; ++var20) {
                for (int var21 = var17 - var15; var21 <= var15 + var17; ++var21) {
                    int var22 = var20 - var9;
                    int var23 = var21 - var10;
                    if (var22 >= 0 && var23 >= 0 && var22 < 128 && var23 < 128 && dist[var22][var23] < 100) {
                        int var24 = 0;
                        if (var20 < var16) {
                            var24 = var16 - var20;
                        } else if (var20 > var16 + var18 - 1) {
                            var24 = var20 - (var18 + var16 - 1);
                        }

                        int var25 = 0;
                        if (var21 < var17) {
                            var25 = var17 - var21;
                        } else if (var21 > var19 + var17 - 1) {
                            var25 = var21 - (var19 + var17 - 1);
                        }

                        int var26 = var25 * var25 + var24 * var24;
                        if (var26 < var13 || var26 == var13 && dist[var22][var23] < var14) {
                            var13 = var26;
                            var14 = dist[var22][var23];
                            var11 = var20;
                            var12 = var21;
                        }
                    }
                }
            }

            if (var13 == Integer.MAX_VALUE) {
                return -1;
            }
        }

        if (x == var11 && var12 == y) {
            return 0;
        }
        byte var28 = 0;
        RouteStrategy.pathX[var28] = var11;
        var13 = var28 + 1;
        RouteStrategy.pathY[var28] = var12;

        int var29;
        for (var14 = var29 = prev[var11 - var9][var12 - var10]; x != var11 || var12 != y; var14 = prev[var11 - var9][var12 - var10]) {
            if (var29 != var14) {
                var29 = var14;
                RouteStrategy.pathX[var13] = var11;
                RouteStrategy.pathY[var13++] = var12;
            }

            if ((var14 & 2) != 0) {
                ++var11;
            } else if ((var14 & 8) != 0) {
                --var11;
            }

            if ((var14 & 1) != 0) {
                ++var12;
            } else if ((var14 & 4) != 0) {
                --var12;
            }
        }

        var16 = 0;

        while (var13-- > 0) {
            pathX[var16] = RouteStrategy.pathX[var13];
            pathY[var16++] = RouteStrategy.pathY[var13];
            if (var16 >= pathX.length) {
                break;
            }
        }

        return var16;
    }

    public abstract boolean isDestination(int x, int y);
}
