package jag.opcode.handler;

import jag.opcode.BitBuffer;
import jag.opcode.NetWriter;

public abstract class ServerProtHandler {

    protected final NetWriter netWriter;

    protected ServerProtHandler(NetWriter netWriter) {
        this.netWriter = netWriter;
    }

    public abstract boolean processComponentConfigUpdate(BitBuffer incoming);

    public abstract boolean processOculusOrbModeUpdate(BitBuffer incoming);

    public abstract boolean processItemTableUpdate(BitBuffer incoming);

    public abstract boolean processLockCameraRequest(BitBuffer incoming);

    public abstract boolean processGcInfoRequest(BitBuffer incoming);

    public abstract boolean processStockMarketUpdate(BitBuffer incoming);
}
