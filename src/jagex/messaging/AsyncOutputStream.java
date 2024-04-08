package jagex.messaging;

import jagex.oldscape.LoginScreenEffect;
import jagex.jagex3.client.input.keyboard.Keyboard;
import jagex.jagex3.client.input.mouse.Mouse;
import jagex.oldscape.client.*;
import jagex.oldscape.client.minimenu.ContextMenu;
import jagex.oldscape.client.scene.entity.PlayerEntity;
import jagex.jagex3.graphics.ComponentSprite;
import jagex.jagex3.graphics.JagGraphics3D;
import jagex.oldscape.shared.prot.ClientProt;
import jagex.oldscape.shared.prot.OutgoingPacket;
import jagex.oldscape.client.worldmap.WorldMapObjectIcon;

import java.io.IOException;
import java.io.OutputStream;

public class AsyncOutputStream implements Runnable {

  public static LoginScreenEffect loginScreenEffect;

  final OutputStream output;
  final Thread thread;
  final int payloadCapacity;
  final byte[] payload;
  boolean closed;
  IOException error;
  int writeIndex;
  int readIndex;

  AsyncOutputStream(OutputStream output, int payloadCapacity) {
    writeIndex = 0;
    readIndex = 0;
    this.output = output;
    this.payloadCapacity = payloadCapacity + 1;
    payload = new byte[this.payloadCapacity];
    thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  public static void processMinimapClick(Component component, int x, int y) {
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
      relativeX -= image.width / 2;
      relativeY -= image.height / 2;
      int rot = Camera.yOffset & 0x7ff;
      int sin = JagGraphics3D.SIN_TABLE[rot];
      int cos = JagGraphics3D.COS_TABLE[rot];
      int dx = relativeY * sin + relativeX * cos >> 11;
      int dy = relativeY * cos - sin * relativeX >> 11;
      int sceneX = dx + PlayerEntity.local.absoluteX >> 7;
      int sceneY = PlayerEntity.local.absoluteY - dy >> 7;
      OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.WALK_MAP, client.stream.encryptor);
      packet.buffer.p1(18);
      packet.buffer.ip2_alt1(client.baseX + sceneX);
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
      client.stream.writeLater(packet);
      client.destinationX = sceneX;
      client.destinationY = sceneY;
    }
  }

  void write(byte[] buffer, int offset, int length) throws IOException {
    if (length >= 0 && offset >= 0 && length + offset <= buffer.length) {
      synchronized (this) {
        if (error != null) {
          throw new IOException(error.toString());
        }
        int available;
        if (writeIndex <= readIndex) {
          available = payloadCapacity - readIndex + writeIndex - 1;
        } else {
          available = writeIndex - readIndex - 1;
        }

        if (available < length) {
          throw new IOException("");
        }
        if (length + readIndex <= payloadCapacity) {
          System.arraycopy(buffer, offset, payload, readIndex, length);
        } else {
          int preLength = payloadCapacity - readIndex;
          System.arraycopy(buffer, offset, payload, readIndex, preLength);
          System.arraycopy(buffer, preLength + offset, payload, 0, length - preLength);
        }

        readIndex = (length + readIndex) % payloadCapacity;
        notifyAll();
      }
    } else {
      throw new IOException();
    }
  }

  void close() {
    synchronized (this) {
      closed = true;
      notifyAll();
    }

    try {
      thread.join();
    } catch (InterruptedException ignored) {
    }
  }

  boolean closeIfPending() {
    if (closed) {
      try {
        output.close();
        if (error == null) {
          error = new IOException("");
        }
      } catch (IOException e) {
        if (error == null) {
          error = new IOException(e);
        }
      }

      return true;
    }
    return false;
  }

  public void run() {
    do {
      int available;
      synchronized (this) {
        while (true) {
          if (error != null) {
            return;
          }

          if (writeIndex <= readIndex) {
            available = readIndex - writeIndex;
          } else {
            available = payloadCapacity - writeIndex + readIndex;
          }

          if (available > 0) {
            break;
          }

          try {
            output.flush();
          } catch (IOException var11) {
            error = var11;
            return;
          }

          if (closeIfPending()) {
            return;
          }

          try {
            wait();
          } catch (InterruptedException ignored) {
          }
        }
      }

      try {
        if (available + writeIndex <= payloadCapacity) {
          output.write(payload, writeIndex, available);
        } else {
          int preLength = payloadCapacity - writeIndex;
          output.write(payload, writeIndex, preLength);
          output.write(payload, 0, available - preLength);
        }
      } catch (IOException e) {
        synchronized (this) {
          error = e;
          return;
        }
      }

      synchronized (this) {
        writeIndex = (available + writeIndex) % payloadCapacity;
      }
    } while (!closeIfPending());

  }
}
