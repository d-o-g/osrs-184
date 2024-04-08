package jagex.oldscape.client.scene.entity;

import jagex.oldscape.client.client;
import jagex.oldscape.client.type.*;
import jagex.messaging.BitBuffer;

public final class NpcEntity extends PathingEntity {

  public NpcDefinition definition;

  public NpcEntity() {

  }

  public static void update(boolean var0, BitBuffer buffer) {
    client.npcDeleteCount = 0;
    client.npcUpdateCount = 0;
    method541();

    while (buffer.remaining(client.stream.incomingPacketSize) >= 27) {
      int index = buffer.g(15);
      if (index == 32767) {
        break;
      }

      boolean createdNew = false;
      if (client.npcs[index] == null) {
        client.npcs[index] = new NpcEntity();
        createdNew = true;
      }

      NpcEntity npc = client.npcs[index];
      client.npcIndices[++client.npcCount - 1] = index;
      npc.npcUpdateCycle = client.ticks;
      int x;
      int y;
      if (var0) {
        x = buffer.g(8);
        if (x > 127) {
          x -= 256;
        }
      } else {
        x = buffer.g(5);
        if (x > 15) {
          x -= 32;
        }
      }

      if (var0) {
        y = buffer.g(8);
        if (y > 127) {
          y -= 256;
        }
      } else {
        y = buffer.g(5);
        if (y > 15) {
          y -= 32;
        }
      }

      int orientation = client.pathingEntityOrientations[buffer.g(3)];
      if (createdNew) {
        npc.modelOrientation = npc.pathOrientation = orientation;
      }

      int update = buffer.g(1);
      if (update == 1) {
        client.updatedNpcIndices[++client.npcUpdateCount - 1] = index;
      }

      int traversed = buffer.g(1);
      npc.definition = NpcDefinition.get(buffer.g(14));
      npc.boundSize = npc.definition.size;
      npc.defaultRotation = npc.definition.rotation;
      if (npc.defaultRotation == 0) {
        npc.pathOrientation = 0;
      }

      npc.walkStance = npc.definition.walkStance;
      npc.turnAroundStance = npc.definition.turnAroundStance;
      npc.walkLeftStance = npc.definition.walkLeftStance;
      npc.walkRightStance = npc.definition.walkRightStance;
      npc.idleStance = npc.definition.idleStance;
      npc.turnLeftStance = npc.definition.turnLeftStance;
      npc.turnRightStance = npc.definition.turnRightStance;
      npc.method683(PlayerEntity.local.pathXQueue[0] + x, PlayerEntity.local.pathYQueue[0] + y, traversed == 1);
    }

    buffer.byteAccess();

    for (int i = 0; i < client.npcUpdateCount; ++i) {
      int index = client.updatedNpcIndices[i];
      NpcEntity npc = client.npcs[index];
      int mask = buffer.g1();
      if ((mask & 0x10) != 0) {
        int animation = buffer.g2();
        if (animation == 65535) {
          animation = -1;
        }

        int animationDelay = buffer.g1_alt4();
        if (animation == npc.animation && animation != -1) {
          int var9 = AnimationSequence.get(animation).replayMode;
          if (var9 == 1) {
            npc.animationFrame = 0;
            npc.animationFrameCycle = 0;
            npc.animationDelay = animationDelay;
            npc.animationLoopCounts = 0;
          }

          if (var9 == 2) {
            npc.animationLoopCounts = 0;
          }
        } else if (animation == -1 || npc.animation == -1 || AnimationSequence.get(animation).priority >= AnimationSequence.get(npc.animation).priority) {
          npc.animation = animation;
          npc.animationFrame = 0;
          npc.animationFrameCycle = 0;
          npc.animationDelay = animationDelay;
          npc.animationLoopCounts = 0;
          npc.anInt2023 = npc.pathQueueSize;
        }
      }

      if ((mask & 0x1) != 0) {
        int var7 = buffer.ig1_alt1();
        if (var7 > 0) {
          for (int var8 = 0; var8 < var7; ++var8) {
            int specialType = -1;
            int damage = -1;
            int specialDamage = -1;
            int type = buffer.gSmarts();
            if (type == 32767) {
              type = buffer.gSmarts();
              damage = buffer.gSmarts();
              specialType = buffer.gSmarts();
              specialDamage = buffer.gSmarts();
            } else if (type != 32766) {
              damage = buffer.gSmarts();
            } else {
              type = -1;
            }

            int delay = buffer.gSmarts();
            npc.addHitSplat(type, damage, specialType, specialDamage, client.ticks, delay);
          }
        }

        int var8 = buffer.g1_alt4();
        if (var8 > 0) {
          for (int j = 0; j < var8; ++j) {
            int id = buffer.gSmarts();
            int updateCycle = buffer.gSmarts();
            if (updateCycle != 32767) {
              int delay = buffer.gSmarts();
              int width = buffer.ig1_alt1();
              int currentWidth = updateCycle > 0 ? buffer.ig1_alt1() : width;
              npc.updateHealthBar(id, client.ticks, updateCycle, delay, width, currentWidth);
            } else {
              npc.deleteHeadbar(id);
            }
          }
        }
      }

      if ((mask & 0x2) != 0) {
        npc.targetIndex = buffer.g2_alt4();
        if (npc.targetIndex == 65535) {
          npc.targetIndex = -1;
        }
      }

      if ((mask & 32) != 0) {
        npc.definition = NpcDefinition.get(buffer.g2s_le());
        npc.boundSize = npc.definition.size;
        npc.defaultRotation = npc.definition.rotation;
        npc.walkStance = npc.definition.walkStance;
        npc.turnAroundStance = npc.definition.turnAroundStance;
        npc.walkLeftStance = npc.definition.walkLeftStance;
        npc.walkRightStance = npc.definition.walkRightStance;
        npc.idleStance = npc.definition.idleStance;
        npc.turnLeftStance = npc.definition.turnLeftStance;
        npc.turnRightStance = npc.definition.turnRightStance;
      }

      if ((mask & 0x4) != 0) {
        int x = buffer.g2_alt4();
        int y = buffer.g2s_le();
        int xdiff = npc.absoluteX - (x - client.baseX - client.baseX) * 64;
        int ydiff = npc.absoluteY - (y - client.baseY - client.baseY) * 64;
        if (xdiff != 0 || ydiff != 0) {
          npc.transformedOrientation = (int) (Math.atan2(xdiff, ydiff) * 325.949D) & 2047;
        }
      }

      if ((mask & 0x8) != 0) {
        npc.effect = buffer.g2s_le();
        int packed = buffer.g4_alt2();
        npc.effectYOffset = packed >> 16;
        npc.effectDelay = (packed & 0xffff) + client.ticks;
        npc.effectFrame = 0;
        npc.effectFrameCycle = 0;
        if (npc.effectDelay > client.ticks) {
          npc.effectFrame = -1;
        }

        if (npc.effect == 65535) {
          npc.effect = -1;
        }
      }

      if ((mask & 0x40) != 0) {
        npc.overheadText = buffer.gstr();
        npc.overheadTextCyclesLeft = 100;
      }
    }

    for (int i = 0; i < client.npcDeleteCount; ++i) {
      int index = client.npcDeleteIndices[i];
      if (client.npcs[index].npcUpdateCycle != client.ticks) {
        client.npcs[index].definition = null;
        client.npcs[index] = null;
      }
    }

    if (buffer.pos != client.stream.incomingPacketSize) {
      throw new RuntimeException(buffer.pos + "," + client.stream.incomingPacketSize);
    }

    for (int i = 0; i < client.npcCount; ++i) {
      if (client.npcs[client.npcIndices[i]] == null) {
        throw new RuntimeException(i + "," + client.npcCount);
      }
    }
  }

  public static void method541() {
    BitBuffer buffer = client.stream.inbuffer;
    buffer.bitAccess();
    int size = buffer.g(8);
    if (size < client.npcCount) {
      for (int i = size; i < client.npcCount; ++i) {
        client.npcDeleteIndices[++client.npcDeleteCount - 1] = client.npcIndices[i];
      }
    }

    if (size > client.npcCount) {
      throw new RuntimeException("");
    }

    client.npcCount = 0;

    for (int i = 0; i < size; ++i) {
      int index = client.npcIndices[i];
      NpcEntity npc = client.npcs[index];
      int type = buffer.g(1);
      if (type == 0) {
        client.npcIndices[++client.npcCount - 1] = index;
        npc.npcUpdateCycle = client.ticks;
        continue;
      }

      int state = buffer.g(2);
      if (state == 0) {
        client.npcIndices[++client.npcCount - 1] = index;
        npc.npcUpdateCycle = client.ticks;
        client.updatedNpcIndices[++client.npcUpdateCount - 1] = index;
      } else if (state == 1) {
        client.npcIndices[++client.npcCount - 1] = index;
        npc.npcUpdateCycle = client.ticks;
        int direction = buffer.g(3);
        npc.step(direction, (byte) 1);
        int update = buffer.g(1);
        if (update == 1) {
          client.updatedNpcIndices[++client.npcUpdateCount - 1] = index;
        }
      } else if (state == 2) {
        client.npcIndices[++client.npcCount - 1] = index;
        npc.npcUpdateCycle = client.ticks;
        int direction = buffer.g(3);
        npc.step(direction, (byte) 2);
        int direction2 = buffer.g(3);
        npc.step(direction2, (byte) 2);
        int update = buffer.g(1);
        if (update == 1) {
          client.updatedNpcIndices[++client.npcUpdateCount - 1] = index;
        }
      } else if (state == 3) {
        client.npcDeleteIndices[++client.npcDeleteCount - 1] = index;
      }
    }
  }

  protected Model getModel() {
    if (definition == null) {
      return null;
    }

    AnimationSequence animation = super.animation != -1 && super.animationDelay == 0 ? AnimationSequence.get(super.animation) : null;
    AnimationSequence stance = super.stance != -1 && (super.stance != super.idleStance || animation == null) ? AnimationSequence.get(super.stance) : null;
    Model animatedModel = definition.getModel(animation, super.animationFrame, stance, super.stanceFrame);
    if (animatedModel == null) {
      return null;
    }
    animatedModel.computeBounds();
    super.modelHeight = animatedModel.height;
    if (super.effect != -1 && super.effectFrame != -1) {
      Model effectModel = EffectAnimation.get(super.effect).getModel(super.effectFrame);
      if (effectModel != null) {
        effectModel.offsetBy(0, -super.effectYOffset, 0);
        Model[] models = new Model[]{animatedModel, effectModel};
        animatedModel = new Model(models, 2);
      }
    }

    if (definition.size == 1) {
      animatedModel.fitsSingleTile = true;
    }

    return animatedModel;
  }

  public void step(int direction, byte traversed) {
    int x = super.pathXQueue[0];
    int y = super.pathYQueue[0];
    if (direction == 0) {
      --x;
      ++y;
    }

    if (direction == 1) {
      ++y;
    }

    if (direction == 2) {
      ++x;
      ++y;
    }

    if (direction == 3) {
      --x;
    }

    if (direction == 4) {
      ++x;
    }

    if (direction == 5) {
      --x;
      --y;
    }

    if (direction == 6) {
      --y;
    }

    if (direction == 7) {
      ++x;
      --y;
    }

    if (super.animation != -1 && AnimationSequence.get(super.animation).walkingPrecedence == 1) {
      super.animation = -1;
    }

    if (super.pathQueueSize < 9) {
      ++super.pathQueueSize;
    }

    for (int i = super.pathQueueSize; i > 0; --i) {
      super.pathXQueue[i] = super.pathXQueue[i - 1];
      super.pathYQueue[i] = super.pathYQueue[i - 1];
      super.pathQueueTraversed[i] = super.pathQueueTraversed[i - 1];
    }

    super.pathXQueue[0] = x;
    super.pathYQueue[0] = y;
    super.pathQueueTraversed[0] = traversed;
  }

  public void method683(int x, int y, boolean traversed) {
    if (super.animation != -1 && AnimationSequence.get(super.animation).walkingPrecedence == 1) {
      super.animation = -1;
    }

    if (!traversed) {
      int xdiff = x - super.pathXQueue[0];
      int ydiff = y - super.pathYQueue[0];
      if (xdiff >= -8 && xdiff <= 8 && ydiff >= -8 && ydiff <= 8) {
        if (super.pathQueueSize < 9) {
          ++super.pathQueueSize;
        }

        for (int i = super.pathQueueSize; i > 0; --i) {
          super.pathXQueue[i] = super.pathXQueue[i - 1];
          super.pathYQueue[i] = super.pathYQueue[i - 1];
          super.pathQueueTraversed[i] = super.pathQueueTraversed[i - 1];
        }

        super.pathXQueue[0] = x;
        super.pathYQueue[0] = y;
        super.pathQueueTraversed[0] = 1;
        return;
      }
    }

    super.pathQueueSize = 0;
    super.anInt2023 = 0;
    super.anInt2022 = 0;
    super.pathXQueue[0] = x;
    super.pathYQueue[0] = y;
    super.absoluteX = super.pathXQueue[0] * 128 + super.boundSize;
    super.absoluteY = super.pathYQueue[0] * 128 + super.boundSize;
  }

  public boolean isDefined() {
    return definition != null;
  }
}
