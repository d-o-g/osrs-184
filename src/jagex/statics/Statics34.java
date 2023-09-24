package jagex.statics;

import jagex.oldscape.client.client;
import jagex.oldscape.client.scene.entity.PlayerEntity;
import jagex.messaging.BitBuffer;
import jagex.messaging.GPI;

public final class Statics34 {

  public static boolean method1168(BitBuffer var0, int var1) {
    int var2 = var0.g(2);
    int var3;
    int var4;
    int var7;
    int var8;
    int var9;
    int var10;
    if (var2 == 0) {
      if (var0.g(1) != 0) {
        method1168(var0, var1);
      }

      var3 = var0.g(13);
      var4 = var0.g(13);
      boolean var12 = var0.g(1) == 1;
      if (var12) {
        GPI.anIntArray588[++GPI.anInt594 - 1] = var1;
      }

      if (client.players[var1] != null) {
        throw new RuntimeException();
      }
      PlayerEntity var6 = client.players[var1] = new PlayerEntity();
      var6.index = var1;
      if (GPI.buffers[var1] != null) {
        var6.decode(GPI.buffers[var1]);
      }

      var6.orientation = GPI.playerOrientations[var1];
      var6.targetIndex = GPI.playerTargetIndices[var1];
      var7 = GPI.playerLocations[var1];
      var8 = var7 >> 28;
      var9 = var7 >> 14 & 255;
      var10 = var7 & 255;
      var6.pathQueueTraversed[0] = GPI.aByteArray596[var1];
      var6.floorLevel = (byte) var8;
      var6.method1414((var9 << 13) + var3 - client.baseX, (var10 << 13) + var4 - client.baseY);
      var6.aBoolean1904 = false;
      return true;
    }
    if (var2 == 1) {
      var3 = var0.g(2);
      var4 = GPI.playerLocations[var1];
      GPI.playerLocations[var1] = (var4 & 268435455) + (((var4 >> 28) + var3 & 3) << 28);
      return false;
    }
    int var5;
    int var11;
    if (var2 == 2) {
      var3 = var0.g(5);
      var4 = var3 >> 3;
      var5 = var3 & 7;
      var11 = GPI.playerLocations[var1];
      var7 = (var11 >> 28) + var4 & 3;
      var8 = var11 >> 14 & 255;
      var9 = var11 & 255;
      if (var5 == 0) {
        --var8;
        --var9;
      }

      if (var5 == 1) {
        --var9;
      }

      if (var5 == 2) {
        ++var8;
        --var9;
      }

      if (var5 == 3) {
        --var8;
      }

      if (var5 == 4) {
        ++var8;
      }

      if (var5 == 5) {
        --var8;
        ++var9;
      }

      if (var5 == 6) {
        ++var9;
      }

      if (var5 == 7) {
        ++var8;
        ++var9;
      }

      GPI.playerLocations[var1] = (var8 << 14) + var9 + (var7 << 28);
      return false;
    }
    var3 = var0.g(18);
    var4 = var3 >> 16;
    var5 = var3 >> 8 & 255;
    var11 = var3 & 255;
    var7 = GPI.playerLocations[var1];
    var8 = (var7 >> 28) + var4 & 3;
    var9 = var5 + (var7 >> 14) & 255;
    var10 = var11 + var7 & 255;
    GPI.playerLocations[var1] = (var9 << 14) + var10 + (var8 << 28);
    return false;
  }
}
