package jagex.oldscape.client.worldmap;

import jagex.oldscape.client.client;
import jagex.oldscape.client.option.ClientPreferences;
import jagex.oldscape.client.chat.ChatHistory;
import jagex.messaging.*;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;

public class WorldMapAreaChunk_Sub3 implements WorldMapAreaChunk {
  int anInt535;
  int anInt285;
  int anInt279;
  int anInt287;
  int anInt536;
  int anInt278;
  int anInt281;
  int anInt534;
  int anInt282;
  int anInt277;
  int anInt533;
  int anInt286;
  int anInt280;
  int anInt283;

  WorldMapAreaChunk_Sub3() {
  }

  public static void method368(String var0) {
    if (var0.equalsIgnoreCase("toggleroof")) {
      client.preferences.roofsHidden = !client.preferences.roofsHidden;
      ClientPreferences.method854();
      if (client.preferences.roofsHidden) {
        ChatHistory.messageReceived(99, "", "Roofs are now all hidden");
      } else {
        ChatHistory.messageReceived(99, "", "Roofs will only be removed selectively");
      }
    }

    if (var0.equalsIgnoreCase("displayfps")) {
      client.displayFps = !client.displayFps;
    }

    if (var0.equalsIgnoreCase("renderself")) {
      client.displaySelf = !client.displaySelf;
    }

    if (var0.equalsIgnoreCase("mouseovertext")) {
      client.displayMouseOverText = !client.displayMouseOverText;
    }

    if (client.rights >= 2) {
      if (var0.equalsIgnoreCase("errortest")) {
        throw new RuntimeException();
      }

      if (var0.equalsIgnoreCase("showcoord")) {
        client.worldMap.drawMouseOverPosition = !client.worldMap.drawMouseOverPosition;
      }

      if (var0.equalsIgnoreCase("fpson")) {
        client.displayFps = true;
      }

      if (var0.equalsIgnoreCase("fpsoff")) {
        client.displayFps = false;
      }

      if (var0.equalsIgnoreCase("gc")) {
        System.gc();
      }

      if (var0.equalsIgnoreCase("clientdrop")) {
        client.dropConnection();
      }
    }

    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PROCESS_COMMAND, client.stream.encryptor);
    packet.buffer.p1(var0.length() + 1);
    packet.buffer.pcstr(var0);
    client.stream.writeLater(packet);
  }

  public boolean contains(int x, int y) {
    return x >= (this.anInt279 << 6) + (this.anInt535 << 3) && x <= (this.anInt279 << 6) + (this.anInt536 << 3) + 7 && y >= (this.anInt277 << 6) + (this.anInt534 << 3) && y <= (this.anInt277 << 6) + (this.anInt533 << 3) + 7;
  }

  public boolean contains(int level, int x, int y) {
    if (level >= this.anInt285 && level < this.anInt285 + this.anInt287) {
      return x >= (this.anInt281 << 6) + (this.anInt278 << 3) && x <= (this.anInt281 << 6) + (this.anInt282 << 3) + 7 && y >= (this.anInt280 << 6) + (this.anInt286 << 3) && y <= (this.anInt280 << 6) + (this.anInt283 << 3) + 7;
    }
    return false;
  }

  public void method93(WorldMapCacheArea var1) {
    if (var1.regionMinX > this.anInt279) {
      var1.regionMinX = this.anInt279;
    }

    if (var1.regionMaxX < this.anInt279) {
      var1.regionMaxX = this.anInt279;
    }

    if (var1.regionMinY > this.anInt277) {
      var1.regionMinY = this.anInt277;
    }

    if (var1.regionMaxY < this.anInt277) {
      var1.regionMaxY = this.anInt277;
    }

  }

  public WorldMapPosition getPosition(int x, int y) {
    if (!this.contains(x, y)) {
      return null;
    }
    int var3 = this.anInt281 * 64 - this.anInt279 * 64 + (this.anInt278 * 8 - this.anInt535 * 8) + x;
    int var4 = this.anInt280 * 64 - this.anInt277 * 64 + y + (this.anInt286 * 8 - this.anInt534 * 8);
    return new WorldMapPosition(this.anInt285, var3, var4);
  }

  public int[] outline(int level, int x, int y) {
    if (!this.contains(level, x, y)) {
      return null;
    }
    return new int[]{this.anInt279 * 64 - this.anInt281 * 64 + x + (this.anInt535 * 8 - this.anInt278 * 8), y + (this.anInt277 * 64 - this.anInt280 * 64) + (this.anInt534 * 8 - this.anInt286 * 8)};
  }

  public void decode(Buffer buffer) {
    anInt285 = buffer.g1();
    anInt287 = buffer.g1();
    anInt281 = buffer.g2();
    anInt278 = buffer.g1();
    anInt282 = buffer.g1();
    anInt280 = buffer.g2();
    anInt286 = buffer.g1();
    anInt283 = buffer.g1();
    anInt279 = buffer.g2();
    anInt535 = buffer.g1();
    anInt536 = buffer.g1();
    anInt277 = buffer.g2();
    anInt534 = buffer.g1();
    anInt533 = buffer.g1();
    method148();
  }

  void method148() {

  }
}
