package jag.game.scene.entity;

import jag.*;
import jag.commons.Jagexception;
import jag.commons.input.Keyboard;
import jag.game.PlayerModel;
import jag.game.client;
import jag.game.menu.ContextMenu;
import jag.game.relationship.ChatHistory;
import jag.game.relationship.AssociateStatus;
import jag.game.relationship.NamePair;
import jag.game.scene.SceneGraph;
import jag.game.type.AnimationSequence;
import jag.game.type.EffectAnimation;
import jag.game.type.ItemDefinition;
import jag.game.type.NpcDefinition;
import jag.graphics.BaseFont;
import jag.opcode.*;
import jag.statics.Statics34;
import jag.worldmap.PreciseWorldMapAreaChunk;

public final class PlayerEntity extends PathingEntity {

    public static PlayerEntity local;

    public final String[] actions;

    public PlayerModel model;

    public AssociateStatus friendsChatUserStatus;
    public AssociateStatus befriendedPlayerStatus;

    public NamePair namePair;

    public Model transformedNpcModel;

    public boolean aBoolean1905;
    public boolean hidden;
    public boolean aBoolean1904;

    public int prayerIcon;
    public int skullIcon;
    public int index;
    public int combatLevel;
    public int skillLevel;
    public int team;
    public int animationStartCycle;
    public int animationEndCycle;
    public int floorLevel;
    public int maxX;
    public int baseTileHeight;
    public int tileHeight;
    public int maxY;
    public int startObjectX;
    public int endObjectX;
    public int startObjectY;
    public int endObjectY;
    public int updateX;
    public int updateY;

    public PlayerEntity() {
        prayerIcon = -1;
        skullIcon = -1;
        actions = new String[3];

        for (int i = 0; i < 3; ++i) {
            actions[i] = "";
        }

        combatLevel = 0;
        skillLevel = 0;
        animationStartCycle = 0;
        animationEndCycle = 0;
        aBoolean1905 = false;
        team = 0;
        hidden = false;
        befriendedPlayerStatus = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
        friendsChatUserStatus = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
        aBoolean1904 = false;
    }

    public static void update(BitBuffer packet, int var1) {
        int var2 = packet.pos;
        GPI.anInt594 = 0;
        int var3 = 0;
        packet.bitAccess();

        int var4;
        int var5;
        int var6;
        int var7;
        int var8;
        byte[] var10000;
        for (var4 = 0; var4 < GPI.playerCount; ++var4) {
            var5 = GPI.playerIndices[var4];
            if ((GPI.playerSkipFlags[var5] & 1) == 0) {
                if (var3 > 0) {
                    --var3;
                    var10000 = GPI.playerSkipFlags;
                    var10000[var5] = (byte) (var10000[var5] | 2);
                } else {
                    var6 = packet.g(1);
                    if (var6 == 0) {
                        var7 = packet.g(2);
                        if (var7 == 0) {
                            var8 = 0;
                        } else if (var7 == 1) {
                            var8 = packet.g(5);
                        } else if (var7 == 2) {
                            var8 = packet.g(8);
                        } else {
                            var8 = packet.g(11);
                        }

                        var3 = var8;
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    } else {
                        GPI.updatePlayerLocation(packet, var5);
                    }
                }
            }
        }

        packet.byteAccess();
        if (var3 != 0) {
            throw new RuntimeException();
        }
        packet.bitAccess();

        for (var4 = 0; var4 < GPI.playerCount; ++var4) {
            var5 = GPI.playerIndices[var4];
            if ((GPI.playerSkipFlags[var5] & 1) != 0) {
                if (var3 > 0) {
                    --var3;
                    var10000 = GPI.playerSkipFlags;
                    var10000[var5] = (byte) (var10000[var5] | 2);
                } else {
                    var6 = packet.g(1);
                    if (var6 == 0) {
                        var7 = packet.g(2);
                        if (var7 == 0) {
                            var8 = 0;
                        } else if (var7 == 1) {
                            var8 = packet.g(5);
                        } else if (var7 == 2) {
                            var8 = packet.g(8);
                        } else {
                            var8 = packet.g(11);
                        }

                        var3 = var8;
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    } else {
                        GPI.updatePlayerLocation(packet, var5);
                    }
                }
            }
        }

        packet.byteAccess();
        if (var3 != 0) {
            throw new RuntimeException();
        }
        packet.bitAccess();

        for (var4 = 0; var4 < GPI.globalPlayerCount; ++var4) {
            var5 = GPI.globalPlayerIndices[var4];
            if ((GPI.playerSkipFlags[var5] & 1) != 0) {
                if (var3 > 0) {
                    --var3;
                    var10000 = GPI.playerSkipFlags;
                    var10000[var5] = (byte) (var10000[var5] | 2);
                } else {
                    var6 = packet.g(1);
                    if (var6 == 0) {
                        var7 = packet.g(2);
                        if (var7 == 0) {
                            var8 = 0;
                        } else if (var7 == 1) {
                            var8 = packet.g(5);
                        } else if (var7 == 2) {
                            var8 = packet.g(8);
                        } else {
                            var8 = packet.g(11);
                        }

                        var3 = var8;
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    } else if (Statics34.method1168(packet, var5)) {
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    }
                }
            }
        }

        packet.byteAccess();
        if (var3 != 0) {
            throw new RuntimeException();
        }
        packet.bitAccess();

        for (var4 = 0; var4 < GPI.globalPlayerCount; ++var4) {
            var5 = GPI.globalPlayerIndices[var4];
            if ((GPI.playerSkipFlags[var5] & 1) == 0) {
                if (var3 > 0) {
                    --var3;
                    var10000 = GPI.playerSkipFlags;
                    var10000[var5] = (byte) (var10000[var5] | 2);
                } else {
                    var6 = packet.g(1);
                    if (var6 == 0) {
                        var7 = packet.g(2);
                        if (var7 == 0) {
                            var8 = 0;
                        } else if (var7 == 1) {
                            var8 = packet.g(5);
                        } else if (var7 == 2) {
                            var8 = packet.g(8);
                        } else {
                            var8 = packet.g(11);
                        }

                        var3 = var8;
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    } else if (Statics34.method1168(packet, var5)) {
                        var10000 = GPI.playerSkipFlags;
                        var10000[var5] = (byte) (var10000[var5] | 2);
                    }
                }
            }
        }

        packet.byteAccess();
        if (var3 != 0) {
            throw new RuntimeException();
        }
        GPI.playerCount = 0;
        GPI.globalPlayerCount = 0;

        PlayerEntity player;
        for (var4 = 1; var4 < 2048; ++var4) {
            var10000 = GPI.playerSkipFlags;
            var10000[var4] = (byte) (var10000[var4] >> 1);
            player = client.players[var4];
            if (player != null) {
                GPI.playerIndices[++GPI.playerCount - 1] = var4;
            } else {
                GPI.globalPlayerIndices[++GPI.globalPlayerCount - 1] = var4;
            }
        }

        for (var3 = 0; var3 < GPI.anInt594; ++var3) {
            var4 = GPI.anIntArray588[var3];
            player = client.players[var4];
            var6 = packet.g1();
            if ((var6 & 0x1) != 0) {
                var6 += packet.g1() << 8;
            }

            byte var9 = -1;
            if ((var6 & 0x100) != 0) {
                for (var7 = 0; var7 < 3; ++var7) {
                    player.actions[var7] = packet.gstr();
                }
            }

            if ((var6 & 0x800) != 0) {
                player.effect = packet.g2();
                var7 = packet.g4();
                player.anInt2014 = var7 >> 16;
                player.effectDelay = (var7 & 65535) + client.ticks;
                player.effectFrame = 0;
                player.effectFrameCycle = 0;
                if (player.effectDelay > client.ticks) {
                    player.effectFrame = -1;
                }

                if (player.effect == 65535) {
                    player.effect = -1;
                }
            }

            if ((var6 & 0x2) != 0) {
                player.overheadText = packet.gstr();
                if (player.overheadText.charAt(0) == '~') {
                    player.overheadText = player.overheadText.substring(1);
                    ChatHistory.messageReceived(2, player.namePair.getRaw(), player.overheadText);
                } else if (player == local) {
                    ChatHistory.messageReceived(2, player.namePair.getRaw(), player.overheadText);
                }

                player.autoChatting = false;
                player.overheadTextForeground = 0;
                player.overheadTextEffect = 0;
                player.overheadTextCyclesLeft = 150;
            }

            if ((var6 & 0x8) != 0) {
                var7 = packet.ig1_alt1();
                byte[] var11 = new byte[var7];
                Buffer var12 = new Buffer(var11);
                packet.igdataa(var11, 0, var7);
                GPI.buffers[var4] = var12;
                player.decode(var12);
            }

            int var14;
            int var15;
            int var18;
            if ((var6 & 0x80) != 0) {
                var7 = packet.g2();
                PlayerAccountType var20 = (PlayerAccountType) EnumType.getByOrdinal(PlayerAccountType.getValues(), packet.g1());
                boolean var13 = packet.ig1_alt1() == 1;
                var14 = packet.ig1_alt1();
                var15 = packet.pos;
                if (player.namePair != null && player.model != null) {
                    boolean var16 = var20.notJagex && client.relationshipManager.isIgnored(player.namePair);

                    if (!var16 && client.anInt1014 == 0 && !player.hidden) {
                        GPI.chatBuffer.pos = 0;
                        packet.igdata(GPI.chatBuffer.payload, 0, var14);
                        GPI.chatBuffer.pos = 0;
                        String var17 = BaseFont.processGtLt(OldConnection.method714(DefaultRouteStrategy.method294(GPI.chatBuffer)));
                        player.overheadText = var17.trim();
                        player.overheadTextForeground = var7 >> 8;
                        player.overheadTextEffect = var7 & 255;
                        player.overheadTextCyclesLeft = 150;
                        player.autoChatting = var13;
                        player.aBoolean2005 = player != local && var20.notJagex && "" != client.aString1091 && !var17.toLowerCase().contains(client.aString1091);
                        if (var20.staff) {
                            var18 = var13 ? 91 : 1;
                        } else {
                            var18 = var13 ? 90 : 2;
                        }

                        if (var20.icon != -1) {
                            ChatHistory.messageReceived(var18, ContextMenu.addImgTags(var20.icon) + player.namePair.getRaw(), var17);
                        } else {
                            ChatHistory.messageReceived(var18, player.namePair.getRaw(), var17);
                        }
                    }
                }

                packet.pos = var14 + var15;
            }

            if ((var6 & 0x1000) != 0) {
                GPI.aByteArray596[var4] = packet.g1b();
            }

            if ((var6 & 0x10) != 0) {
                var7 = packet.g2s_le();
                if (var7 == 65535) {
                    var7 = -1;
                }

                var8 = packet.g1();
                animate(player, var7, var8);
            }

            if ((var6 & 0x200) != 0) {
                var9 = packet.g1b();
            }

            if ((var6 & 0x400) != 0) {
                player.startX = packet.g1_alt2();
                player.startY = packet.g1_alt1();
                player.endX = packet.g1_alt2();
                player.endY = packet.g1_alt2();
                player.forceMovementStartCycle = packet.g2() + client.ticks;
                player.forceMovementEndCycle = packet.g2s_le() + client.ticks;
                player.anInt2019 = packet.g2_alt4();
                if (player.aBoolean1904) {
                    player.startX += player.updateX;
                    player.startY += player.updateY;
                    player.endX += player.updateX;
                    player.endY += player.updateY;
                    player.pathQueueSize = 0;
                } else {
                    player.startX += player.pathXQueue[0];
                    player.startY += player.pathYQueue[0];
                    player.endX += player.pathXQueue[0];
                    player.endY += player.pathYQueue[0];
                    player.pathQueueSize = 1;
                }

                player.anInt2023 = 0;
            }

            if ((var6 & 0x4) != 0) {
                var7 = packet.g1_alt3();
                int var19;
                int var22;
                int var23;
                if (var7 > 0) {
                    for (var8 = 0; var8 < var7; ++var8) {
                        var14 = -1;
                        var15 = -1;
                        var23 = -1;
                        var22 = packet.gSmarts();
                        if (var22 == 32767) {
                            var22 = packet.gSmarts();
                            var15 = packet.gSmarts();
                            var14 = packet.gSmarts();
                            var23 = packet.gSmarts();
                        } else if (var22 != 32766) {
                            var15 = packet.gSmarts();
                        } else {
                            var22 = -1;
                        }

                        var19 = packet.gSmarts();
                        player.addHitSplat(var22, var15, var14, var23, client.ticks, var19);
                    }
                }

                var8 = packet.g1_alt4();
                if (var8 > 0) {
                    for (var22 = 0; var22 < var8; ++var22) {
                        var14 = packet.gSmarts();
                        var15 = packet.gSmarts();
                        if (var15 != 32767) {
                            var23 = packet.gSmarts();
                            var19 = packet.g1_alt3();
                            var18 = var15 > 0 ? packet.g1_alt3() : var19;
                            player.updateHealthBar(var14, client.ticks, var15, var23, var19, var18);
                        } else {
                            player.method1503(var14);
                        }
                    }
                }
            }

            if ((var6 & 0x40) != 0) {
                player.targetIndex = packet.g2s_le();
                if (player.targetIndex == 65535) {
                    player.targetIndex = -1;
                }
            }

            if ((var6 & 0x20) != 0) {
                player.transformedOrientation = packet.g2();
                if (player.pathQueueSize == 0) {
                    player.orientation = player.transformedOrientation;
                    player.transformedOrientation = -1;
                }
            }

            if (player.aBoolean1904) {
                if (var9 == 127) {
                    player.method1414(player.updateX, player.updateY);
                } else {
                    byte var21;
                    if (var9 != -1) {
                        var21 = var9;
                    } else {
                        var21 = GPI.aByteArray596[var4];
                    }

                    player.method1415(player.updateX, player.updateY, var21);
                }
            }
        }

        if (packet.pos - var2 != var1) {
            throw new RuntimeException(packet.pos - var2 + " " + var1);
        }
    }

    public static void method1400() {
        for (int var0 = 0; var0 < GPI.playerCount; ++var0) {
            PlayerEntity var1 = client.players[GPI.playerIndices[var0]];
            var1.setIsBefriendedDefaults();
        }

        Keyboard.method100();
        if (client.friendChat != null) {
            client.friendChat.method1392();
        }

    }

    public static void update(BitBuffer packet) {
        packet.bitAccess();
        int index = client.playerIndex;
        PlayerEntity player = local = client.players[index] = new PlayerEntity();
        player.index = index;
        int var4 = packet.g(30);
        byte var5 = (byte) (var4 >> 28);
        int var6 = var4 >> 14 & 16383;
        int var7 = var4 & 16383;
        player.pathXQueue[0] = var6 - client.baseX;
        player.absoluteX = (player.pathXQueue[0] << 7) + (player.boundSize() << 6);
        player.pathYQueue[0] = var7 - client.baseY;
        player.absoluteY = (player.pathYQueue[0] << 7) + (player.boundSize() << 6);
        SceneGraph.floorLevel = player.floorLevel = var5;
        if (GPI.buffers[index] != null) {
            player.decode(GPI.buffers[index]);
        }

        GPI.playerCount = 0;
        GPI.playerIndices[++GPI.playerCount - 1] = index;
        GPI.playerSkipFlags[index] = 0;
        GPI.globalPlayerCount = 0;

        for (int var8 = 1; var8 < 2048; ++var8) {
            if (index != var8) {
                int var9 = packet.g(18);
                int var10 = var9 >> 16;
                int var11 = var9 >> 8 & 597;
                int var12 = var9 & 597;
                GPI.playerLocations[var8] = (var11 << 14) + var12 + (var10 << 28);
                GPI.playerOrientations[var8] = 0;
                GPI.playerTargetIndices[var8] = -1;
                GPI.globalPlayerIndices[++GPI.globalPlayerCount - 1] = var8;
                GPI.playerSkipFlags[var8] = 0;
            }
        }

        packet.byteAccess();
    }

    public static void animate(PlayerEntity player, int animation, int delay) {
        if (player.animation == animation && animation != -1) {
            int replayMode = AnimationSequence.get(animation).replayMode;
            if (replayMode == 1) {
                player.animationFrame = 0;
                player.animationFrameCycle = 0;
                player.animationDelay = delay;
                player.animationLoopCounts = 0;
            }

            if (replayMode == 2) {
                player.animationLoopCounts = 0;
            }
        } else if (animation == -1 || player.animation == -1 || AnimationSequence.get(animation).priority >= AnimationSequence.get(player.animation).priority) {
            player.animation = animation;
            player.animationFrame = 0;
            player.animationFrameCycle = 0;
            player.animationDelay = delay;
            player.animationLoopCounts = 0;
            player.anInt2023 = player.pathQueueSize;
        }

    }

    public static void method406(PlayerEntity var0, int var1, int var2) {
        int var4 = var0.pathXQueue[0];
        int var5 = var0.pathYQueue[0];
        int var6 = var0.boundSize();
        if (var4 >= var6 && var4 < 104 - var6 && var5 >= var6 && var5 < 104 - var6) {
            if (var1 >= var6 && var1 < 104 - var6 && var2 >= var6 && var2 < 104 - var6) {
                int var9 = var0.boundSize();
                client.routeStrategy.destinationX = var1;
                client.routeStrategy.destinationY = var2;
                client.routeStrategy.destinationSizeX = 1;
                client.routeStrategy.destinationSizeY = 1;
                DefaultRouteStrategy var10 = client.routeStrategy;
                int var11 = RouteStrategy.getPathLength(var4, var5, var9, var10, client.collisionMaps[var0.floorLevel], client.anIntArray945, client.anIntArray942);
                if (var11 >= 1) {
                    for (int var12 = 0; var12 < var11 - 1; ++var12) {
                        var0.method1413(client.anIntArray945[var12], client.anIntArray942[var12], (byte) 2);
                    }

                }
            }
        }
    }

    public Model getModel() {
        if (model == null) {
            return null;
        }
        AnimationSequence animation = super.animation != -1 && super.animationDelay == 0 ? AnimationSequence.get(super.animation) : null;
        AnimationSequence stance = super.stance != -1 && !aBoolean1905 && (super.stance != super.idleStance || animation == null) ? AnimationSequence.get(super.stance) : null;
        Model var3 = model.getModel(animation, super.animationFrame, stance, super.stanceFrame);
        if (var3 == null) {
            return null;
        }
        var3.computeBounds();
        super.modelHeight = var3.height;
        Model var4;
        Model[] var5;
        if (!aBoolean1905 && super.effect != -1 && super.effectFrame != -1) {
            var4 = EffectAnimation.get(super.effect).method1004(super.effectFrame);
            if (var4 != null) {
                var4.offsetBy(0, -super.anInt2014, 0);
                var5 = new Model[]{var3, var4};
                var3 = new Model(var5, 2);
            }
        }

        if (!aBoolean1905 && transformedNpcModel != null) {
            if (client.ticks >= animationEndCycle) {
                transformedNpcModel = null;
            }

            if (client.ticks >= animationStartCycle && client.ticks < animationEndCycle) {
                var4 = transformedNpcModel;
                var4.offsetBy(maxX * super.absoluteX, baseTileHeight - tileHeight, maxY * super.absoluteY);
                if (super.orientation == 512) {
                    var4.rotate90Y();
                    var4.rotate90Y();
                    var4.rotate90Y();
                } else if (super.orientation == 1024) {
                    var4.rotate90Y();
                    var4.rotate90Y();
                } else if (super.orientation == 1536) {
                    var4.rotate90Y();
                }

                var5 = new Model[]{var3, var4};
                var3 = new Model(var5, 2);
                if (super.orientation == 512) {
                    var4.rotate90Y();
                } else if (super.orientation == 1024) {
                    var4.rotate90Y();
                    var4.rotate90Y();
                } else if (super.orientation == 1536) {
                    var4.rotate90Y();
                    var4.rotate90Y();
                    var4.rotate90Y();
                }

                var4.offsetBy(super.absoluteX - maxX, tileHeight - baseTileHeight, super.absoluteY - maxY);
            }
        }

        var3.fitsSingleTile = true;
        return var3;
    }

    public void method828() {
        friendsChatUserStatus = client.friendChat != null && client.friendChat.isCached(namePair) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
    }

    public void method775() {
        befriendedPlayerStatus = client.relationshipManager.isFriendLoggedIn(namePair) ? AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555 : AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1553;
    }

    public void method1414(int var1, int var2) {
        super.pathQueueSize = 0;
        super.anInt2023 = 0;
        super.anInt2022 = 0;
        super.pathXQueue[0] = var1;
        super.pathYQueue[0] = var2;
        int size = boundSize();
        super.absoluteX = size * 64 + super.pathXQueue[0] * 128;
        super.absoluteY = size * 64 + super.pathYQueue[0] * 128;
    }

    public int boundSize() {
        return model != null && model.transformedNpcId != -1 ? NpcDefinition.get(model.transformedNpcId).size : 1;
    }

    public void setIsBefriendedDefaults() {
        befriendedPlayerStatus = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
    }

    public void method1413(int var1, int var2, byte var3) {
        if (super.pathQueueSize < 9) {
            ++super.pathQueueSize;
        }

        for (int i = super.pathQueueSize; i > 0; --i) {
            super.pathXQueue[i] = super.pathXQueue[i - 1];
            super.pathYQueue[i] = super.pathYQueue[i - 1];
            super.pathQueueTraversed[i] = super.pathQueueTraversed[i - 1];
        }

        super.pathXQueue[0] = var1;
        super.pathYQueue[0] = var2;
        super.pathQueueTraversed[0] = var3;
    }

    public void decode(Buffer buffer) {
        buffer.pos = 0;
        int gender = buffer.g1();
        prayerIcon = buffer.g1b();
        skullIcon = buffer.g1b();
        int transformedNpcId = -1;
        team = 0;

        int[] equipment = new int[12];
        for (int i = 0; i < 12; ++i) {
            int equipped = buffer.g1();
            if (equipped == 0) {
                equipment[i] = 0;
                continue;
            }

            int offset = buffer.g1();
            equipment[i] = offset + (equipped << 8);
            if (i == 0 && equipment[0] == 65535) {
                transformedNpcId = buffer.g2();
                break;
            }

            if (equipment[i] >= 512) {
                int team = ItemDefinition.get(equipment[i] - 512).team;
                if (team != 0) {
                    this.team = team;
                }
            }
        }

        int[] appearance = new int[5];
        for (int i = 0; i < 5; ++i) {
            int id = buffer.g1();
            if (id < 0 || id >= PlayerModel.colors[i].length) {
                id = 0;
            }
            appearance[i] = id;
        }

        super.idleStance = buffer.g2();
        if (super.idleStance == 65535) {
            super.idleStance = -1;
        }

        super.turnLeftStance = buffer.g2();
        if (super.turnLeftStance == 65535) {
            super.turnLeftStance = -1;
        }

        super.turnRightStance = super.turnLeftStance;
        super.walkStance = buffer.g2();
        if (super.walkStance == 65535) {
            super.walkStance = -1;
        }

        super.turnAroundStance = buffer.g2();
        if (super.turnAroundStance == 65535) {
            super.turnAroundStance = -1;
        }

        super.walkLeftStance = buffer.g2();
        if (super.walkLeftStance == 65535) {
            super.walkLeftStance = -1;
        }

        super.walkRightStance = buffer.g2();
        if (super.walkRightStance == 65535) {
            super.walkRightStance = -1;
        }

        super.runStance = buffer.g2();
        if (super.runStance == 65535) {
            super.runStance = -1;
        }

        namePair = new NamePair(buffer.gstr(), PreciseWorldMapAreaChunk.loginTypeParameter);
        setIsBefriendedDefaults();
        setIsInFriendsChatDefaults();
        if (this == local) {
            Jagexception.localPlayerName = namePair.getRaw();
        }

        combatLevel = buffer.g1();
        skillLevel = buffer.g2();
        hidden = buffer.g1() == 1;
        if (client.gameTypeId == 0 && client.rights >= 2) {
            hidden = false;
        }

        if (model == null) {
            model = new PlayerModel();
        }

        model.update(equipment, appearance, gender == 1, transformedNpcId);
    }

    public void setIsInFriendsChatDefaults() {
        friendsChatUserStatus = AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554;
    }

    public boolean isDefined() {
        return model != null;
    }

    public boolean method609() {
        if (befriendedPlayerStatus == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
            method775();
        }

        return befriendedPlayerStatus == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
    }

    public boolean method258() {
        if (friendsChatUserStatus == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1554) {
            method828();
        }

        return friendsChatUserStatus == AssociateStatus.A_CHAT_LINE_PRIVACY_TYPE___1555;
    }

    public void method1415(int sceneX, int sceneY, byte var3) {
        if (super.animation != -1 && AnimationSequence.get(super.animation).walkingPrecedence == 1) {
            super.animation = -1;
        }

        super.transformedOrientation = -1;
        if (sceneX >= 0 && sceneX < 104 && sceneY >= 0 && sceneY < 104) {
            if (super.pathXQueue[0] >= 0 && super.pathXQueue[0] < 104 && super.pathYQueue[0] >= 0 && super.pathYQueue[0] < 104) {
                if (var3 == 2) {
                    method406(this, sceneX, sceneY);
                }

                method1413(sceneX, sceneY, var3);
            } else {
                method1414(sceneX, sceneY);
            }
        } else {
            method1414(sceneX, sceneY);
        }

    }
}
