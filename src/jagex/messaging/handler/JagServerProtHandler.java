package jagex.messaging.handler;

import jagex.core.stringtools.Strings;
import jagex.datastructure.instrusive.hashtable.IntegerNode;
import jagex.core.stringtools.Base37;
import jagex.core.time.Clock;
import jagex.oldscape.*;
import jagex.oldscape.client.*;
import jagex.oldscape.client.minimenu.ContextMenu;
import jagex.oldscape.client.chat.ChatHistory;
import jagex.oldscape.client.social.NamePair;
import jagex.oldscape.client.scene.SceneGraph;
import jagex.oldscape.shared.prot.*;
import jagex.oldscape.stockmarket.*;
import jagex.jagex3.js5.Js5Worker;
import jagex.jagex3.js5.ResourceCache;
import jagex.messaging.*;
import jagex.statics.Statics53;

import java.io.IOException;

public class JagServerProtHandler extends ServerProtHandler {

  public JagServerProtHandler(ClientStream stream) {
    super(stream);
  }

  @Override
  public boolean available(BitBuffer incoming) throws IOException {
    Connection connection = stream.unwrap();
    if (stream.currentIncomingPacket == null && !readIncomingPacket(connection, incoming)) {
      return false;
    }

    if (stream.incomingPacketSize == -1) {
      if (!connection.available(1)) {
        return false;
      }

      stream.unwrap().read(incoming.payload, 0, 1);
      stream.incomingPacketSize = incoming.payload[0] & 0xff;
    }

    if (stream.incomingPacketSize == -2) {
      if (!connection.available(2)) {
        return false;
      }

      stream.unwrap().read(incoming.payload, 0, 2);
      incoming.pos = 0;
      stream.incomingPacketSize = incoming.g2();
    }

    return connection.available(stream.incomingPacketSize);
  }

  private boolean readIncomingPacket(Connection connection, BitBuffer incoming) throws IOException {
    if (stream.alive) {
      if (!connection.available(1)) {
        return false;
      }

      connection.read(stream.inbuffer.payload, 0, 1);
      stream.idleReadTicks = 0;
      stream.alive = false;
    }

    incoming.pos = 0;
    if (incoming.nextIsSmart()) {
      if (!connection.available(1)) {
        return false;
      }

      connection.read(stream.inbuffer.payload, 1, 1);
      stream.idleReadTicks = 0;
    }

    stream.alive = true;
    ServerProt[] incomingPacketTypes = ServerProt.values();
    int packetIndex = incoming.gesmart();
    if (packetIndex < 0 || packetIndex >= incomingPacketTypes.length) {
      throw new IOException(packetIndex + " " + incoming.pos);
    }

    stream.currentIncomingPacket = incomingPacketTypes[packetIndex];
    stream.incomingPacketSize = stream.currentIncomingPacket.size;
    return true;
  }

  @Override
  public boolean processComponentConfigUpdate(BitBuffer incoming) {
    int start = incoming.g2_le();
    if (start == 65535) {
      start = -1;
    }

    int end = incoming.g2();
    if (end == 65535) {
      end = -1;
    }

    int key = incoming.ig4();
    int hash = incoming.ig4();

    for (int i = start; i <= end; ++i) {
      long value = ((long) hash << 32) + (long) i;
      IntegerNode node = client.interfaceConfigs.lookup(value);
      if (node != null) {
        node.unlink();
      }

      client.interfaceConfigs.put(new IntegerNode(key), value);
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processOculusOrbModeUpdate(BitBuffer incoming) {
    int state = incoming.g1();
    Js5Worker.setOculusOrbMode(state);
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processItemTableUpdate(BitBuffer incoming) {
    int componentUid = incoming.g4();
    int key = incoming.g2();
    if (componentUid < -70000) {
      key += 32768;
    }

    InterfaceComponent component = null;
    if (componentUid >= 0) {
      component = InterfaceComponent.lookup(componentUid);
    }

    if (component != null) {
      for (int i = 0; i < component.itemIds.length; ++i) {
        component.itemIds[i] = 0;
        component.itemStackSizes[i] = 0;
      }
    }

    Inventory.clear(key);

    int size = incoming.g2();
    for (int index = 0; index < size; ++index) {
      int id = incoming.g2_le();
      int stack = incoming.g1_alt3();
      if (stack == 255) {
        stack = incoming.ig4();
      }

      if (component != null && index < component.itemIds.length) {
        component.itemIds[index] = id;
        component.itemStackSizes[index] = stack;
      }

      Inventory.addItem(key, index, id - 1, stack);
    }

    if (component != null) {
      InterfaceComponent.invalidate(component);
    }

    SubInterface.process();
    client.inventories[++client.anInt1078 - 1 & 31] = key & 32767;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processLockCameraRequest(BitBuffer incoming) {
    client.cameraLocked = true;
    SceneGraph.anInt1235 = incoming.g1() * 128;
    Camera.anInt889 = incoming.g1() * 128;
    StockmarketListingWorldComparator.anInt347 = incoming.g2();
    MouseRecorder.anInt388 = incoming.g1();
    Statics53.anInt520 = incoming.g1();
    if (Statics53.anInt520 >= 100) {
      Camera.x = SceneGraph.anInt1235 * 16384 + 64;
      Camera.y = Camera.anInt889 * 16384 + 64;
      Camera.z = SceneGraph.getTileHeight(Camera.x, Camera.y, SceneGraph.floorLevel) - StockmarketListingWorldComparator.anInt347;
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processGcInfoRequest(BitBuffer incoming) {
    int var6 = incoming.g4();
    int var5 = incoming.g4();
    int time = SerializableString.getGcTime();
    OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.GARBAGE_COLLECTOR, stream.encryptor);
    packet.buffer.p1n(time);
    packet.buffer.p4(var6);
    packet.buffer.p4_alt1(var5);
    packet.buffer.p1_alt1(client.anInt1292);
    stream.writeLater(packet);
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processStockMarketUpdate(BitBuffer incoming) {
    int var6 = incoming.g1();
    if (incoming.g1() == 0) {
      client.stockMarketOffers[var6] = new StockmarketListing();
      incoming.pos += 18;
    } else {
      --incoming.pos;
      client.stockMarketOffers[var6] = new StockmarketListing(incoming);
    }

    client.anInt1071 = client.anInt1075;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processZoneProt(BitBuffer incoming, ZoneProt prot) {
    ZoneProt.process(prot);
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean setIfPosition(BitBuffer incoming) {
    int yMargin = incoming.g2_alt3();
    int xMargin = incoming.g2_alt3();
    int uid = incoming.ig4();
    InterfaceComponent component = InterfaceComponent.lookup(uid);
    if (xMargin != component.xMargin || yMargin != component.yMargin || component.xLayout != 0 || component.yLayout != 0) {
      component.xMargin = xMargin;
      component.yMargin = yMargin;
      component.xLayout = 0;
      component.yLayout = 0;
      InterfaceComponent.invalidate(component);
      client.instance.updateComponentMargin(component);
      if (component.type == 0) {
        InterfaceComponent.revalidateScroll(client.interfaces[uid >> 16], component, false);
      }
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean setUpdateTimer(BitBuffer incoming) {
    client.updateTimer = incoming.g2_le() * 30;
    client.anInt1074 = client.anInt1075;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processStockMarketEvents(BitBuffer incoming) {
    boolean var42 = incoming.g1() == 1;
    if (var42) {
      StockmarketListing.ageAdjustment = Clock.now() - incoming.g8();
      StockmarketListing.manager = new StockmarketManager(incoming);
    } else {
      StockmarketListing.manager = null;
    }

    client.stockMarketEventUpdateCycle = client.anInt1075;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean updatePlayerWeight(BitBuffer incoming) {
    SubInterface.process();
    client.weight = incoming.g2b();
    client.anInt1074 = client.anInt1075;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean setOculusOrbToLocalPlayer(BitBuffer incoming) {
    int var6 = incoming.g4();
    if (var6 != client.anInt1002) {
      client.anInt1002 = var6;
      Camera.setOculusOrbOnLocalPlayer();
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processFriendsChatUpdate(BitBuffer incoming) {
    if (client.friendChat != null) {
      client.friendChat.decodeUpdate(incoming);
    }

    client.method865();
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean moveSubInterface(BitBuffer incoming) {
    int key2 = incoming.ig4();
    int key1 = incoming.g4();
    SubInterface sub1 = client.subInterfaces.lookup(key1);
    SubInterface sub2 = client.subInterfaces.lookup(key2);
    if (sub2 != null) {
      InterfaceComponent.close(sub2, sub1 == null || sub2.id != sub1.id);
    }

    if (sub1 != null) {
      sub1.unlink();
      client.subInterfaces.put(sub1, key2);
    }

    InterfaceComponent component = InterfaceComponent.lookup(key1);
    if (component != null) {
      InterfaceComponent.invalidate(component);
    }

    component = InterfaceComponent.lookup(key2);
    if (component != null) {
      InterfaceComponent.invalidate(component);
      InterfaceComponent.revalidateScroll(client.interfaces[component.uid >>> 16], component, true);
    }

    if (client.rootInterfaceIndex != -1) {
      InterfaceComponent.executeCloseListeners(client.rootInterfaceIndex, 1);
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processFriendsListUpdate(BitBuffer incoming) {
    client.relationshipManager.decodeFriends(incoming, stream.incomingPacketSize);
    client.anInt1065 = client.anInt1075;
    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processFriendsChatMessage(BitBuffer incoming) {
    String var38 = incoming.gstr();
    long var18 = incoming.g8();
    long var20 = incoming.g2();
    long var10 = incoming.g3();
    PlayerAccountType accountType = (PlayerAccountType) EnumType.getByOrdinal(PlayerAccountType.getValues(), incoming.g1());
    long var22 = var10 + (var20 << 32);
    boolean ignored = false;

    for (int i = 0; i < 100; ++i) {
      if (client.messageIds[i] == var22) {
        ignored = true;
        break;
      }
    }

    if (accountType.notJagex && client.relationshipManager.isIgnored(new NamePair(var38, ClientParameter.loginTypeParameter))) {
      ignored = true;
    }

    if (!ignored && client.anInt1014 == 0) {
      client.messageIds[client.messageIndex] = var22;
      client.messageIndex = (client.messageIndex + 1) % 100;
      String escaped = Strings.escapeAngleBrackets(Strings.toTitleCase(Strings.decompressText(incoming)));
      if (accountType.icon != -1) {
        ChatHistory.messageReceived(9, ContextMenu.addImgTags(accountType.icon) + var38, escaped, Base37.decode(var18));
      } else {
        ChatHistory.messageReceived(9, var38, escaped, Base37.decode(var18));
      }
    }

    stream.currentIncomingPacket = null;
    return true;
  }

  @Override
  public boolean processRandomDatUpdate(BitBuffer incoming) {
    incoming.pos += 28;
    if (incoming.matchCrcs()) {
      ResourceCache.createRandom(incoming, incoming.pos - 28);
    }

    stream.currentIncomingPacket = null;
    return true;
  }
}
