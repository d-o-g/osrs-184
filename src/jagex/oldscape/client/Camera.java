package jagex.oldscape.client;

import jagex.jagex3.graphics.JagGraphics3D;
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
  public static int anInt889;

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

  public static void method505(int renderX, int renderZ, int renderY, int renderPitch, int renderYaw, int var5, int var6) {
    int var7 = var6 - 334;
    if (var7 < 0) {
      var7 = 0;
    } else if (var7 > 100) {
      var7 = 100;
    }

    int var8 = (client.aShort920 - client.aShort913) * var7 / 100 + client.aShort913;
    int var9 = var5 * var8 / 256;
    var7 = 2048 - renderPitch & 2047;
    var8 = 2048 - renderYaw & 2047;
    int offsetX = 0;
    int offsetZ = 0;
    int offsetY = var9;
    if (var7 != 0) {
      int sin = JagGraphics3D.SIN_TABLE[var7];
      int cos = JagGraphics3D.COS_TABLE[var7];
      int var15 = -var9 * sin >> 16;
      offsetY = cos * var9 >> 16;
      offsetZ = var15;
    }

    if (var8 != 0) {
      int sin = JagGraphics3D.SIN_TABLE[var8];
      int cos = JagGraphics3D.COS_TABLE[var8];
      int var15 = sin * offsetY >> 16;
      offsetY = offsetY * cos >> 16;
      offsetX = var15;
    }

    x = renderX - offsetX;
    z = renderZ - offsetZ;
    y = renderY - offsetY;
    pitch = renderPitch;
    yaw = renderYaw;

    if (oculusOrbMode == 1
        && client.rights >= 2
        && client.ticks % 50 == 0
        && (oculusOrbAbsoluteX >> 7 != PlayerEntity.local.absoluteX >> 7
            || oculusOrbAbsoluteY >> 7 != PlayerEntity.local.absoluteY >> 7)) {
      int level = PlayerEntity.local.floorLevel;
      int x = client.baseX + (oculusOrbAbsoluteX >> 7);
      int y = client.baseY + (oculusOrbAbsoluteY >> 7);
      client.clientProtHandler.processTeleport(x, y, level, true);
    }

  }
}
