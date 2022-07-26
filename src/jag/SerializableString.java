package jag;

import jag.commons.time.Clock;
import jag.game.GameShell;
import jag.game.Server;
import jag.game.client;
import jag.game.relationship.AssociateComparator;
import jag.game.scene.SceneGraph;
import jag.game.scene.entity.EntityUID;
import jag.game.scene.entity.NpcEntity;
import jag.game.type.HitsplatDefinition;
import jag.game.type.ItemDefinition;
import jag.graphics.IndexedSprite;
import jag.js5.Bzip2Entry;
import jag.opcode.Buffer;
import jag.opcode.login.LoginStep;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

public final class SerializableString implements Serializable {

    public static IndexedSprite[] scrollbars;

    public static int getGcTime() {
        int var0 = 0;
        if (AssociateComparator.aGarbageCollectorMXBean1556 == null || !AssociateComparator.aGarbageCollectorMXBean1556.isValid()) {
            try {

                for (GarbageCollectorMXBean var2 : ManagementFactory.getGarbageCollectorMXBeans()) {
                    if (var2.isValid()) {
                        AssociateComparator.aGarbageCollectorMXBean1556 = var2;
                        GameShell.aLong1319 = -1L;
                        GameShell.aLong1298 = -1L;
                    }
                }
            } catch (Throwable ignored) {
            }
        }

        if (AssociateComparator.aGarbageCollectorMXBean1556 != null) {
            long var3 = Clock.now();
            long var5 = AssociateComparator.aGarbageCollectorMXBean1556.getCollectionTime();
            if (GameShell.aLong1298 != -1L) {
                long var7 = var5 - GameShell.aLong1298;
                long var9 = var3 - GameShell.aLong1319;
                if (var9 != 0L) {
                    var0 = (int) (var7 * 100L / var9);
                }
            }

            GameShell.aLong1298 = var5;
            GameShell.aLong1319 = var3;
        }

        return var0;
    }

    public static void loadNpcsIntoScene(boolean prioritized) {
        for (int i = 0; i < client.npcCount; ++i) {
            NpcEntity npc = client.npcs[client.npcIndices[i]];
            if (npc != null && npc.isDefined() && npc.definition.renderingPrioritized == prioritized && npc.definition.validate()) {
                int sceneX = npc.absoluteX >> 7;
                int sceneY = npc.absoluteY >> 7;
                if (sceneX >= 0 && sceneX < 104 && sceneY >= 0 && sceneY < 104) {
                    if (npc.boundSize == 1 && (npc.absoluteX & 127) == 64 && (npc.absoluteY & 127) == 64) {
                        if (client.pathingEntityRenderPositions[sceneX][sceneY] == client.viewportRenderCount) {
                            continue;
                        }

                        client.pathingEntityRenderPositions[sceneX][sceneY] = client.viewportRenderCount;
                    }

                    long uid = EntityUID.build(0, 0, 1, !npc.definition.interactable, client.npcIndices[i]);
                    npc.renderCycle = client.engineCycle;
                    client.sceneGraph.addEntityMarker(SceneGraph.floorLevel, npc.absoluteX, npc.absoluteY, SceneGraph.getTileHeight(npc.boundSize - 64 + npc.absoluteX, npc.boundSize - 64 + npc.absoluteY, SceneGraph.floorLevel), npc.boundSize - 64 + 60, npc, npc.turnOrientation, uid, npc.stretch);
                }
            }
        }

    }

    public static void setWorld(Server var0) {
        if (var0.isMembers() != client.membersWorld) {
            client.membersWorld = var0.isMembers();
            ItemDefinition.setLoadMembers(var0.isMembers());
        }

        LoginStep.currentDomain = var0.domain;
        client.currentWorld = var0.id;
        client.currentWorldMask = var0.mask;
        HitsplatDefinition.anInt1929 = client.gameType == 0 ? 43594 : var0.id + 40000;
        Bzip2Entry.anInt1579 = client.gameType == 0 ? 443 : var0.id + 50000;
        NpcEntity.port = HitsplatDefinition.anInt1929;
    }

    void encodeAsString(String value, Buffer buffer) {
        buffer.pcstr(value);
    }

    public Object decode(Buffer buffer) {
        return buffer.gstr();
    }

    public void encode(Object value, Buffer buffer) {
        encodeAsString((String) value, buffer);
    }
}
