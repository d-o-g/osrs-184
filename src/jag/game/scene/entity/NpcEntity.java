package jag.game.scene.entity;

import jag.game.client;
import jag.game.type.AnimationSequence;
import jag.game.type.EffectAnimation;
import jag.game.type.NpcDefinition;
import jag.opcode.BitBuffer;

public final class NpcEntity extends PathingEntity {
    public static int port;
    public NpcDefinition definition;

    public NpcEntity() {

    }

    public static void update(boolean var0, BitBuffer buffer) {
        client.npcCount2 = 0;
        client.npcUpdateCount = 0;
        method541();

        int var3;
        NpcEntity var5;
        int var6;
        int var7;
        int var8;
        int var9;
        int var10;
        while (buffer.remaining(client.stream.incomingPacketSize) >= 27) {
            var3 = buffer.g(15);
            if (var3 == 32767) {
                break;
            }

            boolean createdNew = false;
            if (client.npcs[var3] == null) {
                client.npcs[var3] = new NpcEntity();
                createdNew = true;
            }

            var5 = client.npcs[var3];
            client.npcIndices[++client.npcCount - 1] = var3;
            var5.npcUpdateCycle = client.engineCycle;
            if (var0) {
                var6 = buffer.g(8);
                if (var6 > 127) {
                    var6 -= 256;
                }
            } else {
                var6 = buffer.g(5);
                if (var6 > 15) {
                    var6 -= 32;
                }
            }

            if (var0) {
                var7 = buffer.g(8);
                if (var7 > 127) {
                    var7 -= 256;
                }
            } else {
                var7 = buffer.g(5);
                if (var7 > 15) {
                    var7 -= 32;
                }
            }

            var8 = client.pathingEntityOrientations[buffer.g(3)];
            if (createdNew) {
                var5.orientation = var5.turnOrientation = var8;
            }

            var9 = buffer.g(1);
            if (var9 == 1) {
                client.processedNpcIndices[++client.npcUpdateCount - 1] = var3;
            }

            var10 = buffer.g(1);
            var5.definition = NpcDefinition.get(buffer.g(14));
            var5.boundSize = var5.definition.size;
            var5.rotation = var5.definition.rotation;
            if (var5.rotation == 0) {
                var5.turnOrientation = 0;
            }

            var5.walkStance = var5.definition.walkStance;
            var5.turnAroundStance = var5.definition.turnAroundStance;
            var5.walkLeftStance = var5.definition.walkLeftStance;
            var5.walkRightStance = var5.definition.walkRightStance;
            var5.idleStance = var5.definition.idleStance;
            var5.turnLeftStance = var5.definition.turnLeftStance;
            var5.turnRightStance = var5.definition.turnRightStance;
            var5.method683(PlayerEntity.local.pathXQueue[0] + var6, PlayerEntity.local.pathYQueue[0] + var7, var10 == 1);
        }

        buffer.byteAccess();

        int var15;
        for (var3 = 0; var3 < client.npcUpdateCount; ++var3) {
            var15 = client.processedNpcIndices[var3];
            var5 = client.npcs[var15];
            var6 = buffer.g1();
            if ((var6 & 16) != 0) {
                var7 = buffer.g2();
                if (var7 == 65535) {
                    var7 = -1;
                }

                var8 = buffer.method1074();
                if (var7 == var5.animation && var7 != -1) {
                    var9 = AnimationSequence.get(var7).replayMode;
                    if (var9 == 1) {
                        var5.animationFrame = 0;
                        var5.animationFrameCycle = 0;
                        var5.animationDelay = var8;
                        var5.animationLoopCounts = 0;
                    }

                    if (var9 == 2) {
                        var5.animationLoopCounts = 0;
                    }
                } else if (var7 == -1 || var5.animation == -1 || AnimationSequence.get(var7).priority >= AnimationSequence.get(var5.animation).priority) {
                    var5.animation = var7;
                    var5.animationFrame = 0;
                    var5.animationFrameCycle = 0;
                    var5.animationDelay = var8;
                    var5.animationLoopCounts = 0;
                    var5.anInt2023 = var5.pathQueueSize;
                }
            }

            if ((var6 & 1) != 0) {
                var7 = buffer.ig1();
                int var11;
                int var12;
                int var13;
                if (var7 > 0) {
                    for (var8 = 0; var8 < var7; ++var8) {
                        var10 = -1;
                        var11 = -1;
                        var12 = -1;
                        var9 = buffer.gsmarts();
                        if (var9 == 32767) {
                            var9 = buffer.gsmarts();
                            var11 = buffer.gsmarts();
                            var10 = buffer.gsmarts();
                            var12 = buffer.gsmarts();
                        } else if (var9 != 32766) {
                            var11 = buffer.gsmarts();
                        } else {
                            var9 = -1;
                        }

                        var13 = buffer.gsmarts();
                        var5.addHitSplat(var9, var11, var10, var12, client.engineCycle, var13);
                    }
                }

                var8 = buffer.method1074();
                if (var8 > 0) {
                    for (var9 = 0; var9 < var8; ++var9) {
                        var10 = buffer.gsmarts();
                        var11 = buffer.gsmarts();
                        if (var11 != 32767) {
                            var12 = buffer.gsmarts();
                            var13 = buffer.ig1();
                            int var14 = var11 > 0 ? buffer.ig1() : var13;
                            var5.updateHealthBar(var10, client.engineCycle, var11, var12, var13, var14);
                        } else {
                            var5.method1503(var10);
                        }
                    }
                }
            }

            if ((var6 & 2) != 0) {
                var5.targetIndex = buffer.method1055();
                if (var5.targetIndex == 65535) {
                    var5.targetIndex = -1;
                }
            }

            if ((var6 & 32) != 0) {
                var5.definition = NpcDefinition.get(buffer.method1060());
                var5.boundSize = var5.definition.size;
                var5.rotation = var5.definition.rotation;
                var5.walkStance = var5.definition.walkStance;
                var5.turnAroundStance = var5.definition.turnAroundStance;
                var5.walkLeftStance = var5.definition.walkLeftStance;
                var5.walkRightStance = var5.definition.walkRightStance;
                var5.idleStance = var5.definition.idleStance;
                var5.turnLeftStance = var5.definition.turnLeftStance;
                var5.turnRightStance = var5.definition.turnRightStance;
            }

            if ((var6 & 4) != 0) {
                var7 = buffer.method1055();
                var8 = buffer.method1060();
                var9 = var5.absoluteX - (var7 - client.baseX - client.baseX) * 64;
                var10 = var5.absoluteY - (var8 - client.baseY - client.baseY) * 64;
                if (var9 != 0 || var10 != 0) {
                    var5.transformedOrientation = (int) (Math.atan2(var9, var10) * 325.949D) & 2047;
                }
            }

            if ((var6 & 8) != 0) {
                var5.effect = buffer.method1060();
                var7 = buffer.method1019();
                var5.anInt2014 = var7 >> 16;
                var5.effectDelay = (var7 & 65535) + client.engineCycle;
                var5.effectFrame = 0;
                var5.effectFrameCycle = 0;
                if (var5.effectDelay > client.engineCycle) {
                    var5.effectFrame = -1;
                }

                if (var5.effect == 65535) {
                    var5.effect = -1;
                }
            }

            if ((var6 & 64) != 0) {
                var5.overheadText = buffer.gstr();
                var5.overheadTextCyclesLeft = 100;
            }
        }

        for (var3 = 0; var3 < client.npcCount2; ++var3) {
            var15 = client.npcIndices2[var3];
            if (client.npcs[var15].npcUpdateCycle != client.engineCycle) {
                client.npcs[var15].definition = null;
                client.npcs[var15] = null;
            }
        }

        if (buffer.pos != client.stream.incomingPacketSize) {
            throw new RuntimeException(buffer.pos + "," + client.stream.incomingPacketSize);
        }
        for (var3 = 0; var3 < client.npcCount; ++var3) {
            if (client.npcs[client.npcIndices[var3]] == null) {
                throw new RuntimeException(var3 + "," + client.npcCount);
            }
        }

    }

    public static void method541() {
        BitBuffer var0 = client.stream.inbuffer;
        var0.bitAccess();
        int var1 = var0.g(8);
        if (var1 < client.npcCount) {
            for (int var2 = var1; var2 < client.npcCount; ++var2) {
                client.npcIndices2[++client.npcCount2 - 1] = client.npcIndices[var2];
            }
        }

        if (var1 > client.npcCount) {
            throw new RuntimeException("");
        }

        client.npcCount = 0;

        for (int var2 = 0; var2 < var1; ++var2) {
            int var3 = client.npcIndices[var2];
            NpcEntity var4 = client.npcs[var3];
            int var5 = var0.g(1);
            if (var5 == 0) {
                client.npcIndices[++client.npcCount - 1] = var3;
                var4.npcUpdateCycle = client.engineCycle;
            } else {
                int var6 = var0.g(2);
                if (var6 == 0) {
                    client.npcIndices[++client.npcCount - 1] = var3;
                    var4.npcUpdateCycle = client.engineCycle;
                    client.processedNpcIndices[++client.npcUpdateCount - 1] = var3;
                } else {
                    int var7;
                    int var8;
                    if (var6 == 1) {
                        client.npcIndices[++client.npcCount - 1] = var3;
                        var4.npcUpdateCycle = client.engineCycle;
                        var7 = var0.g(3);
                        var4.method682(var7, (byte) 1);
                        var8 = var0.g(1);
                        if (var8 == 1) {
                            client.processedNpcIndices[++client.npcUpdateCount - 1] = var3;
                        }
                    } else if (var6 == 2) {
                        client.npcIndices[++client.npcCount - 1] = var3;
                        var4.npcUpdateCycle = client.engineCycle;
                        var7 = var0.g(3);
                        var4.method682(var7, (byte) 2);
                        var8 = var0.g(3);
                        var4.method682(var8, (byte) 2);
                        int var9 = var0.g(1);
                        if (var9 == 1) {
                            client.processedNpcIndices[++client.npcUpdateCount - 1] = var3;
                        }
                    } else if (var6 == 3) {
                        client.npcIndices2[++client.npcCount2 - 1] = var3;
                    }
                }
            }
        }

    }

    protected Model getModel() {
        if (definition == null) {
            return null;
        }
        AnimationSequence anim = super.animation != -1 && super.animationDelay == 0 ? AnimationSequence.get(super.animation) : null;
        AnimationSequence stance = super.stance != -1 && (super.stance != super.idleStance || anim == null) ? AnimationSequence.get(super.stance) : null;
        Model var3 = definition.getModel(anim, super.animationFrame, stance, super.stanceFrame);
        if (var3 == null) {
            return null;
        }
        var3.computeBounds();
        super.modelHeight = var3.height;
        if (super.effect != -1 && super.effectFrame != -1) {
            Model var4 = EffectAnimation.get(super.effect).method1004(super.effectFrame);
            if (var4 != null) {
                var4.offsetBy(0, -super.anInt2014, 0);
                Model[] var5 = new Model[]{var3, var4};
                var3 = new Model(var5, 2);
            }
        }

        if (definition.size == 1) {
            var3.fitsSingleTile = true;
        }

        return var3;
    }

    public void method682(int var1, byte var2) {
        int var3 = super.pathXQueue[0];
        int var4 = super.pathYQueue[0];
        if (var1 == 0) {
            --var3;
            ++var4;
        }

        if (var1 == 1) {
            ++var4;
        }

        if (var1 == 2) {
            ++var3;
            ++var4;
        }

        if (var1 == 3) {
            --var3;
        }

        if (var1 == 4) {
            ++var3;
        }

        if (var1 == 5) {
            --var3;
            --var4;
        }

        if (var1 == 6) {
            --var4;
        }

        if (var1 == 7) {
            ++var3;
            --var4;
        }

        if (super.animation != -1 && AnimationSequence.get(super.animation).walkingPrecedence == 1) {
            super.animation = -1;
        }

        if (super.pathQueueSize < 9) {
            ++super.pathQueueSize;
        }

        for (int var5 = super.pathQueueSize; var5 > 0; --var5) {
            super.pathXQueue[var5] = super.pathXQueue[var5 - 1];
            super.pathYQueue[var5] = super.pathYQueue[var5 - 1];
            super.pathQueueTraversed[var5] = super.pathQueueTraversed[var5 - 1];
        }

        super.pathXQueue[0] = var3;
        super.pathYQueue[0] = var4;
        super.pathQueueTraversed[0] = var2;
    }

    public void method683(int var1, int var2, boolean var3) {
        if (super.animation != -1 && AnimationSequence.get(super.animation).walkingPrecedence == 1) {
            super.animation = -1;
        }

        if (!var3) {
            int var4 = var1 - super.pathXQueue[0];
            int var5 = var2 - super.pathYQueue[0];
            if (var4 >= -8 && var4 <= 8 && var5 >= -8 && var5 <= 8) {
                if (super.pathQueueSize < 9) {
                    ++super.pathQueueSize;
                }

                for (int var6 = super.pathQueueSize; var6 > 0; --var6) {
                    super.pathXQueue[var6] = super.pathXQueue[var6 - 1];
                    super.pathYQueue[var6] = super.pathYQueue[var6 - 1];
                    super.pathQueueTraversed[var6] = super.pathQueueTraversed[var6 - 1];
                }

                super.pathXQueue[0] = var1;
                super.pathYQueue[0] = var2;
                super.pathQueueTraversed[0] = 1;
                return;
            }
        }

        super.pathQueueSize = 0;
        super.anInt2023 = 0;
        super.anInt2022 = 0;
        super.pathXQueue[0] = var1;
        super.pathYQueue[0] = var2;
        super.absoluteX = super.pathXQueue[0] * 128 + super.boundSize;
        super.absoluteY = super.pathYQueue[0] * 128 + super.boundSize;
    }

    public boolean isDefined() {
        return definition != null;
    }
}
