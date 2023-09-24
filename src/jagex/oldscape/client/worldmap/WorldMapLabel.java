package jagex.oldscape.client.worldmap;

import jagex.oldscape.client.client;
import jagex.oldscape.client.scene.entity.PlayerEntity;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;

public class WorldMapLabel {
  public final int anInt342;
  public final int anInt339;
  final String aString340;
  final WorldMapLabelSize aWorldMapLabelSize_338;

  WorldMapLabel(String var1, int var2, int var3, WorldMapLabelSize var4) {
    this.aString340 = var1;
    this.anInt342 = var2;
    this.anInt339 = var3;
    this.aWorldMapLabelSize_338 = var4;
  }

  public static void method232() {
    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.JOIN_CLANCHANNEL, client.stream.encryptor);
    packet.buffer.p1(0);
    client.stream.writeLater(packet);
  }

  public static void method233(int var0, WorldMapPosition var1, boolean var2) {
    WorldMapCacheArea var3 = client.getWorldMap().getArea(var0);
    int var4 = PlayerEntity.local.floorLevel;
    int var5 = client.baseX + (PlayerEntity.local.absoluteX >> 7);
    int var6 = client.baseY + (PlayerEntity.local.absoluteY >> 7);
    WorldMapPosition var7 = new WorldMapPosition(var4, var5, var6);
    client.getWorldMap().method1277(var3, var7, var1, var2);
  }
}
