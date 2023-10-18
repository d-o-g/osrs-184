package jagex.oldscape.client.scene.entity;

import jagex.jagex3.sound.AudioSystem;
import jagex.jagex3.sound.PcmStream;
import jagex.oldscape.client.client;
import jagex.oldscape.client.type.AnimationSequence;
import jagex.oldscape.client.type.ObjectDefinition;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.client.scene.SceneGraphRenderData;

public class DynamicObject extends Entity {

  public final int id;
  public final int type;
  public final int orientation;
  public final int floorLevel;
  public final int sceneX;
  public final int sceneY;

  public AnimationSequence sequence;

  public int animationDelay;
  public int animationFrame;

  public DynamicObject(int id, int type, int orientation, int floorLevel, int sceneX, int sceneY, int animationId, boolean randomizeInitialAnimation, Entity entity) {
    this.id = id;
    this.type = type;
    this.orientation = orientation;
    this.floorLevel = floorLevel;
    this.sceneX = sceneX;
    this.sceneY = sceneY;
    if (animationId != -1) {
      sequence = AnimationSequence.get(animationId);
      animationFrame = 0;
      animationDelay = client.ticks - 1;
      if (sequence.replayMode == 0 && entity instanceof DynamicObject) {
        DynamicObject obj = (DynamicObject) entity;
        if (obj.sequence == sequence) {
          animationFrame = obj.animationFrame;
          animationDelay = obj.animationDelay;
          return;
        }
      }

      if (randomizeInitialAnimation && sequence.loopOffset != -1) {
        animationFrame = (int) (Math.random() * (double) sequence.frameIds.length);
        animationDelay -= (int) (Math.random() * (double) sequence.frameLengths[animationFrame]);
      }
    }
  }

  protected final Model getModel() {
    if (sequence != null) {
      int lifetime = client.ticks - animationDelay;
      if (lifetime > 100 && sequence.loopOffset > 0) {
        lifetime = 100;
      }

      label56:
      {
        do {
          do {
            if (lifetime <= sequence.frameLengths[animationFrame]) {
              break label56;
            }

            lifetime -= sequence.frameLengths[animationFrame];
            ++animationFrame;
          } while (animationFrame < sequence.frameIds.length);

          animationFrame -= sequence.loopOffset;
        } while (animationFrame >= 0 && animationFrame < sequence.frameIds.length);

        sequence = null;
      }

      animationDelay = client.ticks - lifetime;
    }

    ObjectDefinition definition = ObjectDefinition.get(id);
    if (definition.transformIds != null) {
      definition = definition.transform();
    }

    if (definition == null) {
      return null;
    }

    int spanX;
    int spanY;
    if (orientation != 1 && orientation != 3) {
      spanX = definition.sizeX;
      spanY = definition.sizeY;
    } else {
      spanX = definition.sizeY;
      spanY = definition.sizeX;
    }

    int centerX = (spanX >> 1) + sceneX;
    int centerY = (spanX + 1 >> 1) + sceneX;
    int bottomLeftY = (spanY >> 1) + sceneY;
    int topRightY = (spanY + 1 >> 1) + sceneY;
    int[][] heights = SceneGraphRenderData.tileHeights[floorLevel];
    int height = heights[centerY][bottomLeftY] + heights[centerX][bottomLeftY] + heights[centerX][topRightY] + heights[centerY][topRightY] >> 2;
    int endX = (sceneX << 7) + (spanX << 6);
    int endY = (sceneY << 7) + (spanY << 6);
    return definition.method1107(type, orientation, heights, endX, height, endY, sequence, animationFrame);
  }

  public static void gc() {
    client.stream.stop();
    client.gc();
    client.sceneGraph.initialize();

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
    client.currentAudioTrackGroupId = -1;
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
}
