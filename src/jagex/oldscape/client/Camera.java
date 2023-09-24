package jagex.oldscape.client;

import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.client.scene.entity.PlayerEntity;

public class Camera {

  public static boolean oculusOrbOnLocalPlayer = false;

  public static int oculusOrbAbsoluteX;
  public static int oculusOrbAbsoluteY;

  public static int followHeight = 50;

  public static int x;
  public static int y;
  public static int z;

  public static int pitch;
  public static int yaw;
  public static int minimumPitch = 128;
  public static int yOffset = 0;

  public static int moveX = 0;
  public static int moveY = 0;
  public static int mouseDragX = 0;
  public static int mouseDragY = 0;

  public static int oculusOrbMode = 0;
  public static int oculusOrbSpeed = 12;
  public static int oculusOrbSlowSpeed = 6;

  public static int anInt802;
  public static int packetIndicator = 0;

  public static boolean emitPackets = false;

  public static void setOculusOrbToLocalPlayerPosition() {
    if (oculusOrbOnLocalPlayer && PlayerEntity.local != null) {
      int localX = PlayerEntity.local.pathXQueue[0];
      int localY = PlayerEntity.local.pathYQueue[0];
      if (localX < 0 || localY < 0 || localX >= 104 || localY >= 104) {
        return;
      }

      oculusOrbAbsoluteX = PlayerEntity.local.absoluteX;
      int height = SceneGraph.getTileHeight(PlayerEntity.local.absoluteX, PlayerEntity.local.absoluteY, SceneGraph.floorLevel) - followHeight;
      if (height < anInt802) {
        anInt802 = height;
      }

      oculusOrbAbsoluteY = PlayerEntity.local.absoluteY;
      oculusOrbOnLocalPlayer = false;
    }
  }

  public static void setOculusOrbOnLocalPlayer() {
    if (oculusOrbMode == 1) {
      oculusOrbOnLocalPlayer = true;
    }

  }
}
