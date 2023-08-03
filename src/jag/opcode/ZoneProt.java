package jag.opcode;

import jag.commons.collection.NodeDeque;
import jag.game.client;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.*;
import jag.game.type.ObjectDefinition;
import jag.statics.SceneGraphRenderData;

public class ZoneProt {

    public static final ZoneProt LOC_ADD_CHANGE = new ZoneProt(ServerProt.LOC_ADD_CHANGE);
    public static final ZoneProt SOUND_AREA = new ZoneProt(ServerProt.SOUND_AREA);
    public static final ZoneProt PLAYER_LOC_TRANSMOG = new ZoneProt(ServerProt.PLAYER_LOC_TRANSMOG);
    public static final ZoneProt PICKABLE_ADD = new ZoneProt(ServerProt.PICKABLE_ADD);
    public static final ZoneProt MAP_PROJANIM = new ZoneProt(ServerProt.MAP_PROJANIM);
    public static final ZoneProt LOC_DEL = new ZoneProt(ServerProt.LOC_DEL);
    public static final ZoneProt LOC_ANIM = new ZoneProt(ServerProt.LOC_ANIM);
    public static final ZoneProt MAP_ANIM = new ZoneProt(ServerProt.MAP_ANIM);
    public static final ZoneProt PICKABLE_COUNT = new ZoneProt(ServerProt.PICKABLE_COUNT);
    public static final ZoneProt PICKABLE_REMOVE = new ZoneProt(ServerProt.PICKABLE_REMOVE);

    private final ServerProt serverProt;

    ZoneProt(ServerProt serverProt) {
        this.serverProt = serverProt;
    }

    public static ZoneProt[] values() {
        return new ZoneProt[]{LOC_ADD_CHANGE, SOUND_AREA, PLAYER_LOC_TRANSMOG, PICKABLE_ADD, MAP_PROJANIM, LOC_DEL, LOC_ANIM, MAP_ANIM, PICKABLE_COUNT, PICKABLE_REMOVE};
    }

    public static void process(ZoneProt update) {
        BitBuffer inbuff = client.stream.inbuffer;
        if (PLAYER_LOC_TRANSMOG == update) {
            byte objXStart = inbuff.g1b();
            byte objYStart = inbuff.g1b();
            int sceneY = inbuff.g1();
            int objectType = sceneY >> 2;
            int orientation = sceneY & 3;
            int stubType = client.OBJECT_TYPE_TO_STUB_TYPE[objectType];
            int position = inbuff.ig1_alt1();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            byte objXEnd = inbuff.ig1();
            int startCycle = inbuff.g2_alt4();
            int endCycle = inbuff.g2_le();
            int objectId = inbuff.g2();
            int playerIndex = inbuff.g2_alt4();
            byte objYEnd = inbuff.g1_alt2();

            PlayerEntity player = playerIndex == client.playerIndex ? PlayerEntity.local : client.players[playerIndex];
            if (player != null) {
                ObjectDefinition def = ObjectDefinition.get(objectId);
                int width;
                int height;
                if (orientation != 1 && orientation != 3) {
                    width = def.sizeX;
                    height = def.sizeY;
                } else {
                    width = def.sizeY;
                    height = def.sizeX;
                }

                int x1 = x + (width >> 1);
                int x2 = x + (width + 1 >> 1);
                int y1 = y + (height >> 1);
                int y2 = y + (height + 1 >> 1);
                int[][] heights = SceneGraphRenderData.tileHeights[SceneGraph.floorLevel];
                int modelZ = heights[x1][y1] + heights[x2][y1] + heights[x1][y2] + heights[x2][y2] >> 2;
                int modelX = (x << 7) + (width << 6);
                int modelY = (y << 7) + (height << 6);
                Model model = def.getLitModel(objectType, orientation, heights, modelX, modelZ, modelY);
                if (model != null) {
                    PendingSpawn.pushLater(SceneGraph.floorLevel, x, y, stubType, -1, 0, 0, startCycle + 1, endCycle + 1);
                    player.animationStartCycle = startCycle + client.ticks;
                    player.animationEndCycle = endCycle + client.ticks;
                    player.transformedNpcModel = model;
                    player.maxX = x * 128 + width * 64;
                    player.maxY = y * 128 + height * 64;
                    player.baseTileHeight = modelZ;
                    if (objXStart > objXEnd) {
                        byte temp = objXStart;
                        objXStart = objXEnd;
                        objXEnd = temp;
                    }

                    if (objYStart > objYEnd) {
                        byte temp = objYStart;
                        objYStart = objYEnd;
                        objYEnd = temp;
                    }

                    player.startObjectX = x + objXStart;
                    player.endObjectX = x + objXEnd;
                    player.startObjectY = y + objYStart;
                    player.endObjectY = y + objYEnd;
                }
            }
        } else if (MAP_ANIM == update) {
            int animation = inbuff.g2_alt4();
            int position = inbuff.ig1_alt1();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            int config = inbuff.g1();
            int objectType = config >> 2;
            int rotation = config & 3;
            int stubType = client.OBJECT_TYPE_TO_STUB_TYPE[objectType];
            if (x >= 0 && y >= 0 && x < 103 && y < 103) {
                if (stubType == 0) {
                    Boundary boundary = client.sceneGraph.getBoundaryAt(SceneGraph.floorLevel, x, y);
                    if (boundary != null) {
                        int id = EntityUID.getObjectId(boundary.uid);
                        if (objectType == 2) {
                            boundary.entity = new DynamicObject(id, 2, rotation + 4, SceneGraph.floorLevel, x, y, animation, false, boundary.entity);
                            boundary.linkedEntity = new DynamicObject(id, 2, rotation + 1 & 3, SceneGraph.floorLevel, x, y, animation, false, boundary.linkedEntity);
                        } else {
                            boundary.entity = new DynamicObject(id, objectType, rotation, SceneGraph.floorLevel, x, y, animation, false, boundary.entity);
                        }
                    }
                }

                if (stubType == 1) {
                    BoundaryDecor decor = client.sceneGraph.getBoundaryDecorAt(SceneGraph.floorLevel, x, y);
                    if (decor != null) {
                        int id = EntityUID.getObjectId(decor.uid);
                        if (objectType != 4 && objectType != 5) {
                            if (objectType == 6) {
                                decor.entity = new DynamicObject(id, 4, rotation + 4, SceneGraph.floorLevel, x, y, animation, false, decor.entity);
                            } else if (objectType == 7) {
                                decor.entity = new DynamicObject(id, 4, (rotation + 2 & 3) + 4, SceneGraph.floorLevel, x, y, animation, false, decor.entity);
                            } else if (objectType == 8) {
                                decor.entity = new DynamicObject(id, 4, rotation + 4, SceneGraph.floorLevel, x, y, animation, false, decor.entity);
                                decor.linkedEntity = new DynamicObject(id, 4, (rotation + 2 & 3) + 4, SceneGraph.floorLevel, x, y, animation, false, decor.linkedEntity);
                            }
                        } else {
                            decor.entity = new DynamicObject(id, 4, rotation, SceneGraph.floorLevel, x, y, animation, false, decor.entity);
                        }
                    }
                }

                if (stubType == 2) {
                    EntityMarker entity = client.sceneGraph.getEntityMarkerAt(SceneGraph.floorLevel, x, y);
                    if (objectType == 11) {
                        objectType = 10;
                    }

                    if (entity != null) {
                        entity.entity = new DynamicObject(EntityUID.getObjectId(entity.uid), objectType, rotation, SceneGraph.floorLevel, x, y, animation, false, entity.entity);
                    }
                }

                if (stubType == 3) {
                    TileDecor decor = client.sceneGraph.getTileDecorAt(SceneGraph.floorLevel, x, y);
                    if (decor != null) {
                        decor.entity = new DynamicObject(EntityUID.getObjectId(decor.uid), 22, rotation, SceneGraph.floorLevel, x, y, animation, false, decor.entity);
                    }
                }
            }

        } else if (PICKABLE_REMOVE == update) {
            int position = inbuff.g1_alt3();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            int uid = inbuff.g2s_le();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                NodeDeque<Pickable> deque = client.pickables[SceneGraph.floorLevel][x][y];
                if (deque != null) {
                    for (Pickable pickable = deque.head(); pickable != null; pickable = deque.next()) {
                        if ((uid & 0x7fff) == pickable.id) {
                            pickable.unlink();
                            break;
                        }
                    }

                    if (deque.head() == null) {
                        client.pickables[SceneGraph.floorLevel][x][y] = null;
                    }

                    Pickable.refresh(x, y);
                }
            }

        } else if (LOC_DEL == update) {
            int config = inbuff.ig1_alt1();
            int objectType = config >> 2;
            int orientation = config & 3;
            int stubType = client.OBJECT_TYPE_TO_STUB_TYPE[objectType];
            int position = inbuff.g1_alt3();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                PendingSpawn.pushLater(SceneGraph.floorLevel, x, y, stubType, -1, objectType, orientation, 0, -1);
            }

        } else if (PICKABLE_ADD == update) {
            int stackSize = inbuff.g2_alt4();
            int id = inbuff.g2_le();
            int position = inbuff.g1_alt3();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                Pickable pickable = new Pickable();
                pickable.id = id;
                pickable.stackSize = stackSize;
                if (client.pickables[SceneGraph.floorLevel][x][y] == null) {
                    client.pickables[SceneGraph.floorLevel][x][y] = new NodeDeque<>();
                }

                client.pickables[SceneGraph.floorLevel][x][y].add(pickable);
                Pickable.refresh(x, y);
            }

        } else if (SOUND_AREA == update) {
            int position = inbuff.g1_alt4();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            int config = inbuff.g1_alt4();
            int bounds = config >> 4 & 15;
            int loop = config & 7;
            int id = inbuff.g2();
            int delay = inbuff.g1_alt4();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                int range = bounds + 1;
                if (PlayerEntity.local.pathXQueue[0] >= x - range
                        && PlayerEntity.local.pathXQueue[0] <= range + x
                        && PlayerEntity.local.pathYQueue[0] >= y - range
                        && PlayerEntity.local.pathYQueue[0] <= range + y
                        && client.areaSoundEffectVolume != 0
                        && loop > 0
                        && client.audioEffectCount < 50) {
                    client.audioEffectIds[client.audioEffectCount] = id;
                    client.audioEffectLoops[client.audioEffectCount] = loop;
                    client.audioEffectDelays[client.audioEffectCount] = delay;
                    client.audioEffects[client.audioEffectCount] = null;
                    client.audioEffectPositions[client.audioEffectCount] = bounds + (y << 8) + (x << 16);
                    ++client.audioEffectCount;
                }
            }
        } else if (LOC_ADD_CHANGE == update) {
            int uid = inbuff.g1();
            int objectType = uid >> 2;
            int sceneY = uid & 3;
            int stubType = client.OBJECT_TYPE_TO_STUB_TYPE[objectType];
            int position = inbuff.g1_alt3();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            int objectId = inbuff.g2();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                PendingSpawn.pushLater(SceneGraph.floorLevel, x, y, stubType, objectId, objectType, sceneY, 0, -1);
            }

        } else if (MAP_PROJANIM == update) {
            int id = inbuff.g2s_le();
            int position = inbuff.g1_alt4();
            int startX = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int startY = (position & 7) + SceneGraph.regionChunkY;
            int startCycle = inbuff.g2_le();
            int endCycle = inbuff.g2s_le();
            byte yDistance = inbuff.g1_alt1();
            int targetHeight = inbuff.g1_alt4() * 4;
            int targetDistance = inbuff.g1();
            int slope = inbuff.g1_alt3();
            byte xDistance = inbuff.g1_alt2();
            int targetIndex = inbuff.g2_alt1();
            int height = inbuff.g1_alt4() * 4;
            int targetX = xDistance + startX;
            int targetY = yDistance + startY;
            if (startX >= 0 && startY >= 0 && startX < 104 && startY < 104 && targetX >= 0 && targetY >= 0 && targetX < 104 && targetY < 104 && id != 65535) {
                startX = startX * 128 + 64;
                startY = startY * 128 + 64;
                targetX = targetX * 128 + 64;
                targetY = targetY * 128 + 64;
                Projectile proj = new Projectile(
                        id,
                        SceneGraph.floorLevel, startX, startY, SceneGraph.getTileHeight(startX, startY, SceneGraph.floorLevel) - height,
                        startCycle + client.ticks, endCycle + client.ticks,
                        slope, targetDistance, targetIndex, targetHeight
                );
                proj.target(targetX, targetY, SceneGraph.getTileHeight(targetX, targetY, SceneGraph.floorLevel) - targetHeight, startCycle + client.ticks);
                client.projectiles.add(proj);
            }

        } else if (PICKABLE_COUNT == update) {
            int uid = inbuff.g2_le();
            int stackSize = inbuff.g2_alt4();
            int position = inbuff.ig1_alt1();
            int x = (position >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (position & 7) + SceneGraph.regionChunkY;
            int oldStackSize = inbuff.g2_alt4();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                NodeDeque<Pickable> deque = client.pickables[SceneGraph.floorLevel][x][y];
                if (deque != null) {
                    for (Pickable pickable = deque.head(); pickable != null; pickable = deque.next()) {
                        if ((uid & 0x7fff) == pickable.id && oldStackSize == pickable.stackSize) {
                            pickable.stackSize = stackSize;
                            break;
                        }
                    }

                    Pickable.refresh(x, y);
                }
            }

        } else if (LOC_ANIM == update) {
            int endCycle = inbuff.g2s_le();
            int height = inbuff.g1_alt3();
            int uid = inbuff.g1_alt3();
            int x = (uid >> 4 & 7) + SceneGraph.regionChunkX;
            int y = (uid & 7) + SceneGraph.regionChunkY;
            int id = inbuff.g2_le();
            if (x >= 0 && y >= 0 && x < 104 && y < 104) {
                x = x * 128 + 64;
                y = y * 128 + 64;
                EffectObject effect = new EffectObject(id, SceneGraph.floorLevel, x, y, SceneGraph.getTileHeight(x, y, SceneGraph.floorLevel) - height, endCycle, client.ticks);
                client.effectObjects.add(effect);
            }
        }
    }

    public ServerProt getServerProt() {
        return serverProt;
    }
}
