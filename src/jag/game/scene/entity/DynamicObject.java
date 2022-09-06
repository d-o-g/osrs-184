package jag.game.scene.entity;

import jag.audi.AudioSystem;
import jag.audi.PcmStream;
import jag.game.client;
import jag.game.scene.SceneGraph;
import jag.game.type.AnimationSequence;
import jag.game.type.ObjectDefinition;
import jag.opcode.ClientProt;
import jag.statics.SceneGraphRenderData;

public class DynamicObject extends Entity {

    public final int id;
    public final int type;
    public final int orientation;
    public final int floorLevel;
    public final int sceneX;
    public final int sceneY;

    public AnimationSequence sequence;

    public int anInt379;
    public int anInt372;

    public DynamicObject(int id, int type, int orientation, int floorLevel, int sceneX, int sceneY, int animationId, boolean var8, Entity var9) {
        this.id = id;
        this.type = type;
        this.orientation = orientation;
        this.floorLevel = floorLevel;
        this.sceneX = sceneX;
        this.sceneY = sceneY;
        if (animationId != -1) {
            sequence = AnimationSequence.get(animationId);
            anInt372 = 0;
            anInt379 = client.engineCycle - 1;
            if (sequence.replayMode == 0 && var9 instanceof DynamicObject) {
                DynamicObject var10 = (DynamicObject) var9;
                if (var10.sequence == sequence) {
                    anInt372 = var10.anInt372;
                    anInt379 = var10.anInt379;
                    return;
                }
            }

            if (var8 && sequence.loopOffset != -1) {
                anInt372 = (int) (Math.random() * (double) sequence.frameIds.length);
                anInt379 -= (int) (Math.random() * (double) sequence.frameLengths[anInt372]);
            }
        }

    }

    public static void gc() {
        client.stream.stop();
        client.gc();
        client.sceneGraph.init();

        for (int var0 = 0; var0 < 4; ++var0) {
            client.collisionMaps[var0].initialize();
        }

        System.gc();
        AudioSystem.state = 1;
        AudioSystem.tracks = null;
        AudioSystem.trackGroup = -1;
        AudioSystem.trackFile = -1;
        AudioSystem.volume = 0;
        AudioSystem.aBoolean620 = false;
        AudioSystem.pcmSampleLength = 2;
        client.anInt898 = -1;
        client.aBoolean904 = false;
        ClientProt.method4();
        client.setGameState(10);
    }

    public static void method1507(PcmStream var0) {
        var0.aBoolean665 = false;
        if (var0.aVorbisNode_667 != null) {
            var0.aVorbisNode_667.anInt112 = 0;
        }

        for (PcmStream var1 = var0.method307(); var1 != null; var1 = var0.method308()) {
            method1507(var1);
        }

    }

    public static void loadProjectilesIntoScene() {
        Projectile projectile = client.projectiles.head();
        while (projectile != null) {
            if (projectile.floorLevel == SceneGraph.floorLevel && client.engineCycle <= projectile.endCycle) {
                if (client.engineCycle >= projectile.startCycle) {
                    if (projectile.targetIndex > 0) {
                        NpcEntity npc = client.npcs[projectile.targetIndex - 1];
                        if (npc != null && npc.absoluteX >= 0 && npc.absoluteX < 13312 && npc.absoluteY >= 0 && npc.absoluteY < 13312) {
                            projectile.target(npc.absoluteX, npc.absoluteY, SceneGraph.getTileHeight(npc.absoluteX, npc.absoluteY, projectile.floorLevel) - projectile.targetHeight, client.engineCycle);
                        }
                    }

                    if (projectile.targetIndex < 0) {
                        int var2 = -projectile.targetIndex - 1;
                        PlayerEntity player;
                        if (var2 == client.playerIndex) {
                            player = PlayerEntity.local;
                        } else {
                            player = client.players[var2];
                        }

                        if (player != null && player.absoluteX >= 0 && player.absoluteX < 13312 && player.absoluteY >= 0 && player.absoluteY < 13312) {
                            projectile.target(player.absoluteX, player.absoluteY, SceneGraph.getTileHeight(player.absoluteX, player.absoluteY, projectile.floorLevel) - projectile.targetHeight, client.engineCycle);
                        }
                    }

                    projectile.method1193(client.anInt972);
                    client.sceneGraph.addEntityMarker(SceneGraph.floorLevel, (int) projectile.absoluteX, (int) projectile.absoluteY, (int) projectile.aDouble1660, 60, projectile, projectile.xRotation, -1L, false);
                }
            } else {
                projectile.unlink();
            }
            projectile = client.projectiles.next();
        }

    }

    protected final Model getModel() {
        if (sequence != null) {
            int var1 = client.engineCycle - anInt379;
            if (var1 > 100 && sequence.loopOffset > 0) {
                var1 = 100;
            }

            label56:
            {
                do {
                    do {
                        if (var1 <= sequence.frameLengths[anInt372]) {
                            break label56;
                        }

                        var1 -= sequence.frameLengths[anInt372];
                        ++anInt372;
                    } while (anInt372 < sequence.frameIds.length);

                    anInt372 -= sequence.loopOffset;
                } while (anInt372 >= 0 && anInt372 < sequence.frameIds.length);

                sequence = null;
            }

            anInt379 = client.engineCycle - var1;
        }

        ObjectDefinition var2 = ObjectDefinition.get(id);
        if (var2.transformIds != null) {
            var2 = var2.transform();
        }

        if (var2 == null) {
            return null;
        }
        int var3;
        int var4;
        if (orientation != 1 && orientation != 3) {
            var3 = var2.sizeX;
            var4 = var2.sizeY;
        } else {
            var3 = var2.sizeY;
            var4 = var2.sizeX;
        }

        int var5 = (var3 >> 1) + sceneX;
        int var6 = (var3 + 1 >> 1) + sceneX;
        int var7 = (var4 >> 1) + sceneY;
        int var8 = (var4 + 1 >> 1) + sceneY;
        int[][] var9 = SceneGraphRenderData.tileHeights[floorLevel];
        int var10 = var9[var6][var7] + var9[var5][var7] + var9[var5][var8] + var9[var6][var8] >> 2;
        int var11 = (sceneX << 7) + (var3 << 6);
        int var12 = (sceneY << 7) + (var4 << 6);
        return var2.method1107(type, orientation, var9, var11, var10, var12, sequence, anInt372);
    }
}
