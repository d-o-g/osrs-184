package jag.opcode.handler;

import jag.opcode.ClientStream;

public abstract class ClientProtHandler {

    protected final ClientStream stream;

    protected ClientProtHandler(ClientStream stream) {
        this.stream = stream;
    }

    public abstract void processReflection();

    public abstract void processGameStateEvents();

    public abstract void processMouseMotionRecords();

    public abstract void processMouseActionRecords();

    public abstract void processKeyInfo();

    public abstract void processCameraInfo();

    public abstract void processFocusInfo();
}
