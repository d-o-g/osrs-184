package jag.game.scene.entity;

import jag.commons.collection.LinkedList;
import jag.game.*;
import jag.game.scene.HintArrow;
import jag.game.type.*;
import jag.graphics.*;
import jag.opcode.Buffer;
import jag.opcode.GPI;
import jag.worldmap.WorldMapChunkDefinition;

public abstract class PathingEntity extends Entity {

    public final int[] pathXQueue;
    public final int[] hitsplatCycles;
    public final int[] pathYQueue;
    public final int[] hitsplats;
    public final int[] hitsplatTypes;
    public final int[] specialHitsplatTypes;
    public final int[] specialHitsplats;

    public final byte[] pathQueueTraversed; //this is now in its own class

    public final LinkedList<HealthBar> healthBars;

    public String overheadText;

    public boolean autoChatting;
    public boolean aBoolean2015;
    public boolean aBoolean2005;
    public boolean stretch;

    public int animation;
    public int pathQueueSize;
    public int transformedOrientation;
    public int anInt2023;
    public int anInt2022;
    public int animationDelay;
    public int stance;
    public int boundSize;
    public int idleStance;
    public int animationFrame;
    public int turnLeftStance;
    public int stanceFrame;
    public int turnRightStance;
    public int walkStance;
    public int turnAroundStance;
    public int absoluteX;
    public int walkLeftStance;
    public int walkRightStance;
    public int runStance;
    public int modelHeight;
    public int absoluteY;
    public int effect;
    public int effectFrame;
    public int overheadTextCyclesLeft;
    public int overheadTextForeground;
    public int overheadTextEffect;
    public int npcUpdateCycle;
    public int anInt2014;
    public int targetIndex;
    public int animationFrameCycle;
    public int animationLoopCounts;
    public int orientation;
    public int turnOrientation;
    public int stanceFrameCycle;
    public int rotation;
    public int effectFrameCycle;
    public int effectDelay;
    public int anInt2018;
    public int renderCycle;
    public int forceMovementStartCycle;
    public int forceMovementEndCycle;
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public int anInt2019;

    public byte hitsplatCount;

    public PathingEntity() {
        stretch = false;
        boundSize = -1;
        idleStance = -1;
        turnLeftStance = -1;
        turnRightStance = -1;
        walkStance = -1;
        turnAroundStance = -1;
        walkLeftStance = -1;
        walkRightStance = -1;
        runStance = -1;
        overheadText = null;
        aBoolean2005 = false;
        overheadTextCyclesLeft = 100;
        overheadTextForeground = 0;
        overheadTextEffect = 0;
        hitsplatCount = 0;
        hitsplatTypes = new int[4];
        hitsplats = new int[4];
        hitsplatCycles = new int[4];
        specialHitsplatTypes = new int[4];
        specialHitsplats = new int[4];
        healthBars = new LinkedList<>();
        targetIndex = -1;
        aBoolean2015 = false;
        transformedOrientation = -1;
        stance = -1;
        stanceFrame = 0;
        stanceFrameCycle = 0;
        animation = -1;
        animationFrame = 0;
        animationFrameCycle = 0;
        animationDelay = 0;
        animationLoopCounts = 0;
        effect = -1;
        effectFrame = 0;
        effectFrameCycle = 0;
        npcUpdateCycle = 0;
        modelHeight = 200;
        anInt2018 = 0;
        rotation = 32;
        pathQueueSize = 0;
        pathXQueue = new int[10];
        pathYQueue = new int[10];
        pathQueueTraversed = new byte[10];
        anInt2022 = 0;
        anInt2023 = 0;
    }

    public static void update(PathingEntity entity) {
        AnimationSequence animation;
        if (entity.forceMovementStartCycle > client.ticks) {
            processMovementStartSequence(entity);
        } else if (entity.forceMovementEndCycle >= client.ticks) {
            processMovementEndSequence(entity);
        } else {
            entity.stance = entity.idleStance;
            if (entity.pathQueueSize == 0) {
                entity.anInt2022 = 0;
            } else {
                label548:
                {
                    if (entity.animation != -1 && entity.animationDelay == 0) {
                        animation = AnimationSequence.get(entity.animation);
                        if (entity.anInt2023 > 0 && animation.animatingPrecedence == 0) {
                            ++entity.anInt2022;
                            break label548;
                        }

                        if (entity.anInt2023 <= 0 && animation.walkingPrecedence == 0) {
                            ++entity.anInt2022;
                            break label548;
                        }
                    }

                    int var3 = entity.absoluteX;
                    int var5 = entity.absoluteY;
                    int var6 = entity.pathXQueue[entity.pathQueueSize - 1] * 128 + entity.boundSize;
                    int var7 = entity.pathYQueue[entity.pathQueueSize - 1] * 128 + entity.boundSize;
                    if (var3 < var6) {
                        if (var5 < var7) {
                            entity.orientation = 1280;
                        } else if (var5 > var7) {
                            entity.orientation = 1792;
                        } else {
                            entity.orientation = 1536;
                        }
                    } else if (var3 > var6) {
                        if (var5 < var7) {
                            entity.orientation = 768;
                        } else if (var5 > var7) {
                            entity.orientation = 256;
                        } else {
                            entity.orientation = 512;
                        }
                    } else if (var5 < var7) {
                        entity.orientation = 1024;
                    } else if (var5 > var7) {
                        entity.orientation = 0;
                    }

                    byte var8 = entity.pathQueueTraversed[entity.pathQueueSize - 1];
                    if (var6 - var3 <= 256 && var6 - var3 >= -256 && var7 - var5 <= 256 && var7 - var5 >= -256) {
                        int var9 = entity.orientation - entity.turnOrientation & 2047;
                        if (var9 > 1024) {
                            var9 -= 2048;
                        }

                        int var10 = entity.turnAroundStance;
                        if (var9 >= -256 && var9 <= 256) {
                            var10 = entity.walkStance;
                        } else if (var9 >= 256 && var9 < 768) {
                            var10 = entity.walkRightStance;
                        } else if (var9 >= -768 && var9 <= -256) {
                            var10 = entity.walkLeftStance;
                        }

                        if (var10 == -1) {
                            var10 = entity.walkStance;
                        }

                        entity.stance = var10;
                        int var11 = 4;
                        boolean var12 = true;
                        if (entity instanceof NpcEntity) {
                            var12 = ((NpcEntity) entity).definition.clickable;
                        }

                        if (var12) {
                            if (entity.turnOrientation != entity.orientation && entity.targetIndex == -1 && entity.rotation != 0) {
                                var11 = 2;
                            }

                            if (entity.pathQueueSize > 2) {
                                var11 = 6;
                            }

                            if (entity.pathQueueSize > 3) {
                                var11 = 8;
                            }

                        } else {
                            if (entity.pathQueueSize > 1) {
                                var11 = 6;
                            }

                            if (entity.pathQueueSize > 2) {
                                var11 = 8;
                            }

                        }
                        if (entity.anInt2022 > 0 && entity.pathQueueSize > 1) {
                            var11 = 8;
                            --entity.anInt2022;
                        }

                        if (var8 == 2) {
                            var11 <<= 1;
                        }

                        if (var11 >= 8 && entity.walkStance == entity.stance && entity.runStance != -1) {
                            entity.stance = entity.runStance;
                        }

                        if (var3 != var6 || var5 != var7) {
                            if (var3 < var6) {
                                entity.absoluteX += var11;
                                if (entity.absoluteX > var6) {
                                    entity.absoluteX = var6;
                                }
                            } else if (var3 > var6) {
                                entity.absoluteX -= var11;
                                if (entity.absoluteX < var6) {
                                    entity.absoluteX = var6;
                                }
                            }

                            if (var5 < var7) {
                                entity.absoluteY += var11;
                                if (entity.absoluteY > var7) {
                                    entity.absoluteY = var7;
                                }
                            } else if (var5 > var7) {
                                entity.absoluteY -= var11;
                                if (entity.absoluteY < var7) {
                                    entity.absoluteY = var7;
                                }
                            }
                        }

                        if (var6 == entity.absoluteX && var7 == entity.absoluteY) {
                            --entity.pathQueueSize;
                            if (entity.anInt2023 > 0) {
                                --entity.anInt2023;
                            }
                        }
                    } else {
                        entity.absoluteX = var6;
                        entity.absoluteY = var7;
                        --entity.pathQueueSize;
                        if (entity.anInt2023 > 0) {
                            --entity.anInt2023;
                        }
                    }
                }
            }
        }

        if (entity.absoluteX < 128 || entity.absoluteY < 128 || entity.absoluteX >= 13184 || entity.absoluteY >= 13184) {
            entity.animation = -1;
            entity.effect = -1;
            entity.forceMovementStartCycle = 0;
            entity.forceMovementEndCycle = 0;
            entity.absoluteX = entity.pathXQueue[0] * 128 + entity.boundSize;
            entity.absoluteY = entity.boundSize + entity.pathYQueue[0] * 128;
            entity.method1504();
        }

        if (PlayerEntity.local == entity && (entity.absoluteX < 1536 || entity.absoluteY < 1536 || entity.absoluteX >= 11776 || entity.absoluteY >= 11776)) {
            entity.animation = -1;
            entity.effect = -1;
            entity.forceMovementStartCycle = 0;
            entity.forceMovementEndCycle = 0;
            entity.absoluteX = entity.boundSize + entity.pathXQueue[0] * 128;
            entity.absoluteY = entity.boundSize + entity.pathYQueue[0] * 128;
            entity.method1504();
        }

        if (entity.rotation != 0) {
            if (entity.targetIndex != -1) {
                PathingEntity var13 = null;
                if (entity.targetIndex < 32768) {
                    var13 = client.npcs[entity.targetIndex];
                } else if (entity.targetIndex >= 32768) {
                    var13 = client.players[entity.targetIndex - 32768];
                }

                if (var13 != null) {
                    int var5 = entity.absoluteX - var13.absoluteX;
                    int var6 = entity.absoluteY - var13.absoluteY;
                    if (var5 != 0 || var6 != 0) {
                        entity.orientation = (int) (Math.atan2(var5, var6) * 325.949D) & 2047;
                    }
                } else if (entity.aBoolean2015) {
                    entity.targetIndex = -1;
                    entity.aBoolean2015 = false;
                }
            }

            if (entity.transformedOrientation != -1 && (entity.pathQueueSize == 0 || entity.anInt2022 > 0)) {
                entity.orientation = entity.transformedOrientation;
                entity.transformedOrientation = -1;
            }

            int var3 = entity.orientation - entity.turnOrientation & 2047;
            if (var3 == 0 && entity.aBoolean2015) {
                entity.targetIndex = -1;
                entity.aBoolean2015 = false;
            }

            if (var3 != 0) {
                ++entity.anInt2018;
                boolean var14;
                if (var3 > 1024) {
                    entity.turnOrientation -= entity.rotation;
                    var14 = true;
                    if (var3 < entity.rotation || var3 > 2048 - entity.rotation) {
                        entity.turnOrientation = entity.orientation;
                        var14 = false;
                    }

                    if (entity.idleStance == entity.stance && (entity.anInt2018 > 25 || var14)) {
                        if (entity.turnLeftStance != -1) {
                            entity.stance = entity.turnLeftStance;
                        } else {
                            entity.stance = entity.walkStance;
                        }
                    }
                } else {
                    entity.turnOrientation += entity.rotation;
                    var14 = true;
                    if (var3 < entity.rotation || var3 > 2048 - entity.rotation) {
                        entity.turnOrientation = entity.orientation;
                        var14 = false;
                    }

                    if (entity.stance == entity.idleStance && (entity.anInt2018 > 25 || var14)) {
                        if (entity.turnRightStance != -1) {
                            entity.stance = entity.turnRightStance;
                        } else {
                            entity.stance = entity.walkStance;
                        }
                    }
                }

                entity.turnOrientation &= 2047;
            } else {
                entity.anInt2018 = 0;
            }
        }

        entity.stretch = false;
        if (entity.stance != -1) {
            animation = AnimationSequence.get(entity.stance);
            if (animation != null && animation.frameIds != null) {
                ++entity.stanceFrameCycle;
                if (entity.stanceFrame < animation.frameIds.length && entity.stanceFrameCycle > animation.frameLengths[entity.stanceFrame]) {
                    entity.stanceFrameCycle = 1;
                    ++entity.stanceFrame;
                    client.method181(animation, entity.stanceFrame, entity.absoluteX, entity.absoluteY);
                }

                if (entity.stanceFrame >= animation.frameIds.length) {
                    entity.stanceFrameCycle = 0;
                    entity.stanceFrame = 0;
                    client.method181(animation, entity.stanceFrame, entity.absoluteX, entity.absoluteY);
                }
            } else {
                entity.stance = -1;
            }
        }

        if (entity.effect != -1 && client.ticks >= entity.effectDelay) {
            if (entity.effectFrame < 0) {
                entity.effectFrame = 0;
            }

            int var3 = EffectAnimation.get(entity.effect).animation;
            if (var3 != -1) {
                AnimationSequence var4 = AnimationSequence.get(var3);
                if (var4 != null && var4.frameIds != null) {
                    ++entity.effectFrameCycle;
                    if (entity.effectFrame < var4.frameIds.length && entity.effectFrameCycle > var4.frameLengths[entity.effectFrame]) {
                        entity.effectFrameCycle = 1;
                        ++entity.effectFrame;
                        client.method181(var4, entity.effectFrame, entity.absoluteX, entity.absoluteY);
                    }

                    if (entity.effectFrame >= var4.frameIds.length && (entity.effectFrame < 0 || entity.effectFrame >= var4.frameIds.length)) {
                        entity.effect = -1;
                    }
                } else {
                    entity.effect = -1;
                }
            } else {
                entity.effect = -1;
            }
        }

        if (entity.animation != -1 && entity.animationDelay <= 1) {
            animation = AnimationSequence.get(entity.animation);
            if (animation.animatingPrecedence == 1 && entity.anInt2023 > 0 && entity.forceMovementStartCycle <= client.ticks && entity.forceMovementEndCycle < client.ticks) {
                entity.animationDelay = 1;
                return;
            }
        }

        if (entity.animation != -1 && entity.animationDelay == 0) {
            animation = AnimationSequence.get(entity.animation);
            if (animation != null && animation.frameIds != null) {
                ++entity.animationFrameCycle;
                if (entity.animationFrame < animation.frameIds.length && entity.animationFrameCycle > animation.frameLengths[entity.animationFrame]) {
                    entity.animationFrameCycle = 1;
                    ++entity.animationFrame;
                    client.method181(animation, entity.animationFrame, entity.absoluteX, entity.absoluteY);
                }

                if (entity.animationFrame >= animation.frameIds.length) {
                    entity.animationFrame -= animation.loopOffset;
                    ++entity.animationLoopCounts;
                    if (entity.animationLoopCounts >= animation.maxLoops) {
                        entity.animation = -1;
                    } else if (entity.animationFrame >= 0 && entity.animationFrame < animation.frameIds.length) {
                        client.method181(animation, entity.animationFrame, entity.absoluteX, entity.absoluteY);
                    } else {
                        entity.animation = -1;
                    }
                }

                entity.stretch = animation.stretch;
            } else {
                entity.animation = -1;
            }
        }

        if (entity.animationDelay > 0) {
            --entity.animationDelay;
        }

    }

    public static void processMovementStartSequence(PathingEntity var0) {
        int var1 = var0.forceMovementStartCycle - client.ticks;
        int var2 = var0.boundSize + var0.startX * 128;
        int var3 = var0.boundSize + var0.startY * 128;
        var0.absoluteX += (var2 - var0.absoluteX) / var1;
        var0.absoluteY += (var3 - var0.absoluteY) / var1;
        var0.anInt2022 = 0;
        var0.orientation = var0.anInt2019;
    }

    public static void renderOverhead(PathingEntity entity, int var1, int var2, int var3, int var4, int var5) {
        if (entity != null && entity.isDefined()) {
            if (entity instanceof NpcEntity) {
                NpcDefinition var6 = ((NpcEntity) entity).definition;
                if (var6.transformIds != null) {
                    var6 = var6.transform();
                }

                if (var6 == null) {
                    return;
                }
            }

            int var7 = GPI.playerCount;
            int[] var8 = GPI.playerIndices;
            byte var9 = 0;
            if (var1 < var7 && entity.renderCycle == client.ticks && displayPlayerName((PlayerEntity) entity)) {
                PlayerEntity var10 = (PlayerEntity) entity;
                if (var1 < var7) {
                    Server.absoluteToViewport(entity, entity.modelHeight + 15);
                    BaseFont var11 = client.fonts.get(NamedFont.P12);
                    byte var12 = 9;
                    var11.method1154(var10.namePair.getRaw(), var2 + client.viewportRenderX, var3 + client.viewportRenderY - var12, 16777215, 0);
                    var9 = 18;
                }
            }

            int var13 = -2;
            int var18;
            int var19;
            int var25;
            int var26;
            if (!entity.healthBars.isEmpty()) {
                Server.absoluteToViewport(entity, entity.modelHeight + 15);

                for (HealthBar bar = entity.healthBars.head(); bar != null; bar = entity.healthBars.next()) {
                    HitUpdate update = bar.method695(client.ticks);
                    if (update == null) {
                        if (bar.method693()) {
                            bar.unlink();
                        }
                    } else {
                        HealthBarDefinition def = bar.definition;
                        Sprite var16 = def.method1378();
                        Sprite var17 = def.method1379();
                        var18 = 0;
                        if (var16 != null && var17 != null) {
                            if (def.anInt702 * 2 < var17.width) {
                                var18 = def.anInt702;
                            }

                            var19 = var17.width - var18 * 2;
                        } else {
                            var19 = def.maxWidth;
                        }

                        int var20 = 255;
                        boolean var21 = true;
                        int var22 = client.ticks - update.startCycle;
                        int var23 = var19 * update.currentWidth / def.maxWidth;
                        int var24;
                        int var94;
                        if (update.currentCycle > var22) {
                            var24 = def.anInt368 == 0 ? 0 : def.anInt368 * (var22 / def.anInt368);
                            var25 = var19 * update.startWidth / def.maxWidth;
                            var94 = var24 * (var23 - var25) / update.currentCycle + var25;
                        } else {
                            var94 = var23;
                            var24 = def.anInt367 + update.currentCycle - var22;
                            if (def.anInt564 >= 0) {
                                var20 = (var24 << 8) / (def.anInt367 - def.anInt564);
                            }
                        }

                        if (update.currentWidth > 0 && var94 < 1) {
                            var94 = 1;
                        }

                        if (var16 != null && var17 != null) {
                            if (var19 == var94) {
                                var94 += var18 * 2;
                            } else {
                                var94 += var18;
                            }

                            var24 = var16.height;
                            var13 += var24;
                            var25 = var2 + client.viewportRenderX - (var19 >> 1);
                            var26 = var3 + client.viewportRenderY - var13;
                            var25 -= var18;
                            if (var20 >= 0 && var20 < 255) {
                                var16.method832(var25, var26, var20);
                                JagGraphics.method1364(var25, var26, var25 + var94, var26 + var24);
                                var17.method832(var25, var26, var20);
                            } else {
                                var16.renderAlphaAt(var25, var26);
                                JagGraphics.method1364(var25, var26, var94 + var25, var26 + var24);
                                var17.renderAlphaAt(var25, var26);
                            }

                            JagGraphics.setClip(var2, var3, var2 + var4, var3 + var5);
                        } else {
                            var13 += 5;
                            if (client.viewportRenderX > -1) {
                                var24 = var2 + client.viewportRenderX - (var19 >> 1);
                                var25 = var3 + client.viewportRenderY - var13;
                                JagGraphics.fillRect(var24, var25, var94, 5, 65280);
                                JagGraphics.fillRect(var24 + var94, var25, var19 - var94, 5, 16711680);
                            }

                        }
                        var13 += 2;
                    }
                }
            }

            if (var13 == -2) {
                var13 += 7;
            }

            var13 += var9;
            if (var1 < var7) {
                PlayerEntity player = (PlayerEntity) entity;
                if (player.hidden) {
                    return;
                }

                if (player.prayerIcon != -1 || player.skullIcon != -1) {
                    Server.absoluteToViewport(entity, entity.modelHeight + 15);
                    if (client.viewportRenderX > -1) {
                        if (player.prayerIcon != -1) {
                            var13 += 25;
                            StructDefinition.skullIconSprites[player.prayerIcon].renderAlphaAt(var2 + client.viewportRenderX - 12, var3 + client.viewportRenderY - var13);
                        }

                        if (player.skullIcon != -1) {
                            var13 += 25;
                            WorldMapChunkDefinition.prayerIconSprites[player.skullIcon].renderAlphaAt(var2 + client.viewportRenderX - 12, var3 + client.viewportRenderY - var13);
                        }
                    }
                }

                if (var1 >= 0 && HintArrow.type == 10 && var8[var1] == HintArrow.player) {
                    Server.absoluteToViewport(entity, entity.modelHeight + 15);
                    if (client.viewportRenderX > -1) {
                        var13 += HintArrow.overheadSprites[1].height;
                        HintArrow.overheadSprites[1].renderAlphaAt(var2 + client.viewportRenderX - 12, var3 + client.viewportRenderY - var13);
                    }
                }
            } else {
                NpcDefinition var91 = ((NpcEntity) entity).definition;
                if (var91.transformIds != null) {
                    var91 = var91.transform();
                }

                if (var91.prayerIcon >= 0 && var91.prayerIcon < WorldMapChunkDefinition.prayerIconSprites.length) {
                    Server.absoluteToViewport(entity, entity.modelHeight + 15);
                    if (client.viewportRenderX > -1) {
                        WorldMapChunkDefinition.prayerIconSprites[var91.prayerIcon].renderAlphaAt(var2 + client.viewportRenderX - 12, var3 + client.viewportRenderY - 30);
                    }
                }

                if (HintArrow.type == 1 && client.npcIndices[var1 - var7] == HintArrow.npc && client.ticks % 20 < 10) {
                    Server.absoluteToViewport(entity, entity.modelHeight + 15);
                    if (client.viewportRenderX > -1) {
                        HintArrow.overheadSprites[0].renderAlphaAt(var2 + client.viewportRenderX - 12, var3 + client.viewportRenderY - 28);
                    }
                }
            }

            if (entity.overheadText != null && (var1 >= var7 || !entity.aBoolean2005 && (client.publicChatMode == 4 || !entity.autoChatting && (client.publicChatMode == 0 || client.publicChatMode == 3 || client.publicChatMode == 1 && ((PlayerEntity) entity).method609())))) {
                Server.absoluteToViewport(entity, entity.modelHeight);
                if (client.viewportRenderX > -1 && client.overheadMessageCount < client.overheadMessageCapacity) {
                    client.overheadMessageXShifts[client.overheadMessageCount] = Font.b12full.textWidth(entity.overheadText) / 2;
                    client.overheadMessageYShifts[client.overheadMessageCount] = Font.b12full.anInt375;
                    client.overheadMessageXPositions[client.overheadMessageCount] = client.viewportRenderX;
                    client.overheadMessageYPositions[client.overheadMessageCount] = client.viewportRenderY;
                    client.overheadMessageColors[client.overheadMessageCount] = entity.overheadTextForeground;
                    client.overheadMessageEffects[client.overheadMessageCount] = entity.overheadTextEffect;
                    client.overheadMessageCyclesRemaining[client.overheadMessageCount] = entity.overheadTextCyclesLeft;
                    client.overheadMessages[client.overheadMessageCount] = entity.overheadText;
                    ++client.overheadMessageCount;
                }
            }

            for (int var27 = 0; var27 < 4; ++var27) {
                int var92 = entity.hitsplatCycles[var27];
                int var28 = entity.hitsplatTypes[var27];
                HitsplatDefinition var93 = null;
                int var29 = 0;
                HitsplatDefinition var30;
                if (var28 >= 0) {
                    if (var92 <= client.ticks) {
                        continue;
                    }

                    var18 = entity.hitsplatTypes[var27];
                    var30 = HitsplatDefinition.cache.get(var18);
                    HitsplatDefinition var31;
                    if (var30 == null) {
                        byte[] var32 = HitsplatDefinition.table.unpack(32, var18);
                        var30 = new HitsplatDefinition();
                        if (var32 != null) {
                            var30.decode(new Buffer(var32));
                        }

                        HitsplatDefinition.cache.put(var30, var18);
                    }
                    var31 = var30;

                    var93 = var31;
                    var29 = var31.duration;
                    if (var31 != null && var31.transformIds != null) {
                        var93 = var31.method1437();
                        if (var93 == null) {
                            entity.hitsplatCycles[var27] = -1;
                            continue;
                        }
                    }
                } else if (var92 < 0) {
                    continue;
                }

                var19 = entity.specialHitsplatTypes[var27];
                HitsplatDefinition var33 = null;
                HitsplatDefinition var95;
                if (var19 >= 0) {
                    var95 = HitsplatDefinition.cache.get(var19);
                    if (var95 == null) {
                        byte[] var34 = HitsplatDefinition.table.unpack(32, var19);
                        var95 = new HitsplatDefinition();
                        if (var34 != null) {
                            var95.decode(new Buffer(var34));
                        }

                        HitsplatDefinition.cache.put(var95, var19);
                    }
                    var30 = var95;

                    var33 = var30;
                    if (var30 != null && var30.transformIds != null) {
                        var33 = var30.method1437();
                    }
                }

                if (var92 - var29 <= client.ticks) {
                    if (var93 == null) {
                        entity.hitsplatCycles[var27] = -1;
                    } else {
                        Server.absoluteToViewport(entity, entity.modelHeight / 2);
                        if (client.viewportRenderX > -1) {
                            if (var27 == 1) {
                                client.viewportRenderY -= 20;
                            }

                            if (var27 == 2) {
                                client.viewportRenderX -= 15;
                                client.viewportRenderY -= 10;
                            }

                            if (var27 == 3) {
                                client.viewportRenderX += 15;
                                client.viewportRenderY -= 10;
                            }

                            Sprite var97;
                            Sprite var35;
                            Sprite var36;
                            var25 = 0;
                            var26 = 0;
                            int var37 = 0;
                            int var38 = 0;
                            int var39 = 0;
                            int var40 = 0;
                            int var41 = 0;
                            int var42 = 0;
                            Sprite var43 = null;
                            Sprite var44 = null;
                            Sprite var45 = null;
                            Sprite var46 = null;
                            int var47 = 0;
                            int var48 = 0;
                            int var49 = 0;
                            int var50 = 0;
                            int var51 = 0;
                            int var52 = 0;
                            int var53 = 0;
                            int var54 = 0;
                            int var55 = 0;
                            Sprite var96 = var93.getIcon();
                            int var56;
                            if (var96 != null) {
                                var25 = var96.width;
                                var56 = var96.height;
                                if (var56 > var55) {
                                    var55 = var56;
                                }

                                var39 = var96.anInt377;
                            }

                            var97 = var93.getMiddleSprite();
                            if (var97 != null) {
                                var26 = var97.width;
                                var56 = var97.height;
                                if (var56 > var55) {
                                    var55 = var56;
                                }

                                var40 = var97.anInt377;
                            }

                            var35 = var93.getLeftSprite();
                            if (var35 != null) {
                                var37 = var35.width;
                                var56 = var35.height;
                                if (var56 > var55) {
                                    var55 = var56;
                                }

                                var41 = var35.anInt377;
                            }

                            var36 = var93.getRightSprite();
                            if (var36 != null) {
                                var38 = var36.width;
                                var56 = var36.height;
                                if (var56 > var55) {
                                    var55 = var56;
                                }

                                var42 = var36.anInt377;
                            }

                            if (var33 != null) {
                                var43 = var33.getIcon();
                                if (var43 != null) {
                                    var47 = var43.width;
                                    var56 = var43.height;
                                    if (var56 > var55) {
                                        var55 = var56;
                                    }

                                    var51 = var43.anInt377;
                                }

                                var44 = var33.getMiddleSprite();
                                if (var44 != null) {
                                    var48 = var44.width;
                                    var56 = var44.height;
                                    if (var56 > var55) {
                                        var55 = var56;
                                    }

                                    var52 = var44.anInt377;
                                }

                                var45 = var33.getLeftSprite();
                                if (var45 != null) {
                                    var49 = var45.width;
                                    var56 = var45.height;
                                    if (var56 > var55) {
                                        var55 = var56;
                                    }

                                    var53 = var45.anInt377;
                                }

                                var46 = var33.getRightSprite();
                                if (var46 != null) {
                                    var50 = var46.width;
                                    var56 = var46.height;
                                    if (var56 > var55) {
                                        var55 = var56;
                                    }

                                    var54 = var46.anInt377;
                                }
                            }

                            Font var57 = var93.getFont();
                            if (var57 == null) {
                                var57 = Font.p11;
                            }

                            Font var58;
                            if (var33 != null) {
                                var58 = var33.getFont();
                                if (var58 == null) {
                                    var58 = Font.p11;
                                }
                            } else {
                                var58 = Font.p11;
                            }

                            String var59;
                            String var60 = null;
                            boolean var61 = false;
                            int var62 = 0;
                            var59 = var93.method1436(entity.hitsplats[var27]);
                            int var98 = var57.textWidth(var59);
                            if (var33 != null) {
                                var60 = var33.method1436(entity.specialHitsplats[var27]);
                                var62 = var58.textWidth(var60);
                            }

                            int var63 = 0;
                            int var64 = 0;
                            if (var26 > 0) {
                                if (var35 == null && var36 == null) {
                                    var63 = 1;
                                } else {
                                    var63 = var98 / var26 + 1;
                                }
                            }

                            if (var33 != null && var48 > 0) {
                                if (var45 == null && var46 == null) {
                                    var64 = 1;
                                } else {
                                    var64 = var62 / var48 + 1;
                                }
                            }

                            int var65 = 0;
                            int var66 = var65;
                            if (var25 > 0) {
                                var65 += var25;
                            }

                            var65 += 2;
                            int var67 = var65;
                            if (var37 > 0) {
                                var65 += var37;
                            }

                            int var68 = var65;
                            int var69 = var65;
                            int var70;
                            if (var26 > 0) {
                                var70 = var26 * var63;
                                var65 += var70;
                                var69 += (var70 - var98) / 2;
                            } else {
                                var65 += var98;
                            }

                            var70 = var65;
                            if (var38 > 0) {
                                var65 += var38;
                            }

                            int var71 = 0;
                            int var72 = 0;
                            int var73 = 0;
                            int var74 = 0;
                            int var75 = 0;
                            int var76;
                            if (var33 != null) {
                                var65 += 2;
                                var71 = var65;
                                if (var47 > 0) {
                                    var65 += var47;
                                }

                                var65 += 2;
                                var72 = var65;
                                if (var49 > 0) {
                                    var65 += var49;
                                }

                                var73 = var65;
                                var75 = var65;
                                if (var48 > 0) {
                                    var76 = var48 * var64;
                                    var65 += var76;
                                    var75 += (var76 - var62) / 2;
                                } else {
                                    var65 += var62;
                                }

                                var74 = var65;
                                if (var50 > 0) {
                                    var65 += var50;
                                }
                            }

                            var76 = entity.hitsplatCycles[var27] - client.ticks;
                            int var77 = var93.offsetX - var76 * var93.offsetX / var93.duration;
                            int var78 = var76 * var93.offsetY / var93.duration + -var93.offsetY;
                            int var79 = var77 + (var2 + client.viewportRenderX - (var65 >> 1));
                            int var80 = var78 + (var3 + client.viewportRenderY - 12);
                            int var82 = var80 + var55;
                            int var83 = var80 + var93.anInt1659 + 15;
                            int var84 = var83 - var57.anInt372;
                            int var85 = var83 + var57.anInt379;
                            if (var84 < var80) {
                            }

                            if (var85 > var82) {
                            }

                            int var86 = 0;
                            int var87;
                            int var88;
                            if (var33 != null) {
                                var86 = var80 + var33.anInt1659 + 15;
                            }

                            var87 = 255;
                            if (var93.fade >= 0) {
                                var87 = (var76 << 8) / (var93.duration - var93.fade);
                            }

                            if (var87 >= 0 && var87 < 255) {
                                if (var96 != null) {
                                    var96.method832(var79 + var66 - var39, var80, var87);
                                }

                                if (var35 != null) {
                                    var35.method832(var67 + var79 - var41, var80, var87);
                                }

                                if (var97 != null) {
                                    for (var88 = 0; var88 < var63; ++var88) {
                                        var97.method832(var26 * var88 + (var68 + var79 - var40), var80, var87);
                                    }
                                }

                                if (var36 != null) {
                                    var36.method832(var70 + var79 - var42, var80, var87);
                                }

                                var57.method1164(var59, var69 + var79, var83, var93.textColor, 0, var87);
                                if (var33 != null) {
                                    if (var43 != null) {
                                        var43.method832(var71 + var79 - var51, var80, var87);
                                    }

                                    if (var45 != null) {
                                        var45.method832(var72 + var79 - var53, var80, var87);
                                    }

                                    if (var44 != null) {
                                        for (var88 = 0; var88 < var64; ++var88) {
                                            var44.method832(var48 * var88 + (var79 + var73 - var52), var80, var87);
                                        }
                                    }

                                    if (var46 != null) {
                                        var46.method832(var74 + var79 - var54, var80, var87);
                                    }

                                    var58.method1164(var60, var79 + var75, var86, var33.textColor, 0, var87);
                                }
                            } else {
                                if (var96 != null) {
                                    var96.renderAlphaAt(var79 + var66 - var39, var80);
                                }

                                if (var35 != null) {
                                    var35.renderAlphaAt(var79 + var67 - var41, var80);
                                }

                                if (var97 != null) {
                                    for (var88 = 0; var88 < var63; ++var88) {
                                        var97.renderAlphaAt(var88 * var26 + (var68 + var79 - var40), var80);
                                    }
                                }

                                if (var36 != null) {
                                    var36.renderAlphaAt(var70 + var79 - var42, var80);
                                }

                                var57.drawString(var59, var69 + var79, var83, var93.textColor | -16777216, 0);
                                if (var33 != null) {
                                    if (var43 != null) {
                                        var43.renderAlphaAt(var79 + var71 - var51, var80);
                                    }

                                    if (var45 != null) {
                                        var45.renderAlphaAt(var79 + var72 - var53, var80);
                                    }

                                    if (var44 != null) {
                                        for (var88 = 0; var88 < var64; ++var88) {
                                            var44.renderAlphaAt(var88 * var48 + (var73 + var79 - var52), var80);
                                        }
                                    }

                                    if (var46 != null) {
                                        var46.renderAlphaAt(var74 + var79 - var54, var80);
                                    }

                                    var58.drawString(var60, var75 + var79, var86, var33.textColor | -16777216, 0);
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public static void renderOverhead(int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        client.overheadMessageCount = 0;
        boolean varp = false;
        int var5 = -1;
        int index = -1;
        int playerCount = GPI.playerCount;
        int[] indices = GPI.playerIndices;

        for (int i = 0; i < playerCount + client.npcCount; ++i) {
            PathingEntity entity;
            if (i < playerCount) {
                entity = client.players[indices[i]];
                if (indices[i] == client.combatTargetPlayerIndex) {
                    varp = true;
                    var5 = i;
                    continue;
                }

                if (entity == PlayerEntity.local) {
                    index = i;
                    continue;
                }
            } else {
                entity = client.npcs[client.npcIndices[i - playerCount]];
            }

            renderOverhead(entity, i, viewportX, viewportY, viewportWidth, viewportHeight);
        }

        if (client.displaySelf && index != -1) {
            renderOverhead(PlayerEntity.local, index, viewportX, viewportY, viewportWidth, viewportHeight);
        }

        if (varp) {
            renderOverhead(client.players[client.combatTargetPlayerIndex], var5, viewportX, viewportY, viewportWidth, viewportHeight);
        }

        for (int i = 0; i < client.overheadMessageCount; ++i) {
            int x = client.overheadMessageXPositions[i];
            int y = client.overheadMessageYPositions[i];
            int dx = client.overheadMessageXShifts[i];
            int dy = client.overheadMessageYShifts[i];
            boolean var15 = true;

            while (var15) {
                var15 = false;

                for (int var16 = 0; var16 < i; ++var16) {
                    if (y + 2 > client.overheadMessageYPositions[var16] - client.overheadMessageYShifts[var16] && y - dy < client.overheadMessageYPositions[var16] + 2 && x - dx < client.overheadMessageXShifts[var16] + client.overheadMessageXPositions[var16] && x + dx > client.overheadMessageXPositions[var16] - client.overheadMessageXShifts[var16] && client.overheadMessageYPositions[var16] - client.overheadMessageYShifts[var16] < y) {
                        y = client.overheadMessageYPositions[var16] - client.overheadMessageYShifts[var16];
                        var15 = true;
                    }
                }
            }

            client.viewportRenderX = client.overheadMessageXPositions[i];
            client.viewportRenderY = client.overheadMessageYPositions[i] = y;
            String var17 = client.overheadMessages[i];
            if (client.chatEffects == 0) {
                int var18 = 16776960;
                if (client.overheadMessageColors[i] < 6) {
                    var18 = client.anIntArray1094[client.overheadMessageColors[i]];
                }

                if (client.overheadMessageColors[i] == 6) {
                    var18 = client.viewportRenderCount % 20 < 10 ? 16711680 : 16776960;
                }

                if (client.overheadMessageColors[i] == 7) {
                    var18 = client.viewportRenderCount % 20 < 10 ? 255 : '\uffff';
                }

                if (client.overheadMessageColors[i] == 8) {
                    var18 = client.viewportRenderCount % 20 < 10 ? '뀀' : 8454016;
                }

                int var19;
                if (client.overheadMessageColors[i] == 9) {
                    var19 = 150 - client.overheadMessageCyclesRemaining[i];
                    if (var19 < 50) {
                        var18 = var19 * 1280 + 16711680;
                    } else if (var19 < 100) {
                        var18 = 16776960 - (var19 - 50) * 327680;
                    } else if (var19 < 150) {
                        var18 = (var19 - 100) * 5 + 65280;
                    }
                }

                if (client.overheadMessageColors[i] == 10) {
                    var19 = 150 - client.overheadMessageCyclesRemaining[i];
                    if (var19 < 50) {
                        var18 = var19 * 5 + 16711680;
                    } else if (var19 < 100) {
                        var18 = 16711935 - (var19 - 50) * 327680;
                    } else if (var19 < 150) {
                        var18 = (var19 - 100) * 327680 + 255 - (var19 - 100) * 5;
                    }
                }

                if (client.overheadMessageColors[i] == 11) {
                    var19 = 150 - client.overheadMessageCyclesRemaining[i];
                    if (var19 < 50) {
                        var18 = 16777215 - var19 * 327685;
                    } else if (var19 < 100) {
                        var18 = (var19 - 50) * 327685 + 65280;
                    } else if (var19 < 150) {
                        var18 = 16777215 - (var19 - 100) * 327680;
                    }
                }

                if (client.overheadMessageEffects[i] == 0) {
                    Font.b12full.method1154(var17, viewportX + client.viewportRenderX, client.viewportRenderY + viewportY, var18, 0);
                }

                if (client.overheadMessageEffects[i] == 1) {
                    Font.b12full.method1157(var17, viewportX + client.viewportRenderX, client.viewportRenderY + viewportY, var18, 0, client.viewportRenderCount);
                }

                if (client.overheadMessageEffects[i] == 2) {
                    Font.b12full.method1155(var17, viewportX + client.viewportRenderX, client.viewportRenderY + viewportY, var18, 0, client.viewportRenderCount);
                }

                if (client.overheadMessageEffects[i] == 3) {
                    Font.b12full.method1146(var17, viewportX + client.viewportRenderX, client.viewportRenderY + viewportY, var18, 0, client.viewportRenderCount, 150 - client.overheadMessageCyclesRemaining[i]);
                }

                if (client.overheadMessageEffects[i] == 4) {
                    var19 = (150 - client.overheadMessageCyclesRemaining[i]) * (Font.b12full.textWidth(var17) + 100) / 150;
                    JagGraphics.method1364(viewportX + client.viewportRenderX - 50, viewportY, viewportX + client.viewportRenderX + 50, viewportHeight + viewportY);
                    Font.b12full.drawString(var17, viewportX + client.viewportRenderX + 50 - var19, client.viewportRenderY + viewportY, var18, 0);
                    JagGraphics.setClip(viewportX, viewportY, viewportX + viewportWidth, viewportHeight + viewportY);
                }

                if (client.overheadMessageEffects[i] == 5) {
                    var19 = 150 - client.overheadMessageCyclesRemaining[i];
                    int var20 = 0;
                    if (var19 < 25) {
                        var20 = var19 - 25;
                    } else if (var19 > 125) {
                        var20 = var19 - 125;
                    }

                    JagGraphics.method1364(viewportX, client.viewportRenderY + viewportY - Font.b12full.anInt375 - 1, viewportX + viewportWidth, client.viewportRenderY + viewportY + 5);
                    Font.b12full.method1154(var17, viewportX + client.viewportRenderX, var20 + client.viewportRenderY + viewportY, var18, 0);
                    JagGraphics.setClip(viewportX, viewportY, viewportX + viewportWidth, viewportHeight + viewportY);
                }
            } else {
                Font.b12full.method1154(var17, viewportX + client.viewportRenderX, client.viewportRenderY + viewportY, 16776960, 0);
            }
        }

    }

    public static boolean displayPlayerName(PlayerEntity player) {
        if (client.displayPlayerNames == 0) {
            return false;
        }
        if (PlayerEntity.local != player) {
            boolean var1 = (client.displayPlayerNames & 4) != 0;
            boolean var2 = var1;
            boolean var3;
            if (!var1) {
                var3 = (client.displayPlayerNames & 1) != 0;
                var2 = var3 && player.method609();
            }

            var3 = var2;
            if (!var2) {
                boolean var4 = (client.displayPlayerNames & 2) != 0;
                var3 = var4 && player.method258();
            }

            return var3;
        }
        return displayPlayerNames();
    }

    public static boolean displayPlayerNames() {
        return (client.displayPlayerNames & 8) != 0;
    }

    public static void processMovementEndSequence(PathingEntity entity) {
        if (entity.forceMovementEndCycle == client.ticks
                || entity.animation == -1
                || entity.animationDelay != 0
                || entity.animationFrameCycle + 1 > AnimationSequence.get(entity.animation).frameLengths[entity.animationFrame]) {
            int var1 = entity.forceMovementEndCycle - entity.forceMovementStartCycle;
            int var2 = client.ticks - entity.forceMovementStartCycle;
            int var3 = entity.boundSize + entity.startX * 128;
            int var4 = entity.boundSize + entity.startY * 128;
            int var5 = entity.boundSize + entity.endX * 128;
            int var6 = entity.boundSize + entity.endY * 128;
            entity.absoluteX = (var2 * var5 + var3 * (var1 - var2)) / var1;
            entity.absoluteY = (var6 * var2 + var4 * (var1 - var2)) / var1;
        }

        entity.anInt2022 = 0;
        entity.orientation = entity.anInt2019;
        entity.turnOrientation = entity.orientation;
    }

    public final void addHitSplat(int type, int damage, int specialType, int special, int currentCycle, int delay) {
        boolean expire = true;
        boolean var9 = true;

        for (int i = 0; i < 4; ++i) {
            if (hitsplatCycles[i] > currentCycle) {
                expire = false;
            } else {
                var9 = false;
            }
        }

        int comparisonType = -1;
        int duration = 0;
        if (type >= 0) {
            HitsplatDefinition definition = HitsplatDefinition.cache.get(type);
            if (definition == null) {
                byte[] payload = HitsplatDefinition.table.unpack(32, type);
                definition = new HitsplatDefinition();
                if (payload != null) {
                    definition.decode(new Buffer(payload));
                }
                HitsplatDefinition.cache.put(definition, type);
            }

            comparisonType = definition.comparisonType;
            duration = definition.duration;
        }

        int index = -1;
        if (var9) {
            if (comparisonType == -1) {
                return;
            }

            index = 0;
            int result = 0;
            if (comparisonType == 0) {
                result = hitsplatCycles[0];
            } else if (comparisonType == 1) {
                result = hitsplats[0];
            }

            for (int i = 1; i < 4; ++i) {
                if (comparisonType == 0) {
                    if (hitsplatCycles[i] < result) {
                        index = i;
                        result = hitsplatCycles[i];
                    }
                } else if (comparisonType == 1 && hitsplats[i] < result) {
                    index = i;
                    result = hitsplats[i];
                }
            }

            if (comparisonType == 1 && result >= damage) {
                return;
            }
        } else {
            if (expire) {
                hitsplatCount = 0;
            }

            for (int i = 0; i < 4; ++i) {
                byte count = hitsplatCount;
                hitsplatCount = (byte) ((hitsplatCount + 1) % 4);
                if (hitsplatCycles[count] <= currentCycle) {
                    index = count;
                    break;
                }
            }
        }

        if (index >= 0) {
            hitsplatTypes[index] = type;
            hitsplats[index] = damage;
            specialHitsplatTypes[index] = specialType;
            specialHitsplats[index] = special;
            hitsplatCycles[index] = currentCycle + duration + delay;
        }
    }

    public final void method1503(int var1) {
        HealthBarDefinition var2 = HealthBarDefinition.cache.get(var1);
        HealthBarDefinition var3;
        if (var2 == null) {
            byte[] var4 = HealthBarDefinition.table.unpack(33, var1);
            var2 = new HealthBarDefinition();
            if (var4 != null) {
                var2.decode(new Buffer(var4));
            }

            HealthBarDefinition.cache.put(var2, var1);
        }
        var3 = var2;

        var2 = var3;

        for (HealthBar var5 = healthBars.head(); var5 != null; var5 = healthBars.next()) {
            if (var2 == var5.definition) {
                var5.unlink();
                return;
            }
        }

    }

    public final void updateHealthBar(int id, int currentCycle, int updateCycle, int delay, int width, int currWidth) {
        HealthBarDefinition definition = HealthBarDefinition.cache.get(id);
        if (definition == null) {
            byte[] payload = HealthBarDefinition.table.unpack(33, id);
            definition = new HealthBarDefinition();
            if (payload != null) {
                definition.decode(new Buffer(payload));
            }
            HealthBarDefinition.cache.put(definition, id);
        }

        HealthBar predecessor = null;
        HealthBar lowest = null;
        int highest = definition.hidePriority;
        int i = 0;

        for (HealthBar current = healthBars.head(); current != null; current = healthBars.next()) {
            ++i;
            if (current.definition.anInt574 == definition.anInt574) {
                current.update(currentCycle + delay, width, currWidth, updateCycle);
                return;
            }

            if (current.definition.orderPriority <= definition.orderPriority) {
                predecessor = current;
            }

            if (current.definition.hidePriority > highest) {
                lowest = current;
                highest = current.definition.hidePriority;
            }
        }

        if (lowest != null || i < 4) {
            HealthBar bar = new HealthBar(definition);
            if (predecessor == null) {
                healthBars.addLast(bar);
            } else {
                LinkedList.insertBefore(bar, predecessor);
            }

            bar.update(currentCycle + delay, width, currWidth, updateCycle);
            if (i >= 4) {
                lowest.unlink();
            }

        }
    }

    public boolean isDefined() {
        return false;
    }

    public final void method1504() {
        pathQueueSize = 0;
        anInt2023 = 0;
    }
}
