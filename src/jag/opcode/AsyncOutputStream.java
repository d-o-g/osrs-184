package jag.opcode;

import jag.ClientParameter;
import jag.LoginScreenEffect;
import jag.commons.input.Keyboard;
import jag.commons.input.Mouse;
import jag.game.*;
import jag.game.menu.ContextMenu;
import jag.game.scene.entity.PlayerEntity;
import jag.graphics.ComponentSprite;
import jag.graphics.JagGraphics3D;
import jag.worldmap.WorldMapObjectIcon;

import java.io.IOException;
import java.io.OutputStream;

public class AsyncOutputStream implements Runnable {

    public static LoginScreenEffect loginScreenEffect;

    final OutputStream output;
    final Thread thread;
    final int payloadCapacity;
    final byte[] payload;
    boolean aBoolean109;
    IOException error;
    int anInt104;
    int anInt102;

    AsyncOutputStream(OutputStream output, int payloadCapacity) {
        anInt104 = 0;
        anInt102 = 0;
        this.output = output;
        this.payloadCapacity = payloadCapacity + 1;
        payload = new byte[this.payloadCapacity];
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public static int method15(ClientParameter var0) {
        if (var0 == null) {
            return 12;
        }
        switch (var0.id) {
            case 0:
                return 20;
            default:
                return 12;
        }
    }

    public static void processMinimapClick(InterfaceComponent component, int x, int y) {
        if (client.mapState != 0 && client.mapState != 3 || ContextMenu.open
                || (Mouse.clickMeta != 1 && (WorldMapObjectIcon.mouseCameraEnabled || Mouse.clickMeta != 4))) {
            return;
        }

        ComponentSprite image = component.getSprite(true);
        if (image == null) {
            return;
        }

        int relativeX = Mouse.clickX - x;
        int relativeY = Mouse.clickY - y;
        if (image.contains(relativeX, relativeY)) {
            relativeX -= image.anInt380 / 2;
            relativeY -= image.anInt568 / 2;
            int rot = Camera.yOffset & 0x7ff;
            int sin = JagGraphics3D.SIN_TABLE[rot];
            int cos = JagGraphics3D.COS_TABLE[rot];
            int dx = relativeY * sin + relativeX * cos >> 11;
            int dy = relativeY * cos - sin * relativeX >> 11;
            int sceneX = dx + PlayerEntity.local.absoluteX >> 7;
            int sceneY = PlayerEntity.local.absoluteY - dy >> 7;
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.WALK_MAP, client.netWriter.encryptor);
            packet.buffer.p1(18);
            packet.buffer.ip2a(client.baseX + sceneX);
            packet.buffer.ip2(client.baseY + sceneY);
            packet.buffer.p1n(Keyboard.heldKeys[82] ? (Keyboard.heldKeys[81] ? 2 : 1) : 0);
            packet.buffer.p1(relativeX);
            packet.buffer.p1(relativeY);
            packet.buffer.p2(Camera.yOffset);
            packet.buffer.p1(57);
            packet.buffer.p1(0);
            packet.buffer.p1(0);
            packet.buffer.p1(89);
            packet.buffer.p2(PlayerEntity.local.absoluteX);
            packet.buffer.p2(PlayerEntity.local.absoluteY);
            packet.buffer.p1(63);
            client.netWriter.writeLater(packet);
            client.destinationX = sceneX;
            client.destinationY = sceneY;
        }
    }

    void write(byte[] var1, int var2, int var3) throws IOException {
        if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
            synchronized (this) {
                if (error != null) {
                    throw new IOException(error.toString());
                }
                int var5;
                if (anInt104 <= anInt102) {
                    var5 = payloadCapacity - anInt102 + anInt104 - 1;
                } else {
                    var5 = anInt104 - anInt102 - 1;
                }

                if (var5 < var3) {
                    throw new IOException("");
                }
                if (var3 + anInt102 <= payloadCapacity) {
                    System.arraycopy(var1, var2, payload, anInt102, var3);
                } else {
                    int var6 = payloadCapacity - anInt102;
                    System.arraycopy(var1, var2, payload, anInt102, var6);
                    System.arraycopy(var1, var6 + var2, payload, 0, var3 - var6);
                }

                anInt102 = (var3 + anInt102) % payloadCapacity;
                notifyAll();
            }
        } else {
            throw new IOException();
        }
    }

    void close() {
        synchronized (this) {
            aBoolean109 = true;
            notifyAll();
        }

        try {
            thread.join();
        } catch (InterruptedException ignored) {
        }
    }

    boolean method14() {
        if (aBoolean109) {
            try {
                output.close();
                if (error == null) {
                    error = new IOException("");
                }
            } catch (IOException var2) {
                if (error == null) {
                    error = new IOException(var2);
                }
            }

            return true;
        }
        return false;
    }

    public void run() {
        do {
            int var2;
            synchronized (this) {
                while (true) {
                    if (error != null) {
                        return;
                    }

                    if (anInt104 <= anInt102) {
                        var2 = anInt102 - anInt104;
                    } else {
                        var2 = payloadCapacity - anInt104 + anInt102;
                    }

                    if (var2 > 0) {
                        break;
                    }

                    try {
                        output.flush();
                    } catch (IOException var11) {
                        error = var11;
                        return;
                    }

                    if (method14()) {
                        return;
                    }

                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            try {
                if (var2 + anInt104 <= payloadCapacity) {
                    output.write(payload, anInt104, var2);
                } else {
                    int var5 = payloadCapacity - anInt104;
                    output.write(payload, anInt104, var5);
                    output.write(payload, 0, var2 - var5);
                }
            } catch (IOException var10) {
                synchronized (this) {
                    error = var10;
                    return;
                }
            }

            synchronized (this) {
                anInt104 = (var2 + anInt104) % payloadCapacity;
            }
        } while (!method14());

    }
}
