package jag.opcode.handler;

import jag.opcode.*;

import java.io.IOException;

public abstract class ServerProtHandler {

    protected final NetWriter netWriter;

    protected ServerProtHandler(NetWriter netWriter) {
        this.netWriter = netWriter;
    }

    public abstract boolean available(BitBuffer incoming) throws IOException;

    public abstract boolean processComponentConfigUpdate(BitBuffer incoming);

    public abstract boolean processOculusOrbModeUpdate(BitBuffer incoming);

    public abstract boolean processItemTableUpdate(BitBuffer incoming);

    public abstract boolean processLockCameraRequest(BitBuffer incoming);

    public abstract boolean processGcInfoRequest(BitBuffer incoming);

    public abstract boolean processStockMarketUpdate(BitBuffer incoming);

    public abstract boolean processZoneProt(BitBuffer incoming, ZoneProt prot);

    public abstract boolean setIfPosition(BitBuffer incoming);

    public abstract boolean setUpdateTimer(BitBuffer incoming);

    public abstract boolean processStockMarketEvents(BitBuffer incoming);

    public abstract boolean updatePlayerWeight(BitBuffer incoming);

    public abstract boolean setOculusOrbToLocalPlayer(BitBuffer incoming);

    public abstract boolean processFriendsChatUpdate(BitBuffer incoming);

    public abstract boolean moveSubInterface(BitBuffer incoming);

    public abstract boolean processFriendsListUpdate(BitBuffer incoming);

    public abstract boolean processFriendsChatMessage(BitBuffer incoming);

    public abstract boolean processRandomDatUpdate(BitBuffer incoming);
}
