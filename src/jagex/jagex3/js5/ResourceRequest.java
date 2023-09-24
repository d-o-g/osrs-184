package jagex.jagex3.js5;

import jagex.datastructure.DoublyLinkedNode;
import jagex.core.time.Clock;
import jagex.oldscape.client.social.AssociateComparator_Sub4;
import jagex.messaging.Buffer;
import jagex.oldscape.client.worldmap.WorldMapIcon;

import java.io.IOException;

public class ResourceRequest extends DoublyLinkedNode {

  public byte padding;
  public Archive archive;
  public int crc;

  public ResourceRequest() {

  }

  public static boolean processResources() {
    long time = Clock.now();
    int change = (int) (time - Js5Worker.lastAttempt);
    Js5Worker.lastAttempt = time;
    if (change > 200) {
      change = 200;
    }

    Js5Worker.latency += change;
    if (Js5Worker.pendingResponseCount == 0 && Js5Worker.pendingPriorityResponseCount == 0
        && Js5Worker.pendingCount == 0 && Js5Worker.pendingPriorityCount == 0) {
      return true;
    }

    if (Js5Worker.connection == null) {
      return false;
    }

    try {
      if (Js5Worker.latency > 30000) {
        throw new IOException();
      }
      ResourceRequest buffer;
      Buffer var5;
      while (Js5Worker.pendingPriorityResponseCount < 200 && Js5Worker.pendingPriorityCount > 0) {
        buffer = Js5Worker.pendingPriority.head();
        var5 = new Buffer(4);
        var5.p1(1);
        var5.p3((int) buffer.key);
        Js5Worker.connection.write(var5.payload, 0, 4);
        Js5Worker.pendingPriorityRecv.put(buffer, buffer.key);
        --Js5Worker.pendingPriorityCount;
        ++Js5Worker.pendingPriorityResponseCount;
      }

      while (Js5Worker.pendingResponseCount < 200 && Js5Worker.pendingCount > 0) {
        buffer = Js5Worker.pendingQueue.head();
        var5 = new Buffer(4);
        var5.p1(0);
        var5.p3((int) buffer.key);
        Js5Worker.connection.write(var5.payload, 0, 4);
        buffer.unlinkDoubly();
        Js5Worker.pendingRecv.put(buffer, buffer.key);
        --Js5Worker.pendingCount;
        ++Js5Worker.pendingResponseCount;
      }

      for (int input = 0; input < 100; ++input) {
        int available = Js5Worker.connection.available();
        if (available < 0) {
          throw new IOException();
        }

        if (available == 0) {
          break;
        }

        Js5Worker.latency = 0;
        byte var8 = 0;
        if (Js5Worker.current == null) {
          var8 = 8;
        } else if (Js5Worker.anInt1499 == 0) {
          var8 = 1;
        }

        int var9;
        int var10;
        int var11;
        int var13;
        byte[] header;
        int var10001;
        Buffer var23;
        if (var8 > 0) {
          var9 = var8 - Js5Worker.responseHeader.pos;
          if (var9 > available) {
            var9 = available;
          }

          Js5Worker.connection.read(Js5Worker.responseHeader.payload, Js5Worker.responseHeader.pos, var9);
          if (Js5Worker.encryptionKey != 0) {
            for (var10 = 0; var10 < var9; ++var10) {
              header = Js5Worker.responseHeader.payload;
              var10001 = Js5Worker.responseHeader.pos + var10;
              header[var10001] ^= Js5Worker.encryptionKey;
            }
          }

          var23 = Js5Worker.responseHeader;
          var23.pos += var9;
          if (Js5Worker.responseHeader.pos < var8) {
            break;
          }

          if (Js5Worker.current == null) {
            Js5Worker.responseHeader.pos = 0;
            var10 = Js5Worker.responseHeader.g1();
            var11 = Js5Worker.responseHeader.g2();
            int var12 = Js5Worker.responseHeader.g1();
            var13 = Js5Worker.responseHeader.g4();
            long var14 = var11 + ((long) var10 << 16);
            ResourceRequest var16 = Js5Worker.pendingPriorityRecv.lookup(var14);
            AssociateComparator_Sub4.aBoolean804 = true;
            if (var16 == null) {
              var16 = Js5Worker.pendingRecv.lookup(var14);
              AssociateComparator_Sub4.aBoolean804 = false;
            }

            if (var16 == null) {
              throw new IOException();
            }

            int var17 = var12 == 0 ? 5 : 9;
            Js5Worker.current = var16;
            Js5Worker.archiveBuffer = new Buffer(var13 + var17 + Js5Worker.current.padding);
            Js5Worker.archiveBuffer.p1(var12);
            Js5Worker.archiveBuffer.p4(var13);
            Js5Worker.anInt1499 = 8;
            Js5Worker.responseHeader.pos = 0;
          } else if (Js5Worker.anInt1499 == 0) {
            if (Js5Worker.responseHeader.payload[0] == -1) {
              Js5Worker.anInt1499 = 1;
              Js5Worker.responseHeader.pos = 0;
            } else {
              Js5Worker.current = null;
            }
          }
        } else {
          var9 = Js5Worker.archiveBuffer.payload.length - Js5Worker.current.padding;
          var10 = 512 - Js5Worker.anInt1499;
          if (var10 > var9 - Js5Worker.archiveBuffer.pos) {
            var10 = var9 - Js5Worker.archiveBuffer.pos;
          }

          if (var10 > available) {
            var10 = available;
          }

          Js5Worker.connection.read(Js5Worker.archiveBuffer.payload, Js5Worker.archiveBuffer.pos, var10);
          if (Js5Worker.encryptionKey != 0) {
            for (var11 = 0; var11 < var10; ++var11) {
              header = Js5Worker.archiveBuffer.payload;
              var10001 = var11 + Js5Worker.archiveBuffer.pos;
              header[var10001] ^= Js5Worker.encryptionKey;
            }
          }

          var23 = Js5Worker.archiveBuffer;
          var23.pos += var10;
          Js5Worker.anInt1499 += var10;
          if (var9 == Js5Worker.archiveBuffer.pos) {
            if (16711935L == Js5Worker.current.key) {
              WorldMapIcon.aBuffer314 = Js5Worker.archiveBuffer;

              for (var11 = 0; var11 < 256; ++var11) {
                Archive var18 = Js5Worker.ARCHIVES[var11];
                if (var18 != null) {
                  WorldMapIcon.aBuffer314.pos = var11 * 8 + 5;
                  var13 = WorldMapIcon.aBuffer314.g4();
                  int var19 = WorldMapIcon.aBuffer314.g4();
                  var18.method493(var13, var19);
                }
              }
            } else {
              Js5Worker.crc.reset();
              Js5Worker.crc.update(Js5Worker.archiveBuffer.payload, 0, var9);
              var11 = (int) Js5Worker.crc.getValue();
              if (var11 != Js5Worker.current.crc) {
                try {
                  Js5Worker.connection.stop();
                } catch (Exception ignored) {

                }

                ++Js5Worker.mismatchedCrcCount;
                Js5Worker.connection = null;
                Js5Worker.encryptionKey = (byte) ((int) (Math.random() * 255.0D + 1.0D));
                return false;
              }

              Js5Worker.mismatchedCrcCount = 0;
              Js5Worker.ioExceptionCount = 0;
              Js5Worker.current.archive.method498((int) (Js5Worker.current.key & 65535L), Js5Worker.archiveBuffer.payload, (Js5Worker.current.key & 16711680L) == 16711680L, AssociateComparator_Sub4.aBoolean804);
            }

            Js5Worker.current.unlink();
            if (AssociateComparator_Sub4.aBoolean804) {
              --Js5Worker.pendingPriorityResponseCount;
            } else {
              --Js5Worker.pendingResponseCount;
            }

            Js5Worker.anInt1499 = 0;
            Js5Worker.current = null;
            Js5Worker.archiveBuffer = null;
          } else {
            if (Js5Worker.anInt1499 != 512) {
              break;
            }

            Js5Worker.anInt1499 = 0;
          }
        }
      }

      return true;
    } catch (IOException var22) {
      try {
        Js5Worker.connection.stop();
      } catch (Exception ignored) {
      }

      ++Js5Worker.ioExceptionCount;
      Js5Worker.connection = null;
      return false;
    }
  }
}
