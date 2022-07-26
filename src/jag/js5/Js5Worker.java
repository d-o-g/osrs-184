package jag.js5;

import jag.commons.collection.NodeTable;
import jag.commons.collection.Queue;
import jag.commons.time.Clock;
import jag.game.Camera;
import jag.game.client;
import jag.opcode.Buffer;
import jag.opcode.Connection;
import jag.opcode.OutgoingPacket;
import jag.opcode.ClientProt;

import java.io.IOException;
import java.util.zip.CRC32;

public class Js5Worker {

    public static final NodeTable<ResourceRequest> pending = new NodeTable<>(4096);
    public static final NodeTable<ResourceRequest> pendingPriority = new NodeTable<>(4096);
    public static final Queue<ResourceRequest> pendingQueue = new Queue<>();
    public static final NodeTable<ResourceRequest> pendingPriorityRecv = new NodeTable<>(32);
    public static final NodeTable<ResourceRequest> pendingRecv = new NodeTable<>(4096);
    public static final Archive[] ARCHIVES = new Archive[256];
    public static final Buffer responseHeader = new Buffer(8);
    public static final CRC32 crc = new CRC32();
    public static int mismatchedCrcCount = 0;
    public static int ioExceptionCount = 0;
    public static int latency = 0;
    public static long lastAttempt;
    public static int pendingPriorityCount = 0;
    public static int pendingPriorityResponseCount = 0;
    public static int pendingResponseCount = 0;
    public static Connection connection;
    public static int pendingCount = 0;
    public static int anInt1499 = 0;
    public static byte encryptionKey = 0;
    public static ResourceRequest current;
    public static Buffer archiveBuffer;

    public static boolean method1094(int var0, int var1) {
        return var0 != 4 || var1 < 8;
    }

    public static void setOculusOrbMode(int var0) {
        Camera.oculusOrbMode = var0;
    }

    public static void method1093(String var0) {
        if (client.friendsChatSystem != null) {
            OutgoingPacket packet = OutgoingPacket.prepare(ClientProt.KICK_CLANCHANNEL_USER, client.netWriter.encryptor);
            packet.buffer.p1(Buffer.stringLengthPlusOne(var0));
            packet.buffer.pcstr(var0);
            client.netWriter.writeLater(packet);
        }
    }

    public static void request(Archive archive, int var1, int var2, int crc, byte padding, boolean var5) {
        long var6 = (var1 << 16) + var2;
        ResourceRequest req = pendingPriority.lookup(var6);
        if (req == null) {
            req = pendingPriorityRecv.lookup(var6);
            if (req == null) {
                req = pending.lookup(var6);
                if (req != null) {
                    if (var5) {
                        req.unlinkDoubly();
                        pendingPriority.put(req, var6);
                        --pendingCount;
                        ++pendingPriorityCount;
                    }

                } else {
                    if (!var5) {
                        req = pendingRecv.lookup(var6);
                        if (req != null) {
                            return;
                        }
                    }

                    req = new ResourceRequest();
                    req.archive = archive;
                    req.crc = crc;
                    req.padding = padding;
                    if (var5) {
                        pendingPriority.put(req, var6);
                        ++pendingPriorityCount;
                    } else {
                        pendingQueue.insert(req);
                        pending.put(req, var6);
                        ++pendingCount;
                    }

                }
            }
        }
    }

    public static void kill(boolean var0) {
        if (connection != null) {
            try {
                Buffer var1 = new Buffer(4);
                var1.p1(var0 ? 2 : 3);
                var1.p3(0);
                connection.write(var1.payload, 0, 4);
            } catch (IOException var4) {
                try {
                    connection.stop();
                } catch (Exception ignored) {
                }

                ++ioExceptionCount;
                connection = null;
            }

        }
    }

    public static void start(Connection connection, boolean var1) {
        if (Js5Worker.connection != null) {
            try {
                Js5Worker.connection.stop();
            } catch (Exception ignored) {
            }

            Js5Worker.connection = null;
        }

        Js5Worker.connection = connection;
        kill(var1);
        responseHeader.pos = 0;
        current = null;
        archiveBuffer = null;
        anInt1499 = 0;

        while (true) {
            ResourceRequest var2 = pendingPriorityRecv.head();
            if (var2 == null) {
                while (true) {
                    var2 = pendingRecv.head();
                    if (var2 == null) {
                        if (encryptionKey != 0) {
                            try {
                                Buffer var7 = new Buffer(4);
                                var7.p1(4);
                                var7.p1(encryptionKey);
                                var7.p2(0);
                                Js5Worker.connection.write(var7.payload, 0, 4);
                            } catch (IOException var5) {
                                try {
                                    Js5Worker.connection.stop();
                                } catch (Exception ignored) {
                                }

                                ++ioExceptionCount;
                                Js5Worker.connection = null;
                            }
                        }

                        latency = 0;
                        lastAttempt = Clock.now();
                        return;
                    }

                    pendingQueue.add(var2);
                    pending.put(var2, var2.key);
                    ++pendingCount;
                    --pendingResponseCount;
                }
            }

            pendingPriority.put(var2, var2.key);
            ++pendingPriorityCount;
            --pendingPriorityResponseCount;
        }
    }

    public static void destroy() {
        if (connection != null) {
            connection.stop();
        }
    }
}
