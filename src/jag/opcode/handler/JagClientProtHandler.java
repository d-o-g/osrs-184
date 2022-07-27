package jag.opcode.handler;

import jag.commons.input.Keyboard;
import jag.commons.input.Mouse;
import jag.commons.time.Clock;
import jag.game.Camera;
import jag.game.client;
import jag.opcode.*;
import jag.worldmap.WorldMapObjectIcon;

public class JagClientProtHandler extends ClientProtHandler {

    public JagClientProtHandler(ClientStream writer) {
        super(writer);
    }

    @Override
    public void processReflection() {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.PROCESS_REFLECTION, stream.encryptor);
        packet.buffer.p1(0);
        int offset = packet.buffer.pos;
        ClassStructure.process(packet.buffer);
        packet.buffer.psize1(packet.buffer.pos - offset);
        stream.writeLater(packet);
    }

    @Override
    public void processGameStateEvents() {
        OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.GAME_STATE_EVENT_REQUEST, stream.encryptor);
        packet.buffer.p1(0);
        int previousOffset = packet.buffer.pos;
        client.gameStateEvent.writeTo(packet.buffer);
        packet.buffer.psize1(packet.buffer.pos - previousOffset);
        stream.writeLater(packet);
        client.gameStateEvent.method1118();
    }

    @Override
    public void processMouseMotionRecords() {
        synchronized (client.mouseRecorder.lock) {
            if (!client.lockMouseRecorder) {
                client.mouseRecorder.caret = 0;
                return;
            }

            if (Mouse.clickMeta != 0 || client.mouseRecorder.caret >= 40) {
                MouseRecorder recorder = client.mouseRecorder;
                OutgoingPacket packet = null;
                int offset = 0;
                int lastIndex = 0;
                int timeDelta = 0;
                int count = 0;
                int xDiff;
                int yDiff;
                int timeDiff;

                for (int i = 0; i < recorder.caret && (packet == null || packet.buffer.pos - offset < 246); ++i) {
                    lastIndex = i;
                    int y = recorder.ys[i];
                    if (y < -1) {
                        y = -1;
                    } else if (y > 65534) {
                        y = 65534;
                    }

                    int x = recorder.xs[i];
                    if (x < -1) {
                        x = -1;
                    } else if (x > 65534) {
                        x = 65534;
                    }

                    if (x == client.lastMouseRecordX && y == client.lastMouseRecordY) {
                        continue;
                    }

                    if (packet == null) {
                        packet = OutgoingPacket.prepare(ClientProt.MOUSE_MOTION_RECORD, stream.encryptor);
                        packet.buffer.p1(0);
                        offset = packet.buffer.pos;
                        packet.buffer.pos += 2;
                        timeDelta = 0;
                        count = 0;
                    }

                    if (client.lastMouseRecordTime != -1L) {
                        xDiff = x - client.lastMouseRecordX;
                        yDiff = y - client.lastMouseRecordY;
                        timeDiff = (int) ((recorder.times[i] - client.lastMouseRecordTime) / 20L);
                        timeDelta = (int) ((long) timeDelta + (recorder.times[i] - client.lastMouseRecordTime) % 20L);
                    } else {
                        xDiff = x;
                        yDiff = y;
                        timeDiff = Integer.MAX_VALUE;
                    }

                    client.lastMouseRecordX = x;
                    client.lastMouseRecordY = y;
                    if (timeDiff < 8 && xDiff >= -32 && xDiff <= 31 && yDiff >= -32 && yDiff <= 31) {
                        xDiff += 32;
                        yDiff += 32;
                        packet.buffer.p2((timeDiff << 12) + yDiff + (xDiff << 6));
                    } else if (timeDiff < 32 && xDiff >= -128 && xDiff <= 127 && yDiff >= -128 && yDiff <= 127) {
                        xDiff += 128;
                        yDiff += 128;
                        packet.buffer.p1(timeDiff + 128);
                        packet.buffer.p2(yDiff + (xDiff << 8));
                    } else if (timeDiff < 32) {
                        packet.buffer.p1(timeDiff + 192);
                        if (x != -1 && y != -1) {
                            packet.buffer.p4(x | y << 16);
                        } else {
                            packet.buffer.p4(Integer.MIN_VALUE);
                        }
                    } else {
                        packet.buffer.p2((timeDiff & 0x1fff) + 0xe000);
                        if (x != -1 && y != -1) {
                            packet.buffer.p4(x | (y << 16));
                        } else {
                            packet.buffer.p4(Integer.MIN_VALUE);
                        }
                    }

                    ++count;
                    client.lastMouseRecordTime = recorder.times[i];
                }

                if (packet != null) {
                    packet.buffer.psize1(packet.buffer.pos - offset);
                    int prev = packet.buffer.pos;
                    packet.buffer.pos = offset;
                    packet.buffer.p1(timeDelta / count);
                    packet.buffer.p1(timeDelta % count);
                    packet.buffer.pos = prev;
                    stream.writeLater(packet);
                }

                if (lastIndex < recorder.caret) {
                    recorder.caret -= lastIndex;
                    System.arraycopy(recorder.xs, lastIndex, recorder.xs, 0, recorder.caret);
                    System.arraycopy(recorder.ys, lastIndex, recorder.ys, 0, recorder.caret);
                    System.arraycopy(recorder.times, lastIndex, recorder.times, 0, recorder.caret);
                } else {
                    recorder.caret = 0;
                }
            }
        }
    }

    @Override
    public void processMouseActionRecords() {
        if (Mouse.clickMeta == 1 || !WorldMapObjectIcon.mouseCameraEnabled && Mouse.clickMeta == 4 || Mouse.clickMeta == 2) {
            long clickTimeDelta = (Mouse.timeOfClick - client.timeOfPreviousClick * -1L) / 50L;
            if (clickTimeDelta > 4095L) {
                clickTimeDelta = 4095L;
            }

            client.timeOfPreviousClick = Mouse.timeOfClick * -1L;
            int y = Mouse.clickY;
            if (y < 0) {
                y = 0;
            } else if (y > client.canvasHeight) {
                y = client.canvasHeight;
            }

            int x = Mouse.clickX;
            if (x < 0) {
                x = 0;
            } else if (x > client.canvasWidth) {
                x = client.canvasWidth;
            }

            int compressedClickTimeDelta = (int) clickTimeDelta;
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.MOUSE_ACTION, stream.encryptor);
            packet.buffer.p2((Mouse.clickMeta == 2 ? 1 : 0) + (compressedClickTimeDelta << 1));
            packet.buffer.p2(x);
            packet.buffer.p2(y);
            stream.writeLater(packet);
        }
    }

    @Override
    public void processKeyInfo() {
        if (Keyboard.pressedKeysCount > 0) {
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.KEY_INFO, stream.encryptor);
            packet.buffer.p2(0);
            int startOffset = packet.buffer.pos;
            long time = Clock.now();

            for (int i = 0; i < Keyboard.pressedKeysCount; ++i) {
                long timeOffset = time - client.timeOfPreviousKeyPress;
                if (timeOffset > 0xffffffL) {
                    timeOffset = 0xffffffL;
                }

                client.timeOfPreviousKeyPress = time;
                packet.buffer.ip3((int) timeOffset);
                packet.buffer.writeByteS(Keyboard.pressedKeyIndices[i]);
            }

            packet.buffer.psize2(packet.buffer.pos - startOffset);
            stream.writeLater(packet);
        }
    }

    @Override
    public void processCameraInfo() {
        if (Camera.packetIndicator > 0) {
            --Camera.packetIndicator;
        }

        if (Keyboard.heldKeys[96] || Keyboard.heldKeys[97] || Keyboard.heldKeys[98] || Keyboard.heldKeys[99]) {
            Camera.emitPackets = true;
        }

        if (Camera.emitPackets && Camera.packetIndicator <= 0) {
            Camera.packetIndicator = 20;
            Camera.emitPackets = false;

            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.CAMERA_INFO, stream.encryptor);
            packet.buffer.ip2a(Camera.minimumPitch);
            packet.buffer.ip2(Camera.yOffset);
            stream.writeLater(packet);
        }
    }

    @Override
    public void processFocusInfo() {
        if (client.hasFocus && !client.previousFocusState) {
            client.previousFocusState = true;

            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.FOCUS_INFO, stream.encryptor);
            packet.buffer.p1(1);
            stream.writeLater(packet);
        }

        if (!client.hasFocus && client.previousFocusState) {
            client.previousFocusState = false;

            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.FOCUS_INFO, stream.encryptor);
            packet.buffer.p1(0);
            stream.writeLater(packet);
        }
    }
}
