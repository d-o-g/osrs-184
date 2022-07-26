package jag.opcode.handler;

import jag.opcode.NetWriter;

public abstract class ClientProtHandler {

    protected final NetWriter netWriter;

    protected ClientProtHandler(NetWriter netWriter) {
        this.netWriter = netWriter;
    }

    public abstract void processReflection();

    public abstract void processGameStateEvents();

    public abstract void processMouseMotionRecords();

    public abstract void processMouseActionRecords();

    public abstract void processKeyInfo();

    public abstract void processCameraInfo();

    public abstract void processFocusInfo();
}
