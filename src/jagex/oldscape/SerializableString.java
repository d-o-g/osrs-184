package jagex.oldscape;

import jagex.core.time.Clock;
import jagex.jagex3.client.applet.GameShell;
import jagex.oldscape.client.*;
import jagex.oldscape.client.social.AssociateComparator;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.scene.entity.EntityUID;
import jagex.oldscape.client.scene.entity.NpcEntity;
import jagex.jagex3.graphics.IndexedSprite;
import jagex.messaging.Buffer;

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
          npc.renderCycle = client.ticks;
          client.sceneGraph.addEntityMarker(SceneGraph.floorLevel, npc.absoluteX, npc.absoluteY, SceneGraph.getTileHeight(npc.boundSize - 64 + npc.absoluteX, npc.boundSize - 64 + npc.absoluteY, SceneGraph.floorLevel), npc.boundSize - 64 + 60, npc, npc.pathOrientation, uid, npc.stretch);
        }
      }
    }

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
