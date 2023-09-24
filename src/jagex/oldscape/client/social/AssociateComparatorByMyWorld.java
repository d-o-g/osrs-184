package jagex.oldscape.client.social;

import jagex.oldscape.client.client;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;

public class AssociateComparatorByMyWorld<T extends Associate<T>> extends AssociateComparator<T> {

  public final boolean aBoolean764;

  public AssociateComparatorByMyWorld(boolean var1) {
    this.aBoolean764 = var1;
  }

  public static void method603(int var0) {
    client.aLong1081 = 0L;
    client.resizableMode = var0 >= 2;

    if (client.isResizable() == 1) {
      client.instance.method947(765, 503);
    } else {
      client.instance.method947(7680, 2160);
    }

    if (client.gameState >= 25) {
      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.GAME_BOUNDS, client.stream.encryptor);
      packet.buffer.p1(client.isResizable());
      packet.buffer.p2(client.canvasWidth);
      packet.buffer.p2(client.canvasHeight);
      client.stream.writeLater(packet);
    }

  }

  int method604(T var1, T var2) {
    if (client.currentWorld == var1.world) {
      if (var2.world != client.currentWorld) {
        return this.aBoolean764 ? -1 : 1;
      }
    } else if (var2.world == client.currentWorld) {
      return this.aBoolean764 ? 1 : -1;
    }

    return this.method1135(var1, var2);
  }

  public int compare(T var1, T var2) {
    return this.method604(var1, var2);
  }
}
