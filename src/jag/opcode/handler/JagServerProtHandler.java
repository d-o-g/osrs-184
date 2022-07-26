package jag.opcode.handler;

import jag.SerializableString;
import jag.commons.collection.IntegerNode;
import jag.commons.time.Clock;
import jag.game.*;
import jag.game.scene.SceneGraph;
import jag.game.stockmarket.StockMarketOffer;
import jag.game.stockmarket.StockMarketOfferWorldComparator;
import jag.js5.Js5Worker;
import jag.opcode.*;
import jag.statics.Statics53;

import java.io.IOException;

public class JagServerProtHandler extends ServerProtHandler {

    public JagServerProtHandler(NetWriter netWriter) {
        super(netWriter);
    }

    @Override
    public boolean available(BitBuffer incoming) throws IOException {
        Connection connection = netWriter.unwrap();
        if (netWriter.currentIncomingPacket == null && !readIncomingPacket(connection, incoming)) {
            return false;
        }

        if (netWriter.incomingPacketSize == -1) {
            if (!connection.available(1)) {
                return false;
            }

            netWriter.unwrap().read(incoming.payload, 0, 1);
            netWriter.incomingPacketSize = incoming.payload[0] & 0xff;
        }

        if (netWriter.incomingPacketSize == -2) {
            if (!connection.available(2)) {
                return false;
            }

            netWriter.unwrap().read(incoming.payload, 0, 2);
            incoming.pos = 0;
            netWriter.incomingPacketSize = incoming.g2();
        }

        return connection.available(netWriter.incomingPacketSize);
    }

    private boolean readIncomingPacket(Connection connection, BitBuffer incoming) throws IOException {
        if (netWriter.alive) {
            if (!connection.available(1)) {
                return false;
            }

            connection.read(netWriter.inbuffer.payload, 0, 1);
            netWriter.idleReadTicks = 0;
            netWriter.alive = false;
        }

        incoming.pos = 0;
        if (incoming.nextIsSmart()) {
            if (!connection.available(1)) {
                return false;
            }

            connection.read(netWriter.inbuffer.payload, 1, 1);
            netWriter.idleReadTicks = 0;
        }

        netWriter.alive = true;
        ServerProt[] incomingPacketTypes = ServerProt.values();
        int packetIndex = incoming.gesmart();
        if (packetIndex < 0 || packetIndex >= incomingPacketTypes.length) {
            throw new IOException(packetIndex + " " + incoming.pos);
        }

        netWriter.currentIncomingPacket = incomingPacketTypes[packetIndex];
        netWriter.incomingPacketSize = netWriter.currentIncomingPacket.size;
        return true;
    }

    @Override
    public boolean processComponentConfigUpdate(BitBuffer incoming) {
        int start = incoming.readLEUShortA();
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

        netWriter.currentIncomingPacket = null;
        return true;
    }

    @Override
    public boolean processOculusOrbModeUpdate(BitBuffer incoming) {
        int state = incoming.g1();
        Js5Worker.setOculusOrbMode(state);
        netWriter.currentIncomingPacket = null;
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
            int id = incoming.readLEUShortA();
            int stack = incoming.method1056();
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
        netWriter.currentIncomingPacket = null;
        return true;
    }

    @Override
    public boolean processLockCameraRequest(BitBuffer incoming) {
        client.cameraLocked = true;
        SceneGraph.anInt1235 = incoming.g1() * 128;
        Clock.anInt889 = incoming.g1() * 128;
        StockMarketOfferWorldComparator.anInt347 = incoming.g2();
        MouseRecorder.anInt388 = incoming.g1();
        Statics53.anInt520 = incoming.g1();
        if (Statics53.anInt520 >= 100) {
            Camera.x = SceneGraph.anInt1235 * 16384 + 64;
            Camera.y = Clock.anInt889 * 16384 + 64;
            Camera.z = SceneGraph.getTileHeight(Camera.x, Camera.y, SceneGraph.floorLevel) - StockMarketOfferWorldComparator.anInt347;
        }

        netWriter.currentIncomingPacket = null;
        return true;
    }

    @Override
    public boolean processGcInfoRequest(BitBuffer incoming) {
        int var6 = incoming.g4();
        int var5 = incoming.g4();
        int time = SerializableString.getGcTime();
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.GARBAGE_COLLECTOR, netWriter.encryptor);
        packet.buffer.p1n(time);
        packet.buffer.p4(var6);
        packet.buffer.pif4(var5);
        packet.buffer.writeByteS(client.anInt1292);
        netWriter.writeLater(packet);
        netWriter.currentIncomingPacket = null;
        return true;
    }

    @Override
    public boolean processStockMarketUpdate(BitBuffer incoming) {
        int var6 = incoming.g1();
        if (incoming.g1() == 0) {
            client.stockMarketOffers[var6] = new StockMarketOffer();
            incoming.pos += 18;
        } else {
            --incoming.pos;
            client.stockMarketOffers[var6] = new StockMarketOffer(incoming);
        }

        client.anInt1071 = client.anInt1075;
        netWriter.currentIncomingPacket = null;
        return true;
    }
}
